package top.charjin.shoppingclient.entity;

import java.io.Serializable;

public class OsShop implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer shopId;
    private String shopName;
    /**
     * 商品类型(id)
     */
    private Integer shopTypeId;

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer id) {
        this.shopId = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String name) {
        this.shopName = name;
    }

    public Integer getShopTypeId() {
        return shopTypeId;
    }

    public void setShopTypeId(Integer shopTypeId) {
        this.shopTypeId = shopTypeId;
    }
}