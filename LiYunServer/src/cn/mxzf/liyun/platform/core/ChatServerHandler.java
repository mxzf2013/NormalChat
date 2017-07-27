package cn.mxzf.liyun.platform.core;


import java.util.Map;
import cn.mxzf.liyun.platform.commend.Configs.MsgType;
import cn.mxzf.liyun.platform.task.CreateAccountTask;
import cn.mxzf.liyun.platform.task.LoginAccountTask;
import cn.mxzf.liyun.platform.task.SendMessageTask;
import cn.mxzf.liyun.platform.util.JsonUtil;
import cn.mxzf.liyun.platform.visual.OnLineListDataBase;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.nio.NioEventLoopGroup;


public class ChatServerHandler extends ChannelInboundHandlerAdapter
{
    NioEventLoopGroup workGroup = new NioEventLoopGroup();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
        throws Exception
    {
        // 这里接收到的entry为完整的消息包
        Map<String, Object> map = JsonUtil.parseObject(String.valueOf(msg));
        if (map != null && map.get("RequestType") != null)
        {
            String requestType = String.valueOf(map.get("RequestType"));
            System.out.println("requestType:" + requestType);
            
            
            if (MsgType.CREATE.name().equals(requestType))
            {
                workGroup.execute(new CreateAccountTask(ctx, map));
            }
            else if (MsgType.LOGIN.name().equals(requestType))
            {
                workGroup.execute(new LoginAccountTask(ctx, map));
            }
            else if (MsgType.TEXT.name().equals(requestType))
            {
                workGroup.execute(new SendMessageTask(ctx, map));
            }
            else if (MsgType.IMAGE.name().equals(requestType))
            {

            }
            else if (MsgType.VOICE.name().equals(requestType))
            {

            }
            else if (MsgType.FILE.name().equals(requestType))
            {
            	
            }
            else if (MsgType.LOCATION.equals(requestType))
            {

            }
            else if (MsgType.OTHER.name().equals(requestType))
            {

            }
            else if (MsgType.LOGOUT.name().equals(requestType))
            {

            }
        }
    }

    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
        throws Exception
    {
        cause.printStackTrace();
        // 发生异常，关闭链路
        ctx.close();
        // 从在线列表移除
        OnLineListDataBase.remove(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx)
        throws Exception
    {
        ctx.flush();
        // 它的作用是把消息发送队列中的消息写入SocketChannel中发送给对方
        // 为了防止频繁的唤醒Selector进行消息发送，Netty的write方法，并不直接将消息写入SocketChannel中
        // 调用write方法只是把待发送的消息发到缓冲区中，再调用flush，将发送缓冲区中的消息
        // 全部写到SocketChannel中
    }

    /**
     * 反馈客户端
     * 
     * @param ctx
     * @param messageEntry
     */
    public void response(ChannelHandlerContext ctx, Map<String, Object> map)
    {
        String body = JsonUtil.toJsonString(map) + "$_";
        ctx.writeAndFlush(Unpooled.copiedBuffer(body.getBytes()));
    }
}
