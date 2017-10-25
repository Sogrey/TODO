package org.sogrey.sogreyframe.utils;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 打开或关闭软键盘
 * 
 * @author Sogrey
 * @date 2015年7月23日
 */
public class KeyBoardUtils {
	/**
	 * 打卡软键盘
	 * 
	 * @param mEditText
	 *            输入框
	 * @param mContext
	 *            上下文
	 */
	public static void openKeybord(EditText mEditText,Context mContext) {
//		if (!mEditText.hasFocus()&&!mEditText.isFocused()) {
		mEditText.requestFocus();
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(mEditText,InputMethodManager.RESULT_SHOWN);
		imm.toggleSoftInput(
				InputMethodManager.SHOW_FORCED,
				InputMethodManager.HIDE_IMPLICIT_ONLY);
//		}
	}

	/**
	 * 关闭软键盘
	 * 
	 * @param mEditText
	 *            输入框
	 * @param mContext
	 *            上下文
	 */
	public static void closeKeybord(EditText mEditText,Context mContext) {
		mEditText.clearFocus();
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
	}

	/**
	 * 自动隐藏软键盘-点击空白处自动隐藏<br>
	 * 完美适用activity与fragment<br>
	 * for example:<br>
	 * 获取点击事件
	 * 
	 * <pre>
	 * &#064;Override
	 * public boolean dispatchTouchEvent(MotionEvent ev) {
	 * 	if (ev.getAction() == MotionEvent.ACTION_DOWN) {
	 * 		KeyBoardUtils.hideInputAuto(context, ev);// context:Activity or Fragment
	 * 	}
	 * 	return super.dispatchTouchEvent(ev);
	 * }
	 * </pre>
	 * 
	 * @author Sogrey
	 * @date 2015年6月19日
	 * @param context
	 *            上下文对象
	 * @param ev
	 *            MotionEvent 点击事件的MotionEvent
	 */
	public static void hideInputAuto(Context context,MotionEvent ev) {
		// 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
		View view= ((Activity) context).getCurrentFocus();
		if (isHideInput(view, ev)) {
			HideSoftInput(context, view.getWindowToken());
		}
	}

	/**
	 * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
	 * 
	 * @param v
	 * @param event
	 * @return
	 */
	public static boolean isHideInput(View v,MotionEvent event) {
		if (v != null && (v instanceof EditText)) {
			int[] l = { 0, 0 };
			v.getLocationInWindow(l);
			int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
					+ v.getWidth();
			if (event.getX() > left && event.getX() < right
					&& event.getY() > top && event.getY() < bottom) {
				// 点击EditText的事件，忽略它。
				return false;
			} else {
				return true;
			}
		}
		// 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
		return false;
	}

	// 隐藏软键盘
	private static void HideSoftInput(Context context,IBinder token) {
		if (token != null) {
			InputMethodManager manager = (InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			manager.hideSoftInputFromWindow(token,
											InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

}
