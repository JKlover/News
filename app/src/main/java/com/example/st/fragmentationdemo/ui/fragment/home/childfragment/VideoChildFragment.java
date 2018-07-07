package com.example.st.fragmentationdemo.ui.fragment.home.childfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.st.fragmentationdemo.R;
import com.example.st.fragmentationdemo.adapter.VideoListAdapter;
import com.example.st.fragmentationdemo.adapter.VideoViewHolder;
import com.example.st.fragmentationdemo.base.BasePageChildFragment;
import com.example.st.fragmentationdemo.domain.VideoBean;
import com.example.st.fragmentationdemo.net.NetCallBackListener;
import com.example.st.fragmentationdemo.net.Utils3NetWork;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.st.fragmentationdemo.Api.news_Base_Uri;
import static com.example.st.fragmentationdemo.Api.video_Base;
import static com.example.st.fragmentationdemo.Api.video_Base_Page;
import static com.example.st.fragmentationdemo.Api.video_Base_Uri;
import static com.example.st.fragmentationdemo.Api.video_CHOICE;
import static com.example.st.fragmentationdemo.Api.video_ENTERTAINMENT;
import static com.example.st.fragmentationdemo.Api.video_FUN;
import static com.example.st.fragmentationdemo.Api.video_HOT;

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
public class VideoChildFragment  extends BasePageChildFragment{
    private RefreshLayout mRefreshLayout;
    private VideoListAdapter mAdapter;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private static final String ARG_FROM = "arg_from";
    private int mFrom;
    private List<VideoBean> mBeans;
    private int mPage=0;
    private int mLoadData=0;//设置加载数据的状态0代表第一次加载；1代表下拉刷新；2代表上拉加载更多
    private LinearLayout mLoadingView;

