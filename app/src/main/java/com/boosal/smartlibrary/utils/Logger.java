package com.boosal.smartlibrary.utils;

import android.util.Log;

/**
 * Created by kqw on 2016/9/1.
 * Logger
 */
public class Logger {

    public static void i(String tag, String msg) {
         Log.i(tag, msg);
    }

    public static void e(String tag, String msg){
        Log.e(tag,msg);
    }
}
