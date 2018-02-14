package com.transmilenio.fuerzaoperativa.http;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class API {

//    public static final String BASE_URL = "http://10.0.2.2:8080/TmAPI/";
    public static final String BASE_URL = "http://35.226.255.51:8080/TmAPI/";
    private static Retrofit retrofit = null;

    public static Retrofit getApi(){
        if(retrofit == null ){
            retrofit =  new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
