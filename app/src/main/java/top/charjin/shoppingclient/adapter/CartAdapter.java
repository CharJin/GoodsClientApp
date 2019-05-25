package top.charjin.shoppingclient.adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.charjin.shoppingclient.R;
import top.charjin.shoppingclient.activity.GoodsActivity;
import top.charjin.shoppingclient.model.CartGoodsModel;
import top.charjin.shoppingclient.model.CartShopModel;
import top.charjin.shoppingclient.utils.HttpUtil;
import top.charjin.shoppingclient.utils.Router;

public class CartAdapter extends BaseExpandableListAdapter implements Callback {

    private Context context;
    private List<CartShopModel> shopList;
    private Map<CartShopModel, List<CartGoodsModel>> cartMap;
    private List<Integer> chosenGoodsIdList = new ArrayList<>();
    private int chosenNum = 0;


    private OnItemSelectedListener onItemSelectedListener;
    private OnCartGoodsChangedListener onCartGoodsChangedListener;

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
        Log.e("Cart", "fdsafdsa");
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
        holder.cbShopChoose.setOnClickListener(v -> {
            shop.setChecked(holder.cbShopChoose.isChecked());
            onItemSelectedListener.onShopItemSelected(holder.cbShopChoose.isChecked(), groupPosition);
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
        holder.tvGoodsName.setText(goods.getName());
        holder.tvGoodsNum.setText(String.format("%d", goods.getGoodsNum()));    //初始化商品的数量

        holder.cbChoose.setChecked(goods.isChecked());
        view.setOnClickListener(e -> {
            Intent intent = new Intent(context, GoodsActivity.class);
            intent.putExtra("goods", goods);
            context.startActivity(intent);
        });
        // 设置商品数量增减
        holder.btnPlus.setOnClickListener(e -> {
            int num = Integer.parseInt(holder.tvGoodsNum.getText().toString()), newNum = num + 1;

            HttpUtil.sendOkHttpRequestByGet(Router.CART_URL + "addGoodsNum?userId=" + 1 + "&goodsId=" + goods.getId() + "&number=" + newNum, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    onCartGoodsChangedListener.onCartGoodsChanged(false);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    int affectedNum = 0;
                    if (response.body() != null) {
                        affectedNum = Integer.parseInt(response.body().string());
                    }
                    // 修改成功执行回调函数
                    if (affectedNum > 0) {
                        goods.setGoodsNum(newNum);
                        ((Activity) context).runOnUiThread(() -> {
                            holder.tvGoodsNum.setText(String.format("%d", newNum));
                            onCartGoodsChangedListener.onCartGoodsChanged(true);
                        });
                    }
                }
            });
        });
        // 减少商品数量
        holder.btnSubtract.setOnClickListener(e -> {
            int num = Integer.parseInt(holder.tvGoodsNum.getText().toString()), newNum = num - 1;
            if (newNum == 0) {
                Toast.makeText(context, "商品数量不能再少啦!", Toast.LENGTH_SHORT).show();
                return;
            }
            HttpUtil.sendOkHttpRequestByGet(Router.CART_URL + "addGoodsNum?userId=" + 1 + "&goodsId=" + goods.getId() + "&number=" + newNum, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    onCartGoodsChangedListener.onCartGoodsChanged(false);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    int affectedNum = 0;
                    if (response.body() != null) {
                        affectedNum = Integer.parseInt(response.body().string());
                        onCartGoodsChangedListener.onCartGoodsChanged(false);
                    }
                    // 修改成功执行回调函数
                    if (affectedNum > 0) {
                        goods.setGoodsNum(newNum);
                        ((Activity) context).runOnUiThread(() -> {
                            holder.tvGoodsNum.setText(String.format("%d", newNum));
                            onCartGoodsChangedListener.onCartGoodsChanged(true);
                        });
                    }
                }
            });
        });
        holder.cbChoose.setOnClickListener(v -> {
            goods.setChecked(holder.cbChoose.isChecked());
            onItemSelectedListener.onGoodsItemSelected(holder.cbChoose.isChecked(), groupPosition, childPosition);
        });
        holder.tvGoodsPrice.setText(String.format("%s", goods.getPrice()));

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    public OnItemSelectedListener getOnItemSelectedListener() {
        return onItemSelectedListener;
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public OnCartGoodsChangedListener getOnCartGoodsChangedListener() {
        return onCartGoodsChangedListener;
    }

    public void setOnCartGoodsChangedListener(OnCartGoodsChangedListener onCartGoodsChangedListener) {
        this.onCartGoodsChangedListener = onCartGoodsChangedListener;
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {

    }

    public enum GoodsOperatorType {
        GOODS_ADD,
        GOODS_SUBTRACT
    }

    public interface OnItemSelectedListener {
        void onShopItemSelected(boolean isChecked, int shopPos);

        void onGoodsItemSelected(boolean isChecked, int shopPos, int goodsPos);
    }

    public interface OnCartGoodsChangedListener {
        void onCartGoodsChanged(boolean isSucceeded);
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
