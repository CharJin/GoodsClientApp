package top.charjin.shoppingclient.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import top.charjin.shoppingclient.R

class AddressActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.address_modify_activity_main)
    }

    fun finishOnClick(view: View) = finish()

    fun addNewAddress(view: View) {

    }
}
