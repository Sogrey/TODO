package org.sogrey.sogreyframe.okhttp3.okhttp;

/**
 * 带进度的http请求监听接口
 * Created by Sogrey on 2016/11/18.
 */
public interface OkHttp3ProgressCallBack<T,W> extends OkHttp3ResponseCallBack<T,W> {

    /**
     * 响应进度更新
     * @param tag
     * @param total
     * @param current
     * @param percentage
     */
    void onProgress(String tag,long total,long current,double percentage);
}