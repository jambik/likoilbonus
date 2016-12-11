package com.likoil.likoilbonus.mvp.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.likoil.likoilbonus.mvp.network.StatusData;

public interface StatusView extends MvpView {
    void showLoading();
    void hideLoading();
    void setUserInfo(StatusData statusData);
}
