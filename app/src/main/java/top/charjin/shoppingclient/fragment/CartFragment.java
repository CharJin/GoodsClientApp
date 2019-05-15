package top.charjin.shoppingclient.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import top.charjin.shoppingclient.adapter.CartAdapter;
import top.charjin.shoppingclient.entity.OsGoods;
import top.charjin.shoppingclient.entity.OsShop;
import top.charjin.shoppingclient.entity.OsUser;
import top.charjin.shoppingclient.utils.HttpUtil;
import top.charjin.shoppingclient.utils.Router;

public class CartFragment extends Fragment {

    View homeView;

    private ExpandableListView elvCart;
    private CartAdapter cartAdapter;
    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            elvCart.setAdapter(cartAdapter);
        }
    };
    private List<OsShop> shopList = new ArrayList<>();
    private Map<OsShop, List<OsGoods>> cartMap = new HashMap<>();
    private OsUser user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View homeView = inflater.inflate(R.layout.cart_fragment_main, container, false);
        return homeView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


//        MyApplication application = (MyApplication) this.getActivity().getApplicationContext();
//        user = application.getUser();

        System.out.println(homeView);
        elvCart = homeView.findViewById(R.id.elv_cart);

        initCartData();

    }

    private void initCartData() {
        HttpUtil.sendOkHttpRequestByGet(Router.BASE_URL + "cart/query-cart?id=" + 1, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(this.getClass().getName(), e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonData = response.body().string();

                Gson gson = new Gson();
                try {
                    JSONArray jsonArray = new JSONArray(jsonData);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        OsShop shop = gson.fromJson(jsonObject.getJSONObject("shop").toString(), OsShop.class);
                        shopList.add(shop);

                        List<OsGoods> goodsList = gson.fromJson(
                                jsonObject.getJSONArray("listGoods").toString(), new TypeToken<List<OsGoods>>() {
                                }.getType());
                        cartMap.put(shop, goodsList);

                    }

                    handler.sendEmptyMessage(1);
                    cartAdapter = new CartAdapter(CartFragment.this.getContext(), shopList, cartMap);
//                    elvCart.setAdapter(cartAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}