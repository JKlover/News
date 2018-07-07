package com.example.st.fragmentationdemo.ui.fragment.report;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.st.fragmentationdemo.R;
import com.example.st.fragmentationdemo.base.BaseBackFragment;
import com.example.st.fragmentationdemo.base.BaseMainFragment;
import com.example.st.fragmentationdemo.domain.ReportBean;
import com.example.st.fragmentationdemo.ui.fragment.account.Login;
import com.example.st.fragmentationdemo.ui.fragment.account.StringDialogCallback;
import com.example.st.fragmentationdemo.utils.GlideImageLoader;
import com.google.gson.Gson;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import static com.example.st.fragmentationdemo.Api.news_Report_Files_Api;
import static com.example.st.fragmentationdemo.Api.news_Report_Text_Api;

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
 * Created by st on 2018/5/10
 */
public class ReportFragment extends BaseBackFragment {
    private static String ARG_DATA="data";
    private EditText mTitle, mTime,mLocation,mContent;
    private Button mFiles,mPost;
//    private ReportBean bean;
    private String reportUserName;
    String picUrl;

    private ArrayList<ImageItem>mImageItems;


    public static  ReportFragment newInstance(String data) {
        Bundle args = new Bundle();
        args.putString(ARG_DATA, data);
        ReportFragment fg = new ReportFragment();
        fg.setArguments(args);
        return fg;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            reportUserName= args.getString(ARG_DATA);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_report,container,false);
       initView(view);
        return view;
    }

    private void initView(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.report);
        initToolbarNav(toolbar);
        mTitle=view.findViewById(R.id.et_title);
        mTime=view.findViewById(R.id.et_time);
        mLocation=view.findViewById(R.id.et_location);
        mContent=view.findViewById(R.id.et_content);
        mFiles=view.findViewById(R.id.btn_files);
        mPost=view.findViewById(R.id.btn_post);
        mFiles.setOnClickListener(view1 -> {
            ImagePicker imagePicker = ImagePicker.getInstance();
            imagePicker.setImageLoader(new GlideImageLoader());
            imagePicker.setMultiMode(true);   //多选
            imagePicker.setShowCamera(true);  //显示拍照按钮
            imagePicker.setSelectLimit(1);    //最多选择9张
            imagePicker.setCrop(false);       //不进行裁剪
            Intent intent = new Intent(_mActivity, ImageGridActivity.class);
            startActivityForResult(intent, 100);
        });
        mPost.setOnClickListener(view2 -> doPostData());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                mImageItems = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (mImageItems != null && mImageItems.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < mImageItems.size(); i++) {
                        if (i == mImageItems.size() - 1) sb.append("图片").append(i + 1).append(" ： ").append(mImageItems.get(i).path);
                        else sb.append("图片").append(i + 1).append(" ： ").append(mImageItems.get(i).path).append("\n");
                    }
//                    tvImages.setText(sb.toString());
                } else {
//                    tvImages.setText("--");
                }
            } else {
                Toast.makeText(_mActivity, "没有选择图片", Toast.LENGTH_SHORT).show();
//                tvImages.setText("--");
            }
        }

        doPostFiles();

    }

    private void doPostFiles() {
        ArrayList<File> files = new ArrayList<>();
        if (mImageItems != null && mImageItems.size() > 0) {
            for (int i = 0; i < mImageItems.size(); i++) {
                files.add(new File(mImageItems.get(i).path));
            }
        }

        OkGo.<String>post(news_Report_Files_Api)//
                .tag(this)//
//                .isMultipart(true)
//                .params("reportTitle", bean.getTitle())//
//                .params("reportTime",bean.getTime())//
//                .params("reportLocation", bean.getLocation())//
//                .params("reportContent", bean.getContent())//
//                .params("reportUserName", reportUserName)//

                .addFileParams("File", files)
                .execute(new StringCallback() {

                    @Override
                    public void onError(Response<String> response) {

                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        picUrl= response.body();
                        Log.e("返回的是", picUrl);

                    }
                });
    }

    private void doPostData() {
//        ArrayList<File> files = new ArrayList<>();
//        if (mImageItems != null && mImageItems.size() > 0) {
//            for (int i = 0; i < mImageItems.size(); i++) {
//                files.add(new File(mImageItems.get(i).path));
//            }
//        }
        String reportTitle=mTitle.getText().toString();
        String reportTime=mTime.getText().toString();
        String reportLocation=mLocation.getText().toString();
        String reportContent=mContent.getText().toString();
        ReportBean  bean=new ReportBean();
        bean.setTitle(reportTitle);
        bean.setTime(reportTime);
        bean.setLocation(reportLocation);
        bean.setContent(reportContent);
        bean.setUserName(reportUserName);
        bean.setPicUrl(picUrl);
        Log.e("得到的集合为", String.valueOf(bean));
        Log.e("得到的图片服务器路径", picUrl);
        Gson gson = new Gson();
        // 简单的bean转为json
        String bean2str = gson.toJson(bean);
        OkGo.<String>post(news_Report_Text_Api)//
                .tag(this)//
                .params("reportInfo",bean2str)
//                .addFileParams("File", files)
                .execute(new StringCallback() {

                    @Override
                    public void onError(Response<String> response) {

                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String bb= response.body();
                        Log.e("返回的是", bb);
                        new AlertDialog.Builder(getContext())
                                .setTitle(getString(R.string.dialog_report_title))
                                .setMessage(getString(R.string.dialog_report_msg))
                                .setPositiveButton(getString(R.string.dialog_ok), new myOnClickListener())
                                .setNegativeButton(getString(R.string.dialog_cancel), new myOnClickListener())
                                .show();
                    }
                });
    }

    class myOnClickListener implements  DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if (i==-1){
                pop();
            }else {
                pop();
            }
        }
    }

}
