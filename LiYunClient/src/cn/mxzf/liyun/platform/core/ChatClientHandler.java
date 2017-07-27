package cn.mxzf.liyun.platform.core;


import java.util.Map;

import cn.mxzf.liyun.platform.commend.Configs.MsgStatus;
import cn.mxzf.liyun.platform.commend.Configs.MsgType;
import cn.mxzf.liyun.platform.util.JsonUtil;
import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


public class ChatClientHandler extends ChannelInboundHandlerAdapter
{
    // 用户名
    private String account;

    public ChatClientHandler()
    {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx)
        throws Exception
    {
        System.out.println("Connected Server...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
        throws Exception
    {
        Map<String, Object> map = JsonUtil.parseObject(String.valueOf(msg));

        if (map != null && map.get("ResponseType") != null)
        {
            String responseType = String.valueOf(map.get("ResponseType"));
            Object msgStatus = map.get("MsgStatus");

            System.out.println("responseType:" + responseType);

            // 这里接收到的entry为完整的消息包
            if (MsgType.CREATE.name().equals(responseType))
            {
                // if (MsgStatus.SUCCESS.name().equals(msgStatus))
                System.out.println(JSON.toJSONString(map));
            }
            else if (MsgType.LOGIN.name().equals(responseType))
            {
                System.out.println(JSON.toJSONString(map));
                if (MsgStatus.SUCCESS.name().equals(msgStatus))
                {
                    account = String.valueOf(map.get("MsgData"));
                }
            }
            else if (MsgType.TEXT.name().equals(responseType))
            {
                System.out.print(account + ":收到文本消息: ");
                System.out.println(JSON.toJSONString(map));
            }
            else if (MsgType.TEXT_RSP.name().equals(responseType))
            {
                System.out.print(account + ":收到消息反馈: ");
                System.out.println(JSON.toJSONString(map));
            }
            else if (MsgType.IMAGE.name().equals(responseType))
            {

            }
            else if (MsgType.VOICE.name().equals(responseType))
            {

            }
            else if (MsgType.FILE.name().equals(responseType))
            {

            }
            else if (MsgType.LOCATION.name().equals(responseType))
            {

            }
            else if (MsgType.OTHER.name().equals(responseType))
            {

            }
            else if (MsgType.LOGOUT.name().equals(responseType))
            {

            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx)
        throws Exception
    {
        // 读完，清空缓存
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
        throws Exception
    {
        // 异常重连
        cause.printStackTrace();
        ctx.close();
    }

    public String getAccount()
    {
        return account;
    }
}
