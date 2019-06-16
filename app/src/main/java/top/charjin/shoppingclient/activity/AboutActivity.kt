package top.charjin.shoppingclient.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.about_activity_main.*
import top.charjin.shoppingclient.R
import top.charjin.shoppingclient.utils.WindowUtil

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        WindowUtil.setStatusBarStyle(this, WindowUtil.COLOR, R.color.app_status_bar_white)
        WindowUtil.setAndroidNativeLightStatusBar(this, true)

        setContentView(R.layout.about_activity_main)

        about_web_view.settings.javaScriptEnabled = true
        about_web_view.webViewClient = WebViewClient()
        about_web_view.loadUrl("https://github.com/charjindev/shopping-client.git")

    }
}
