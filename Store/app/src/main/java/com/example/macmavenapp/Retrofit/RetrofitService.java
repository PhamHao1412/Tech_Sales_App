package com.example.macmavenapp.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class RetrofitService {
    private String IPAddress="10.12.24.27"; //cafe
    OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(40, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();
    Gson gson = new GsonBuilder().setDateFormat("yyyy MM dd HH :mm:ss").setLenient().create();
    private Retrofit retrofit = null;
    public RetrofitService(){
        initializeRetrofit();
    }

    private void initializeRetrofit() {
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://"+IPAddress+":8090").addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
    public Retrofit getRetrofit() {
        return retrofit;
    }

}
