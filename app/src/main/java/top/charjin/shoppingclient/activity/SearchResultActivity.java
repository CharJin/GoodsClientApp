package top.charjin.shoppingclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.charjin.shoppingclient.R;
import top.charjin.shoppingclient.adapter.HomeGoodsAdapter;
import top.charjin.shoppingclient.adapter.SearchResultGoodsAdapter;
import top.charjin.shoppingclient.entity.OsGoods;
import top.charjin.shoppingclient.utils.HttpUtil;
import top.charjin.shoppingclient.utils.Router;

public class SearchResultActivity extends AppCompatActivity implements Callback {

    private RecyclerView rvRsGoods;
    private SearchResultGoodsAdapter adapter;
    private HomeGoodsAdapter adapter1;
    private List<OsGoods> goodsList = new ArrayList<>();

    private TextView tvSearchKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_activity);

        initComponent();

        // 设置搜索框，内容
        String key = getIntent().getStringExtra("key");
        tvSearchKey.setText(key);

        ///////////

        adapter1 = new HomeGoodsAdapter(this, goodsList, goods -> {
            Intent intent = new Intent(this, GoodsActivity.class);
            intent.putExtra("goods", goods);
            startActivity(intent);
        });
        rvRsGoods.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rvRsGoods.setAdapter(adapter1);
        HttpUtil.sendOkHttpRequestByGet(Router.BASE_URL + "goods/getGoodsByKey?key=" + key, this);

        //////////


    }

    private void initComponent() {
        tvSearchKey = findViewById(R.id.tv_search_result_key);
        rvRsGoods = findViewById(R.id.rv_search_result_goods);
    }

    private void initGoods() {


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

    public void finish(View view) {
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
            adapter1.getData().clear();
            adapter1.getData().addAll(goodsList);
            adapter1.notifyDataSetChanged();
        });


    }
}
