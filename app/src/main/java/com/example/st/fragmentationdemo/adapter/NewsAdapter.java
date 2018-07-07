package com.example.st.fragmentationdemo.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.st.fragmentationdemo.R;
import com.example.st.fragmentationdemo.domain.NewsBean;

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
public class NewsAdapter extends BaseQuickAdapter<NewsBean,BaseViewHolder>{
    private Context context;
    public NewsAdapter(Context context) {
        super(R.layout.item_news_card_view);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsBean item) {
          helper.setText(R.id.tv_news_title,item.getTitle())
                .setText(R.id.tv_news_time,item.getTime())
                .setText(R.id.tv_news_content, item.getDesc());
          Glide.with(context).load(item.getImageSrc().get(0)).thumbnail(0.6f).into((ImageView) helper.getView(R.id.iv_news_icon));//用播放路径当图片地址展示

    }

//    @Override
//    protected BaseViewHolder createBaseViewHolder(View view) {
//        return super.createBaseViewHolder(view);
//    }
}
