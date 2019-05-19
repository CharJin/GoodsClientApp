package top.charjin.shoppingclient.activity;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.charjin.shoppingclient.entity.OsUser;
import top.charjin.shoppingclient.utils.HttpUtil;
import top.charjin.shoppingclient.utils.Router;

public class MyApplication extends Application {

    private Context context;
    private OsUser user;

    private Map<String, Object> map = new HashMap<>();


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        checkUser();
    }

    /**
     * 判断用户是否修改了密码
     */
    private void checkUser() {
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        String username = sp.getString("username", null), password = sp.getString("password", null);
        if (username != null) { // 用户名不为空，检测密码是否修改
            HttpUtil.sendOkHttpRequestByGet(Router.BASE_URL + "user/login?username=" + username + "&password=" + password, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String jsonData = response.body().string();
                    if (jsonData.equals("")) { // 查询结果为空，则密码错误
                        // 进入登录页面.登录页面未选择登录 则user未null，如果登录则重新实例化此对象，并更新SharedPreferences
                        user = null;
                    } else {
                        user = new Gson().fromJson(jsonData, OsUser.class);
                    }
                }
            });
//            if (isFirst) {
//                SharedPreferences.Editor editor = sp.edit();
//                editor.putBoolean("isFirst", false);
//                editor.apply();
//            }
        }


    }

    public Context getContext() {
        return this.context;
    }

    public OsUser getUser() {
        return user;
    }

    public void setUser(OsUser user) {
        this.user = user;
    }
}
