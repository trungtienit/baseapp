package com.example.trantien.appreview.mvp.login.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trantien.appreview.R;

public class LoginWithAccount extends AppCompatActivity {

    private EditText txtUserName;
    private EditText txtPassword;
    private Button btnButton;
    private TextView txtForgotPassword;
    private TextView txtCreateNewAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_account);

        this.getControl();
        this.createEvent();
    }

    private void getControl(){
        this.txtUserName = findViewById(R.id.txtUserName);
        this.txtPassword = findViewById(R.id.txtPassWord);
        this.btnButton = findViewById(R.id.btnLogin);
        this.txtForgotPassword = findViewById(R.id.txtForgotPassword);
        this.txtCreateNewAccount = findViewById(R.id.txtCreateNewAccount);
    }

    private void createEvent(){
        this.btnButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Login button clicked", Toast.LENGTH_SHORT).show();
            }
        });


        this.txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "FogotPassword clicked", Toast.LENGTH_SHORT).show();
            }
        });

        this.txtCreateNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Create New Account clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
