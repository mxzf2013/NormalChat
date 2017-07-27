package cn.mxzf.liyun.platform.task;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import cn.mxzf.liyun.platform.commend.Configs.MsgStatus;
import cn.mxzf.liyun.platform.commend.Configs.MsgType;
import cn.mxzf.liyun.platform.domain.User;
import cn.mxzf.liyun.platform.util.JsonUtil;
import cn.mxzf.liyun.platform.util.ResponseMapUtil;
import cn.mxzf.liyun.platform.visual.OnLineListDataBase;
import cn.mxzf.liyun.platform.visual.UserDataBase;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

/**
 * 帐号登录
 *
 * @author Deleter
 * @version 2017年7月22日
 * @see LoginAccountTask
 * @since
 */
public class LoginAccountTask implements Runnable {
	private ChannelHandlerContext ctx;

	private Map<String, Object> map;

	public LoginAccountTask(ChannelHandlerContext ctx, Map<String, Object> map) {
		this.ctx = ctx;
		this.map = map;
	}

	@Override
	public void run() {
		String username = String.valueOf(map.get("UserName"));
		String password = String.valueOf(map.get("PassWord"));

		if (OnLineListDataBase.getCtx(username) != null) {
			// 反馈信息
			response(ctx, ResponseMapUtil.createMessageRsp(MsgType.LOGIN,
					MsgStatus.ERROR, "请不要重复登录"));
			return;
		}

		User user = null;
		boolean isExist = false;
		Set<User> users = UserDataBase.userList;
		Iterator<User> iterator = users.iterator();
		while (iterator.hasNext()) {
			user = iterator.next();
			if (user.getUsername().equals(username)
					&& user.getPassword().equals(password)) {
				isExist = true;
				break;
			}
		}

		// 反馈信息
		if (isExist) {
			System.out.println(username);
			OnLineListDataBase.put(username, ctx);
			response(ctx, ResponseMapUtil.createMessageWithDataRsp(
					MsgType.LOGIN, MsgStatus.SUCCESS, user.getUsername(),
					"登入成功"));
		} else {
			response(ctx, ResponseMapUtil.createMessageRsp(MsgType.LOGIN,
					MsgStatus.ERROR, "用户名或密码错误"));
		}
	}

	/**
	 * 反馈客户端
	 * 
	 * @param ctx
	 * @param messageEntry
	 */
	private void response(ChannelHandlerContext ctx, Map<String, Object> map) {
		String body = JsonUtil.toJsonString(map) + "$_";
		ctx.writeAndFlush(Unpooled.copiedBuffer(body.getBytes()));
	}
}
