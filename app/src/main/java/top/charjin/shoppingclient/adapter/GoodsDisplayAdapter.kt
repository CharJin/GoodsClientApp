package top.charjin.shoppingclient.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.goods_display_list_item.view.*
import top.charjin.shoppingclient.R
import top.charjin.shoppingclient.activity.GoodsActivity
import top.charjin.shoppingclient.entity.OsGoods

class GoodsDisplayAdapter(val context: Context, val goodsList: List<OsGoods>) : RecyclerView.Adapter<GoodsDisplayAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.goods_display_list_item, viewGroup, false))

    override fun getItemCount(): Int = goodsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val goods = goodsList[position]
        Glide.with(context).load(goods.image).into(holder.ivGoods)
        holder.tvGoodsName.text = goods.goodsName
        holder.tvPrice.text = String.format("%.0f", goods.price)
        holder.tvRegion.text = goods.region
        holder.tvSaleVolume.text = String.format(context.resources.getString(R.string.goods_display_sale_volume), goods.salesVolume)
        holder.itemView.setOnClickListener {
            Intent(this.context, GoodsActivity::class.java).apply {
                putExtra("goods", goods)
                context.startActivity(this)
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivGoods = view.iv_goods_display
        val tvGoodsName = view.tv_goods_display_title
        val tvPrice = view.tv_goods_display_price
        val tvRegion = view.tv_goods_display_region
        val tvSaleVolume = view.tv_goods_display_sale_volume
    }
}