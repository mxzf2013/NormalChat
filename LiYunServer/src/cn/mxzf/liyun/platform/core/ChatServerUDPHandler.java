package cn.mxzf.liyun.platform.core;

import java.util.Map;
import cn.mxzf.liyun.platform.commend.Configs.MsgType;
import cn.mxzf.liyun.platform.util.JsonUtil;
import cn.mxzf.liyun.platform.visual.NetMapperListDataBase;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

public class ChatServerUDPHandler extends
		SimpleChannelInboundHandler<DatagramPacket> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet)
			throws Exception {
		// 读取收到的数据
		ByteBuf buf = (ByteBuf) packet.copy().content();
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req, CharsetUtil.UTF_8);
		System.out.println("【NOTE】>>>>>> 收到客户端的数据：" + body);
		if (body != null) {
			Map<String, Object> map = JsonUtil.parseObject(body);

			Object msgType = map.get("RequestType");

			if (msgType != null) {
				if (MsgType.LOGIN.name().equals(msgType)) {
					ChatServerUDP.workGroup.execute(new LoginTask(map, ctx,
							packet));
				}
			}
		}
	}

	private class LoginTask implements Runnable {

		private Map<String, Object> map;
		private ChannelHandlerContext ctx;
		private DatagramPacket packet;

		public LoginTask(Map<String, Object> map, ChannelHandlerContext ctx,
				DatagramPacket packet) {
			this.map = map;
			this.ctx = ctx;
			this.packet = packet;
		}

		@Override
		public void run() {
			NetMapperListDataBase.put(map.get("Username").toString(), ctx);
			// 回复一条信息给客户端
			try {
				ctx.writeAndFlush(
						new DatagramPacket(Unpooled.copiedBuffer(
								"Hello，我是Server，您登陆成功", CharsetUtil.UTF_8),
								packet.sender())).sync();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}