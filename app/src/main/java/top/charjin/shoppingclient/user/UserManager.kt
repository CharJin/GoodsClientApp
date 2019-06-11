package top.charjin.shoppingclient.user

import android.content.Context

interface UserManager {
    fun userLoginIntercept(context: Context): Boolean
}