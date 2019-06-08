package top.charjin.shoppingclient.entity;

import java.io.Serializable;

public class OsGoods implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer goodsId;
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
    private String goodsName;
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

    private OsShop shop;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer id) {
        this.goodsId = id;
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

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String name) {
        this.goodsName = name;
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

    public OsShop getShop() {
        return shop;
    }

    public void setShop(OsShop shop) {
        this.shop = shop;
    }
}