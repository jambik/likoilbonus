package com.likoil.likoilbonus.mvp.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jambi on 11.12.2016.
 */

public interface ServerAPI {
    @GET("user/auth/")
    Call<LoginData> login(@Query("email") String login, @Query("password") String password);

    @GET("user/info/")
    Call<StatusData> userInfo(@Query("api_token") String token);

    @GET("user/discounts/")
    Call<DiscountData> userDiscounts(@Query("api_token") String token);

    @GET("user/withdrawals/")
    Call<WithdrawalsData> userWithdrawals(@Query("api_token") String token);
}
