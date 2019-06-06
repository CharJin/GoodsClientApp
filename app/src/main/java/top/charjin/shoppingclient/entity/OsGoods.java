package top.charjin.shoppingclient.entity;

import java.io.Serializable;

public class OsGoods implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    /**
     * 商品(id)
     */
    private Integer goodsTypeId;
    /**
     * 店铺(id)
     */
    private Integer shopId;
    /**
     * 商品名
     */
    private String name;
    /**
     * 商品展示图片,存放路径,多图片使用分隔符"^^^"
     */
    private String image;
    /**
     * 价格
     */
    private Double price;
    /**
     * 销量
     */
    private Short salesVolume;
    /**
     * 商品地区
     */
    private String region;
    /**
     * 商品描述信息
     */
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(Integer goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Short getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(Short salesVolume) {
        this.salesVolume = salesVolume;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}