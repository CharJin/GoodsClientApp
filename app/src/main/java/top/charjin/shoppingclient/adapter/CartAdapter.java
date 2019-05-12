package top.charjin.shoppingclient.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.List;
import java.util.Map;

import top.charjin.shoppingclient.model.CommodityModel;
import top.charjin.shoppingclient.model.ShopModel;

public class CartAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<ShopModel> listShop;
    private Map<ShopModel, List<CommodityModel>> mapCart;

    public CartAdapter(Context context, List<ShopModel> listShop, Map<ShopModel, List<CommodityModel>> mapCart) {
        this.context = context;
        this.listShop = listShop;
        this.mapCart = mapCart;
    }

    // group的数量 购物车中商店的数量
    @Override
    public int getGroupCount() {
        return this.listShop.size();
    }

    // 每个店铺中 商品的数量
    @Override
    public int getChildrenCount(int groupPosition) {
        return this.mapCart.get(this.listShop.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listShop.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
