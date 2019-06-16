package top.charjin.shoppingclient.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.order_include_address.*
import kotlinx.android.synthetic.main.order_pay_pop_window.view.*
import kotlinx.android.synthetic.main.order_submit_activity_main.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import top.charjin.shoppingclient.R
import top.charjin.shoppingclient.adapter.OrderSubmitGoodsAdapter
import top.charjin.shoppingclient.entity.OsAddress
import top.charjin.shoppingclient.entity.OsOrder
import top.charjin.shoppingclient.entity.OsOrderDetail
import top.charjin.shoppingclient.model.PreOrderGoodsModel
import top.charjin.shoppingclient.model.ResultModel
import top.charjin.shoppingclient.utils.HttpUtil
import top.charjin.shoppingclient.utils.JsonUtil
import top.charjin.shoppingclient.utils.Router
import top.charjin.shoppingclient.utils.ShoppingClientUtil
import top.charjin.shoppingclient.view.GoodsPopupWindow
import java.io.IOException

class OrderSubmitActivity : BaseActivity() {

    private var address: OsAddress? = null
    private val orderGoodsList = arrayListOf<PreOrderGoodsModel>()
    private var sum = 0.0


    // 初始化提出窗口
    private lateinit var orderPayContentView: View
    private lateinit var orderPayPopWindow: GoodsPopupWindow


    companion object {
        const val CHANGE_ADDRESS = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.order_submit_activity_main)
        initDefaultAddress()
        initPreOrderData()


