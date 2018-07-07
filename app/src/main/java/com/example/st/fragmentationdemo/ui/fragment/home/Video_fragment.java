package com.example.st.fragmentationdemo.ui.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.st.fragmentationdemo.R;

import com.example.st.fragmentationdemo.adapter.VideoFragmentPagerAdapter;

import com.example.st.fragmentationdemo.base.BaseMainFragment;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

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

public class Video_fragment extends BaseMainFragment {

    public static Video_fragment newInstance() {
        Bundle args = new Bundle();
        Video_fragment fragment = new Video_fragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        initView(view);
        return view;
    }
    private void initView(View view) {
        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        TabLayout mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        mToolbar.setTitle(R.string.video);
        initToolbarNav(mToolbar);
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.video_hot));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.video_entertainment));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.video_fun));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.video_choice));
        mViewPager.setAdapter(new VideoFragmentPagerAdapter(getChildFragmentManager(),
                getString(R.string.video_hot),
                getString(R.string.video_entertainment),
                getString(R.string.video_fun),
                getString(R.string.video_choice)));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * NiceVideoPlayer大窗口点击返回按钮退出
     * @return
     */
    @Override
    public boolean onBackPressedSupport() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) ;
        return super.onBackPressedSupport();
    }
}
