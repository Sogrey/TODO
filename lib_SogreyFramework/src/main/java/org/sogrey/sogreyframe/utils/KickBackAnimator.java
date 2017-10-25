/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.sogreyframe.utils;

import android.animation.TypeEvaluator;

public class KickBackAnimator implements TypeEvaluator<Float> {

    private final float s=1.70158f;
    float mDuration=0f;

    public void setDuration(float duration) {
        mDuration=duration;
    }

    public Float evaluate(float fraction,Float startValue,Float endValue) {
        float t     =mDuration*fraction;
        float b     =startValue.floatValue();
        float c     =endValue.floatValue()-startValue.floatValue();
        float d     =mDuration;
        float result=calculate(t,b,c,d);
        return result;
    }

    public Float calculate(float t,float b,float c,float d) {
        return c*((t=t/d-1)*t*((s+1)*t+s)+1)+b;
    }
}
