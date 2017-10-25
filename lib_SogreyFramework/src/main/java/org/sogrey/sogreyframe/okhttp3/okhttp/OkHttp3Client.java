package org.sogrey.sogreyframe.okhttp3.okhttp;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import com.bumptech.glide.RequestManager;

import org.sogrey.sogreyframe.utils.MD5;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * http://blog.csdn.net/lmj623565791/article/details/47911083
 * http://www.cnblogs.com/whoislcj/p/5526431.html
 * Created by Sogrey on 2016/11/10.
 */

public class OkHttp3Client {

    private static final MediaType MEDIA_TYPE_JSON
            =MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");//mdiatype
    // 这个需要和服务端保持一致
    private static final MediaType MEDIA_TYPE_MARKDOWN
            =MediaType.parse("text/x-markdown; charset=utf-8");
    //流媒体
    private static final MediaType MEDIA_OBJECT_STREAM
            =MediaType.parse("application/octet-stream");
    //mdiatype 这个需要和服务端保持一致
    private static final String    TAG
            =RequestManager.class.getSimpleName();
    private              String    BASE_URL
            ="http://www.baidu.com";//请求接口根地址
    private static volatile OkHttp3Client mInstance;//单例引用
    private                 OkHttpClient  mOkHttpClient;//okHttpClient 实例
    private                 Handler       okHttpHandler;//全局处理子线程和M主线程通信

    public enum RequestType {
        /** get请求 */
        TYPE_GET,
        /** post请求参数为json */
        TYPE_POST_JSON,
        /** post请求参数为表单 */
        TYPE_POST_FORM
    }


