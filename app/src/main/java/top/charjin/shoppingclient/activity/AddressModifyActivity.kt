package top.charjin.shoppingclient.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.address_modify_activity_main.*
import top.charjin.shoppingclient.R
import top.charjin.shoppingclient.entity.OsAddress

class AddressModifyActivity : AppCompatActivity(), TextWatcher {
    private lateinit var address: OsAddress

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.address_modify_activity_main)
        address = intent.getSerializableExtra("address") as OsAddress

        initComponentData()
    }

    private fun initComponentData() {

        et_address_modify_receiver.addTextChangedListener(this)
        et_address_modify_phone.addTextChangedListener(this)
        et_address_modify_district.addTextChangedListener(this)
        et_address_modify_detail.addTextChangedListener(this)

        et_address_modify_receiver.setText(address.receiver)
        et_address_modify_phone.setText(address.phone.toString())
        et_address_modify_district.setText(String.format("%s%s%s", address.province, address.city, address.district))
        et_address_modify_detail.setText(address.addressDetail)
        cb_address_modify_is_default.isChecked = address.isDefault

    }

    fun saveOnClick(view: View) {

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
