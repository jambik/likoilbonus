package com.likoil.likoilbonus.app;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.likoil.likoilbonus.model.UserRepository;
import com.likoil.likoilbonus.mvp.network.ServerAPI;
import com.likoil.likoilbonus.ui.IRouter;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jambi on 11.12.2016.
 */

public class MyApp extends Application {

    private static MyApp instance;
    private static  IRouter router;
    private static  UserRepository userRepository;
    private static ServerAPI serverAPI;

    public static ServerAPI getServerAPI() {
        return serverAPI;
    }

    public static MyApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        UserRepository.setContext(this);

//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://jambik.ru/api/")
                //.client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        serverAPI = retrofit.create(ServerAPI.class);
    }

    public static IRouter getRouter() {
        return router;
    }

    public static void setRouter(IRouter router) {
        MyApp.router = router;
    }

    public UserRepository getUserRepository() {
        if (userRepository == null) {
            userRepository = new UserRepository();
        }
        return userRepository;
    }
}
