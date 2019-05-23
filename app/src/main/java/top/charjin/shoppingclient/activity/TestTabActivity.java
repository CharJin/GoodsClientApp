package top.charjin.shoppingclient.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import top.charjin.shoppingclient.R;
import top.charjin.shoppingclient.adapter.IntroViewPagerAdapter;
import top.charjin.shoppingclient.fragment.CartFragment;
import top.charjin.shoppingclient.fragment.ProfileFragment;

public class TestTabActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_viewpaper);
        tabLayout = findViewById(R.id.tablayout);

        viewPager = findViewById(R.id.viewpager);
        Fragment[] tabFgs = {new ProfileFragment(), new CartFragment()};
        String[] tabFgTitles = {"Text", "Button"};

        IntroViewPagerAdapter adapter = new IntroViewPagerAdapter(getSupportFragmentManager());
        for (int i = 0; i < tabFgs.length; i++) {
            adapter.addFragmentView(tabFgs[i], tabFgTitles[i]);
        }
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
