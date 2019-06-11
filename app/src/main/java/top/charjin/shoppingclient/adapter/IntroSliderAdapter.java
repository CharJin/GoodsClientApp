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

    private int[] slide_images = {R.drawable.eat_icon, R.drawable.sleep_icon, R.drawable.code_icon};
    private String[] slide_headings = {"EAT", "SLEEP", "CODE"};
    private String[] slide_descs = {
            "测试数据\nfdsafjidfdsafdsa"
            , "54325432543254325432543\n都是测试数据\nfdsafdsafd"
            , "54325555555555555544444444444444444444\nfdsafdsa\n就是测试数据"};

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
        ImageView slideImageView = view.findViewById(R.id.slide_image);
        TextView slideHeading = view.findViewById(R.id.slide_heading);
        TextView slideDescription = view.findViewById(R.id.slide_desc);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_descs[position]);

        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
