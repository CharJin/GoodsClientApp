package top.charjin.shoppingclient.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.order_list_item.view.*
import top.charjin.shoppingclient.R
import top.charjin.shoppingclient.activity.OrderDetailActivity
import top.charjin.shoppingclient.activity.ShopActivity
import top.charjin.shoppingclient.model.OsOrderModel
import top.charjin.shoppingclient.view.OrderGoodsView

class OrderAdapter(val context: Context, val orderList: List<OsOrderModel>) : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {


    var isAllOrder: Boolean = false

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.order_list_item, viewGroup, false))
    }

    @SuppressLint("DefaultLocale")
    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        val order = orderList[i]
        val shop = order.shop
        holder.tvShopName.text = shop.shopName

//        Glide.with(context).load("").into(holder.ivShopType)  //商品类型不同,显示的前缀图标也不错 暂不修改
//        订单状态暂不显示
//        holder.tvOrderStatus.setText(String.format("%s", order.orderStatus));

        holder.llShopMain.removeAllViews()

        order.orderDetailList.forEach {
            holder.llShopMain.addView(OrderGoodsView(context, it))
        }

        holder.tvGoodsNum.text = context.resources.getString(R.string.order_goods_item_goods_num, order.orderDetailList.size)

        holder.tvGoodsAmount.text = String.format("%.2f", order.orderAmountActual)

        if (isAllOrder) {
            holder.tvOrderStatus.visibility = View.VISIBLE
            when (order.orderStatus) {
                -1 -> holder.tvOrderStatus.text = "订单已取消"
                0 -> holder.tvOrderStatus.text = "未支付"
                1 -> holder.tvOrderStatus.text = "待发货"
                2 -> holder.tvOrderStatus.text = "待收货"
                3 -> holder.tvOrderStatus.text = "待评论"
            }

        } else
            holder.tvOrderStatus.visibility = View.GONE

        // 点击头部 跳转到店铺
        holder.llHeaderShop.setOnClickListener {
            val intent = Intent(context, ShopActivity::class.java)
//            val shop = OsShop()
//            shop.shopId = order.shop.shopId
//            shop.shopName = order.shop.shopName
            intent.putExtra("shop", shop)
            context.startActivity(intent)
        }

        // 点击商品部分 跳转至订单详情页
        holder.llShopMain.setOnClickListener {
            val intent = Intent(context, OrderDetailActivity::class.java)
            intent.putExtra("order", order)
            context.startActivity(intent)

//            传递orderList至详情页面
//            intent.putExtra("goodsId", order.shop.shopId)
//            context.startActivity(intent)
        }
        //        holder.ivGoods.setText(order);
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val llHeaderShop = view.ll_order_list_item_header_shop
        val ivShopType = view.iv_order_list_item_type
        val tvShopName = view.tv_order_list_item_shop_name
        val tvOrderStatus = view.tv_order_list_item_order_status

        val llShopMain = view.ll_order_list_item_goods

        val tvGoodsNum = view.tv_order_list_item_goods_all_num      // 商品的总件数
        val tvGoodsAmount = view.tv_order_list_item_goods_amount
    }
}
