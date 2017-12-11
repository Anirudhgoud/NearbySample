package com.cybrilla.assignment.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.cybrilla.assignment.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Anirudh on 12/11/2017.
 */

public class BaseActivity extends AppCompatActivity {

    int titleResId;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    private Toolbar toolbar;
    private int layoutResId;

    public BaseActivity(int layoutResId, int titleResId) {
        this.layoutResId = layoutResId;
        this.titleResId = titleResId;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutResId);
        ButterKnife.bind(this);

        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            initToolbar();
        }
    }

    private void initToolbar() {
        mTvTitle.setText(titleResId);
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}




