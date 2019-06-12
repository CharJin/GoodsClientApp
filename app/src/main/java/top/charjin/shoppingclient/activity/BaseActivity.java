package top.charjin.shoppingclient.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.charjin.shoppingclient.ShoppingApplication;
import top.charjin.shoppingclient.entity.OsUser;
import top.charjin.shoppingclient.user.UserManager;
import top.charjin.shoppingclient.utils.HttpUtil;
import top.charjin.shoppingclient.utils.Router;

public abstract class BaseActivity extends AppCompatActivity implements UserManager {
    protected OsUser user = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 不允许横屏
        user = ShoppingApplication.getUser();
//        checkUser();
    }


    /**
     * 每个活动对用户登录信息进行验证
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (user != ShoppingApplication.getUser())
            user = ShoppingApplication.getUser();   // 从其他页面返回后 重新在application中获取user对象
    }

    /**
     * 判断用户是否修改了密码
     */
    protected void checkUser() {
        SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        String username = sp.getString("username", null), password = sp.getString("password", null);
        System.out.println(Router.BASE_URL + "user/login?username=" + username + "&password=" + password);
        /*
         * 用户名不为空，说明存在登录记录, 检测密码是否修改 (如果密码正确 则获取用户信息至user实体类)
         * 如果用户名不存在, 说明无登录记录或者已退出登录, 不做任何操作
         */
        if (username != null) {
            HttpUtil.sendOkHttpRequestByGet(Router.BASE_URL + "user/login?username=" + username + "&password=" + password, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
//                    try {
//                        Thread.sleep(1000);
//                        // 延迟1秒再开启用户信息的检测
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    assert response.body() != null;
                    String jsonData = response.body().string();
                    runOnUiThread(() -> {

                        if (jsonData.equals("")) { // 查询结果为空，则密码错误
                            // 进入登录页面.登录页面未选择登录 则user未null，如果登录则重新实例化此对象，并更新SharedPreferences
                            Toast.makeText(BaseActivity.this, "密码已修改,请重新登录！", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(BaseActivity.this, LoginActivity.class));
                        } else {
                            user = new Gson().fromJson(jsonData, OsUser.class);
                            ShoppingApplication.setUser(user);
                        }
                    });
                }
            });
        }


    }

    @Override
    public boolean userLoginIntercept(@NonNull Context context) {
        if (!existsUser()) {
            toast(context, "请先登录账户哦!");
            startActivity(new Intent(context, LoginActivity.class));
            return false;
        } else {
            return true;
        }
    }

    protected boolean existsUser() {
        return user != null;
    }

    public OsUser getUser() {
        return user;
    }

    public void setUser(OsUser user) {
        this.user = user;
    }

    protected void toast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    protected void saveUserToFileSystem(OsUser newUser) {
        SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear()
                .putString("username", newUser.getUsername())
                .putString("password", newUser.getPassword())
                .apply();
    }
}
