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
import top.charjin.shoppingclient.fragment.ProfileFragment;

/**
 * @author char_
 * 用于底部导航视图资源的获取与引用
 */
public class BottomNavResource {
    // 底部Tab的常态样式
    public static final int[] mTabRes = new int[]{R.drawable.tab_home_selector, R.drawable.tab_message_selector,
            R.drawable.tab_cart_selector, R.drawable.tab_profile_selector};
    // 底部Tab按下之后的样式
    public static final int[] mTabResPressed = new int[]{R.drawable.ic_tab_strip_icon_feed_selected, R.drawable.ic_tab_strip_icon_category_selected,
            R.drawable.ic_tab_strip_icon_pgc_selected, R.drawable.ic_tab_strip_icon_profile_selected};
    private static final int TAB_COUNT = 4;
    // 底部各Tab显示的文本信息
    private static final String[] mTabTitle = new String[]{"首页", "消息", "购物车", "我的"};


    /**
     * 获取各个Tab所对应的Fragment.
     */
    public static Fragment[] getFragments() {
        return new Fragment[]{new HomeFragment(), new ProfileFragment(),
                new CartFragment(), new ProfileFragment()};
    }


    /**
     * 获取所有的Tab.
     *
     * @param context
     * @return
     */
    public static View[] createTabView(Context context) {
        View[] tabViews = new View[BottomNavResource.mTabTitle.length];
        View tabView;
        for (int i = 0; i < tabViews.length; i++) {
            tabView = LayoutInflater.from(context).inflate(R.layout.app_tablayout_item, null);
            ImageView tabImage = tabView.findViewById(R.id.tab_content_image);
            TextView tabText = tabView.findViewById(R.id.tab_content_text);

            tabImage.setImageResource(BottomNavResource.mTabRes[i]);
            tabText.setText(mTabTitle[i]);
            tabViews[i] = tabView;
        }
        return tabViews;
    }
}