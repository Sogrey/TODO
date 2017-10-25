package org.sogrey.sogreyframe.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import org.sogrey.sogreyframe.utils.FingerprintUtils.OnFingerprintAuthenticationListener
        .FingerprintAuthentType;

/**
 * 指纹识别
 * Created by Sogrey on 2016/10/8.
 * <p/>
 * if (isFinger()) {
 * Toast.makeText(MainActivity.this, "请进行指纹识别", Toast.LENGTH_LONG).show();
 * Log(TAG, "keyi");
 * startListening(null);
 * }
 *
 * @ Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
 * if (requestCode == REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS) {
 * // Challenge completed, proceed with using cipher
 * if (resultCode == RESULT_OK) {
 * Toast.makeText(this, "识别成功", Toast.LENGTH_SHORT).show();
 * } else {
 * Toast.makeText(this, "识别失败", Toast.LENGTH_SHORT).show();
 * }
 * }
 * }
 * <p/>
 * http://m.blog.csdn.net/article/details?id=51437795
 */

public class FingerprintUtils {

    public static final String TAG="FingerprintUtils";

    private final Context mContext;
    FingerprintManager manager;
    KeyguardManager    mKeyManager;
    private final static int REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS=0;
    OnFingerprintAuthenticationListener mOnFingerprintAuthenticationListener;

    public FingerprintUtils(Context context,OnFingerprintAuthenticationListener l) {
        this.mContext=context;
        this.mOnFingerprintAuthenticationListener=l;
        manager=(FingerprintManager)mContext.getSystemService(Context.FINGERPRINT_SERVICE);
        mKeyManager=(KeyguardManager)mContext.getSystemService(Context.KEYGUARD_SERVICE);
    }

    @TargetApi(23)
    public boolean isFinger() {

        //android studio 上，没有这个会报错
        if (ActivityCompat.checkSelfPermission(mContext,Manifest.permission.USE_FINGERPRINT)
            !=PackageManager.PERMISSION_GRANTED) {
            LogUtil.e(TAG,"没有指纹识别权限:android.permission.USE_FINGERPRINT");
            if (mOnFingerprintAuthenticationListener!=null) {
                mOnFingerprintAuthenticationListener.post(FingerprintAuthentType
                                                                  .TYPE_NO_PERMISSION_USE_FINGERPRINT);
            }
            return false;
        }
        LogUtil.d(TAG,"有指纹权限");
        //判断硬件是否支持指纹识别
        if (!manager.isHardwareDetected()) {
            LogUtil.e(TAG,"没有指纹识别模块");
            if (mOnFingerprintAuthenticationListener!=null) {
                mOnFingerprintAuthenticationListener.post(FingerprintAuthentType
                                                                  .TYPE_NO_FINGERPRINT_HARDWARE_DETECTED);
            }
            return false;
        }
        LogUtil.d(TAG,"有指纹模块");
        //判断 是否开启锁屏密码

        if (!mKeyManager.isKeyguardSecure()) {
            LogUtil.e(TAG,"没有开启锁屏密码");
            if (mOnFingerprintAuthenticationListener!=null) {
                mOnFingerprintAuthenticationListener.post(FingerprintAuthentType
                                                                  .TYPE_DID_NOT_OPEN_SCREEN_LOCK);
            }
            return false;
        }
        LogUtil.d(TAG,"已开启锁屏密码");
        //判断是否有指纹录入
        if (!manager.hasEnrolledFingerprints()) {
            LogUtil.e(TAG,"没有录入指纹");
            if (mOnFingerprintAuthenticationListener!=null) {
                mOnFingerprintAuthenticationListener.post(FingerprintAuthentType
                                                                  .TYPE_FINGERPRINT_BANK_EMPTY);
            }
            return false;
        }
        LogUtil.d(TAG,"已录入指纹");

        return true;
    }

