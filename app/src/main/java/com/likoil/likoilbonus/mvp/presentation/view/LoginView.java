package com.likoil.likoilbonus.mvp.presentation.view;

import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;

public interface LoginView extends MvpView {
    void showError(String error);
    void showError(@StringRes int error);
    void showLoading();
    void hideLoading();
    void showSuccess();
}
