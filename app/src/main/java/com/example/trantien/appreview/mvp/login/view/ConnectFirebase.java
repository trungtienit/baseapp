package com.example.trantien.appreview.mvp.login.view;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.trantien.appreview.GetMessageResults;
import com.example.trantien.appreview.LoginFirebaseResult;
import com.example.trantien.appreview.PushFirebaseResult;
import com.example.trantien.appreview.SignupFirebaseResult;
import com.example.trantien.appreview.mvp.login.model.Message;
import com.example.trantien.appreview.mvp.login.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ConnectFirebase  {
    DatabaseReference databaseReference;
    Context c;
    HashMap<String, String> hashMap_login = new HashMap<>();

    public ConnectFirebase(Context c) {
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
        this.c = c;

    }

    public void signupWithFacebook(final User login, final SignupFirebaseResult signupFirebaseResult) {


        databaseReference.child("Data").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot users : dataSnapshot.getChildren()) {
                    User temp = new User();
                    temp.setEmail(users.getValue(User.class).getEmail());
                    if (login.getEmail().equals(temp.getEmail())) {
                        Toast.makeText(c, "User have existed!!!", Toast.LENGTH_SHORT).show();
                        signupFirebaseResult.onFailure();
                        return;
                    }

                }
                databaseReference.child("Data").push().setValue(login, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError == null)
                            Toast.makeText(c,"Create Successfull", Toast.LENGTH_SHORT).show();
                        signupFirebaseResult.onSuccess();
                        return;
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public String getMessages( final GetMessageResults getMessageResults) {

        final List<Message>  mList= new ArrayList<>();
        databaseReference.child("Blog").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot m : dataSnapshot.getChildren()) {
                    Message temp = new Message(m.getValue(Message.class).getUserid(),m.getValue(Message.class).getMlat(),m.getValue(Message.class).getMlong());
                    mList.add(temp);
                    if(temp.getUserid()!=null);
                     Log.d("XXXX",temp.getUserid());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return "";
    }
    public void pushNotify(final Message message, final PushFirebaseResult pushFirebaseResult) {


        databaseReference.child("Blog").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                databaseReference.child("Blog").push().setValue(message, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError == null)
                            Toast.makeText(c,"Create Successfull", Toast.LENGTH_SHORT).show();
                        pushFirebaseResult.onSuccess();
                        return;
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void put_hashmap(User x) {
        hashMap_login.put(x.getId_fb(), x.getEmail());
    }

    public void loginWithFacebook(String id, String email, final LoginFirebaseResult loginFirebaseResult) {
        final User login = new User();
        login.setId_fb(id);
        login.setEmail(email);
        databaseReference.child("Data").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User x = new User();
                    x.setId_fb(ds.getValue(User.class).getId_fb());
                    x.setEmail(ds.getValue(User.class).getEmail());
                    put_hashmap(x);
                }

                for (String key : hashMap_login.keySet())
                {
                    if(key.toString().equals(login.getId_fb()) && hashMap_login.get(key).toString().equals(login.getEmail())){

                        loginFirebaseResult.onSuccess();
                        return;
                    }
                }

                loginFirebaseResult.onFailure();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
