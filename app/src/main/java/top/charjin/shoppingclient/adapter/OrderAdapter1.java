package top.charjin.shoppingclient.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import top.charjin.shoppingclient.R;
import top.charjin.shoppingclient.model.OsOrderModel;

public class OrderAdapter1 extends RecyclerView.Adapter<OrderAdapter1.ViewHolder> {

    private Context context;
    private List<OsOrderModel> orderList;

    public OrderAdapter1(Context context, List<OsOrderModel> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.order_list_item, viewGroup, false));
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        OsOrderModel order = orderList.get(i);
 /*       holder.tvShopName.setText(order.getShopName());
        holder.tvOrderStatus.setText(String.format("%s", order.getOrderStatus()));
        holder.tvGoodsName.setText(order.getGoodsName());
        holder.tvGoodsPrice.setText(String.format("%s", order.getPrice()));
        holder.tvGoodsNum.setText(String.format("x%d", order.getGoodsNumber()));
        holder.tvOrderAmount.setText(String.format("%s", order.getOrderAmount()));

        holder.clOrderHeader.setOnClickListener(v -> {
            Intent intent = new Intent(context, ShopActivity.class);
            OsShop shop = new OsShop();
            shop.setShopId(order.getShopId());
            shop.setName(order.getShopName());
            intent.putExtra("shop", shop);
            context.startActivity(intent);
        });

        holder.clOrderMain.setOnClickListener(v -> {
            Intent intent = new Intent(context, GoodsActivity.class);

            intent.putExtra("goodsId", order.getShopId());
            context.startActivity(intent);
        });*/
//        holder.ivGoods.setText(order);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout clOrderHeader, clOrderMain;

        TextView tvShopName;
        TextView tvOrderStatus;

        TextView tvGoodsName;
        TextView tvGoodsPrice;
        TextView tvGoodsNum;
        TextView tvOrderAmount;

        ImageView ivGoods;


        ViewHolder(@NonNull View view) {
            super(view);
//            clOrderHeader = view.findViewById(R.id.cl_order_header);
//            clOrderMain = view.findViewById(R.id.cl_order_main);
//
//            tvShopName = view.findViewById(R.id.tv_order_shop_name);
//            tvOrderStatus = view.findViewById(R.id.tv_order_header_status);
//
//            tvGoodsName = view.findViewById(R.id.tv_order_goods_name);
//            tvGoodsPrice = view.findViewById(R.id.tv_order_goods_price);
//            tvGoodsNum = view.findViewById(R.id.tv_order_goods_num);
//            tvOrderAmount = view.findViewById(R.id.tv_order_bottom_amount);

            ivGoods = view.findViewById(R.id.iv_order_goods);
        }
    }
}
