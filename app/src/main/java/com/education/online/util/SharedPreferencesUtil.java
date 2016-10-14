package com.education.online.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


/**
 * 对SharedPreferences进行操作的工具类
 * 
 * @author heyongbo@infohold.com.cn
 */

public final class SharedPreferencesUtil {

	/** SharedPreferences对应xml文件的文件名 ,次配置文件只保持银行数据 */
	private static final String SHARED_PREFERENCES_NAME = "data";// 这个数据退出时会清掉数据。
	private static final String SHARED_PREFERENCES_XML_PARSE_PACKAGE = "com.education.online";

	/** 当<配置文件>中不存在你要取的值时，返回此int状态值 */
	public static final int FAILURE_INT = Integer.MIN_VALUE;

	/** 当<配置文件>中不存在你要取的值时，返回此String状态值 */
	public static final String FAILURE_STRING = "___no_data___";
	private static final String ShareKey="0-0-0-0";

	public static void setString(Context context, String key, String value) {
		String convertValue = convert(value,true);
		LogUtil.i("interface", "interface getString convertValue--->" + convertValue + "key-->" + key);
		SharedPreferences sp = getSharedPreferencesContext(context).getSharedPreferences(getShareName(context),
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(key, convertValue);
		editor.commit();
	}

	public static String getString(Context context, String key) {
		SharedPreferences sp = getSharedPreferencesContext(context).getSharedPreferences(getShareName(context),
				Context.MODE_PRIVATE);
		String value=sp.getString(key, FAILURE_STRING);
		
		if(value.startsWith(ShareKey) && value.endsWith(ShareKey)){
			String replaceValue = value.replaceAll(ShareKey, "");
			String convertValue = convert(replaceValue,false);
			LogUtil.i("interface", "interface getString convertValue--->" + convertValue + "key-->" + key);
			return convertValue;			
		}else{
			setString(context, key, value);
		}
		return value;
	}
	
	public static void setInt(Context context, String key, int value) {
		SharedPreferences sp = getSharedPreferencesContext(context).getSharedPreferences(getShareName(context),
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	public static int getInt(Context context, String key , int def) {
		SharedPreferences sp = getSharedPreferencesContext(context).getSharedPreferences(getShareName(context),
				Context.MODE_PRIVATE);
		return sp.getInt(key, def);
	}
	
	public static String convert(String inStr, boolean vert) {
		String flag = "";
		if (vert) {// 加密
			flag = ShareKey; // 如果是0000开头和结尾的需要解密
		}
		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't' ^ 'a');
		}
		String s = new String(a);
		return flag + s + flag;
	}

	// 删除指定key
	public static void delString(Context context, String key) {
		SharedPreferences sp = getSharedPreferencesContext(context).getSharedPreferences(getShareName(context),
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.remove(key);
		editor.commit();
	}

	/**
	 * 获取配置文件中的boolean 值。
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static boolean getValue(Context context, String key , boolean bef) {
		SharedPreferences sp = getSharedPreferencesContext(context).getSharedPreferences(getShareName(context),
				Context.MODE_PRIVATE);
		return sp.getBoolean(key, bef);
	}

	/**
	 * 加入数据到<应用包动态配置文件>中。
	 * 
	 * @param context 应用上下文
	 * @param key 键
	 * @param value 值
	 */
	public static void setValue(Context context, String key, boolean value) {
		SharedPreferences sp = getSharedPreferencesContext(context).getSharedPreferences(getShareName(context),
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	/**
	 * 加入数据到<应用包动态配置文件>中。
	 * 
	 * @param context 应用上下文
	 * @param key 键
	 * @param value 值
	 */
	public static void addData(Context context, String key, String value) {
		String convertValue = convert(value,true);
		LogUtil.i("DemoTest", "addData convertValue--->" + convertValue + "key-->" + key);
		SharedPreferences sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME,
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(key, convertValue);
		editor.commit();
	}

	/**
	 * 从<应用包动态配置文件>中取得数据。
	 * 
	 * @param context 应用上下文
	 * @param key 键
	 * @return 值
	 */
	public static String getData(Context context, String key) {
		/*SharedPreferences sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME,
				Context.MODE_PRIVATE);
		return sp.getString(key, FAILURE_STRING);*/
		SharedPreferences sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME,
				Context.MODE_PRIVATE);
		String value = sp.getString(key, FAILURE_STRING);
		if(value.startsWith(ShareKey) && value.endsWith(ShareKey)){
			String replaceValue = value.replaceAll(ShareKey, "");
			String convertValue = convert(replaceValue,false);
			LogUtil.i("DemoTest", "getString convertValue--->" + convertValue + "key-->" + key);
			return convertValue;			
		}else{
			addData(context, key, value);
		}
		return value ;
	}

	/**
	 * 清空<应用包动态配置文件>
	 * 
	 * @param context 应用上下文
	 */
	public static void clearData(Context context) {
		SharedPreferences sp = getSharedPreferencesContext(context).getSharedPreferences(SHARED_PREFERENCES_NAME,
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.clear();
		editor.commit();
	}

	/**
	 * 在登录成功后，将用户ID（userid）加入到<应用包动态配置文件>中。
	 * 
	 * @param context 应用上下文
	 * @param userid 用户ID
	 */
	public static void setUserid(Context context, String userid) {
		setString(context, "userid", "" + userid);
	}

	/**
	 * 从<应用包动态配置文件>中取得用户ID。如果用户没有登陆，则返回值为{@link #FAILURE_INT}
	 * 
	 * @param context 应用上下文
	 * @return 用户ID。如果用户没有登陆，则返回值为{@link #FAILURE_INT}
	 */
	public static String getUserid(Context context) {
		final String useridStr = getString(context, "userid");
		return useridStr;
	}

	/**
	 * 增加关联账户
	 * 
	 * @param context 应用上下文
	 * @param card 登录用户持有的卡片
	 */
	public static void addCard(Context context, String card, String img) {
		List<String> cardList = getCardList(context);
		List<String> imgList = getImageList(context);// 图片集合
		cardList.add(card);
		imgList.add(img);
		setCardList(context, cardList);
		setImgList(context, imgList);
	}

	/**
	 * 解除关联账户
	 * 
	 * @param context 应用上下文
	 * @param card 登录用户持有的卡片
	 */
	public static void delCard(Context context, String card) {
		List<String> cardList = getCardList(context);
		List<String> imgList = getImageList(context);// 图片集合
		int len = cardList.size();
		String s;
		for (int i = 0; i < len; i++) {
			s = cardList.get(i);
			if (s.equals(card)) {
				cardList.remove(i);
				imgList.remove(i);
				setCardList(context, cardList);
				setImgList(context, imgList);
				return;
			}
		}
	}

	/**
	 * 设置默认账户
	 * 
	 * @param context 应用上下文
	 * @param defaultCard 默认账户
	 */
	public static void setDefaultCard(Context context, String defaultCard) {
		List<String> cardList = getCardList(context);
		List<String> imgList = getImageList(context);// 图片集合
		int len = cardList.size();
		String s;
		for (int i = 0; i < len; i++) {
			s = cardList.get(i);
			if (s.equals(defaultCard)) {
				String img = imgList.get(i);
				cardList.remove(i);
				imgList.remove(i);
				cardList.add(0, defaultCard);
				imgList.add(0, img);
				setImgList(context, imgList);
				setCardList(context, cardList);
				return;
			}
		}
	}

	/**
	 * 在登录成功后，将用户持有的卡片加入到<应用包动态配置文件>中。
	 * 
	 * @param context 应用上下文
	 * @param cardList 登录用户持有的卡片列表；此列表的第一项为默认卡片。
	 */
	public static void setCardList(Context context, List<String> cardList) {
		if (null != cardList) {
			final int len = cardList.size();
			if (len > 0) {
				final StringBuilder buf = new StringBuilder();
				for (int i = 0; i < len; i++) {
					buf.append(cardList.get(i)).append(',');
				}
				buf.deleteCharAt(buf.length() - 1);
				addData(context, "card_number_list", buf.toString());

				if (cardList.get(0).contains("!")) {// 登陆时才回执行（包含“！” ）
					addData(context, "card_img_list", buf.toString());
				}
			}
		}
	}

	/**
	 * 处理增加，删除，设置默认卡时，处理图片
	 * 
	 * @param context 应用上下文
	 * @param cardList 登录用户持有的卡片列表；此列表的第一项为默认卡片。
	 */
	public static void setImgList(Context context, List<String> cardList) {
		if (null != cardList) {
			final int len = cardList.size();
			if (len > 0) {
				final StringBuilder buf = new StringBuilder();
				for (int i = 0; i < len; i++) {
					buf.append(cardList.get(i)).append(',');
				}
				buf.deleteCharAt(buf.length() - 1);
				addData(context, "card_img_list", buf.toString());
			}
		}
	}

	/**
	 * 从<应用包动态配置文件>中取得用户持有的卡片列表。如果用户没有登陆，则返回集合的元素个数为零，而非{@code null}。
	 * 
	 * @param context 应用上下文
	 * @return 登录用户持有的卡片列表，此列表的第一项为默认卡片。如果用户没有登陆，则返回集合的元素个数为零，而非{@code null}。
	 */
	public static List<String> getCardList(Context context) {
		final ArrayList<String> cardList = new ArrayList<String>();
		final String cardListStr = getData(context, "card_number_list");
		if (null != cardListStr && !"".equals(cardListStr.trim())) {
			String[] cardArr = cardListStr.split(",");
			final int len = cardArr.length;
			if (null != cardArr && cardArr[0].contains("!")) {
				for (int i = 0; i < len; i++) {
					String[] cardAccount = cardArr[i].split("!");
					if (null != cardAccount) {
						cardList.add(cardAccount[1]);// 保存卡号
					}
				}
			} else {
				for (int i = 0; i < len; i++) {
					cardList.add(cardArr[i]);// 保存卡号
				}
			}
		}
		LogUtil.i("DemoTest", "getCardList---->" + cardList);
		return cardList;
	}

	// 获取图片
	public static List<String> getImageList(Context context) {
		final ArrayList<String> imgList = new ArrayList<String>();
		final String cardListStr = getData(context, "card_img_list");
		if (null != cardListStr && !"".equals(cardListStr.trim())) {
			String[] cardArr = cardListStr.split(",");
			final int len = cardArr.length;
			if (null != cardArr && cardArr[0].contains("!")) {
				for (int i = 0; i < len; i++) {
					String[] cardAccount = cardArr[i].split("!");
					if (null != cardAccount) {
						imgList.add(cardAccount[2]);
					}
				}
			} else {
				for (int i = 0; i < len; i++) {
					imgList.add(cardArr[i]);
				}
			}
		}
		LogUtil.i("DemoTest", "getImageList---->" + imgList);
		return imgList;
	}

	/**
	 * 将<国际移动用户识别码IMSI>加入到<应用包动态配置文件>中。
	 * 
	 * @param context 应用上下文
	 * @param imsi 国际移动用户识别码IMSI
	 */
	public static void setIMSI(Context context, String imsi) {
		setString(context, "IMSI", imsi);
	}

	/**
	 * 从<应用包动态配置文件>中取得<国际移动用户识别码IMSI>。
	 * 
	 * @param context 应用上下文
	 * @return 国际移动用户识别码IMSI
	 */
	public static String getIMSI(Context context) {
		return getString(context, "IMSI");
	}

	/**
	 * 将<国际移动设备身份码IMEI>加入到<应用包动态配置文件>中。
	 * 
	 * @param context 应用上下文
	 * @param imei 国际移动设备身份码IMEI
	 */
	public static void setIMEI(Context context, String imei) {
		setString(context, "IMEI", imei);
	}

	/**
	 * 从<应用包动态配置文件>中取得<国际移动设备身份码IMEI>。
	 * 
	 * @param context 应用上下文
	 * @return 国际移动设备身份码IMEI
	 */
	public static String getIMEI(Context context) {
		return getString(context, "IMEI");
	}

	/**
	 * 在登录成功后，将用户会话Id（sessionid）加入到<应用包动态配置文件>中。
	 * 
	 * @param context 应用上下文
	 * @param userid 用户ID
	 */
	public static void setUsercode(Context context, String usercode) {
		setString(context, "usercode", "" + usercode);
	}

	public static String getUsercode(Context context) {
		final String usercodeStr = getString(context, "usercode");
		return usercodeStr;
	}

	/**
	 * 在登录成功后，将用户会话Id（sessionid）加入到<应用包动态配置文件>中。
	 * 
	 * @param context 应用上下文
	 * @param userid 用户ID
	 */
	public static void setSessionid(Context context, String sessionid) {
		addData(context, "sessionid", "" + sessionid);
	}

	public static String getSessionid(Context context) {
		final String sessionidStr = getData(context, "sessionid");
		return sessionidStr;
	}

	private static final String TAG = "SharedPreferencesUtil";

	public static boolean getBooleanSharedPreference(Context context, String key, boolean defValue) {
		return getSharedPreferences(context).getBoolean(key, defValue);
	}

	/**
	 * 获取Long类型的值
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static long getLongSharedPreference(Context context, String key, long defValue) {
		return getSharedPreferences(context).getLong(key, defValue);
	}

	public static String getStringSharedPreference(Context context, String key, String defValue) {
		return getSharedPreferences(context).getString(key, defValue);
	}

	public static int getIntSharedPreference(Context context, String key, int defValue) {
		return getSharedPreferences(context).getInt(key, defValue);
	}

	public synchronized static void saveBooleanSharedPreference(Context context, String key,
																boolean value) {
		LogUtil.i(TAG, "save Boolean data. key(" + key + "); value(" + value + ")");
		Editor editor = getSharedPreferences(context).edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public synchronized static void saveStringSharedPreference(Context context, String key,
															   String value) {
		LogUtil.i(TAG, "save String data. key(" + key + "); value(" + value + ")");
		Editor editor = getSharedPreferences(context).edit();
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * 保存long类型的数值
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public synchronized static void saveLongSharedPreference(Context context, String key, long value) {
		LogUtil.i(TAG, "save long data. key(" + key + "); value(" + value + ")");
		Editor editor = getSharedPreferences(context).edit();
		editor.putLong(key, value);
		editor.commit();
	}

	public synchronized static void saveIntSharedPreference(Context context, String key, int value) {
		LogUtil.i(TAG, "save String data. key(" + key + "); value(" + value + ")");
		Editor editor = getSharedPreferences(context).edit();
		editor.putInt(key, value);
		editor.commit();
	}

	private static SharedPreferences getSharedPreferences(Context context) {
		return getSharedPreferencesContext(context).getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
	}
	
	//---------------------------------------------------------------------------------------
	
	public static String getCommonValue(Context context, String key) {
		SharedPreferences sp = getSharedPreferencesContext(context).getSharedPreferences(getShareName(context),
				Context.MODE_PRIVATE);
		return sp.getString(key, "");
	}

	public static void setCommonValue(Context context, String key, String value) {
		SharedPreferences sp = getSharedPreferencesContext(context).getSharedPreferences(getShareName(context),
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	
	public static String getShareName(Context context){
		String name = "interface" ;
		return name ;
		
	}
	
	public static Context getSharedPreferencesContext(Context context){
		Context mOtherContext = null;
		try {
			mOtherContext = context.createPackageContext(
					SHARED_PREFERENCES_XML_PARSE_PACKAGE,
					Context.CONTEXT_INCLUDE_CODE);
		} catch (NameNotFoundException e) {
			LogUtil.e(TAG, "没有找到名字", e);
		}
		if(mOtherContext == null){
			return context;
		}
		return mOtherContext;
	}
	
}
