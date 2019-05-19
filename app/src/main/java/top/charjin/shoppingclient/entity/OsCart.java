package top.charjin.shoppingclient.entity;

import java.io.Serializable;

public class OsCart implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主外键,用户id
     */
    private Integer userId;
    /**
     * 主外键,商品id
     */
    private Integer goodsId;
    /**
     * 该商品的数量
     */
    private Integer number;
    private String plan;

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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", goodsId=").append(goodsId);
        sb.append(", number=").append(number);
        sb.append(", plan=").append(plan);
        sb.append("]");
        return sb.toString();
    }
}