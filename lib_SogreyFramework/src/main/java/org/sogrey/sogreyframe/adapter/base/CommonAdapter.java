/**
 * @author Sogrey
 * @date 2015-05-11
 */
package org.sogrey.sogreyframe.adapter.base;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.sogrey.sogreyframe.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * 通用适配器
 *
 * @author Sogrey
 * @date 2015-05-11
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

	protected Context mContext;
	protected List<T>       mLst= new ArrayList<T>();
	protected List<Integer> mPositions
			= new ArrayList<Integer>();
	protected int mLayoutId;

	/**
	 *
	 * @param context
	 *            上下文对象
	 * @param lst
	 *            数据源
	 * @param layoutId
	 *            布局ID
	 */
	public CommonAdapter(Context context,List<T> lst,int layoutId) {
		super();
		this.mContext = context;
		this.mLst = lst;
		this.mLayoutId = layoutId;
	}
	/**
	 * 数据源刷新
	 *
	 * @author Sogrey
	 * @date 2015-12-23下午3:10:07
	 * @param lst
	 */
	public void refeshData(List<T> lst) {
		this.mLst=lst ;
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		return (this.mLst != null && this.mLst.size() > 0) ? this.mLst.size()
				: 0;
	}

	@Override
	public T getItem(int position) {
		return this.mLst.get(position);
	}

	@Override
	public long getItemId(int position) {
		return this.mLst.get(position).hashCode();
	}

	@Override
	public View getView(int position,View convertView,ViewGroup parent) {
		CommonViewHolder holder = CommonViewHolder.get(this.mContext,
				convertView, parent, this.mLayoutId, position);
		convert(holder, getItem(position));
		return holder.getConvertView();
	}

	/**
	 * 适配器数据适配
	 *
	 * @author Sogrey
	 * @date 2015年5月11日
	 * @param holder
	 *            通用ViewHolder
	 * @param t
	 *            item of the list
	 * @see CommonViewHolder
	 */
	public abstract void convert(CommonViewHolder holder, T t);

	/**
	 * 获取适配器布局ID
	 *
	 * @author Sogrey
	 * @date 2015年8月14日
	 * @return
	 */
	public int getLayoutId() {
		return mLayoutId;
	}

	/**
	 * 添加当前position到已选择序列中
	 *
	 * @author Sogrey
	 * @date 2015年8月7日
	 * @param position
	 * @return
	 */
	public boolean addPosition(int position) {
		return mPositions.add((Integer) position);
	}

	/**
	 * 从已选择序列中移除指定position
	 *
	 * @author Sogrey
	 * @date 2015年8月7日
	 * @param position
	 * @return
	 */
	public boolean removePosition(int position) {
		return mPositions.remove((Integer) position);
	}

	/**
	 * 从已选择序列中移除多个指定position
	 *
	 * @author Sogrey
	 * @date 2015年8月7日
	 * @param positions
	 * @return
	 */
	public void removePositions(int... positions) {
		if (positions != null) {
			for (int i = 0; i < positions.length; i++) {
				mPositions.remove((Integer) positions[i]);
			}
		}
	}

	/**
	 * 从已选择序列中移除多个指定position
	 *
	 * @author Sogrey
	 * @date 2015年8月14日
	 * @param positions
	 */
	public void removePositions(List<Integer> positions) {
		if (positions != null) {
			mPositions.removeAll(positions);
		}
	}

	/**
	 * 获取当前以选择的position集合
	 *
	 * @author Sogrey
	 * @date 2015年8月7日
	 * @return
	 */
	public List<Integer> getPositions() {
		return mPositions;
	}

	/**
	 * 清空position集合
	 *
	 * @author Sogrey
	 * @date 2015年8月7日
	 */
	public void clearPositions() {
		mPositions.clear();
	}

	/**
	 * 全选当前集合
	 *
	 * @author Sogrey
	 * @date 2015年8月7日
	 */
	public void selectedAllPositions() {
		for (int i = 0; i < mLst.size(); i++) {
			mPositions.add((Integer) i);
		}
	}

	/**
	 * 判断是否已选择
	 *
	 * @author Sogrey
	 * @date 2015年8月7日
	 * @param position
	 * @return
	 */
	public boolean isSelected(int position) {
		return mPositions.contains((Integer) position);
	}

	/**
	 * 智能切换选择状态
	 *
	 * @author Sogrey
	 * @date 2015年8月7日
	 * @param position
	 */
	public void toggleSelected(int position) {
		if (isSelected(position)) {
			mPositions.remove((Integer) position);
		} else {
			mPositions.add((Integer) position);
		}
	}

	/**
	 * 添加数据
	 *
	 * @param t
	 *            数据项
	 */
	public boolean addItem(T t) {
		return mLst.add(t);
	}

	/**
	 * 在指定索引位置添加数据
	 *
	 * @param location
	 *            索引
	 * @param t
	 *            数据
	 */
	public void addItem(int location, T t) {
		mLst.add(location, t);
	}

	/**
	 * 集合方式添加数据
	 *
	 * @param collection
	 *            集合
	 */
	public boolean addItem(Collection<? extends T> collection) {
		return mLst.addAll(collection);
	}

	/**
	 * 在指定索引位置添加数据集合
	 *
	 * @param location
	 *            索引
	 * @param collection
	 *            数据集合
	 */
	public boolean addItem(int location, Collection<? extends T> collection) {
		return mLst.addAll(location, collection);
	}

	/**
	 * 设置数据源（全部替换）
	 *
	 * @author Sogrey
	 * @date 2015年8月14日
	 * @param collection
	 */
	public void setDatas(Collection<? extends T> collection) {
		mLst = (List<T>) collection;
	}

	/**
	 * 获取所有数据
	 *
	 * @author Sogrey
	 * @date 2015年8月14日
	 * @return
	 */
	public List<T> getDatas() {
		return mLst;
	}

	/**
	 * 移除指定对象数据
	 *
	 * @param t
	 *            移除对象
	 * @return 是否移除成功
	 */
	public boolean removeItem(T t) {
		return mLst.remove(t);
	}

	/**
	 * 移除指定索引位置对象
	 *
	 * @param location
	 *            删除对象索引位置
	 * @return 被删除的对象
	 */
	public T removeItem(int location) {
		return mLst.remove(location);
	}

	/**
	 * 移除指定集合对象
	 *
	 * @param collection
	 *            待移除的集合
	 * @return 是否移除成功
	 */
	public boolean removeAll(Collection<? extends T> collection) {
		return mLst.removeAll(collection);
	}

	/**
	 * 清空数据
	 */
	public void clearDatas() {
		mLst.clear();
	}

	/**
	 * 获取Activity方法
	 *
	 * @return Activity的子类
	 */
	public Activity getActivity() {
		if (null == mContext)
			return null;

		if (mContext instanceof BaseActivity)
			return (BaseActivity) mContext;

		return null;
	}
}
