package top.charjin.shoppingclient.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AlertDialog
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


//    private var options1Items = arrayListOf<ProvinceModel>()
//    private var options2Items = mutableListOf<MutableList<String>>()
//    private var options3Items = mutableListOf<MutableList<MutableList<String>>>()
//
//    private lateinit var pvOptions: OptionsPickerView<Any>

    private var type: Int = ADDRESS_ADD

    private lateinit var address: OsAddress

    private var province: String = ""
    private var city: String = ""
    private var district: String = ""

    companion object {
        const val ADDRESS_ADD = 0
        const val ADDRESS_UPDATE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.address_modify_activity_main)

//        initAreaInfo()

        address = intent.getSerializableExtra("address") as OsAddress
//        address = if (extraAddress != null) extraAddress as OsAddress else OsAddress()

        type = intent.getIntExtra("addressOperatorType", ADDRESS_ADD)   // 默认数值为0 : add

        initComponentData()
    }

    private fun initComponentData() {

        et_address_modify_receiver.addTextChangedListener(this)
        et_address_modify_phone.addTextChangedListener(this)
        et_address_modify_district.addTextChangedListener(this)
        et_address_modify_detail.addTextChangedListener(this)

        // 添加状态不做数据初始化,但需修改数据header界面
        if (type == ADDRESS_ADD) {
            tv_address_modify_header_title.text = resources.getString(R.string.address_modify_header_title_tv_add)
            tv_btn_address_modify_header_delete.visibility = View.GONE
            return
        }

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
        address.province = this.province
        address.city = this.city
        address.district = this.district
        address.addressDetail = et_address_modify_detail.text.toString()
        address.isDefault = cb_address_modify_is_default.isChecked

        address.createTime = null
        address.updateTime = null   // 如果是更新, 把更新时间字段置为null, 使数据库自动更新时间

        val jsonAddress = Gson().toJson(address)
        val requestUrl: String = when (type) {
            ADDRESS_ADD -> Router.ADDRESS_URL + "add"
            else -> Router.ADDRESS_URL + "update"
        }


        HttpUtil.sendOkHttpRequestByPost(requestUrl, jsonAddress, object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toasty.error(this@AddressModifyActivity, "地址修改请求失败！").show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val isSucceed = response.body()?.string()?.toInt() ?: 0 > 0 // 如果不为空则取返回值并进行格式转换,否则为0
                runOnUiThread {
                    if (isSucceed) {
                        when (type) {
                            ADDRESS_ADD -> Toasty.success(this@AddressModifyActivity, "地址创建成功！").show()
                            else -> Toasty.success(this@AddressModifyActivity, "地址修改成功！").show()
                        }
                        finish()
                    } else
                        Toasty.error(this@AddressModifyActivity, "地址修改未成功！").show()
                }
            }

        })

    }


    /**
     * 删除当前地址
     */
    @SuppressLint("InflateParams")
    fun deleteOnClick(view: View) {

/*
        val alertView: View = LayoutInflater.from(this).inflate(R.layout.address_modify_delete_alert_dialog, null, false)
        val layoutParams = alertView.layoutParams
        layoutParams.height = 300
        layoutParams.width = 150
        alertView.layoutParams = layoutParams
*/


        AlertDialog.Builder(this)
//                .setView(view)
                .setTitle("是否删除该地址")
                .setPositiveButton("删除地址") { _, _ ->
                    HttpUtil.sendOkHttpRequestByGet(Router.ADDRESS_URL + "deleteById?addressId=" + address.addressId, object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            runOnUiThread {
                                Toasty.error(this@AddressModifyActivity, "地址数据请求失败！").show()
                            }
                        }

                        override fun onResponse(call: Call, response: Response) {
                            val isSucceed = response.body()?.string()?.toInt() ?: 0 > 0 // 如果不为空则取返回值并进行格式转换,否则为0
                            runOnUiThread {
                                if (isSucceed)
                                    Toasty.success(this@AddressModifyActivity, "地址删除成功！").show()
                                else
                                    Toasty.error(this@AddressModifyActivity, "地址删除未成功！").show()
                                finish()

                            }
                        }


                    })
                }
                .setNegativeButton("取消") { _, _ -> }
                .show()


    }

//    private fun initAreaInfo() {
//        val stringBuilder = StringBuilder()
//        val inputStream = resources.assets.open("province.json")
//        inputStream.bufferedReader().useLines { line ->
//            line.forEach {
//                //                Log.e("aa",it )
//                stringBuilder.append(it)
//            }
//        }
//        val jsonData = String(stringBuilder).trim()
//        val list = Gson().fromJson<MutableList<ProvinceModel>>(jsonData, (object : TypeToken<MutableList<ProvinceModel>>() {}.type))
//
//        list.forEach {
//            options1Items.add(it)
//            options2Items.add(options1Items)
//            it.city.forEach { _ ->
//                options3Items.add(options2Items)
//            }
//        }
//    }

    /**
     * 选择省市县区
     */
/*
    fun chooseProvinceCityDistrict(view: View) {
        pvOptions = OptionsPickerBuilder(this, OnOptionsSelectListener { options1, option2, options3, _ ->
            //返回的分别是三个级别的选中位置
            val tx = (options1Items.get(options1)
                    + options2Items.get(options1).get(option2)
                    + options3Items.get(options1).get(option2).get(options3))
//            tvOptions.setText(tx)
        }).setOptionsSelectChangeListener { options1, options2, options3 ->
            val str = "options1: $options1\noptions2: $options2\noptions3: $options3"
            Toast.makeText(this@AddressModifyActivity, str, Toast.LENGTH_SHORT).show()
        }
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("城市选择")//标题
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
                .setTitleBgColor(-0xcccccd)//标题背景颜色 Night mode
                .setBgColor(-0x1000000)//滚轮背景颜色 Night mode
                .setContentTextSize(18)//滚轮文字大小
//                .setLinkage(false)//设置是否联动，默认true
                .setLabels("省", "市", "区")//设置选择的三级单位
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(1, 1, 1)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .isDialog(true)//是否显示为对话框样式
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .build()

        pvOptions.setPicker(options1Items, options2Items, options3Items)//添加数据源
        pvOptions.show()
    }
*/


    fun finishOnClick(view: View) = finish()

    override fun afterTextChanged(s: Editable?) {}
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val etArr: Array<EditText> = arrayOf(et_address_modify_receiver, et_address_modify_phone,
                et_address_modify_district, et_address_modify_detail)
        etArr.forEach { it.paint.isFakeBoldText = it.text.isNotEmpty() }
    }

}
