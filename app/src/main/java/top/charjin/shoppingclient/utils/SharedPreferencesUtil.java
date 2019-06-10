package top.charjin.shoppingclient.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {
    public static boolean isFirstLaunched(Context context) {
        SharedPreferences sp = context.getSharedPreferences("goodsList", Context.MODE_PRIVATE);
        boolean isFirst = sp.getBoolean("isFirst", true);
        if (isFirst) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("isFirst", false);
            editor.apply();
        }
        return isFirst;
    }

}
