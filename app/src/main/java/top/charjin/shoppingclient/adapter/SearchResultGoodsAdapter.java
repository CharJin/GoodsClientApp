package top.charjin.shoppingclient.adapter;


import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import top.charjin.shoppingclient.R;
import top.charjin.shoppingclient.entity.OsGoods;

public class SearchResultGoodsAdapter extends RecyclerView.Adapter<SearchResultGoodsAdapter.ViewHolder> {
    private List<OsGoods> data;
    private Context context;


    public SearchResultGoodsAdapter(List<OsGoods> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        if (context == null) {
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.home_list_item_goods, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OsGoods goods = this.data.get(position);

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/simhei.ttf");
        holder.tv_title.setText(goods.getName());
        holder.tv_title.setTypeface(tf);
        holder.tv_price.setText(goods.getPrice() + "");
        Glide.with(context).load(R.drawable.background).into(holder.iv_pic);

        holder.itemView.setOnClickListener(v -> {
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_pic;
        private TextView tv_title;
        private TextView tv_price;

        ViewHolder(View view) {
            super(view);
            iv_pic = view.findViewById(R.id.iv_list_item_home_goods_pic);
            tv_title = view.findViewById(R.id.tv_list_item_home_goods_title);
            tv_price = view.findViewById(R.id.tv_list_item_home_goods_price);
        }
    }


}