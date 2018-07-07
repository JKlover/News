package com.example.st.fragmentationdemo.net;

import org.jsoup.nodes.Document;

import io.reactivex.jsoup.network.manager.RxJsoupNetWork;
import io.reactivex.jsoup.network.manager.RxJsoupNetWorkListener;

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
public class RxNetWork {

    public  static  void getHtml(String url, final RxJsoupNetWorkCallbackListener listener){
        RxJsoupNetWork.getInstance().getApi( url
                , new RxJsoupNetWorkListener<String>() {
                    @Override
                    public void onNetWorkStart() {
                        listener.onNetStart();
                    }

                    @Override
                    public void onNetWorkError(Throwable e) {
                        listener.onError(e);
                    }

                    @Override
                    public void onNetWorkComplete() {

                    }

                    @Override
                    public void onNetWorkSuccess(String data) {
                        listener.onFinish(data);
                    }

                    @Override
                    public String getT(Document document) {
                        return document.toString();
                    }

                });


    }
}
