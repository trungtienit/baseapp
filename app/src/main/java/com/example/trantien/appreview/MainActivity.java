package com.example.trantien.appreview;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.example.trantien.appreview.base.AppConstants;
import com.example.trantien.appreview.base.drawer.DrawerActivity;
import com.example.trantien.appreview.mvp.login.view.ConnectFirebase;
import com.example.trantien.appreview.mvp.login.view.LoginActivity;
import com.example.trantien.appreview.mvp.signup.SignUpActivity;
import com.example.trantien.appreview.utils.MySharedPreferences;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;

import java.net.URL;

import butterknife.ButterKnife;

public class MainActivity extends DrawerActivity {
    MySharedPreferences mySharedPreferences;
    public String email="admin@gmail.com";
    public String password="112233";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        FirebaseMessaging.getInstance().subscribeToTopic("news");

         final FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                           // Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                           // Log.w(TAG, "signInWithEmail:failure", task.getException());
                       //     Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                  //  Toast.LENGTH_SHORT).show();
                           // updateUI(null);
                        }

                        // ...
                    }
                });

        mySharedPreferences= new MySharedPreferences(getBaseContext());
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (mySharedPreferences.Get("fullname").equals("NULL")) {
            direcLogin(getWindow().getDecorView().getRootView());
        }else
            setInfor(mySharedPreferences.Get("imageURL"),mySharedPreferences.Get("fullname"));
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
                                    setInfor(imageURL.toString(),name);
//                                    setName(name);
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

    public void direcHome(View view) {
        Intent signupIntent = new Intent(MainActivity.this, HomeActivity.class);
       startActivity(signupIntent);
    }
}
