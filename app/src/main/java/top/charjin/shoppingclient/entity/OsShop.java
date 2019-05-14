package top.charjin.shoppingclient.entity;

import java.io.Serializable;

public class OsShop implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    /**
     * 商品类型(id)
     */
    private Integer shopTypeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getShopTypeId() {
        return shopTypeId;
    }

    public void setShopTypeId(Integer shopTypeId) {
        this.shopTypeId = shopTypeId;
    }
}