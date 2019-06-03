package top.charjin.shoppingclient.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.address_list_item.view.*
import top.charjin.shoppingclient.R
import top.charjin.shoppingclient.activity.AddressModifyActivity
import top.charjin.shoppingclient.entity.OsAddress


class AddressAdapter(val context: Context, val data: List<OsAddress>) : RecyclerView.Adapter<AddressAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder = ViewHolder(LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.address_list_item, viewGroup, false))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val address = data[position]
        holder.tvReceiver.text = address.receiver
        holder.tvReceiverPhone.text = address.phone
        holder.tvAddressDetail.text = String.format("%s%s%s %s", address.province, address.city, address.district, address.addressDetail)
        holder.tvIsDefault.visibility = if (address.isDefault) View.VISIBLE else View.GONE
        holder.tvBtnEdit.setOnClickListener {

            val intent = Intent(context, AddressModifyActivity::class.java)
            intent.putExtra("address", address)
            (context as Activity).startActivity(intent)
            //            Toast.makeText(context, "show", Toast.LENGTH_SHORT).show()

        }


    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvReceiver = view.tv_list_item_address_receiver!!
        val tvReceiverPhone = view.tv_list_item_address_phone!!
        val tvAddressDetail = view.tv_list_item_address_detail!!
        val tvIsDefault = view.tv_list_item_address_is_default!!
        val tvBtnEdit = view.tv_btn_list_item_address_edit!!

    }
}