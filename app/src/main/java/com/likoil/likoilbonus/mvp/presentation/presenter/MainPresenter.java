package com.likoil.likoilbonus.mvp.presentation.presenter;

import com.likoil.likoilbonus.R;
import com.likoil.likoilbonus.app.MyApp;
import com.likoil.likoilbonus.model.UserRepository;
import com.likoil.likoilbonus.mvp.presentation.view.MainView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    UserRepository userRepository;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        userRepository = MyApp.getInstance().getUserRepository();
    }

    @Override
    public void attachView(MainView view) {
        super.attachView(view);
        if (!userRepository.isAuth()) {
            getViewState().showAuth();
        } else {
            getViewState().showStatus();
        }
    }
}
