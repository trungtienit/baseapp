package com.example.trantien.appreview.utils;

import android.content.Context;

import com.example.trantien.appreview.widget.DialogProgress;

public class DialogUtils {

    public static DialogProgress showProgressDialog(Context context) {
        DialogProgress progressDialog = new DialogProgress(context);
        progressDialog.show();
        return progressDialog;
    }

}