package com.likoil.likoilbonus.mvp.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.likoil.likoilbonus.R;
import com.likoil.likoilbonus.app.MyApp;
import com.likoil.likoilbonus.mvp.presentation.presenter.LoginPresenter;
import com.likoil.likoilbonus.mvp.presentation.view.LoginView;
import com.likoil.likoilbonus.ui.SCREENS;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginFragment extends MvpAppCompatFragment implements LoginView {
    public static final String TAG = "LoginFragment";
    @InjectPresenter
    LoginPresenter mLoginPresenter;

    @BindView(R.id.edLogin)
    EditText edLogin;

    @BindView(R.id.edPassword)
    EditText edPassword;

    @BindView(R.id.btnLogin)
    Button btnLogin;

    @BindView(R.id.progressBar)
    View progressBar;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        btnLogin.setOnClickListener(v -> {
            mLoginPresenter.doLogin(edLogin.getText().toString(), edPassword.getText().toString());
        });
        return view;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError(@StringRes int error) {
        showError(getContext().getString(error));
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showSuccess() {
        Toast.makeText(getContext(), "Вход выполнен", Toast.LENGTH_LONG).show();
        new Handler().postDelayed(() -> MyApp.getRouter().showFragment(SCREENS.SCREEN_STATUS, false), 1000);
    }
}
