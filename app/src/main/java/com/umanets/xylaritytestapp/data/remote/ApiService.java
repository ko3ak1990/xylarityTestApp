package com.umanets.xylaritytestapp.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.umanets.xylaritytestapp.data.model.BackendResponse;
import com.umanets.xylaritytestapp.util.MyGsonTypeAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;


public interface ApiService {

    String ENDPOINT = "http://backend1.lordsandknights.com/XYRALITY/";
    @FormUrlEncoded
    @Headers({
            "accept: application/json"
    })
    @POST("WebObjects/BKLoginServer.woa/wa/worlds")
    Observable<BackendResponse> getWords(@Field("login") String login,
                                         @Field("password")String password,
                                         @Field("deviceType") String deviceType,
                                         @Field("deviceId") String deviceId);

    /******** Helper class that sets up a new services *******/
    class Creator {

        public static ApiService newApiService() {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(MyGsonTypeAdapterFactory.create())
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiService.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(ApiService.class);
        }
    }
}
