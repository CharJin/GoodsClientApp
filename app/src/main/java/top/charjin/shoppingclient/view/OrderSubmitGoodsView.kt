package top.charjin.shoppingclient.view

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.order_submit_include_goods_item.view.*
import top.charjin.shoppingclient.R
import top.charjin.shoppingclient.model.CartGoodsModel

class OrderSubmitGoodsView(context: Context, val goods: CartGoodsModel) : ConstraintLayout(context) {

    init {
        LayoutInflater.from(context).inflate(R.layout.order_submit_include_goods_item, this)


//        Glide.with(iv_order_submit_goods).load(goods.image)
//        iv_order_submit_goods = goods.
        tv_order_submit_goods_name.text = goods.name
        tv_order_submit_plan.text = context.resources.getString(R.string.preorder_list_item_goods_plan, "默认套餐")
        tv_order_submit_price.text = String.format("%.2f", goods.price)
        tv_order_submit_goods_num.text = context.resources.getString(R.string.preorder_list_item_goods_num, goods.goodsNum)


    }

}