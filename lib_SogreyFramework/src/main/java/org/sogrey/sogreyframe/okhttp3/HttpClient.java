package org.sogrey.sogreyframe.okhttp3;

import android.content.Context;
import android.text.TextUtils;

import org.sogrey.sogreyframe.utils.LogUtil;
import org.sogrey.sogreyframe.utils.NetUtils;

import java.io.IOException;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Sogrey on 2016/9/14.
 */
public class HttpClient {

    public static final String TAG = HttpClient.class.getSimpleName();
    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");

    private static HttpClient   mHttpClient;
    private        OkHttpClient mClient;

    enum HttpMethod {GET,POST}

    private HttpClient() {
        mClient=new OkHttpClient();
    }

    public static HttpClient getSingleton() {
        if (mHttpClient==null) {
            return new HttpClient();
        }
        return mHttpClient;
    }

    public static void debug(boolean isDebug){
        if(isDebug){LogUtil.openAll();}else{LogUtil.closeAll();}
    }
    /**
     * Get请求
     *
     * @param context
     *         上下文
     * @param tag
     *         请求tag
     * @param url
     *         请求地址
     * @param httpResponse
     *         响应监听
     *
     * @return
     */
    public void get(Context context,String tag,String url,HttpResponse httpResponse) {
        getData(context,tag,HttpMethod.GET,url,null,null,httpResponse);
    }

    /**
     * Get请求
     *
     * @param context
     *         上下文
     * @param tag
     *         请求tag
     * @param url
     *         请求地址
     * @param param
     *         请求参数键值对
     * @param httpResponse
     *         响应监听
     *
     * @return
     */
    public void get(Context context,String tag,String url,HttpRequestMapParameter param,HttpResponse httpResponse) {
        getData(context,tag,HttpMethod.GET,mkUrl(url,param),null,null,httpResponse);
    }

    /**
     * Get方法拼接url
     *
     * @param url 请求地址
     * @param param
     *         参数键值对
     *
     * @return
     */
    private String mkUrl(String url,HttpRequestMapParameter param) {
        LogUtil.i(TAG,"GET:"+url);
        if (param!=null&&param.size()>0) {
            LogUtil.i(TAG,"参数:");
            url+="?";
            Map<String,String> paramMap=param.getParam();
            for (Map.Entry entry : paramMap.entrySet()) {
                LogUtil.i(TAG,entry.getKey()+" = "+entry.getValue());
                url+=entry.getKey()+"="+entry.getValue()+"&";
            }
        }
        return url;
    }


    /**
     * Post请求 提交json数据
     *
     * @param context
     *         上下文
     * @param tag
     *         请求tag
     * @param url
     *         请求url
     * @param json
     *         提交数据json
     * @param httpResponse
     *         响应监听
     */
    public void post(Context context,String tag,String url,String json,HttpResponse httpResponse) {
        LogUtil.i(TAG,"POST:"+url);
        LogUtil.i(TAG,"参数:"+json);
        getData(context,tag,HttpMethod.POST,url,json,null,httpResponse);
    }

    /**
     * Post请求 提交键值对数据
     *
     * @param context
     *         上下文
     * @param tag
     *         请求tag
     * @param url
     *         请求url
     * @param param
     *         提交数据键值对
     * @param httpResponse
     *         响应监听
     */
    public void post(Context context,String tag,String url,HttpRequestMapParameter param,HttpResponse
            httpResponse) {
        LogUtil.i(TAG,"POST:"+url);
        if (param.getParam()!=null&&param.getParam().size()>0){
            LogUtil.i(TAG,"参数:");
            for (Map.Entry entry : param.getParam().entrySet()) {
                LogUtil.i(TAG,entry.getKey()+" = "+entry
                        .getValue());
            }
        }

        getData(context,tag,HttpMethod.POST,url,null,param,httpResponse);
    }


