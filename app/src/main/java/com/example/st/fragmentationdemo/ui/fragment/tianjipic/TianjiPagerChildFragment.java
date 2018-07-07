package com.example.st.fragmentationdemo.ui.fragment.tianjipic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.st.fragmentationdemo.R;
import com.example.st.fragmentationdemo.adapter.MZiAdapter;
import com.example.st.fragmentationdemo.adapter.TianjiAdapter;
import com.example.st.fragmentationdemo.base.BasePageChildFragment;
import com.example.st.fragmentationdemo.domain.MeiZiBean;
import com.example.st.fragmentationdemo.domain.NewsBean;
import com.example.st.fragmentationdemo.domain.TianjiPicBean;
import com.example.st.fragmentationdemo.net.RxJsoupNetWorkCallbackListener;
import com.example.st.fragmentationdemo.net.RxNetWork;
import com.example.st.fragmentationdemo.ui.activity.EasyWebActivity;
import com.example.st.fragmentationdemo.ui.activity.MmDetailsList;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;


import static com.example.st.fragmentationdemo.Api.BASE_Tianji;

import static com.example.st.fragmentationdemo.Api.picA;
import static com.example.st.fragmentationdemo.Api.picA_Base;
import static com.example.st.fragmentationdemo.Api.picB;
import static com.example.st.fragmentationdemo.Api.picB_Base;
import static com.example.st.fragmentationdemo.Api.picC;
import static com.example.st.fragmentationdemo.Api.picC_Base;
import static com.example.st.fragmentationdemo.Api.picD;
import static com.example.st.fragmentationdemo.Api.picD_Base;
import static com.example.st.fragmentationdemo.Api.picE;
import static com.example.st.fragmentationdemo.Api.picE_Base;

/**
 * Created by st on 18-2-24.
 */

public class TianjiPagerChildFragment extends BasePageChildFragment {
    private RefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;

    private TianjiAdapter mAdapter;
    private ArrayList<TianjiPicBean> mBeans;
    int  mPage=2;
    private Context mcontext;

    private static final String ARG_FROM = "arg_from";
    private int mFrom;

    public static TianjiPagerChildFragment newInstance(int from) {
        Bundle args = new Bundle();
        args.putInt(ARG_FROM, from);
        TianjiPagerChildFragment fragment = new TianjiPagerChildFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mcontext=getActivity();
        Bundle args = getArguments();
        if (args != null) {
            mFrom = args.getInt(ARG_FROM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mzitu_pager, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRecyclerView=view.findViewById(R.id.rv_mian);
        mAdapter=new TianjiAdapter(mcontext);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
//        recyclerView.setLayoutManager(new LinearLayoutManager(mcontext));
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mBeans=new ArrayList<>();
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        //上拉刷新下拉加载更多
        mRefreshLayout = view.findViewById(R.id.srl_m_refresh);
        //设置 Header 为 Material样式
//        refreshLayout.setRefreshHeader(new MaterialHeader(mcontext).getResources().getColor());
//        //设置 Footer 为 球脉冲
//        refreshLayout.setRefreshFooter(new BallPulseFooter(mcontext).setSpinnerStyle(SpinnerStyle.Scale));
        mAdapter.setOnItemClickListener(new myItemClickListener());
        refreshData();
    }

    private void refreshData() {

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mAdapter.getData().clear();
                initData();
                mAdapter.notifyDataSetChanged();
                refreshlayout.finishRefresh(2000);
            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPage++;
                if (mFrom==0){
                    getDataFromNet(picA_Base+mPage+BASE_Tianji);
                }if (mFrom==1){
                    getDataFromNet(picB_Base+mPage+BASE_Tianji);
                }if (mFrom==2){
                    getDataFromNet(picC_Base+mPage+BASE_Tianji);
                }if (mFrom==3){
                    getDataFromNet(picD_Base+mPage+BASE_Tianji);
                }if (mFrom==4){
                    getDataFromNet(picE_Base+mPage+BASE_Tianji);
                }

                refreshlayout.finishLoadmore(2000);
            }
        });
    }

    @Override
    protected void getData() {
        initData();
    }

    private void initData() {
        if (mFrom==0){
            getDataFromNet(picA);
        }else if (mFrom==1){
            getDataFromNet(picB);
        }else if (mFrom==2){
            getDataFromNet(picC);
        }else if (mFrom==3){
            getDataFromNet(picD);
        }else{
            getDataFromNet(picE);
        }

    }

    private void getDataFromNet(String uri) {
        RxNetWork.getHtml(uri ,new RxJsoupNetWorkCallbackListener() {
            @Override
            public void onNetStart() {

            }

            @Override
            public void onFinish(String response) {
                spiderData(response);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    private void spiderData(String html) {

        Document doc = Jsoup.parse(html);
        Elements body = null;
        if (mFrom==0){
            body = doc.select(".lb_box dl");//动画
        }else if (mFrom==1){
            body = doc.select(".lb_box dl");//明星
        }else if (mFrom==2){
            body = doc.select(".lb_box dl");//美女
        }else if (mFrom==3){
            body = doc.select(".star_box dl");//搞笑
        }else{
            body = doc.select(".lb_box dl");//时尚伊人
        }
        for (Element e : body) {
            TianjiPicBean bean = new TianjiPicBean();
            String htmlurl = e.select("dt").select("a").attr("href");
            bean.setHtmlUrl(htmlurl);
            Log.e("html",htmlurl);


            String picUrl = e.select("dt").select("img").attr("src");
            bean.setPicUrl(picUrl);
            Log.e("picUrl",picUrl);

            String title = e.select("dd").text();
            bean.setTitle(title);
            Log.e("title",title);

            String date = e.select(".date").text();
            bean.setData(date);
            mBeans.add(bean);
            Log.e("date",date);

        }
        mAdapter.setNewData(mBeans);
    }

    class  myItemClickListener implements BaseQuickAdapter.OnItemClickListener{
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            TianjiPicBean bean= (TianjiPicBean) adapter.getItem(position);
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            intent.setClass(mcontext,MmDetailsList.class);
            bundle.putSerializable("Pic",bean);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}
