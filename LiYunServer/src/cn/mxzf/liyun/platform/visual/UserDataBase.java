package cn.mxzf.liyun.platform.visual;

import java.util.HashSet;
import java.util.Set;

import cn.mxzf.liyun.platform.domain.User;


public class UserDataBase
{
    // 用户列表
    public static Set<User> userList = new HashSet<>();

    static
    {
        userList.add(new User("1943815081", "123456"));
        userList.add(new User("1943815082", "123456"));
    }
}
