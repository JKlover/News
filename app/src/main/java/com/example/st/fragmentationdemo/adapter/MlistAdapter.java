package com.example.st.fragmentationdemo.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.st.fragmentationdemo.R;
import com.example.st.fragmentationdemo.domain.MImgUrlBean;
import com.squareup.picasso.Picasso;

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
public class MlistAdapter extends BaseQuickAdapter<MImgUrlBean,BaseViewHolder> {
    private Context context;
    public MlistAdapter(Context context) {
        super(R.layout.item_mzitu_card_list_view);
        this.context=context;
    }
    @Override
    protected void convert(BaseViewHolder helper, MImgUrlBean item) {
        GlideUrl url = new GlideUrl(item.getmImgdetail(), new LazyHeaders.Builder().addHeader("Referer", "http://www.mzitu.com/").build());
        Glide.with(context).load(url).into((ImageView) helper.getView(R.id.image));
//        Picasso.with(context).load(item.getmImgdetail()) .resize(720, 720)  .into((ImageView) helper.getView(R.id.image));

    }
}
