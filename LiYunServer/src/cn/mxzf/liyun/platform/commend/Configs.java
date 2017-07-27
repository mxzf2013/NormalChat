package cn.mxzf.liyun.platform.commend;

/**
 * @Title: Configs
 * @Dscription: 常量池
 * @author Deleter
 * @date 2017年5月21日 上午2:16:08
 * @version 1.0
 */
public class Configs {
	// user
	public static String USERNAME;

	// 消息类型
	public static enum MsgType {
		CREATE, CREATE_RSP, LOGIN, LOGIN_RSP, LOGOUT, LOGOUT_RSP, TEXT, TEXT_RSP, IMAGE, IMAGE_RSP, VOICE, VOICE_RSP, VIDEO, VIDEO_RSP, FILE, FILE_RSP, LOCATION, LOCATION_RSP, OTHER, OTHER_RSP
	}

	// 消息状态
	public static enum MsgStatus {
		SUCCESS, ERROR
	}

	// 聊天类型
	public static enum ChatType {
		SINGLE, ALL
	}

	// responseDesc
	public static final String DESC_REG_OK = "注册成功";

	public static final String DESC_REG_ERROR = "注册失败";

	public static final String DESC_USERNAME_EXIST = "用户名已存在，请更换用户名重试";

	public static final String DESC_LOGIN_OK = "登录成功";

	public static final String DESC_LOGIN_ERROR = "登录失败";

	public static final String DESC_USERNAME_UNEXIST = "用户名不存在，请检查后重试";

	public static final String DESC_PASSWORD_ERROR = "密码错误，请重新输入";

	public static final String DESC_USER_UNLINE = "对方不在线，待对方上线后系统会自动通知";

	public static final String DESC_ACTION_OK = "操作成功";

	public static final String DESC_ACTION_ERROR = "操作失败";

	public static final String serverIP = "127.0.0.1";
	public static final int localUDPPort = 1234;
	public static final int serverUDPPort = 2345;
}
