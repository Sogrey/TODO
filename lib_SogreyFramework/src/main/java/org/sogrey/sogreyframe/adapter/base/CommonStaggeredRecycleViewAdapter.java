package org.sogrey.sogreyframe.adapter.base;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * 瀑布流適配器
 * Created by Sogrey on 2016/4/1.
 */
public abstract  class CommonStaggeredRecycleViewAdapter<T> extends CommonRecycleViewAdapter {

    private List<Integer> mHeights;

    public CommonStaggeredRecycleViewAdapter(Context context,List<T> data,int layoutId ) {
        super(context,data, layoutId);
        mHeights = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            mHeights.add((int) (100+Math.random()*300));
        }
    }

    @Override
    public void onBindViewHolder(CommonRecycleViewHolder holder, int position) {
        initEvent(holder, position);
    }
}
