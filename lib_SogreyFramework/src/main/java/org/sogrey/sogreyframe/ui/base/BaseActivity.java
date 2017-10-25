package org.sogrey.sogreyframe.ui.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import org.sogrey.sogreyframe.BaseApplication;
import org.sogrey.sogreyframe.R;
import org.sogrey.sogreyframe.global.AppConstans;
import org.sogrey.sogreyframe.utils.SPUtils;
import org.sogrey.sogreyframe.utils.ScreenListener;
import org.sogrey.sogreyframe.utils.ScreenListener.ScreenStateListener;
import org.sogrey.sogreyframe.utils.ToastUtil;

import java.util.LinkedHashMap;

/**
 * 基本Activity类;
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static final int DELAY = 300;
    public Context mContext;
    protected ToastUtil mToast;
    private ActivityManager manager = ActivityManager.getActivityManager();
    private LinkedHashMap<String, Boolean> mLinkedMap;
    private ScreenListener mScreenListener;//锁屏监听

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        //        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        // WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            setTranslucentStatus(true);
//            SystemBarTintManager tintManager = new SystemBarTintManager(this);
//            tintManager.setStatusBarTintEnabled(true);
//            tintManager.setStatusBarTintResource(R.color.bg_blue);//通知栏所需颜色
//        }
        initState();
        mContext = this;
        manager.putActivity(this);
        mToast = ToastUtil.getSingleton(mContext);
        if (mLinkedMap == null) {
            mLinkedMap = new LinkedHashMap<>();
        }
        setContentView(getLayoutResId());
        init(savedInstanceState);
    }
    /**
     * 沉浸式状态栏
     */
    private void initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
    /**
     * 设置布局文件
     *
     * @return
     */
    protected abstract int getLayoutResId();

    /**
     * 初始化
     *
     * @author Sogrey
     * @date 2016年3月11日
     * @param savedInstanceState
     */
    protected abstract void init(Bundle savedInstanceState);

    /**
     * 检查app是否处于前台
     */
    protected void checkIsAppOnForeground() {
        if (!BaseApplication.getInstance().getIsAppOnForeground()) {//在后台
            if (!TextUtils.isEmpty((String) SPUtils.get(mContext, AppConstans.SP_KEY_FINGERPASSWORD, ""))) {
                //                Bundle bundle=new Bundle();
                //                bundle.putInt(CheckLock9ViewActivity.KEY_GOTO_WHERE,
                // CheckLock9ViewActivity
                //                        .VALUE_GOTO_RECENT);
                //                startIntent(
                //                        CheckLock9ViewActivity.class,bundle,
                //                        R.anim.base_slide_right_in,
                //                        R.anim.base_slide_remain
                //                );
            }
        }
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean b) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (b) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    protected void onStart() {
        checkIsAppOnForeground();
        mScreenListener = new ScreenListener(this);
        mScreenListener.begin(new ScreenStateListener() {

            @Override
            public void onUserPresent() {
                Log.e("onScreen", "onUserPresent");
                BaseApplication.getInstance().setIsAppOnForeground(false);
            }

            @Override
            public void onScreenOn() {
                Log.e("onScreen", "onScreenOn");
            }

            @Override
            public void onScreenOff() {
                Log.e("onScreen", "onScreenOff");
                BaseApplication.getInstance().setIsAppOnForeground(false);
            }
        });
        checkVPN();
        super.onStart();
    }

