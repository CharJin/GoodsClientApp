package top.charjin.shoppingclient.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.charjin.shoppingclient.R;
import top.charjin.shoppingclient.entity.OsGoods;
import top.charjin.shoppingclient.entity.OsUser;
import top.charjin.shoppingclient.utils.HttpUtil;
import top.charjin.shoppingclient.utils.Router;

public class GoodsActivity extends AppCompatActivity {

    private OsGoods goods;

    private Banner banner;
    private TextView tvGoodsName;
    private TextView tvGoodsPrice;
    // 运费 字段未加 暂时保留
    private TextView tvSaleVolume;
    private TextView tvRegion;


    private void initComponent() {
        banner = findViewById(R.id.banner_goods_pic);
        tvGoodsName = findViewById(R.id.tv_goods_name);
        tvGoodsPrice = findViewById(R.id.tv_goods_price);
        tvSaleVolume = findViewById(R.id.tv_goods_sale_volume);
        tvRegion = findViewById(R.id.tv_goods_region);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_activity_main);
        // 获取点击商品后传来的商品实体类
        goods = (OsGoods) this.getIntent().getSerializableExtra("goods");

        initComponent();
        initBanner();

        // 设置基本显示内容
        tvGoodsName.setText(goods.getName());
        tvGoodsPrice.setText(String.format("%s", goods.getPrice()));
        tvSaleVolume.setText(String.format("销量 %s", goods.getSalesVolume()));
        tvRegion.setText(goods.getRegion());

    }

    /**
     * 获取图片路径 Banner 显示
     */
    private void initBanner() {
        List<String> imgUrls = new ArrayList<>();
        String[] urls = goods.getImage().split("~");
        Collections.addAll(imgUrls, urls);
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context.getApplicationContext())
                        .load(path)
                        .into(imageView);
            }
        });
        banner.setBannerAnimation(Transformer.Default);
        banner.setDelayTime(3000);
        banner.isAutoPlay(true);
        banner.setImages(imgUrls).start();
    }

    public void backOnClick(View view) {
        this.finish();
    }


    public void shareOnClick(View view) {
        Toast.makeText(this, "已分享成功，哈哈！", Toast.LENGTH_SHORT).show();
    }

    public void addCart(View view) {
        OsUser user = ((MyApplication) this.getApplication()).getUser();
        if (user == null) {
            Toast.makeText(this, "请登录", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
        }
        System.out.println(Router.BASE_URL + "cart/addGoods?userId=" + user.getId() + "&goodsId=" + goods.getId());
        HttpUtil.sendOkHttpRequestByGet(Router.BASE_URL + "cart/addGoods?userId=" + user.getId() + "&goodsId=" + goods.getId(), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                assert response.body() != null;
                String jsonData = response.body().string();
                runOnUiThread(() -> {
                    if (Integer.parseInt(jsonData) > 0)
                        Toast.makeText(GoodsActivity.this, "已添加至购物车!", Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(GoodsActivity.this, "好像出错了!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void buyNow(View view) {
    }

    public void goShop(View view) {
    }

    public void collectGoods(View view) {
    }
}
