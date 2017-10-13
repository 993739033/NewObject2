package com.example.mnkj.newobject.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.view.menu.MenuBuilder;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mnkj.newobject.Base.BaseActivity;
import com.example.mnkj.newobject.R;
import com.example.mnkj.newobject.Fragment.Fragment_HomePage;
import com.example.mnkj.newobject.Fragment.Fragment_Classify;
import com.example.mnkj.newobject.Fragment.Fragment_Search;
import com.example.mnkj.newobject.Fragment.Fragment_Statistics;
import com.example.mnkj.newobject.Fragment.Fragment_Account;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    private Menu mNavMenu;

    @Bind(R.id.vp_container)
    ViewPager vpContainer;
    @Bind(R.id.tab_container)
    TabLayout tabContainer;
    @Bind(R.id.main_toolbar_tv_title)
    TextView tv_title;
    long firstTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        //通过新建MenuBuilder对象得到一个Menu对象
        mNavMenu = new MenuBuilder(this);

        //使用菜单填充对象加载Menu布局文件
        getMenuInflater().inflate(R.menu.nav_bottom_menu, mNavMenu);

        //将适配器和ViewPager容器进行绑定
        vpContainer.setAdapter(new MyAdapter(getSupportFragmentManager()));

        //将TabLayout对象和ViewPager对象进行绑定
        tabContainer.setupWithViewPager(vpContainer);

        vpContainer.setOffscreenPageLimit(5);//设置最大缓存数量

        //设置每一个tab选项卡
        for (int i = 0; i < mNavMenu.size(); i++) {
            //获取每一个tab选项卡
            TabLayout.Tab tab = tabContainer.getTabAt(i);

            //获取每一个menu选项
            MenuItem item = mNavMenu.getItem(i);


            //将数据和控件关联
            tab.setCustomView(getTabView(item, i));

        }

        vpContainer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        tv_title.setText("首页");
                        break;
                    case 1:
                        tv_title.setText("分类");
                        break;
                    case 2:
                        tv_title.setText("搜索");
                        break;
                    case 3:
                        ((Fragment_Statistics) ((MyAdapter) vpContainer.getAdapter()).getFragments().get(position)).animate();//统计表动画效果
                        tv_title.setText("统计");
                        break;
                    case 4:
                        tv_title.setText("账号");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private View getTabView(MenuItem item, int position) {
        //将布局文件转换成View对象
        View view = View.inflate(this, R.layout.nav_item, null);

        //获取每一个自定义的tab
        TextView tab = (TextView) view.findViewById(R.id.tv_nav_item);

        //设置选项卡文本
        tab.setText(item.getTitle());

        //设置选项卡图标
        tab.setCompoundDrawablesWithIntrinsicBounds(null, item.getIcon(), null, null);

        return view;
    }

    class MyAdapter extends FragmentPagerAdapter {
        public List<Fragment> getFragments() {
            return fragments;
        }

        private List<Fragment> fragments = new ArrayList<>();

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new Fragment_HomePage();
                    fragments.add(fragment);
                    break;
                case 1:
                    fragment = new Fragment_Classify();
                    fragments.add(fragment);
                    break;
                case 2:
                    fragment = new Fragment_Search();
                    fragments.add(fragment);
                    break;
                case 3:
                    fragment = new Fragment_Statistics();
                    fragments.add(fragment);
                    break;
                case 4:
                    fragment = new Fragment_Account();
                    fragments.add(fragment);
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return mNavMenu.size();
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();

            if (secondTime - firstTime > 800) {// 如果两次按键时间间隔大于800毫秒，则不退出
                Toast.makeText(MainActivity.this, "再按一次退出程序...",
                        Toast.LENGTH_SHORT).show();
                firstTime = secondTime;// 更新firstTime
                return true;
            } else {
                System.exit(0);// 否则退出程序
            }
        }
        return super.onKeyUp(keyCode, event);
    }
}