//    private DialogUtils dialogSetVpn;

    protected boolean checkVPN() {
        //        if (!Utility.isVpnUsed()&&(dialogSetVpn==null||(dialogSetVpn!=null&&!dialogSetVpn
        //                .isShowing()))) {
        //            dialogSetVpn=new DialogUtils(this,R.style.CircularDialog) {
        //
        //                @Override
        //                public void ok() {
        //                    IntentUtils.GoToVPNSetting(mContext);
        //                }
        //
        //                @Override
        //                public void cancle() {}
        //
        //                @Override
        //                public void ignore() {}
        //            };
        //            dialogSetVpn.show();
        //            TextView tv=new TextView(this);
        //            tv.setText(R.string.content_setting_vpn_null);
        //            dialogSetVpn.setContent(tv);
        //            dialogSetVpn.setCancleable(false);
        //            dialogSetVpn.setDialogTitle(R.string.goto_setting_vpn);
        //            dialogSetVpn.setDialogCancleBtn(R.string.cancle);
        //            dialogSetVpn.setDialogOkBtn(R.string.goto_setting);
        //            dialogSetVpn.setDialogCancleBtnColor(getResources().getColor(R.color
        // .dimgray));
        //            dialogSetVpn.setDialogOkBtnColor(getResources().getColor(R.color.dimgray));
        //        }
        return true;// (Utility.isVpnUsed());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStop() {
        mScreenListener.unregisterListener();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        WindowManager.LayoutParams params = getWindow().getAttributes();
        if (params.softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE) {
            // 隐藏软键盘
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            params.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN;
        }
    }

    // 退出提示;
    public void showExitDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("退出");
        builder.setMessage("确定退出程序？");

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                        exit();
                    }
                });

        String negativeText = getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }

    /**
     * 退出应用
     */
    public void exit() {
        if (mLinkedMap != null) {
            mLinkedMap.clear();
            mLinkedMap = null;
        }
        manager.exit();
    }

    /**
     * 退出应用 - 真退出
     */
    public void exitProgrames() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 退出应用 - 假退出
     */
    public void exit2Home() {
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.removeActivity(this);
        manager.removeActivityCls(this.getClass());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK: {//Back 键
                if (doSomeThingBeforeDestroy()) return true;
                if (isShowExitDialog()) {//彈出退出確認對話框
                    showExitDialog();
                } else {//不彈退出確認對話框
                    if (System.currentTimeMillis() - BaseApplication.getInstance().dataFrist < 2000) {
                        exit();
                    } else {
                        ToastUtil.showToast(mContext, "再次点击返回键退出");
                        BaseApplication.getInstance().dataFrist = System.currentTimeMillis();
                    }
                }
            }
            return true;
            case KeyEvent.KEYCODE_HOME: {//Home 键
                BaseApplication.getInstance().setIsAppOnForeground(false);
            }
            break;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 返回退出时要做的事，返回true会跳过后续退出的操作
     *
     * @return
     */
    protected boolean doSomeThingBeforeDestroy() {
        return false;
    }

    public void finishThis() {
//        if (checkVPN()) {//已连接VPN
            finish();
            overridePendingTransition(R.anim.base_slide_remain, R.anim.base_slide_right_out);
//        }
    }

    public void finishThisDelay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finishThis();
            }
        }, 1000);
    }

    public void finishThisRemain() {
//        if (checkVPN()) {//已连接VPN
            finish();
            overridePendingTransition(R.anim.base_slide_remain, R.anim.base_slide_remain);
//        }
    }

    public void finishThisRemainDelay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finishThisRemain();
            }
        }, 1000);
    }

    /**
     * 若要自定義Activity退出時是顯示提示框還是toast,默認toast,若要修改需複寫此方法返回true。
     *
     * @return if true show exit dialog,Otherwise show toast.
     */
    public boolean isShowExitDialog() {
        return false;
    }

    public void putNetWorkFlag(String key, boolean val) {
        if (mLinkedMap == null) {
            mLinkedMap = new LinkedHashMap<String, Boolean>();
        }
        mLinkedMap.put(key, val);
    }

    public boolean getNetWorkFlag(String key, boolean val) {
        if (mLinkedMap == null) {
            mLinkedMap = new LinkedHashMap<String, Boolean>();
        }
        if (mLinkedMap.containsKey(key)) {
            val = mLinkedMap.get(key);
        } else {
            val = false;
        }
        return val;
    }

    public void clearAllNetWorkFlag() {
        if (mLinkedMap == null) {
            mLinkedMap = new LinkedHashMap<String, Boolean>();
        }
        mLinkedMap.clear();
        mLinkedMap = null;
    }

    /**
     * Activity 跳转
     *
     * @param cls 要跳转到的Activity
     */
    public void startIntent(Class<?> cls) {
        startIntent(cls, 0, 0, false);
    }

    public void startIntent(Class<?> cls, boolean isFinish) {
        startIntent(cls, 0, 0, isFinish);
    }

    /**
     * Activity 跳转
     *
     * @param cls   要跳转到的Activity
     * @param delay 自定義延時（毫秒）
     */
    public void startIntent(Class<?> cls, int delay) {
        startIntent(cls, 0, 0, delay, false);
    }

    public void startIntent(Class<?> cls, int delay, boolean isFinish) {
        startIntent(cls, 0, 0, delay, isFinish);
    }

    /**
     * Activity 跳转(动画)
     *
     * @param cls     要跳转到的Activity
     * @param animIn  进入动画
     * @param animOut 退出动画
     */
    public void startIntent(Class<?> cls, final int animIn, final int animOut) {
        startIntent(cls, animIn, animOut, -1, false);
    }

    public void startIntent(Class<?> cls, final int animIn, final int animOut, boolean isFinish) {
        startIntent(cls, animIn, animOut, -1, isFinish);
    }

    /**
     * Activity 跳转(动画)
     *
     * @param cls     要跳转到的Activity
     * @param animIn  进入动画
     * @param animOut 退出动画
     * @param delay   自定義延時（毫秒）
     */
    public void startIntent(Class<?> cls, final int animIn, final int animOut, int delay) {
        startIntent(cls, null, animIn, animOut, delay, false);
    }

    public void startIntent(Class<?> cls, final int animIn, final int animOut, int delay, boolean isFinish) {
        startIntent(cls, null, animIn, animOut, delay, isFinish);
    }

    /**
     * Activity 跳转(动画)
     *
     * @param cls     要跳转到的Activity
     * @param bundle  传递参数
     * @param animIn  进入动画
     * @param animOut 退出动画
     */
    public void startIntent(Class<?> cls, Bundle bundle, final int animIn, final int animOut) {
        startIntent(cls, bundle, animIn, animOut, -1, false);
    }

    public void startIntent(Class<?> cls, Bundle bundle, final int animIn, final int animOut, boolean isFinish) {
        startIntent(cls, bundle, animIn, animOut, -1, isFinish);
    }

    /**
     * Activity 跳转(动画)
     *
     * @param cls     要跳转到的Activity
     * @param bundle  传递参数
     * @param animIn  进入动画
     * @param animOut 退出动画
     * @param delay   自定義延時（毫秒）
     */
    public void startIntent(Class<?> cls, Bundle bundle, final int animIn, final int animOut, int delay, final boolean isFinish) {
        if (!checkVPN()) return;
        if (manager.hasActivityCls(cls)) {
            return;
        }
        manager.putActivityCls(cls);
        final Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) intent.putExtras(bundle);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(intent);
                if (animIn != 0 && animOut != 0) {
                    try {
                        overridePendingTransition(animIn, animOut);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (isFinish) finish();
            }
        }, delay < 0 ? DELAY : delay);
    }

    /**
     * Activity 跳转(需要返回结果)
     *
     * @param cls         要跳转到的Activity
     * @param requestCode 请求码
     */
    public void startIntentForResult(Class<?> cls, int requestCode) {
        startIntentForResult(cls, requestCode, 0, 0);
    }

    /**
     * Activity 跳转(需要返回结果)
     *
     * @param cls         要跳转到的Activity
     * @param requestCode 请求码
     * @param delay       自定義延時（毫秒）
     */
    public void startIntentForResult(Class<?> cls, int requestCode, int delay) {
        startIntentForResult(cls, requestCode, 0, 0, delay);
    }

    /**
     * Activity 跳转(需要返回结果,动画)
     *
     * @param cls         要跳转到的Activity
     * @param requestCode 请求码
     * @param animIn      进入动画
     * @param animOut     退出动画
     */
    public void startIntentForResult(Class<?> cls, int requestCode, int animIn, int animOut) {
        startIntentForResult(cls, null, requestCode, animIn, animOut);
    }

    /**
     * Activity 跳转(需要返回结果,动画)
     *
     * @param cls         要跳转到的Activity
     * @param requestCode 请求码
     * @param animIn      进入动画
     * @param animOut     退出动画
     * @param delay       自定義延時（毫秒）
     */
    public void startIntentForResult(Class<?> cls, int requestCode, int animIn, int animOut, int delay) {
        startIntentForResult(cls, null, requestCode, animIn, animOut, delay);
    }

    /**
     * Activity 跳转(需要返回结果,动画)
     *
     * @param cls         要跳转到的Activity
     * @param bundle      參數
     * @param requestCode 请求码
     * @param animIn      进入动画
     * @param animOut     退出动画
     */
    public void startIntentForResult(Class<?> cls, Bundle bundle, int requestCode, int animIn, int animOut) {
        startIntentForResult(cls, bundle, requestCode, animIn, animOut, -1);
    }

    /**
     * Activity 跳转(需要返回结果,动画)
     *
     * @param cls         要跳转到的Activity
     * @param bundle      传递参数
     * @param requestCode 请求码
     * @param animIn      进入动画
     * @param animOut     退出动画
     * @param delay       自定義延時（毫秒）
     */
    public void startIntentForResult(Class<?> cls, Bundle bundle, final int requestCode, final int animIn, final int animOut, int delay) {
        if (!checkVPN()) return;
        if (manager.hasActivityCls(cls)) {
            return;
        }
        manager.putActivityCls(cls);
        final Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) intent.putExtras(bundle);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivityForResult(intent, requestCode);
                if (animIn != 0 && animOut != 0) {
                    try {
                        overridePendingTransition(animIn, animOut);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, delay < 0 ? DELAY : delay);
    }
}
