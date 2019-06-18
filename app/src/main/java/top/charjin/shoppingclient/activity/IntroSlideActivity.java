package top.charjin.shoppingclient.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import top.charjin.shoppingclient.R;
import top.charjin.shoppingclient.adapter.IntroSliderAdapter;
import top.charjin.shoppingclient.utils.SharedPreferencesUtil;

public class IntroSlideActivity extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private Button btnPre, btnNext;

    private TextView[] mDots;
    private int mCurrentPageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置全屏显示
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (!SharedPreferencesUtil.isFirstLaunched(this)) {
            startActivity(new Intent(this, AppActivity.class));
            this.finish();
        }

        setContentView(R.layout.intro_activity_main);

        btnPre = findViewById(R.id.btnPre);
        btnNext = findViewById(R.id.btnNext);

        mSlideViewPager = findViewById(R.id.slideViewpager);
        mDotLayout = findViewById(R.id.mDotLayout);

        mSlideViewPager.setAdapter(new IntroSliderAdapter(this));

        addDotsIndicator(mCurrentPageIndex);
        mSlideViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onPageSelected(int i) {
                mCurrentPageIndex = i;
                if (i == 0) btnPre.setVisibility(View.INVISIBLE);
                else {
                    btnPre.setVisibility(View.VISIBLE);
                    if (i == mDots.length - 1) {
                        btnNext.setText("进入");
                    } else {
                        btnPre.setText("上一页");
                        btnNext.setText("下一页");
                    }
                }
                addDotsIndicator(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        btnPre.setVisibility(View.INVISIBLE);


        // OnClickListener
        btnNext.setOnClickListener(e -> {
            mCurrentPageIndex++;
            if (mCurrentPageIndex < 3) { // not the last page.
                mSlideViewPager.setCurrentItem(mCurrentPageIndex);
                addDotsIndicator(mCurrentPageIndex);
            } else {
                btnNext.setOnClickListener(null);
                SharedPreferencesUtil.setLoginRecord(this);
                Intent intent = new Intent(this, AppActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnPre.setOnClickListener(e -> {
            mSlideViewPager.setCurrentItem(--mCurrentPageIndex);
            addDotsIndicator(mCurrentPageIndex);
        });
    }


    /**
     * 添加底部的指示点
     */
    private void addDotsIndicator(int position) {

        mDots = new TextView[3];
        mDotLayout.removeAllViews();
        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));

            mDotLayout.addView(mDots[i]);
        }
        // make the dot of the current page white.
        mDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
    }


}
