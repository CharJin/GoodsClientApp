package top.charjin.shoppingclient.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import top.charjin.shoppingclient.R;
import top.charjin.shoppingclient.activity.GoodsActivity;
import top.charjin.shoppingclient.model.CartGoodsModel;
import top.charjin.shoppingclient.model.CartShopModel;

public class CartAdapter extends BaseExpandableListAdapter implements CompoundButton.OnCheckedChangeListener {

    private Context context;
    private List<CartShopModel> shopList;
    private Map<CartShopModel, List<CartGoodsModel>> cartMap;
    private List<Integer> chosenGoodsIdList = new ArrayList<>();
    private int chosenNum = 0;


    private OnShopItemSelectedListener onShopItemSelectedListener;

    public CartAdapter(Context context, List<CartShopModel> shopList, Map<CartShopModel, List<CartGoodsModel>> cartMap) {
        this.context = context;
        this.shopList = shopList;
        this.cartMap = cartMap;
    }

    // group的数量 购物车中商店的数量
    @Override
    public int getGroupCount() {
        return this.shopList.size();
    }

    // 每个店铺中 商品的数量
    @Override
    public int getChildrenCount(int groupPosition) {
        return Objects.requireNonNull(this.cartMap.get(this.shopList.get(groupPosition))).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.shopList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return Objects.requireNonNull(this.cartMap.get(this.shopList.get(groupPosition))).get(childPosition);
    }


    // id 实际就是指的位序
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * 父项: 店铺项
     *
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        CartShopModel shop = (CartShopModel) this.getGroup(groupPosition);
        View view;
        ViewHolderShop holder;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_item_shop_group, parent, false);
            holder = new ViewHolderShop();
            holder.cbShopChoose = view.findViewById(R.id.cb_cart_shop);
            holder.tvShopName = view.findViewById(R.id.tv_cart_shop_name);
            // action
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolderShop) view.getTag();
        }
        // property, bind event---
        holder.tvShopName.setText(shop.getName());
        holder.cbShopChoose.setChecked(shop.isChecked());
        holder.cbShopChoose.setOnCheckedChangeListener((buttonView, isChecked) -> {
            onShopItemSelectedListener.onItemSelected(buttonView.isChecked(), groupPosition);
//            Toast.makeText(context, buttonView.getText() + "  " + isChecked + "", Toast.LENGTH_SHORT).show();

        });

        return view;
    }

    /**
     * 子项: 即商品项
     *
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView
     * @param parent
     * @return
     */
    @SuppressLint("DefaultLocale")
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        CartGoodsModel goods = (CartGoodsModel) this.getChild(groupPosition, childPosition);
        View view;
        ViewHolderGoods holder;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_item_shop_child, parent, false);
            holder = new ViewHolderGoods();
            holder.cbChoose = view.findViewById(R.id.cb_cart_goods);
            holder.ivGoods = view.findViewById(R.id.iv_cart_goods_image);
            holder.tvGoodsName = view.findViewById(R.id.tv_cart_goods_name);
            holder.tvGoodsPlan = view.findViewById(R.id.tv_cart_goods_plan);
            holder.tvGoodsPrice = view.findViewById(R.id.tv_cart_goods_price);
            holder.tvGoodsNum = view.findViewById(R.id.tv_cart_goods_num);
            holder.btnSubtract = view.findViewById(R.id.btn_cart_subtract);
            holder.btnPlus = view.findViewById(R.id.btn_cart_plus);
            // action
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolderGoods) view.getTag();
        }
        // property, bind event---
        holder.cbChoose.setChecked(goods.isChecked());
        if (goods.isChecked()) chosenNum++;
        Toast.makeText(context, "selected", Toast.LENGTH_SHORT).show();
        holder.tvGoodsName.setText(goods.getName());
        holder.cbChoose.setChecked(goods.isChecked());
        view.setOnClickListener(e -> {
            Toast.makeText(context, "parent", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, GoodsActivity.class);
            intent.putExtra("goods", goods);
            context.startActivity(intent);
        });
        // 设置商品数量增减
        holder.btnPlus.setOnClickListener(e -> {
            int num = Integer.parseInt(holder.tvGoodsNum.getText().toString());
            holder.tvGoodsNum.setText(String.format("%d", num + 1));
        });
        holder.btnSubtract.setOnClickListener(e -> {
            int num = Integer.parseInt(holder.tvGoodsNum.getText().toString());
            if (num == 1) {
                Toast.makeText(context, "商品数量不能再少啦!", Toast.LENGTH_SHORT).show();
                return;
            }
            holder.tvGoodsNum.setText(String.format("%d", num - 1));
        });
        holder.cbChoose.setOnCheckedChangeListener(this);
        holder.tvGoodsPrice.setText(String.format("%s", goods.getPrice()));

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    }

    public OnShopItemSelectedListener getOnShopItemSelectedListener() {
        return onShopItemSelectedListener;
    }

    public void setOnShopItemSelectedListener(OnShopItemSelectedListener onShopItemSelectedListener) {
        this.onShopItemSelectedListener = onShopItemSelectedListener;
    }


    public interface OnShopItemSelectedListener {
        void onItemSelected(boolean isChecked, int shopPos);
    }

    class ViewHolderGoods {
        CheckBox cbChoose;
        ImageView ivGoods;
        TextView tvGoodsName;
        TextView tvGoodsPlan;
        TextView tvGoodsPrice;
        TextView tvGoodsNum;
        Button btnPlus;
        Button btnSubtract;
    }

    class ViewHolderShop {
        CheckBox cbShopChoose;
        TextView tvShopName;
    }
}
