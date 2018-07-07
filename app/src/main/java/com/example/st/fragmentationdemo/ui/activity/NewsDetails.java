package com.example.st.fragmentationdemo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.st.fragmentationdemo.R;
import com.example.st.fragmentationdemo.customview.MyWebView;
import com.example.st.fragmentationdemo.domain.NewsBean;
import com.example.st.fragmentationdemo.net.NetCallBackListener;
import com.example.st.fragmentationdemo.net.Utils3NetWork;
import com.example.st.fragmentationdemo.ui.fragment.account.StringDialogCallback;
import com.example.st.fragmentationdemo.utils.HtmlUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

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
 * Created by st on 2018/5/11
 */
public class NewsDetails extends AppCompatActivity {
//    private MyWebView mWebView;
    private WebView mWebView;
    private Toolbar mToolbar;
//    private String mUrl;
    private NewsBean mBean;
    private LinearLayout mLy;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        mLy=findViewById(R.id.X5WebView_Ly);
        initHardwareAccelerate();
        mWebView=new WebView(this);
        mLy.addView(mWebView,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        Intent intent = getIntent();
//        mUrl = intent.getStringExtra("NEWS");//DETAILS_URL
        mBean= (NewsBean) getIntent().getSerializableExtra("NEWS_163");
//        Log.e("传过来的url",mUrl);
        initView();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(v -> onBackPressed());
//        mWebView = findViewById(R.id.webView);
//        mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);//防止内存泄漏
        mToolbar.setTitle(mBean.getTitle());
        getDetailsData(mBean.getContentUrl());

        WebSettings webSettings = mWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setUseWideViewPort(true); // 关键点
            webSettings.setAllowFileAccess(true); // 允许访问文件
            webSettings.setSupportZoom(true); // 支持缩放
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setPluginsEnabled(true);
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容
            webSettings.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

            mWebView.setWebChromeClient(new WebChromeClient());
            WebViewClient wvc = new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                    mWebView.loadUrl(url);
                    Log.e("网址为",url);
                    return false;

                }
            };
           mWebView.setWebViewClient(wvc);
//            webView.loadUrl(mPlayLink);
    }

    private void getDetailsData(String url) {
//        RequestParams params=new RequestParams(url);
//        x.http().get(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                parseHtml(result);
//                Log.e("A这是什么....",result);
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        }) ;
        OkGo.<String>get(url)//
                .tag(this)//
                .headers("header1", "headerValue1")//
//                .params("param1", "paramValue1")//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {

                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        parseHtml(response.body());
                    }
                });
    }

    private void parseHtml(String result) {
        Document doc= Jsoup.parse(result);
//        String title=doc.select("div.post_content_main h1").text();
//        mToolbar.setTitle(title);
//        String bodyHtml=doc.select("div.post_text").html();
        String bodyHtml=doc.select("#endText").html();
        Log.e("B这是什么....",bodyHtml);
//        String data=getMyHtml(bodyHtml);
        String getData=bodyHtml.replaceAll("<img","<img style='max-width:100%;height:auto;'");//让图片为屏幕做适应除去恶心人的横向滑轮
        String showData=getData.replaceAll("<div class=\"video\"","<div class=\"video\" style='height:auto;'");//改变视频的窗口属性适应手机屏幕
//        mWebView.loadDataUrl(showData);
        mWebView.loadDataWithBaseURL(null, HtmlUtils.getHtml(showData), HtmlUtils.getMimeType(), HtmlUtils.getCoding(), null);
    }

    /**
     * 1、先剔除不要的内容
     * 2、把图片路径换成可以显示的路径
     * 3、让图片为屏幕做适应除去恶心人的横向滑轮
     * @param htmltext
     * @return
     */
    public static String getMyHtml(String htmltext){
        Document doc = Jsoup.parse(htmltext);
        //1、剔除不要的内容
        doc.select(".post_topshare_wrap").remove();
        doc.select("div.pagination").remove();
        doc.select("p.article-tags").remove();
        doc.select("p.post-copyright").remove();
        //2、把图片路径换成可以显示的绝对路径
        Elements pngs = doc.select("img[src]");
        for (Element element : pngs) {
            String imgUrl = element.attr("src");
            if (imgUrl.trim().startsWith("/")) {
//                imgUrl =HTTPHOST + imgUrl;
                element.attr("src", imgUrl);//替换静态页面的img地址属性再加载
            }
        }
//        //让图片为屏幕做适应除去恶心人的横向滑轮
//        Elements elements=doc.getElementsByTag("img");
//        for (Element element : elements) {
////            element.attr("width","100%").attr("height","auto");
//            element.attr("width","auto").attr("height","100%");
//        }
        return doc.toString();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        } else {
            finish();
        }
        return false;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.removeAllViews();
        if(mWebView!=null){
            mWebView.destroy();
        }

    }

    /**
     * 开启硬件加速
     */
    private void initHardwareAccelerate() {
        try {
            if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 11) {
                getWindow()
                        .setFlags(
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        } catch (Exception e) {
        }
    }

}
