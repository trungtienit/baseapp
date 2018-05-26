package com.example.trantien.appreview.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;


public class CheckPermission {

    public static void checkAndRequestPermissionsDefault(Activity activity) {
        String[] permissions = new String[]{
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
        };
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity.getBaseContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
        }
    }

    public static void checkAndRequestPermissions(Activity activity, String str) {
        String[] permissions = new String[]{str};
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity.getBaseContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
        }
    }
}
