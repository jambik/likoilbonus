package com.likoil.likoilbonus.ui;

import com.arellomobile.mvp.MvpAppCompatFragment;

/**
 * Created by jambi on 11.12.2016.
 */

public interface IRouter {
    void showFragment(String fragment, boolean animated);
    void restart();
}
