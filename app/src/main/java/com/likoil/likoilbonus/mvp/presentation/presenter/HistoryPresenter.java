package com.likoil.likoilbonus.mvp.presentation.presenter;

import com.likoil.likoilbonus.app.MyApp;
import com.likoil.likoilbonus.model.UserRepository;
import com.likoil.likoilbonus.mvp.network.DiscountData;
import com.likoil.likoilbonus.mvp.network.ServerAPI;
import com.likoil.likoilbonus.mvp.network.WithdrawalsData;
import com.likoil.likoilbonus.mvp.presentation.view.HistoryView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class HistoryPresenter extends MvpPresenter<HistoryView> {

    private ServerAPI serverAPI;
    private UserRepository userRepository;


    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        //loadData();
    }

    @Override
    public void attachView(HistoryView view) {
        super.attachView(view);
    }

    public void loadData(boolean isWithdrawal) {
        getViewState().showLoading();

        userRepository = MyApp.getInstance().getUserRepository();
        serverAPI = MyApp.getServerAPI();

        if (!isWithdrawal) {
            serverAPI.userDiscounts(userRepository.getToken()).enqueue(new Callback<DiscountData>() {
                @Override
                public void onResponse(Call<DiscountData> call, Response<DiscountData> response) {
                    getViewState().hideLoading();
                    if (response.code() == 401) {
                        //// TODO: 11.12.2016 очистка токена
                        userRepository.clearAuth();
                        MyApp.getRouter().restart();
                    } else if (response.code() == 200) {
                        DiscountData body = response.body();
                        getViewState().setData(body);
                    }
                }

                @Override
                public void onFailure(Call<DiscountData> call, Throwable t) {
                    getViewState().hideLoading();
                }
            });
        } else {
            serverAPI.userWithdrawals(userRepository.getToken()).enqueue(new Callback<WithdrawalsData>() {
                @Override
                public void onResponse(Call<WithdrawalsData> call, Response<WithdrawalsData> response) {
                    getViewState().hideLoading();
                    if (response.code() == 401) {
                        //// TODO: 11.12.2016 очистка токена
                        userRepository.clearAuth();
                        MyApp.getRouter().restart();
                    } else if (response.code() == 200) {
                        WithdrawalsData body = response.body();
                        getViewState().setWithdrawalsData(body);
                    }
                }

                @Override
                public void onFailure(Call<WithdrawalsData> call, Throwable t) {
                    getViewState().hideLoading();
                }
            });
        }
    }
}
