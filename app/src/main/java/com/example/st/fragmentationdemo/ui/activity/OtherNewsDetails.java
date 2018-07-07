package com.example.st.fragmentationdemo.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.example.st.fragmentationdemo.R;
import com.example.st.fragmentationdemo.domain.NewsBean;
import com.example.st.fragmentationdemo.net.NetCallBackListener;
import com.example.st.fragmentationdemo.net.Utils3NetWork;
import com.example.st.fragmentationdemo.utils.HtmlHttpImageGetter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.st.fragmentationdemo.Api.news_Details_Base;
import static com.example.st.fragmentationdemo.Api.news_Details_Base_Html;

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
public class OtherNewsDetails extends AppCompatActivity {
    private TextView mTVContent;
    private NewsBean mBean;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_othernews_details);
        mBean= (NewsBean) getIntent().getSerializableExtra("NEWS_OTHER");
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
        mTVContent=findViewById(R.id.tv_news_content);
    }
    private void initData() {
        getData(news_Details_Base+mBean.getDocId()+news_Details_Base_Html);
    }

    private void getData(String url) {
        Utils3NetWork.getResponse(url, new NetCallBackListener() {
            @Override
            public void onSuccess(String response) {
                parseResponse(response);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    private void parseResponse(String response) {
        String  content= parseJson(response);
        mTVContent.setText(Html.fromHtml(content, new HtmlHttpImageGetter(mTVContent), null));
    }

    private String parseJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject object = (JSONObject) jsonObject.get(mBean.getDocId());
            String news_detail = object.get("body").toString();
            String imageUrl;
            String imageElement;
            JSONArray jsonArray = object.getJSONArray("img");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject imageObject = (JSONObject) jsonArray.get(i);
                imageUrl = imageObject.get("src").toString();
                imageElement = "<img src=\"" + imageUrl +  "\"/><br>";
                news_detail = news_detail.replace(imageObject.get("ref").toString(), imageElement);
            }
            return news_detail;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


}
