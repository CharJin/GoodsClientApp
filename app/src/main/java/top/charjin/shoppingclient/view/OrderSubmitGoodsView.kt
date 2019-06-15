package top.charjin.shoppingclient.view

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.order_include_goods_item.view.*
import top.charjin.shoppingclient.R
import top.charjin.shoppingclient.model.CartGoodsModel


/**
 * 购物车设计稍复杂,改动涉及变动过多,此类暂不改变,其他与订单详情相关的商品显示 则做统一化
 */
class OrderSubmitGoodsView(context: Context, val goods: CartGoodsModel) : ConstraintLayout(context) {

    init {
        LayoutInflater.from(context).inflate(R.layout.order_include_goods_item, this)

        Glide.with(context).load(goods.image).into(iv_order_goods)
        tv_order_goods_name.text = goods.goodsName
        tv_order_mode.text = context.resources.getString(R.string.preorder_list_item_goods_plan, "默认套餐")
        tv_order_price.text = String.format("%.2f", goods.price)
        tv_order_goods_num.text = context.resources.getString(R.string.preorder_list_item_goods_num, goods.goodsNum)


    }

}