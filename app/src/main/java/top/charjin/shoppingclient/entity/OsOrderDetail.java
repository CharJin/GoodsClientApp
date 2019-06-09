package top.charjin.shoppingclient.entity;

import java.io.Serializable;

public class OsOrderDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    private Integer orderDetailId;
    /**
     * 外键,来自Order表
     */
    private Integer orderId;
    /**
     * 外键,商品id
     */
    private Integer goodsId;

    /**
     * 订单编号,与商品id构成唯一键
     */
    private String orderNo;

    /**
     * 商品名称(商品可能被商家删除,故需做记录)
     */
    private String goodsName;
    /**
     * 商品价格(商品可能被商家删除,故需做记录)
     */
    private Double goodsPrice;
    /**
     * 用户所选的商品的型号，如颜色、规格、包装等(保留字段)
     */
    private String goodsMode;
    /**
     * JSON格式保存,记录商品完整的规格信息(规格表暂未添加,保留该字段)
     */
    private String goodsModeParams;
    /**
     * 购买的数量
     */
    private Integer goodsNum;
    /**
     * 该商品的最终价
     */
    private Double goodsAmountTotal;
    /**
     * 用户备注信息(限定80个中文字符)
     */
    private String remark;
    /**
     * 记录商品是否被店家删除
     */
    private Boolean isExists;
    private OsGoods goods;

    public OsOrderDetail() {
    }

    public OsOrderDetail(Integer goodsId, String orderNo, String goodsName, Double goodsPrice, String goodsMode, String goodsModeParams, Integer goodsNum, Double goodsAmountTotal, String remark) {
        this.goodsId = goodsId;
        this.orderNo = orderNo;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.goodsMode = goodsMode;
        this.goodsModeParams = goodsModeParams;
        this.goodsNum = goodsNum;
        this.goodsAmountTotal = goodsAmountTotal;
        this.remark = remark;
    }

    public Integer getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Integer orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsMode() {
        return goodsMode;
    }

    public void setGoodsMode(String goodsMode) {
        this.goodsMode = goodsMode;
    }

    public String getGoodsModeParams() {
        return goodsModeParams;
    }

    public void setGoodsModeParams(String goodsModeParams) {
        this.goodsModeParams = goodsModeParams;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public Double getGoodsAmountTotal() {
        return goodsAmountTotal;
    }

    public void setGoodsAmountTotal(Double goodsAmountTotal) {
        this.goodsAmountTotal = goodsAmountTotal;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getIsExists() {
        return isExists;
    }

    public void setIsExists(Boolean isExists) {
        this.isExists = isExists;
    }

    public OsGoods getGoods() {
        return goods;
    }

    public void setGoods(OsGoods goods) {
        this.goods = goods;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", orderDetailId=").append(orderDetailId);
        sb.append(", orderId=").append(orderId);
        sb.append(", goodsId=").append(goodsId);
        sb.append(", goodsName=").append(goodsName);
        sb.append(", goodsPrice=").append(goodsPrice);
        sb.append(", goodsMode=").append(goodsMode);
        sb.append(", goodsModeParams=").append(goodsModeParams);
        sb.append(", goodsNum=").append(goodsNum);
        sb.append(", goodsAmountTotal=").append(goodsAmountTotal);
        sb.append(", remark=").append(remark);
        sb.append(", isExists=").append(isExists);
        sb.append("]");
        return sb.toString();
    }
}