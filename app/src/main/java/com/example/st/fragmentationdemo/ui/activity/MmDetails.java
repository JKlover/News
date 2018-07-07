package com.example.st.fragmentationdemo.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;

import com.example.st.fragmentationdemo.R;
import com.example.st.fragmentationdemo.adapter.ImgPagerAdapter;
import com.example.st.fragmentationdemo.customview.HackyViewPager;
import com.example.st.fragmentationdemo.domain.MImgUrlBean;

import java.util.ArrayList;

import okhttp3.OkHttpClient;

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
public class MmDetails extends Activity {
    private ArrayList<MImgUrlBean> mImgUrlBeans;
    ViewPager mViewPager;
    String Uri;
    //http://m.mzitu.com/121418 http://www.mzitu.com/121418
    private OkHttpClient mHttpClient;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e("原来地址为",A);
            Log.e("转换后的地址",Uri);
            mViewPager.setAdapter(new ImgPagerAdapter(mImgUrlBeans));
            mViewPager.setCurrentItem(0);
        }
    };
    private String A;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mmdetails);
        mViewPager = (HackyViewPager) findViewById(R.id.my_img_viewpager);
        Intent intent = getIntent();
        String mUrl = intent.getStringExtra("DETAILS_URL");//DETAILS_URL
        mImgUrlBeans=new ArrayList<>();
        MImgUrlBean mImgUrlBean=new MImgUrlBean();
        mImgUrlBean.setmImgdetail(mUrl);
        mImgUrlBeans.add(mImgUrlBean);
        mViewPager.setAdapter(new ImgPagerAdapter(mImgUrlBeans));
        mViewPager.setCurrentItem(0);
    }

//    private void getDetailsData(String url) {
//
//        mHttpClient = new OkHttpClient();
//        Request request = new Request.Builder().url(url).build();
//        mHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String json = response.body().string();
//                mImgUrlBeans =  parseMziTudetails(json);
//                handler.sendEmptyMessage(10);
//
//            }
//        });
//
//    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finish();
        return super.onKeyDown(keyCode, event);
    }
}

