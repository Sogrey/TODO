/**
 * @author Sogrey
 * @date 2015-10-27上午10:40:37
 */
package org.sogrey.sogreyframe.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author Sogrey
 * @date 2015-10-27上午10:40:37
 */
public class DrawUtils {
	/**
	 * textview 设置drawable
	 * 
	 * @param ctx
	 * @param v
	 * @param resId
	 * @param w
	 *            宽
	 * @param h
	 *            高
	 * @param type
	 *            0左1右2下3上
	 * @return
	 */
	public static void setViewDrawable(Context ctx,View v,int resId,int w,
									   int h,int type) {

		try {
			Drawable drawable= ctx.getResources().getDrawable(resId);
			drawable.setBounds(0, 0, w, h);
			switch (type) {
			// 左
			case 0: {
				if (v instanceof TextView) {
					((TextView) v).setCompoundDrawables(drawable,null,null,
														null);
				} else if (v instanceof EditText) {
					((EditText) v).setCompoundDrawables(drawable,null,null,
														null);
				}
			}
				break;
			// 右
			case 1: {
				if (v instanceof TextView) {
					((TextView) v).setCompoundDrawables(null,null,drawable,
														null);
				} else if (v instanceof EditText) {
					((EditText) v).setCompoundDrawables(null,null,drawable,
														null);
				}
			}
				break;
			// 下
			case 2: {
				if (v instanceof TextView) {
					((TextView) v).setCompoundDrawables(null,null,null,
														drawable);
				} else if (v instanceof EditText) {
					((EditText) v).setCompoundDrawables(null,null,null,
														drawable);
				}
			}
				break;
			// 上
			case 3: {
				if (v instanceof TextView) {
					((TextView) v).setCompoundDrawables(null,drawable,null,
														null);
				} else if (v instanceof EditText) {
					((EditText) v).setCompoundDrawables(null,drawable,null,
														null);
				}
			}
				break;

			default:
				break;
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
