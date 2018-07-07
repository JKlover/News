package com.example.st.fragmentationdemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * //                            _ooOoo_
 * //                           o8888888o
 * //                           88" . "88
 * //                           (| -_- |)
 * //                           O\  =  /O
 * //                        ____/`---'\____
 * //                      .'  \\|     |//  `.
 * //                     /  \\|||  :  |||//  \
 * //                    /  _||||| -:- |||||-  \
 * //                    |   | \\\  -  /// |   |
 * //                    | \_|  ''\---/''  |   |
 * //                    \  .-\__  `-`  ___/-. /
 * //                  ___`. .'  /--.--\  `. . __
 * //               ."" '<  `.___\_<|>_/___.'  >'"".
 * //              | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 * //              \  \ `-.   \_ __\ /__ _/   .-` /  /
 * //         ======`-.____`-.___\_____/___.-`____.-'======
 * //                            `=---='
 * //        ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * //                      Buddha Bless, No Bug !
 * /**
 * Created by st on 18-5-2
 */
public abstract class BasePageChildFragment extends BaseMainFragment{
    protected boolean isVisible;
    protected boolean isPrepared;
    protected boolean isFirst=true;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared=true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()){
            isVisible=true;
            lazyLoad();
        }else {
            isVisible=false;
            onInVisible();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 试图不可见时的操作
     */
    protected void onInVisible() {
    }


    /**
     * 试图可见懒加载数据
     */
    protected void lazyLoad() {
        if (!isPrepared||!isVisible||!isFirst){
            return;
        }
        getData();
        isFirst=false;
    }

    /**
     * 让儿子强制实现加载数据
     */
    protected abstract void getData();
}
