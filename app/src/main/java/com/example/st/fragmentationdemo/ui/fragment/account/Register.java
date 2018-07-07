package com.example.st.fragmentationdemo.ui.fragment.account;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import com.example.st.fragmentationdemo.R;
import com.example.st.fragmentationdemo.base.BaseBackFragment;
import com.example.st.fragmentationdemo.utils.MD5Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static com.example.st.fragmentationdemo.Api.news_Register_Api;

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
public class Register extends BaseBackFragment {
    private EditText etAccount, etPwd, etPwdConfirm;
    private Button btnRegister;
    private Login.OnLoginSuccessListener mOnLoginSuccessListener;

    public static Register newInstance() {
        Bundle args = new Bundle();
        Register fg = new Register();
        fg.setArguments(args);
        return fg;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Login.OnLoginSuccessListener) {
            mOnLoginSuccessListener = (Login.OnLoginSuccessListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnLoginSuccessListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        etAccount = (EditText) view.findViewById(R.id.et_account);
        etPwd = (EditText) view.findViewById(R.id.et_password);
        etPwdConfirm = (EditText) view.findViewById(R.id.et_password_confirm);
        btnRegister = (Button) view.findViewById(R.id.btn_register);

        showSoftInput(etAccount);

        toolbar.setTitle("注册");
        initToolbarNav(toolbar);

        btnRegister.setOnClickListener(v -> {
            String strAccount = etAccount.getText().toString();
            String strPassword = etPwd.getText().toString();
            String strPasswordConfirm = etPwdConfirm.getText().toString();
            if (TextUtils.isEmpty(strAccount.trim())) {
//                    Toast.makeText(_mActivity, R.string.error_username, Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(getContext())
                        .setTitle(getString(R.string.dialog_register_title))
                        .setMessage(getString(R.string.error_username))
                        .setPositiveButton(getString(R.string.dialog_ok), null)
                        .setNegativeButton(getString(R.string.dialog_cancel), null)
//                                        .setNeutralButton(getString(R.string.dialog_register), new myOnClickListener())
                        .show();
                return;
            }
            if (TextUtils.isEmpty(strPassword.trim()) || TextUtils.isEmpty(strPasswordConfirm.trim())) {
//                    Toast.makeText(_mActivity, R.string.error_pwd, Toast.LENGTH_SHORT).show();
                 new AlertDialog.Builder(getContext())
                 .setTitle(getString(R.string.dialog_register_title))
                        .setMessage(getString(R.string.error_userpwd))
                        .setPositiveButton(getString(R.string.dialog_ok), null)
                        .setNegativeButton(getString(R.string.dialog_cancel), null)
//                                        .setNeutralButton(getString(R.string.dialog_register), new myOnClickListener())
                        .show();
                return;
            }
            doPostData(strAccount,strPassword);
//                // 注册成功
//                mOnLoginSuccessListener.onLoginSuccess(strAccount);
//                popTo(Login.class, true);
        });
    }

    private void doPostData(final String strAccount, String strPassword) {
        OkGo.<String>get(news_Register_Api)//
                .tag(this)//
//                .headers("header1", "headerValue1")//
                .params("username", strAccount)//
                .params("userpwd", MD5Utils.md5Password(strPassword))//
                .execute(new StringDialogCallback(_mActivity) {

                    @Override
                    public void onError(Response response) {
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
                            String getMsg= URLDecoder.decode(String.valueOf(response.body()),"utf-8");
                            Log.e("返回的是", getMsg);
                            // 登录成功
                            if (getMsg.equals("注册失败用户名已经存在!")||getMsg.equals("注册失败!")){
                                new AlertDialog.Builder(getContext())
                                        .setTitle(getString(R.string.dialog_register_title))
                                        .setMessage(getString(R.string.dialog_content_register))
                                        .setPositiveButton(getString(R.string.dialog_ok), null)
                                        .setNegativeButton(getString(R.string.dialog_cancel), null)
                                        .show();
                                return;
                            }else {
//                                new AlertDialog.Builder(getContext())
//                                        .setMessage(getString(R.string.dialog_register_success))
//                                        .setPositiveButton(getString(R.string.login),null)
//                                        .show();
                                mOnLoginSuccessListener.onLoginSuccess(strAccount);
                                popTo(Login.class, true);
                            }
//
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }


                    }
                });
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        hideSoftInput();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnLoginSuccessListener = null;
    }
}
