package top.charjin.shoppingclient.entity;

import java.io.Serializable;

public class OsSearchHistory implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    /**
     * 外键,用户id
     */
    private Integer userId;
    /**
     * 搜索内容
     */
    private String content;
    /**
     * 搜索权重
     */
    private Short weight;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Short getWeight() {
        return weight;
    }

    public void setWeight(Short weight) {
        this.weight = weight;
    }
}