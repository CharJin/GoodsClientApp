package top.charjin.shoppingclient.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {
    public static boolean isFirstLaunched(Context context) {
        SharedPreferences sp = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        return sp.getBoolean("isFirst", true);
    }

    public static void setLoginRecord(Context context) {
        SharedPreferences sp = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isFirst", false);
        editor.apply();
    }


}
