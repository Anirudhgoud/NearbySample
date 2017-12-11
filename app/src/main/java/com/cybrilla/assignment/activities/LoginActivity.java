package com.cybrilla.assignment.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cybrilla.assignment.R;
import com.cybrilla.assignment.interfaces.FireBaseAuthListener;
import com.cybrilla.assignment.managers.FireBaseManager;
import com.cybrilla.assignment.util.GeneralUtil;
import com.cybrilla.assignment.util.LogUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Anirudh on 10-12-2017.
 */

public class LoginActivity extends AppCompatActivity implements FireBaseAuthListener {

    @BindView(R.id.etEmail)
    EditText mEtEmail;

    @BindView(R.id.etPassword)
    EditText mEtPassword;

    @BindView(R.id.btnLogin)
    Button mBtLogin;

    @BindView(R.id.progressLoading)
    View mProgressLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initUi();
    }

    private void initUi() {
        FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();
        if (auth != null) {
            startActivity(new Intent(this, HomeScreenActivity.class));
            finish();
        } else {
            FireBaseManager.getInstance().setAuthListener(this);
        }

    }

    /**
     * login api call to validate the details entered
     */
    @OnClick(R.id.btnLogin)
    public void validateLogin() {
        String email = mEtEmail.getText().toString();
        String password = mEtPassword.getText().toString();
        if (GeneralUtil.isNetworkAvailable(this)) {
            FireBaseManager.getInstance().signInToFireBase(this, email, password);
        } else {
            LogUtil.showToast(this, getString(R.string.please_enable_internet));
        }
    }

    /**
     * Hide loading progress after response is recieved
     */
    @Override
    public void hideProgress() {
        mProgressLoading.setVisibility(View.GONE);
    }

    /**
     * Show progress loading during api call
     */
    @Override
    public void showProgress() {
        mProgressLoading.setVisibility(View.VISIBLE);
    }


    @Override
    public void authMessage(String msg) {
        LogUtil.showToast(this, msg);
    }

    /**
     * Handles login success
     */
    @Override
    public void onLoginSuccess() {
        LogUtil.showToast(this, getString(R.string.login_success));
        startActivity(new Intent(this, HomeScreenActivity.class));
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        FireBaseManager.getInstance().onDestroy();
    }
}
