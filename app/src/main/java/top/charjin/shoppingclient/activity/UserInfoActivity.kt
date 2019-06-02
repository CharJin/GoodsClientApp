package top.charjin.shoppingclient.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.user_info_activity_main.*
import top.charjin.shoppingclient.R
import top.charjin.shoppingclient.entity.OsUser

class UserInfoActivity : AppCompatActivity() {


    private lateinit var user: OsUser

    private lateinit var strFormat: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_info_activity_main)

        strFormat = resources.getString(R.string.user_info_base_format)

        user = OsUser()
        user.nickname = "dfsafdas"
        user.username = "usernam"
        user.sex = "nan"
        user.phone = "432432"
        user.realName = "jinzhichao"
        initComponentData()
    }

    private fun initComponentData() {
        tv_info_setting_nickname.text = String.format(strFormat, user.nickname)
        tv_info_setting_username.text = user.username
        tv_user_info_content_sex.text = user.sex
        tv_user_info_content_real_name.text = user.realName
        tv_user_info_content_phone.text = user.phone

    }


    fun settingItemOnClick(view: View) {
        val viewId = view.id
        startActivity(Intent(this, AddressActivity::class.java))
//        val intent = Intent(this,)
//        when(viewId){
//            R.id.ll_user_info_address ->
//        }
    }

    fun finishOnClick(view: View) = finish()
}
