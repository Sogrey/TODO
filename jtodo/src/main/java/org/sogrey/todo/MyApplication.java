package org.sogrey.todo;

import org.sogrey.sogreyframe.BaseApplication;

/**
 * Created by Sogrey on 2017/9/30.
 */

public class MyApplication extends BaseApplication {
    @Override
    protected boolean isLog() {
        return true;
    }

    @Override
    protected boolean isDebugLog() {
        return true;
    }
}
