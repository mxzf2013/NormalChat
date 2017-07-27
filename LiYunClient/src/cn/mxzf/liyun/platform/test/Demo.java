package cn.mxzf.liyun.platform.test;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.mxzf.liyun.platform.commend.Configs.ChatType;
import cn.mxzf.liyun.platform.commend.Configs.MsgType;
import cn.mxzf.liyun.platform.core.ChatClient;
import cn.mxzf.liyun.platform.domain.LYMessage;
import cn.mxzf.liyun.platform.domain.User;
import cn.mxzf.liyun.platform.util.JsonUtil;

public class Demo {
	/*
	 * 方法测试
	 */
	public static void main(String[] args) throws InterruptedException,
			UnknownHostException {
		/*
		 * ChatClient clientA = new ChatClient(); clientA.start(); // 模拟重复注册
		 * clientA.createAccountOnServer("1943815081", "123456", "1234567");
		 * clientA.createAccountOnServer("1943815083", "123456", "123456"); //
		 * 模拟重复登录 clientA.loginOnServer("1943815081", "123456");
		 * clientA.loginOnServer("1943815081", "123456"); // 模拟通信 ChatClient
		 * clientB = new ChatClient(); clientB.start();
		 * clientB.loginOnServer("1943815083", "123456");
		 * clientB.sendMessageToUser("1943815081", "你好，我是1943815083");
		 */
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("RequestType", "test");
		// 100条消息
		List<LYMessage> messagesList = new ArrayList<LYMessage>();
		for (int i = 0; i < 100; i++) {
			LYMessage m = new LYMessage();
			m.setChatType(ChatType.SINGLE);
			m.setChatUsername("1943815081" + i);
			m.setContent("哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈");
			m.setMsgType(MsgType.TEXT);
			m.setToChatUsername("1943815082");
			messagesList.add(m);
		}
		map.put("MessagesList", messagesList);
		// 500个好友
		List<User> userList = new ArrayList<User>();
		for (int i = 0; i < 500; i++) {
			User u = new User();
			u.setHeadImg("http://www.umxzf.cn/head/xxxxx.jpg");
			u.setNickname("空。");
			u.setUserId(1000000 + i);
			u.setUsername("1943815081");
			userList.add(u);
		}
		map.put("UserList", userList);
		// 15个分组
		List<List<Integer>> groupList = new ArrayList<List<Integer>>();
		List<Integer> temp;
		for (int i = 0; i < 15; i++) {
			temp = new ArrayList<Integer>();
			for (int j = 0; j < 33; j++) {
				temp.add(j);
			}
			groupList.add(temp);
		}
		map.put("GroupList", groupList);

		System.out.println("未加密");
		String jsonString = JSON.toJSONString(map);
		System.out.println(jsonString);
		System.out.println(jsonString.getBytes().length + " Byte");// b
		System.out.println(jsonString.getBytes().length / 1024.0 + " KByte");// kb
		System.out.println(jsonString.getBytes().length / 1024 / 2014.0
				+ " MByte");// mb

		System.out.println("加密");
		String jsonString2 = JsonUtil.toJsonString(map);
		System.out.println(jsonString2.getBytes().length + " Byte");// b
		System.out.println(jsonString2.getBytes().length / 1024.0 + " KByte");// kb
		System.out.println(jsonString2.getBytes().length / 1024 / 2014.0
				+ " MByte");// mb

	}
}