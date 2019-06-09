package top.charjin.shoppingclient.utils

import java.sql.Timestamp
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

object ShoppingClientUtil {

    /**
     * 生成22位的订单号
     */
    fun generateOrderNo(shopId: Int, userId: Int): String =
            SimpleDateFormat("yyyyMMddHHmmss", Locale("zh")).format(Date()) +
                    DecimalFormat("0000").format(shopId) + DecimalFormat("0000").format(userId)

    fun getCurrentTime(): Timestamp {
//        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("zh"))
//        val nowTime = dateFormat.format(Date())
//        return dateFormat.parse(nowTime)

        val date = Date()//获得系统时间.

        val nowTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("zh")).format(date)

        return Timestamp.valueOf(nowTime)
    }
}