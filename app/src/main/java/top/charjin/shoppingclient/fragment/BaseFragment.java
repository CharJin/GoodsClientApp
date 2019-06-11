package top.charjin.shoppingclient.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.charjin.shoppingclient.activity.AppActivity;
import top.charjin.shoppingclient.activity.LoginActivity;
import top.charjin.shoppingclient.activity.MyApplication;
import top.charjin.shoppingclient.entity.OsUser;
import top.charjin.shoppingclient.utils.HttpUtil;
import top.charjin.shoppingclient.utils.Router;

public abstract class BaseFragment extends Fragment {
    protected Context context;
    protected AppActivity activity;
    protected OsUser user;


    /**
     * 获取context和activity
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();

        activity = (AppActivity) getActivity();
        user = activity.getUser();
    }

    /**
     * 判断用户是否修改了密码
     */
    protected void checkUser() {
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        String username = sp.getString("username", null), password = sp.getString("password", null);
        System.out.println(Router.BASE_URL + "user/login?username=" + username + "&password=" + password);
        if (username != null) { // 用户名不为空，检测密码是否修改
            HttpUtil.sendOkHttpRequestByGet(Router.BASE_URL + "user/login?username=" + username + "&password=" + password, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        Thread.sleep(3000);
                        // 延迟3秒再开启用户信息的检测
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String jsonData = response.body().string();
                    activity.runOnUiThread(() -> {

                        if (jsonData.equals("")) { // 查询结果为空，则密码错误
                            // 进入登录页面.登录页面未选择登录 则user未null，如果登录则重新实例化此对象，并更新SharedPreferences
                            Toast.makeText(context, "密码已修改,请重新登录！", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(activity, LoginActivity.class));
                        } else {
                            OsUser user = new Gson().fromJson(jsonData, OsUser.class);
                            MyApplication.map.put("user", user);
                        }
                    });
                }
            });
        }


    }


}

