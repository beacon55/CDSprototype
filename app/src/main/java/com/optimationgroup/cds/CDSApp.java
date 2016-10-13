package com.optimationgroup.cds;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.List;
import java.util.UUID;

/**
 * Created by dave on 1/10/16.
 */

public class CDSApp extends Application {

    // BeaconManager is the basis for beacon monitoring
    private BeaconManager beaconManager;

    @Override
    public void onCreate() {
        super.onCreate();

        beaconManager = new BeaconManager(getApplicationContext());

        // five second scan for beacons, at 55 second intervals... (scanning every minute)
        beaconManager.setBackgroundScanPeriod(5000L, 55000L);

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startMonitoring(new Region(
                        "prototype monitored region",
                        UUID.fromString(ProtoBeacons.blueberryUUID),
                        ProtoBeacons.majorNumber, ProtoBeacons.blueBerryMinor));
            }
        });

        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {
                showNotification(
                        "Enjoy your swim at Glacial Pool!",
                        "" );
            }

            @Override
            public void onExitedRegion(Region region) {
                showNotification(
                "Tell us about your swim!",
                        "Thanks for visiting Glacial Pool.  Please tell us about your experience _here_ (and receive $10 Alex dollars!)" );
                // could add an "exit" notification too if you want (-:
            }
        });


    }


    // helper method
    public void showNotification(String title, String message) {
        Intent notifyIntent = new Intent(this, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[] { notifyIntent }, PendingIntent.FLAG_UPDATE_CURRENT);
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
