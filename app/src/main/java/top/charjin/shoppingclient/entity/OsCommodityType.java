package top.charjin.shoppingclient.entity;

import java.io.Serializable;

public class OsCommodityType implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    /**
     * 商品类型
     */
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}