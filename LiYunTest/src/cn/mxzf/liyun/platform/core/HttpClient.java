package cn.mxzf.liyun.platform.core;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;

import java.io.File;
import java.net.URI;
import java.util.List;

public class HttpClient {
	private StringBuffer resultBuffer = new StringBuffer();
	private EventLoopGroup workerGroup = new NioEventLoopGroup();
	private Object waitObject = new Object();

	private HttpDataFactory factory = null;
	private ChannelFuture future = null;
	private Bootstrap b = null;

	public void connect(String host, int port) throws Exception {
		try {
			this.b = new Bootstrap();
			this.factory = new DefaultHttpDataFactory(
					DefaultHttpDataFactory.MINSIZE);
			b.group(workerGroup);
			b.channel(NioSocketChannel.class);
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					// 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
					ch.pipeline().addLast(new HttpResponseDecoder());
					// 客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
					ch.pipeline().addLast(new HttpRequestEncoder());
					ch.pipeline().addLast(new HttpClientInboundHandler());
				}
			});

			// Start the client.
			this.future = b.connect(host, port).sync();

			URI uri = new URI("http://127.0.0.1:8844");
			String msg = "Are you ok?";
			DefaultFullHttpRequest request = new DefaultFullHttpRequest(
					HttpVersion.HTTP_1_1, HttpMethod.GET, uri.toASCIIString(),
					Unpooled.wrappedBuffer(msg.getBytes("UTF-8")));

			// 构建http请求
			request.headers().set(HttpHeaders.Names.HOST, host);
			request.headers().set(HttpHeaders.Names.CONNECTION,
					HttpHeaders.Values.KEEP_ALIVE);
			request.headers().set(HttpHeaders.Names.CONTENT_LENGTH,
					request.content().readableBytes());
			// 发送http请求
			future.channel().write(request);
			future.channel().flush();
			future.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
		}

	}

	public static void main(String[] args) throws Exception {
		HttpClient client = new HttpClient();
		client.connect("127.0.0.1", 8844);
	}

	// 等待数据的传输通道关闭
	public void shutdownClient() {
		workerGroup.shutdownGracefully();
		factory.cleanAllHttpDatas();
	}

	public boolean isCompleted() {
		while (waitObject != null) {
			// 当通道处于开通和活动时，处于等待
		}
		if (resultBuffer.length() > 0) {
			if ("200".equals(resultBuffer.toString())) {
				resultBuffer.setLength(0);
				return true;
			}
		}
		return false;
	}

	public void uploadFile(String path) {
		if (path == null) {
			System.out.println("上传文件的路径不能为null...");
			return;
		}
		File file = new File(path);
		if (!file.canRead()) {
			System.out.println(file.getName() + "不可读...");
			return;
		}
		if (file.isHidden() || !file.isFile()) {
			System.out.println(file.getName() + "不存在...");
			return;
		}

		try {
			HttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_1,
					HttpMethod.POST, "");

			HttpPostRequestEncoder bodyRequestEncoder = new HttpPostRequestEncoder(
					factory, request, false);

			bodyRequestEncoder.addBodyAttribute("getform", "POST");
			bodyRequestEncoder.addBodyFileUpload("myfile", file,
					"application/x-zip-compressed", false);

			List<InterfaceHttpData> bodylist = bodyRequestEncoder
					.getBodyListAttributes();
			if (bodylist == null) {
				System.out.println("请求体不存在...");
				return;
			}

			HttpRequest request2 = new DefaultHttpRequest(HttpVersion.HTTP_1_1,
					HttpMethod.POST, file.getName());
			HttpPostRequestEncoder bodyRequestEncoder2 = new HttpPostRequestEncoder(
					factory, request2, true);

			bodyRequestEncoder2.setBodyHttpDatas(bodylist);
			bodyRequestEncoder2.finalizeRequest();

			Channel channel = this.future.channel();
			if (channel.isActive() && channel.isWritable()) {
				channel.writeAndFlush(request2);

				if (bodyRequestEncoder2.isChunked()) {
					channel.writeAndFlush(bodyRequestEncoder2)
							.awaitUninterruptibly();
				}

				bodyRequestEncoder2.cleanFiles();
			}
			channel.closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class UpLoadClientIntializer extends
			ChannelInitializer<SocketChannel> {
		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			ChannelPipeline pipeline = ch.pipeline();

			pipeline.addLast("decoder", new HttpResponseDecoder());
			pipeline.addLast("encoder", new HttpRequestEncoder());
			pipeline.addLast("chunkedWriter", new ChunkedWriteHandler());
			pipeline.addLast("dispatcher", new UpLoadClientHandler());
		}
	}

	private class UpLoadClientHandler extends
			SimpleChannelInboundHandler<HttpObject> {
		private boolean readingChunks = false;
		private int succCode = 200;

		protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg)
				throws Exception {
			if (msg instanceof HttpResponse) {
				HttpResponse response = (HttpResponse) msg;

				succCode = response.getStatus().code();

				if (succCode == 200
						&& HttpHeaders.isTransferEncodingChunked(response)) {
					readingChunks = true;
				}
			}

			if (msg instanceof HttpContent) {
				HttpContent chunk = (HttpContent) msg;
				System.out.println("【响应】" + succCode + ">>"
						+ chunk.content().toString(CharsetUtil.UTF_8));
				if (chunk instanceof LastHttpContent) {
					readingChunks = false;
				}
			}

			if (!readingChunks) {
				resultBuffer.append(succCode);
				ctx.channel().close();
			}
		}

		@Override
		public void channelInactive(ChannelHandlerContext ctx) throws Exception {
			waitObject = null;
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
				throws Exception {

			resultBuffer.setLength(0);
			resultBuffer.append(500);
			System.out.println("管道异常：" + cause.getMessage());
			cause.printStackTrace();
			ctx.channel().close();
		}
	}
}