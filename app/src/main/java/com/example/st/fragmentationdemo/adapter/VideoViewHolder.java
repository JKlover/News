package com.example.st.fragmentationdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.st.fragmentationdemo.R;
import com.example.st.fragmentationdemo.domain.VideoBean;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.TxVideoPlayerController;


/**
 * Created by XiaoJianjun on 2017/5/21.
 */

public class VideoViewHolder extends RecyclerView.ViewHolder {

    public TxVideoPlayerController mController;
    public NiceVideoPlayer mVideoPlayer;
    public TextView mTVSource;
    public TextView mTVDesc;

    public VideoViewHolder(View itemView) {
        super(itemView);
        mVideoPlayer = (NiceVideoPlayer) itemView.findViewById(R.id.nice_video_player);
        mTVSource=itemView.findViewById(R.id.tv_video_source);
        mTVDesc=itemView.findViewById(R.id.tv_video_desc);


        // 将列表中的每个视频设置为默认16:9的比例
        ViewGroup.LayoutParams params = mVideoPlayer.getLayoutParams();
        params.width = itemView.getResources().getDisplayMetrics().widthPixels; // 宽度为屏幕宽度
        params.height = (int) (params.width * 9f / 16f);    // 高度为宽度的9/16
        mVideoPlayer.setLayoutParams(params);
    }

    public void setController(TxVideoPlayerController controller) {
        mController = controller;
        mVideoPlayer.setController(mController);
    }

    public void bindData(VideoBean bean) {
        mController.setTitle(bean.getName());
        mController.setLenght(bean.getSize());
        Glide.with(itemView.getContext())
                .load(bean.getImageUrl())
//                .placeholder(android.R.mipmap.sym_def_app_icon)
                .crossFade()
                .into(mController.imageView());
        mVideoPlayer.setUp(bean.getPalyUri(), null);
        mTVSource.setText(bean.getSource());
        mTVDesc.setText(bean.getDesc());
    }
}
