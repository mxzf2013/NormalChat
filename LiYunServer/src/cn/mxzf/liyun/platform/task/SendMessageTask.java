package cn.mxzf.liyun.platform.task;

import java.util.Iterator;
import java.util.Map;
import cn.mxzf.liyun.platform.commend.Configs.MsgStatus;
import cn.mxzf.liyun.platform.commend.Configs.MsgType;
import cn.mxzf.liyun.platform.util.JsonUtil;
import cn.mxzf.liyun.platform.util.ResponseMapUtil;
import cn.mxzf.liyun.platform.visual.MessageQueue;
import cn.mxzf.liyun.platform.visual.OnLineListDataBase;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

/**
 * 发送信息
 *
 * @author Deleter
 * @version 2017年7月22日
 * @see SendMessageTask
 * @since
 */
public class SendMessageTask implements Runnable {
	private ChannelHandlerContext ctx;

	private Map<String, Object> map;

	public SendMessageTask(ChannelHandlerContext ctx, Map<String, Object> map) {
		this.ctx = ctx;
		this.map = map;
	}

	@Override
	public void run() {
		String toUserId = map.get("ToUserId").toString();

		if (toUserId != null && !toUserId.trim().equals("")) {
			boolean isExist = false;

			Iterator<String> userIterator = OnLineListDataBase
					.userToCtxKeySet();
			while (userIterator.hasNext()) {
				String userId = userIterator.next();
				if (toUserId.equals(userId)) {
					isExist = true;
					break;
				} else {
					isExist = false;
				}
			}

			// 用户在线
			if (isExist) {
				// 在线[转发]
				map.remove("RequestType");
				map.put("ResponseType", MsgType.TEXT);
				// 响应目标客户端
				response(OnLineListDataBase.getCtx(toUserId), map);
				// 响应请求的客户端
				response(
						ctx,
						ResponseMapUtil.createSendMessageRsp(
								map.get("ToUserId"), MsgStatus.SUCCESS, "发送成功"));
			} else {
				// 不在线[暂存]
				MessageQueue.putToTextQueue(toUserId, map);
				// 响应请求的客户端
				response(ctx, ResponseMapUtil.createMessageRsp(
						MsgType.TEXT_RSP, MsgStatus.ERROR, "对方不在线,待上线后系统会通知对方"));
			}
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
