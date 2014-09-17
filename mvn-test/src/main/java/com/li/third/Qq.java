package com.li.third;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.li.http.HttpRequestUtil;


public class Qq {
	private static final String AUTHORIZE_URL = "https://graph.qq.com/oauth2.0/authorize";
	private static final String GET_TOKEN_URL = "https://graph.qq.com/oauth2.0/token";
	private static final String GET_OPENID_URL = "https://graph.qq.com/oauth2.0/me";
	private static final String GET_USER_INFO_URL = "https://graph.qq.com/user/get_user_info";
	
	private String accessToken = "9FFB1DA8BFC35180AFDE714096C2A44D";

	public Qq() {
	}
	
	public Qq(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * step1:拼装访问地址,获取Authorization Code
	 * @param callBackUrl：qq回调地址
	 * @param scope：请求权限，在配置文件中配置
	 * @return
	 */
	public String getLoginUrl(String callBackUrl, String scope) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("client_id", ThirdConfig.QQ_CLIENT_ID);
		param.put("redirect_uri", callBackUrl);
		param.put("response_type", "code");
		param.put("scope", scope);
		return AUTHORIZE_URL + "?" + HttpRequestUtil.encodeParameters(param);
	}

	/**
	 * step2:使用Authorization_Code获取Access_Token
	 * 请求qq后的返回说明：access_token=FE04***CCE2&expires_in=7776000&refresh_token=88E4***BE14
	 * 参考api：http://wiki.connect.qq.com/%E4%BD%BF%E7%94%A8authorization_code%E8%8E%B7%E5%8F%96access_token
	 * @param callBackUrl：qq回调地址
	 * @param code：用户同意之后，qq回调时传过来的参数。此code会在10分钟内过期
	 * @return
	 */
	public String getAccessToken(String callBackUrl, String code) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("grant_type", "authorization_code");
		param.put("client_id", ThirdConfig.QQ_CLIENT_ID);
		param.put("client_secret", ThirdConfig.QQ_CLIENT_SECRET);
		param.put("code", code);
		param.put("state", "");
		param.put("redirect_uri", callBackUrl);
		try {
			// 解析请求结果
			Map<String, String> json = HttpRequestUtil.parseStr(HttpRequestUtil.get(GET_TOKEN_URL, param));
			this.accessToken = json.get("access_token");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return this.accessToken;
	}

	/**
	 * step3:获取用户OpenID_OAuth2.0
	 * 请求返回说明：callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} )
	 * 参考：http://wiki.connect.qq.com/%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7openid_oauth2-0
	 * @return
	 */
	public String getOpenid() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("access_token", this.accessToken);
		try {
			// 解析请求结果
			JSONObject json = HttpRequestUtil.parseJsonStr(HttpRequestUtil.get(GET_OPENID_URL, param));
			return json.getString("openid");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * get_user_info接口
	 * @param openid
	 * @return
	 */
	public JSONObject getUserInfo(String openid) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("openid", openid);
		String s = this.api(GET_USER_INFO_URL, param, "get");
		try {
			// 解析请求结果
			return HttpRequestUtil.parseJsonStr(s);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null; 
	}

	/**
	 * OpenAPI调用
	 * 参考：http://wiki.connect.qq.com/openapi%E8%B0%83%E7%94%A8%E8%AF%B4%E6%98%8E_oauth2-0#
	 * @param url
	 * @param param
	 * @param method
	 * @return
	 */
	public String api(String url, Map<String, Object> param, String method) {
		param.put("access_token", this.accessToken);
		param.put("oauth_consumer_key", ThirdConfig.QQ_CLIENT_ID);
		param.put("format", "json");

		String result = null;
		try {
			if ("get".equalsIgnoreCase(method)) {
				result = HttpRequestUtil.get(url, param);
			}
			else {
				result = HttpRequestUtil.post(url, param);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
