package org.sogrey.sogreyframe.okhttp3.okhttp;

import java.util.List;

/**
 * http请求监听接口
 * Created by Sogrey on 2016/11/18.
 */

public interface OkHttp3ResponseCallBack <T,W> {
    /**
     * 开始请求
     * @param tag 请求标识
     */
    void start(String tag);

    /**
     * 成功
     * @param tag 请求标识
     * @param json 请求成功返回结果
     */
    void success(String tag,T json);

    /**
     * json对象解析成功
     * @param tag 请求标识
     * @param w 解析的json对象
     */
    void parse(String tag,W w);

    /**
     * json对象集合解析成功
     * @param tag 请求标识
     * @param ws 解析的json对象集合
     */
    void parse(String tag,List<W> ws);

    /**
     * 失败
     * @param tag 请求标识
     * @param err 请求失败返回异常信息
     */
    void fail(String tag,String err);

    /**
     * 完成（最后一步）
     * @param tag 请求标识
     */
    void complete(String tag);
}