    @TargetApi(23)
    public void startListening(/*FingerprintManager.CryptoObject cryptoObject*/) {
        //android studio 上，没有这个会报错
        if (ActivityCompat.checkSelfPermission(mContext,Manifest.permission.USE_FINGERPRINT)
            !=PackageManager.PERMISSION_GRANTED) {
            LogUtil.e(TAG,"没有指纹识别权限");
            if (mOnFingerprintAuthenticationListener!=null) {
                mOnFingerprintAuthenticationListener.post(FingerprintAuthentType
                                                                  .TYPE_NO_PERMISSION_USE_FINGERPRINT);
            }
            return;
        }

        final CancellationSignal mCancellationSignal=new CancellationSignal();
        //回调方法

        FingerprintManager.AuthenticationCallback mSelfCancelled=new FingerprintManager
                .AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,CharSequence errString) {
                //但多次指纹密码验证错误后，进入此方法；并且，不能短时间内调用指纹验证
                LogUtil.d(TAG,errString.toString());
                if (mOnFingerprintAuthenticationListener!=null) {
                    mOnFingerprintAuthenticationListener.post(FingerprintAuthentType
                                                                      .TYPE_MULTIPLE_FINGERPRINT_VERIFICATION_FAILED);
                }
                mCancellationSignal.cancel();
                //            showAuthenticationScreen();
            }

            @Override
            public void onAuthenticationHelp(int helpCode,CharSequence helpString) {
                //            ToastUtil.getSingleton(mContext).showToast(helpString.toString());
                LogUtil.d(TAG,helpString.toString());
            }

            @Override
            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                LogUtil.d(TAG,"指纹识别成功");
                if (mOnFingerprintAuthenticationListener!=null) {
                    mOnFingerprintAuthenticationListener.post(FingerprintAuthentType
                                                                      .TYPE_FINGERPRINT_VERIFICATION_SUCCESS);
                }
            }

            @Override
            public void onAuthenticationFailed() {
                LogUtil.d(TAG,"指纹识别失败");
                if (mOnFingerprintAuthenticationListener!=null) {
                    mOnFingerprintAuthenticationListener.post(FingerprintAuthentType
                                                                      .TYPE_FINGERPRINT_VERIFICATION_FAILED);
                }
            }
        };

        manager.authenticate(null/*cryptoObject*/,mCancellationSignal,0,mSelfCancelled,null);

    }

    /**
     * 锁屏密码
     */
    @TargetApi(21)
    private void showAuthenticationScreen() {

        Intent intent=mKeyManager.createConfirmDeviceCredentialIntent("finger","测试指纹识别");
        if (intent!=null) {
            ((Activity)mContext).startActivityForResult(
                    intent,
                    REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS
            );
        }
    }

    public interface OnFingerprintAuthenticationListener {

        enum FingerprintAuthentType {
            /**
             * 没有指纹权限
             */
            TYPE_NO_PERMISSION_USE_FINGERPRINT(0x0,"没有指纹权限"),
            /**
             * 没有是纹识别模块
             */
            TYPE_NO_FINGERPRINT_HARDWARE_DETECTED(0x1,"没有指纹识别模块"),
            /**
             * 没有设置锁屏密码
             */
            TYPE_DID_NOT_OPEN_SCREEN_LOCK(0x2,"没有设置锁屏密码"),
            /**
             * 指纹库是空的，没有录入指纹
             */
            TYPE_FINGERPRINT_BANK_EMPTY(0x3,"指纹库是空的，没有录入指纹"),
            /**
             * 指纹多次验证失败，需要输入手势图案解锁
             */
            TYPE_MULTIPLE_FINGERPRINT_VERIFICATION_FAILED(0x4,"指纹多次验证失败，需要输入手势图案解锁"),
            /**
             * 指纹识别成功
             */
            TYPE_FINGERPRINT_VERIFICATION_SUCCESS(0x5,"指纹识别成功"),
            /**
             * 指纹识别失败
             */
            TYPE_FINGERPRINT_VERIFICATION_FAILED(0x6,"指纹识别失败");

            private int    code;
            private String desc;

            FingerprintAuthentType(int code,String desc) {
                this.code=code;
                this.desc=desc;
            }

            public int getCode() {
                return code;
            }

            public String getDesc() {
                return desc;
            }
        }

        void post(FingerprintAuthentType type);
    }

    public static boolean hasClassFingerprintManager() {
        boolean result=false;
        try {
            Class aClass=Class.forName("android.hardware.fingerprint.FingerprintManager");
            Log.e("NET-",aClass.getName());
            //            Class aClass2=Class.forName("android.hardware.fingerprint.FingerprintManager"
            //                                        +".AuthenticationCallback");
            //            Log.e("NET-",aClass2.getName());
            result=true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e("NET-","无此类>>"+e.getMessage());
        }
        return result;
    }
}
