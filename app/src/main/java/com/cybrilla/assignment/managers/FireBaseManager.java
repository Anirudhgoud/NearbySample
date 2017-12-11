package com.cybrilla.assignment.managers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.cybrilla.assignment.R;
import com.cybrilla.assignment.interfaces.FireBaseAuthListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Anirudh on 10-12-2017.
 */

public class FireBaseManager {
    private static FireBaseManager ourInstance;
    private FirebaseAuth firebaseAuth;
    private FireBaseAuthListener mAuthListener;

    private FireBaseManager() {
        if (firebaseAuth == null) {
            firebaseAuth = FirebaseAuth.getInstance();
        }
    }

    public static FireBaseManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new FireBaseManager();
        }
        return ourInstance;
    }

    public void signInToFireBase(final Context context, String email, String password) {
        mAuthListener.showProgress();
        if (TextUtils.isEmpty(email)) {
            mAuthListener.authMessage(context.getString(R.string.please_enter_valid_email));
            mAuthListener.hideProgress();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            mAuthListener.authMessage(context.getString(R.string.please_enter_valid_password));
            mAuthListener.hideProgress();
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mAuthListener.hideProgress();
                if (task.isSuccessful()) {
                    mAuthListener.onLoginSuccess();
                } else {
                    mAuthListener.authMessage(context.getString(R.string.invalid_login));
                }
            }
        });
    }

    public void setAuthListener(FireBaseAuthListener mAuthListener) {
        this.mAuthListener = mAuthListener;
    }

    public void onDestroy() {
        if (ourInstance != null) {
            ourInstance = null;
        }
        if (mAuthListener != null) {
            mAuthListener = null;
        }
    }
}
