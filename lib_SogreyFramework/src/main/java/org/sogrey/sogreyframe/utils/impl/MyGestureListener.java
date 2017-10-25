package org.sogrey.sogreyframe.utils.impl;

import android.content.Context;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

/**
 * Created by Sogrey on 2016/8/31.
 */
public class MyGestureListener extends SimpleOnGestureListener {
    private String TAG=this.getClass().getSimpleName();
    private Context mContext;


    public MyGestureListener(Context context) {
        mContext = context;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        //        Log.e(TAG, "DOWN X:"+e.getX()+" Y:"+e.getY());
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        //        Log.e(TAG, "SHOW X:"+e.getX()+" Y:"+e.getY());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        //        Log.e(TAG, "SINGLE UP X:"+e.getX()+" Y:"+e.getY());
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        //        Log.e(TAG, "SCROLL e1.X:"+e1.getX()+" e1.Y:"+e1.getY()+" e2.X:"+e2.getX()+" e2.Y:"+e2.getY());

        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        //        Log.e(TAG, "LONG X:"+e.getX()+" Y:"+e.getY());
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(e2.getX()-e1.getX() > 100){
        }else if(e2.getX()-e1.getX() < -100){
        }
        //        Log.e(TAG, "FLING e1.X:"+e1.getX()+" e1.Y:"+e1.getY()+" e2.X:"+e2.getX()+" e2.Y:"+e2.getY());
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        //        Log.e(TAG, "DOUBLE X:"+e.getX()+" Y:"+e.getY());
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        //        Log.e(TAG, "DOUBLE EVENT X:"+e.getX()+" Y:"+e.getY());
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        //        Log.e(TAG, "SINGLE CONF X:"+e.getX()+" Y:"+e.getY());
        return false;
    }
}