package top.charjin.shoppingclient.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.charjin.shoppingclient.R;
import top.charjin.shoppingclient.entity.OsUser;
import top.charjin.shoppingclient.utils.HttpUtil;
import top.charjin.shoppingclient.utils.JsonUtil;
import top.charjin.shoppingclient.utils.Router;

public class LoginActivity extends AppCompatActivity implements View.OnKeyListener {

    private MyApplication application;

    private TextInputEditText etUserName, etPwd;
    private TextView tvBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_main);
        application = (MyApplication) this.getApplication();

        etUserName = findViewById(R.id.et_login_user_name);
        etPwd = findViewById(R.id.et_login_password);
        tvBtnLogin = findViewById(R.id.tv_btn_login);

//        etUserName.setOnKeyListener(this);
//        etPwd.setOnKeyListener(this);

    }

    public void finish(View view) {
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
                    OsUser user = JsonUtil.parseJSONObject(jsonData, OsUser.class);
                    System.out.println(user);
                    application.setUser(user);
                    SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
//                    if (sp.getString("username", null) == null) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.clear()
                            .putString("username", user.getUsername())
                            .putString("password", user.getPassword())
                            .apply();
//                    }
                }
            }
        });
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (etUserName.getText().length() == 0 && etPwd.getText().length() == 0) {
            tvBtnLogin.setBackground(getDrawable(R.drawable.login_btn_disable_shape));
        } else {
            tvBtnLogin.setBackground(getDrawable(R.drawable.login_btn_able_shape));
        }
        return true;
    }
}
