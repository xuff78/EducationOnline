package com.education.online.util;

import android.os.Environment;


/**
 * 全局变量类。
 * 
 * @author heyongbo@infohold.com.cn
 */

public final class GlbsProp {

	/** 应用debug状态 */
	public static final boolean DEBUG = true;

	/*
	 * 以下是各个Log标签。
	 */
	public static final String TAG_GLBS = "GLBS";
	public static final String TAG_GLBS_NET = TAG_GLBS + ".net";
	public static final String TAG_GLBS_JSON = TAG_GLBS + ".json";
	public static final String TAG_GLBS_TASK = TAG_GLBS + ".task";
	public static final String TAG_GLBS_ERROR = TAG_GLBS + ".error";
	public static final String TAG_GLBS_TOAST = TAG_GLBS + ".toast";
	public static final String TAG_GLBS_LOGIN = TAG_GLBS + ".login";
	public static final String TAG_GLBS_REDPACKAGE = TAG_GLBS + ".redpackage";
	public static final String SavePath = Environment.getExternalStorageDirectory().getAbsolutePath()
			+ "/food";

	/*
	 * Intent Key
	 */
	public static final String Intent_Key_UiTitle = "界面顶部显示的标题";
	public static final String Intent_Key_BackActivity = "跑完一个业务操作流程后，点击确认按钮应该回到的界面";
	/**
	 *	签名参数 Key
	 */
	public static final class SIGNATURE{
		public static final String DEVICEID = "deviceid";
		public static final String IMSI = "imsi";
		public static final String IMEI = "imei";
		public static final String APPKEY = "appkey";
		public static final String CHANNEL_ID = "channel_id";
		public static final String METHOD = "method";
		public static final String API_VERSION = "api_version";
		public static final String UA = "ua";
		public static final String NONCE = "nonce";
		public static final String TIMESTAMP = "timestamp";
	}
	
	public static final class TICKETCHECK{
		public static final String REMEMBER_USERNAME = "remember_id";
		public static final String LOGIN_USERNAME = "user_id";
		public static final String LOGIN_PASSWORD = "pwd";
		public static final String QRCODE = "qrcode";
		public static final String DEVICE_ID = "device_id";
		public static final String SUPPLIER_ID = "supplier_id";
		public static final String VALID_MSG = "valid_msg";
		public static final String FROM_DATE = "from_date";
		public static final String TO_DATE = "to_date";
		public static final String OLD_PWD = "old_pwd";
		public static final String NEW_PWD = "new_pwd";
	}
	
	public class REQUEST {
		public static final String url_api="url_api";
		public static final String picSavePath="picSavePath";
		public static final String app_key="app_key";
		public static final String app_channel="app_channel";
		public static final String app_secret="app_secret";
		public static final String deviceid="deviceid";
		public static final String sessionid="sessionid";
		public static final String picUrl="pic_url";
	}
	
	public class ShareKey {
		public static final String ERROR_NUM="keyboard_error";
		public static final String PHONE_NUM="phoneNum";
		public static final String WKENPIN="wkenpin";
		public static final String ISLOGIN="islogin";
		public static final String PASSWORD="password";
		public static final String PASSWORDERRORLOCK="passwordError";
		public static final String USERSTATUS="userstatus";
	}
	
}
