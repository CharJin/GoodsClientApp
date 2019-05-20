package top.charjin.shoppingclient.activity;

import android.app.Application;
import android.content.Context;

import java.util.HashMap;
import java.util.Map;

public class MyApplication extends Application {

    private Context context;

    public static final Map<String, Object> map = new HashMap<>();


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }


    public Context getContext() {
        return this.context;
    }

}
