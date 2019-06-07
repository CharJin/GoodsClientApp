package top.charjin.shoppingclient.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.order_include_address.*
import kotlinx.android.synthetic.main.order_submit_activity_main.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import top.charjin.shoppingclient.R
import top.charjin.shoppingclient.adapter.PreOrderAdapter
import top.charjin.shoppingclient.entity.OsAddress
import top.charjin.shoppingclient.model.PreOrderGoodsModel
import top.charjin.shoppingclient.utils.HttpUtil
import top.charjin.shoppingclient.utils.JsonUtil
import top.charjin.shoppingclient.utils.Router
import java.io.IOException

class OrderSubmitActivity : AppCompatActivity() {

    private lateinit var address: OsAddress

    private val preOrderGoodsList = arrayListOf<PreOrderGoodsModel>()

    companion object {
        const val CHANGE_ADDRESS = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.order_submit_activity_main)
        initDefaultAddress()
        initPreOrderData()

    }

    /**
     * 初始化pre订单数据(商品信息)
     */
    private fun initPreOrderData() {
        val adapter = PreOrderAdapter(this, preOrderGoodsList)
        rv_order_submit_goods.layoutManager = LinearLayoutManager(this)
        rv_order_submit_goods.adapter = adapter

//        val goodsList: ArrayList<PreOrderGoodsModel> = intent.getSerializableExtra("goods") as ArrayList<PreOrderGoodsModel>
//        preOrderGoodsList.addAll(goodsList)
//        adapter.notifyDataSetChanged()

        val goodsList: ArrayList<PreOrderGoodsModel> = intent.getSerializableExtra("preOrderGoodsList") as ArrayList<PreOrderGoodsModel>

        preOrderGoodsList.clear()
        preOrderGoodsList.addAll(goodsList)


        /*
         *
         */
        var sum = 0.0
        preOrderGoodsList.forEach { preOrderModel ->
            preOrderModel.goodsList.forEach { sum += it.goodsNum * it.price }
        }

        this.tv_order_submit_sum.text = String.format("%.2f", sum)
    }


    /**
     * 获取默认地址, 若用户未设置默认地址, 则使用最近使用过或者修改过的地址
     * 逻辑由服务器端处理
     */
    private fun initDefaultAddress() {
        HttpUtil.sendOkHttpRequestByGet(Router.ADDRESS_URL + "getDefaultAddress?userId=1",
                object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        Toasty.error(this@OrderSubmitActivity, "地址数据请求错误!").show()
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val jsonData = response.body()!!.string()
                        address = JsonUtil.parseJSONObject(jsonData, OsAddress::class.java)
                        runOnUiThread {
                            tv_order_address_receiver.text = address.receiver
                            tv_order_address_phone.text = address.phone
                            tv_order_address_detail.text = String.format("%s%s%s %s",
                                    address.province, address.city, address.district, address.addressDetail)

                        }
                    }

                })
    }

    /**
     * 更换收货地址，转至地址页面
     */
    fun changeAddressOnClick(view: View) {
        val intent = Intent(this, AddressActivity::class.java)
        intent.putExtra("flag", CHANGE_ADDRESS)
        startActivityForResult(intent, CHANGE_ADDRESS)
    }


    /**
     * 根据从地址页面返回的address可序列化对象 重新加载地址数据
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CHANGE_ADDRESS && resultCode == Activity.RESULT_OK) {
            address = data!!.getSerializableExtra("address") as OsAddress
            tv_order_address_receiver.text = address.receiver
            tv_order_address_phone.text = address.phone
            tv_order_address_detail.text = String.format("%s%s%s %s",
                    address.province, address.city, address.district, address.addressDetail)

        }
    }

    fun submitOrderOnClick(view: View) {
        startActivity(Intent(this, OrderDetailActivity::class.java))

    }


    fun finishOnClick(view: View) = finish()
}
