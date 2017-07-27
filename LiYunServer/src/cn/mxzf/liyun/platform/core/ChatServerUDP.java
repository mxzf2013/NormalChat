package cn.mxzf.liyun.platform.core;

import cn.mxzf.liyun.platform.commend.Configs;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class ChatServerUDP {
	public static EventLoopGroup workGroup = new NioEventLoopGroup();

	public static void main(String[] args) throws InterruptedException {
		Bootstrap b = new Bootstrap();
		EventLoopGroup group = new NioEventLoopGroup();
		b.group(group).channel(NioDatagramChannel.class)
				.handler(new ChatServerUDPHandler());

		// 服务端监听在9999端口
		b.bind(Configs.serverUDPPort).sync().channel().closeFuture().await();
	}
}
