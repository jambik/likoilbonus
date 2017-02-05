package com.likoil.likoilbonus.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
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

    private boolean isMenuHidden;

    public boolean isMenuHidden() {
        return isMenuHidden;
    }

    public void setMenuHidden(boolean menuHidden) {
        isMenuHidden = menuHidden;
        invalidateOptionsMenu();
    }

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
            instance = HistoryFragment.newInstance(false);
            showFragment(instance, animated, true);
        } else if (SCREENS.SCREEN_STATUS.equals(fragment)) {
            setMenuHidden(false);
            showFragment(StatusFragment.newInstance(), false, false);
        } else  if (SCREENS.SCREEN_HISTORY_WITHDRAWAL.equals(fragment)) {
            instance = HistoryFragment.newInstance(true);
            showFragment(instance, animated, true);
        }
    }


    private void clearBackStack() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
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
        clearBackStack();
        showFragment(LoginFragment.newInstance(), false, false);
        setMenuHidden(true);
    }

    @Override
    public void showStatus() {
        setMenuHidden(false);
        showFragment(StatusFragment.newInstance(), false, false);
    }

    @Override
    public void restart() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isMenuHidden()) {
            return super.onCreateOptionsMenu(menu);
        }
        getMenuInflater().inflate(R.menu.status_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mExit:
                MyApp.getInstance().getUserRepository().clearAuth();
                restart();
                break;
            case R.id.mBonusLog:
                MyApp.getRouter().showFragment(SCREENS.SCREEN_HISTORY_WITHDRAWAL, true);
                break;
            case R.id.mHistory:
                MyApp.getRouter().showFragment(SCREENS.SCREEN_HISTORY, true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
