package top.charjin.shoppingclient.model;

import top.charjin.shoppingclient.entity.OsShop;

public class CartShopModel extends OsShop {
    private boolean isChecked = false;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
