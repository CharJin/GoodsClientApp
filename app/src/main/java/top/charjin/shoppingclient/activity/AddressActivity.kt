package top.charjin.shoppingclient.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.address_activity_main.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import top.charjin.shoppingclient.R
import top.charjin.shoppingclient.adapter.AddressAdapter
import top.charjin.shoppingclient.entity.OsAddress
import top.charjin.shoppingclient.utils.HttpUtil
import top.charjin.shoppingclient.utils.JsonUtil
import top.charjin.shoppingclient.utils.Router
import java.io.IOException

class AddressActivity : BaseActivity(), Callback {

    private lateinit var adapter: AddressAdapter

    private val addressList = mutableListOf<OsAddress>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.address_activity_main)

        // 初始化地址信息
        adapter = AddressAdapter(this, addressList)
        rv_address.layoutManager = LinearLayoutManager(this)
        rv_address.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rv_address.adapter = adapter


        // 从订单提交页面转来，则添加item点击监听以回传address数据
        val flag = intent.getIntExtra("flag", -1)
        if (flag == OrderSubmitActivity.CHANGE_ADDRESS) {
            adapter.onItemClickListener = object : AddressAdapter.OnItemClickListener {
                override fun onItemClick(address: OsAddress) {
                    Intent().apply {
                        putExtra("address", address)
                        setResult(Activity.RESULT_OK, this)
                    }
                    finish()
                }

            }
        }

    }

    override fun onResume() {
        super.onResume()
        // 在onResume中进行数据请求以避免从另一页面修改地址后转回而数据不更新
        addressList.clear()
        HttpUtil.sendOkHttpRequestByGet(Router.ADDRESS_URL + "getAllAddressByUserId?userId=${user.userId}", this)
    }

    fun finishOnClick(view: View) = finish()

    fun addNewAddress(view: View) {
        Intent(this, AddressModifyActivity::class.java).apply {
            putExtra("addressOperatorType", AddressModifyActivity.ADDRESS_ADD)  // 0 : add, 1 : update
            putExtra("address", OsAddress().apply { this.userId = user.userId }) //传递空数据的的Address地址对象
            startActivity(this)
        }

    }

    override fun onFailure(call: Call, e: IOException) {
        runOnUiThread {
            Toasty.error(this@AddressActivity, "地址数据访问失败!").show()
        }
    }

    override fun onResponse(call: Call, response: Response) {
        val jsonData = response.body()!!.string()
        val data = JsonUtil.parseJSONObjectInStringToEntityList(jsonData, OsAddress::class.java)

        addressList.addAll(data)
        runOnUiThread {
            adapter.notifyDataSetChanged()
        }
    }
}
