package top.charjin.shoppingclient.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import top.charjin.shoppingclient.R;
import top.charjin.shoppingclient.adapter.IntroSliderAdapter;

public class IntroSlideActivity extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private Button btnPre, btnNext;

    private TextView[] mDots;
    private int mCurrentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        if (!SharedPreferencesUtil.isFirstLaunched(this)) {
            startActivity(new Intent(this, AppActivity.class));
            this.finish();
//        }

        setContentView(R.layout.intro_activity_main);

        btnPre = findViewById(R.id.btnPre);
        btnNext = findViewById(R.id.btnNext);

        mSlideViewPager = findViewById(R.id.slideViewpager);
        mDotLayout = findViewById(R.id.mDotLayout);

        IntroSliderAdapter introSliderAdapter = new IntroSliderAdapter(this);
        mSlideViewPager.setAdapter(introSliderAdapter);

        addDotsIndicator(mCurrentPage);
        mSlideViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onPageSelected(int i) {
                mCurrentPage = i;
                if (i == 0) btnPre.setVisibility(View.INVISIBLE);
                else {
                    btnPre.setVisibility(View.VISIBLE);
                    if (i == mDots.length - 1) btnNext.setText("FINISH");
                    else {
                        btnPre.setText("PRE");
                        btnNext.setText("NEXT");
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
            mCurrentPage++;
            if (mCurrentPage < 3) { // not the last page.
                mSlideViewPager.setCurrentItem(mCurrentPage);
                addDotsIndicator(mCurrentPage);
            } else {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnPre.setOnClickListener(e -> {
            mSlideViewPager.setCurrentItem(--mCurrentPage);
            addDotsIndicator(mCurrentPage);
        });
    }

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
