package com.example.st.fragmentationdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.st.fragmentationdemo.R;
import com.example.st.fragmentationdemo.domain.VideoBean;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

import java.util.List;

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
 * Created by st on 18-5-3
 */
public class VideoListAdapter  extends RecyclerView.Adapter<VideoViewHolder> {

    private Context mContext;
    private List<VideoBean>mVideoList;

    public VideoListAdapter(Context context, List<VideoBean>videoList) {
        mContext = context;
        mVideoList = videoList;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_video_list_card_view, parent, false);
        VideoViewHolder holder = new VideoViewHolder(itemView);
        TxVideoPlayerController controller = new TxVideoPlayerController(mContext);
        holder.setController(controller);
        return holder;
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        VideoBean video = mVideoList.get(position);
        holder.bindData(video);
    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }

    //下面两个方法提供给页面刷新和加载时调用
    public void addMore(List<VideoBean> addList) {
        //增加数据
        int position = mVideoList.size();
        mVideoList.addAll(position, addList);
        notifyItemInserted(position);
    }

    public void refresh(List<VideoBean> newList) {
        //刷新数据
        mVideoList.removeAll(mVideoList);
        mVideoList.addAll(newList);
        notifyDataSetChanged();
    }
}
