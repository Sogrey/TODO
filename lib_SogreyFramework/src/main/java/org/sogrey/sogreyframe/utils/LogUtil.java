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
import android.util.Log;

import org.sogrey.sogreyframe.BuildConfig;

import java.util.Calendar;



/**
 * 名称：AbLogUtil.java 描述：日志工具类.
 * 
 * @author Sogrey
 * @date 2015年7月27日
 */
public class LogUtil {

	private static final String  TAG= "LogUtil";
	/** debug开关. */
	public static        boolean D  =true&&BuildConfig.DEBUG;

	/** info开关. */
	public static boolean I = true && BuildConfig.DEBUG;

	/** warn开关. */
	public static boolean W = true && BuildConfig.DEBUG;

	/** error开关. */
	public static boolean E = true && BuildConfig.DEBUG;

	/** 起始执行时间. */
	public static long startLogTimeInMillis = 0;

	/**
	 * debug日志
	 * 
	 * @param tag
	 * @param message
	 */
	public static void d(String tag,String message) {
		if (D)
			Log.d(tag,message);
	}

	/**
	 * debug日志
	 * 
	 * @param message
	 */
	public static void d(String message) {
		d(TAG, message);
	}

	/**
	 * debug日志
	 * 
	 * @param context
	 * @param message
	 */
	public static void d(Context context,String message) {
		String tag= context.getClass().getSimpleName();
		d(tag, message);
	}

	/**
	 * debug日志
	 * 
	 * @param clazz
	 * @param message
	 */
	public static void d(Class<?> clazz,String message) {
		String tag= clazz.getSimpleName();
		d(tag, message);
	}

	/**
	 * info日志
	 * 
	 * @param tag
	 * @param message
	 */
	public static void i(String tag,String message) {
		if (I)
			Log.i(tag,message);
	}

	/**
	 * info日志
	 * 
	 * @param message
	 */
	public static void i(String message) {
		i(TAG, message);
	}

	/**
	 * info日志
	 * 
	 * @param context
	 * @param message
	 */
	public static void i(Context context,String message) {
		String tag= context.getClass().getSimpleName();
		i(tag, message);
	}

	/**
	 * info日志
	 * 
	 * @param clazz
	 * @param message
	 */
	public static void i(Class<?> clazz,String message) {
		String tag= clazz.getSimpleName();
		i(tag, message);
	}

	/**
	 * warn日志
	 * 
	 * @param tag
	 * @param message
	 */
	public static void w(String tag,String message) {
		if (W)
			Log.w(tag,message);
	}

	/**
	 * warn日志
	 * 
	 * @param message
	 */
	public static void w(String message) {
		w(TAG, message);
	}

	/**
	 * warn日志
	 * 
	 * @param context
	 * @param message
	 */
	public static void w(Context context,String message) {
		String tag= context.getClass().getSimpleName();
		w(tag, message);
	}

	/**
	 * warn日志
	 * 
	 * @param clazz
	 * @param message
	 */
	public static void w(Class<?> clazz,String message) {
		String tag= clazz.getSimpleName();
		w(tag, message);
	}

	/**
	 * error日志
	 * 
	 * @param tag
	 * @param message
	 */
	public static void e(String tag,String message) {
		if (E)
			Log.e(tag,message);
	}

	/**
	 * error日志
	 * 
	 * @param message
	 */
	public static void e(String message) {
		e(TAG, message);
	}

	/**
	 * error日志
	 * 
	 * @param context
	 * @param message
	 */
	public static void e(Context context,String message) {
		String tag= context.getClass().getSimpleName();
		e(tag, message);
	}

	/**
	 * error日志
	 * 
	 * @param clazz
	 * @param message
	 */
	public static void e(Class<?> clazz,String message) {
		String tag= clazz.getSimpleName();
		e(tag, message);
	}

	/**
	 * error日志-打印异常信息
	 * 
	 * @author Sogrey
	 * @date 2015年6月19日
	 * @param tag
	 * @param message
	 * @param e
	 */
	public static void e(String tag,String message,Throwable e) {
		if (E)
			Log.e(tag,message,e);
	}

	/**
	 * 描述：记录当前时间毫秒.
	 * 
	 */
	public static void prepareLog(String tag) {
		Calendar current= Calendar.getInstance();
		startLogTimeInMillis = current.getTimeInMillis();
		Log.d(tag,"日志计时开始："+startLogTimeInMillis);
	}

	/**
	 * 描述：记录当前时间毫秒.
	 * 
	 */
	public static void prepareLog(Context context) {
		String tag= context.getClass().getSimpleName();
		prepareLog(tag);
	}

	/**
	 * 描述：记录当前时间毫秒.
	 * 
	 */
	public static void prepareLog(Class<?> clazz) {
		String tag= clazz.getSimpleName();
		prepareLog(tag);
	}

	/**
	 * 描述：打印这次的执行时间毫秒，需要首先调用prepareLog().
	 * 
	 * @param tag
	 *            标记
	 * @param message
	 *            描述
	 * @param printTime
	 *            是否打印时间
	 */
	public static void d(String tag,String message,boolean printTime) {
		Calendar current           = Calendar.getInstance();
		long     endLogTimeInMillis= current.getTimeInMillis();
		Log.d(tag,message+":"+(endLogTimeInMillis-startLogTimeInMillis)
				  +"ms");
	}

	/**
	 * 描述：打印这次的执行时间毫秒，需要首先调用prepareLog().
	 * 
	 * @param context
	 *            标记
	 * @param message
	 *            描述
	 * @param printTime
	 *            是否打印时间
	 */
	public static void d(Context context,String message,boolean printTime) {
		String tag= context.getClass().getSimpleName();
		d(tag, message, printTime);
	}

	/**
	 * 描述：打印这次的执行时间毫秒，需要首先调用prepareLog().
	 * 
	 * @param clazz
	 *            标记
	 * @param message
	 *            描述
	 * @param printTime
	 *            是否打印时间
	 */
	public static void d(Class<?> clazz,String message,boolean printTime) {
		String tag= clazz.getSimpleName();
		d(tag, message, printTime);
	}

	/**
	 * debug日志的开关
	 */
	public static void debug(boolean d) {
		D = d;
	}

	/**
	 * info日志的开关
	 */
	public static void info(boolean i) {
		I = i;
	}

	/**
	 * warn日志的开关
	 */
	public static void warn(boolean w) {
		W = w;
	}

	/**
	 * error日志的开关
	 */
	public static void error(boolean e) {
		E = e;
	}

	/**
	 * 设置日志的开关
	 */
	public static void setVerbose(boolean d, boolean i, boolean w, boolean e) {
		D = d;
		I = i;
		W = w;
		E = e;
	}

	/**
	 * 打开所有日志，默认全打开
	 */
	public static void openAll() {
		D = true;
		I = true;
		W = true;
		E = true;
	}

	/**
	 * 关闭所有日志
	 */
	public static void closeAll() {
		D = false;
		I = false;
		W = false;
		E = false;
	}
}
