package top.charjin.shoppingclient.data;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import top.charjin.shoppingclient.R;
import top.charjin.shoppingclient.fragment.CartFragment;
import top.charjin.shoppingclient.fragment.HomeFragment;
import top.charjin.shoppingclient.fragment.MessageFragment;
import top.charjin.shoppingclient.fragment.ProfileFragment;

/**
 * @author char_
 * 用于底部导航视图资源的获取与引用
 */
public class BottomNavResource {
    // 底部Tab的常态样式
    public static final int[] mTabResNormal = new int[]{R.drawable.home_light, R.drawable.message_light, R.drawable.cart_light, R.drawable.my_light};
    // 底部Tab按下之后的样式
    public static final int[] mTabResSelected = new int[]{R.drawable.home_fill_light_press, R.drawable.message_fill_light_press, R.drawable.cart_fill_light_press, R.drawable.my_fill_light_press};

    private static final int TAB_COUNT = 4;
    // 底部各Tab显示的文本信息
    private static final String[] mTabTitle = new String[]{"首页", "消息", "购物车", "我的"};


    /**
     * 获取各个Tab所对应的Fragment.
     */
    public static Fragment[] getFragments() {
        return new Fragment[]{new HomeFragment(), new MessageFragment(),
                new CartFragment(), new ProfileFragment()};
    }


    /**
     * 实例并获取所有的Tab.
     *
     * @param context
     * @return
     */
    public static View[] createTabView(Context context) {
        View[] tabViews = new View[BottomNavResource.mTabTitle.length];
        View tabView;
        for (int i = 0; i < tabViews.length; i++) {
            tabView = LayoutInflater.from(context).inflate(R.layout.app_tablayout_item, null);
            ImageView tabImage = tabView.findViewById(R.id.app_tab_content_image);
            TextView tabText = tabView.findViewById(R.id.app_tab_content_text);

            tabImage.setImageResource(BottomNavResource.mTabResNormal[i]);
            tabText.setText(mTabTitle[i]);
            tabViews[i] = tabView;
        }
        return tabViews;
    }
}