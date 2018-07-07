package com.example.st.fragmentationdemo.ui.fragment.mzitu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.st.fragmentationdemo.R;
import com.example.st.fragmentationdemo.adapter.MzituFragmentAdapter;
import com.example.st.fragmentationdemo.base.BaseMainFragment;
import com.example.st.fragmentationdemo.ui.fragment.home.HomeFragment;

/**
 * Created by st on 18-2-24.
 */

public class MzituFragment extends BaseMainFragment {
    public static MzituFragment newInstance() {
        return new MzituFragment();
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
        mToolbar.setTitle(R.string.mzitu);
        initToolbarNav(mToolbar);
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.xinggan));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.japan));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.taiwan));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.mm));
//        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.zipai));
        mViewPager.setAdapter(new MzituFragmentAdapter(getChildFragmentManager(),
                getString(R.string.xinggan),
                getString(R.string.japan),
                getString(R.string.taiwan),
                getString(R.string.mm)));
//                getString(R.string.zipai)));
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
