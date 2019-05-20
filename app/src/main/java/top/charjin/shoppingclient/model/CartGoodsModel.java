package top.charjin.shoppingclient.model;

import top.charjin.shoppingclient.entity.OsGoods;

public class CartGoodsModel extends OsGoods {
    private boolean isChecked = false;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
