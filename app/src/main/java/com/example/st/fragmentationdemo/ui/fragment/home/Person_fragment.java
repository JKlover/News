package com.example.st.fragmentationdemo.ui.fragment.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.st.fragmentationdemo.R;

import com.example.st.fragmentationdemo.adapter.ReportAdapter;
import com.example.st.fragmentationdemo.base.BaseMainFragment;
import com.example.st.fragmentationdemo.domain.ReportBean;

import com.example.st.fragmentationdemo.domain.TianjiPicBean;
import com.example.st.fragmentationdemo.ui.activity.MmDetailsList;
import com.example.st.fragmentationdemo.ui.activity.PersonNews;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.st.fragmentationdemo.Api.news_Report_List_Api;

/**
 * 　　　　　　　　┏┓　　　┏┓+ +
 * 　　　　　　　┏┛┻━━━┛┻┓ + +
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃ ++ + + +
 * 　　　　       	██ ━██  ┃+
 * 　　　　　　　┃　　　　　　　┃ +
 * 　　　　　　　┃　　　┻　　　┃
 * 　　　　　　　┃　　　　　　　┃ + +
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃ + + + +
 * 　　　　　　　　　┃　　　┃　　　　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃ + 　　　　神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃　　+
 * 　　　　　　　　　┃　 　　┗━━━┓ + +
 * 　　　　　　　　　┃ 　　　　　　　┣┓
 * 　　　　　　　　　┃ 　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛ + + + +
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛+ + + +
 * <p>
 * Created by st on 2018/3/11.
 */

public class Person_fragment extends BaseMainFragment {
    private RecyclerView mRecyclerView;
    private Context mContext;
    private ReportAdapter mAdapter;
    private List<ReportBean>mBean;
    private SwipeRefreshLayout mRefreshLayout;
    private LinearLayout mLoadingView;
    private boolean notNet;

    public static Person_fragment newInstance() {
        Bundle args = new Bundle();
        Person_fragment fragment = new Person_fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mLoadingView=view.findViewById(R.id.pb_loading_view);
        Toolbar mToolbar = view.findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.person);
        initToolbarNav(mToolbar);
        mRecyclerView=view.findViewById(R.id.rl_person_list);
        mRefreshLayout=view.findViewById(R.id.srl_person);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter=new ReportAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setColorSchemeColors(Color.RED, Color.YELLOW);
        mRefreshLayout.setOnRefreshListener(new myOnRefreshListener());
        mAdapter.setOnItemClickListener(new myItemClickListener());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDataFromServlet();
    }

    private void getDataFromServlet() {
        OkGo.<String>get(news_Report_List_Api)//
                .tag(this)//
                .headers("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.4620.400 QQBrowser/9.7.13014.400")//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                            notNet=true;
                            mLoadingView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        notNet=false;
                        String body= response.body();
                        parseResponse(body);

                    }
                });
    }

    private void parseResponse(String body) {
        mBean=parseJson(body);
        mAdapter.setNewData(mBean);
        mLoadingView.setVisibility(View.GONE);
        mRefreshLayout.setRefreshing(false);
        
    }

    private ArrayList<ReportBean> parseJson(String json) {
        ArrayList<ReportBean> beans = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.optJSONArray("listName");
            if(jsonArray!= null && jsonArray.length() >0){
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObjectItem = (JSONObject) jsonArray.get(i);
                    if(jsonObjectItem != null){
                        ReportBean bean = new ReportBean();
                        
                        String  title = jsonObjectItem.optString("title");//标题
                        Log.e("标题",title);
                        bean.setTitle(title);

                        String time = jsonObjectItem.optString("time");//时间
                        Log.e("时间", time);
                        bean.setTime(time);

                        String location = jsonObjectItem.optString("location");//地点
                        bean.setLocation(location);
                        Log.e("地点",location);

                        String content = jsonObjectItem.optString("content");//内容
                        bean.setContent(content);
                        Log.e("内容",content);

                        String pic = jsonObjectItem.optString("picUrl");//影片描述
                        bean.setPicUrl(pic);
                        Log.e("图片", pic);
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

    class myOnRefreshListener implements  SwipeRefreshLayout.OnRefreshListener{
        @Override
        public void onRefresh() {
            getDataFromServlet();
            if (notNet){
                new AlertDialog.Builder(getContext())
                        .setTitle("出事了")
                        .setMessage("没有网络，或者是作者带着服务器跑路了，连接不上服务器>>>>>>")
                        .setPositiveButton(getString(R.string.dialog_ok), null)
                        .setNegativeButton(getString(R.string.dialog_cancel), null)
//                                        .setNeutralButton(getString(R.string.dialog_register), new myOnClickListener())
                        .show();
                mRefreshLayout.setRefreshing(false);
                return;
            }

        }
    }

    class  myItemClickListener implements BaseQuickAdapter.OnItemClickListener{
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            ReportBean bean= (ReportBean) adapter.getItem(position);
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            intent.setClass(mContext,PersonNews.class);
            bundle.putSerializable("PersonNews",bean);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}
