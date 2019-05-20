package top.charjin.shoppingclient.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListView;

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

public class CartFragment extends Fragment {

    View homeView;

    private Activity activity;

    private ExpandableListView elvCart;
    private CartAdapter cartAdapter;

    private List<CartShopModel> shopList = new ArrayList<>();
    private Map<CartShopModel, List<CartGoodsModel>> cartMap = new HashMap<>();

    private CheckBox cbChooseAll;
    private boolean isChooseAll = true;

//    @SuppressLint("HandlerLeak")
//    private final Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            elvCart.setAdapter(cartAdapter);
//            for (int i = 0; i < shopList.size(); i++) {
//                elvCart.expandGroup(i);
//            }
//        }
//    };
//    private OsUser user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View homeView = inflater.inflate(R.layout.cart_fragment_main, container, false);

        activity = getActivity();

        elvCart = homeView.findViewById(R.id.elv_cart);
        cartAdapter = new CartAdapter(this.getContext(), shopList, cartMap);
        cartAdapter.setOnShopItemSelectedListener((isChecked, shopPos) -> {
            List<CartGoodsModel> list = cartMap.get(shopList.get(shopPos));
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setChecked(isChecked);
                    cartAdapter.notifyDataSetChanged();
                }
            }
        });
        elvCart.setAdapter(cartAdapter);

        cbChooseAll = homeView.findViewById(R.id.cb_cart_choose_all);
        cbChooseAll.setOnClickListener(e -> {
            cartMap.forEach((cartShopModel, cartGoodsModels) -> {
                cartShopModel.setChecked(isChooseAll);
                for (CartGoodsModel goods : cartGoodsModels)
                    goods.setChecked(isChooseAll);
            });
            isChooseAll = !isChooseAll;
            cartAdapter.notifyDataSetChanged();
        });
        return homeView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initCartData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


//        MyApplication application = (MyApplication) this.getActivity().getApplicationContext();
//        user = application.getUser();

    }

    private void initCartData() {
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
}