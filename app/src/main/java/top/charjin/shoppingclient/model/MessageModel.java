package top.charjin.shoppingclient.model;

public class MessageModel {
    private String shopPic;
    private String shopTitle;
    private String latestMsg;

    public MessageModel(String shopPic, String shopTitle, String latestMsg) {
        this.shopPic = shopPic;
        this.shopTitle = shopTitle;
        this.latestMsg = latestMsg;
    }


    public String getShopPic() {
        return shopPic;
    }

    public void setShopPic(String shopPic) {
        this.shopPic = shopPic;
    }

    public String getShopTitle() {
        return shopTitle;
    }

    public void setShopTitle(String shopTitle) {
        this.shopTitle = shopTitle;
    }

    public String getLatestMsg() {
        return latestMsg;
    }

    public void setLatestMsg(String latestMsg) {
        this.latestMsg = latestMsg;
    }
}