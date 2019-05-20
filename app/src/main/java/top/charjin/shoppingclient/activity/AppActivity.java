package top.charjin.shoppingclient.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.charjin.shoppingclient.R;
import top.charjin.shoppingclient.data.BottomNavResource;
import top.charjin.shoppingclient.entity.OsUser;
import top.charjin.shoppingclient.utils.HttpUtil;
import top.charjin.shoppingclient.utils.Router;

/**
 * 主活动,滑动介绍页面(即SlideActivity)显示过后,app的视图
 */
public class AppActivity extends AppCompatActivity {

    private MyApplication application;
    private TabLayout mTabLayout;
    private Fragment[] mFragments;

    private ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_activity);

//        application = (MyApplication) getApplication();

//        mImmersionBar = ImmersionBar.with(this);
//        mImmersionBar.init();
        initView();
        new CheckUser().execute();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();
    }

    private void initView() {
        mFragments = BottomNavResource.getFragments();
        mTabLayout = findViewById(R.id.bottom_tab_layout);

        // 添加底部Tab
        View[] tabViews = BottomNavResource.createTabView(this);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                replaceShownFragment(tab.getPosition());
                // Tab 选中之后，改变各个Tab的状态
                for (int i = 0; i < mTabLayout.getTabCount(); i++) {
                    // 获取自定义的Tab
                    View custom_tab = mTabLayout.getTabAt(i).getCustomView();

                    ImageView icon = custom_tab.findViewById(R.id.tab_content_image);
                    TextView text = custom_tab.findViewById(R.id.tab_content_text);
                    // 如果被选中,修改对应样式
                    if (i == tab.getPosition()) {
                        icon.setImageResource(BottomNavResource.mTabResPressed[i]);
                        text.setTextColor(getResources().getColor(android.R.color.black));
                    } else {
                        // 未选中,修改对应样式
                        icon.setImageResource(BottomNavResource.mTabRes[i]);
                        text.setTextColor(getResources().getColor(android.R.color.darker_gray));
                    }
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        for (View tabView : tabViews) mTabLayout.addTab(mTabLayout.newTab().setCustomView(tabView));
    }

    /**
     * 监听之后,Tab改变同时替换显示的Fragment.
     *
     * @param position
     */
    private void replaceShownFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = mFragments[0];
                break;
            case 1:
                fragment = mFragments[1];
                break;
            case 2:
                fragment = mFragments[2];
                break;
            case 3:
                fragment = mFragments[3];
                break;
        }
        // 替换显示的Fragment.
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.home_container, fragment)
                    .commit();
        }
    }


    /**
     * 判断用户是否修改了密码
     */
    private void checkUser() {
        SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        String username = sp.getString("username", null), password = sp.getString("password", null);
        System.out.println(Router.BASE_URL + "user/login?username=" + username + "&password=" + password);
        if (username != null) { // 用户名不为空，检测密码是否修改
            HttpUtil.sendOkHttpRequestByGet(Router.BASE_URL + "user/login?username=" + username + "&password=" + password, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String jsonData = response.body().string();
                    runOnUiThread(() -> {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (jsonData.equals("")) { // 查询结果为空，则密码错误
                            // 进入登录页面.登录页面未选择登录 则user未null，如果登录则重新实例化此对象，并更新SharedPreferences
                            Toast.makeText(AppActivity.this, "密码已修改,请重新登录！", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AppActivity.this, LoginActivity.class));
                        } else {
                            OsUser user = new Gson().fromJson(jsonData, OsUser.class);
                            MyApplication.map.put("user", user);
                        }
                    });
                }
            });
//            if (isFirst) {
//                SharedPreferences.Editor editor = sp.edit();
//                editor.putBoolean("isFirst", false);
//                editor.apply();
//            }
        }


    }


    private static class CheckUser extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}