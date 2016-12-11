package com.likoil.likoilbonus.mvp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.likoil.likoilbonus.R;
import com.likoil.likoilbonus.app.MyApp;
import com.likoil.likoilbonus.mvp.network.StatusData;
import com.likoil.likoilbonus.mvp.presentation.view.StatusView;
import com.likoil.likoilbonus.mvp.presentation.presenter.StatusPresenter;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.likoil.likoilbonus.ui.SCREENS;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatusFragment extends MvpAppCompatFragment implements StatusView {
    public static final String TAG = "StatusFragment";
    @InjectPresenter
    StatusPresenter mStatusPresenter;

    @BindView(R.id.txtBonusCount)
    TextView txtBonusCount;

    @BindView(R.id.txtName)
    TextView txtName;

    @BindView(R.id.btnHistory)
    Button btnHistory;

    @BindView(R.id.progressBar)
    View progressBar;

    public static StatusFragment newInstance() {
        StatusFragment fragment = new StatusFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status, container, false);
        ButterKnife.bind(this, view);

        btnHistory.setOnClickListener(v -> {
            MyApp.getRouter().showFragment(SCREENS.SCREEN_HISTORY, true);
        });

        return view;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
    public void setUserInfo(StatusData statusData) {
        if (statusData == null) {
            return;
        }
        txtBonusCount.setText(statusData.getBonus());
        txtName.setText(String.format(Locale.getDefault(), "%s %s %s", statusData.getInfo().getLast_name(),
                statusData.getInfo().getName(),
                statusData.getInfo().getPatronymic()));
    }
}
