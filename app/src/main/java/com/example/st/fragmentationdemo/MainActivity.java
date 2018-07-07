package com.example.st.fragmentationdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.st.fragmentationdemo.base.BaseMainFragment;
import com.example.st.fragmentationdemo.base.MySupportActivity;
import com.example.st.fragmentationdemo.base.MySupportFragment;
import com.example.st.fragmentationdemo.ui.activity.EasyWebActivity;
import com.example.st.fragmentationdemo.ui.activity.NewsDetails;
import com.example.st.fragmentationdemo.ui.fragment.account.Login;
import com.example.st.fragmentationdemo.ui.fragment.account.Register;
import com.example.st.fragmentationdemo.ui.fragment.home.HomeFragment;
//import com.example.st.fragmentationdemo.ui.fragment.mzitu.MzituFragment;
import com.example.st.fragmentationdemo.ui.fragment.mzitu.MzituFragment;
import com.example.st.fragmentationdemo.ui.fragment.report.ReportFragment;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class MainActivity extends MySupportActivity   implements NavigationView.OnNavigationItemSelectedListener, BaseMainFragment.OnFragmentOpenDrawerListener ,Login.OnLoginSuccessListener,Login.OnLoginGetDataListener{


    public static final String TAG = MainActivity.class.getSimpleName();

    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;
    private TextView mTvName;   // NavigationView上的名字
    private ImageView mImgNav;  // NavigationView上的头像
    private boolean loginStatus=false;//没有登录状态为false

    private MenuItem  menuItem ;
    private String sendData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MySupportFragment fragment = findFragment(HomeFragment.class);
        if (fragment == null) {
            loadRootFragment(R.id.fl_container, HomeFragment.newInstance());
        }
        initView();
    }

    /**
     * 设置动画，也可以使用setFragmentAnimator()设置
     */
    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置默认Fragment动画  默认竖向(和安卓5.0以上的动画相同)
        return super.onCreateFragmentAnimator();
        // 设置横向(和安卓4.x动画相同)
//        return new DefaultHorizontalAnimator();
        // 设置自定义动画
//        return new FragmentAnimator(enter,exit,popEnter,popExit);
    }

    private void initView() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        mDrawer.setDrawerListener(toggle);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.setCheckedItem(R.id.nav_home);
        //用于显示item原始图标的颜色
//        mNavigationView.setItemIconTintList(null);

        LinearLayout llNavHeader = (LinearLayout) mNavigationView.getHeaderView(0);
        mTvName = (TextView) llNavHeader.findViewById(R.id.tv_name);
        mImgNav = (ImageView) llNavHeader.findViewById(R.id.img_nav);
        llNavHeader.setOnClickListener(v -> {
            mDrawer.closeDrawer(GravityCompat.START);
            mDrawer.postDelayed(() -> {
                if (!loginStatus){
                    goLogin();
                }else {
                    return;
                }

            }, 250);
        });
    }

    private void goLogin() {
        start(Login.newInstance());
//        Intent intent=new Intent();
//        intent.setClass(this,EasyWebActivity.class);
//        startActivity(intent);
    }

    //双击返回退出
    @Override
    public void onBackPressedSupport() {


        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            ISupportFragment topFragment = getTopFragment();

            // 主页的Fragment
            if (topFragment instanceof BaseMainFragment) {
                mNavigationView.setCheckedItem(R.id.nav_home);
            }
            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                pop();
            } else {
                if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
                    finish();
                } else {
                    TOUCH_TIME = System.currentTimeMillis();
//                    Toast.makeText(this, R.string.press_again_exit, Toast.LENGTH_SHORT).show();
                }
            }
        }

//        //播放器的点击一次返回
//        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
//        super.onBackPressed();
    }

    @Override
    public void onOpenDrawer() {
        if (!mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.openDrawer(GravityCompat.START);
        }
    }

    /**
     *  注意了左边抽屉切换fragment时相应的fragment必须有视图有生命周期才可以切换回来，不然不可以切换回来，可以切换到一个空fragment试试
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        mDrawer.closeDrawer(GravityCompat.START);

        mDrawer.postDelayed(() -> {
            int id = item.getItemId();

            final ISupportFragment topFragment = getTopFragment();
            MySupportFragment myHome = (MySupportFragment) topFragment;
            if (id == R.id.nav_home) {
                HomeFragment fg = findFragment(HomeFragment.class);
                Bundle newBundle = new Bundle();
                newBundle.putString("from", "From:" + topFragment.getClass().getSimpleName());
                fg.putNewBundle(newBundle);
                myHome.start(fg, SupportFragment.SINGLETASK);
            } else if (id == R.id.nav_mzitu) {
               MzituFragment fg = findFragment(MzituFragment.class);
                if (fg == null) {
                    myHome.startWithPopTo(MzituFragment.newInstance(), HomeFragment.class, false);
                } else {
                    // 如果已经在栈内,则以SingleTask模式start
                    myHome.start(fg, SupportFragment.SINGLETASK);
                }
            }else if (id==R.id.nav_report){
                if (!loginStatus){
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle(getString(R.string.dialog_main_title))
                            .setMessage(getString(R.string.dialog_main_error_content))
                            .setPositiveButton(getString(R.string.dialog_ok), null)
                            .setNegativeButton(getString(R.string.login),new myOnClickListener())
//                                       .setNeutralButton(getString(R.string.dialog_register), new myOnClickListener())
                            .show();

//                        myHome.start(fg, SupportFragment.SINGLETASK);
                }else {
                    ReportFragment fg=findFragment(ReportFragment.class);
                    if (fg==null){
                        myHome.startWithPopTo(ReportFragment.newInstance(sendData),HomeFragment.class,false);
                    }else {
                        // 如果已经在栈内,则以SingleTask模式start
                        myHome.start(fg, SupportFragment.SINGLETASK);
                    }
                }


            }
//                } else if (id == R.id.nav_shop) {
//                    ShopFragment fragment = findFragment(ShopFragment.class);
//                    if (fragment == null) {
//                        myHome.startWithPopTo(ShopFragment.newInstance(), HomeFragment.class, false);
//                    } else {
//                        // 如果已经在栈内,则以SingleTask模式start,也可以用popTo
////                        start(fragment, SupportFragment.SINGLETASK);
//                        myHome.popTo(ShopFragment.class, false);
//                    }
//
//                } else if (id == R.id.nav_login) {
//                    goLogin();
//                } else if (id == R.id.nav_swipe_back) {
//                    startActivity(new Intent(MainActivity.this, SwipeBackSampleActivity.class));
//                }
        }, 300);
        return true;
    }
    @Override
    public void onLoginSuccess( String account) {
        loginStatus=true;
        sendData=account;
        mTvName.setText(account);
        mImgNav.setImageResource(R.drawable.ic_account_circle_white_48dp);
        Toast.makeText(this, "登录成功+", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetPostdata(String userId) {
        Toast.makeText(this, "登录ID!!!+"+userId, Toast.LENGTH_SHORT).show();
    }

    class myOnClickListener implements  DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if (i==-2){
                start(Login.newInstance());
            }
        }
    }

//    @Override
//    public void onLoginSuccess(String account) {
//
//    }

//    @Override
//    public void onBackPressed() {
//
//    }
}