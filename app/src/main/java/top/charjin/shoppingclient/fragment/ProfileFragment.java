package top.charjin.shoppingclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.charjin.shoppingclient.R;
import top.charjin.shoppingclient.ShoppingApplication;
import top.charjin.shoppingclient.activity.LoginActivity;
import top.charjin.shoppingclient.activity.OrderActivity;
import top.charjin.shoppingclient.activity.UserInfoActivity;
import top.charjin.shoppingclient.adapter.GoodsDisplayAdapter;
import top.charjin.shoppingclient.entity.OsGoods;
import top.charjin.shoppingclient.entity.OsUser;
import top.charjin.shoppingclient.utils.HttpUtil;
import top.charjin.shoppingclient.utils.JsonUtil;
import top.charjin.shoppingclient.utils.Router;

public class ProfileFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout rlMyOrder;
    private LinearLayout llOrderObligation, llWaitReceiving, llOrderComment;
    private TextView tvUserName;
    private CircleImageView civHeaderPortrait;
    private View homeView;
    private ConstraintLayout clUser;
    private LinearLayout llGoodsDisplay;
    private RecyclerView rvGoodsDisplay;


    private GoodsDisplayAdapter goodsAdapter;
    private List<OsGoods> goodsList = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.profile_fragment_main;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        homeView = super.onCreateView(inflater, container, savedInstanceState);
        homeView = LayoutInflater.from(context).inflate(R.layout.profile_fragment_main, container, false);


        intiComponent();

        llGoodsDisplay.setVisibility(View.GONE);    // 去除猜你喜欢字样

        clUser.setOnClickListener(e -> {
            if (activity.userLoginIntercept(context))
                startActivity(new Intent(getContext(), UserInfoActivity.class));
        });
        rlMyOrder.setOnClickListener(v -> {
            if (activity.userLoginIntercept(context)) {
                Intent intent = new Intent(context, OrderActivity.class);
                intent.putExtra("orderType", OrderActivity.OrderType.All);
                startActivity(intent);
            }
        });


        llOrderObligation.setOnClickListener(this);
        llWaitReceiving.setOnClickListener(this);
        llOrderComment.setOnClickListener(this);


        goodsAdapter = new GoodsDisplayAdapter(context, goodsList);
        rvGoodsDisplay.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rvGoodsDisplay.setAdapter(goodsAdapter);

        initGoodsDisplayData();
        LinearLayout llTool = homeView.findViewById(R.id.ll_profile_tools);
        llTool.setOnClickListener(v -> toast("未开发"));

        return homeView;
    }


    private void intiComponent() {
        tvUserName = homeView.findViewById(R.id.tv_profile_user_name);
        clUser = homeView.findViewById(R.id.rl_profile_top);
        civHeaderPortrait = homeView.findViewById(R.id.civ_user_head_portrait);
        rlMyOrder = homeView.findViewById(R.id.rl_profile_my_order);
        llOrderObligation = homeView.findViewById(R.id.ll_shop_order_obligation);
        llWaitReceiving = homeView.findViewById(R.id.ll_shop_order_wait_receiving);
        llOrderComment = homeView.findViewById(R.id.ll_shop_order_comment);
        llGoodsDisplay = homeView.findViewById(R.id.ll_goods_display_header);
        rvGoodsDisplay = homeView.findViewById(R.id.rv_goods_display);
    }


    /**
     * 初始化header注释
     */
    @Override
    public void onResume() {
        super.onResume();
        OsUser user = ShoppingApplication.getUser();
        if (user != null) {
            tvUserName.setText(user.getUsername());
            Glide.with(this)
                    .load(user.getHeadPortrait())
                    .centerCrop()
                    .into(civHeaderPortrait);
        } else {
            tvUserName.setText(context.getResources().getText(R.string.profile_user_name));
//            Glide.with(this)
//                    .load("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=120606963,1622152554&fm=26&gp=0.jpg")
//                    .centerCrop()
//                    .into(civHeaderPortrait);
        }
    }

    @Override
    public void onClick(View v) {
        if (ShoppingApplication.getUser() == null) {
            startActivity(new Intent(context, LoginActivity.class));
            return;
        }
        int id = v.getId();
        Intent intent = new Intent(context, OrderActivity.class);
        switch (id) {
            case R.id.ll_shop_order_obligation:
                intent.putExtra("orderType", OrderActivity.OrderType.OBLIGATION);
                break;
            case R.id.ll_shop_order_wait_receiving:
                intent.putExtra("orderType", OrderActivity.OrderType.WAIT_RECEIVING);
                break;
            case R.id.ll_shop_order_comment:
                intent.putExtra("orderType", OrderActivity.OrderType.WAIT_COMMENT);
                break;
        }
        startActivity(intent);
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