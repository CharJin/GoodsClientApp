package top.charjin.shoppingclient.entity;

import java.io.Serializable;

public class OsCommodityReview implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    /**
     * 外键,商品id
     */
    private Integer commodityId;
    /**
     * 评论内容,80个汉字以内
     */
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}