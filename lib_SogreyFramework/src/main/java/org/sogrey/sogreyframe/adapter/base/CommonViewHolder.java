/**
 * @author Sogrey
 * @date 2015年5月11日
 */
package org.sogrey.sogreyframe.adapter.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;

import org.sogrey.sogreyframe.utils.GlideUtils;


/**
 * 通用ViewHolder
 *
 * @author Sogrey
 * @date 2015年5月11日
 */
public class CommonViewHolder {

    private SparseArray<View> mView;
    private Context           mContext;
    private int               mLayoutId;
    private int               mPostion;
    private View              mConvertView;

    /**
     * @author Sogrey
     * @date 2015年5月11日
     */

    private CommonViewHolder(
            Context context,ViewGroup parent,int layoutId,
            int postion
    ) {
        this.mContext=context;
        this.mLayoutId=layoutId;
        this.mPostion=postion;

        this.mView=new SparseArray<View>();
        mConvertView=LayoutInflater.from(this.mContext).inflate(
                this.mLayoutId,null);
        mConvertView.setTag(this);
    }

    /**
     * 获取ViewHolder
     *
     * @param context
     *         上下文对象
     * @param convertView
     *         the convertView
     * @param parent
     *         the ViewGroup
     * @param layoutId
     *         the id of the layout
     * @param position
     *         the position of the item
     *
     * @return CommonViewHolder
     *
     * @author Sogrey
     * @date 2015年5月11日
     */
    public static CommonViewHolder get(
            Context context,View convertView,
            ViewGroup parent,int layoutId,int position
    ) {
        if (convertView==null) {
            return new CommonViewHolder(context,parent,layoutId,position);
        } else {
            CommonViewHolder holder=(CommonViewHolder)convertView.getTag();
            holder.mPostion=position;
            return holder;
        }
    }

