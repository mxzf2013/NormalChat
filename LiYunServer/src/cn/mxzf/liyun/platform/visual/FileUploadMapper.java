package cn.mxzf.liyun.platform.visual;


import java.util.Hashtable;
import java.util.Map;


public class FileUploadMapper
{
    // 路径-文件总长
    public static Map<String, Long> cache = new Hashtable<String, Long>();

    // 路径-当前长度
    public static Map<String, Integer> current = new Hashtable<String, Integer>();
}
