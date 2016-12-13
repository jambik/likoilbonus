package com.likoil.likoilbonus.mvp.presentation.presenter;

import com.likoil.likoilbonus.app.MyApp;
import com.likoil.likoilbonus.model.UserRepository;
import com.likoil.likoilbonus.mvp.network.DiscountData;
import com.likoil.likoilbonus.mvp.network.ServerAPI;
import com.likoil.likoilbonus.mvp.presentation.view.HistoryView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

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
        userRepository = MyApp.getInstance().getUserRepository();
        serverAPI = MyApp.getServerAPI();
        loadData();
    }

    @Override
    public void attachView(HistoryView view) {
        super.attachView(view);
    }

    void loadData() {
        getViewState().showLoading();
        serverAPI.userDiscounts(userRepository.getToken()).enqueue(new Callback<DiscountData>() {
            @Override
            public void onResponse(Call<DiscountData> call, Response<DiscountData> response) {
                getViewState().hideLoading();
                if (response.code() == 401) {
                    //// TODO: 11.12.2016 очистка токена
                    userRepository.clearAuth();

                } else if (response.code() == 200) {
                    getViewState().setData(response.body());
                }
            }

            @Override
            public void onFailure(Call<DiscountData> call, Throwable t) {
                getViewState().hideLoading();
            }
        });
    }
}
