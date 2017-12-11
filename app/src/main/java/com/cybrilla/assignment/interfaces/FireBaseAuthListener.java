package com.cybrilla.assignment.interfaces;

/**
 * Created by Anirudh on 10-12-2017.
 */

public interface FireBaseAuthListener {

    void hideProgress();

    void showProgress();

    void authMessage(String msg);

    void onLoginSuccess();
}
