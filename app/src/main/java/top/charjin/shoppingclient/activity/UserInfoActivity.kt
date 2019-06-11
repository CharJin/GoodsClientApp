package top.charjin.shoppingclient.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.user_info_activity_main.*
import top.charjin.shoppingclient.R
import top.charjin.shoppingclient.ShoppingApplication

class UserInfoActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_info_activity_main)

        initComponentData()
    }

    private fun initComponentData() {
        Glide.with(this)
                .load(user.headPortrait)
                .centerCrop()
                .into(civ_info_setting_head_portrait)
        tv_info_setting_nickname.text = user.nickname
        tv_info_setting_username.text = user.username
        tv_user_info_content_sex.text = user.sex
        tv_user_info_content_real_name.text = user.realName
        tv_user_info_content_phone.text = user.phone

    }


    fun settingItemOnClick(view: View) {
        when (view.id) {
            R.id.tv_btn_user_info_quit -> {
                ShoppingApplication.setUser(null)
                getSharedPreferences("user", Context.MODE_PRIVATE).edit().clear().apply()
                Toasty.success(this, "已退出").show()
                finish()
            }
            else -> startActivity(Intent(this, AddressActivity::class.java))
        }
    }

    fun finishOnClick(view: View) = finish()
}
