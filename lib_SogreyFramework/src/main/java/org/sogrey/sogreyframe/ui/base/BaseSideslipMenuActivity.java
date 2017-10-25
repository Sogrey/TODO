package org.sogrey.sogreyframe.ui.base;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import org.sogrey.sogreyframe.ui.base.sideslipmenu.SideslipMenu;
import org.sogrey.sogreyframe.ui.base.sideslipmenu.SideslipMenu.DisableDirection;
import org.sogrey.sogreyframe.ui.base.sideslipmenu.SideslipMenu.OnMenuListener;
import org.sogrey.sogreyframe.ui.base.sideslipmenu.SideslipMenuItem;

import java.util.List;

/**
 * 带有策划菜单的activity基类
 *
 * @author Sogrey
 */
public abstract class BaseSideslipMenuActivity extends BaseActivity {

    protected SideslipMenu mSideslipMenu;
    public boolean mIsMenuClosed=true;

    @Override
    protected int getLayoutResId() {
        return getMainContentLayout();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setSideslipMenu();
        doOtherThings();
    }
    /**
     * 设置侧滑菜单
     */
    private void setSideslipMenu() {
        // attach to current activity;
        mSideslipMenu=new SideslipMenu(this);
        mSideslipMenu.setBackground(setSideslipMenuBackground());
        mSideslipMenu.attachToActivity(this);
        mSideslipMenu.setMenuListener(_listener);
        // valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
        mSideslipMenu.setScaleValue(0.6f);
        // 禁止使用右侧菜单
        mSideslipMenu.setDirectionDisable(getDirectionDisable());

        mSideslipMenu.addMenuItems(getLeftMenuItems(),DisableDirection.DIRECTION_LEFT);
        mSideslipMenu.addMenuItems(getRightMenuItems(),DisableDirection.DIRECTION_RIGHT);

        mSideslipMenu.addMenuInfo(addMenuInfo());
        mSideslipMenu.addSettingLayout(addSettingLayout());

        mSideslipMenu.clearIgnoredViewList();
    }

    /**
     * 设置主布局文件ID
     *
     * @return 布局ID
     */
    protected abstract int getMainContentLayout();

    /**
     * 设置侧滑菜单的背景图
     *
     * @return
     */
    protected abstract int setSideslipMenuBackground();

    /**
     * 设置禁止哪个方向的菜单不显示
     *
     * @return
     */
    protected abstract DisableDirection getDirectionDisable();

    /**
     * 左侧侧滑菜单项列表
     *
     * @return
     */
    protected abstract List<SideslipMenuItem> getLeftMenuItems();

    /**
     * 右侧侧滑菜单项列表
     *
     * @return
     */
    protected abstract List<SideslipMenuItem> getRightMenuItems();

    /**
     * 添加左侧侧滑菜单头部
     *
     * @return
     */
    protected abstract View addMenuInfo();

    /**
     * 添加左侧侧滑菜单尾部
     */
    protected abstract View addSettingLayout();

    /**
     * 做后续事情
     */
    protected abstract void doOtherThings();

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mSideslipMenu.dispatchTouchEvent(ev);
    }

    public SideslipMenu getSideslipMenu() {
        return mSideslipMenu;
    }

    /**
     * 菜单点击事件
     */
    private OnMenuListener _listener=new OnMenuListener() {
        @Override
        public void openMenu() {
            mIsMenuClosed=false;
            menuOpened();
        }

        @Override
        public void closeMenu() {
            mIsMenuClosed=true;
            menuClosed();
        }
    };

    /**
     * 菜单打开
     */
    protected abstract void menuOpened();

    /**
     * 菜单关闭
     */
    protected abstract void menuClosed();

    public boolean onKeyDown(int keyCode,KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK) {
            // 判断菜单是否关闭
            if (!mIsMenuClosed) {
                mSideslipMenu.closeMenu();
                return true;
            }
        }
        return super.onKeyDown(keyCode,event);
    }
}
