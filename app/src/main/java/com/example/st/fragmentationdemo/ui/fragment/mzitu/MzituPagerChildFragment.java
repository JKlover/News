package com.example.st.fragmentationdemo.ui.fragment.mzitu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.st.fragmentationdemo.R;
import com.example.st.fragmentationdemo.adapter.MZiAdapter;
import com.example.st.fragmentationdemo.base.BaseMainFragment;
import com.example.st.fragmentationdemo.base.BasePageChildFragment;
import com.example.st.fragmentationdemo.base.MySupportFragment;
import com.example.st.fragmentationdemo.domain.MeiZiBean;
import com.example.st.fragmentationdemo.net.RxJsoupNetWorkCallbackListener;
import com.example.st.fragmentationdemo.net.RxNetWork;
import com.example.st.fragmentationdemo.ui.activity.MmDetailsList;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;



import static com.example.st.fragmentationdemo.Api.A_URL;
import static com.example.st.fragmentationdemo.Api.BASE_PAGE;
import static com.example.st.fragmentationdemo.Api.B_URL;
import static com.example.st.fragmentationdemo.Api.C_URL;
import static com.example.st.fragmentationdemo.Api.D_URL;

/**
 * Created by st on 18-2-24.
 */

public class MzituPagerChildFragment extends BasePageChildFragment {
    private RefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;

    private MZiAdapter mAdapter;
    private ArrayList<MeiZiBean> mBeans;
    int  mPage=1;
    private Context mcontext;

    private static final String ARG_FROM = "arg_from";
    private int mFrom;
    private LinearLayout mLoadingView;

    public static MzituPagerChildFragment newInstance(int from) {
        Bundle args = new Bundle();
        args.putInt(ARG_FROM, from);
        MzituPagerChildFragment fragment = new MzituPagerChildFragment();
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
        mLoadingView=view.findViewById(R.id.pb_loading_view);
        mRecyclerView=view.findViewById(R.id.rv_mian);
        mAdapter=new MZiAdapter(mcontext);
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
                initData();
                mAdapter.notifyDataSetChanged();
                refreshlayout.finishRefresh(2000);
            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                ++mPage;
                if (mFrom==0){
                    getDataFromNet(A_URL+BASE_PAGE+mPage+"/");
                }if (mFrom==1){
                    getDataFromNet(B_URL+BASE_PAGE+mPage+"/");
                }if (mFrom==2){
                    getDataFromNet(C_URL+BASE_PAGE+mPage+"/");
                }if (mFrom==3){
                    getDataFromNet(D_URL);
                }if (mFrom==4){
//            getDataFromNet(E_URL);
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
            getDataFromNet(A_URL);
        }if (mFrom==1){
            getDataFromNet(B_URL);
        }if (mFrom==2){
            getDataFromNet(C_URL);
        }if (mFrom==3){
            getDataFromNet(D_URL);
        }if (mFrom==4){
//            getDataFromNet(E_URL);
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
                   mLoadingView.setVisibility(View.GONE);
            }
        });
    }

    private void spiderData(String html) {
        Document doc= Jsoup.parse(html);
        Elements getData=doc.select("figure");
//        Elements getData=doc.getElementsByClass("place-padding");
        for (Element e:getData){
            MeiZiBean meiZiBean=new MeiZiBean();
//            String title=e.select("h2").text();
//            Log.e("标题",title);
            String href=e.select("a").attr("href");
            Log.e("链接",href);
            meiZiBean.setMeiziHref(href);
            String imgurl=e.select("img").attr("data-original");
            Log.e("图片地址",imgurl);
            meiZiBean.setMeiziImg(imgurl);
            mBeans.add(meiZiBean);
        }

        mAdapter.setNewData(mBeans);
        mLoadingView.setVisibility(View.GONE);
    }

    class  myItemClickListener implements BaseQuickAdapter.OnItemClickListener{
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            MeiZiBean mBean=mBeans.get(position);
            Intent intent = new Intent(mcontext, MmDetailsList.class);
            intent.putExtra("DETAILS_URL",mBean.getMeiziHref());
            startActivity(intent);
        }
    }
}
