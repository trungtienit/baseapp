package com.example.trantien.reviewapp.utils;

import android.content.Context;

import com.example.trantien.reviewapp.widget.DialogProgress;

public class DialogUtils {

    public static DialogProgress showProgressDialog(Context context) {
        DialogProgress progressDialog = new DialogProgress(context);
        progressDialog.show();
        return progressDialog;
    }

}