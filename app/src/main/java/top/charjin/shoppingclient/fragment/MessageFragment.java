package top.charjin.shoppingclient.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

public class MessageFragment extends BaseFragment {
    private View viewMessage;

    private RecyclerView rvMessage;
    private MessageAdapter adapter;
    private SwipeRefreshLayout srlMessage;
    private List<MessageModel> data = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.message_fragment_main;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        viewMessage = super.onCreateView(inflater, container, savedInstanceState);
        viewMessage = LayoutInflater.from(context).inflate(R.layout.message_fragment_main, container, false);

        // RecyclerView
        rvMessage = viewMessage.findViewById(R.id.rv_message);
        rvMessage.setLayoutManager(new LinearLayoutManager(container.getContext()));

        adapter = new MessageAdapter(data);

        rvMessage.addItemDecoration(new DividerItemDecoration(container.getContext(), DividerItemDecoration.VERTICAL));
        rvMessage.setAdapter(adapter);


        initMessageList();

        // SwipeRefreshLayout
        srlMessage = viewMessage.findViewById(R.id.swipe_layout_message);
        srlMessage.setColorSchemeColors(getResources().getColor(R.color.app_swipe_circle_color));
        srlMessage.setOnRefreshListener(this::refreshMsg);

        return viewMessage;
    }

    private void initMessageList() {
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                data.add(new MessageModel("", "这是标题" + i, "[最新消息]"));
            }
            activity.runOnUiThread(() -> adapter.notifyDataSetChanged());
        }).start();
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