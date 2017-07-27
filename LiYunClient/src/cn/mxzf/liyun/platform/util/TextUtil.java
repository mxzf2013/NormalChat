package cn.mxzf.liyun.platform.util;

public class TextUtil
{
    /**
     * 判断是否空串
     * 
     * @param strings
     * @return
     */
    public static boolean noEmpty(String... strings)
    {
        boolean result = false;
        for (int index = 0; index < strings.length; index++ )
        {
            if (strings[index] != null && !strings[index].trim().equals(""))
                result = true;
            else
            {
                result = false;
            }
        }
        return result;
    }
}
