package com.example.st.fragmentationdemo.domain;

import java.io.Serializable;
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
 * Created by st on 18-5-5
 */
public class NewsBean implements Serializable{
    public static final String TAG = "News";

    public static final String NEWS_IMAGE = "news_Images";

    public static final String NEWS_TEXT = "news_Text";

    public static final String IMAGE_SRC = "imageSrc";

    public static final String IMAGE_COUNT = "imageCount";

    public static final String IMAGE_SIZE = "imageSize";

    private String type=null ;
    private String title=null;
    private String desc=null;
    private String time =null;
    private String contentUrl=null;
    private String docId=null;
    private String source=null;

    private List<String> imageSrc = new ArrayList<>();

    public void setType(String type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void addImageSrc(String src) {
        this.imageSrc.add(src);
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getTime() {
        return time;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public String getDocId() {
        return docId;
    }

    public String getSource() {
        return source;
    }

    public List<String> getImageSrc() {
        return imageSrc;
    }
}
