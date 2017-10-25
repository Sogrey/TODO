package org.sogrey.sogreyframe.utils;

import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 用TextView显示图片
 * http://blog.csdn.net/qibin0506/article/details/48675839
 *
 * Created by Sogrey on 2016/9/28.
 */
public class CssFontHelper {
    public static final String FONTS_DIR = "fonts/";
    public static final String DEF_FONT = FONTS_DIR + "yourfont.ttf";

    public static final void injectFont(View rootView) {
        injectFont(rootView,Typeface.createFromAsset(rootView.getContext()
                                                             .getAssets(),DEF_FONT));
    }

    public static final void injectFont(View rootView, Typeface tf) {
        if(rootView instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) rootView;
            int count = group.getChildCount();
            for(int i=0;i<count;i++) {
                injectFont(group.getChildAt(i), tf);
            }
        }else if(rootView instanceof TextView) {
            ((TextView) rootView).setTypeface(tf);
        }
    }
}
