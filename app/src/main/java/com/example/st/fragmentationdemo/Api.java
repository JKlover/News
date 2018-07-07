package com.example.st.fragmentationdemo;

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
public interface Api {
    //视频
    //        "http://c.3g.163.com/nc/video/list/V9LG4B3A0/n/0-8.html";
    String video_Base_Page="-8.html";

    String video_Base="/n/";
    String video_Base_Uri="http://c.3g.163.com/nc/video/list/";
            // 热点视频
    String video_HOT = "V9LG4B3A0";
    // 娱乐视频
    String video_ENTERTAINMENT = "V9LG4CHOR";
    // 搞笑视频
    String video_FUN= "V9LG4E6VR";
    // 精品视频
    String video_CHOICE= "00850FRB";
    //妹子网图片
    String BASE_PAGE="page/";
    String BASE_URL="http://www.mzitu.com/";
    String A_URL="http://m.mzitu.com/xinggan/";
    String B_URL="http://m.mzitu.com/japan/";
    String C_URL="http://m.mzitu.com/taiwan/";
    String D_URL="http://m.mzitu.com/mm/";
    String E_URL="http://m.mzitu.com/zipai/";
    String F_URL="http://www.tesetu.com/tupian/xfmn/";
//    天极网图片
//    http://pic.yesky.com/c/6_18332.shtml
//    http://pic.yesky.com/c/6_243.shtml
//    http://pic.yesky.com/c/6_20491.shtml
//    http://pic.yesky.com/c/6_61112.shtml
//    http://pic.yesky.com/c/6_60201.shtml
    String BASE_Tianji=".shtml";
    String picA="http://pic.yesky.com/c/6_18332.shtml";
    String picB="http://pic.yesky.com/c/6_243.shtml";
    String picC="http://pic.yesky.com/c/6_20491.shtml";
    String picD="http://pic.yesky.com/c/6_61112.shtml";
    String picE="http://pic.yesky.com/c/6_61100.shtml";

    String picA_Base="http://pic.yesky.com/c/6_18332_";//动漫图片
    String picB_Base="http://pic.yesky.com/c/6_243_";//明星写真
    String picC_Base="http://pic.yesky.com/c/6_20491_";//美女图片
    String picD_Base="http://pic.yesky.com/c/6_61112_";//搞笑图片
    String picE_Base="http://pic.yesky.com/c/6_61100_";//时尚伊人



    //新闻http://c.m.163.com/nc/article/headline/T1348649580692/0-10.html
    String news_Base_Page="-10.html";
    String news_Base_Uri="http://c.m.163.com/nc/article/headline/";
    String news_A="T1348647909107/";//头条
    String news_B="T1348649580692/";//科技
    String news_C="T1348648756099/";//财经
    String news_D="T1348648141035/";//军事
    String news_E="T1348649079062/";//体育
    String news_F="T1348648650048/";//电影
    String news_G="T1348654204705/";//手机

    //新闻详情页面的uri
    String news_Details_Base="http://c.m.163.com/nc/article/";
    String news_Details_Base_Html="/full.html";
    //服务器通信Api,改为自己的运行ip
    String news_Login_Api="http://127.0.0.1:8080/Mynews/LoginServlet";
    String news_Register_Api="http://127.0.0.1:8080/Mynews/RegisterServlet";
    String news_Report_Files_Api="http://127.0.0.1:8080/Mynews/ReportServlet";
    String news_Report_Text_Api="http://127.0.0.1:8080/Mynews/PersonServlet";
    String news_Report_List_Api="http://127.0.0.1:8080/Mynews/ShowListServlet";
}
