package org.sogrey.sogreyframe.utils;

import android.content.Context;
import android.graphics.Paint;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <ul>
 * <li>给TextView设置部分大小</li>
 * <li>给TextView设置部分颜色</li>
 * <li>给TextView设置下划线</li>
 * <li>半角转换为全角</li>
 * <li>去除特殊字符或将所有中文标号替换为英文标号</li></ul>
 * Created by Sogrey on 2016/9/28.
 */
public class TextViewUtil {

    //给TextView设置部分大小
    public static void setPartialSize(TextView tv,int start,int end,int textSize) {
        String    s        =tv.getText().toString();
        Spannable spannable=new SpannableString(s);
        spannable.setSpan(new AbsoluteSizeSpan(textSize),start,end,Spannable
                .SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(spannable);
    }

    //给TextView设置部分颜色
    public static void setPartialColor(TextView tv,int start,int end,int textColor) {
        String    s        =tv.getText().toString();
        Spannable spannable=new SpannableString(s);
        spannable.setSpan(new ForegroundColorSpan(textColor),start,end,Spannable
                .SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(spannable);
    }

    //给TextView设置下划线
    public static void setUnderLine(TextView tv) {
        if (tv.getText()!=null) {
            String          udata  =tv.getText().toString();
            SpannableString content=new SpannableString(udata);
            content.setSpan(new UnderlineSpan(),0,udata.length(),0);
            tv.setText(content);
            content.setSpan(new UnderlineSpan(),0,udata.length(),0);
        } else {
            tv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        }
    }

    //取消TextView的置下划线
    public static void clearUnderLine(TextView tv) {
        tv.getPaint().setFlags(0);
    }

    //半角转换为全角
    public static String ToDBC(String input) {
        char[] c=input.toCharArray();
        for (int i=0;i<c.length;i++) {
            if (c[i]==12288) {
                c[i]=(char)32;
                continue;
            }
            if (c[i]>65280&&c[i]<65375)
                c[i]=(char)(c[i]-65248);
        }
        return new String(c);
    }

    //去除特殊字符或将所有中文标号替换为英文标号
    public static String replaceCharacter(String str) {
        str=str.replaceAll("【","[").replaceAll("】","]")
               .replaceAll("！","!").replaceAll("：",":").replaceAll("（","(").replaceAll("（",")");
        // 替换中文标号
        String  regEx="[『』]"; // 清除掉特殊字符
        Pattern p    =Pattern.compile(regEx);
        Matcher m    =p.matcher(str);
        return m.replaceAll("").trim();
    }


    /**
     * 获取TextView显示的字符串宽度
     * TextPaint textPaint = textView.getPaint();
     * float textPaintWidth = textPaint.measureText(text);
     * 使用textView的TextPaint调用measureText方法得到的宽度才是真正的宽度。
     *
     * @param context
     * @param text
     * @param textSize
     *
     * @return
     */
    public float getTextWidth(Context context,String text,int textSize) {
        TextPaint paint        =new TextPaint();
        float     scaledDensity=context.getResources().getDisplayMetrics().scaledDensity;
        paint.setTextSize(scaledDensity*textSize);
        return paint.measureText(text);
    }
}
