package cn.mxzf.liyun.platform.util;


import java.util.HashMap;

import com.alibaba.fastjson.JSON;


/**
 * @Title: JsonUtil
 * @Dscription: Json工具类
 * @author Deleter
 * @date 2017年6月24日 下午2:45:33
 * @version 1.0
 */
public class JsonUtil
{
    public static String toJsonString(Object obj)
    {
        return DesUtil.encrypt(JSON.toJSONString(obj));
    }

    @SuppressWarnings("unchecked")
	public static HashMap<String, Object> parseObject(String data)
    {
        return JSON.parseObject(DesUtil.decrypt(data), HashMap.class);
    }
}
