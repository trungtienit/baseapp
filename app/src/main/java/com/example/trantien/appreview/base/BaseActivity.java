package com.example.trantien.appreview.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity implements IActivityListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    //region listener
    @Override
    public void showBarLoading() {
    }

    @Override
    public void hideBarLoading() {
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {

    }
    //endregion
}
