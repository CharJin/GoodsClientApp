package top.charjin.shoppingclient.asyn;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;
import top.charjin.shoppingclient.utils.HttpUtil;
import top.charjin.shoppingclient.utils.Router;

public class MyAsyncTask extends AsyncTask<String, Void, Void> {

    /**
     * 判断用户是否修改了密码
     */
    private void checkUser(String username, String password, Callback callback) {
        System.out.println(Router.BASE_URL + "user/login?username=" + username + "&password=" + password);
        if (username != null) { // 用户名不为空，检测密码是否修改
            HttpUtil.sendOkHttpRequestByGet(Router.BASE_URL + "user/login?username=" + username + "&password=" + password, new okhttp3.Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String jsonData = response.body().string();
                    callback.response();
                }
            });
//            if (isFirst) {
//                SharedPreferences.Editor editor = sp.edit();
//                editor.putBoolean("isFirst", false);
//                editor.apply();
//            }
        }


    }

    @Override
    protected Void doInBackground(String... data) {
        return null;
    }

    public interface Callback {
        void response();
    }

}
