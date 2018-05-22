package com.example.trantien.reviewapp.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.trantien.reviewapp.utils.DialogUtils;
import com.example.trantien.reviewapp.widget.DialogProgress;

public class BaseActivity extends AppCompatActivity implements IActivityListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (isTransparentStatusBar()) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    // progress dialog
    private DialogProgress mProgressDialog;
    protected void dismissDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
    // end progress dialog
    //region listener

    @Override
    public void showLoading() {
        dismissDialog();
        mProgressDialog = DialogUtils.showProgressDialog(this);
    }

    @Override
    public void hideLoading() {
        dismissDialog();
    }
    //endregion
    public boolean isTransparentStatusBar() {
        return false;
    }
    //Toast
    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // Keyboard utils
    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void showKeyboard() {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
    // End keyboard utils

    protected boolean checkPermissions(String[] permissions) {
        for (String s : permissions) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), s) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }
}
