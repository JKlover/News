package com.example.st.fragmentationdemo.ui.activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.st.fragmentationdemo.R;
import com.example.st.fragmentationdemo.domain.NewsBean;
import com.example.st.fragmentationdemo.utils.HtmlUtils;
import com.google.gson.Gson;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.MiddlewareWebChromeBase;
import com.just.agentweb.MiddlewareWebClientBase;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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
public class EasyWebActivity extends AppCompatActivity {

	private TextView mTitleTextView;
	private AgentWeb mAgentWeb;
	private LinearLayout mLinearLayout;
	private NewsBean mBean;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web);
		mBean= (NewsBean) getIntent().getSerializableExtra("NEWS_163");
		LinearLayout mLinearLayout = findViewById(R.id.container);
		Toolbar mToolbar = findViewById(R.id.toolbar);
		mToolbar.setTitleTextColor(Color.WHITE);
		mToolbar.setTitle(mBean.getTitle() );
		mTitleTextView = findViewById(R.id.toolbar_title);
		this.setSupportActionBar(mToolbar);
		if (getSupportActionBar() != null) {
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
		mToolbar.setNavigationOnClickListener(v -> EasyWebActivity.this.finish());
		getDetailsData(mBean.getContentUrl());
		mAgentWeb = AgentWeb.with(this)//传入Activity
				.setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(-1, -1))//传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams
				.useDefaultIndicator(-1, 3)
				.createAgentWeb()//
				.ready()
				.go("");//这个在调用系统的webview后会被覆盖

//	    Log.e("dsdsd",HtmlUtils.getHtml(showData));
	}

	private void getDetailsData(String url) {
		OkGo.<String>get(url)//
				.tag(this)//
				.headers("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.4620.400 QQBrowser/9.7.13014.400")//
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
		String bodyHtml=doc.select("#endText").html();
		Log.e("B这是什么....",bodyHtml);
//        String data=getMyHtml(bodyHtml);
		String getData=bodyHtml.replaceAll("<img","<img style='max-width:100%;height:auto;'");//让图片为屏幕做适应除去恶心人的横向滑轮
		String showData=getData.replaceAll("<div class=\"video\"","<div class=\"video\" style='height:auto;'");//改变视频的窗口属性适应手机屏幕
//		String showHtml=showData.replaceAll("<a  href","<a  href=''");//改变视频的窗口属性适应手机屏幕
		mAgentWeb.getWebCreator().getWebView().loadDataWithBaseURL(null, HtmlUtils.getHtml(showData), HtmlUtils.getMimeType(), HtmlUtils.getCoding(), null);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (mAgentWeb.handleKeyEvent(keyCode, event)) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onPause() {
		mAgentWeb.getWebLifeCycle().onPause();
		super.onPause();

	}

	@Override
	protected void onResume() {
		mAgentWeb.getWebLifeCycle().onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//mAgentWeb.destroy();
		mAgentWeb.getWebLifeCycle().onDestroy();
	}

}
