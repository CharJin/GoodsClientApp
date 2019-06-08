package top.charjin.shoppingclient.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import top.charjin.shoppingclient.R

class OrderDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.order_detail_activity_main)
/*
        val list = listOf(OrderGoodsView(this), OrderGoodsView(this), OrderGoodsView(this),
                OrderGoodsView(this), OrderGoodsView(this), OrderGoodsView(this), OrderGoodsView(this),
                OrderGoodsView(this), OrderGoodsView(this), OrderGoodsView(this), OrderGoodsView(this))

        this.ll_order_detail_goods_container.removeAllViews()
        list.forEach(this.ll_order_detail_goods_container::addView)*/
    }


    fun finishOnClick(view: View) = finish()

}
