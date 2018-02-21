package com.example.georged.orarupb.webApiClient.implementation;

import com.example.georged.orarupb.utils.interfaces.IServiceEvents;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by George D on 23-Jun-17.
 */

class BaseService<T> {
    T api;
    IServiceEvents events;

    BaseService(String url, Class<T> type, IServiceEvents events) {
        this.events = events;
        this.api = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build())
                .build()
                .create(type);
    }
}
