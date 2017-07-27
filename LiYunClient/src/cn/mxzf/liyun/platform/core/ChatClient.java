package cn.mxzf.liyun.platform.core;

import java.util.Map;
import cn.mxzf.liyun.platform.util.JsonUtil;
import cn.mxzf.liyun.platform.util.RequestMapUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * 聊天客户端
 *
 * @author Deleter
 * @version 2017年7月19日
 * @see ChatClient
 * @since
 */
public class ChatClient extends Thread {
	// 聊天服务器地址
	public final String HOST = "127.0.0.1";

	// 聊天服务器端口
	public final int PORT = 1234;

	private Bootstrap b;

	private Channel channel;

	private ChatClientHandler handler;

	private EventLoopGroup workGroup = new NioEventLoopGroup();

	@Override
	public void run() {
		// 配置客户端NIO线程组
		EventLoopGroup group = new NioEventLoopGroup();

		try {
			b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
					.option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel sc)
								throws Exception {
							ByteBuf delimiter = Unpooled.copiedBuffer("$_"
									.getBytes());
							sc.pipeline().addLast(
									new DelimiterBasedFrameDecoder(1024,
											delimiter));
							sc.pipeline().addLast(new StringDecoder());
							handler = new ChatClientHandler();
							sc.pipeline().addLast(handler);
						}
					});

			// 发起异步连接操作
			// ChannelFuture f = b.connect(hostName, port).sync();
			// 发起同步连接操作
			ChannelFuture f = b.connect(HOST, PORT);
			// 保存
			channel = f.channel();
			// 等待客户端链路关闭
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// 优雅退出，释放NIO线程组
			group.shutdownGracefully();
		}
	}

	/**
	 * 发送到服务器
	 * 
	 * @param str
	 */
	public void request(Map<String, Object> map) {
		String buff = JsonUtil.toJsonString(map) + "$_";
		while (channel == null) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		channel.writeAndFlush(Unpooled.copiedBuffer(buff.getBytes()));
	}

	// 获得适配器对象
	public ChatClientHandler getHandler() {
		return this.handler;
	}

	// 注册
	public void createAccountOnServer(String username, String password,
			String rePassword) {
		request(RequestMapUtil.createCreateMessage(username, password,
				rePassword));
	}

	// 登录
	public void loginOnServer(String username, String password) {
		request(RequestMapUtil.createLoginMessage(username, password));
	}

	// 异步发送消息
	// 登录成功之后返回userId和同步在线人员信息
	public void sendMessageToUser(final String username, final String text) {
		workGroup.execute(new Runnable() {
			@Override
			public void run() {
				while (getHandler().getAccount() == null) {
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				request(RequestMapUtil.createSendMessage(handler.getAccount(),
						username, text));
			}
		});
	}
}