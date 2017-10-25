package org.sogrey.sogreyframe.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 通用RecycleView適配器
 * Created by Sogrey on 2016/4/1.
 */
public abstract class CommonRecycleViewAdapter<T> extends RecyclerView
        .Adapter<CommonRecycleViewHolder> {

    Context        mContext;
    List<T>        mData;
    LayoutInflater mInflater;
    int mLayoutId=0;
    private OnItemClickListener     mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public CommonRecycleViewAdapter(Context context,List<T> data,int layoutId) {
        this.mContext=context;
        this.mData=data;
        this.mLayoutId=layoutId;
        mInflater=LayoutInflater.from(this.mContext);
    }

    @Override
    public CommonRecycleViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View itemView=mInflater.inflate(this.mLayoutId,null);
        CommonRecycleViewHolder holder=new CommonRecycleViewHolder(itemView) {
            @Override
            public Context getContext() {
                return mContext;
            }
        };
        return holder;
    }

    @Override
    public void onBindViewHolder(CommonRecycleViewHolder holder,int position) {
        initEvent(holder,position);
        //                holder.setPosition(position);
        convert(holder,getItem(position),position);
    }

    protected abstract void convert(CommonRecycleViewHolder holder,T item,int position);


    protected void initEvent(final CommonRecycleViewHolder holder,final int position) {
        holder.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener!=null) {
                            mOnItemClickListener.onItemClick(holder.itemView,getItem(position),position);
                        }
                    }
                }
        );
        holder.itemView.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (mOnItemLongClickListener!=null) {
                            mOnItemLongClickListener.onItemLongClick(holder.itemView,getItem(position),position);
                            return true;
                        }
                        return false;
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return this.mData==null ? 0 : this.mData.size();
    }

    public T getItem(int position) {
        return this.mData.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        this.mOnItemClickListener=l;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener l) {
        this.mOnItemLongClickListener=l;
    }

    public interface OnItemClickListener {
        <T>void onItemClick(View view,T item,int position);
    }

    public interface OnItemLongClickListener {

        <T>void onItemLongClick(View view,T item,int position);
    }
}
