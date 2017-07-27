package cn.mxzf.liyun.platform.util;

import java.util.HashMap;
import java.util.Map;
import cn.mxzf.liyun.platform.commend.Configs.MsgType;

/**
 * 请求构造器
 * 
 * @name RequestMapUtil
 * @author Deleter
 * @time 2017年7月25日 上午10:33:34
 */
public class RequestMapUtil {
	/**
	 * 构造注册信息
	 * 
	 * @param username
	 * @param password
	 * @param rePassword
	 * @return
	 */
	public static Map<String, Object> createCreateMessage(String username,
			String password, String rePassword) {

		if (!TextUtil.noEmpty(username, password, rePassword))
			throw new NullPointerException();

		if (!password.equals(rePassword)) {
			System.err.println("密码不一致");
			return null;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("RequestType", MsgType.CREATE);
		map.put("UserName", username);
		map.put("PassWord", password);
		return map;
	}

	/**
	 * 构造登录信息
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public static Map<String, Object> createLoginMessage(String username,
			String password) {

		if (!TextUtil.noEmpty(username, password))
			throw new NullPointerException();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("RequestType", MsgType.LOGIN);
		map.put("UserName", username);
		map.put("PassWord", password);
		return map;
	}

	/**
	 * 构造文本信息
	 * 
	 * @param fromUserId
	 * @param toUserId
	 * @param msgData
	 * @return
	 */
	public static Map<String, Object> createSendMessage(String fromUserId,
			String toUserId, String msgData) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("RequestType", MsgType.TEXT);
		map.put("FromUserId", fromUserId);
		map.put("ToUserId", toUserId);
		map.put("MsgData", msgData);
		return map;
	}
}
