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

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import top.charjin.shoppingclient.R;
import top.charjin.shoppingclient.ShoppingApplication;
import top.charjin.shoppingclient.activity.LoginActivity;
import top.charjin.shoppingclient.activity.OrderActivity;
import top.charjin.shoppingclient.activity.UserInfoActivity;
import top.charjin.shoppingclient.entity.OsUser;

public class ProfileFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout rlMyOrder;
    private LinearLayout llOrderObligation, llWaitReceiving, llOrderComment;
    private TextView tvUserName;
    private CircleImageView civHeaderPortrait;
    private View homeView;
    private RelativeLayout rlUser;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeView = inflater.inflate(R.layout.profile_fragment_main, container, false);
        intiComponent();


//        rlUser.setOnClickListener(e -> startActivity(new Intent(getContext(), LoginActivity.class)));
        rlUser.setOnClickListener(e -> {
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
//
//        tvBtnAllOrder = homeView.findViewById(R.id.tv_btn_order_all);
//        tvBtnAllOrder.setOnClickListener(v -> startActivity(new Intent(context, OrderActivity.class)));

        llOrderObligation.setOnClickListener(this);
        llWaitReceiving.setOnClickListener(this);
        llOrderComment.setOnClickListener(this);


        return homeView;
    }


    private void intiComponent() {
        tvUserName = homeView.findViewById(R.id.tv_user_name);
        rlUser = homeView.findViewById(R.id.rl_profile_top);
        civHeaderPortrait = homeView.findViewById(R.id.civ_user_head_portrait);
        rlMyOrder = homeView.findViewById(R.id.rl_profile_my_order);
        llOrderObligation = homeView.findViewById(R.id.ll_shop_order_obligation);
        llWaitReceiving = homeView.findViewById(R.id.ll_shop_order_wait_receiving);
        llOrderComment = homeView.findViewById(R.id.ll_shop_order_comment);
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
            tvUserName.setText("用户未登录");
            Glide.with(this)
                    .load(R.drawable.background)
                    .centerCrop()
                    .into(civHeaderPortrait);
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
}