package gr.museum.app.museumapp;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.List;
import java.util.UUID;

/**
 * Created by barbarosa on 24/4/2016.
 */
public class MyApplication extends Application {

    private BeaconManager beaconManager;


    @Override
    public void onCreate() {
        super.onCreate();
        beaconManager = new BeaconManager(getApplicationContext());


    }


    public void saveLogin(String username,String password) {
        SharedPreferences settings;
        settings = getSharedPreferences("MUSEUM_APP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();
    }

    public void logOut (){
        SharedPreferences settings;
        settings = getSharedPreferences("MUSEUM_APP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("username", null);
        editor.putString("password", null);
        editor.apply();
    }

    public boolean isUserLogedIn(){
        SharedPreferences settings;
        settings = getSharedPreferences("MUSEUM_APP", Context.MODE_PRIVATE);
        String username=settings.getString("username",null);
        String password=settings.getString("password",null);
        if(username!=null && password!=null){
            return true;
        }else {
            return false;
        }
    }

    public BeaconManager getBeaconManager() {
        return beaconManager;
    }


    public void showNotification(String title, String message) {
        Intent notifyIntent = new Intent(this, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[]{notifyIntent}, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }


}
