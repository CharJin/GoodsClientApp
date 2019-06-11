package top.charjin.shoppingclient.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.charjin.shoppingclient.R;
import top.charjin.shoppingclient.entity.OsUser;
import top.charjin.shoppingclient.utils.HttpUtil;
import top.charjin.shoppingclient.utils.JsonUtil;
import top.charjin.shoppingclient.utils.Router;

public class LoginActivity extends BaseActivity implements TextWatcher {

    private TextInputEditText etUserName, etPwd;
    private TextView tvBtnLogin;

/*
    // 点击第4个选项卡时候 判断一下用户是否登录
        if (MyApplication.map.get("user") == null) {
        startActivity(new Intent(AppActivity.this, LoginActivity.class));
        return;
    }
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_main);
        MyApplication application = (MyApplication) this.getApplication();

        etUserName = findViewById(R.id.et_login_user_name);
        etPwd = findViewById(R.id.et_login_password);
        tvBtnLogin = findViewById(R.id.tv_btn_login);

//        etUserName.setOnKeyListener(this);
//        etPwd.setOnKeyListener(this);
        // 设置监听，更改Login按钮的样式
        etUserName.addTextChangedListener(this);
        etPwd.addTextChangedListener(this);

    }

    public void finishOnClick(View view) {
        this.finish();
    }

    public void login(View view) {
        HttpUtil.sendOkHttpRequestByGet(Router.BASE_URL + "user/login?username="
                + etUserName.getText() + "&password=" + etPwd.getText(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonData = response.body().string();
                if (jsonData.equals("")) {
                    runOnUiThread(() -> Toast.makeText(LoginActivity.this, "用户名或密码错误!", Toast.LENGTH_SHORT).show());
                } else {
                    user = JsonUtil.parseJSONObject(jsonData, OsUser.class);
//                    System.out.println(user);
                    MyApplication.map.put("user", user);
                    SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
//                    if (sp.getString("username", null) == null) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.clear()
                            .putString("username", user.getUsername())
                            .putString("password", user.getPassword())
                            .apply();
//                    }
                    runOnUiThread(() -> {
                        Toast.makeText(LoginActivity.this, "登录成功!", Toast.LENGTH_SHORT).show();
                        finish();
                    });

                }
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if (Objects.requireNonNull(etUserName.getText()).length() == 0
                || Objects.requireNonNull(etPwd.getText()).length() == 0) {
            tvBtnLogin.setEnabled(false);
        } else {
            tvBtnLogin.setEnabled(true);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void registerOnClick(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
