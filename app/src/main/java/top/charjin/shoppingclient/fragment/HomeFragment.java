package top.charjin.shoppingclient.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import top.charjin.shoppingclient.R;
import top.charjin.shoppingclient.adapter.HomeGoodsAdapter;
import top.charjin.shoppingclient.model.GoodsModel;

public class HomeFragment extends Fragment {

    List<GoodsModel> data = new ArrayList<>();
    HomeGoodsAdapter adapter;
    private Context context;
    private View viewHome;
    private RecyclerView rvGoods;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Banner banner_ads;
    private BannerAdsImageLoader imageLoader;
    private NestedScrollView nestedScrollView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = container.getContext();
        viewHome = inflater.inflate(R.layout.home_fragment_main, container, false);

        swipeRefreshLayout = viewHome.findViewById(R.id.swipe_layout_home);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.app_swipe_circle_color));
        swipeRefreshLayout.setOnRefreshListener(this::refreshGoods);

        // RecyclerView
        initRecommendGoods();
        adapter = new HomeGoodsAdapter(data);

        rvGoods = viewHome.findViewById(R.id.rv_home_goods);
        rvGoods.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rvGoods.setAdapter(adapter);

        // NestedScrollView
        nestedScrollView = viewHome.findViewById(R.id.nested_scroll_home);
        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                initRecommendGoods();
            }
        });


        initBannerAdsResource();

        return viewHome;
    }

    private void intiComponent() {

    }

    private void initRecommendGoods() {
        String[] des = new String[]{"这是描述:fdushuafhduisafhuidhsiufdhsifhdiushfiuOJI", "84938294",
                "999999ffffffffohguidshghfdsds"};
        data.clear();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            int n = random.nextInt(300);
            int j = random.nextInt(3);
            data.add(new GoodsModel("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557425251324&di=07fccf2fc756baeb13c222eff210bc46&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20180504%2Fcc178a20314c4d409dbee7b5419d796c.jpeg"
                    , des[j], n + ""));
        }
    }

    private void refreshGoods() {
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getActivity().runOnUiThread(() -> {
                initRecommendGoods();
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            });
        }
        ).start();
    }


    /**
     * 初始化轮播图 广告视图
     */
    private void initBannerAdsResource() {
        List<Integer> list_path = new ArrayList<>();
        List<String> list_title = new ArrayList<>();
        list_path.add(R.drawable.background);
        list_path.add(R.drawable.sleep_icon);
        list_path.add(R.drawable.home_category_img_foods);

        list_title.add("9090");
        list_title.add("9090");
        list_title.add("9090");

        imageLoader = new BannerAdsImageLoader();

        banner_ads = viewHome.findViewById(R.id.banner_home_ads);
//        banner_ads.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner_ads.setImageLoader(imageLoader);
        banner_ads.setBannerAnimation(Transformer.Default);
        banner_ads.setBannerTitles(list_title);
        banner_ads.setDelayTime(3000);
        banner_ads.isAutoPlay(true);
//        banner_ads.setIndicatorGravity(BannerConfig.CIRCLE_INDICATOR);
        banner_ads.setImages(list_path)
//                .setOnBannerListener(this)
                .start();
    }

    private class BannerAdsImageLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context.getApplicationContext())
                    .load(path)
                    .into(imageView);
        }
    }
}