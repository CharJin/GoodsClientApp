package top.charjin.shoppingclient.entity;

import java.io.Serializable;

public class OsPayBusiness implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 自增id
     */
    private Integer payId;
    /**
     * 支付商户名称
     */
    private String name;

    public Integer getPayId() {
        return payId;
    }

    public void setPayId(Integer payId) {
        this.payId = payId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", payId=").append(payId);
        sb.append(", name=").append(name);
        sb.append("]");
        return sb.toString();
    }
}