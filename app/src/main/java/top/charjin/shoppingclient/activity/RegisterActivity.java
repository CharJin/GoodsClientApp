package top.charjin.shoppingclient.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.charjin.shoppingclient.R;
import top.charjin.shoppingclient.entity.OsUser;
import top.charjin.shoppingclient.model.ResultModel;
import top.charjin.shoppingclient.utils.HttpUtil;
import top.charjin.shoppingclient.utils.JsonUtil;
import top.charjin.shoppingclient.utils.Router;

public class RegisterActivity extends BaseActivity implements TextWatcher {

    private TextInputEditText etUserName, etPwd, etPwdConfirm;
    private TextView tvBtnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity_main);

        initComponent();

        etUserName.addTextChangedListener(this);
        etPwd.addTextChangedListener(this);
        etPwdConfirm.addTextChangedListener(this);

        tvBtnRegister = findViewById(R.id.tv_btn_register);
    }

    private void initComponent() {
        etUserName = findViewById(R.id.et_register_user_name);
        etPwd = findViewById(R.id.et_register_password);
        etPwdConfirm = findViewById(R.id.et_register_password_confirm);
    }

    public void finishOnClick(View view) {
        this.finish();
    }

    public void registerOnClick(View view) {
        String pwd = Objects.requireNonNull(etPwd.getText()).toString(),
                pwdConfirm = Objects.requireNonNull(etPwdConfirm.getText()).toString();
        if (!pwd.equals(pwdConfirm)) {
            Toasty.info(this, "两次密码不一致").show();
        } else {
            OsUser user = new OsUser();
            user.setUsername(etUserName.getText().toString());
            user.setPassword(pwd);

            // 传递用户实体类对象至服务端 如果成功,将此对象设置为全局对象
            String json_user = new Gson().toJson(user);
            HttpUtil.sendOkHttpRequestByPost(Router.USER_URL + "register", json_user, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
//                    Log.e("Register", "注册失败,用户");
                    runOnUiThread(() -> Toasty.warning(RegisterActivity.this, "注册失败!").show());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
//                    Log.e("Register", "注册成功");

                    assert response.body() != null;
                    String jsonData = response.body().string();
                    ResultModel rs = JsonUtil.parseJSONObject(jsonData, ResultModel.class);

                    runOnUiThread(() -> {
                        if (rs.getCode() == 202)
                            Toasty.warning(RegisterActivity.this, "该用户名已被使用!").show();
                        else {
                            Toasty.success(RegisterActivity.this, "注册成功!,去登录吧!").show();
//                            ShoppingApplication.setUser(user);
                            finish();
                        }
                    });
                }
            });
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        TextInputEditText[] arr = new TextInputEditText[]{etUserName, etPwd, etPwdConfirm};
        int notEmptyNum = 0;
        for (TextInputEditText editText : arr) {
            if (Objects.requireNonNull(editText.getText()).length() != 0) {
                notEmptyNum++;
            }
        }
        tvBtnRegister.setEnabled(notEmptyNum == 3);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
