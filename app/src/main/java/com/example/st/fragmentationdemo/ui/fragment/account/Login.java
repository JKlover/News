package com.example.st.fragmentationdemo.ui.fragment.account;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.st.fragmentationdemo.R;
import com.example.st.fragmentationdemo.base.BaseBackFragment;
import com.example.st.fragmentationdemo.utils.MD5Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.PublicKey;

import static com.example.st.fragmentationdemo.Api.news_Login_Api;

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
 * Created by st on 2018/5/8
 */
public class Login extends BaseBackFragment {

    private EditText etAccount, etPwd;
    private Button btnLogin, btnRegister;
    private OnLoginSuccessListener mOnLoginSuccessListener;
    private OnLoginGetDataListener mOnLoginGetDataListener ;
    public String getMsg;
    public static Login newInstance() {
        Bundle args = new Bundle();
        Login fg = new Login();
        fg.setArguments(args);
        return fg;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLoginSuccessListener) {
            mOnLoginSuccessListener = (OnLoginSuccessListener) context;
        }else if (context instanceof OnLoginGetDataListener){

        }
        else {
            throw new RuntimeException(context.toString()
                    + " must implement OnLoginSuccessListener");
        }

        mOnLoginGetDataListener = (OnLoginGetDataListener) context;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        etAccount = (EditText) view.findViewById(R.id.et_account);
        etPwd = (EditText) view.findViewById(R.id.et_password);
        btnLogin = (Button) view.findViewById(R.id.btn_login);
        btnRegister = (Button) view.findViewById(R.id.btn_register);

        toolbar.setTitle(R.string.login);
        initToolbarNav(toolbar);

        btnLogin.setOnClickListener(v -> {
            String strAccount = etAccount.getText().toString();
            String strPassword = etPwd.getText().toString();
            if (TextUtils.isEmpty(strAccount.trim())) {
                 new AlertDialog.Builder(getContext())
                                    .setTitle(getString(R.string.dialog_title))
                                    .setMessage(getString(R.string.error_username))
                                    .setPositiveButton(getString(R.string.dialog_ok), null)
                                    .setNegativeButton(getString(R.string.dialog_cancel), null)
//                                        .setNeutralButton(getString(R.string.dialog_register), new myOnClickListener())
                                    .show();
//                                start(Register.newInstance());
                return;
            }
            if (TextUtils.isEmpty(strPassword.trim())) {
//                    Toast.makeText(_mActivity, R.string.error_pwd, Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(getContext())
                        .setTitle(getString(R.string.dialog_title))
                        .setMessage(getString(R.string.error_userpwd))
                        .setPositiveButton(getString(R.string.dialog_ok), null)
                        .setNegativeButton(getString(R.string.dialog_cancel), null)
//                                        .setNeutralButton(getString(R.string.dialog_register), new myOnClickListener())
                        .show();
                return;
            }
            getPostData(strAccount,strPassword);

//                pop();
        });

        btnRegister.setOnClickListener(v -> start(Register.newInstance()));
    }

    private void getPostData(final String strAccount, String strPassword) {
        OkGo.<String>get(news_Login_Api)//
                .tag(this)
                .params("name", strAccount)//
                .params("passwd", MD5Utils.md5Password(strPassword))//
                .execute(new StringDialogCallback(_mActivity) {

                    @Override
                    public void onError(Response response) {
                        Log.e("返回的是", "出错，作者带着服务器跑路了，或者没网络");
                        new AlertDialog.Builder(getContext())
                                .setTitle("出事了")
                                .setMessage("没有网络，或者是作者带着服务器跑路了，连接不上服务器>>>>>>")
                                .setPositiveButton(getString(R.string.dialog_ok), null)
                                .setNegativeButton(getString(R.string.dialog_cancel), null)
//                                        .setNeutralButton(getString(R.string.dialog_register), new myOnClickListener())
                                .show();
                        return;
                    }

                    @Override
                    public void onSuccess(Response response) {
                        try {
                            getMsg= URLDecoder.decode(String.valueOf(response.body()),"utf-8");
                            mOnLoginGetDataListener.onGetPostdata(getMsg);
                            Log.e("返回的是", getMsg);
                            // 登录成功
                            if (getMsg.equals("false")){
//                                new AlertDialog.Builder(getContext())
//                                        .setTitle(getString(R.string.dialog_title))
//                                        .setMessage(getString(R.string.dialog_message))
//                                        .setPositiveButton(getString(R.string.dialog_ok), null)
//                                        .setNegativeButton(getString(R.string.dialog_cancel), null)
//                                        .setNeutralButton(getString(R.string.dialog_register), new myOnClickListener())
//                                        .show();
//                                start(Register.newInstance());
                                initBottomDialog();
                                return;
                            }
                            mOnLoginSuccessListener.onLoginSuccess(strAccount);
                            pop();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }


                    }
                });

    }

    private void initBottomDialog() {
        final BottomSheetDialog myDialog = new BottomSheetDialog(getContext());
        View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_bottom, null);
        Button btn_dialog_bottom_sheet_ok = dialogView.findViewById(R.id.btn_dialog_bottom_sheet_ok);
        Button btn_dialog_bottom_sheet_cancel = dialogView.findViewById(R.id.btn_dialog_bottom_sheet_cancel);
//        ImageView img_bottom_dialog = dialogView.findViewById(R.id.img_bottom_dialog);
//        Glide.with(getContext()).load(R.drawable.bottom_dialog).into(img_bottom_dialog);
        myDialog.setContentView(dialogView);

        btn_dialog_bottom_sheet_ok.setOnClickListener(v -> myDialog.dismiss());
        btn_dialog_bottom_sheet_cancel.setOnClickListener(v -> myDialog.dismiss());
        myDialog.show();
    }




    public interface OnLoginSuccessListener {
        void onLoginSuccess(String account);
    }

    public interface OnLoginGetDataListener{
        void onGetPostdata(String userId);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnLoginSuccessListener = null;
        mOnLoginGetDataListener=null;
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        hideSoftInput();
    }
}
