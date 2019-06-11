package top.charjin.shoppingclient;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import top.charjin.shoppingclient.entity.OsUser;

public class ShoppingApplication extends Application {

    public static final Map<String, Object> map = new HashMap<>();
    private static OsUser user = null;

    @SuppressLint("StaticFieldLeak")
    @NotNull
    private static ShoppingApplication sInstance;
    private Context context;

    public ShoppingApplication() {
        sInstance = this;
    }

    @NotNull
    public static ShoppingApplication getInstance() {
        return sInstance;
    }

    public static OsUser getUser() {
        return user;
    }

    public static void setUser(OsUser user) {
        ShoppingApplication.user = user;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }


    public Context getContext() {
        return this.context;
    }

}
