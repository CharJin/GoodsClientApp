package top.charjin.shoppingclient.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

    public static parseJSONObjectGoods(JsonObject jsonObject) {
        for (String s : jsonObject.keySet()) {

        }


    }

    /**
     * 把jsonObject转为实体类
     *
     * @param jsonObject
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T parseJSONObject(JsonObject jsonObject, Class<T> tClass) {
        return new Gson().fromJson(jsonObject.toString(), tClass);
    }

    public static <T> List<T> getParamList(JSONArray jsonArray, Class<T> clzz) {
        try {
            List<T> dataList = new ArrayList<>();
            Gson gson = new Gson();
            for (int i = 0; i < jsonArray.length(); i++) {

                Object o = jsonArray.get(i);
                if (o instanceof JSONObject) {
                    dataList.add(gson.fromJson(o.toString(), clzz));
                }
            }
            return dataList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
