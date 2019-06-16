package top.charjin.shoppingclient.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.charjin.shoppingclient.R;
import top.charjin.shoppingclient.activity.GoodsActivity;
import top.charjin.shoppingclient.activity.SearchActivity;
import top.charjin.shoppingclient.activity.SearchResultActivity;
import top.charjin.shoppingclient.adapter.GoodsDisplayAdapter;
import top.charjin.shoppingclient.entity.OsGoods;
import top.charjin.shoppingclient.entity.OsRecommend;
import top.charjin.shoppingclient.utils.HttpUtil;
import top.charjin.shoppingclient.utils.JsonUtil;
import top.charjin.shoppingclient.utils.Router;

public class HomeFragment extends BaseFragment {

    private List<OsGoods> goodsList = new ArrayList<>();
    private GoodsDisplayAdapter adapter;
    private View viewHome;
    private RecyclerView rvGoods;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Banner banner_ads;
    private BannerAdsImageLoader imageLoader;
    private NestedScrollView nestedScrollView;
    private RelativeLayout rlHomeSearch;


    //推荐TextView
    private TextView tvRecommend1, tvRecommend2, tvRecommend3, tvRecommend4;
    private boolean isRefresh = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_fragment_main;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewHome = LayoutInflater.from(context).inflate(R.layout.home_fragment_main, container, false);

        initComponent();


        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.app_swipe_circle_color));
        swipeRefreshLayout.setOnRefreshListener(this::refreshGoods);

        initRecommend();

        // RecyclerView
        adapter = new GoodsDisplayAdapter(context, goodsList);


        rvGoods.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rvGoods.setAdapter(adapter);

        initGoodsDisplayData();

        // 检测是否滑到底部，更新出新数据
        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                initGoodsDisplayData();
                isRefresh = false;
                Toast.makeText(this.context, "HomeFragment:已滑到了底!", Toast.LENGTH_SHORT).show();
            }
        });


        // 点击搜索 跳转到新页面
        rlHomeSearch.setOnClickListener(e -> startActivity(new Intent(this.getActivity(), SearchActivity.class)));

        initBannerAdsResource();

        return viewHome;
    }


    private void initComponent() {
        /**
         * 四个推荐信息,其后改为自定义View
         */
        tvRecommend1 = viewHome.findViewById(R.id.tv_home_recommend_text_1);
        tvRecommend2 = viewHome.findViewById(R.id.tv_home_recommend_text_2);
        tvRecommend3 = viewHome.findViewById(R.id.tv_home_recommend_text_3);
        tvRecommend4 = viewHome.findViewById(R.id.tv_home_recommend_text_4);

        swipeRefreshLayout = viewHome.findViewById(R.id.swipe_layout_home);
        rvGoods = viewHome.findViewById(R.id.rv_home_goods);
        nestedScrollView = viewHome.findViewById(R.id.nested_scroll_home);
        rlHomeSearch = viewHome.findViewById(R.id.rl_home_search);
    }


    /**
     * 初始化顶部 4 个推荐搜索的text
     */
    private void initRecommend() {
        HttpUtil.sendOkHttpRequestByGet(Router.BASE_URL + "recommend/getRecommend", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                List<OsRecommend> recommendList = new Gson().fromJson(response.body().string(), new TypeToken<List<OsRecommend>>() {
                }.getType());
                activity.runOnUiThread(() -> {
                    TextView[] arr = {tvRecommend1, tvRecommend2, tvRecommend3, tvRecommend4};
                    for (int i = 0; i < recommendList.size(); i++) {
                        String content = recommendList.get(i).getContent();
                        arr[i].setText(content);
                        arr[i].setOnClickListener(e -> {
                            Intent intent = new Intent(activity, SearchResultActivity.class);
                            intent.putExtra("key", content);
                            startActivity(intent);
                        });
                    }
                });
            }
        });
    }

    private void initGoodsDisplayData() {
        HttpUtil.sendOkHttpRequestByGet(Router.BASE_URL + "goods/getAllGoods", new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                List<OsGoods> goodsListData = JsonUtil.parseJSONObjectInStringToEntityList(response.body().string(), OsGoods.class);
                goodsList.clear();
                goodsList.addAll(goodsListData);

//                isRefresh = true;
//                goodsList.addAll(HomeFragment.this.goodsList);
                activity.runOnUiThread(() -> {
//                    adapter.setGoodsList(HomeFragment.this.goodsList);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(context, "数据已更新", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    /**
     * 下拉刷新后 对数据进行重新加载
     */
    private void refreshGoods() {
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            activity.runOnUiThread(() -> {
                initRecommend();
                initGoodsDisplayData();
                initBannerAdsResource();
                swipeRefreshLayout.setRefreshing(false);
            });
        }
        ).start();
    }


    /**
     * 初始化轮播图 广告视图
     */
    private void initBannerAdsResource() {


//        list_title.add("9090");
//        list_title.add("9090");
//        list_title.add("9090");
//
//        list_path.add(R.drawable.background);
//        list_path.add(R.drawable.app_sleep_icon);
//        list_path.add(R.drawable.home_category_img_foods);

        HttpUtil.sendOkHttpRequestByGet(Router.GOODS_URL + "getBannerGoods", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                assert response.body() != null;
                String jsonData = response.body().string();
                List<OsGoods> bannerGoodsList = JsonUtil.parseJSONObjectInStringToEntityList(jsonData, OsGoods.class);

                List<String> list_title = new ArrayList<>();
                List<String> list_path = new ArrayList<>();
                bannerGoodsList.forEach(goods -> {
                    list_title.add(goods.getGoodsName());
                    list_path.add(goods.getImage());
                });

                activity.runOnUiThread(() -> {
                    imageLoader = new BannerAdsImageLoader() {
                        @Override
                        public void displayImage(Context context, Object path, ImageView imageView) {
                            Glide.with(context.getApplicationContext())
                                    .load(path)
                                    .into(imageView);
                        }
                    };

                    banner_ads = viewHome.findViewById(R.id.banner_home_ads);
                    banner_ads.setImageLoader(imageLoader);
                    banner_ads.setBannerAnimation(Transformer.Default);
                    banner_ads.setBannerTitles(list_title);
                    banner_ads.setDelayTime(3000);
                    banner_ads.isAutoPlay(true);
//        banner_ads.setIndicatorGravity(BannerConfig.CIRCLE_INDICATOR);
                    banner_ads.setImages(list_path)
                            .setOnBannerListener(position -> {
                                Intent intent = new Intent(context, GoodsActivity.class);
                                intent.putExtra("goods", bannerGoodsList.get(position));
                                startActivity(intent);
                            })
                            .start();
                });

            }
        });


    }

    private class BannerAdsImageLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {

        }
    }
}