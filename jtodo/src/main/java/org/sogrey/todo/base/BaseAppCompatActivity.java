package org.sogrey.todo.base;

import android.os.Bundle;

import org.sogrey.sogreyframe.ui.base.BaseActivity;

import butterknife.ButterKnife;

/**
 *
 * Created by Sogrey on 2017/9/30.
 */

public abstract class BaseAppCompatActivity extends BaseActivity {
    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }
}
