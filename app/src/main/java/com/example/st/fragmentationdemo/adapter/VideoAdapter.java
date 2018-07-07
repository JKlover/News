package com.example.st.fragmentationdemo.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.st.fragmentationdemo.R;
import com.example.st.fragmentationdemo.domain.VideoBean;

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
public class VideoAdapter extends BaseQuickAdapter<VideoBean,BaseViewHolder>{
    private Context context;

    public VideoAdapter(Context context) {
        super(R.layout.item_video_card_view);
        this.context = context;

    }

    @Override
    protected void convert(BaseViewHolder helper, VideoBean item) {
          helper.setText(R.id.tv_name,item.getName())
                .setText(R.id.tv_news_time,item.getSource())
                .setText(R.id.tv_desc, item.getDesc());
          Glide.with(context).load(item.getImageUrl()).thumbnail(0.6f).into((ImageView) helper.getView(R.id.iv_icon));//用播放路径当图片地址展示

    }

}
