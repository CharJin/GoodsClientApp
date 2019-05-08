package top.charjin.shoppingclient.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import top.charjin.shoppingclient.R;

// ① 创建Adapter
public class NormalAdapter extends RecyclerView.Adapter<NormalAdapter.ViewHolder> {
    private List<String> mDatas;

    public NormalAdapter(List<String> data) {
        this.mDatas = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull android.view.ViewGroup viewGroup, int i) {
        //LayoutInflater.from指定写法
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_1, viewGroup, false);
        return new ViewHolder(v);
    }

    //③ 在Adapter中实现3个方法
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(mDatas.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //item 点击事件
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    //② 创建ViewHolder
    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;

        ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title_view);
        }
    }

}