package com.example.st.fragmentationdemo.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.st.fragmentationdemo.R;
import com.example.st.fragmentationdemo.adapter.MlistAdapter;
import com.example.st.fragmentationdemo.domain.MImgUrlBean;
import com.example.st.fragmentationdemo.domain.TianjiPicBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import static com.example.st.fragmentationdemo.Api.BASE_URL;
import static com.example.st.fragmentationdemo.utils.MJsoupSpider.parseMziTudetails;

//import static com.example.st.fragmentationdemo.Api.BASE_URL;

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
public class MmDetailsList extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private MlistAdapter mAdapter;
    private ArrayList<MImgUrlBean> mImgUrlBeans;
    private TianjiPicBean mBean;
    private String Uri;

    //    private String Uri;
//    private String A;
//    private OkHttpClient mHttpClient;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mAdapter.setNewData(mImgUrlBeans);
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mm_details_list);

        Intent intent = getIntent();
        String mUrl = intent.getStringExtra("DETAILS_URL");//DETAILS_URL
        if (mUrl!=null){
            Uri=BASE_URL+mUrl.replace("http://m.mzitu.com/","");
        }
        mBean= (TianjiPicBean) intent.getSerializableExtra("Pic");

        initView();
        initData();

    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView=findViewById(R.id.rl_mm_list);
        mAdapter=new MlistAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
        mImgUrlBeans=new ArrayList<>();
        mAdapter.setOnItemClickListener(new myListItemOnClickListener());
        toolbar.setTitleTextColor(Color.WHITE);
        if (mBean!=null){
            toolbar.setTitle(mBean.getTitle() );
        }else {
            toolbar.setTitle("妹子图");
        }

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new myNavigationOnClickListener());
    }

    private void initData() {
//        Intent intent = getIntent();
//        String mUrl = intent.getStringExtra("DETAILS_URL");//DETAILS_URL
//        String mTitle=intent.getStringExtra("DETAILS_TITLE");

//        A=mUrl;
//        Uri=BASE_URL+mUrl.replace("http://m.mzitu.com/","");
//        getDetailsData(Uri);
        if (mBean!=null){
            getDetailsData(mBean.getHtmlUrl());
        }else {
            getDetailsData(Uri);
        }

    }

    private void getDetailsData(String url) {
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
        OkGo.<String>get(url)//
                .tag(this)//
                .headers("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.4620.400 QQBrowser/9.7.13014.400")//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {

                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String body= response.body();
                        if (mBean!=null){
                            parseHtml(body);
                        }else {
                            mImgUrlBeans =  parseMziTudetails(body);
                            handler.sendEmptyMessage(10);
                        }


                    }
                });


    }

    private void parseHtml(String html) {
        Document doc= Jsoup.parse(html);
        Elements picData=doc.select(".viewport li");
        for (Element e:picData){
            MImgUrlBean bean=new MImgUrlBean();
            String picShowUrl=e.select("a").select("img").attr("src");
            String picUrl=picShowUrl.replace("113x113","700x1050").replaceAll("_113","");
            bean.setmImgdetail(picUrl);
            mImgUrlBeans.add(bean);
        }
        mAdapter.setNewData(mImgUrlBeans);
    }

    class myListItemOnClickListener implements BaseQuickAdapter.OnItemClickListener{

        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            MImgUrlBean mImgUrlBean=mImgUrlBeans.get(position);
            Intent intent = new Intent(MmDetailsList.this, MmDetails.class);
            intent.putExtra("DETAILS_URL",mImgUrlBean.getmImgdetail());
            startActivity(intent);
        }
    }

    class myNavigationOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
          MmDetailsList.this.finish();
        }
    }

}
