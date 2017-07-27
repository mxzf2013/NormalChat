package cn.mxzf.liyun.platform.util;

import cn.mxzf.liyun.platform.domain.User;

/**
 * 
 * @Title: TokenUtil
 * @Dscription: Token工具
 * @author Deleter
 * @date 2017年6月26日 上午10:20:18
 * @version 1.0
 */
public class TokenUtil
{
    public static String make(long id)
    {
        return DesUtil.encrypt("mxzf&" + id + "&" + System.currentTimeMillis());
    }
    
    public static String make(User user)
    {
        return DesUtil.encrypt("mxzf&" + user.getUserId() + "&" + System.currentTimeMillis());
    }
    
    /**
     * 验证token有效性
     * 
     * @return
     */
    public static boolean isOk(String token, User user)
    {
        String[] str = DesUtil.decrypt(token).split("&");
        // 长度
        if (str.length != 3)
        {
            System.err.println("长度 error");
            return false;
        }
        // 头标
        if (!str[0].equals("mxzf"))
        {
            System.err.println("头标 error");
            return false;
        }
        
        // 用户名ID是否存在
        long userId = Long.valueOf(str[1]);
        if (userId != user.getUserId())
        {
            System.err.println("用户ID error");
            return false;
        }
        
        // 时间戳相差3分钟内
        long lastTimeMillis = Long.valueOf(str[2]);
        long currentTimeMillis = System.currentTimeMillis();
        long minute = (currentTimeMillis - lastTimeMillis) / 1000 / 60;
        if (minute > 3)
        {
            System.out.println("时间间隔 error");
            return false;
        }
        return true;
    }
    
    public static void main(String[] args)
    {
        User user = new User("1943815081", "zz,520.");
        String token = TokenUtil.make(user);
        System.out.println(TokenUtil.isOk(token, user));
    }
}