        orderPayContentView = LayoutInflater.from(this).inflate(R.layout.order_pay_pop_window, null)
        orderPayPopWindow = GoodsPopupWindow(this, orderPayContentView)

    }

    /**
     * 初始化pre订单数据(商品信息)
     */
    private fun initPreOrderData() {
        val adapter = OrderSubmitGoodsAdapter(this, orderGoodsList)
        rv_order_submit_goods.layoutManager = LinearLayoutManager(this)
        rv_order_submit_goods.adapter = adapter

        val goodsList: ArrayList<PreOrderGoodsModel> = intent.getSerializableExtra("orderGoodsList") as ArrayList<PreOrderGoodsModel>

        orderGoodsList.clear()
        orderGoodsList.addAll(goodsList)


        orderGoodsList.forEach { preOrderModel ->
            preOrderModel.goodsList.forEach { sum += it.goodsNum * it.price }
        }

        this.tv_order_submit_sum.text = String.format("%.2f", sum)
    }


    /**
     * 获取默认地址, 若用户未设置默认地址, 则使用最近使用过或者修改过的地址
     * 逻辑由服务器端处理
     */
    private fun initDefaultAddress() {
        HttpUtil.sendOkHttpRequestByGet(Router.ADDRESS_URL + "getDefaultAddress?userId=" + user.userId,
                object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        Toasty.error(this@OrderSubmitActivity, "地址数据请求错误!").show()
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val jsonData = response.body()!!.string()
                        println(jsonData)
                        val gson = GsonBuilder()
                                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                                .create()
                        val rs = gson.fromJson<ResultModel<OsAddress>>(jsonData, object : TypeToken<ResultModel<OsAddress>>() {}.type)
                        runOnUiThread {
                            if (rs.code == 200) {
                                address = rs.data as OsAddress
                                address?.let {
                                    tv_order_address_receiver.text = it.receiver
                                    tv_order_address_phone.text = it.phone
                                    tv_order_address_detail.text = String.format("%s%s%s %s",
                                            it.province, it.city, it.district, it.addressDetail)
                                }
                            } else {
                                tv_order_address_receiver.text = ""
                                tv_order_address_phone.text = ""
                                tv_order_address_detail.text = "您还未添加任何地址"
                            }

                        }
                    }

                })
    }

    /**
     * 更换收货地址，转至地址页面
     */
    fun changeAddressOnClick(view: View) {
        Intent(this, AddressActivity::class.java).apply {
            putExtra("flag", CHANGE_ADDRESS)
            startActivityForResult(this, CHANGE_ADDRESS)
        }
    }


    /**
     * 根据从地址页面返回的address可序列化对象 重新加载地址数据
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CHANGE_ADDRESS && resultCode == Activity.RESULT_OK) {
            address = data!!.getSerializableExtra("address") as OsAddress
            address?.let {
                tv_order_address_receiver.text = it.receiver
                tv_order_address_phone.text = it.phone
                tv_order_address_detail.text = String.format("%s%s%s %s",
                        it.province, it.city, it.district, it.addressDetail)
            }

        }
    }


    /**
     * 订单提交按钮监听事件
     * 根据其他电商平台,点击提交后 订单就已创建成功, 初始状态为"未支付".
     */
    fun submitOrderOnClick(view: View) {
        // 如果地址为空则返回
        if (address == null) {
            Toasty.warning(this, "请选择收货地址!").show()
            return
        }

        val orderNoList = arrayListOf<String>()
        // 订单按照商店创建
        orderGoodsList.forEach {
            val goodsList = it.goodsList
            val orderDetailGoodsList = arrayListOf<OsOrderDetail>()
            val orderNo = ShoppingClientUtil.generateOrderNo(it.shopId, user.userId)
            var amountActual = 0.0

            orderNoList.add(orderNo)

            // 构建出订单详情列表
            goodsList.forEach { goods ->
                amountActual += goods.price * goods.goodsNum
                val orderDetailGoods = OsOrderDetail(goods.goodsId, orderNo, goods.goodsName, goods.price, "默认套餐", "默认套餐",
                        goods.goodsNum, goods.goodsNum * goods.price, it.remark)
                orderDetailGoodsList.add(orderDetailGoods)
            }

            val order = OsOrder(user.userId, it.shopId, address!!.addressId, orderNo, amountActual, amountActual,
                    ShoppingClientUtil.getCurrentTime(), null, 0, 1)

            HttpUtil.sendOkHttpRequestByPost(Router.ORDER_URL + "/addOrder", JsonUtil.parseObjectToJSONWithDateFormat(order), object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread {
                        Toasty.error(this@OrderSubmitActivity, "订单接口请求错误!")
                        Log.i("orderSubmit", e.message.toString())

                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    val resultMap = JsonUtil.parseJSONObject(response.body()!!.string(), ResultModel::class.java)
                    if (resultMap.code == 200) {
                    } else {
                    }

                }

            })
            HttpUtil.sendOkHttpRequestByPost(Router.ORDER_URL + "/addOrderDetailGoodsList?userId=" + user.userId, JsonUtil.parseObjectToJSONWithDateFormat(orderDetailGoodsList), object : Callback {

                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread {
                        Toasty.error(this@OrderSubmitActivity, "订单接口请求错误!")
                        Log.i("orderSubmit", e.message.toString())
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    val resultMap = JsonUtil.parseJSONObject(response.body()!!.string(), ResultModel::class.java)
                    if (resultMap.code == 201) {
                    } else {
                    }
                }
            })

        }


        /*
         * 大于0: 订单创建成功
         */
        if (orderNoList.size > 0) {
            var orderNos = ""
            orderNoList.forEach { orderNos += "$it\n" }
            orderPayContentView.tv_order_pay_order_number.text = orderNos
            orderPayContentView.tv_order_pay_amount.text = sum.toString()
            orderPayContentView.tv_btn_order_pay_cancel.setOnClickListener {
                Toast.makeText(this, "支付未成功!", Toast.LENGTH_SHORT).show()
                orderPayPopWindow.dismiss()
                val intent = Intent(this, OrderResultActivity::class.java)
                intent.putExtra("orderStatus", false)
                startActivity(intent)
                finish()
            }

            // 点击确认支付, 订单状态改为已支付
            orderPayContentView.tv_btn_order_pay_confirm.setOnClickListener {
                // 此处需要修改支付时间(在服务器端进行)
                // 支付成功 待发货 状态为1
                HttpUtil.sendOkHttpRequestByPost(Router.ORDER_URL + "updateOrderStatusByOrderNo?orderStatus=1", Gson().toJson(orderNoList),
                        object : Callback {
                            override fun onFailure(call: Call, e: IOException) {}

                            override fun onResponse(call: Call, response: Response) {

                                runOnUiThread {
                                    Toast.makeText(this@OrderSubmitActivity, "支付成功!", Toast.LENGTH_SHORT).show()
                                    orderPayPopWindow.dismiss()
//                                    val intent = Intent(this@OrderSubmitActivity, OrderDetailActivity::class.java)
//                                    intent.putExtra("orderGoodsList", orderGoodsList)
//                                    startActivity(intent)

                                    val intent = Intent(this@OrderSubmitActivity, OrderResultActivity::class.java)
                                    intent.putExtra("orderStatus", true)
                                    startActivity(intent)
                                    finish()
                                }
                            }
                        })

            }
            orderPayPopWindow.openPopupWindow()

        }

    }

    fun finishOnClick(view: View) = finish()

}
