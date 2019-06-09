package top.charjin.shoppingclient.model

import java.io.Serializable

class PreOrderGoodsModel : Serializable {
    var shopId: Int
    var shopName: String
    var remark: String = ""
    var goodsList: MutableList<CartGoodsModel>


    constructor(shopId: Int, shopName: String, goodsList: MutableList<CartGoodsModel>) {
        this.shopId = shopId
        this.shopName = shopName
        this.goodsList = goodsList
    }

    constructor(shopId: Int, shopName: String, remark: String, goodsList: MutableList<CartGoodsModel>) {
        this.shopId = shopId
        this.shopName = shopName
        this.remark = remark
        this.goodsList = goodsList
    }
}