package top.charjin.shoppingclient;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import top.charjin.shoppingclient.adapter.ViewPagerAdapter;
import top.charjin.shoppingclient.fragment.ButtonFragment;
import top.charjin.shoppingclient.fragment.TextFragment;

public class LoginActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_viewpaper);
        tabLayout = findViewById(R.id.tablayout);
        appBarLayout = findViewById(R.id.appbar_layout);

        viewPager = findViewById(R.id.viewpager);
        Fragment[] tabFgs = {new TextFragment(), new ButtonFragment()};
        String[] tabFgTitles = {"Text", "Button"};

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        for (int i = 0; i < tabFgs.length; i++) {
            adapter.addFragmentView(tabFgs[i], tabFgTitles[i]);
        }
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
