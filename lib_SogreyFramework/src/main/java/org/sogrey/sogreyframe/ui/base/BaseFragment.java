package org.sogrey.sogreyframe.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;


/**
 * 基本fragment;
 *
 * @author Sogrey.
 */
public abstract class BaseFragment extends Fragment {

    private LayoutInflater inflater;
    private View           contentView;
    public  Context        mContext;
    private ViewGroup      container;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,Bundle savedInstanceState
    ) {
        this.inflater=inflater;
        this.container=container;
        onCreateView(savedInstanceState);
        if (contentView==null)
            return super.onCreateView(inflater,container,savedInstanceState);
        initDatas();
        return contentView;
    }

    public abstract void initDatas();

    protected abstract void onCreateView(Bundle savedInstanceState); {
        //setContentView() here
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        contentView=null;
        container=null;
        inflater=null;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        this.mContext=context;
    }


    public void setContentView(int layoutResID) {
        contentView =LayoutInflater.from(this.mContext)
                                        .inflate(layoutResID,container,false);
        setContentView(contentView);
    }

    public void setContentView(View view) {
        contentView=view;
//        ButterKnife.bind(this,contentView);
    }

    public View getContentView() {
        return contentView;
    }

    public View findViewById(int id) {
        if (contentView!=null)
            return contentView.findViewById(id);
        return null;
    }

    // http://stackoverflow.com/questions/15207305/getting-the-error-java-lang
    // -illegalstateexception-activity-has-been-destroyed
    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager=Fragment.class
                    .getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this,null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
