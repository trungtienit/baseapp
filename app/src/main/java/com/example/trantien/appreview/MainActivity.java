package com.example.trantien.appreview;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.trantien.appreview.base.AppConstants;
import com.example.trantien.appreview.base.drawer.DrawerActivity;
import com.example.trantien.appreview.mvp.Home.NewsAdapter;
import com.example.trantien.appreview.mvp.Home.NewsModel;
import com.example.trantien.appreview.mvp.login.model.Message;
import com.example.trantien.appreview.mvp.login.view.ConnectFirebase;
import com.example.trantien.appreview.mvp.login.view.LoginActivity;
import com.example.trantien.appreview.mvp.signup.SignUpActivity;
import com.example.trantien.appreview.utils.CheckPermission;
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

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class MainActivity extends DrawerActivity  implements LocationListener {
    NumberFormat format = new DecimalFormat("#0.0");
    LocationManager locationManager;
    private boolean canGetLocation;
    private Location location;
    private ListView listView;
    MySharedPreferences mySharedPreferences;
    public String email="admin@gmail.com";
    public String password="112233";

    private List<NewsModel> list;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        Button button = findViewById(R.id.btnSos);
        CheckPermission.checkAndRequestPermissionsDefault(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPost();
                Location l= getLocation();
               MySharedPreferences mySharedPreferences= new MySharedPreferences(getBaseContext());
                Message m=new Message(mySharedPreferences.Get("id"),l.getLatitude()+"",l.getLongitude() +"");
                Log.d("XAX",mySharedPreferences.Get("id"));
                ConnectFirebase connectFirebase = new ConnectFirebase(getBaseContext());
                connectFirebase.pushNotify(m, new PushFirebaseResult() {
                    @Override
                    public void onSuccess() {
                        showToast("Success");
                    }

                    @Override
                    public void onFailure() {

                    }
                });
                int a=0;

            }
        });
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


        this.getData();

        this.listView = (ListView)findViewById(R.id.listNews);

        this.listView.setAdapter(new NewsAdapter(list, this));

            setInfor(mySharedPreferences.Get("imageURL"),mySharedPreferences.Get("fullname"));
    }

    private void getData(){
        this.list = new ArrayList<>();
        this.list.add(new NewsModel("news_1", "Bạo lực học đường: Một tháng 6 lần 'yêu cầu xử lý nghiêm'!", "https://thanhnien.vn/giao-duc/bao-luc-hoc-duong-mot-thang-6-lan-yeu-cau-xu-ly-nghiem-949624.html"));
        this.list.add(new NewsModel("news_2", "Bạo lực học đường: Trách nhiệm đầu tiên thuộc về ai?", "https://news.zing.vn/bao-luc-hoc-duong-trach-nhiem-dau-tien-thuoc-ve-ai-post791305.html"));
        this.list.add(new NewsModel("news_3", "Vì sao bạo lực học đường ở Việt Nam bùng nổ lớn?", "https://chantroimoimedia.com/2018/03/22/vi-sao-bao-luc-hoc-duong-o-viet-nam-bung-no-lon/"));
        this.list.add(new NewsModel("news_4", "Bạo lực học đường: Đừng chịu đựng một mình", "http://dantri.com.vn/giao-duc-khuyen-hoc/bao-luc-hoc-duong-dung-chiu-dung-mot-minh-20180203121623315.htm"));
        this.list.add(new NewsModel("news_5", "Tăng cường đối thoại để phát hiện sớm bạo lực học đường", "https://tuoitre.vn/tang-cuong-doi-thoai-de-phat-hien-som-bao-luc-hoc-duong-2018040510513018.htm"));

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


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    public android.location.Location getLocation() {
        int MIN_TIME_BW_UPDATES = 10000;
        int MIN_DISTANCE_CHANGE_FOR_UPDATES = 10000;

        try {
            locationManager = (LocationManager) getApplicationContext()
                    .getSystemService(LOCATION_SERVICE);

            boolean isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            boolean isPassiveEnabled = locationManager
                    .isProviderEnabled(LocationManager.PASSIVE_PROVIDER);

            boolean isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (isGPSEnabled || isNetworkEnabled || isPassiveEnabled) {

                this.canGetLocation = true;
                if (isNetworkEnabled && location == null) {
                    if (ActivityCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    }
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network Enabled");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled && location == null) {

                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("GPS", "GPS Enabled");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }
                }
                if (isPassiveEnabled && location == null) {
                    locationManager.requestLocationUpdates(
                            LocationManager.PASSIVE_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network Enabled");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                    }
                }


            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }
    public void sendPost() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://fcm.googleapis.com/fcm/send");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Authorization","key=AAAA7SSj5vY:APA91bHw-nxGqZhDkhkrZD8I6jciRH9elGQrhJBTPWC9UbM4j0xrdSUzWbKZU6GGb52WnLW3Sl_TQ2r5yk-Cb8zvvtGfG2yRYGO_pJaoRCx-ehWhMxVhai34HYJJ7M-te0v39rzKJ7Vi");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject s = new JSONObject();
                    s.put("message", "This is a Firebase Cloud Messaging Topic Message!");
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("to", "/topics/news");
                    jsonParam.put("data", s);

                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();

                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG" , conn.getResponseMessage());

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
}
