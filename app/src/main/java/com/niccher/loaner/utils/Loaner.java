package com.niccher.loaner.utils;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

public class Loaner extends Application {
    public static final String ch_ID = "channel1";

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        Picasso.Builder bul=new Picasso.Builder(this);
        bul.downloader(new OkHttp3Downloader(this,Integer.MAX_VALUE));
        Picasso buil=bul.build();
        buil.setIndicatorsEnabled(false);
        buil.setLoggingEnabled(true);
        Picasso.setSingletonInstance(buil);

        Notifiers();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void Notifiers() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel ch = new NotificationChannel(
                    ch_ID,
                    "Channel 01",
                    NotificationManager.IMPORTANCE_HIGH
            );

            ch.canShowBadge();
            ch.setDescription("This is dope");

            NotificationManager man = getSystemService(NotificationManager.class);
            man.createNotificationChannel(ch);
        }

    }
}
