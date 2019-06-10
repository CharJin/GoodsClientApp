package top.charjin.shoppingclient.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.shop_goods_list_item.view.*
import top.charjin.shoppingclient.R
import top.charjin.shoppingclient.activity.GoodsActivity
import top.charjin.shoppingclient.entity.OsGoods


class ShopGoodsAdapter(private var context: Context, var goodsList: List<OsGoods>) :
        RecyclerView.Adapter<ShopGoodsAdapter.ViewHolder>() {

    lateinit var onCartClickListener: OnCartClickListener

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.shop_goods_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val goods = this.goodsList[position]

//        val tf = Typeface.createFromAsset(context!!.assets, "fonts/simhei.ttf")
        holder.tvGoodsName.text = goods.goodsName
//        holder.tvGoodsName.typeface = tf
        holder.tvGoodsPrice.text = String.format("%.2f", goods.price)
        holder.tvService.text = "包邮"
        holder.tvSaleVolume.text = String.format(context.resources.getString(R.string.shop_goods_sale_volume), goods.salesVolume)
        Glide.with(context).load(R.drawable.background).into(holder.ivGoods)

        holder.itemView.setOnClickListener {
            val intent = Intent(this.context, GoodsActivity::class.java)
            intent.putExtra("goods", goods)
            context.startActivity(intent)
        }

        holder.ivCart.setOnClickListener {
            onCartClickListener.onCartClick(goods)
        }
    }

    override fun getItemCount(): Int {
        return goodsList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val ivGoods: ImageView = view.iv_shop_goods
        val tvGoodsName: TextView = view.tv_shop_goods_name
        val tvGoodsPrice: TextView = view.tv_shop_goods_price
        val tvService: TextView = view.tv_shop_goods_service
        val tvSaleVolume: TextView = view.tv_shop_goods_sale_volume
        val ivCart: ImageView = view.iv_shop_goods_cart
    }

    interface OnCartClickListener {
        fun onCartClick(goods: OsGoods)
    }
}