package top.charjin.shoppingclient.view

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.view.LayoutInflater
import top.charjin.shoppingclient.R

class OrderDetailGoodsView(context: Context?) : ConstraintLayout(context) {



    init {
        LayoutInflater.from(context).inflate(R.layout.order_detail_include_goods_item, this)

    }

}