    /**
     * 初始化RequestManager
     */
    private OkHttp3Client(Context context) {
        //初始化OkHttpClient
        mOkHttpClient=new OkHttpClient()
                .newBuilder()
                .connectTimeout(10,TimeUnit.SECONDS)//设置超时时间
                .readTimeout(10,TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(10,TimeUnit.SECONDS)//设置写入超时时间
                .build();
        //初始化Handler
        okHttpHandler=new Handler(context.getMainLooper());
    }

    /**
     * 设置请求接口根地址
     *
     * @param baseUrl 请求接口根地址
     */
    public void setBaseUrl(String baseUrl) {
        this.BASE_URL=baseUrl;
    }

    /**
     * 获取单例引用
     *
     * @return
     */
    public static OkHttp3Client getInstance(Context context) {
        OkHttp3Client inst=mInstance;
        if (inst==null) {
            synchronized (OkHttp3Client.class) {
                inst=mInstance;
                if (inst==null) {
                    inst=new OkHttp3Client(context.getApplicationContext());
                    mInstance=inst;
                }
            }
        }
        return inst;
    }

    /**
     * okHttp同步请求统一入口
     *
     * @param actionUrl   接口地址
     * @param requestType 请求类型
     * @param paramsMap   请求参数
     */
    public void requestSyn(
            String actionUrl,RequestType requestType,HashMap<String,String>
            paramsMap
    ) {
        switch (requestType) {
            case TYPE_GET:
                requestGetBySyn(actionUrl,paramsMap);
                break;
            case TYPE_POST_JSON:
                requestPostBySyn(actionUrl,paramsMap);
                break;
            case TYPE_POST_FORM:
                requestPostBySynWithForm(actionUrl,paramsMap);
                break;
        }
    }

    /**
     * okHttp get同步请求
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     */
    private void requestGetBySyn(String actionUrl,HashMap<String,String> paramsMap) {
        StringBuilder tempParams=new StringBuilder();
        try {
            //处理参数
            int pos=0;
            for (String key : paramsMap.keySet()) {
                if (pos>0) {
                    tempParams.append("&");
                }
                //对参数进行URLEncoder
                tempParams.append(String.format("%s=%s",key,URLEncoder.encode(
                        paramsMap.get(key),
                        "utf-8"
                )));
                pos++;
            }
            //补全请求地址
            String requestUrl=String.format("%s/%s?%s",BASE_URL,actionUrl,tempParams.toString());
            //创建一个请求
            Request request=addHeaders().url(requestUrl).build();
            //创建一个Call
            final Call call=mOkHttpClient.newCall(request);
            //执行请求
            final Response response=call.execute();
            response.body().string();
        } catch (Exception e) {
            Log.e(TAG,e.toString());
        }
    }

    /**
     * okHttp post同步请求
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     */
    private void requestPostBySyn(String actionUrl,HashMap<String,String> paramsMap) {
        try {
            //处理参数
            StringBuilder tempParams=new StringBuilder();
            int           pos       =0;
            for (String key : paramsMap.keySet()) {
                if (pos>0) {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s",key,URLEncoder.encode(
                        paramsMap.get(key),
                        "utf-8"
                )));
                pos++;
            }
            //补全请求地址
            String requestUrl=String.format("%s/%s",BASE_URL,actionUrl);
            //生成参数
            String params=tempParams.toString();
            //创建一个请求实体对象 RequestBody
            RequestBody body=RequestBody.create(MEDIA_TYPE_JSON,params);
            //创建一个请求
            final Request request=addHeaders().url(requestUrl).post(body).build();
            //创建一个Call
            final Call call=mOkHttpClient.newCall(request);
            //执行请求
            Response response=call.execute();
            //请求执行成功
            if (response.isSuccessful()) {
                //获取返回数据 可以是String，bytes ,byteStream
                Log.e(TAG,"response ----->"+response.body().string());
            }
        } catch (Exception e) {
            Log.e(TAG,e.toString());
        }
    }

    /**
     * okHttp post同步请求表单提交
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     */
    private void requestPostBySynWithForm(String actionUrl,HashMap<String,String> paramsMap) {
        try {
            //创建一个FormBody.Builder
            FormBody.Builder builder=new FormBody.Builder();
            for (String key : paramsMap.keySet()) {
                //追加表单信息
                builder.add(key,paramsMap.get(key));
            }
            //生成表单实体对象
            RequestBody formBody=builder.build();
            //补全请求地址
            String requestUrl=String.format("%s/%s",BASE_URL,actionUrl);
            //创建一个请求
            final Request request=addHeaders().url(requestUrl).post(formBody).build();
            //创建一个Call
            final Call call=mOkHttpClient.newCall(request);
            //执行请求
            Response response=call.execute();
            if (response.isSuccessful()) {
                Log.e(TAG,"response ----->"+response.body().string());
            }
        } catch (Exception e) {
            Log.e(TAG,e.toString());
        }
    }

    /**
     * okHttp异步请求统一入口
     *
     * @param tag         请求标识
     * @param actionUrl   接口地址
     * @param requestType 请求类型
     * @param paramsMap   请求参数
     * @param callBack    请求返回数据回调
     * @param <T>         数据泛型
     * @param <W>         解析数据泛型
     **/
    public <T,W> Call requestAsyn(
            String tag,String actionUrl,
            RequestType requestType,Map<String,String> paramsMap,
            OkHttp3ResponseCallBack<T,W> callBack
    ) {
        startCallBack(tag,callBack);
        Call call=null;
        switch (requestType) {
            case TYPE_GET:
                call=requestGetByAsyn(tag,actionUrl,paramsMap,callBack);
                break;
            case TYPE_POST_JSON:
                call=requestPostByAsyn(tag,actionUrl,paramsMap,callBack);
                break;
            case TYPE_POST_FORM:
                call=requestPostByAsynWithForm(tag,actionUrl,paramsMap,callBack);
                break;
        }
        return call;
    }

    /**
     * okHttp get异步请求
     *
     * @param tag       请求标识
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @param callBack  请求返回数据回调
     * @param <T>       数据泛型
     * @param <W>       解析数据泛型
     *
     * @return
     */
    private <T,W> Call requestGetByAsyn(
            final String tag,String actionUrl,Map<String,String> paramsMap,
            final OkHttp3ResponseCallBack<T,W> callBack
    ) {
        StringBuilder tempParams=new StringBuilder();
        try {
            int pos=0;
            for (String key : paramsMap.keySet()) {
                if (pos>0) {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s",key,URLEncoder.encode(
                        paramsMap.get(key),
                        "utf-8"
                )));
                pos++;
            }
            String requestUrl=String.format("%s/%s?%s",BASE_URL,actionUrl,tempParams
                    .toString());
            final Request request=addHeaders().url(requestUrl).build();
            final Call    call   =mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call,IOException e) {
                    failedCallBack(tag,"访问失败",callBack);
                    Log.e(TAG,e.toString());
                }

                @Override
                public void onResponse(Call call,Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String string=response.body().string();
                        //Log.e(TAG,"response ----->"+string);
                        successCallBack(tag,(T)string,callBack);
                    } else {
                        failedCallBack(tag,"服务器错误",callBack);
                    }
                }
            });
            return call;
        } catch (Exception e) {
            Log.e(TAG,e.toString());
        }
        return null;
    }

    /**
     * okHttp post异步请求
     *
     * @param tag       请求标识
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @param callBack  请求返回数据回调
     * @param <T>       数据泛型
     * @param <W>       解析数据泛型
     *
     * @return
     */
    private <T,W> Call requestPostByAsyn(
            final String tag,String actionUrl,Map<String,String> paramsMap,
            final OkHttp3ResponseCallBack<T,W> callBack
    ) {
        try {
            StringBuilder tempParams=new StringBuilder();
            int           pos       =0;
            for (String key : paramsMap.keySet()) {
                if (pos>0) {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s",key,URLEncoder.encode(
                        paramsMap.get(key),
                        "utf-8"
                )));
                pos++;
            }
            String        params    =tempParams.toString();
            RequestBody   body      =RequestBody.create(MEDIA_TYPE_JSON,params);
            String        requestUrl=String.format("%s/%s",BASE_URL,actionUrl);
            final Request request   =addHeaders().url(requestUrl).post(body).build();
            final Call    call      =mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call,IOException e) {
                    failedCallBack(tag,"访问失败",callBack);
                    Log.e(TAG,e.toString());
                }

                @Override
                public void onResponse(Call call,Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String string=response.body().string();
                        Log.e(TAG,"response ----->"+string);
                        successCallBack(tag,(T)string,callBack);
                    } else {
                        failedCallBack(tag,"服务器错误",callBack);
                    }
                }
            });
            return call;
        } catch (Exception e) {
            Log.e(TAG,e.toString());
        }
        return null;
    }

    /**
     * okHttp post异步请求表单提交
     *
     * @param tag       请求标识
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @param callBack  请求返回数据回调
     * @param <T>       数据泛型
     * @param <W>       解析数据泛型
     *
     * @return
     */
    private <T,W> Call requestPostByAsynWithForm(
            final String tag,String actionUrl,Map<String,String> paramsMap,
            final OkHttp3ResponseCallBack<T,W> callBack
    ) {
        try {
            FormBody.Builder builder=new FormBody.Builder();
            for (String key : paramsMap.keySet()) {
                builder.add(key,paramsMap.get(key));
            }
            RequestBody   formBody  =builder.build();
            String        requestUrl=String.format("%s/%s",BASE_URL,actionUrl);
            final Request request   =addHeaders().url(requestUrl).post(formBody).build();
            final Call    call      =mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call,IOException e) {
                    failedCallBack(tag,"访问失败",callBack);
                    Log.e(TAG,e.toString());
                }

                @Override
                public void onResponse(Call call,Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String string=response.body().string();
                        Log.e(TAG,"response ----->"+string);
                        successCallBack(tag,(T)string,callBack);
                    } else {
                        failedCallBack(tag,"服务器错误",callBack);
                    }
                }
            });
            return call;
        } catch (Exception e) {
            Log.e(TAG,e.toString());
        }
        return null;
    }

    /**
     * 1.）不带参数上传文件<br/>
     * 上传文件
     *
     * @param tag       请求标识
     * @param actionUrl 接口地址
     * @param filePath  本地文件地址
     * @param callBack  结果回调
     * @param <T>       回调泛型
     * @param <W>       解析数据泛型
     */
    public <T,W> void upLoadFile(
            final String tag,String actionUrl,String filePath,
            final OkHttp3ResponseCallBack<T,W> callBack
    ) {
        startCallBack(tag,callBack);
        //补全请求地址
        String requestUrl=String.format("%s/%s",BASE_URL,actionUrl);
        //创建File
        File file=new File(filePath);
        //创建RequestBody
        RequestBody body=RequestBody.create(MEDIA_OBJECT_STREAM,file);
        //创建Request
        final Request request=new Request.Builder().url(requestUrl).post(body).build();
        final Call call=mOkHttpClient.newBuilder().writeTimeout(50,TimeUnit.SECONDS).build
                ().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call,IOException e) {
                Log.e(TAG,e.toString());
                failedCallBack(tag,"上传失败："+e.getMessage(),callBack);
            }

            @Override
            public void onResponse(Call call,Response response) throws IOException {
                if (response.isSuccessful()) {
                    String string=response.body().string();
                    Log.e(TAG,"response ----->"+string);
                    successCallBack(tag,(T)string,callBack);
                } else {
                    failedCallBack(tag,"上传失败",callBack);
                }
            }
        });
    }

    /**
     * 2.）带参数上传文件<br/>
     * 上传文件
     *
     * @param tag       请求标识
     * @param actionUrl 接口地址
     * @param paramsMap 参数
     * @param callBack  结果回调
     * @param <T>       回调泛型
     * @param <W>       解析数据泛型
     */
    public <T,W> void upLoadFile(
            final String tag,String actionUrl,HashMap<String,Object> paramsMap,
            final OkHttp3ResponseCallBack<T,W> callBack
    ) {
        startCallBack(tag,callBack);
        try {
            //补全请求地址
            String                requestUrl=String.format("%s/%s",BASE_URL,actionUrl);
            MultipartBody.Builder builder   =new MultipartBody.Builder();
            //设置类型
            builder.setType(MultipartBody.FORM);
            //追加参数
            for (String key : paramsMap.keySet()) {
                Object object=paramsMap.get(key);
                if (!(object instanceof File)) {
                    builder.addFormDataPart(key,object.toString());
                } else {
                    File file=(File)object;
                    builder.addFormDataPart(key,file.getName(),RequestBody.create(null,file));
                }
            }
            //创建RequestBody
            RequestBody body=builder.build();
            //创建Request
            final Request request=new Request.Builder().url(requestUrl).post(body).build();
            //单独设置参数 比如读取超时时间
            final Call call=mOkHttpClient.newBuilder().writeTimeout(50,TimeUnit.SECONDS).build()
                                         .newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call,IOException e) {
                    Log.e(TAG,e.toString());
                    failedCallBack(tag,"上传失败",callBack);
                }

                @Override
                public void onResponse(Call call,Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String string=response.body().string();
                        Log.e(TAG,"response ----->"+string);
                        successCallBack(tag,(T)string,callBack);
                    } else {
                        failedCallBack(tag,"上传失败",callBack);
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG,e.toString());
        }
    }

    /**
     * 3.)带参数带进度上传文件<br/>
     * 上传文件
     *
     * @param tag       请求标识
     * @param actionUrl 接口地址
     * @param paramsMap 参数
     * @param callBack  进度回调
     * @param <T>       回调泛型
     * @param <W>       解析数据泛型
     */
    public <T,W> void upLoadFile(
            final String tag,String actionUrl,HashMap<String,Object> paramsMap,
            final OkHttp3ProgressCallBack<T,W> callBack
    ) {
        startCallBack(tag,callBack);
        try {
            //补全请求地址
            String                requestUrl=String.format("%s/%s",BASE_URL,actionUrl);
            MultipartBody.Builder builder   =new MultipartBody.Builder();
            //设置类型
            builder.setType(MultipartBody.FORM);
            //追加参数
            for (String key : paramsMap.keySet()) {
                Object object=paramsMap.get(key);
                if (!(object instanceof File)) {
                    builder.addFormDataPart(key,object.toString());
                } else {
                    File file=(File)object;
                    builder.addFormDataPart(key,file.getName(),createProgressRequestBody
                            (tag,MEDIA_OBJECT_STREAM,file,callBack));
                }
            }
            //创建RequestBody
            RequestBody body=builder.build();
            //创建Request
            final Request request=new Request.Builder().url(requestUrl).post(body).build();
            final Call call=mOkHttpClient.newBuilder().writeTimeout(50,TimeUnit.SECONDS)
                                         .build().newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call,IOException e) {
                    Log.e(TAG,e.toString());
                    failedCallBack(tag,"上传失败",callBack);
                }

                @Override
                public void onResponse(Call call,Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String string=response.body().string();
                        Log.e(TAG,"response ----->"+string);
                        successCallBack(tag,(T)string,callBack);
                    } else {
                        failedCallBack(tag,"上传失败",callBack);
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG,e.toString());
        }
    }


    /**
     * 4.）创建带进度的RequestBody
     *
     * @param tag         请求标识
     * @param contentType MediaType
     * @param file        准备上传的文件
     * @param callBack    进度回调
     * @param <T>         回调泛型
     * @param <W>         解析数据泛型
     *
     * @return
     */
    public <T,W> RequestBody createProgressRequestBody(
            final String tag,final MediaType contentType,final File file,
            final OkHttp3ProgressCallBack<T,W> callBack
    ) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return contentType;
            }

            @Override
            public long contentLength() {
                return file.length();
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Source source;
                try {
                    source=Okio.source(file);
                    Buffer buf      =new Buffer();
                    long   remaining=contentLength();
                    long   current  =0;
                    for (long readCount;(readCount=source.read(buf,2048))!=-1;) {
                        sink.write(buf,readCount);
                        current+=readCount;
                        Log.e(TAG,"current------>"+current);
                        progressCallBack(tag,remaining,current,callBack);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * 5.)不带进度文件下载<br/>
     * 下载文件
     *
     * @param tag         请求标识
     * @param fileUrl     文件url
     * @param destFileDir 存储目标目录
     * @param callBack    结果回调
     * @param <T>         回调泛型
     * @param <W>         解析数据泛型
     */
    public <T,W> void downLoadFile(
            final String tag,String fileUrl,final String destFileDir,
            final OkHttp3ResponseCallBack<T,W> callBack
    ) {
        startCallBack(tag,callBack);
        final String fileName=MD5.md5(fileUrl);
        final File   file    =new File(destFileDir,fileName);
        if (file.exists()) {
            successCallBack(tag,(T)file,callBack);
            return;
        }
        final Request request=new Request.Builder().url(fileUrl).build();
        final Call    call   =mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call,IOException e) {
                Log.e(TAG,e.toString());
                failedCallBack(tag,"下载失败",callBack);
            }

            @Override
            public void onResponse(Call call,Response response) throws IOException {
                InputStream      is =null;
                byte[]           buf=new byte[2048];
                int              len=0;
                FileOutputStream fos=null;
                try {
                    long total=response.body().contentLength();
                    Log.e(TAG,"total------>"+total);
                    long current=0;
                    is=response.body().byteStream();
                    fos=new FileOutputStream(file);
                    while ((len=is.read(buf))!=-1) {
                        current+=len;
                        fos.write(buf,0,len);
                        Log.e(TAG,"current------>"+current);
                    }
                    fos.flush();
                    successCallBack(tag,(T)file,callBack);
                } catch (IOException e) {
                    Log.e(TAG,e.toString());
                    failedCallBack(tag,"下载失败",callBack);
                } finally {
                    try {
                        if (is!=null) {
                            is.close();
                        }
                        if (fos!=null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        Log.e(TAG,e.toString());
                    }
                }
            }
        });
    }


    /**
     * 6.）带进度文件下载<br/>
     * 下载文件
     *
     * @param tag         请求标识
     * @param fileUrl     文件url
     * @param destFileDir 存储目标目录
     * @param callBack    结果回调
     * @param <T>         回调泛型
     * @param <W>         解析数据泛型
     */
    public <T,W> void downLoadFile(
            final String tag,String fileUrl,final String destFileDir,
            final OkHttp3ProgressCallBack<T,W> callBack
    ) {
        startCallBack(tag,callBack);
        final String fileName=MD5.md5(fileUrl);
        final File   file    =new File(destFileDir,fileName);
        if (file.exists()) {
            successCallBack(tag,(T)file,callBack);
            return;
        }
        final Request request=new Request.Builder().url(fileUrl).build();
        final Call    call   =mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call,IOException e) {
                Log.e(TAG,e.toString());
                failedCallBack(tag,"下载失败",callBack);
            }

            @Override
            public void onResponse(Call call,Response response) throws IOException {
                InputStream      is =null;
                byte[]           buf=new byte[2048];
                int              len=0;
                FileOutputStream fos=null;
                try {
                    long total=response.body().contentLength();
                    Log.e(TAG,"total------>"+total);
                    long current=0;
                    is=response.body().byteStream();
                    fos=new FileOutputStream(file);
                    while ((len=is.read(buf))!=-1) {
                        current+=len;
                        fos.write(buf,0,len);
                        Log.e(TAG,"current------>"+current);
                        progressCallBack(tag,total,current,callBack);
                    }
                    fos.flush();
                    successCallBack(tag,(T)file,callBack);
                } catch (IOException e) {
                    Log.e(TAG,e.toString());
                    failedCallBack(tag,"下载失败",callBack);
                } finally {
                    try {
                        if (is!=null) {
                            is.close();
                        }
                        if (fos!=null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        Log.e(TAG,e.toString());
                    }
                }
            }
        });
    }

    /**
     * 8.）文件上传下载进度回调实现<br/>
     * 统一处理进度信息
     *
     * @param tag      请求标识
     * @param total    总计大小
     * @param current  当前进度
     * @param callBack 进度回调
     * @param <T>      回调泛型
     * @param <W>      解析数据泛型
     */
    private <T,W> void progressCallBack(
            final String tag,
            final long total,final long current,final
            OkHttp3ProgressCallBack<T,W> callBack
    ) {
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack!=null) {
                    callBack.onProgress(tag,total,current,((int)(current*10000.00/total))/100.00);
                }
            }
        });
    }


    /**
     * 统一为请求添加头信息
     *
     * @return 构建者
     */
    private Request.Builder addHeaders() {
        Request.Builder builder=new Request.Builder()
                .addHeader("Connection","keep-alive")
                .addHeader("platform","2")
                .addHeader("phoneModel",Build.MODEL)
                .addHeader("systemVersion",Build.VERSION.RELEASE)
                .addHeader("appVersion","3.2.0");
        return builder;
    }

    /**
     * 统一处理开始请求
     *
     * @param tag      请求标识
     * @param callBack 回调
     */
    private void startCallBack(
            final String tag,final OkHttp3ResponseCallBack callBack
    ) {
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack!=null) {
                    callBack.start(tag);
                }
            }
        });
    }

    /**
     * 统一同意处理成功信息
     *
     * @param tag      请求标识
     * @param result   返回成功结果
     * @param callBack 回调
     * @param <T>      回调数据泛型
     * @param <W>      解析数据泛型
     */
    private <T,W> void successCallBack(
            final String tag,final T result,
            final OkHttp3ResponseCallBack<T,W> callBack
    ) {
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack!=null) {
                    callBack.success(tag,result);
                    callBack.complete(tag);
                }
            }
        });
    }

    /**
     * 统一处理失败信息
     *
     * @param tag      请求标识
     * @param errorMsg 错误信息
     * @param callBack 回调
     * @param <T>      回调数据泛型
     * @param <W>      解析数据泛型
     */
    private <T,W> void failedCallBack(
            final String tag,final String errorMsg,
            final OkHttp3ResponseCallBack<T,W> callBack
    ) {
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack!=null) {
                    callBack.fail(tag,errorMsg);
                    callBack.complete(tag);
                }
            }
        });
    }
}
