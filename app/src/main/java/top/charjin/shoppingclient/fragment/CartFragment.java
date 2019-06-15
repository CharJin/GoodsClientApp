package top.charjin.shoppingclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.charjin.shoppingclient.R;
import top.charjin.shoppingclient.ShoppingApplication;
import top.charjin.shoppingclient.activity.LoginActivity;
import top.charjin.shoppingclient.activity.OrderSubmitActivity;
import top.charjin.shoppingclient.adapter.CartAdapter;
import top.charjin.shoppingclient.adapter.GoodsDisplayAdapter;
import top.charjin.shoppingclient.entity.OsGoods;
import top.charjin.shoppingclient.model.CartGoodsModel;
import top.charjin.shoppingclient.model.CartShopModel;
import top.charjin.shoppingclient.model.PreOrderGoodsModel;
import top.charjin.shoppingclient.utils.HttpUtil;
import top.charjin.shoppingclient.utils.JsonUtil;
import top.charjin.shoppingclient.utils.Router;

public class CartFragment extends BaseFragment implements CartAdapter.OnItemSelectedListener {

    View homeView;

    private ExpandableListView elvCart;
    private CartAdapter cartAdapter;

    private List<CartShopModel> shopList = new ArrayList<>();
    private Map<CartShopModel, List<CartGoodsModel>> cartMap = new HashMap<>();

    private TextView tvBtnCheckout;
    private TextView tvCartSum;
    private CheckBox cbChooseAll;
    private boolean isChooseAll = false;

    private int goods_num = 0;          //商品的价格
    private double sum_price = 0.0;     //合计总价格
    private boolean isLoaded = true;

    private LinearLayout llHintEmpty;

    private RecyclerView rvGoodsDisplay;
    private GoodsDisplayAdapter goodsAdapter;
    private List<OsGoods> goodsList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.cart_fragment_main;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View homeView = super.onCreateView(inflater, container, savedInstanceState);
        View homeView = LayoutInflater.from(context).inflate(R.layout.cart_fragment_main, container, false);


        tvBtnCheckout = homeView.findViewById(R.id.tv_btn_cart_chock_out);
        tvCartSum = homeView.findViewById(R.id.tv_cart_sum);

        elvCart = homeView.findViewById(R.id.elv_cart);
        cartAdapter = new CartAdapter(this.getContext(), shopList, cartMap);
        cartAdapter.setOnItemSelectedListener(this);
        cartAdapter.setOnCartGoodsChangedListener(this::onCartGoodsChanged);
        elvCart.setAdapter(cartAdapter);

        cbChooseAll = homeView.findViewById(R.id.cb_cart_choose_all);
        cbChooseAll.setOnClickListener(this::onChooseAllClick);

        // 初始总额为0
        tvBtnCheckout.setText(String.format(getResources().getString(R.string.cart_bottom_checkout), 0 + ""));
        // 点击结算按钮,跳转至订单提交页面
        tvBtnCheckout.setOnClickListener(v -> {
            List<PreOrderGoodsModel> orderGoodsList = new ArrayList<>();

            // 遍历map, 过滤出被选中的商品存入list中
            cartMap.forEach((shop, goodsList) -> {
                List<CartGoodsModel> selectedGoodsList = goodsList.stream().filter(CartGoodsModel::isChecked).collect(Collectors.toList());
                if (selectedGoodsList.size() != 0) {
                    PreOrderGoodsModel preOrder = new PreOrderGoodsModel(shop.getShopId(), shop.getShopName(), selectedGoodsList);
                    orderGoodsList.add(preOrder);
                }
            });

            Intent intent = new Intent(context, OrderSubmitActivity.class);
            intent.putExtra("orderGoodsList", (Serializable) orderGoodsList);
            startActivity(intent);

        });

        llHintEmpty = homeView.findViewById(R.id.ll_cart_hint_empty);


        // 初始化推荐商品信息
       /* rvGoodsDisplay = homeView.findViewById(R.id.rv_goods_display);

        goodsAdapter = new GoodsDisplayAdapter(context, goodsList);
        rvGoodsDisplay.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rvGoodsDisplay.setAdapter(goodsAdapter);

        initGoodsDisplayData();*/

