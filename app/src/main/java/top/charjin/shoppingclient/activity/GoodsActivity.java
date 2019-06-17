package top.charjin.shoppingclient.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.charjin.shoppingclient.R;
import top.charjin.shoppingclient.entity.OsGoods;
import top.charjin.shoppingclient.entity.OsShop;
import top.charjin.shoppingclient.model.CartGoodsModel;
import top.charjin.shoppingclient.model.PreOrderGoodsModel;
import top.charjin.shoppingclient.utils.HttpUtil;
import top.charjin.shoppingclient.utils.JsonUtil;
import top.charjin.shoppingclient.utils.Router;
import top.charjin.shoppingclient.utils.WindowUtil;
import top.charjin.shoppingclient.view.GoodsPopupWindow;

public class GoodsActivity extends BaseActivity {

    private OsGoods goods;

    private Banner banner;
    private TextView tvGoodsName;
    private TextView tvGoodsPrice;
    // 运费 字段未加 暂时保留
    private TextView tvSaleVolume;
    private TextView tvRegion;

    private RelativeLayout rlGoodsHeader;
    private ImageView ivGoodsHeaderBack;
    private ImageView ivGoodsHeaderShare;
    private TextView tvGoodsHeaderTitle;
    private ScrollView slGoodsMain;

    private GoodsPopupWindow popupWindow;
    private View popupWindowContentView;