    /**
     * 通过viewId获取控件对象
     *
     * @param viewId
     *         控件ID
     *
     * @return View
     *
     * @author Sogrey
     * @date 2015年5月11日
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        /* 依据key(viewId)获取View控件 */
        View view=mView.get(viewId);
        if (view==null) {
            view=mConvertView.findViewById(viewId);
			/* 添加到控件队列：key:viewId,value:View */
            mView.put(viewId,view);
        }
        return (T)view;
    }

    /**
     * 获取convertView
     *
     * @return convertView
     *
     * @author Sogrey
     * @date 2015年5月11日
     */
    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 获取当前条目的position
     *
     * @return position
     *
     * @author Sogrey
     * @date 2015年5月11日
     */
    public int getPosition() {
        return mPostion;
    }

    /**
     * 设置Tag
     *
     * @param viewId
     * @param tag
     *
     * @return
     *
     * @author Sogrey
     * @date 2015年8月14日
     */
    public CommonViewHolder setTag(int viewId,Object tag) {
        getView(viewId).setTag(tag);
        return this;
    }

    /**
     * 设置指定key的Tag
     *
     * @param viewId
     * @param key
     * @param tag
     *
     * @return
     *
     * @author Sogrey
     * @date 2015年8月14日
     */
    public CommonViewHolder setTag(int viewId,int key,Object tag) {
        getView(viewId).setTag(key,tag);
        return this;
    }

    /**
     * 获取Tag 没有设置时为null
     *
     * @param viewId
     *
     * @return
     *
     * @author Sogrey
     * @date 2015年8月14日
     */
    public Object getTag(int viewId) {
        return getView(viewId).getTag();
    }

    /**
     * 获取指定key的Tag 没有设置时为null
     *
     * @param viewId
     * @param key
     *
     * @return
     *
     * @author Sogrey
     * @date 2015年8月14日
     */
    public Object getTag(int viewId,int key) {
        return getView(viewId).getTag(key);
    }

    /**
     * 设置可见状态<br>
     *
     * @param viewId
     * @param visibility
     *         {@link View#VISIBLE},{@link View#GONE},{@link View#INVISIBLE}
     *
     * @return
     *
     * @author Sogrey
     * @date 2015年8月14日
     */
    public CommonViewHolder setVisibility(int viewId,int visibility) {
        getView(viewId).setVisibility(visibility);
        return this;
    }

    /**
     * 获取可见状态
     *
     * @param viewId
     *
     * @return {@link View#VISIBLE},{@link View#GONE},{@link View#INVISIBLE}
     *
     * @author Sogrey
     * @date 2015年8月14日
     */
    public int getVisibility(int viewId) {
        return getView(viewId).getVisibility();
    }

    /**
     * 设置是否可点击<br>
     *
     * @param viewId
     * @param clickable
     *
     * @return
     *
     * @author Sogrey
     * @date 2015年8月14日
     */
    public CommonViewHolder setClickable(int viewId,boolean clickable) {
        getView(viewId).setClickable(clickable);
        return this;
    }

    /**
     * 是否可长按
     *
     * @param viewId
     * @param clickable
     *
     * @return
     *
     * @author Sogrey
     * @date 2015年8月14日
     */
    public CommonViewHolder setLongClickable(int viewId,boolean clickable) {
        getView(viewId).setLongClickable(clickable);
        return this;
    }

    /**
     * 设置是否可用
     *
     * @param viewId
     * @param enabled
     *
     * @return
     *
     * @author Sogrey
     * @date 2015年8月14日
     */
    public CommonViewHolder setEnabled(int viewId,boolean enabled) {
        getView(viewId).setEnabled(enabled);
        return this;
    }

    /**
     * 给TextView,EditText,Button设置内容
     *
     * @param viewId
     *         id of the TextView
     * @param content
     *         the content to set
     *
     * @return CommonViewHolder
     *
     * @author Sogrey
     * @date 2015年5月11日
     */
    public CommonViewHolder setText(int viewId,String content) {
        View view=getView(viewId);

        if (view instanceof EditText) {
            ((EditText)view).setText(content);
        } else if (view instanceof Button) {
            ((Button)view).setText(content);
        } else if (view instanceof TextView) {
            ((TextView)view).setText(content);
        }

        return this;
    }

    /**
     * 给TextView,EditText,Button设置字体颜色
     *
     * @param viewId
     *         id of the TextView
     * @param colorId
     *         the colorId to set
     *
     * @return CommonViewHolder
     *
     * @author Sogrey
     * @date 2016年8月26日
     */
    public CommonViewHolder setTextColor(int viewId,int colorId) {
        View view=getView(viewId);

        if (view instanceof EditText) {
            ((EditText)view).setTextColor(mContext.getResources().getColor(colorId));
        } else if (view instanceof Button) {
            ((Button)view).setTextColor(mContext.getResources().getColor(colorId));
        } else if (view instanceof TextView) {
            ((TextView)view).setTextColor(mContext.getResources().getColor(colorId));
        }

        return this;
    }


    /**
     * 为ImageView设置资源-Bitmap
     *
     * @param viewId
     *         id of the ImageView
     * @param bm
     *         the Bitmap to set
     *
     * @return CommonViewHolder
     *
     * @author Sogrey
     * @date 2015年5月11日
     */
    public CommonViewHolder setImg_ImageView(int viewId,Bitmap bm) {
        ((ImageView)getView(viewId)).setImageBitmap(bm);
        return this;
    }

    /**
     * 为ImageView设置资源-Drawable
     *
     * @param viewId
     *         id of the ImageView
     * @param drawable
     *         the Drawable to set
     *
     * @return CommonViewHolder
     *
     * @author Sogrey
     * @date 2015年5月11日
     */
    public CommonViewHolder setImg_ImageView(int viewId,Drawable drawable) {
        ((ImageView)getView(viewId)).setImageDrawable(drawable);
        return this;
    }

    /**
     * 为ImageView设置资源-ResourceId
     *
     * @param viewId
     *         id of the ImageView
     * @param resId
     *         the id of resource to set
     *
     * @return CommonViewHolder
     *
     * @author Sogrey
     * @date 2015年5月11日
     */
    public CommonViewHolder setImg_ImageView(int viewId,int resId) {
        ((ImageView)getView(viewId)).setImageResource(resId);
        return this;
    }

    /**
     * 为ImageView设置资源-Uri
     *
     * @param viewId
     *         id of the ImageView
     * @param uri
     *         the Uri to set
     *
     * @return CommonViewHolder
     *
     * @author Sogrey
     * @date 2015年5月11日
     */
    public CommonViewHolder setImg_ImageView(int viewId,Uri uri) {
        ((ImageView)getView(viewId)).setImageURI(uri);
        return this;
    }

    /**
     * 为ImageView设置资源-Url(网络路径)
     *
     * @param viewId
     *         id of the ImageView
     * @param url
     *         the url of resource to set
     *
     * @return CommonViewHolder
     *
     * @author Sogrey
     * @date 2015年5月11日
     */
    public CommonViewHolder setImg_ImageView(int viewId,String url) {
        //		ImageLoader.getInstance()
        //				.displayImage(url, (ImageView) getView(viewId));// 需要
        // universal-image-loader jar
        GlideUtils.setImage(mContext,(ImageView)getView(viewId),url);// 需要 Glide jar
        return this;
    }

    /**
     * 设置CheckBox选中与否
     *
     * @param viewId
     *         the id of the CheckBox
     * @param b
     *         is checked
     *
     * @return
     *
     * @author Sogrey
     * @date 2015年5月11日
     */
    public CommonViewHolder setChecked_CheckBox(int viewId,boolean b) {
        ((CheckBox)getView(viewId)).setChecked(b);
        return this;
    }

    /**
     * 设置RadioButton选中与否
     *
     * @param viewId
     *         the id of the RadioButton
     * @param b
     *         is checked
     *
     * @return
     *
     * @author Sogrey
     * @date 2015年5月11日
     */
    public CommonViewHolder setChecked_RadioButton(int viewId,boolean b) {
        ((RadioButton)getView(viewId)).setChecked(b);
        return this;
    }

    /**
     * 设置评星数
     *
     * @param viewId
     * @param ratingbar_size
     *
     * @author Sogrey
     * @date 2015-10-26下午5:28:13
     */
    public CommonViewHolder setSize_RatingBar(int viewId,int ratingbar_size) {
        ((RatingBar)getView(viewId)).setProgress(ratingbar_size);
        return this;
    }

    /**
     * 设置评星（float）
     *
     * @param viewId
     * @param rating
     *
     * @return
     *
     * @author Sogrey
     * @date 2015-11-13上午11:06:17
     */
    public CommonViewHolder setRating_RatingBar(int viewId,float rating) {
        ((RatingBar)getView(viewId)).setRating(rating);
        return this;
    }

    /**
     * 设置View背景drawable
     *
     * @param viewId
     * @param background
     *         Drawable资源
     *
     * @return
     *
     * @author Sogrey
     * @date 2015年5月27日
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public CommonViewHolder setBackGround_View(int viewId,Drawable background) {
        getView(viewId).setBackground(background);
        return this;
    }

    /**
     * 设置View背景drawable
     *
     * @param viewId
     * @param backgroundResId
     *         背景資源ID
     *
     * @return
     *
     * @author Sogrey
     * @date 2016年4月26日
     */
    public CommonViewHolder setBackGround_View(int viewId,int backgroundResId) {
        getView(viewId).setBackgroundResource(backgroundResId);
        return this;
    }

    /**
     * 设置View背景颜色
     *
     * @param viewId
     * @param color
     *         颜色值
     *
     * @return
     *
     * @author Sogrey
     * @date 2015年5月27日
     */
    public CommonViewHolder setBackGroundColor_View(int viewId,int color) {
        getView(viewId).setBackgroundColor(color);
        return this;
    }

    /**
     * 设置View背景drawable
     *
     * @param viewId
     * @param background
     *         Drawable资源
     *
     * @return
     *
     * @author Sogrey
     * @date 2015年5月27日
     */
    @Deprecated
    public CommonViewHolder setBackGroundDrawable_View(
            int viewId,
            Drawable background
    ) {
        getView(viewId).setBackgroundDrawable(background);
        return this;
    }

    /**
     * 设置View背景资源
     *
     * @param viewId
     * @param resid
     *         资源ID
     *
     * @return
     *
     * @author Sogrey
     * @date 2015年5月27日
     */
    public CommonViewHolder setBackGroundResource_View(int viewId,int resid) {
        getView(viewId).setBackgroundResource(resid);
        return this;
    }

    /**
     * 设置View 点击事件
     *
     * @param viewId
     *         the id of the view
     * @param l
     *         the OnLongClickListener of view
     *
     * @return
     *
     * @author Sogrey
     * @date 2015年7月8日
     */
    public CommonViewHolder setOnclickListener(int viewId,OnClickListener l) {
        setOnclickListener(getView(viewId),l);
        return this;
    }

    /**
     * 设置View 点击事件
     *
     * @param v
     *         the view is clicked
     * @param l
     *         the OnLongClickListener of view
     *
     * @return
     *
     * @author Sogrey
     * @date 2015年7月8日
     */
    public CommonViewHolder setOnclickListener(View v,OnClickListener l) {
        v.setOnClickListener(l);
        return this;
    }

    /**
     * 设置View 长按事件
     *
     * @param viewId
     *         the id of the view
     * @param l
     *         the OnLongClickListener of view
     *
     * @return
     *
     * @author Sogrey
     * @date 2015年7月8日
     */
    public CommonViewHolder setOnLongclickListener(
            int viewId,
            OnLongClickListener l
    ) {
        setOnLongclickListener(getView(viewId),l);
        return this;
    }

    /**
     * 设置View 长按事件
     *
     * @param v
     *         the view is clicked
     * @param l
     *         the OnLongClickListener of view
     *
     * @return
     *
     * @author Sogrey
     * @date 2015年7月8日
     */
    public CommonViewHolder setOnLongclickListener(View v,OnLongClickListener l) {
        v.setOnLongClickListener(l);
        return this;
    }

}
