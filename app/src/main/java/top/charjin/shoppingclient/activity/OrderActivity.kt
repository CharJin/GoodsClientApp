package top.charjin.shoppingclient.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.goods_display_include_recommend.*
import kotlinx.android.synthetic.main.order_activity_main.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import top.charjin.shoppingclient.R
import top.charjin.shoppingclient.adapter.GoodsDisplayAdapter
import top.charjin.shoppingclient.adapter.OrderAdapter
import top.charjin.shoppingclient.entity.OsGoods
import top.charjin.shoppingclient.model.OsOrderModel
import top.charjin.shoppingclient.utils.HttpUtil
import top.charjin.shoppingclient.utils.JsonUtil
import top.charjin.shoppingclient.utils.Router
import top.charjin.shoppingclient.utils.WindowUtil
import java.io.IOException
import java.io.Serializable
import java.util.stream.Collectors

class OrderActivity : BaseActivity(), TabLayout.OnTabSelectedListener {


    private lateinit var tlOrder: TabLayout

    private lateinit var rvOrderCommon: RecyclerView
    private lateinit var adapter: OrderAdapter

    private lateinit var orderType: OrderType
    private val orderList = ArrayList<OsOrderModel>()
    private val allOrderList = arrayListOf<OsOrderModel>()


    private lateinit var goodsAdapter: GoodsDisplayAdapter
    private var goodsList = arrayListOf<OsGoods>()

    override fun setColorId() {
        this.mColorId = R.color.app_status_bar_white
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.order_activity_main)
        WindowUtil.setAndroidNativeLightStatusBar(this, true)

        initComponent()

        // 初始化订单list
        adapter = OrderAdapter(this, orderList)
        rvOrderCommon.layoutManager = LinearLayoutManager(this)
        rvOrderCommon.adapter = adapter

        // 初始化商品推荐list
        goodsAdapter = GoodsDisplayAdapter(this, goodsList)
        rv_goods_display.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rv_goods_display.adapter = goodsAdapter

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
        HttpUtil.sendOkHttpRequestByGet(Router.ORDER_URL + "getAllOrdersByUserId?userId=" + user.userId, object : Callback {
            override fun onFailure(call: Call, e: IOException) {}

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val jsonData = response.body()!!.string()
                Log.e("Order", jsonData)
                allOrderList.addAll(JsonUtil.parseJSONObjectInStringToEntityList(jsonData, OsOrderModel::class.java))
                orderList.addAll(allOrderList)
//                runOnUiThread { adapter.notifyDataSetChanged() }
                runOnUiThread {
                    onTabSelected(tlOrder.getTabAt(tlOrder.selectedTabPosition)!!)
                    initGoodsDisplay()
                }
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
        cl_order_no_record_tip.visibility = View.GONE
        /**
         * 订单状态(0:待付款 1:待发货 2:待收货 3:待评论)
         */
        val sTab = tab.text
        val filterList: List<OsOrderModel>
        val bufferList = arrayListOf<OsOrderModel>()
        bufferList.addAll(allOrderList)

        adapter.isAllOrder = sTab == "全部"       // 如果是全部页Tab,则右上角显示订单状态

        filterList = when (sTab) {
            "待付款" -> bufferList.stream().filter { it.orderStatus.toInt() == 0 }.collect(Collectors.toList())
            "待发货" -> bufferList.stream().filter { it.orderStatus.toInt() == 1 }.collect(Collectors.toList())
            "待收货" -> bufferList.stream().filter { it.orderStatus.toInt() == 2 }.collect(Collectors.toList())
            "待评论" -> bufferList.stream().filter { it.orderStatus.toInt() == 3 }.collect(Collectors.toList())
            else -> bufferList
        }
        orderList.clear()
        orderList.addAll(filterList)
        adapter.notifyDataSetChanged()

        // NestedScrollView 滑至顶部
        nsv_order.fling(0)
        nsv_order.smoothScrollTo(0, 0)

        if (orderList.size == 0) cl_order_no_record_tip.visibility = View.VISIBLE
        initGoodsDisplay()
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


    private fun initGoodsDisplay() {
        // 等待数据加载完成后再显示
        ll_order_rv_goods.visibility = View.GONE
        goodsList.clear()
        HttpUtil.sendOkHttpRequestByGet(Router.BASE_URL + "goods/getAllGoods", object : Callback {

            override fun onFailure(call: Call, e: IOException) {}

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val goodsListData = JsonUtil.parseJSONObjectInStringToEntityList(response.body()!!.string(), OsGoods::class.java)
                goodsList.addAll(goodsListData)
                runOnUiThread {
                    goodsAdapter.notifyDataSetChanged()
                    ll_order_rv_goods.visibility = View.VISIBLE
                    Toast.makeText(this@OrderActivity, "数据已更新", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


}

