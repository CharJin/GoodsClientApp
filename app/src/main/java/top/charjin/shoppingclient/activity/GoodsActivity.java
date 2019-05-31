package top.charjin.shoppingclient.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.charjin.shoppingclient.R;
import top.charjin.shoppingclient.entity.OsGoods;
import top.charjin.shoppingclient.entity.OsUser;
import top.charjin.shoppingclient.utils.HttpUtil;
import top.charjin.shoppingclient.utils.JsonUtil;
import top.charjin.shoppingclient.utils.Router;
import top.charjin.shoppingclient.view.GoodsPopupWindow;

public class GoodsActivity extends AppCompatActivity {

    private MyApplication application;
    private OsGoods goods;

    private Banner banner;
    private TextView tvGoodsName;
    private TextView tvGoodsPrice;
    // 运费 字段未加 暂时保留
    private TextView tvSaleVolume;
    private TextView tvRegion;

    private GoodsPopupWindow popupWindow;
    private View popupWindowContentView;


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
        application = (MyApplication) this.getApplication();
        // 获取点击商品后传来的商品实体类

        initComponent();

        // 根据传入的实体类或者goodsId初始化基本内容
        goods = (OsGoods) this.getIntent().getSerializableExtra("goods");
        if (goods != null) {
            tvGoodsName.setText(goods.getName());
            tvGoodsPrice.setText(String.format("%s", goods.getPrice()));
            tvSaleVolume.setText(String.format("销量 %s", goods.getSalesVolume()));
            tvRegion.setText(goods.getRegion());
            initBanner();
        } else {
            int goodsId = getIntent().getIntExtra("goodsId", -1);
            if (goodsId != -1)
                HttpUtil.sendOkHttpRequestByGet(Router.Goods_URL + "getGoodsById?goodsId=" + goodsId, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Toasty.error(GoodsActivity.this, "商品请求错误!").show();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String jsonData = response.body().string();
                        goods = JsonUtil.parseJSONObject(jsonData, OsGoods.class);
                        runOnUiThread(() -> {
                            tvGoodsName.setText(goods.getName());
                            tvGoodsPrice.setText(String.format("%s", goods.getPrice()));
                            tvSaleVolume.setText(String.format("销量 %s", goods.getSalesVolume()));
                            tvRegion.setText(goods.getRegion());
                        });
                    }
                });
        }


        // 初始化底部弹出窗口信息
        popupWindowContentView = LayoutInflater.from(this).inflate(R.layout.goods_standard_popup_window, null);
        popupWindow = new GoodsPopupWindow(this, popupWindowContentView);

    }

    /**
     * 初始化弹出窗口属性及事件
     */
    private void initPopupWindowEvent() {
        ImageView ivGoodsItem = popupWindowContentView.findViewById(R.id.iv_goods_standard_item);
        TextView tvGoodsItemPrice = popupWindowContentView.findViewById(R.id.tv_goods_item_price);
        TextView tvGoodsItemStock = popupWindowContentView.findViewById(R.id.tv_goods_item_stock);
        TextView tvGoodsItemSendRegion = popupWindowContentView.findViewById(R.id.tv_goods_item_send_region);
        TextView tvGoodsItemBuyLimited = popupWindowContentView.findViewById(R.id.tv_goods_item_buy_limited_number);
        TextView tvGoodsItemChooseNumber = popupWindowContentView.findViewById(R.id.tv_goods_item_choose_number);
        Button btnPlus = popupWindowContentView.findViewById(R.id.btn_goods_item_plus);
        Button btnSubtract = popupWindowContentView.findViewById(R.id.btn_goods_item_subtract);

        TextView tvBtnBuyConfirm = popupWindowContentView.findViewById(R.id.tv_btn_confirm_goods_item);
        tvGoodsItemChooseNumber.setText(getResources().getString(R.string.app_goods_chosen_buy_number, 1));

        int maxBuyNumber = 3;
        btnPlus.setOnClickListener(v -> {
            int nowNum = Integer.parseInt(tvGoodsItemChooseNumber.getText().toString());
            if (nowNum == maxBuyNumber)
                Toasty.warning(GoodsActivity.this, "已经超过限购数量啦！").show();
            else
                tvGoodsItemChooseNumber.setText(getResources().getString(R.string.app_goods_chosen_buy_number, nowNum + 1));
        });

        btnSubtract.setOnClickListener(v -> {
            int nowNum = Integer.parseInt(tvGoodsItemChooseNumber.getText().toString());
            if (nowNum == 1)
                Toasty.warning(GoodsActivity.this, "商品数量不能再少啦！").show();
            else
                tvGoodsItemChooseNumber.setText(getResources().getString(R.string.app_goods_chosen_buy_number, nowNum - 1));
        });

        tvBtnBuyConfirm.setOnClickListener(v -> {

        });

//        String imgUri = goods.get
        Glide.with(this).load(R.drawable.background).into(ivGoodsItem);

        // 固定的数据 需要再添加数据库字段  后补充
        tvGoodsItemPrice.setText(getResources().getString(R.string.goods_item_pop_price, goods.getPrice()));
        tvGoodsItemStock.setText(getResources().getString(R.string.goods_item_pop_stock, 99));
        tvGoodsItemSendRegion.setText(getResources().getString(R.string.goods_item_pop_send_region, goods.getRegion()));
        tvGoodsItemBuyLimited.setText(getResources().getString(R.string.goods_item_pop_buy_limited, maxBuyNumber));

    }

    /**
     * 获取图片路径 Banner 显示
     */
    private void initBanner() {
        List<Integer> imgUrls = new ArrayList<>();
//        String[] urls = goods.getImage().split("~");
//        Collections.addAll(imgUrls, urls);

        imgUrls.add(R.drawable.background);
        imgUrls.add(R.drawable.background);
        imgUrls.add(R.drawable.background);
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(GoodsActivity.this)
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

        popupWindow.openPopupWindow();
        initPopupWindowEvent();     // 注： 只需执行一次


        if (popupWindow != null) return;


        OsUser user = (OsUser) MyApplication.map.get("user");
        if (user == null) {
            Toast.makeText(this, "请登录", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        // 输出请求url
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
