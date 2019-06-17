package top.charjin.shoppingclient.activity

import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.widget.Toast
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.goods_display_include_recommend.*
import kotlinx.android.synthetic.main.order_detail_activity_main.*
import kotlinx.android.synthetic.main.order_detail_include_info.*
import kotlinx.android.synthetic.main.order_include_address.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import top.charjin.shoppingclient.R
import top.charjin.shoppingclient.adapter.GoodsDisplayAdapter
import top.charjin.shoppingclient.entity.OsGoods
import top.charjin.shoppingclient.model.OsOrderModel
import top.charjin.shoppingclient.utils.HttpUtil
import top.charjin.shoppingclient.utils.JsonUtil
import top.charjin.shoppingclient.utils.Router
import top.charjin.shoppingclient.view.OrderGoodsView
import java.io.IOException

class OrderDetailActivity : BaseActivity() {

    private lateinit var order: OsOrderModel

    private lateinit var adapter: GoodsDisplayAdapter
    private var goodsList = arrayListOf<OsGoods>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.order_detail_activity_main)

        order = intent.getSerializableExtra("order") as OsOrderModel

        initHeaderView()
        initAddressView()
        initOrderDetailView()
        initBottomBtnView()

        adapter = GoodsDisplayAdapter(this, goodsList)
        rv_goods_display.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rv_goods_display.adapter = adapter
        initGoodsDisplay()
    }

    /**
     * 显示收货地址信息
     */
    private fun initAddressView() {
        val address = order.address
        this.tv_order_address_receiver.text = address.receiver
        this.tv_order_address_phone.text = address.phone
        this.tv_order_address_detail.text = String.format("%s%s%s %s", address.province, address.city, address.district, address.addressDetail)
    }

    /**
     * 显示头部信息
     */
    private fun initHeaderView() {
        when (order.orderStatus) {

            -1 -> tv_order_detail_status.text = "订单已取消"
            0 -> tv_order_detail_status.text = "未支付"
            1 -> tv_order_detail_status.text = "待发货"
            2 -> tv_order_detail_status.text = "待收货"
            3 -> tv_order_detail_status.text = "待评论"
        }
    }

    /**
     * 显示订单信息
     */
    private fun initOrderDetailView() {
        this.ll_order_detail_goods_container.removeAllViews()
        order.orderDetailList.forEach {
            this.ll_order_detail_goods_container.addView(OrderGoodsView(this, it))
        }

        tv_order_detail_shop_name.text = order.shop.shopName

        tv_order_detail_center_goods_sum.text = String.format("%.2f", order.orderAmountTotal)
        tv_order_detail_center_goods_freight.text = String.format("%d", order.orderFreight)
        tv_order_detail_center_goods_order_pay.text = String.format("%.2f", order.orderAmountActual)
        tv_order_detail_center_goods_pay_actual.text = String.format("%.2f", order.orderAmountActual)

        tv_order_detail_pay_channel.text = order.payBusiness.name
        tv_order_detail_order_remark.text = order.orderDetailList.first()?.remark ?: "无备注信息"
        tv_order_detail_order_no.text = order.orderNo
        tv_order_detail_order_create_time.text = order.orderCreateTime.toString()
        tv_order_detail_order_paid_time.text = order.orderPayTime?.toString() ?: ""
        tv_order_detail_order_deliver_time.text = ""            // 该字段暂被忘记创建了 哈哈.
        tv_order_detail_order_fulfil_time.text = order.orderFulfilTime?.toString() ?: ""
    }

    /**
     * 初始化底部按键的功能(根据不用的订单状态)
     * .....  之后设计自定义的view更新其内容
     */
    private fun initBottomBtnView() {
        val btn1 = tv_btn_order_detail_bottom_order_operation
        val btn2 = tv_btn_order_detail_bottom_pay_operation

        btn1.setOnClickListener {
            Toasty.info(this, "暂时不想做了!").show()
        }
        btn2.setOnClickListener {
            Toasty.info(this, "暂时不想做了!").show()
        }
//        when (order.orderStatus) {
//            1 -> {
//                btn1.setOnClickListener {
//
//                }
//            }
//        }
    }


    /**
     * 显示推荐商品的信息显示 以避免界面不充实
     */
    private fun initGoodsDisplay() {
        HttpUtil.sendOkHttpRequestByGet(Router.BASE_URL + "goods/getAllGoods", object : Callback {

            override fun onFailure(call: Call, e: IOException) {}

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val goodsListData = JsonUtil.parseJSONObjectInStringToEntityList(response.body()!!.string(), OsGoods::class.java)
                goodsList.addAll(goodsListData)
                runOnUiThread {
                    adapter.notifyDataSetChanged()
                    Toast.makeText(this@OrderDetailActivity, "数据已更新", Toast.LENGTH_SHORT).show()
                }
            }
        })


    }


    fun finishOnClick(view: View) = finish()

}
