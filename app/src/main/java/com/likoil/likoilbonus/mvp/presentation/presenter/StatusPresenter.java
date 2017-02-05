package com.likoil.likoilbonus.mvp.presentation.presenter;

import com.google.gson.stream.MalformedJsonException;
import com.likoil.likoilbonus.app.MyApp;
import com.likoil.likoilbonus.model.UserRepository;
import com.likoil.likoilbonus.mvp.network.ServerAPI;
import com.likoil.likoilbonus.mvp.network.StatusData;
import com.likoil.likoilbonus.mvp.presentation.view.StatusView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class StatusPresenter extends MvpPresenter<StatusView> {

    private ServerAPI serverAPI;
    private UserRepository userRepository;

    @Override
    protected void onFirstViewAttach() {
        userRepository = MyApp.getInstance().getUserRepository();
        serverAPI = MyApp.getServerAPI();
        loadData();
    }


    private void loadData() {
        getViewState().showLoading();
        serverAPI.userInfo(userRepository.getToken()).enqueue(new Callback<StatusData>() {
            @Override
            public void onResponse(Call<StatusData> call, Response<StatusData> response) {
                getViewState().hideLoading();

                if (response.code() == 401) {
                    userRepository.clearAuth();
                    MyApp.getRouter().restart();
                } else if (response.code() == 200) {
                    getViewState().setUserInfo(response.body());
                }
            }

            @Override
            public void onFailure(Call<StatusData> call, Throwable t) {
                getViewState().hideLoading();

                ///unauthorized

                if (t instanceof MalformedJsonException) {
                    userRepository.clearAuth();
                    MyApp.getRouter().restart();
                }
            }
        });
    }
}
