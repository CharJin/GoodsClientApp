package top.charjin.shoppingclient.entity;

import java.io.Serializable;
import java.util.Date;

public class OsOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer orderId;
    /**
     * 外键,用户id
     */
    private Integer userId;
    /**
     * 外键,商品id
     */
    private Integer goodsId;
    /**
     * 商品数量
     */
    private Integer goodsNumber;
    /**
     * 订单编号(保留字段 不使用,订单时间+用户id+商品id)
     */
    private String orderNumber;
    /**
     * 订单总金额
     */
    private Double orderAmount;
    /**
     * 订单状态(0:待付款 1:待发货 2:待收货 3:待评论)
     */
    private Byte orderStatus;
    /**
     * 订单创建时间
     */
    private Date createDate;
    /**
     * 订单支付时间
     */
    private Date payTime;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Byte getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Byte orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", orderId=").append(orderId);
        sb.append(", userId=").append(userId);
        sb.append(", goodsId=").append(goodsId);
        sb.append(", goodsNumber=").append(goodsNumber);
        sb.append(", orderNumber=").append(orderNumber);
        sb.append(", orderAmount=").append(orderAmount);
        sb.append(", orderStatus=").append(orderStatus);
        sb.append(", createDate=").append(createDate);
        sb.append(", payTime=").append(payTime);
        sb.append("]");
        return sb.toString();
    }
}