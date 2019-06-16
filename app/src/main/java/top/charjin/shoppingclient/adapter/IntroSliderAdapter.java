package top.charjin.shoppingclient.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import top.charjin.shoppingclient.R;

/**
 * 首次进入app的介绍活动之Pager适配器
 *
 * @author char_
 */
public class IntroSliderAdapter extends PagerAdapter {

    private Context context;

    private int[] slide_pager_bg = {R.drawable.app_tab_eat_bg, R.drawable.app_tab_sleep_bg, R.drawable.app_tab_walk_bg};
    private int[] slide_images = {R.drawable.app_eat_icon, R.drawable.app_sleep_icon, R.drawable.app_code_icon};
    private String[] slide_headings = {"吃啥。。", "住啥。。", "干啥。。"};
    private String[] slide_decs = {
            "之丑陋，凑凑页面!...."
            , "之丑陋，凑凑页面!...."
            , "之丑陋，凑凑页面!...."};

    public IntroSliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        View view = layoutInflater.inflate(R.layout.intro_fragment_item, container, false);

//        view.setBackground(container.getResources().getDrawable(slide_pager_bg[position]));

        ImageView ivSlideBg = view.findViewById(R.id.iv_app_slide_bg);
        ImageView ivSlideHeader = view.findViewById(R.id.iv_app_slide_image);
        TextView tvSlideDes = view.findViewById(R.id.tv_app_slide_heading);
        TextView slideDescription = view.findViewById(R.id.tv_app_slide_desc);

        ivSlideBg.setImageResource(slide_pager_bg[position]);
        ivSlideHeader.setImageResource(slide_images[position]);
        tvSlideDes.setText(slide_headings[position]);
        slideDescription.setText(slide_decs[position]);

        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
