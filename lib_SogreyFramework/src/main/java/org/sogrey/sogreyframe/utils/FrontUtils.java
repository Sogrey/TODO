/**
 * @author Sogrey
 * @date 2015-11-27上午10:44:04
 */
package org.sogrey.sogreyframe.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author Sogrey
 * @date 2015-11-27上午10:44:04
 */
public class FrontUtils {

	// android字体大小根据分辨率自动调整

	// 手机设备太多，分辨率也不一样，看到网上大部分的适应字体的方法是定义values320×480或value-hdpi方式去处理。
	// 采用第一种的就惨了，很多设备的分辨率是不一样的，难道要每种都定义吗？
	// 采用第二种的在平板电脑里没有效果。

	// 最后还是代码的方式方便快捷。。。

	public static void changeViewSize(Context mcontext,int viewGroupId,
									  int resTitleId,int wpx,int hpx) {
		ViewGroup viewGroup= (ViewGroup) LayoutInflater.from(mcontext).inflate(viewGroupId,null);
		changeViewSize(viewGroup, resTitleId, wpx, hpx);
	}
	// 遍历设置字体
	public static void changeViewSize(ViewGroup viewGroup,int resTitleId,
									  int screenWidth,int screenHeight) {// 传入Activity顶层Layout,屏幕宽,屏幕高
		int adjustFontSize = adjustFontSize(screenWidth, screenHeight);
		for (int i = 0; i < viewGroup.getChildCount(); i++) {
			View v= viewGroup.getChildAt(i);
			if (v instanceof ViewGroup) {
				changeViewSize((ViewGroup) v,resTitleId,screenWidth,
							   screenHeight);
			} else if (v instanceof Button) {// 按钮加大这个一定要放在TextView上面，因为Button也继承了TextView
				((Button) v).setTextSize(adjustFontSize+2);
			} else if (v instanceof TextView) {
				if (v.getId() == resTitleId) {// 顶部标题
					((TextView) v).setTextSize(adjustFontSize+4);
				} else {
					((TextView) v).setTextSize(adjustFontSize);
				}
			}
		}
	}

	// 获取字体大小
	public static int adjustFontSize(int screenWidth, int screenHeight) {
		screenWidth = screenWidth > screenHeight ? screenWidth : screenHeight;
		/**
		 * 1. 在视图的 onsizechanged里获取视图宽度，一般情况下默认宽度是320，所以计算一个缩放比率 rate = (float)
		 * w/320 w是实际宽度 2.然后在设置字体尺寸时 paint.setTextSize((int)(8*rate));
		 * 8是在分辨率宽为320 下需要设置的字体大小 实际字体大小 = 默认字体大小 x rate
		 */
		int rate = (int) (5 * (float) screenWidth / 320); // 我自己测试这个倍数比较适合，当然你可以测试后再修改
		return rate < 15 ? 15 : rate; // 字体太小也不好看的
	}
	

	// 最后在Avtivity的oncreate完后调用一下changeViewSize就行了。。。文字大了那么它对应的背景也就跟着大，所以建议控件的背景图片用9宫格类型的图片，看起来舒服。
	// 另外附加，如果你开发的应用想在平板电脑上浏览无碍请在AndroidManifest.xml文件中的manifest节点（DTD建议放在application节点上面）里加入：

	// <supports-screens
	// android:anyDensity="true"
	// android:largeScreens="true"
	// android:normalScreens="true"
	// android:smallScreens="true"
	// android:resizeable="true"/>





}
