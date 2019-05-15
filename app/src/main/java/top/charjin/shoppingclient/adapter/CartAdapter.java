package top.charjin.shoppingclient.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import top.charjin.shoppingclient.R;
import top.charjin.shoppingclient.entity.OsCart;
import top.charjin.shoppingclient.entity.OsGoods;
import top.charjin.shoppingclient.entity.OsShop;

public class CartAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<OsShop> shopList;
    private Map<OsShop, List<OsGoods>> cartMap;

    public CartAdapter(Context context, List<OsShop> shopList, Map<OsShop, List<OsGoods>> cartMap) {
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
        return this.cartMap.get(this.shopList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.shopList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.cartMap.get(this.shopList.get(groupPosition)).get(childPosition);
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

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        OsShop shop = (OsShop) this.getGroup(groupPosition);
        View view;
        ViewHolderShop holder;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_item_shop_group, parent, false);
            holder = new ViewHolderShop();
            holder.cbAllChoose = view.findViewById(R.id.cb_cart_goods_choose_all);
            holder.tvShopName = view.findViewById(R.id.tv_cart_shop_name);
            // action
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolderShop) view.getTag();
        }
        // property, bind event---

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        OsCart cart = (OsCart) this.getChild(groupPosition, childPosition);
        View view;
        ViewHolderGoods holder;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_item_shop_child, parent, false);
            holder = new ViewHolderGoods();
            holder.cbChoose = view.findViewById(R.id.cb_cart_goods_choose);
            holder.ivGoods = view.findViewById(R.id.iv_cart_goods_image);
            holder.tvGoodsName = view.findViewById(R.id.tv_cart_goods_name);
            holder.tvGoodsPlan = view.findViewById(R.id.tv_cart_goods_plan);
            holder.tvGoodsPrice = view.findViewById(R.id.tv_cart_goods_price);
            holder.tvGoodsNum = view.findViewById(R.id.tv_cart_goods_num);
            holder.btnPlus = view.findViewById(R.id.btn_cart_plus);
            holder.btnSubtract = view.findViewById(R.id.btn_cart_subtract);
            // action
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolderGoods) view.getTag();
        }
        // property, bind event---

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class ViewHolderShop {
        CheckBox cbAllChoose;
        TextView tvShopName;
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
}
