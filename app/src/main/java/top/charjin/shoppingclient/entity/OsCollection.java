package top.charjin.shoppingclient.entity;

import java.io.Serializable;

public class OsCollection implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    /**
     * 外键,用户id
     */
    private Integer userId;
    /**
     * 外键,商品id
     */
    private Integer commodityId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }
}