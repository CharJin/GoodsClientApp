package top.charjin.shoppingclient.activity;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

//    private static Context app_context, intro_context, login_context;

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

}
