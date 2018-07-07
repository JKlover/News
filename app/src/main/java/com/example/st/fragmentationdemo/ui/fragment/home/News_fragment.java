package com.example.st.fragmentationdemo.ui.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.example.st.fragmentationdemo.R;
import com.example.st.fragmentationdemo.adapter.NewsFragmentPagerAdapter;
import com.example.st.fragmentationdemo.adapter.VideoFragmentPagerAdapter;
import com.example.st.fragmentationdemo.base.BaseHomeFragment;
import com.example.st.fragmentationdemo.base.BaseMainFragment;

/**
 * 　　　　　　　　┏┓　　　┏┓+ +
 * 　　　　　　　┏┛┻━━━┛┻┓ + +
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃ ++ + + +
 * 　　　　       	██ ━██  ┃+
 * 　　　　　　　┃　　　　　　　┃ +
 * 　　　　　　　┃　　　┻　　　┃
 * 　　　　　　　┃　　　　　　　┃ + +
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃ + + + +
 * 　　　　　　　　　┃　　　┃　　　　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃ + 　　　　神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃　　+
 * 　　　　　　　　　┃　 　　┗━━━┓ + +
 * 　　　　　　　　　┃ 　　　　　　　┣┓
 * 　　　　　　　　　┃ 　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛ + + + +
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛+ + + +
 * <p>
 * Created by st on 2018/3/11.
 */

public class News_fragment extends BaseMainFragment {

    private boolean currentFlag = false;

    public static News_fragment newInstance() {
        Bundle args = new Bundle();
        News_fragment fragment = new News_fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        TabLayout mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        mToolbar.setTitle(R.string.news);
        initToolbarNav(mToolbar);
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.news_a));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.news_b));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.news_c));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.news_d));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.news_e));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.news_f));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.news_g));
        mViewPager.setAdapter(new NewsFragmentPagerAdapter(getChildFragmentManager(),
                getString(R.string.news_a),
                getString(R.string.news_b),
                getString(R.string.news_c),
                getString(R.string.news_d),
                getString(R.string.news_e),
                getString(R.string.news_f),
                getString(R.string.news_g)));
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
