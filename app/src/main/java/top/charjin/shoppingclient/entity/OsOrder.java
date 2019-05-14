package top.charjin.shoppingclient.entity;

import java.io.Serializable;
import java.util.Date;

public class OsOrder implements Serializable {
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
    /**
     * 订单建立时间
     */
    private Date orderDate;
    /**
     * 订单是否支付
     */
    private Boolean isPaid;

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

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }
}