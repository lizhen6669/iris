package com.li.third;

/**
 * 配置项常量类，在dispatcherServlet初始化时读取config.properties配置文件来修改各个配置项
 * @author lizhen
 *
 */
public class ThirdConfig {
	
	public static String QQ_CLIENT_ID = "100368858";
	public static String QQ_CLIENT_SECRET = "a951528f5351894dc9b282856b958d23";
	public static String QQ_REDIRECT_URI = "http://my.weke.com/callback";
	public static String QQ_SCOPE = "get_user_info,add_share";

	public static String SINA_CLIENT_ID = "94994837";
	public static String SINA_CLIENT_SECRET = "869ba6dfa31bb289f68c21c97f071eaf";
	public static String SINA_REDIRECT_URI = "http://my.weke.com/callback";
}
