package top.charjin.shoppingclient.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.charjin.shoppingclient.R;
import top.charjin.shoppingclient.activity.MyApplication;
import top.charjin.shoppingclient.adapter.CartAdapter;
import top.charjin.shoppingclient.entity.OsUser;
import top.charjin.shoppingclient.model.CartGoodsModel;
import top.charjin.shoppingclient.model.CartShopModel;
import top.charjin.shoppingclient.utils.HttpUtil;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View homeView = inflater.inflate(R.layout.cart_fragment_main, container, false);

//        activity = getActivity();

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
        return homeView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


//        MyApplication application = (MyApplication) this.getActivity().getApplicationContext();
//        user = application.getUser();

    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println(context);
        checkUser();
        if (isLoaded)
            initCartData();
        Log.e("Cart", "onResume");

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("Cart", "onStart");

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        isLoaded = false;
        // 防止从另一个活动返回后 重新加载数据 导致原页面数据被清除
        super.onSaveInstanceState(outState);
        Log.e("Cart", "onSaveInstanceState");


    }

    private void initCartData() {
        Log.e("Cart", "initCartData");
        OsUser user = (OsUser) MyApplication.map.get("user");
        // 保留 check
//        if (user == null) {
//            Toast.makeText(activity, "请登录账户", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(activity, LoginActivity.class));
//            return;
//        }
        HttpUtil.sendOkHttpRequestByGet(Router.BASE_URL + "cart/query-cart?userId=" + 1, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(this.getClass().getName(), e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 清空原数据，购物车重新加载
                shopList.clear();
                cartMap.clear();

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
}