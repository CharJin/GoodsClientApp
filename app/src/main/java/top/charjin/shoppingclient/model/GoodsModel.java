package top.charjin.shoppingclient.model;

public class GoodsModel {
    private String pic;
    private String title;
    private String price;

    public GoodsModel(String pic, String title, String price) {
        this.pic = pic;
        this.title = title;
        this.price = price;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
