package com.example.sanchit.groupsofwhatsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Splash extends AppCompatActivity {


    static int SPLASH_TIME_OUT = 3000;

    UserLocalStore userLocalStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        userLocalStore = new UserLocalStore(Splash.this);
        int a = userLocalStore.getCount();
        System.out.println("valus of a is "+a);
//        Toast.makeText(Splash.this, a, Toast.LENGTH_SHORT).show();


        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {


                if (new Util().check_connection(Splash.this)) {

                    Intent i = new Intent(Splash.this, Home.class);
                    startActivity(i);


                } else {
                    new SweetAlertDialog(Splash.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("No Internent Connection")
                            .setContentText("Won't be able to go in!")
                            .setConfirmText("Go to Settings!")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    startActivity(new Intent(Settings.ACTION_SETTINGS));
                                    sDialog.cancel();
                                }
                            })
                            .show();
                }

            }

        }, SPLASH_TIME_OUT);
    }
}