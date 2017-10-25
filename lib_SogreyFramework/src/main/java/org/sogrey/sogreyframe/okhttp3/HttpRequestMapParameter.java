package org.sogrey.sogreyframe.okhttp3;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sogrey on 2016/9/14.
 */
public class HttpRequestMapParameter {
    private Map<String,String> mParam = new HashMap<>();
    public HttpRequestMapParameter(){
        mParam.clear();
    }
    private void check(){
        if (mParam==null)  mParam = new HashMap<>();
    }
    public HttpRequestMapParameter add(String key,String value){
        check();
        mParam.put(key, value);
        return this;
    }
    public HttpRequestMapParameter remove(String key,String value){
        check();
        mParam.remove(key);
        return this;
    }
    public Map<String,String> getParam(){
        return mParam;
    }
    public int size(){
        return mParam.size();
    }
}
