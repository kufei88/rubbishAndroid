package com.boosal.smartlibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.boosal.smartlibrary.base.baseApp.BaseApplication;

import java.util.Random;
import java.util.Set;
import java.util.UUID;

/**
 * 存储xml键值对
 *
 * @author liuyuanqi
 */
public class Preferences {

	private static final String SHAREDNAME = "DESK";
	public static final String UUID = "uuid";
	public static final String HAS_UUID = "has_uuid";

	/**
	 * 所有Key值
	 **/
	public static final class PreKey {

		//Token
		public static final String TOKEN = "token";

	}

	//***********************************************************************************************

	/**
	 * 获取用户Token
	 *
	 * @return
	 */
	public static String getToken() {
		String token = getString(PreKey.TOKEN);
		return token;
	}

	//***********************************************************************************************

	/**
	 * put一个string键值对
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean putString(String key, String value) {

		BaseApplication app = BaseApplication.mApplication;
		SharedPreferences prefence = app.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);

		Editor eidtor = prefence.edit();
		eidtor.putString(key, value);
		return eidtor.commit();
	}

	/**
	 * put一个set键值对
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean putStringSet(String key, Set<String> value) {

		BaseApplication app = BaseApplication.mApplication;
		SharedPreferences prefence = app.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);

		Editor eidtor = prefence.edit();
		eidtor.putStringSet(key, value);
		return eidtor.commit();
	}

	/**
	 * put一个string键值对
	 *
	 * @param key
	 * @return
	 */
	public static boolean removeKey(String key) {

		BaseApplication app = BaseApplication.mApplication;
		SharedPreferences prefence = app.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);

		Editor eidtor = prefence.edit();
		eidtor.remove(key);
		return eidtor.commit();
	}

	/**
	 * put一个boolean键值对
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean putBoolean(String key, boolean value) {

		BaseApplication app = BaseApplication.mApplication;
		SharedPreferences prefence = app.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);

		Editor eidtor = prefence.edit();
		eidtor.putBoolean(key, value);
		return eidtor.commit();
	}

	/**
	 * 获取string 值
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
		BaseApplication app = BaseApplication.mApplication;
		SharedPreferences prefence = app.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);
		return prefence.getString(key, "");
	}

	/**
	 * 获取string Set
	 * @param key
	 * @return
	 */
	public static Set<String> getStringSet(String key) {
		BaseApplication app = BaseApplication.mApplication;
		SharedPreferences prefence = app.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);
		return prefence.getStringSet(key, null);
	}

	/**
	 * 获取string 值
	 * @param key
	 * @return
	 */
	public static String getString(String key, String defaultValue) {
		BaseApplication app = BaseApplication.mApplication;
		SharedPreferences prefence = app.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);
		return prefence.getString(key, defaultValue);
	}

	/**
	 * 获取int 值
	 * @param key
	 * @return
	 */
	public static int getInt(String key, int defaultValue) {
		BaseApplication app = BaseApplication.mApplication;
		SharedPreferences prefence = app.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);
		return prefence.getInt(key, defaultValue);
	}
	/**
	 * 获取long 值
	 * @param key
	 * @return
	 */
	public static long getLong(String key, long defaultValue) {
		BaseApplication app = BaseApplication.mApplication;
		SharedPreferences prefence = app.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);
		return prefence.getLong(key, defaultValue);
	}

	/**
	 * put一个Int键值对
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean putInt(String key, int value) {
		BaseApplication app = BaseApplication.mApplication;
		SharedPreferences prefence = app.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);

		Editor eidtor = prefence.edit();
		eidtor.putInt(key, value);
		return eidtor.commit();
	}

    /**
     *  put一个Long键值对
     */
    public static boolean putLong(String key, long value) {
		BaseApplication app = BaseApplication.mApplication;
		SharedPreferences prefence = app.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);
		Editor eidtor = prefence.edit();
		eidtor.putLong(key, value);
		return eidtor.commit();
	}

	/**
	 * 获取boolean值
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(String key) {
		BaseApplication app = BaseApplication.mApplication;
		SharedPreferences prefence = app.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);
		return prefence.getBoolean(key, false);
	}
	/**
	 * 获取boolean 值
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(String key, boolean defaultValue) {
		BaseApplication app = BaseApplication.mApplication;
		SharedPreferences prefence = app.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);
		return prefence.getBoolean(key, defaultValue);
	}

	/**
	 * 保存UUID至本地
	 */
	private static void saveUUID() {
		BaseApplication app = BaseApplication.mApplication;
		SharedPreferences prefence = app.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);

		String uuid = getUUID(app);
		Editor editor = prefence.edit();
		editor.putString(UUID, uuid);
		editor.putBoolean(HAS_UUID, true);
		editor.commit();
	}

	/**
	 * 获取UUID
	 *
	 * @return
	 */
	public static String getUUID() {
		BaseApplication app = BaseApplication.mApplication;
		SharedPreferences prefence = app.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);

		if (hasUUID()) {
			return prefence.getString(UUID, "");
		} else {
			saveUUID();
			return prefence.getString(UUID, "");
		}
	}

	private static String getUUID(Context context)
	{
		String UUID = null;
        TelephonyManager telephoneManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = telephoneManager.getDeviceId();
        String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        if ((androidId!=null && androidId.length()>0) || (deviceId!=null && deviceId.length()>0))
        {
        	if(null == androidId){
        		androidId = new Random().nextInt(10000000)+999999 +"";
        	}
        	if(null == deviceId){
        		deviceId = new Random().nextInt(10000000)+999999 +"";
        	}
        	
        	Log.i("Prefrence", "android_id:"+androidId);
        	Log.i("Prefrence", "device_id:"+deviceId);
        	
            java.util.UUID uuid = new UUID(androidId.hashCode(), (long)deviceId.hashCode() << 32 );
            UUID = uuid.toString();
            
            Log.i("Prefrence", "uuid:" + UUID);
        }
        return UUID;
	}
	
	/**
	 * UUID 是否已经生成
	 * 
	 * @return
	 */
	public static boolean hasUUID() {
		BaseApplication app = BaseApplication.mApplication;
		SharedPreferences prefence = app.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);
		return prefence.getBoolean(HAS_UUID, false);
	}

	/***
	 * 清除本地缓存数据
	 */
	public static void cleanUserinfo(){
		BaseApplication app = BaseApplication.mApplication;
		SharedPreferences prefence = app.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);
		Editor eidtor = prefence.edit();
		eidtor.clear();
		eidtor.commit();
	}

}
