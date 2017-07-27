package cn.mxzf.liyun.platform.visual;

import io.netty.channel.ChannelHandlerContext;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

/**
 * 外网地址
 */
public class NetMapperListDataBase {
	private static Map<String, ChannelHandlerContext> userToCtx = new Hashtable<String, ChannelHandlerContext>();

	private static Map<ChannelHandlerContext, String> ctxToUser = new Hashtable<ChannelHandlerContext, String>();

	public synchronized static void put(String username,
			ChannelHandlerContext ctx) {
		if (userToCtx.containsKey(username))
			return;
		userToCtx.put(username, ctx);
		ctxToUser.put(ctx, username);
	}

	public synchronized static void remove(ChannelHandlerContext channel) {
		// 移除用户名
		userToCtx.remove(ctxToUser.get(channel));
		// 移除通道
		ctxToUser.remove(channel);
	}

	public static Iterator<String> userToCtxKeySet() {
		return userToCtx.keySet().iterator();
	}

	public static ChannelHandlerContext getCtx(String username) {
		return userToCtx.get(username);
	}

	public static String getUsername(ChannelHandlerContext ctx) {
		return ctxToUser.get(ctx);
	}
}
