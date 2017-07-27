package cn.mxzf.liyun.platform.core;

import java.util.HashMap;
import java.util.Map;

import cn.mxzf.liyun.platform.commend.Configs.MsgType;
import cn.mxzf.liyun.platform.util.JsonUtil;

public class ChatClientUDP {

	public static void main(String[] args) throws Exception {
		// 初始化本地UDP的Socket
		LocalUDPSocketProvider.getInstance().initSocket();
		// 启动本地UDP监听（接收数据用的）
		LocalUDPDataReciever.getInstance().startup();

		// 循环发送数据给服务端
		while (true) {
			// 要发送的数据
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("RequestType", MsgType.LOGIN);
			map.put("Username", "1943815081");
			byte[] soServerBytes = JsonUtil.toJsonString(map).getBytes("UTF-8");

			// 开始发送
			boolean ok = UDPUtils.send(soServerBytes, soServerBytes.length);
			if (ok)
				System.out.println("EchoClient: 发往服务端的信息已送出.");
			else
				System.out.println("EchoClient: 发往服务端的信息没有成功发出！！！");

			// 3000秒后进入下一次循环
			Thread.sleep(3000);
		}
	}
}
