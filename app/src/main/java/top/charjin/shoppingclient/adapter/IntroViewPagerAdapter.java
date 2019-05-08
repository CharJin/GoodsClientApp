package top.charjin.shoppingclient.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 首次进入app的介绍活动之FragmentPager适配器
 * 用于和TabLayout联合使用
 *
 * @author char_
 */

public class IntroViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fgm_list = new ArrayList<>();
    private final List<String> fgm_title = new ArrayList<>();

    public IntroViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragmentView(Fragment fg, String title) {
        this.fgm_list.add(fg);
        this.fgm_title.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return fgm_list.get(position);
    }

    @Override
    public int getCount() {
        return fgm_list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        Log.e("jinzhichao", fgm_title.get(position));
        return fgm_title.get(position);
    }
}
