package top.charjin.shoppingclient.activity

import android.os.Bundle
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

class ShopActivity : BaseActivity() {

    private lateinit var adapter: ShopGoodsAdapter
    private val goodsList = arrayListOf<OsGoods>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shop_activity_main)

        adapter = ShopGoodsAdapter(this, goodsList)
        adapter.onCartClickListener = object : ShopGoodsAdapter.OnCartClickListener {
            override fun onCartClick(goods: OsGoods) {
                HttpUtil.sendOkHttpRequestByGet(Router.CART_URL + "addGoods?userId=${user.userId}&goodsId=${goods.goodsId}&goodsNum=1",
                        object : Callback {
                            override fun onFailure(call: Call, e: IOException) {
                                Toasty.error(this@ShopActivity, "购物车添加请求失败!").show()
                            }

                            override fun onResponse(call: Call, response: Response) {
                                val affectedRow = response.body()!!.string().toInt()
                                runOnUiThread {
                                    if (affectedRow > 0)
                                        Toasty.success(this@ShopActivity, "已加至购物车!").show()
                                    else
                                        Toasty.success(this@ShopActivity, "添加失败,似乎出现了问题!").show()
                                }
                            }

                        })
            }
        }

        rv_shop.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        this.rv_shop.adapter = adapter

        val shop = intent.getSerializableExtra("shop") as OsShop
        tv_shop_name.text = shop.shopName

        intiGoodsData(shop.shopId)
    }

    private fun intiGoodsData(shopId: Int) {
        if (shopId == -1) return
        HttpUtil.sendOkHttpRequestByGet(Router.GOODS_URL + "getAllGoodsByShopId?shopId=$shopId", object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Toasty.warning(this@ShopActivity, "商品信息请求错误！").show()
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonData = response.body()?.string()
                val goodsListData = JsonUtil.parseJSONObjectInStringToEntityList(jsonData, OsGoods::class.java)
                goodsList.addAll(goodsListData)
                runOnUiThread(adapter::notifyDataSetChanged)
            }
        })

    }


    fun finishOnClick(view: View) = finish()
}
