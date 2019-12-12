package com.boosal.smartlibrary.utils;

import android.content.Context;
import android.os.storage.StorageManager;

import java.lang.reflect.Method;

import static android.content.Context.STORAGE_SERVICE;

public class PathUtil {

    public static final String USB = "USB";

    /**
     * 6.0获取外置sdcard和U盘路径，并区分
     *
     * @param mContext
     * @param keyword
     *            EXT = "内部存储"; SD = "SD卡"; USB = "U盘"
     * @return
     */
    public static String getStoragePath(Context mContext, String keyword) {
        String resultpath = "";
        String[] paths = getAllVolumePaths(mContext);
        String sd_path = getSystemPropString("vold.path.external_sd","sd");
        if ("EXT".equals(keyword)&&paths.length>0)
            resultpath = paths[0];
        if ("SD".equals(keyword)&&!"sd".equals(sd_path))
            resultpath = sd_path;

        if ("USB".equals(keyword)){
            //0默认为内部存储
            for (int i = 1; i <paths.length ; i++) {
                if (sd_path.equals("sd"))
                    resultpath = paths[i];
                else if(!sd_path.equals("sd")&&!paths[i].equals(sd_path)){
                    resultpath = paths[i];
                }
            }
        }
        return resultpath;
    }

    /**
     * @return 返回所有存储路径
     */
    private static  String[] getAllVolumePaths(Context mContext) {
        try {
            //存储管理类
            StorageManager sm = (StorageManager)mContext.getSystemService(STORAGE_SERVICE);
            //获取存储路径的方法
            Method getVolumePathsMethod = StorageManager.class.getMethod("getVolumePaths", null);
            //获取路径数组
            String[] paths = (String[]) getVolumePathsMethod.invoke(sm, null);
            return paths;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Method getStringPropMethod = null;

    private static String getSystemPropString(final String key,
                                             final String defaultValue) {
        try {
            if (getStringPropMethod == null) {
                getStringPropMethod = Class.forName("android.os.SystemProperties").getMethod("get",
                        String.class, String.class);
            }
            return (String) getStringPropMethod.invoke(null, key, defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
