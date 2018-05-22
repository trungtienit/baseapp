package com.example.trantien.reviewapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;

import com.example.trantien.reviewapp.base.drawer.DrawerActivity;
import com.example.trantien.reviewapp.mvp.login.view.LoginActivity;
import com.example.trantien.reviewapp.utils.MySharedPreferences;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONObject;

import java.net.URL;

import butterknife.ButterKnife;

public class MainActivity extends DrawerActivity {
    MySharedPreferences mySharedPreferences;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mySharedPreferences= new MySharedPreferences(getBaseContext());
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
//        Profile profile = Profile.getCurrentProfile();
        if (mySharedPreferences.Get("fullname").equals("NULL")) {
            DirecLogin(getWindow().getDecorView().getRootView());
        }else
            setAvatar(mySharedPreferences.Get("imageURL"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        showToast("CCCC");
        showToast(requestCode+"");
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            showLoading();
            GraphRequest request = GraphRequest.newMeRequest(
                    AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object,
                                                GraphResponse response) {
                            // Application code
                            String name = object.optString(getString(R.string.name));
                            String id = object.optString(getString(R.string.id));
                            String email = object.optString(getString(R.string.email));
                            String link = object.optString(getString(R.string.link));
                            URL imageURL = extractFacebookIcon(id);
                            Log.d("namezz: ",name);
                            Log.d("idzz: ",id);
                            Log.d("emailzz: ",email);
                            Log.d("link: ",link);
                            Log.d("imageURL: ",imageURL.toString());

                            mySharedPreferences.Save("fullname",name);
                            mySharedPreferences.Save("id",id);
                            mySharedPreferences.Save("imageURL",imageURL.toString());
                            setAvatar(mySharedPreferences.Get("imageURL"));
                            hideLoading();
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString(getString(R.string.fields), getString(R.string.fields_name));
            request.setParameters(parameters);
            request.executeAsync();
        }
    }
    public URL extractFacebookIcon(String id) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

            URL imageURL = new URL("http://graph.facebook.com/" + id
                    + "/picture?type=large");
            return imageURL;
        } catch (Throwable e) {
            return null;
        }
    }

    public void DirecLogin(View view) {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivityForResult(loginIntent,10);
    }
}
