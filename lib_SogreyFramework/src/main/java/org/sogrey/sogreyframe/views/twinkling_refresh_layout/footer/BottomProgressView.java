package org.sogrey.sogreyframe.views.twinkling_refresh_layout.footer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;

import org.sogrey.sogreyframe.R;
import org.sogrey.sogreyframe.views.twinkling_refresh_layout.IBottomView;


/**
 * Created by lcodecore on 2016/10/1.
 */

public class BottomProgressView extends ProgressView implements IBottomView {


    public BottomProgressView(Context context) {
        this(context,null);
    }

    public BottomProgressView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BottomProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        setLayoutParams(params);
        setIndicatorColor(getResources().getColor(R.color.Orange));
        setIndicatorId(BallPulse);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onPullingUp(float fraction, float maxHeadHeight, float headHeight) {
       // setVisibility(GONE);
    }

    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {
        //setVisibility(VISIBLE);
    }

    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {
       // setVisibility(GONE);
    }

    @Override
    public void onFinish() {

    }
}
