package top.charjin.shoppingclient.view

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.order_include_goods_item.view.*
import top.charjin.shoppingclient.R
import top.charjin.shoppingclient.entity.OsOrderDetail

class OrderGoodsView(context: Context, orderDetailGoods: OsOrderDetail) : ConstraintLayout(context) {

    init {
        LayoutInflater.from(context).inflate(R.layout.order_include_goods_item, this)

        val goods = orderDetailGoods.goods

//        Glide.with(iv_order_submit_goods).load(goods.image)
//        iv_order_submit_goods = goods.
        tv_order_goods_name.text = goods.goodsName
        tv_order_mode.text = context.resources.getString(R.string.preorder_list_item_goods_plan, orderDetailGoods.goodsMode)
        tv_order_price.text = String.format("%.2f", goods.price)
        tv_order_goods_num.text = context.resources.getString(R.string.preorder_list_item_goods_num, orderDetailGoods.goodsNum)
    }

}