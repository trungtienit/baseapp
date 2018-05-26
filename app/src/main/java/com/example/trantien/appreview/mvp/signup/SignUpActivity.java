package com.example.trantien.appreview.mvp.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.example.trantien.appreview.MainActivity;
import com.example.trantien.appreview.R;
import com.example.trantien.appreview.SignupFirebaseResult;
import com.example.trantien.appreview.base.AppConstants;
import com.example.trantien.appreview.mvp.login.model.User;
import com.example.trantien.appreview.mvp.login.view.ConnectFirebase;
import com.example.trantien.appreview.utils.MySharedPreferences;
import com.facebook.AccessToken;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.txtFullName)
    EditText tvFullName;

    @BindView(R.id.txtAddress)
    EditText tvAddress;

    @BindView(R.id.txtEmail)
    EditText tvEmail;

    @BindView(R.id.txtPassword)
    EditText tvPassword;

    @BindView(R.id.txtPhoneNumber)
    EditText tvPhonenumber;

    @BindView(R.id.userImage)
    CircleImageView userImage;

    @BindView(R.id.btnSignUp)
    Button btnSignUp;

    MySharedPreferences mySharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        mySharedPreferences= new MySharedPreferences(getBaseContext());
        AccessToken accessToken = AccessToken.getCurrentAccessToken();


        String name = mySharedPreferences.Get("fullname");
        final String email = mySharedPreferences.Get("email");
        final String url = mySharedPreferences.Get("imageURL");
        final String id = mySharedPreferences.Get("id");

        setAvatar(url);
        tvFullName.setText(name);
        tvEmail.setText(email);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user= new User();
                user.setAddress(tvAddress.getText().toString());
                user.setEmail(email);
                user.setId_fb(id);
                user.setPhoneNumber(tvPhonenumber.getText().toString());
                user.setPassword(tvPassword.getText().toString());

                ConnectFirebase connectFirebase = new ConnectFirebase(getBaseContext());
                connectFirebase.signupWithFacebook(user, new SignupFirebaseResult() {
                    @Override
                    public void onSuccess() {
                        direcMain(getWindow().getDecorView().getRootView());
                    }

                    @Override
                    public void onFailure() {

                    }
                });
            }
        });
    }

    private void setAvatar(String id) {
        Glide.with(getBaseContext())
                .load(id)
                .into(userImage);
    }
    public void direcMain(View view) {
        Intent signupIntent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivityForResult(signupIntent, AppConstants.FORM_SIGNUP);
        finish();
    }
}