        return homeView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (ShoppingApplication.getUser() == null) {
            toast("请登录账户哦!");
            startActivity(new Intent(context, LoginActivity.class));
            cbChooseAll.setEnabled(false);
            return;
        }
        cbChooseAll.setEnabled(true);
        initCartData();

    }


    /**
     * 初始化购物车数据
     */
    private void initCartData() {
        llHintEmpty.setVisibility(View.GONE);
        HttpUtil.sendOkHttpRequestByGet(Router.BASE_URL + "cart/query-cart?userId=" + ShoppingApplication.getUser().getUserId(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(this.getClass().getName(), e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 清空原数据，购物车重新加载
                shopList.clear();
                cartMap.clear();

                assert response.body() != null;
                String jsonData = response.body().string();

                Gson gson = new Gson();
                try {
                    JSONArray jsonArray = new JSONArray(jsonData);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        CartShopModel shop = gson.fromJson(jsonObject.getJSONObject("shop").toString(), CartShopModel.class);
                        shopList.add(shop);

                        List<CartGoodsModel> goodsList = gson.fromJson(
                                jsonObject.getJSONArray("listGoods").toString(), new TypeToken<List<CartGoodsModel>>() {
                                }.getType());
                        cartMap.put(shop, goodsList);

                    }

                    activity.runOnUiThread(() -> {
                        if (shopList.size() > 0) {
                            cbChooseAll.setEnabled(true);
                            elvCart.setVisibility(View.VISIBLE);
                        } else {
                            cbChooseAll.setChecked(false);
                            cbChooseAll.setEnabled(false);
                            llHintEmpty.setVisibility(View.VISIBLE);
                            elvCart.setVisibility(View.GONE);
                        }
                        cartAdapter.notifyDataSetChanged();
                        // 根据店铺的数量，把每个item展开
                        for (int i = 0; i < shopList.size(); i++) {
                            elvCart.expandGroup(i);
                        }
                    });

//                    cartAdapter = new CartAdapter(CartFragment.this.getContext(), shopList, cartMap);
//                    handler.sendEmptyMessage(1);
//                    elvCart.setAdapter(cartAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * 回调函数, 购物车中店铺被check 对应的其中所有的商品也需变为checked状态
     *
     * @param isChecked
     * @param shopPos
     */
    @Override
    public void onShopItemSelected(boolean isChecked, int shopPos) {
        CartShopModel shopModel = shopList.get(shopPos);

        List<CartGoodsModel> list = cartMap.get(shopModel);
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setChecked(isChecked);
                cartAdapter.notifyDataSetChanged();
            }
        }

        cbChooseAll.setChecked(shopList.stream().filter(CartShopModel::isChecked).count() == shopList.size());
        updateBtnCheckout();

    }

    /**
     * 回调函数，在购物车商品被勾选时，将状态标记为checked，更新适配器
     *
     * @param isChecked
     * @param shopPos
     * @param goodsPos
     */
    @Override
    public void onGoodsItemSelected(boolean isChecked, int shopPos, int goodsPos) {
        CartShopModel shopModel = shopList.get(shopPos);

        if (!isChecked) {
            // 商品未被check 对应店铺一定也未check
            shopModel.setChecked(false);
        } else {
            // 商品被check 判断对应店铺下该商品是否被全部选中 以此更新shop的check状态
            List<CartGoodsModel> goodsModels = cartMap.get(shopModel);
            int childCount;
            if (goodsModels != null) {
                childCount = (int) goodsModels.stream().filter(CartGoodsModel::isChecked).count();
                if (childCount == goodsModels.size()) {
                    shopModel.setChecked(true);
                }
            }
        }

        cbChooseAll.setChecked(shopList.stream().filter(CartShopModel::isChecked).count() == shopList.size());
        cartAdapter.notifyDataSetChanged();

        updateBtnCheckout();
    }

    /**
     * 点击底部的全选按钮
     *
     * @param e
     */
    private void onChooseAllClick(View e) {
        cartMap.forEach((cartShopModel, cartGoodsModels) -> {
            isChooseAll = cbChooseAll.isChecked();
            cartShopModel.setChecked(isChooseAll);
            for (CartGoodsModel goods : cartGoodsModels)
                goods.setChecked(isChooseAll);
        });
        isChooseAll = !isChooseAll;
        cartAdapter.notifyDataSetChanged();
        updateBtnCheckout();
    }


    /**
     * 更新总价格以及选中的商品个数
     */
    private void updateBtnCheckout() {
        goods_num = 0;
        sum_price = 0.0;
        shopList.stream().map(shop -> cartMap.get(shop)).filter(Objects::nonNull).forEach(goodsModels -> {
            if (goodsModels != null) {
                goodsModels.forEach(goods -> {
                    if (goods.isChecked()) {
                        goods_num++;
                        sum_price += goods.getPrice() * goods.getGoodsNum();
                    }
                });
            }
        });
        tvBtnCheckout.setText(String.format(getResources().getString(R.string.cart_bottom_checkout), goods_num + ""));
        tvCartSum.setText(String.format("%s", sum_price));
        tvBtnCheckout.setEnabled(sum_price > 0);
    }

    private void onCartGoodsChanged(boolean isSucceeded) {
        if (isSucceeded) {
//            activity.runOnUiThread(this::);
            updateBtnCheckout();
        }
    }


    private void initGoodsDisplayData() {
        HttpUtil.sendOkHttpRequestByGet(Router.BASE_URL + "goods/getAllGoods", new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                assert response.body() != null;
                String jsonData = response.body().string();
                activity.runOnUiThread(() -> {
                    goodsList.clear();
                    goodsList.addAll(JsonUtil.parseJSONObjectInStringToEntityList(jsonData, OsGoods.class));
                    goodsAdapter.notifyDataSetChanged();
                });
            }
        });
    }


}