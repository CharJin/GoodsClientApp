package top.charjin.shoppingclient.utils;

import java.util.Map;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {


    /**
     * get 方式发送请求
     * 通过enqueue, okhttp自动开启子线程
     *
     * @param address
     * @param callback
     */
    public static void sendOkHttpRequestByGet(String address, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * post 方式发送请求
     * 通过enqueue, okhttp自动开启子线程
     *
     * @param address
     * @param postArgs post方式传递的参数,使用map装入
     * @param callback
     */
    public static void sendOkHttpRequestByPost(String address, Map<String, String> postArgs, Callback callback) {
        OkHttpClient client = new OkHttpClient();

        FormBody.Builder requestBodyBuilder = new FormBody.Builder();
        postArgs.forEach(requestBodyBuilder::add);
        RequestBody requestBody = requestBodyBuilder.build();

        Request request = new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

}
