package com.likoil.likoilbonus.mvp.presentation.presenter;

import com.likoil.likoilbonus.R;
import com.likoil.likoilbonus.app.MyApp;
import com.likoil.likoilbonus.model.UserRepository;
import com.likoil.likoilbonus.mvp.network.LoginData;
import com.likoil.likoilbonus.mvp.presentation.view.LoginView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@InjectViewState
public class LoginPresenter extends MvpPresenter<LoginView> {

    UserRepository userRepository = MyApp.getInstance().getUserRepository();

    public void doLogin(String login, String password) {
        getViewState().showLoading();
        MyApp.getServerAPI().login(login, password).enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                getViewState().hideLoading();
                if (response.code() == 200 && response.body().api_token != null) {
                    userRepository.saveAuth(response.body().api_token);
                    getViewState().showSuccess();
                } else {
                    getViewState().showError(response.code() == 401 ? "Неправильные данные" : "Неизвестная ошибка");
                }
            }

            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {
                getViewState().hideLoading();
                getViewState().showError(t.getLocalizedMessage());
            }
        });
    }
}
