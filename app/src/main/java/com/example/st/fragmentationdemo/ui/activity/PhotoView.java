package com.example.st.fragmentationdemo.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.st.fragmentationdemo.R;
import com.example.st.fragmentationdemo.adapter.ImgPagerAdapter;
import com.example.st.fragmentationdemo.adapter.PhotoPagerAdapter;
import com.example.st.fragmentationdemo.customview.HackyViewPager;
import com.example.st.fragmentationdemo.domain.MImgUrlBean;
import com.example.st.fragmentationdemo.domain.NewsBean;

import java.util.ArrayList;

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
 * Created by st on 2018/5/13
 */
public class PhotoView extends AppCompatActivity {
    private HackyViewPager mHVP;
    private NewsBean mBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        mBean= (NewsBean) getIntent().getSerializableExtra("NEWS_PHOTO");
        initView();
    }


    private void initView() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setTitle(mBean.getTitle() );
        this.setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mToolbar.setNavigationOnClickListener(v -> PhotoView.this.finish());
        mHVP=findViewById(R.id.img_viewpager);
        mHVP.setAdapter(new PhotoPagerAdapter(mBean.getImageSrc()));
        mHVP.setCurrentItem(0);
    }
}
