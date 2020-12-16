package com.khusiexpress.fieldbuzznayan.network;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient mInstance;
    private final Retrofit retrofit;
    private RetrofitClient(){
        String BASE_URL = "https://recruitment.fisdev.com/";
        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient())
                .build();
    }

    public static synchronized RetrofitClient getInstance(){
        if (mInstance==null)
            mInstance=new RetrofitClient();
        return mInstance;
    }

    public ApiInterface getApi(){
        return retrofit.create(ApiInterface.class);
    }

    private static OkHttpClient okHttpClient(){
        return new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
    }

}
