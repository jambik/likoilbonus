package com.likoil.likoilbonus.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.likoil.likoilbonus.app.MyApp;
import com.likoil.likoilbonus.model.UserRepository;
import com.likoil.likoilbonus.mvp.presentation.view.MainView;
import com.likoil.likoilbonus.mvp.presentation.presenter.MainPresenter;


import com.likoil.likoilbonus.R;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.likoil.likoilbonus.mvp.ui.fragment.HistoryFragment;
import com.likoil.likoilbonus.mvp.ui.fragment.LoginFragment;
import com.likoil.likoilbonus.mvp.ui.fragment.StatusFragment;
import com.likoil.likoilbonus.ui.IRouter;
import com.likoil.likoilbonus.ui.SCREENS;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends MvpAppCompatActivity implements MainView, IRouter, FragmentManager.OnBackStackChangedListener {
    public static final String TAG = "MainActivity";
    @InjectPresenter
    MainPresenter mMainPresenter;

    @BindView(R.id.frameContainer)
    FrameLayout frameContainer;

    public static Intent getIntent(final Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        MyApp.setRouter(this);
        getSupportFragmentManager().addOnBackStackChangedListener(this);
        shouldDisplayHomeUp();
    }

    @Override
    public void onBackStackChanged() {
        shouldDisplayHomeUp();
    }

    public void shouldDisplayHomeUp(){
        //Enable Up button only  if there are entries in the back stack
        boolean canback = getSupportFragmentManager().getBackStackEntryCount()>0;
        getSupportActionBar().setDisplayHomeAsUpEnabled(canback);
    }

    @Override
    public boolean onSupportNavigateUp() {
        //This method is called when the up button is pressed. Just the pop back stack.
        getSupportFragmentManager().popBackStack();
        return true;
    }

    @Override
    public void showFragment(String fragment, boolean animated) {
        MvpAppCompatFragment instance = null;
        if (SCREENS.SCREEN_HISTORY.equals(fragment)) {
            instance = HistoryFragment.newInstance();
            showFragment(instance, animated, true);
        } else if (SCREENS.SCREEN_STATUS.equals(fragment)) {
            showFragment(StatusFragment.newInstance(), false, false);
        }
    }

    private void showFragment(MvpAppCompatFragment fragment, boolean anim, boolean backstack) {
        if (fragment == null) {
            return;
        }
        FragmentTransaction add = getSupportFragmentManager().beginTransaction()
                .add(R.id.frameContainer, fragment);
        if (backstack) {
            add = add.addToBackStack(null);
        }
        add.commit();
    }

    @Override
    public void showAuth() {
        showFragment(LoginFragment.newInstance(), false, false);
    }

    @Override
    public void showStatus() {
        showFragment(StatusFragment.newInstance(), false, false);
    }
}
