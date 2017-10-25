package org.sogrey.sogreyframe.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * 用法：
 * <pre>
 *     final SuperRecycler recycler = new SuperRecycler(context);
 * recycler.setOnBottomCallBack(new OnBottomCallBack() {
 * @ Override
 * public void onBottom() {
 * Snackbar.make(recycler, "到底了，没有了。", Snackbar.LENGTH_LONG).show();
 * }
 * });
 * </pre>
 * Created by Sogrey on 2017/1/10.
 */

public class SuperRecycler extends RecyclerView {
    private OnBottomCallBack mOnBottomCallBack;

    public SuperRecycler(Context context) {
        this(context, null);

    }

    public SuperRecycler(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SuperRecycler(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        if (isSlideToBottom() && mOnBottomCallBack != null) {
            mOnBottomCallBack.onBottom();
        }
    }

    private boolean isSlideToBottom() {
        return this != null && this.computeVerticalScrollExtent() + this.computeVerticalScrollOffset() >= this.computeVerticalScrollRange();
    }

    public void setOnBottomCallBack(OnBottomCallBack callBack) {
        mOnBottomCallBack = callBack;
    }

    public interface OnBottomCallBack {
        void onBottom();
    }
}
