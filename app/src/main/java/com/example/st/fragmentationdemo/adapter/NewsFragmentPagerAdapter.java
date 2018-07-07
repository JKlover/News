package com.example.st.fragmentationdemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.st.fragmentationdemo.ui.fragment.home.childfragment.NewsChildFragment;

/**
 * Created by st on 18-2-24.
 */

public class NewsFragmentPagerAdapter extends FragmentPagerAdapter {
    String[] mTitles;
    //用于获取子fragment的控件
    public Fragment currentFragment;
    public NewsFragmentPagerAdapter(FragmentManager fm, String... titles) {
        super(fm);
        this.mTitles = titles;
    }
    /**
     * 通过外部调用currentFragment来获取当前fragmentg给悬浮按钮用的
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        this.currentFragment= (Fragment) object;
        super.setPrimaryItem(container, position, object);
    }
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return NewsChildFragment.newInstance(0);
        } else if (position == 1) {
            return  NewsChildFragment.newInstance(1);
        } else if (position==2){
            return  NewsChildFragment.newInstance(2);
        }else if (position==3){
            return  NewsChildFragment.newInstance(3);
        }else if (position==4){
            return  NewsChildFragment.newInstance(4);
        }else if (position==5){
            return  NewsChildFragment.newInstance(5);
        }else{
            return  NewsChildFragment.newInstance(6);
        }
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    /**
     * 解决tablayout只能加载两个的问题
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }
}
