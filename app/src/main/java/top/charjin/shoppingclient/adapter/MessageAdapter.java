package top.charjin.shoppingclient.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import top.charjin.shoppingclient.R;
import top.charjin.shoppingclient.model.MessageModel;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private List<MessageModel> data;

    public MessageAdapter(List<MessageModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MessageModel message = data.get(position);
        holder.tvShopTitle.setText(message.getShopTitle());
        holder.tvLatestMsg.setText(message.getLatestMsg());
//        Glide.with(context).load(R.drawable.background).into(holder.civShopPic);
//        Glide.with(context).load(message.getShopPic()).into(holder.civShopPic);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView civShopPic;
        TextView tvShopTitle;
        TextView tvLatestMsg;

        ViewHolder(@NonNull View view) {
            super(view);
            civShopPic = view.findViewById(R.id.iv_list_item_message_shop_img);
            tvShopTitle = view.findViewById(R.id.tv_list_item_message_title);
            tvLatestMsg = view.findViewById(R.id.tv_list_item_message_latest);
        }
    }


}
