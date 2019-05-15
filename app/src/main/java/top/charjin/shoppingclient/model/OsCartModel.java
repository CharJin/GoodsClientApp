package top.charjin.shoppingclient.model;

import java.util.List;

import top.charjin.shoppingclient.entity.OsGoods;
import top.charjin.shoppingclient.entity.OsShop;

public class OsCartModel {
    private OsShop shop;
    private List<OsGoods> goodsList;

    public OsCartModel(OsShop shop, List<OsGoods> goodsList) {
        this.shop = shop;
        this.goodsList = goodsList;
    }

    public OsShop getShop() {
        return shop;
    }

    public void setShop(OsShop shop) {
        this.shop = shop;
    }

    public List<OsGoods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<OsGoods> goodsList) {
        this.goodsList = goodsList;
    }

}
