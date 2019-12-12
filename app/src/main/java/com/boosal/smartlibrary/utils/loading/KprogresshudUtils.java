package com.boosal.smartlibrary.utils.loading;

import android.content.Context;

import com.kaopiz.kprogresshud.KProgressHUD;

public class KprogresshudUtils {
    private static KProgressHUD kProgressHUD;

    public static void show(Context context) {
        if (kProgressHUD != null) {
            kProgressHUD.dismiss();
            kProgressHUD = null;
        }
        kProgressHUD = new KprogresshudOptition().getKProgressHUD(context);
        kProgressHUD.show();
    }

    public static void dismiss() {
        if (kProgressHUD != null) {
            kProgressHUD.dismiss();
            kProgressHUD=null;
        }
    }
}
