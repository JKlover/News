package com.example.st.fragmentationdemo.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.st.fragmentationdemo.R;
import com.example.st.fragmentationdemo.domain.MeiZiBean;

/**
 * Created by st on 18-2-14.
 */

public class MZiAdapter extends BaseQuickAdapter<MeiZiBean,BaseViewHolder> {
    private Context context;
    public MZiAdapter(Context context) {
        super(R.layout.item_mzitu_card_view);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MeiZiBean item) {
        GlideUrl url = new GlideUrl(item.getMeiziImg(), new LazyHeaders.Builder().addHeader("Referer", "http://www.mzitu.com/").build());
        Glide.with(context).load(url).dontAnimate().into((ImageView) helper.getView(R.id.image));
//        helper.setText(R.id.tv_mzitu_title,item.getMeiziTitle());

    }
}