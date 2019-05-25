package top.charjin.shoppingclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import top.charjin.shoppingclient.R;
import top.charjin.shoppingclient.activity.LoginActivity;
import top.charjin.shoppingclient.activity.OrderActivity;

public class ProfileFragment extends BaseFragment {

    TextView tvBtnAllOrder;
    private View homeView;
    private RelativeLayout rlUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeView = inflater.inflate(R.layout.profile_fragment_main, container, false);

        rlUser = homeView.findViewById(R.id.rl_profile_top);
        rlUser.setOnClickListener(e -> startActivity(new Intent(getContext(), LoginActivity.class)));
//        CardView cardView

        tvBtnAllOrder = homeView.findViewById(R.id.tv_btn_order_all);
        tvBtnAllOrder.setOnClickListener(v -> {
            startActivity(new Intent(context, OrderActivity.class));
        });

        return homeView;
    }
}