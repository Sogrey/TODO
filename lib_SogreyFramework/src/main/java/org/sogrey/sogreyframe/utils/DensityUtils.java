package org.sogrey.sogreyframe.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * 常用单位转换的辅助类
 * 
 * @author Sogrey
 * @date 2015年7月23日
 */
public class DensityUtils {
	private DensityUtils() {
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}
	/**
	 * 像素密度（单位像素点数/160）
	 * @author Sogrey
	 * @date 2016-1-11上午9:52:43
	 * @param context
	 * @return
	 */
	public static float getDensity(Context context) {
		return context.getResources().getDisplayMetrics().density;
	}
	/**
	 * 单位像素点数（160n）
	 * @author Sogrey
	 * @date 2016-1-11上午9:52:56
	 * @param context
	 * @return
	 */
	public static float getDensityDpi(Context context) {
		return context.getResources().getDisplayMetrics().densityDpi;
	}
	/**
	 * dp转px
	 * 
	 * @param context
	 * @param dpVal
	 * @return
	 */
	public static int dp2px(Context context,float dpVal) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
											   dpVal,context.getResources().getDisplayMetrics());
	}

	/**
	 * sp转px
	 * 
	 * @param context
	 * @param spVal
	 * @return
	 */
	public static int sp2px(Context context,float spVal) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
											   spVal,context.getResources().getDisplayMetrics());
	}

	/**
	 * px转dp
	 * 
	 * @param context
	 * @param pxVal
	 * @return
	 */
	public static float px2dp(Context context,float pxVal) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (pxVal / scale);
	}

	/**
	 * px转sp
	 * 
	 * @param context
	 * @param pxVal
	 * @return
	 */
	public static float px2sp(Context context,float pxVal) {
		return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
	}

	/**
	 * 获取屏幕宽度
	 * @param context
     * @return
     */
	public static int getScreenWidth(Context context) {
		DisplayMetrics dm= new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	/**
	 * 获取屏幕高度
	 * @param context
     * @return
     */
	public static int getScreenHeight(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}

}
