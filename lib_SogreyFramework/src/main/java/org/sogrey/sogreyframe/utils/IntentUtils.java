package org.sogrey.sogreyframe.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

/**
 * Created by Sogrey on 2016/9/21.
 */
public class IntentUtils {

    //VPN设置
    private static final String ACTION_VPN_SETTINGS="android.net.vpn.SETTINGS";
    private static final int REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS=1000;
    //前往设置VPN
    public static void GoToVPNSetting(Context context) {
        Intent intent=new Intent(ACTION_VPN_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        if (intent.resolveActivity(context.getPackageManager())!=null) {
            context.startActivity(intent);
        }
    }

    /**
     * 跳转到手势密码校验界面
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void jumpToGesturePassCheck(Context context) {
        KeyguardManager keyguardManager=
                (KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE);
        Intent intent=
                keyguardManager.createConfirmDeviceCredentialIntent("finger","指纹识别");
        ((Activity)context).startActivityForResult(intent,REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS);
    }

    /**
     * 真退出应用
     * @param context
     */
    public static void exitProgrames(Context context) {
        Intent startMain=new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(startMain);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 假退出
     * @param context
     */
    public void exitProgrames2Home(Context context) {
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(i);
        ((Activity)context).finish();
    }
}
