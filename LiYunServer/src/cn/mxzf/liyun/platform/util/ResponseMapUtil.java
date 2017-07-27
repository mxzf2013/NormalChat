package cn.mxzf.liyun.platform.util;

import java.util.HashMap;
import java.util.Map;
import cn.mxzf.liyun.platform.commend.Configs.MsgStatus;
import cn.mxzf.liyun.platform.commend.Configs.MsgType;

/**
 * 响应构造器
 * 
 * @name ResponseMapUtil
 * @author Deleter
 * @time 2017年7月25日 上午10:41:35
 */
public class ResponseMapUtil {

	public static Map<String, Object> createMessageRsp(MsgType type,
			MsgStatus status, String info) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ResponseType", type);
		map.put("MsgStatus", status);
		map.put("Info", info);
		return map;
	}

	public static Map<String, Object> createMessageWithDataRsp(MsgType type,
			MsgStatus status, Object data, String info) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ResponseType", type);
		map.put("MsgStatus", status);
		map.put("MsgData", data);
		map.put("Info", info);
		return map;
	}

	public static Map<String, Object> createSendMessageRsp(Object toUsername,
			MsgStatus status, String info) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ResponseType", MsgType.TEXT_RSP);
		map.put("toUsername", toUsername);
		map.put("MsgStatus", status);
		map.put("Info", info);
		return map;
	}
}
