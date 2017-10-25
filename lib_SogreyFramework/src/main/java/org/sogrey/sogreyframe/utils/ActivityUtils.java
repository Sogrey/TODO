package org.sogrey.sogreyframe.utils;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Sogrey on 2016/12/5.
 */

public class ActivityUtils {

    /**
     * 获取Activity 根布局 另一种：(ViewGroup)activity.getWindow().getDecorView();
     * @param activity
     * @return
     */
    public static View getRootView(Activity activity) {
        return (
                (ViewGroup)activity
                        .findViewById(android.R.id.content)
        ).getChildAt(0);
    }
}
