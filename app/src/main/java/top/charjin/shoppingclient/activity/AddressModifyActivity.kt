package top.charjin.shoppingclient.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.google.gson.Gson
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.address_modify_activity_main.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import top.charjin.shoppingclient.R
import top.charjin.shoppingclient.entity.OsAddress
import top.charjin.shoppingclient.utils.HttpUtil
import top.charjin.shoppingclient.utils.Router
import java.io.IOException

class AddressModifyActivity : AppCompatActivity(), TextWatcher {


    private var type: Int = ADDRESS_ADD

    private lateinit var address: OsAddress

    private var province: String = ""
    private var city: String = ""
    private var district: String = ""

    companion object {
        val ADDRESS_ADD = 0
        val ADDRESS_UPDATE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.address_modify_activity_main)

        val extraAddress = intent.getSerializableExtra("address")
        address = if (extraAddress != null) extraAddress as OsAddress else OsAddress()

        type = intent.getIntExtra("addressOperatorType", ADDRESS_ADD)   // 默认数值为0 : add

        initComponentData()
    }

    private fun initComponentData() {

        et_address_modify_receiver.addTextChangedListener(this)
        et_address_modify_phone.addTextChangedListener(this)
        et_address_modify_district.addTextChangedListener(this)
        et_address_modify_detail.addTextChangedListener(this)

        if (type == ADDRESS_ADD) return // 添加状态不做数据初始化


        province = address.province
        city = address.city
        district = address.district

        et_address_modify_receiver.setText(address.receiver)
        et_address_modify_phone.setText(address.phone.toString())
        et_address_modify_district.setText(String.format("%s%s%s", province, city, district))
        et_address_modify_detail.setText(address.addressDetail)
        cb_address_modify_is_default.isChecked = address.isDefault

    }


    /**
     *  添加新地址 || 保存修改后的地址
     */
    fun saveOnClick(view: View) {
        address.receiver = et_address_modify_receiver.text.toString()
        address.phone = et_address_modify_phone.text.toString()
        address.district = district
        address.addressDetail = et_address_modify_detail.text.toString()
        address.isDefault = cb_address_modify_is_default.isChecked

        val jsonAddress = Gson().toJson(address)
        val requestUrl: String = when (type) {
            ADDRESS_ADD -> Router.ADDRESS_URL + "add"
            else -> Router.ADDRESS_URL + "update"
        }

        address.createTime = null
        address.updateTime = null   // 如果是更新, 把更新时间字段置为null, 使数据库自动更新时间

        HttpUtil.sendOkHttpRequestByPost(requestUrl, jsonAddress, object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                runOnUiThread {
                    when (type) {
                        ADDRESS_ADD -> Toasty.info(this@AddressModifyActivity, "地址创建成功！").show()
                        else -> Toasty.info(this@AddressModifyActivity, "地址修改成功！").show()
                    }

                }
            }

        })

    }

    fun deleteOnClick(view: View) {}

    fun finishOnClick(view: View) = finish()

    override fun afterTextChanged(s: Editable?) {}
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val etArr: Array<EditText> = arrayOf(et_address_modify_receiver, et_address_modify_phone,
                et_address_modify_district, et_address_modify_detail)
        etArr.forEach { it.paint.isFakeBoldText = it.text.isNotEmpty() }
    }

}
