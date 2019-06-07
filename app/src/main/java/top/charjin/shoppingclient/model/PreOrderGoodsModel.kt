package top.charjin.shoppingclient.model

import java.io.Serializable

class PreOrderGoodsModel(var shopId: Int, var shopName: String, var goodsList: MutableList<CartGoodsModel>) : Serializable