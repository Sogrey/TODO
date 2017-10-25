/**
 * @author Sogrey
 * @date 2015-10-27下午2:04:21
 */
package org.sogrey.sogreyframe.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;

/**
 * @author Sogrey
 * @date 2015-10-27下午2:04:21
 */
public class Utility {

    /**
     * 动态计算ListView高度
     *
     * @author Sogrey
     * @date 2015-10-27下午2:04:21
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter=listView.getAdapter();
        if (listAdapter==null) {
            // pre-condition
            return;
        }

        int totalHeight=0;
        for (int i=0;i<listAdapter.getCount();i++) {
            View listItem=listAdapter.getView(i,null,listView);
            listItem.measure(0,0);
            totalHeight+=listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params=listView.getLayoutParams();
        params.height=totalHeight
                      +(listView.getDividerHeight()*(listAdapter.getCount()-1));
        listView.setLayoutParams(params);
    }

    /**
     * 通过文件名获取资源id 例子：getResId("icon", R.drawable.class);
     *
     * @param variableName
     * @param c
     *
     * @return
     */
    public static int getResId(String variableName,Class<?> c) {
        try {
            Field idField=c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 检查vpn是否打开
     *
     * @return
     */
    public static boolean isVpnUsed() {
        try {
            Enumeration<NetworkInterface> niList=NetworkInterface.getNetworkInterfaces();
            if (niList!=null) {
                for (NetworkInterface intf : Collections.list(niList)) {
                    if (!intf.isUp()||intf.getInterfaceAddresses().size()==0) {
                        continue;
                    }
                    Log.d("Utility.isVpnUsed()","isVpnUsed() NetworkInterface Name: "+intf
                            .getName());
                    if ("tun0".equals(intf.getName())||"ppp0".equals(intf.getName())) {
                        return true; // The VPN is up
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * textview 设置drawable
     *
     * @param ctx
     * @param v
     * @param resId
     * @param w
     *         宽
     * @param h
     *         高
     * @param type
     *         0左1右
     *
     * @return
     */
    public static void setViewDrawable(
            Context ctx,View v,int resId,int w,
            int h,int type
    ) {

        try {
            Drawable drawable=null;
            if (resId>0) {drawable=ctx.getResources().getDrawable(resId);  drawable.setBounds(0,0,w,h);}

            switch (type) {
                // 左
                case 0: {
                    if (v instanceof TextView) {
                        ((TextView)v).setCompoundDrawables(drawable,null,null,
                                                           null
                        );
                    } else if (v instanceof EditText) {
                        ((EditText)v).setCompoundDrawables(drawable,null,null,
                                                           null
                        );
                    }
                }
                break;
                // 右
                case 1: {
                    if (v instanceof TextView) {
                        ((TextView)v).setCompoundDrawables(null,null,drawable,
                                                           null
                        );
                    } else if (v instanceof EditText) {
                        ((EditText)v).setCompoundDrawables(null,null,drawable,
                                                           null
                        );
                    }
                }
                break;

                default:
                    break;
            }

        } catch (Exception e) {
        }
    }

    /**
     * 判断是否为平板 true:平板，false:手机，尺寸小于6英寸
     *
     * @return
     */
    public static boolean isPad(Context context) {
        WindowManager wm     = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display       display= wm.getDefaultDisplay();
        // 屏幕宽度
        float screenWidth = display.getWidth();
        // 屏幕高度
        float          screenHeight= display.getHeight();
        DisplayMetrics dm          = new DisplayMetrics();
        display.getMetrics(dm);
        double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
        double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
        // 屏幕尺寸
        double screenInches = Math.sqrt(x + y);
        // 大于6尺寸则为Pad
        if (screenInches >= 6.0) {
            return true;
        }
        return false;
    }
}
