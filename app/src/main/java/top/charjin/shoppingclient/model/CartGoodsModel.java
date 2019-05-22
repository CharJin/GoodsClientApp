package top.charjin.shoppingclient.model;

import top.charjin.shoppingclient.entity.OsGoods;

public class CartGoodsModel extends OsGoods {
    private int goodsNum = 1;
    private boolean isChecked = false;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }
}
