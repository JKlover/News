package com.example.st.fragmentationdemo.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.st.fragmentationdemo.R;
import com.example.st.fragmentationdemo.domain.ReportBean;
import com.example.st.fragmentationdemo.domain.TianjiPicBean;

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
 * Created by st on 2018/6/1
 */
public class PersonNews extends AppCompatActivity {
    private ReportBean mBean;
    private ImageView mIv;
    private TextView mTvContent;
    private TextView mTvTime;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        mBean= (ReportBean) getIntent().getSerializableExtra("PersonNews");
        initView();
        initData();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(v -> onBackPressed());
        mToolbar.setTitle(mBean.getTitle());
        mIv=findViewById(R.id.iv_content_img);
        mTvContent=findViewById(R.id.tv_person_news_content);
        mTvTime=findViewById(R.id.tv_time);


    }

    private void initData() {
        Glide.with(this).load(mBean.getPicUrl()).dontAnimate().into(mIv);
         mTvContent.setText(mBean.getContent());
         mTvTime.setText(mBean.getTime());
    }


}
