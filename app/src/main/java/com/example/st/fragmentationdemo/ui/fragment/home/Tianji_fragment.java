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
import com.example.st.fragmentationdemo.adapter.TianjiFragmentAdapter;
import com.example.st.fragmentationdemo.base.BaseMainFragment;

//import com.example.st.fragmentationdemo.adapter.MzituFragmentAdapter;

/**
 * Created by st on 18-2-24.
 */

public class Tianji_fragment extends  BaseMainFragment {
    public static Tianji_fragment newInstance() {
        return new Tianji_fragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_mzitu,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        TabLayout mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        mToolbar.setTitle(R.string.tianji);
        initToolbarNav(mToolbar);
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.dongmanpic));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.mingxingpic));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.meinvpic));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.gaoxiaopic));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.sheyingpic));
        mViewPager.setAdapter(new TianjiFragmentAdapter(getChildFragmentManager(),
                getString(R.string.dongmanpic),
                getString(R.string.mingxingpic),
                getString(R.string.meinvpic),
                getString(R.string.gaoxiaopic),
                getString(R.string.sheyingpic)));
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
