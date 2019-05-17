package top.charjin.shoppingclient.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import top.charjin.shoppingclient.entity.OsGoods;

public interface GoodsService {
    @GET("goods/getAllGoods")
    Call<List<OsGoods>> getAllGoods();


    @GET("goods/getGoodsByKey")
    Call<List<OsGoods>> getGoodsByKey(@Query("key") String key);
}
