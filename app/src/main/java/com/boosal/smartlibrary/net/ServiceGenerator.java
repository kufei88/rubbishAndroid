package com.boosal.smartlibrary.net;

import com.boosal.smartlibrary.base.baseApp.BaseApplication;
import com.boosal.smartlibrary.net.Authenicator.TokenAuthenticator;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(initOKhttClient());


    public static <T> T createServiceFrom(Class<T> serviceClass) {
        builder.baseUrl(UrlStore.BASEURL_APP);
        return builder.build().create(serviceClass);
    }

    public static <T> T createServiceFrom(Class<T> serviceClass,String baseUrl) {
        builder.baseUrl(baseUrl);
        return builder.build().create(serviceClass);
    }

    private static OkHttpClient initOKhttClient() {
        try {
            //测试环境忽略所有证书
            HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);

            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .authenticator(new TokenAuthenticator())
                    .connectTimeout(20, TimeUnit.SECONDS)//设置连接超时时间
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(100, TimeUnit.SECONDS)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request()
                                    .newBuilder()
                                    .addHeader("Content-Type", "application/json;charset=utf-8")
                                    .addHeader("Accept", "application/json")
                                    .addHeader("Authorization", BaseApplication.getToken())
                                    .build();
                            return chain.proceed(request);
                        }
                    })
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    })
                    .build();
            return httpClient;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
