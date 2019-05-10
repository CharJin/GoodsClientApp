package top.charjin.shoppingclient.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import top.charjin.shoppingclient.R;
import top.charjin.shoppingclient.adapter.MessageAdapter;
import top.charjin.shoppingclient.model.MessageModel;

public class MessageFragment extends Fragment {
    private View viewMessage;

    private RecyclerView rvMessage;
    private MessageAdapter adapter;
    private List<MessageModel> data;
    private SwipeRefreshLayout srlMessage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewMessage = inflater.inflate(R.layout.message_fragment_main, container, false);

        // RecyclerView
        rvMessage = viewMessage.findViewById(R.id.rv_message);
        rvMessage.setLayoutManager(new LinearLayoutManager(container.getContext()));

        data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add(new MessageModel("", "这是标题" + i, "[最新消息]"));
        }
        adapter = new MessageAdapter(data);

        rvMessage.addItemDecoration(new DividerItemDecoration(container.getContext(), DividerItemDecoration.VERTICAL));
        rvMessage.setAdapter(adapter);


        // SwipeRefreshLayout
        srlMessage = viewMessage.findViewById(R.id.swipe_layout_message);
        srlMessage.setColorSchemeColors(getResources().getColor(R.color.app_swipe_circle_color));
        srlMessage.setOnRefreshListener(this::refreshMsg);

        return viewMessage;
    }

    private void refreshMsg() {
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            getActivity().runOnUiThread(() -> {
//                adapter.notifyDataSetChanged();
//                srlMessage.setRefreshing(false);
//            });
            srlMessage.setRefreshing(false);
        }
        ).start();
    }
}