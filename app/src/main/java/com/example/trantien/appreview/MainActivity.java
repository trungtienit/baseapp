package com.example.trantien.appreview;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.trantien.appreview.base.AppConstants;
import com.example.trantien.appreview.base.drawer.DrawerActivity;
import com.example.trantien.appreview.mvp.Home.NewsAdapter;
import com.example.trantien.appreview.mvp.Home.NewsModel;
import com.example.trantien.appreview.mvp.login.view.ConnectFirebase;
import com.example.trantien.appreview.mvp.login.view.LoginActivity;
import com.example.trantien.appreview.mvp.signup.SignUpActivity;
import com.example.trantien.appreview.utils.MySharedPreferences;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class MainActivity extends DrawerActivity {

    private ListView listView;
    MySharedPreferences mySharedPreferences;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        mySharedPreferences= new MySharedPreferences(getBaseContext());
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (mySharedPreferences.Get("fullname").equals("NULL")) {
            direcLogin(getWindow().getDecorView().getRootView());
        }else
            setAvatar(mySharedPreferences.Get("imageURL"));

        List<NewsModel> list = new ArrayList<>();
        list.add(new NewsModel(null, "Đại biểu Quốc hội: Dẹp nạn bạo hành trẻ em là việc khẩn cấp"));
        list.add(new NewsModel(null, "Ấn Độ: Biểu tình biên thành bạo lực, 9 người chết"));

        this.listView = (ListView)findViewById(R.id.listNews);

        this.listView.setAdapter(new NewsAdapter(list, this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        showToast("CCCC");
        showToast(requestCode+"");
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case AppConstants.FORM_LOGIN:
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
                                    mySharedPreferences.Save("email",email);
                                    mySharedPreferences.Save("imageURL",imageURL.toString());
//                                    setAvatar(mySharedPreferences.Get("imageURL"));
                                    ConnectFirebase connectFirebase = new ConnectFirebase(getBaseContext());
                                    connectFirebase.loginWithFacebook(id, email, new LoginFirebaseResult() {
                                        @Override
                                        public void onSuccess() {
                                            showToast( "Login successful!!!");
                                            Log.d("XX","Login successful!!!");
                                        }

                                        @Override
                                        public void onFailure() {
                                            direcSignup(getWindow().getDecorView().getRootView());
                                            showToast("Account does not exist!");
                                            Log.d("XX","Account does not exist!");
                                        }
                                    });
                                    hideLoading();
                                }
                            });
                    Bundle parameters = new Bundle();
                    parameters.putString(getString(R.string.fields), getString(R.string.fields_name));
                    request.setParameters(parameters);
                    request.executeAsync();
                }
                 break;
                default:
                    break;
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

    public void direcLogin(View view) {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivityForResult(loginIntent, AppConstants.FORM_LOGIN);
    }
    public void direcSignup(View view) {
        Intent signupIntent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivityForResult(signupIntent, AppConstants.FORM_LOGIN);
    }
}