    public static VideoChildFragment newInstance(int from) {
        Bundle args = new Bundle();
        args.putInt(ARG_FROM, from);
        VideoChildFragment fragment = new VideoChildFragment();
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
        View view=inflater.inflate(R.layout.fragment_video_pager,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mLoadingView=view.findViewById(R.id.pb_loading_view);
        mRecyclerView=view.findViewById(R.id.rlv_video);
//        mAdapter=new VideoListAdapter(mContext);
        mRefreshLayout = view.findViewById(R.id.srl_video);
        mRecyclerView.setHasFixedSize(true);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        //设置 Footer 为 球脉冲 样式
        //        mRefreshLayout.setRefreshFooter(new BallPulseFooter(mContext).setSpinnerStyle(SpinnerStyle.Scale));
        mRefreshLayout.setRefreshFooter(new BallPulseFooter(mContext).setSpinnerStyle(SpinnerStyle.Scale));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setRecyclerListener(holder -> {
            NiceVideoPlayer niceVideoPlayer = ((VideoViewHolder) holder).mVideoPlayer;
            if (niceVideoPlayer == NiceVideoPlayerManager.instance().getCurrentNiceVideoPlayer()) {
                NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
            }
        });
        refreshData();
    }


    private void refreshData() {
        mRefreshLayout.setOnRefreshListener(refreshlayout -> {
            mPage=0;
            mLoadData=1;
            initData();
            refreshlayout.finishRefresh(2000);
        });
        mRefreshLayout.setOnLoadmoreListener(refreshlayout -> {
            mLoadData=2;
            mPage=mPage+8;
            if (mFrom==0){
                getDataFromNet(video_Base_Uri+video_HOT+video_Base+mPage+video_Base_Page);
            }if (mFrom==1){
                getDataFromNet(video_Base_Uri+video_ENTERTAINMENT+video_Base+mPage+video_Base_Page);
            }if (mFrom==2){
                getDataFromNet(video_Base_Uri+video_FUN+video_Base+mPage+video_Base_Page);
            }if (mFrom==3){
                getDataFromNet(video_Base_Uri+video_CHOICE+video_Base+mPage+video_Base_Page);
            }
            refreshlayout.finishLoadmore(2000);
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
            getDataFromNet(video_Base_Uri+video_HOT+video_Base+mPage+video_Base_Page);
        }if (mFrom==1){
            getDataFromNet(video_Base_Uri+video_ENTERTAINMENT+video_Base+mPage+video_Base_Page);
        }if (mFrom==2){
            getDataFromNet(video_Base_Uri+video_FUN+video_Base+mPage+video_Base_Page);
        }if (mFrom==3){
            getDataFromNet(video_Base_Uri+video_CHOICE+video_Base+mPage+video_Base_Page);
        }
    }

    private void getDataFromNet(String url) {
        Utils3NetWork.getResponse(url, new NetCallBackListener() {
            @Override
            public void onSuccess(String response) {
                Log.e("API",response);
                parseResponse(response);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("出错", String.valueOf(e));
                mLoadingView.setVisibility(View.GONE);
            }
        });

    }

    private void parseResponse(String response) {
        mBeans=parseJson(response);
        if (mBeans.size()>0||mBeans!=null){
//            new AlertDialog.Builder(getContext())
//                    .setTitle("提示")
//                    .setMessage("页面出现空白！不要怕，不是程序的Bug，是该死的网易云接口出问题只是这个“热点视频”而已，可以多刷新几次试试，不行就浏览其他>>>>>>")
//                    .setPositiveButton(getString(R.string.dialog_ok), null)
//                    .setNegativeButton(getString(R.string.dialog_cancel), null)
////                                        .setNeutralButton(getString(R.string.dialog_register), new myOnClickListener())
//                    .show();
            if (mLoadData==0){
                Log.e("这是A消息","第一次载入数据");
                Log.e("集合的大小为", String.valueOf(mBeans.size()));
                mAdapter=new VideoListAdapter(mContext,mBeans);
                mRecyclerView.setAdapter(mAdapter);
                mLoadingView.setVisibility(View.GONE);
            }else if (mLoadData==1){
                Log.e("这是B消息","刷新数据");
                mAdapter.refresh(mBeans);
            }else {
                Log.e("这是C消息","加载更多数据");
                mAdapter.addMore(mBeans);
            }

        }else {
            mLoadingView.setVisibility(View.GONE);
        }

    }

    private ArrayList<VideoBean> parseJson(String json) {
        ArrayList<VideoBean> videoItems = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = null;
            if (mFrom==0){
                jsonArray = jsonObject.optJSONArray(video_HOT);
            }if (mFrom==1){
                jsonArray = jsonObject.optJSONArray(video_ENTERTAINMENT);
            }if (mFrom==2){
                jsonArray = jsonObject.optJSONArray(video_FUN);
            }if (mFrom==3){
                jsonArray = jsonObject.optJSONArray(video_CHOICE);
            }
//            JSONArray jsonArray = jsonObject.optJSONArray(video_HOT);
            if(jsonArray!= null && jsonArray.length() >0){
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObjectItem = (JSONObject) jsonArray.get(i);
                    if(jsonObjectItem != null){
                        VideoBean videoBean = new VideoBean();
//                        utils=new Utils();
                        String video_Name = jsonObjectItem.optString("title");//片名
                        Log.e("片名",video_Name);
                        videoBean.setName(video_Name);
                        int video_size = jsonObjectItem.optInt("length");//视频长度
                        videoBean.setSize(video_size*1000);
                        Log.e("视频长度", String.valueOf(videoBean.getSize()));
                        String video_source = jsonObjectItem.optString("videosource");//视频来源
                        videoBean.setSource(video_source);
                        Log.e("视频来源",video_source);

                        String image_Url = jsonObjectItem.optString("cover");//图片
                        videoBean.setImageUrl(image_Url);
                        Log.e("图片",image_Url);

                        String video_TopicDesc = jsonObjectItem.optString("topicName");//影片描述
                        Log.e("影片描述", String.valueOf(video_TopicDesc));
                        videoBean.setDesc(video_TopicDesc);

                        String video_Url = jsonObjectItem.optString("mp4_url");//播放路径
                        videoBean.setPalyUri(video_Url);
                        Log.e("播放路径",video_Url);
                        videoItems.add(videoBean);
                    }

                }
                Log.e("数据大小", String.valueOf(videoItems.size()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return videoItems;
    }

    @Override
    public void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

}
