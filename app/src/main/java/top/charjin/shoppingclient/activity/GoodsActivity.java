package top.charjin.shoppingclient.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import top.charjin.shoppingclient.R;
import top.charjin.shoppingclient.entity.OsGoods;

public class GoodsActivity extends AppCompatActivity {

    private Banner banner;
    private OsGoods goods;

    private TextView tvGoodsName;
    private TextView tvGoodsPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_activity_main);
        goods = (OsGoods) this.getIntent().getSerializableExtra("goods");
        initBanner();

    }

    /**
     * 获取图片路径 Banner 显示
     */
    private void initBanner() {
        List<String> imgUrls = new ArrayList<>();
        String[] urls = goods.getImage().split("~");
        Collections.addAll(imgUrls, urls);
        banner = findViewById(R.id.banner_goods_pic);
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

    }


    public void shareOnClick(View view) {

    }
}
