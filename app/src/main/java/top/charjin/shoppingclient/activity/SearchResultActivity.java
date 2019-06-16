package top.charjin.shoppingclient.activity;

import android.graphics.Color;
import android.os.Bundle;
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
import top.charjin.shoppingclient.adapter.GoodsDisplayAdapter;
import top.charjin.shoppingclient.entity.OsGoods;
import top.charjin.shoppingclient.utils.HttpUtil;
import top.charjin.shoppingclient.utils.Router;

import static top.charjin.shoppingclient.utils.WindowUtil.setAndroidNativeLightStatusBar;

public class SearchResultActivity extends BaseActivity implements Callback {

    private RecyclerView rvRsGoods;
    private GoodsDisplayAdapter adapter;
    private List<OsGoods> goodsList = new ArrayList<>();

    private TextView tvSearchKey;
    private String key;

    @Override
    protected void setColorId() {
        this.mColorId = Color.TRANSPARENT;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_activity);
        setAndroidNativeLightStatusBar(this, true);
        initComponent();

        // 设置搜索框，内容
        key = getIntent().getStringExtra("key");
        tvSearchKey.setText(key);

        initGoodsData();
    }

    private void initComponent() {
        tvSearchKey = findViewById(R.id.tv_search_result_key);
        rvRsGoods = findViewById(R.id.rv_search_result_goods);
    }

    private void initGoodsData() {

        adapter = new GoodsDisplayAdapter(this, goodsList);
        rvRsGoods.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rvRsGoods.setAdapter(adapter);
        HttpUtil.sendOkHttpRequestByGet(Router.GOODS_URL + "getGoodsByKey?key=" + key, this);


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

    public void finishOnClick(View view) {
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
        List<OsGoods> goodsListData = gson.fromJson(jsonData, new TypeToken<List<OsGoods>>() {
        }.getType());
        this.goodsList.clear();
        this.goodsList.addAll(goodsListData);

        runOnUiThread(() -> adapter.notifyDataSetChanged());


    }
}
