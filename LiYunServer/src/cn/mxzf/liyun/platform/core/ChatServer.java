package cn.mxzf.liyun.platform.core;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;


/**
 * 以分隔符作为码流结束标识的消息的解码
 * 
 * @Title: ChatServer
 * @Dscription:
 * @author Deleter
 * @date 2017年6月24日 上午11:40:30
 * @version 1.0
 */
public class ChatServer extends Thread
{
    public void bind(int port)
    {
        // 配置服务器的NIO线程组
        EventLoopGroup boosgGroup = new NioEventLoopGroup();
        EventLoopGroup workgGroup = new NioEventLoopGroup();
        try
        {
            ServerBootstrap b = new ServerBootstrap();
            b.group(boosgGroup, workgGroup).channel(NioServerSocketChannel.class) // NIO Socket管道
            .option(ChannelOption.SO_BACKLOG, 1024)// 握手成功缓存大小
            .option(ChannelOption.SO_KEEPALIVE, true) // 2小时无数据激活心跳机制
            .handler(new LoggingHandler(LogLevel.INFO)).childHandler(
                new ChannelInitializer<SocketChannel>()
                {

                    @Override
                    protected void initChannel(SocketChannel sc)
                        throws Exception
                    {
                        ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
                        sc.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
                        sc.pipeline().addLast(new StringDecoder());
                        sc.pipeline().addLast(new ChatServerHandler());
                    }
                });
            // 绑定、监听客户端连接
            ChannelFuture f = b.bind(port).sync();
            // 等待服务器套接字关闭
            // 在本例中，这种情况不会发生，但您可以优雅地做到这一点 - 关闭服务器
            f.channel().closeFuture().sync();
        }
        catch (InterruptedException e)
        {
            // e.printStackTrace();
        }
        finally
        {
            // 优雅退出，释放线程池资源
            boosgGroup.shutdownGracefully();
            workgGroup.shutdownGracefully();
        }
    }

    @Override
    public void run()
    {
        bind(1234);
    }
}
