package com.likoil.likoilbonus.mvp.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.likoil.likoilbonus.mvp.network.DiscountData;

import java.util.List;

public interface HistoryView extends MvpView {
    void showLoading();
    void hideLoading();
    void setData(DiscountData data);
    void showError(String error);
}
