package com.example.trantien.reviewapp.mvp.login.presenter;

import com.example.trantien.reviewapp.mvp.login.model.User;
import com.example.trantien.reviewapp.mvp.login.view.ILoginView;

public class LoginPresenter implements ILoginPresenter{

    private ILoginView view;
    private User model;

    public LoginPresenter(ILoginView view, User model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }
}
