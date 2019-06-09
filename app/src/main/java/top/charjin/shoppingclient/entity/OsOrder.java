package top.charjin.shoppingclient.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class OsOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer orderId;
    /**
     * 外键,用户id
     */
    private Integer userId;
    /**
     * 外键,商店id，一次提交订单按店铺生成
     */
    private Integer shopId;
    /**
     * 地址id(来自地址表)
     */
    private Integer addressId;
    /**
     * 订单编号(保留字段 不使用,订单时间+用户id+商品id)
     */
    private String orderNo;
    /**
     * 订单总金额
     */
    private Double orderAmountTotal;
    /**
     * 订单实际支付金额
     */
    private Double orderAmountActual;
    /**
     * 运费
     */
    private Integer orderFreight;
    /**
     * 订单创建时间
     */
    private Timestamp orderCreateTime;
    /**
     * 订单支付时间
     */
    private Timestamp orderPayTime;
    /**
     * 订单完成事件(用户确认收货后)
     */
    private Timestamp orderFulfilTime;
    /**
     * 订单状态(0:待付款 1:待发货 2:待收货 3:待评论)
     */
    private Integer orderStatus;
    /**
     * 物流单号
     */
    private String logisticsNo;
    /**
     * shopId 来自支付渠道表,支付方式(支付宝，信用卡，各种银行等)
     */
    private Integer payChannel;

    public OsOrder() {
    }

    public OsOrder(Integer userId, Integer shopId, Integer addressId, String orderNo, Double orderAmountTotal, Double orderAmountActual, Timestamp orderCreateTime, Timestamp orderPayTime, Integer orderStatus, Integer payChannel) {
        this.userId = userId;
        this.shopId = shopId;
        this.addressId = addressId;
        this.orderNo = orderNo;
        this.orderAmountTotal = orderAmountTotal;
        this.orderAmountActual = orderAmountActual;
        this.orderCreateTime = orderCreateTime;
        this.orderPayTime = orderPayTime;
        this.orderStatus = orderStatus;
        this.payChannel = payChannel;
    }

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

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Double getOrderAmountTotal() {
        return orderAmountTotal;
    }

    public void setOrderAmountTotal(Double orderAmountTotal) {
        this.orderAmountTotal = orderAmountTotal;
    }

    public Double getOrderAmountActual() {
        return orderAmountActual;
    }

    public void setOrderAmountActual(Double orderAmountActual) {
        this.orderAmountActual = orderAmountActual;
    }

    public Integer getOrderFreight() {
        return orderFreight;
    }

    public void setOrderFreight(Integer orderFreight) {
        this.orderFreight = orderFreight;
    }

    public Timestamp getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(Timestamp orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public Timestamp getOrderPayTime() {
        return orderPayTime;
    }

    public void setOrderPayTime(Timestamp orderPayTime) {
        this.orderPayTime = orderPayTime;
    }

    public Timestamp getOrderFulfilTime() {
        return orderFulfilTime;
    }

    public void setOrderFulfilTime(Timestamp orderFulfilTime) {
        this.orderFulfilTime = orderFulfilTime;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public Integer getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(Integer payChannel) {
        this.payChannel = payChannel;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", orderId=").append(orderId);
        sb.append(", userId=").append(userId);
        sb.append(", shopId=").append(shopId);
        sb.append(", addressId=").append(addressId);
        sb.append(", orderNo=").append(orderNo);
        sb.append(", orderAmountTotal=").append(orderAmountTotal);
        sb.append(", orderAmountActual=").append(orderAmountActual);
        sb.append(", orderFreight=").append(orderFreight);
        sb.append(", orderCreateTime=").append(orderCreateTime);
        sb.append(", orderPayTime=").append(orderPayTime);
        sb.append(", orderFulfilTime=").append(orderFulfilTime);
        sb.append(", orderStatus=").append(orderStatus);
        sb.append(", logisticsNo=").append(logisticsNo);
        sb.append(", payChannel=").append(payChannel);
        sb.append("]");
        return sb.toString();
    }
}