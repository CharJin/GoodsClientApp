package top.charjin.shoppingclient.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.goods_display_include_recommend.*
import kotlinx.android.synthetic.main.order_result_activity_main.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import top.charjin.shoppingclient.R
import top.charjin.shoppingclient.adapter.GoodsDisplayAdapter
import top.charjin.shoppingclient.entity.OsGoods
import top.charjin.shoppingclient.utils.HttpUtil
import top.charjin.shoppingclient.utils.JsonUtil
import top.charjin.shoppingclient.utils.Router
import java.io.IOException


class OrderResultActivity : BaseActivity() {

    private lateinit var adapter: GoodsDisplayAdapter
    private var goodsList = arrayListOf<OsGoods>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.order_result_activity_main)

        when (intent.getBooleanExtra("orderStatus", false)) {
            true -> {

            }
            false -> {
                Glide.with(this)
                        .load(R.drawable.order_result_unsuccess_icon_fill_white)
                        .into(iv_order_result_status)
                tv_order_result_status.text = "交易失败"
                tv_order_result_tip.text = "快去为你的宝贝买单吧~"
                tv_btn_order_result_evaluate.text = "立即支付"
            }
        }

        tv_btn_order_result_go_home

        adapter = GoodsDisplayAdapter(this, goodsList)
        rv_goods_display.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rv_goods_display.adapter = adapter
        initGoodsDisplay()
    }


    private fun initGoodsDisplay() {
        HttpUtil.sendOkHttpRequestByGet(Router.BASE_URL + "goods/getAllGoods", object : Callback {

            override fun onFailure(call: Call, e: IOException) {}

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val goodsListData = JsonUtil.parseJSONObjectInStringToEntityList(response.body()!!.string(), OsGoods::class.java)
                goodsList.addAll(goodsListData)
                runOnUiThread {
                    adapter.notifyDataSetChanged()
                    Toast.makeText(this@OrderResultActivity, "数据已更新", Toast.LENGTH_SHORT).show()
                }
            }
        })


    }


    fun goHomeOnClick(view: View) {
        val intent = Intent(this, AppActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    fun evaluateOnClick(view: View) {
        Toast.makeText(this, "待开发,哈哈", Toast.LENGTH_SHORT).show()
    }

    fun finishOnClick(view: View) = finish()


//    override fun setStatusBarStyle() {
//        tintManager.setStatusBarTintDrawable(getDrawable(R.drawable.order_bg))
//    }
}
