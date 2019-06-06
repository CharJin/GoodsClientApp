package top.charjin.shoppingclient.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.order_submit_list_item.view.*
import top.charjin.shoppingclient.R
import top.charjin.shoppingclient.model.PreOrderGoodsModel


class PreOrderAdapter(val context: Context, val data: List<PreOrderGoodsModel>) : RecyclerView.Adapter<PreOrderAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.order_submit_list_item, viewGroup, false))

    override fun getItemCount(): Int = data.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val preOrder = data[position]
//        holder.ivShopType
//        holder.ivGoods.text =  preOrder

        holder.tvShopName.text = preOrder.shopName
        holder.tvGoodsName.text = preOrder.name
        holder.tvPlan.text = context.resources.getString(R.string.preorder_list_item_goods_plan, "默认套餐")
        holder.tvGoodsPrice.text = String.format("%.2f", preOrder.price)
        holder.tvGoodsNum.text = context.resources.getString(R.string.preorder_list_item_goods_num, preOrder.goodsNum)
        holder.tvSendType.text = "普通配送"
        holder.tvOrderSum.text = String.format("%.2f", preOrder.price * preOrder.goodsNum)
        holder.tvBtnSendType.setOnClickListener { }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivShopType = view.iv_order_submit_list_item_type
        val tvShopName = view.tv_order_submit_list_item_shop_name
        val ivGoods = view.iv_order_submit_list_item_goods
        val tvGoodsName = view.tv_order_submit_list_item_goods_name
        val tvPlan = view.tv_order_submit_list_item_plan
        val tvGoodsPrice = view.tv_order_submit_list_item_price
        val tvGoodsNum = view.tv_order_submit_list_item_goods_num
        val tvSendType = view.tv_order_submit_list_item_send_type_value
        val tvBtnSendType = view.tv_btn_order_submit_list_item_send_type
        val etOrderRemark = view.et_order_submit_list_item_remark
        val tvOrderSum = view.tv_order_submit_list_item_sum
    }

}

