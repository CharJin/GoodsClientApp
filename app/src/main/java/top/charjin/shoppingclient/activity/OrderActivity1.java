package top.charjin.shoppingclient.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.charjin.shoppingclient.R;
import top.charjin.shoppingclient.adapter.OrderAdapter;
import top.charjin.shoppingclient.model.OsOrderModel;
import top.charjin.shoppingclient.utils.HttpUtil;
import top.charjin.shoppingclient.utils.JsonUtil;
import top.charjin.shoppingclient.utils.Router;

public class OrderActivity1 extends AppCompatActivity implements TabLayout.BaseOnTabSelectedListener {


    private TabLayout tlOrder;
    private ViewPager vpOrder;

    private RecyclerView rvOrderCommon;
    private OrderAdapter adapter;
    private List<OsOrderModel> orderList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity_main);
        initComponent();

        adapter = new OrderAdapter(this, orderList);
        rvOrderCommon.setLayoutManager(new LinearLayoutManager(this));
        rvOrderCommon.setAdapter(adapter);


        tlOrder.addOnTabSelectedListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        initOrderData();
    }

    private void initOrderData() {
        HttpUtil.sendOkHttpRequestByGet(Router.ORDER_URL + "getAllOrdersByUserId?userId=" + 1, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonData = response.body().string();
                Log.e("Order", jsonData);
                orderList.addAll(JsonUtil.parseJSONObjectInStringToEntityList(jsonData, OsOrderModel.class));
                runOnUiThread(() -> adapter.notifyDataSetChanged());
            }
        });
    }

    private void initComponent() {
        tlOrder = findViewById(R.id.tl_order_header);
        rvOrderCommon = findViewById(R.id.rv_order_common);
    }

    public void finishOnClick(View view) {
        finish();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        orderList.stream().filter(order -> order.getOrderStatus() == 0);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

}
