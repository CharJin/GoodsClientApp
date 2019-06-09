package top.charjin.shoppingclient.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

    /**
     * 把jsonObject转为实体类
     *
     * @param jsonObject
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T parseJSONObject(JsonObject jsonObject, Class<T> clazz) {
        return new Gson().fromJson(jsonObject.toString(), clazz);
    }

    public static <T> T parseJSONObject(String jsonObject, Class<T> clazz) {

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        return gson.fromJson(jsonObject, clazz);
    }

//    public static <T> T parseJSONObjectInStringToEntity(String jsonData, Class<T> clazz) {
//        return new Gson().fromJson(jsonData, clazz);
//    }

    public static <T> List<T> parseJSONObjectInStringToEntityList(String jsonData, Class<T> clazz) {
//        Gson gson = new Gson();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        List<T> data = new ArrayList<>();
        JsonArray array = new JsonParser().parse(jsonData).getAsJsonArray();
        for (JsonElement e : array) {
            data.add(gson.fromJson(e, clazz));
        }
//        return gson.fromJson(jsonData, new TypeToken<ArrayList<T>>() {
//        }.getType());
        return data;
    }

    public static <T> List<T> parseJSONArrayToList(JSONArray jsonArray, Class<T> clazz) {
        try {
            List<T> dataList = new ArrayList<>();
            Gson gson = new Gson();
            for (int i = 0; i < jsonArray.length(); i++) {
                Object o = jsonArray.get(i);
                if (o instanceof JSONObject) {
                    dataList.add(gson.fromJson(o.toString(), clazz));
                }
            }
            return dataList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String parseObjectToJSONWithDateFormat(Object object) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                .create();
        return gson.toJson(object);
    }
}
