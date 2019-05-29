package top.charjin.shoppingclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import top.charjin.shoppingclient.R;
import top.charjin.shoppingclient.activity.LoginActivity;
import top.charjin.shoppingclient.activity.OrderActivity;

public class ProfileFragment extends BaseFragment implements View.OnClickListener {

    private TextView tvBtnAllOrder;
    private RelativeLayout rlMyOrder;
    private LinearLayout llOrderObligation, llWaitReceiving, llOrderComment;
    private View homeView;
    private RelativeLayout rlUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeView = inflater.inflate(R.layout.profile_fragment_main, container, false);
        intiComponent();

        rlUser.setOnClickListener(e -> startActivity(new Intent(getContext(), LoginActivity.class)));

        rlMyOrder.setOnClickListener(v -> {
            Intent intent = new Intent(context, OrderActivity.class);
            intent.putExtra("orderType", OrderActivity.OrderType.All);
            startActivity(intent);
        });
//
//        tvBtnAllOrder = homeView.findViewById(R.id.tv_btn_order_all);
//        tvBtnAllOrder.setOnClickListener(v -> startActivity(new Intent(context, OrderActivity.class)));

        llOrderObligation.setOnClickListener(this);
        llWaitReceiving.setOnClickListener(this);
        llOrderComment.setOnClickListener(this);


        return homeView;
    }


    private void intiComponent() {
        rlUser = homeView.findViewById(R.id.rl_profile_top);
        rlMyOrder = homeView.findViewById(R.id.rl_profile_my_order);
        llOrderObligation = homeView.findViewById(R.id.ll_shop_order_obligation);
        llWaitReceiving = homeView.findViewById(R.id.ll_shop_order_wait_receiving);
        llOrderComment = homeView.findViewById(R.id.ll_shop_order_comment);


    }

    @Override
    public void onClick(View v) {
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
}