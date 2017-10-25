/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sogrey.sogreyframe.utils;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

/**
 * 名称：AbToastUtil.java 描述：Toast工具
 *
 * @author Sogrey
 */

public class ToastUtil {

    /** 显示Toast. */
    public static final int     SHOW_TOAST =0;
    /** 上下文 */
    private static      Context mContext   =null;
    /**
     * 主要Handler类，在线程中可用 what值.提示文本信息
     */
    private static      Handler baseHandler=new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_TOAST:
                    showToastBottom(mContext,msg.getData().getString("TEXT"));
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 描述：在线程中提示文本信息
     *
     * @param resId 要提示的字符串资源ID，消息what值为0,
     */
    public static void showToastInThread(Context context,int resId) {
        mContext=context;
        Message msg   =baseHandler.obtainMessage(SHOW_TOAST);
        Bundle  bundle=new Bundle();
        bundle.putString("TEXT",context.getResources().getString(resId));
        msg.setData(bundle);
        baseHandler.sendMessage(msg);
    }

    /**
     * 描述：在线程中提示文本信息
     *
     * @param context 上下文
     * @param text    消息文本
     */
    public static void showToastInThread(Context context,String text) {
        mContext=context;
        Message msg   =baseHandler.obtainMessage(SHOW_TOAST);
        Bundle  bundle=new Bundle();
        bundle.putString("TEXT",text);
        msg.setData(bundle);
        baseHandler.sendMessage(msg);
    }

    /**
     * 单例模式（用于全局使用）
     */
    private static ToastUtil mToastUtil;

    private ToastUtil(Context context) {
        this.mContext=context;
    }

    public static ToastUtil getSingleton(Context context) {
        if (mToastUtil==null) {
            return mToastUtil=new ToastUtil(context);
        }
        return mToastUtil;
    }
    public void showToast(String text) {
        showToast(this.mContext,text);
    }

    public void showToast(int resId) {
        showToast(this.mContext,resId);
    }

    public void showToast(String text,int gravity) {
        showToast(this.mContext,text,gravity);
    }

    public void showToast(int resId,int gravity) {
        showToast(this.mContext,resId,gravity);
    }

    /**
     * 默认显示toast-Center
     *
     * @param context 上下文
     * @param text    要显示的提示文本
     *
     * @author Sogrey
     * @date 2015年4月22日
     */
    public static void showToast(Context context,String text) {
        showToastCenter(context,text);
    }

    /**
     * 默认显示toast-Center
     *
     * @param context 上下文
     * @param resId   要显示提示文本资源Id
     *
     * @author Sogrey
     * @date 2015年4月22日
     */
    public static void showToast(Context context,int resId) {
        showToastCenter(context,resId);
    }

    /**
     *  默认显示toast-Center
     * @param context 上下文
     * @param v 自定义布局
     */
    public static void showToast(Context context,View v) {
        showToastCenter(context,v);
    }

    /**
     * 显示toast-自定义位置
     *
     * @param context
     * @param text    要显示的提示文本
     * @param gravity 要显示的位置
     *
     * @author Sogrey
     * @date 2015年4月22日
     */
    public static void showToast(Context context,String text,int gravity) {
        showToastAnyWhere(context,text,null,gravity);
    }

    /**
     * 显示toast-自定义位置
     * @param context 上下文
     * @param v 自定义布局
     * @param gravity 位置
     */
    public static void showToast(Context context,View v,int gravity) {
        showToastAnyWhere(context,null,v,gravity);
    }

    /**
     * 显示toast-自定义位置
     *
     * @param context
     * @param resId   要显示提示文本资源Id
     * @param gravity 要显示的位置
     *
     * @author Sogrey
     * @date 2015年4月22日
     */
    public static void showToast(Context context,int resId,int gravity) {
        String text=context.getResources().getString(resId);
        showToastAnyWhere(context,text,null,gravity);
    }


    /**
     * 描述：Toast提示文本.
     *
     * @param text 文本
     */
    public static void showToastCenter(Context context,String text) {
        showToast(context,text,Gravity.CENTER);
    }

    /**
     * 描述：Toast提示文本.
     * @param context 上下文
     * @param v 自定义布局
     */
    public static void showToastCenter(Context context,View v) {
        showToast(context,v,Gravity.CENTER);
    }

    /**
     * 描述：Toast提示文本.
     *
     * @param text 文本
     */
    public static void showToastBottom(Context context,String text) {
        showToast(context,text,Gravity.BOTTOM);
    }

    /**
     * 描述：Toast提示文本.
     *
     * @param resId 文本的资源ID
     */
    public static void showToastCenter(Context context,int resId) {
        showToast(context,resId,Gravity.CENTER);
    }

    /**
     * 描述：Toast提示文本.
     *
     * @param resId 文本的资源ID
     */
    public static void showToastBottom(Context context,int resId) {
        showToast(context,resId,Gravity.BOTTOM);
    }

    /**
     * 描述：Toast提示文本.
     * @param context 上下文
     * @param v 自定义布局
     */
    public static void showToastBottom(Context context,View v) {
        showToast(context,v,Gravity.BOTTOM);
    }


    /**
     * 描述：Toast提示文本.
     *
     * @param text    文本
     * @param view    自定义布局
     * @param gravity 要显示的位置
     */
    private static void showToastAnyWhere(
            Context context,CharSequence text,View view,
            int gravity
    ) {
        Toast toast=Toast.makeText(context,text,Toast.LENGTH_SHORT);
        if (!TextUtils.isEmpty(text)) {
            toast.setText(text);
            toast.setGravity(gravity,0,0);
            toast.show();
        } else if (view!=null) {
            toast.setView(view);
            toast.setGravity(gravity,0,0);
            toast.show();
        }
    }
}
