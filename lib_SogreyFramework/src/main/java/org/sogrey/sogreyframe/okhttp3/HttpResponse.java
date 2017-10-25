package org.sogrey.sogreyframe.okhttp3;

/**
 * Created by Sogrey on 2016/9/14.
 */
public interface HttpResponse {

    /**
     * 开始请求
     * @param tag
     */
    void start(String tag);

    /**
     * 成功
     * @param tag
     * @param json
     */
    void success(String tag,String json);

    /**
     * 失败
     * @param tag
     * @param err
     */
    void fail(String tag,String err);

    /**
     * 完成（最后一步）
     * @param tag
     */
    void complete(String tag);

}