    /**
     * 请求网络数据
     *
     * @param context
     *         上下文
     * @param tag
     *         请求tag
     * @param method
     *         请求方法get or post
     * @param url
     *         请求地址url
     * @param json
     *         post请求提交json数据
     * @param param
     *         post请求提交键值对数据
     * @param httpResponse
     *         响应监听
     */
    private void getData(Context context,
            String tag,HttpMethod method,String url,String json,
            HttpRequestMapParameter param,HttpResponse httpResponse
    ) {
        if (!NetUtils.isConnected(context)) {// 没有网络连接
            if (httpResponse!=null) {
                httpResponse.fail(
                        tag,
                        "网络异常..."
                );// 网络异常
            }
            return;
        }
        try {
            new OkHttp3AsynRequest(tag,method,url,json,
                                   param,httpResponse
            ).execute();
        } catch (Exception e) {
            e.printStackTrace();
            if (httpResponse!=null)
                httpResponse.fail(tag,e.getMessage());
        }
    }

    /**
     * 异步访问网络
     *
     * @author Sogrey
     * @date 2015年6月29日
     */
    private class OkHttp3AsynRequest extends AsyncTask<Void,Void,HttpResponseResult> {

        String                  _tag;
        HttpMethod              _method;
        String                  _url;
        String                  _json;
        HttpRequestMapParameter _param;
        HttpResponse            _httpResponse;

        /**
         * 异步访问网络
         */
        public OkHttp3AsynRequest(
                String tag,HttpMethod method,String url,String json,
                HttpRequestMapParameter param,HttpResponse httpResponse
        ) {
            this._tag=tag;
            this._method=method;
            this._url=url;
            this._json=json;
            this._param=param;
            this._httpResponse=httpResponse;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            LogUtil.i(TAG,"准备访问网络...");
            if (this._httpResponse!=null) {
                this._httpResponse.start(this._tag);
            }
        }

        @Override
        protected HttpResponseResult doInBackground(Void... params) {
            HttpResponseResult result=new HttpResponseResult();
            switch (this._method) {
                case GET: {//Get请求
                    Request request=new Request.Builder().url(_url).build();
                    try {
                        Response response=mClient.newCall(request).execute();
                        if (response.isSuccessful()) {
                            result.result=response.body().string();
                        } else {
                            throw new IOException("Unexpected code "+response);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        result.err=e.getMessage();
                    }
                }
                break;
                case POST: {//Post请求
                    if (TextUtils.isEmpty(_json)) {//post提交json数据
                        RequestBody body=RequestBody.create(JSON,this._json);
                        Request request=new Request.Builder()
                                .url(this._url)
                                .post(body)
                                .build();
                        try {
                            Response response=mClient.newCall(request).execute();
                            if (response.isSuccessful()) {
                                result.result=response.body().string();
                            } else {
                                throw new IOException("Unexpected code "+response);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            result.err=e.getMessage();
                        }
                    } else if (_param!=null&&_param.size()>0) {
                        FormBody.Builder builder=new FormBody.Builder();
                        for (Map.Entry entry : _param.getParam().entrySet()) {
                            builder.add((String)entry.getKey(),(String)entry.getValue());
                        }
                        RequestBody formBody=builder.build();
                        Request request=new Request.Builder()
                                .url(this._url)
                                .post(formBody)
                                .build();
                        try {
                            Response response=mClient.newCall(request).execute();
                            if (response.isSuccessful()) {
                                result.result=response.body().string();
                            } else {
                                throw new IOException("Unexpected code "+response);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            result.err=e.getMessage();
                        }
                    }
                }
                break;
            }
            return result;
        }

        @Override
        protected void onPostExecute(HttpResponseResult result) {
            super.onPostExecute(result);
            LogUtil.i(TAG,"访问网络完成...");
            if (result!=null) {
                if (!TextUtils.isEmpty(result.result)) {
                    LogUtil.i(TAG,">>"+result.result);
                    if (this._httpResponse!=null) {
                        this._httpResponse.success(this._tag,result.result);
                    }
                } else if (!TextUtils.isEmpty(result.err)) {
                    LogUtil.i(TAG,">>"+result.err);
                    if (this._httpResponse!=null) {
                        this._httpResponse.fail(this._tag,result.err);
                    }
                }
            }
            if (this._httpResponse!=null) {
                this._httpResponse.complete(this._tag);
            }
            this.cancel(true);
        }
    }

    private static class HttpResponseResult {

        String result="";
        String err   ="";
    }
}
