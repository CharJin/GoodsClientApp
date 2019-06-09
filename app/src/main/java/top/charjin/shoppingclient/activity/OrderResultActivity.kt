package top.charjin.shoppingclient.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import top.charjin.shoppingclient.R


class OrderResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.order_result_activity_main)
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
