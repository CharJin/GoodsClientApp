package top.charjin.shoppingclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import top.charjin.shoppingclient.R;
import top.charjin.shoppingclient.activity.LoginActivity;

public class ProfileFragment extends BaseFragment {

    private View homeView;
    private RelativeLayout rlUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeView = inflater.inflate(R.layout.profile_fragment_main, container, false);

        rlUser = homeView.findViewById(R.id.rl_profile_top);
        rlUser.setOnClickListener(e -> startActivity(new Intent(getContext(), LoginActivity.class)));
//        CardView cardView


        return homeView;
    }
}