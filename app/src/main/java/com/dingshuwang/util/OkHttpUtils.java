package com.dingshuwang.util;

import android.util.Log;

import com.dingshuwang.DataView;
import com.dingshuwang.base.BaseActivity;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.apache.http.HttpStatus;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by raiyi-suzhou on 2015/5/20 0020.
 */
public class OkHttpUtils
{
    private static final OkHttpClient sOkHttpClient = new OkHttpClient();

    static
    {
        sOkHttpClient.setConnectTimeout(20, TimeUnit.SECONDS);
        sOkHttpClient.setReadTimeout(20, TimeUnit.SECONDS);
    }

    /**
     * 开启异步线程访问网络
     *
     * @param request：请求
     * @param callback:回调
     */
    public static void asyncExecute(Request request, Callback callback)
    {
        sOkHttpClient.newCall(request).enqueue(callback);
    }

    public static void cancleRequest(Request request)
    {
        sOkHttpClient.cancel(request);
    }

    public static void asynExecute(Request request)
    {
        asyncExecute(request, new Callback()
        {
            @Override
            public void onFailure(Request request, IOException e)
            {
                //空实现
            }

            @Override
            public void onResponse(Response response) throws IOException
            {

                //空实现
            }
        });
    }

    /**
     * 创建线程请求，返回的字符串结果在Callback#onResponse方法中的message中
     *
     * @param url
     * @param callback
     */
    public static void asynExecuteWithThread(final String url, final Callback callback)
    {
        new Thread()
        {
            public void run()
            {
                try
                {
                    StringBuffer stringBuffer = new StringBuffer();
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
                    int responsedCode = httpURLConnection.getResponseCode();
                    if (responsedCode == HttpStatus.SC_OK)
                    {
                        InputStream intputstream = httpURLConnection.getInputStream();
                        int len = -1;
                        byte[] buffer = new byte[1024];
                        while (((len = intputstream.read(buffer)) != -1))
                        {
                            stringBuffer.append(new String(buffer, 0, len, HTTP.UTF_8));
                        }
                    }
                    String json = stringBuffer.toString();
                    System.out.println("json = " + json);
                    callback.onResponse(new Response.Builder().request(new Request.Builder().url(url).build()).code(HttpStatus.SC_OK).protocol(Protocol.HTTP_1_1).message(json).build());
                } catch (IOException e)
                {
                    callback.onFailure(new Request.Builder().build(), new IOException("请求发生错误"));
                }
            }
        }.start();
    }

    public static void customAsyncExecuteWithFile(final BaseActivity activity, String url, File file, boolean needToken, final DataView dataView, final String mRequestTag)
    {
        System.out.println(" 请求URL = " + url);
        String accessToken = "";
        if (needToken)
        {
//            accessToken = activity.getAccessTokenItem();
//            if (null != accessTokenItem && accessTokenItem.accessToken != null)
//            {
//                accessToken = accessTokenItem.accessToken.accessToken;
//            }
        }
        Headers headers = new Headers.Builder().add("Authorization", "Bearer " + accessToken).build();
        MultipartBuilder multipartBuilder = new MultipartBuilder().type(MultipartBuilder.MIXED);
        //添加提交的文件列表
        if (null != file)
        {
            if (!file.exists())
            {
                System.out.println("选中的图片文件不存在");
            }
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
            String fileName = file.getName();
            if (!fileName.endsWith(".png"))
            {
                fileName = fileName + ".png";
            }
            multipartBuilder.addFormDataPart("files", fileName, fileBody);
        }
        RequestBody reqBody = multipartBuilder.build();
        Request request = new Request.Builder().url(url).headers(headers).post(reqBody).build();

        Callback callback = new Callback()
        {
            public void onFailure(Request request, final IOException e)
            {
                activity.runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        activity.dismissProgressDialog();
                        dataView.onGetDataFailured(e.getMessage(), mRequestTag);
                    }
                });
            }

            @Override
            public void onResponse(final Response response) throws IOException
            {
                final String result = (response != null) ? response.body().string() : null;
                activity.runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
//                        activity.dismissProgressDialog();
                        dataView.onGetDataSuccess(result, mRequestTag);
                    }
                });
            }
        };
        asyncExecute(request, callback);
    }

    public static final class TheadUtils
    {
        // 个人信息编辑
        public static void ModifyPhoto(final BaseActivity activity, final String apiurl, final String filePath, final boolean needAddToken, final DataView dataView, final String requestTag)
        {
            new Thread()
            {
                @Override
                public void run()
                {
                    doUploadPhoto(activity, apiurl, filePath, needAddToken, dataView, requestTag);
                }
            }.start();
        }

        public static void doUploadPhoto(final BaseActivity activity, final String apiurl, String filePath, boolean needAddToken, final DataView dataView, final String requestTag)
        {
            try
            {
                byte[] buf = new byte[1024];
                File file = new File( filePath);
                InputStream inputStream = new FileInputStream(file);
                final URL url = new URL(apiurl);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(4000);
                con.setReadTimeout(4000);
                con.setRequestMethod("POST");
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setRequestProperty("Charset", "utf-8");
                con.setRequestProperty("connection", "keep-alive");
                con.setRequestProperty("Content-Type", "multipart/form-data");
                DataOutputStream osw = new DataOutputStream(con.getOutputStream());
                int len = 0;
                while ((len = inputStream.read(buf) )!= -1)
                {
                    osw.write(buf,0,len);
                }
                osw.flush();
                osw.close();
                inputStream.close();
                String result = "";
                if(con.getResponseCode() == 200){
                    InputStream input = con.getInputStream();
                    StringBuffer sb1 = new StringBuffer();
                    int ss;
                    while ((ss = input.read()) != -1) {
                        sb1.append((char) ss);
                    }
                   result  = sb1.toString();
                }

                final String finalResult = result;
                activity.runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
//                        System.out.println("上传成功");

//                        DataView dataView1 = new DataView() {
//                            @Override
//                            public void onGetDataFailured(String msg, String requestTag) {
//
//                            }
//
//                            @Override
//                            public void onGetDataSuccess(String result, String requestTag) {
//                                Log.i("》》》》》   ","===== "+result);
//                            }
//                        };
//                        RequestUtils.getDataFromUrl(activity,apiurl,dataView1,"",false,false);
//                        activity.showToast("发布成功");
                        dataView.onGetDataSuccess(finalResult, requestTag);
                    }
                });
                return;
            } catch (Exception e)
            {
                Log.i("》》》》》   ",""+e.toString());
                e.printStackTrace();
            }
            activity.runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    activity.dismissProgressDialog();
//                    activity.showToast("发布失败，请稍后再试");
                }
            });
        }
    }
}
