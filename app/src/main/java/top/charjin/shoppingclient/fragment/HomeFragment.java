package top.charjin.shoppingclient.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import top.charjin.shoppingclient.R;
import top.charjin.shoppingclient.adapter.NormalAdapter;

public class HomeFragment extends Fragment {

    boolean is = false;
    private RecyclerView lv_goods;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view_home = inflater.inflate(R.layout.home_fragment_main, container, false);
        lv_goods = view_home.findViewById(R.id.lv_home_goods);

        List<String> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add("index:  " + i);
        }
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Objects.requireNonNull(this.getContext()), android.R.layout.simple_list_item_1, data);
        NormalAdapter adapter = new NormalAdapter(data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Objects.requireNonNull(container).getContext());
        lv_goods.setLayoutManager(linearLayoutManager);
        lv_goods.setAdapter(adapter);


        return view_home;
    }


}