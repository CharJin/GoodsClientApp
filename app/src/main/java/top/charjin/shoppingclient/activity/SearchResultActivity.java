package top.charjin.shoppingclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.charjin.shoppingclient.R;
import top.charjin.shoppingclient.adapter.SearchResultGoodsAdapter;
import top.charjin.shoppingclient.entity.OsGoods;
import top.charjin.shoppingclient.utils.HttpUtil;
import top.charjin.shoppingclient.utils.Router;

public class SearchResultActivity extends AppCompatActivity implements Callback {

    private RecyclerView rvRsGoods;
    private SearchResultGoodsAdapter adapter;
    private List<OsGoods> goodsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_activity);

        initGoods();

        rvRsGoods = findViewById(R.id.rv_search_result_goods);
        rvRsGoods.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

    }

    private void initGoods() {
        Intent intent = getIntent();
        String key = intent.getStringExtra("key");

        HttpUtil.sendOkHttpRequestByGet(Router.BASE_URL + "goods/getAllGoods", this);


//        Retrofit retrofit = new Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create())
//                .baseUrl(Router.BASE_URL)
//                .build();
//
//        GoodsService goodsService = retrofit.create(GoodsService.class);
//
//        Call<List<OsGoods>> allGoods = goodsService.getAllGoods();
//        allGoods.enqueue(new Callback<List<OsGoods>>() {
//            @Override
//            public void onResponse(Call<List<OsGoods>> call, Response<List<OsGoods>> response) {
//                Log.e("jijiji", response.body() + "");
////                System.out.println("dsadsadsdads"+response.body());
//            }
//
//            @Override
//            public void onFailure(Call<List<OsGoods>> call, Throwable t) {
//                Log.e("fdfdfd", t.getMessage());
//            }
//        });

    }

    public void backHome(View view) {
        this.finish();
    }

    @Override
    public void onFailure(Call call, IOException e) {
        System.out.println(e.getMessage());

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String jsonData = response.body().string();
        System.out.println(jsonData);

        Gson gson = new Gson();
        goodsList = gson.fromJson(jsonData, new TypeToken<List<OsGoods>>() {
        }.getType());

        runOnUiThread(() -> {
            adapter = new SearchResultGoodsAdapter(goodsList);
            rvRsGoods.setAdapter(adapter);
        });


    }
}
