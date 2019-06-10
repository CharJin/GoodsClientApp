package top.charjin.shoppingclient.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.widget.Toast
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


class OrderResultActivity : AppCompatActivity() {

    private lateinit var adapter: GoodsDisplayAdapter
    private var goodsList = arrayListOf<OsGoods>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.order_result_activity_main)

        adapter = GoodsDisplayAdapter(this, goodsList)
        rv_order_result_goods_display.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rv_order_result_goods_display.adapter = adapter
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
}
