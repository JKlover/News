package com.example.st.fragmentationdemo.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.st.fragmentationdemo.R;
import com.example.st.fragmentationdemo.domain.NewsBean;
import com.example.st.fragmentationdemo.domain.ReportBean;

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
public class ReportAdapter extends BaseQuickAdapter<ReportBean,BaseViewHolder>{
    private Context context;
    public ReportAdapter(Context context) {
        super(R.layout.item_news_card_view);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ReportBean item) {
          helper.setText(R.id.tv_news_title,item.getTitle())
                .setText(R.id.tv_news_time,item.getTime())
                .setText(R.id.tv_news_content, item.getContent());
          Glide.with(context).load(item.getPicUrl()).thumbnail(0.6f).into((ImageView) helper.getView(R.id.iv_news_icon));//用播放路径当图片地址展示

    }

}
