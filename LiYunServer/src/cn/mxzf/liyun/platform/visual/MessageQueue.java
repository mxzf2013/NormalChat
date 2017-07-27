package cn.mxzf.liyun.platform.visual;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 消息队列
 *
 * @author Deleter
 * @version 2017年7月19日
 * @see MessageQueue
 * @since
 */
public class MessageQueue
{
    // 消息队列
    private static List<Map<String, Object>> textQueue = new ArrayList<>();

    // 图片队列
    private static List<Map<String, Object>> imageQueue = new ArrayList<>();

    // 音频队列
    private static List<Map<String, Object>> voiceQueue = new ArrayList<>();

    // 地点队列
    private static List<Map<String, Object>> locationQueue = new ArrayList<>();

    public static void putToTextQueue(String toUserId, Map<String, Object> map)
    {
        Map<String, Object> msgMap = new HashMap<String, Object>();
        msgMap.put(toUserId, map);
        textQueue.add(msgMap);
    }
}
