package top.charjin.shoppingclient.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.shop_activity_main.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import top.charjin.shoppingclient.R
import top.charjin.shoppingclient.adapter.ShopGoodsAdapter
import top.charjin.shoppingclient.entity.OsGoods
import top.charjin.shoppingclient.entity.OsShop
import top.charjin.shoppingclient.utils.HttpUtil
import top.charjin.shoppingclient.utils.JsonUtil
import top.charjin.shoppingclient.utils.Router
import java.io.IOException

class ShopActivity : AppCompatActivity() {

    private lateinit var adapter: ShopGoodsAdapter
    private val goodsList = arrayListOf<OsGoods>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shop_activity_main)
        adapter = ShopGoodsAdapter(this, goodsList, ShopGoodsAdapter.ItemClickListener {
            val intent = Intent(this, GoodsActivity::class.java)
            intent.putExtra("goods", it)
            startActivity(intent)
        })
        rv_shop.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        this.rv_shop.adapter = adapter

        val shop = intent.getSerializableExtra("shop") as OsShop
        tv_shop_name.text = shop.name

        intiGoodsData(shop.id)
    }

    private fun intiGoodsData(shopId: Int) {
        if (shopId == -1) return
        HttpUtil.sendOkHttpRequestByGet(Router.Goods_URL + "getAllGoodsByShopId?shopId=$shopId", object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Toasty.warning(this@ShopActivity, "商品信息请求错误！").show()
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonData = response.body()?.string()
                val goodsDataList = JsonUtil.parseJSONObjectInStringToEntityList(jsonData, OsGoods::class.java)
                goodsList.addAll(goodsDataList)
                runOnUiThread(adapter::notifyDataSetChanged)
            }
        })

    }


    fun finishOnClick(view: View) = finish()
}
