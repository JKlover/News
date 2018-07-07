package com.example.st.fragmentationdemo.ui.fragment.home.childfragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.st.fragmentationdemo.R;

import com.example.st.fragmentationdemo.adapter.NewsAdapter;



import com.example.st.fragmentationdemo.base.BasePageChildFragment;
import com.example.st.fragmentationdemo.domain.NewsBean;

import com.example.st.fragmentationdemo.net.NetCallBackListener;
import com.example.st.fragmentationdemo.net.Utils3NetWork;
import com.example.st.fragmentationdemo.ui.activity.EasyWebActivity;


import com.example.st.fragmentationdemo.ui.activity.NewsDetails;
import com.example.st.fragmentationdemo.ui.activity.OtherNewsDetails;
import com.example.st.fragmentationdemo.ui.activity.PhotoView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;

import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.st.fragmentationdemo.Api.news_A;
import static com.example.st.fragmentationdemo.Api.news_B;
import static com.example.st.fragmentationdemo.Api.news_Base_Page;
import static com.example.st.fragmentationdemo.Api.news_Base_Uri;
import static com.example.st.fragmentationdemo.Api.news_C;
import static com.example.st.fragmentationdemo.Api.news_D;
import static com.example.st.fragmentationdemo.Api.news_E;
import static com.example.st.fragmentationdemo.Api.news_F;
import static com.example.st.fragmentationdemo.Api.news_G;


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
public class NewsChildFragment extends BasePageChildFragment{
    private RefreshLayout mRefreshLayout;
    private NewsAdapter mAdapter;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private static final String ARG_FROM = "arg_from";
    private int mFrom;
    private ArrayList<NewsBean>mBeans;
    private int mPage=0;
    private LinearLayout mLoadingView;
    public static NewsChildFragment newInstance(int from) {
        Bundle args = new Bundle();
        args.putInt(ARG_FROM, from);
        NewsChildFragment fragment = new NewsChildFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
        Bundle args = getArguments();
        if (args != null) {
            mFrom = args.getInt(ARG_FROM);
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_news_pager,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mLoadingView=view.findViewById(R.id.pb_loading_view);
        mRecyclerView=view.findViewById(R.id.rlv_news);
        mAdapter=new NewsAdapter(mContext);
        mRefreshLayout = view.findViewById(R.id.srl_news);
        //设置 Header 为 贝塞尔雷达 样式
//        mRefreshLayout.setRefreshHeader(new BezierRadarHeader(mContext).setEnableHorizontalDrag(true));
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        mRefreshLayout.setRefreshFooter(new BallPulseFooter(mContext).setSpinnerStyle(SpinnerStyle.Scale));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
        //&&(mBean.getContentUrl()).contains(" http://tech.163.com/")
        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
//                Log.e("这是一个点击事件","我被点击了");
            NewsBean mBean= (NewsBean) adapter.getItem(position);
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
             String  data =  mBean.getContentUrl();
//                Log.e("链接地址是",mBean.getContentUrl());
            if(mBean.getType().equals(NewsBean.NEWS_TEXT)&&!data.equals("not")){
//                     Intent intent = new Intent(mContext, NewsDetails.class);
//                     intent.putExtra("NEWS",mBean.getContentUrl());
//                     startActivity(intent);
//                     Log.e("这是一个点击事件",mBean.getContentUrl());
//                    intent.setClass(mContext,NewsDetails.class);
                intent.setClass(mContext,NewsDetails.class);
                bundle.putSerializable("NEWS_163",mBean);
            } else if (mBean.getType().equals(NewsBean.NEWS_IMAGE)){
                intent.setClass(mContext,PhotoView.class);
                bundle.putSerializable("NEWS_PHOTO",mBean);
//                    intent.setClass(getActivity(), PhotoActivity.class);
//                    bundle.putSerializable(News.IMAGE_NEWS, newsList.get(position));
                 Log.e("这是一个点击事件","图片新闻被点击了");
            }else {
                intent.setClass(mContext,OtherNewsDetails.class);
                bundle.putSerializable("NEWS_OTHER",mBean);
                Log.e("这是一个点击事件","其他新闻来源被点击");

            }

            intent.putExtras(bundle);
            startActivity(intent);
        });
        refreshData();
    }

    private void refreshData() {
        mRefreshLayout.setOnRefreshListener(refreshlayout -> {
            mPage=0;
            mAdapter.getData().clear();
            initData();
            mAdapter.notifyDataSetChanged();
            refreshlayout.finishRefresh(1000);
        });
        mRefreshLayout.setOnLoadmoreListener(refreshlayout -> {
                mPage+=10;
                if (mFrom==0){
                    getDataFromNet(news_Base_Uri+news_A+mPage+news_Base_Page);
                }if (mFrom==1){
                    getDataFromNet(news_Base_Uri+news_B+mPage+news_Base_Page);
                }if (mFrom==2){
                    getDataFromNet(news_Base_Uri+news_C+mPage+news_Base_Page);
                }if (mFrom==3){
                    getDataFromNet(news_Base_Uri+news_D+mPage+news_Base_Page);
                }if(mFrom==4){
                    getDataFromNet(news_Base_Uri+news_E+mPage+news_Base_Page);
                }if (mFrom==5){
                    getDataFromNet(news_Base_Uri+news_F+mPage+news_Base_Page);
                }if (mFrom==6){
                    getDataFromNet(news_Base_Uri+news_G+mPage+news_Base_Page);
                }
             refreshlayout.finishLoadmore(1000);
        });
    }

    /**
     * 懒加载获取数据
     */
    @Override
    protected void getData() {
      initData();
    }

    private void initData() {
        if (mFrom==0){
            getDataFromNet(news_Base_Uri+news_A+mPage+news_Base_Page);
        }if (mFrom==1){
            getDataFromNet(news_Base_Uri+news_B+mPage+news_Base_Page);
        }if (mFrom==2){
            getDataFromNet(news_Base_Uri+news_C+mPage+news_Base_Page);
        }if (mFrom==3){
            getDataFromNet(news_Base_Uri+news_D+mPage+news_Base_Page);
        }if(mFrom==4){
            getDataFromNet(news_Base_Uri+news_E+mPage+news_Base_Page);
        }if (mFrom==5){
            getDataFromNet(news_Base_Uri+news_F+mPage+news_Base_Page);
        }if (mFrom==6){
            getDataFromNet(news_Base_Uri+news_G+mPage+news_Base_Page);
        }
    }

    private void getDataFromNet(String url) {
        Utils3NetWork.getResponse(url, new NetCallBackListener() {
            @Override
            public void onSuccess(String response) {
                System.out.println(response);
                parseResponse(response);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("出错", String.valueOf(e));
            }
        });

    }

    private void parseResponse(String response) {
        mBeans=parseJson(response);
        mAdapter.addData(mBeans);
        mLoadingView.setVisibility(View.GONE);

    }

    private ArrayList<NewsBean> parseJson(String json) {
        ArrayList<NewsBean> beans = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = null;
            if (mFrom==0){
                jsonArray = jsonObject.optJSONArray("T1348647909107");
            }if (mFrom==1){
                jsonArray = jsonObject.optJSONArray("T1348649580692");
            }if (mFrom==2){
                jsonArray = jsonObject.optJSONArray("T1348648756099");
            }if (mFrom==3){
                jsonArray = jsonObject.optJSONArray("T1348648141035");
            }if (mFrom==4){
                jsonArray = jsonObject.optJSONArray("T1348649079062");
            }if (mFrom==5){
                jsonArray = jsonObject.optJSONArray("T1348648650048");
            }if (mFrom==6){
                jsonArray = jsonObject.optJSONArray("T1348654204705");
            }
            if(jsonArray!= null && jsonArray.length() >0){
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObjectItem = (JSONObject) jsonArray.get(i);
                    if(jsonObjectItem != null){
                        NewsBean bean=new NewsBean();
                        String title = jsonObjectItem.optString("title");
                        Log.e("1、标题",title);
                        bean.setTitle(title);
                        String time = jsonObjectItem.optString("ptime");
                        Log.e("2、时间",time);
                        bean.setTime(time);
                        String imageSrc = jsonObjectItem.optString("imgsrc");
                        Log.e("3、图片", imageSrc);
                        bean.addImageSrc(imageSrc);
                        //||jsonObjectItem.has("url")，jsonObjectItem.has("url_3w")
                        if(jsonObjectItem.has("url_3w")){
                            bean.setType(NewsBean.NEWS_TEXT);
                            String content = jsonObjectItem.optString("digest");
                            Log.e("3、内容",content);
                            bean.setDesc(content);
                            String docId = jsonObjectItem.optString("docid");
                            Log.e("4、docId",docId);
                            bean.setDocId(docId);
                            String source = jsonObjectItem.optString("source");
                            Log.e("5、来源",source);
                            bean.setSource(source);
                            String contentUrl;
                            //if ()，jsonObjectItem.has("url_3w")
                            if (jsonObjectItem.get("url_3w").equals("")){
                                bean.setContentUrl("not");
                                Log.e("6、内容地址链接",bean.getContentUrl());

                            }else {
                                contentUrl = jsonObjectItem.optString("url_3w");
                                bean.setContentUrl(contentUrl);
                                Log.e("6、内容地址链接",bean.getContentUrl());
                            }


                            //else {
                            //                                contentUrl = jsonObjectItem.getString("url");
                            //
                            //                                bean.setContentUrl(contentUrl);
                            //                                Log.e("6、内容地址链接",bean.getContentUrl());
                            //                            }
                        } else {
                              bean.setType(NewsBean.NEWS_IMAGE);
                            if(jsonObjectItem.has("imgextra")){
                                JSONArray imageArray = jsonObjectItem.getJSONArray("imgextra");
                                for (int j = 0; j <imageArray.length(); j++) {
                                    JSONObject object = (JSONObject)imageArray.get(j);
                                    bean.addImageSrc(object.get("imgsrc").toString());
                                }
                            }
                        }
                        beans.add(bean);
                    }

                }
                Log.e("数据大小", String.valueOf(beans.size()));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return beans;
    }

}
