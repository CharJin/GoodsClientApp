package top.charjin.shoppingclient.activity;

import android.app.Application;
import android.content.Context;

import top.charjin.shoppingclient.entity.OsUser;

public class MyApplication extends Application {

//    private static Context app_context, intro_context, login_context;

    private Context context;
    private OsUser user = new OsUser();

    public Context getContext() {
        return this.context;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public OsUser getUser() {
        user.setId(1);
        return user;
    }

    public void setUser(OsUser user) {
        this.user = user;
    }
}
