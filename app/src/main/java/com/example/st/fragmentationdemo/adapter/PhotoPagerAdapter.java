package com.example.st.fragmentationdemo.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.st.fragmentationdemo.domain.MImgUrlBean;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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
 * Created by st on 18-5-2
 */
public class PhotoPagerAdapter extends PagerAdapter {
    private List<String> mBean;
    public PhotoPagerAdapter(List<String>mBean) {
        this.mBean=mBean;
    }

    @Override
    public int getCount() {
        return mBean.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(container.getContext());
        final PhotoViewAttacher attacher = new PhotoViewAttacher(photoView);
//       Glide.with(container.getContext())
////                .load(mImgUrlBean.get(position))
//                .load(mImgUrlBean.get(position).getmImgdetail())
//                .into(photoView);
//        GlideUrl url = new GlideUrl(mBean.get(position), new LazyHeaders.Builder().addHeader("Referer", "http://www.mzitu.com/").build());
        Glide.with(container.getContext()).load(mBean.get(position)).into(photoView);
//        Glide.with(container.getContext()).load(mImgUrlBean.get(position).getmImgdetail()).into(photoView);

//        Picasso.with(container.getContext())
////                .load(mImgUrlBean.get(position))
//                .load(mImgUrlBean.get(position).getmImgdetail())
//                .into(photoView, new Callback() {
//                    @Override
//                    public void onSuccess() {
//                        attacher.update();
//                    }
//
//                    @Override
//                    public void onError() {
//
//                    }
//                });

        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
//        return false;
        return view == object;
    }
}