    private int windowHeight = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_activity_main);

        initComponent();
        windowHeight = getWindowManager().getDefaultDisplay().getHeight();
        slGoodsMain.setOnScrollChangeListener(this::onScrollChange);

        // 根据传入的实体类或者goodsId初始化基本内容
        goods = (OsGoods) this.getIntent().getSerializableExtra("goods");

        TextView tvGoodsData = findViewById(R.id.tv_goods_json);
        tvGoodsData.setText(new Gson().toJson(goods));  // 简单显示goods的数据

        if (goods != null) {
//            tvGoodsHeaderTitle.setText("此处其后添加TabLayout");
            tvGoodsName.setText(goods.getGoodsName());
            tvGoodsPrice.setText(String.format("%s", goods.getPrice()));
            tvSaleVolume.setText(String.format("销量 %s", goods.getSalesVolume()));
            tvRegion.setText(goods.getRegion());
            initBanner();
        } else {
            int goodsId = getIntent().getIntExtra("goodsId", -1);
            if (goodsId != -1)
                HttpUtil.sendOkHttpRequestByGet(Router.GOODS_URL + "getGoodsById?goodsId=" + goodsId, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Toasty.error(GoodsActivity.this, "商品请求错误!").show();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String jsonData = response.body().string();
                        goods = JsonUtil.parseJSONObject(jsonData, OsGoods.class);
                        runOnUiThread(() -> {
                            tvGoodsName.setText(goods.getGoodsName());
                            tvGoodsPrice.setText(String.format("%s", goods.getPrice()));
                            tvSaleVolume.setText(String.format("销量 %s", goods.getSalesVolume()));
                            tvRegion.setText(goods.getRegion());
                        });
                    }
                });
        }


        // 初始化底部弹出窗口信息
        popupWindowContentView = LayoutInflater.from(this).inflate(R.layout.goods_mode_popup_window, null);
        popupWindow = new GoodsPopupWindow(this, popupWindowContentView);
        initPopupWindowEvent();

    }

    private void initComponent() {
        banner = findViewById(R.id.banner_goods_pic);
        tvGoodsName = findViewById(R.id.tv_goods_name);
        tvGoodsPrice = findViewById(R.id.tv_goods_price);
        tvSaleVolume = findViewById(R.id.tv_goods_sale_volume);
        tvRegion = findViewById(R.id.tv_goods_region);

        rlGoodsHeader = findViewById(R.id.rl_goods_header);
        slGoodsMain = findViewById(R.id.sl_goods_main);
        ivGoodsHeaderBack = findViewById(R.id.iv_goods_header_back);
        ivGoodsHeaderShare = findViewById(R.id.iv_goods_header_share);
        tvGoodsHeaderTitle = findViewById(R.id.tv_goods_header_title);

        ivGoodsHeaderBack.getBackground().mutate().setAlpha(50);
        ivGoodsHeaderShare.getBackground().mutate().setAlpha(50);

        tvGoodsHeaderTitle.setAlpha(0.0f);
        rlGoodsHeader.getBackground().mutate().setAlpha(0); // 标题栏的背景将背景透明度做初始化
        int statusBarHeight = WindowUtil.getStatusBarHeight(this);
        ViewGroup.LayoutParams goodsHeaderLayoutParams = rlGoodsHeader.getLayoutParams();
        goodsHeaderLayoutParams.height += statusBarHeight;
        rlGoodsHeader.setLayoutParams(goodsHeaderLayoutParams);
        rlGoodsHeader.setPadding(0, statusBarHeight, 0, 0);
    }

    /**
     * 初始化弹出窗口属性及事件,(需改进，使用自定义View来加载事件)
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

        int maxBuyNumber = 10;
        btnPlus.setOnClickListener(v -> {
            int nowNum = Integer.parseInt(tvGoodsItemChooseNumber.getText().toString());
            if (nowNum == maxBuyNumber)
                Toasty.warning(GoodsActivity.this, "已经超过限购数量啦！").show();
            else
                tvGoodsItemChooseNumber.setText(getResources().getString(R.string.app_goods_chosen_buy_number, nowNum + 1));
        });

        // 弹唱中减少商品
        btnSubtract.setOnClickListener(v -> {
            int nowNum = Integer.parseInt(tvGoodsItemChooseNumber.getText().toString());
            if (nowNum == 1)
                Toasty.warning(GoodsActivity.this, "商品数量不能再少啦！").show();
            else
                tvGoodsItemChooseNumber.setText(getResources().getString(R.string.app_goods_chosen_buy_number, nowNum - 1));
        });

        // 弹窗中点击确认按钮
        tvBtnBuyConfirm.setOnClickListener(v -> {
            if (user == null) {
                Toast.makeText(this, "请登录", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                return;
            }
            // 输出请求url
            int chooseNum = Integer.parseInt(tvGoodsItemChooseNumber.getText().toString());
            System.out.println(Router.BASE_URL + "cart/addGoods?userId=" + user.getUserId() + "&goodsId=" + goods.getGoodsId());
            HttpUtil.sendOkHttpRequestByGet(Router.BASE_URL + "cart/addGoods?userId=" + user.getUserId() + "&goodsId=" + goods.getGoodsId()
                    + "&goodsNum=" + chooseNum, new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    assert response.body() != null;
                    String jsonData = response.body().string();
                    runOnUiThread(() -> {
                        if (Integer.parseInt(jsonData) > 0) {
                            Toast.makeText(GoodsActivity.this, "已添加至购物车!", Toast.LENGTH_SHORT).show();
                            popupWindow.dismiss();
                        } else {
                            Toast.makeText(GoodsActivity.this, "好像出错了!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

        });

//        String imgUri = goods.get
        Glide.with(this).load(goods.getImage()).into(ivGoodsItem);

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
        List<String> imgUrls = new ArrayList<>();
        String[] urls = goods.getImage().split("~~");
        Collections.addAll(imgUrls, urls);

        imgUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560275469604&di=4ab8e3f71951c7d70402f03533a453d1&imgtype=0&src=http%3A%2F%2Fimg4.cache.netease.com%2Flady%2F2013%2F12%2F2%2F201312021033519354e_550.jpg");

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
    }

    public void buyNow(View view) {
        if (!existsUser()) {
            Toasty.info(this, "请登录账户").show();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        Intent intent = new Intent(this, OrderSubmitActivity.class);
        OsShop shop = goods.getShop();
        ArrayList<PreOrderGoodsModel> orderGoodsList = new ArrayList<>();
//        = intent.getSerializableExtra("orderGoodsList")
//        as ArrayList<PreOrderGoodsModel >
        Gson gson = new Gson();
        CartGoodsModel goodsModel = gson.fromJson(gson.toJson(goods), CartGoodsModel.class);

        ArrayList<CartGoodsModel> goodList = new ArrayList<>();
        goodList.add(goodsModel);
        PreOrderGoodsModel orderGoodsModel = new PreOrderGoodsModel(shop.getShopId(), shop.getShopName(), goodList);

        orderGoodsList.add(orderGoodsModel);
        intent.putExtra("orderGoodsList", orderGoodsList);
        startActivity(intent);
//        finish();
    }

    public void goShop(View view) {
        Intent intent = new Intent(this, ShopActivity.class);
        intent.putExtra("shop", goods.getShop());
        startActivity(intent);
    }

    public void collectGoods(View view) {
    }


    @Override
    protected void setColorId() {
        mColorId = Color.TRANSPARENT;
    }

    private void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY >= 0 && scrollY < 765) {
            float f = (float) scrollY / 765;
            tvGoodsHeaderTitle.setAlpha(f);
            rlGoodsHeader.getBackground().mutate().setAlpha(scrollY / 3);
            if (f > 0.8) {
                ivGoodsHeaderBack.setBackground(null);
                ivGoodsHeaderBack.setImageResource(R.drawable.goods_header_img_back_white);
                ivGoodsHeaderShare.setBackground(null);
                ivGoodsHeaderShare.setImageResource(R.drawable.goods_header_img_share_white);
            } else {
                ivGoodsHeaderBack.setBackground(getResources().getDrawable(R.drawable.goods_back_bg_shape));
                ivGoodsHeaderBack.setImageResource(R.drawable.goods_header_img_back_black);
                ivGoodsHeaderShare.setBackground(getResources().getDrawable(R.drawable.goods_back_bg_shape));
                ivGoodsHeaderShare.setImageResource(R.drawable.goods_header_img_share_grey);
            }
        }
    }
}
