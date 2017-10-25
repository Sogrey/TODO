package org.sogrey.sogreyframe.ui.base.sideslipmenu;


import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.sogrey.sogreyframe.R;


/**
 * User: special Date: 13-12-10 Time: 下午11:05 Mail: specialcyci@gmail.com
 */
public class SideslipMenuItem extends LinearLayout {

	/** menu item icon */
	private ImageView iv_icon;
	/** menu item title */
	private TextView tv_title;

	public SideslipMenuItem(Context context) {
		super(context);
		initViews(context);
	}

	public SideslipMenuItem(Context context, int icon, int title) {
		super(context);
		initViews(context);
		iv_icon.setImageResource(icon);
		tv_title.setText(title);
	}

	public SideslipMenuItem(Context context, int icon, String title) {
		super(context);
		initViews(context);
		iv_icon.setImageResource(icon);
		tv_title.setText(title);
	}

	private void initViews(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.sideslipmenu_item,this);
		iv_icon = (ImageView) findViewById(R.id.iv_sideslipmenu_item_icon);
		tv_title = (TextView) findViewById(R.id.tv_sideslipmenu_item_title);
	}

	/**
	 * set the icon color;
	 * 
	 * @param icon
	 */
	public void setIcon(int icon) {
		iv_icon.setImageResource(icon);
	}

	/**
	 * set the title with resource ;
	 * 
	 * @param title
	 */
	public void setTitle(int title) {
		tv_title.setText(title);
	}

	/**
	 * set the title with string;
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		tv_title.setText(title);
	}
}
