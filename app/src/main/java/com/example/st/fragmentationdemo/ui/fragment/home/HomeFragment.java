package com.example.st.fragmentationdemo.ui.fragment.home;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.st.fragmentationdemo.R;
import com.example.st.fragmentationdemo.base.BaseMainFragment;
import com.example.st.fragmentationdemo.base.MySupportFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by st on 18-2-13.
 */

public class HomeFragment extends BaseMainFragment {
    private Toolbar mToolbar;
    private BottomBar mBottomBar;


    private List<BaseMainFragment> mBaseFragment;
    private int position;
    private Fragment mContent;

    
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
//        动态改动 当前Fragment的动画
//        setFragmentAnimator(fragmentAnimator);

        return view;
    }

    private void initFragment() {
        mBaseFragment = new ArrayList<>();
        mBaseFragment.add(new News_fragment());//常用框架Fragment
        mBaseFragment.add(new Video_fragment());//第三方Fragment
        mBaseFragment.add(new Tianji_fragment());//自定义控件Fragment
        mBaseFragment.add(new Person_fragment());//自定义控件Fragment
    }


    private void initView(View view) {
//        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
//        mToolbar.setTitle(R.string.home);
//        initToolbarNav(mToolbar, true);
//
        mBottomBar=view.findViewById(R.id.bottomBar);
        mBottomBar.setOnTabSelectListener(tabId -> {
//                Object ob=null;
            switch (tabId) {
                case R.id.tab_a:
//                        ob  = new News_fragment();
                    position=0;
                    break;
                case R.id.tab_b:
//                        ob  = new Video_fragment();
                    position=1;
                    break;
                case R.id.tab_c:
                    position=2;
                    break;
                case R.id.tab_d:
                    position=3;
                    break;
            }
            //根据位置得到对应的Fragment
            BaseMainFragment to = getFragment();
            //替换
            switchFrament(mContent,to);
//                getChildFragmentManager().beginTransaction().replace(R.id.fl_tab_container,(Fragment) ob).commit();
        });

//        mBottomBar.setOnTabReselectListener(new OnTabReselectListener() {
//            @Override
//            public void onTabReSelected(@IdRes int tabId) {
//                getChildFragmentManager().beginTransaction().replace(R.id.fl_tab_container,new News_fragment()).commit();
//            }
//        });

    }

    private void switchFrament(Fragment from, BaseMainFragment to) {
        if(from != to){
            mContent = to;
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            //才切换
            //判断有没有被添加
            if(!to.isAdded()){
                //to没有被添加
                //from隐藏
                if(from != null){
                    ft.hide(from);
                }
                //添加to
                if(to != null){
                    ft.add(R.id.fl_tab_container,to).commit();
                }
            }else{
                //to已经被添加
                // from隐藏
                if(from != null){
                    ft.hide(from);
                }
                //显示to
                if(to != null){
                    ft.show(to).commit();
                }
            }
        }
    }

    private BaseMainFragment getFragment() {
        BaseMainFragment fragment = mBaseFragment.get(position);
        return fragment;
    }
}
