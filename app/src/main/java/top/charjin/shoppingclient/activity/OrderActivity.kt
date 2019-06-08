package top.charjin.shoppingclient.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import top.charjin.shoppingclient.R
import top.charjin.shoppingclient.adapter.OrderAdapter
import top.charjin.shoppingclient.model.OsOrderModel
import top.charjin.shoppingclient.utils.HttpUtil
import top.charjin.shoppingclient.utils.JsonUtil
import top.charjin.shoppingclient.utils.Router
import java.io.IOException
import java.io.Serializable
import java.util.stream.Collectors

class OrderActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener {


    private lateinit var tlOrder: TabLayout

    private lateinit var rvOrderCommon: RecyclerView
    private lateinit var adapter: OrderAdapter

    private lateinit var orderType: OrderType
    private var isFirst = true              // 取消首次监听执行
    private val orderList = ArrayList<OsOrderModel>()
    private val allOrderList = arrayListOf<OsOrderModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.order_activity_main)


        initComponent()

        adapter = OrderAdapter(this, orderList)
        rvOrderCommon.layoutManager = LinearLayoutManager(this)
        rvOrderCommon.adapter = adapter


        tlOrder.addOnTabSelectedListener(this)

        orderType = intent.getSerializableExtra("orderType") as OrderType
        Log.e("Order", orderType.name)
        when (orderType) {
            OrderType.All -> tlOrder.getTabAt(0)!!.select()
            OrderType.OBLIGATION -> tlOrder.getTabAt(1)!!.select()
            OrderType.WAIT_SENDING -> tlOrder.getTabAt(2)!!.select()
            OrderType.WAIT_RECEIVING -> tlOrder.getTabAt(3)!!.select()
            OrderType.WAIT_COMMENT -> tlOrder.getTabAt(4)!!.select()
        }
    }

    override fun onResume() {
        super.onResume()
        initOrderData()
    }

    private fun initOrderData() {
        allOrderList.clear()
        HttpUtil.sendOkHttpRequestByGet(Router.ORDER_URL + "getAllOrdersByUserId?userId=" + 1, object : Callback {
            override fun onFailure(call: Call, e: IOException) {}

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val jsonData = response.body()!!.string()
//                Log.e("Order", jsonData)
                allOrderList.addAll(JsonUtil.parseJSONObjectInStringToEntityList(jsonData, OsOrderModel::class.java))
                orderList.addAll(allOrderList)
//                runOnUiThread { adapter.notifyDataSetChanged() }
                runOnUiThread { onTabSelected(tlOrder.getTabAt(tlOrder.selectedTabPosition)!!) }
            }
        })
    }

    private fun initComponent() {
        tlOrder = findViewById(R.id.tl_order_header)
        rvOrderCommon = findViewById(R.id.rv_order_common)
    }

    fun finishOnClick(view: View) {
        finish()
    }

    override fun onTabSelected(tab: TabLayout.Tab) {
//        if (isFirst) {
//            isFirst = false
//            return
//        }
        /**
         * 订单状态(0:待付款 1:待发货 2:待收货 3:待评论)
         */
        val sTab = tab.text
        val filterList: List<OsOrderModel>
        val bufferList = arrayListOf<OsOrderModel>()
        bufferList.addAll(allOrderList)

        filterList = when (sTab) {
            "待付款" -> bufferList.stream().filter { it.orderStatus.toInt() == 1 }.collect(Collectors.toList())
            "待发货" -> bufferList.stream().filter { it.orderStatus.toInt() == 2 }.collect(Collectors.toList())
            "待收货" -> bufferList.stream().filter { it.orderStatus.toInt() == 3 }.collect(Collectors.toList())
            "待评论" -> bufferList.stream().filter { it.orderStatus.toInt() == 4 }.collect(Collectors.toList())
            else -> bufferList
        }
        orderList.clear()
        orderList.addAll(filterList)
        adapter.notifyDataSetChanged()
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {

    }

    override fun onTabReselected(tab: TabLayout.Tab) {

    }


    enum class OrderType : Serializable {
        All,                // 全部
        OBLIGATION,         // 待付款
        WAIT_SENDING,       // 待发货
        WAIT_RECEIVING,     // 待收货
        WAIT_COMMENT        // 待评论
    }

}

