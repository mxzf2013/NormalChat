package cn.mxzf.liyun.platform.task;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import cn.mxzf.liyun.platform.commend.Configs.MsgStatus;
import cn.mxzf.liyun.platform.commend.Configs.MsgType;
import cn.mxzf.liyun.platform.domain.User;
import cn.mxzf.liyun.platform.util.JsonUtil;
import cn.mxzf.liyun.platform.visual.UserDataBase;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;


/**
 * 创建用户名
 *
 * @author Deleter
 * @version 2017年7月22日
 * @see CreateAccountTask
 * @since
 */
public class CreateAccountTask implements Runnable
{
    private ChannelHandlerContext ctx;

    private Map<String, Object> map;

    public CreateAccountTask(ChannelHandlerContext ctx, Map<String, Object> map)
    {
        this.ctx = ctx;
        this.map = map;
    }

    @Override
    public void run()
    {
        String username = String.valueOf(map.get("UserName"));
        String password = String.valueOf(map.get("PassWord"));

        boolean isExist = false;
        Set<User> users = UserDataBase.userList;
        Iterator<User> iterator = users.iterator();
        User user;
        while (iterator.hasNext())
        {
            user = iterator.next();
            if (username.equals(user.getUsername()))
            {
                isExist = true;
                break;
            }
        }
        // 反馈信息
        Map<String, Object> rsp = new HashMap<String, Object>();
        rsp.put("ResponseType", MsgType.CREATE);
        if (!isExist)
        {
            // 添加用户
            UserDataBase.userList.add(new User(username, password));
            rsp.put("MsgStatus", MsgStatus.SUCCESS);
            rsp.put("Info", "注册成功");
            response(ctx, rsp);
        }
        else
        {
            rsp.put("MsgStatus", MsgStatus.ERROR);
            rsp.put("Info", "用户已存在");
            response(ctx, rsp);
        }
    }

    /**
     * 反馈客户端
     * 
     * @param ctx
     * @param messageEntry
     */
    private void response(ChannelHandlerContext ctx, Map<String, Object> map)
    {
        String body = JsonUtil.toJsonString(map) + "$_";
        ctx.writeAndFlush(Unpooled.copiedBuffer(body.getBytes()));
    }
}
