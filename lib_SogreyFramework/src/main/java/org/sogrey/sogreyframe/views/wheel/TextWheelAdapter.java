package org.sogrey.sogreyframe.views.wheel;

import android.content.Context;

import java.util.List;

/**
 * Created by Sogrey on 2016/11/17.
 */

public class TextWheelAdapter extends AbstractWheelTextAdapter {

    private List<ItemIdName> mItems;

    public TextWheelAdapter(Context context,List<ItemIdName> items) {
        super(context);
        this.mItems=items;
    }

    /**
     * 获取当前滚轮上文字
     * @param index the item index
     * @return 返回选择的文字描述
     */
    @Override
    protected CharSequence getItemText(int index) {
        return (this.mItems!=null&&index>=0&&index<getItemsCount()) ?
               this.mItems.get(index).name : null;
    }

    /**
     * 获取当前滚轮上文字对应的ID
     * @param index the item index
     * @return 返回选择的文字对应的ID
     */
    protected CharSequence getItemId(int index) {
        return (this.mItems!=null&&index>=0&&index<getItemsCount()) ?
               this.mItems.get(index).id : null;
    }

    public void setSelection(int position){

    }

    @Override
    public int getItemsCount() {
        return this.mItems!=null?this.mItems.size():0;
    }
}
