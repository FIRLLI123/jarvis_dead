package com.example.barcode;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

public class ExecutableService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Menampilkan notifikasi
        showNotification();

        // Layanan tidak perlu dijalankan lagi setelah notifikasi ditampilkan
        stopSelf();
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void showNotification() {
        // Buatlah intent untuk membuka Activity setelah notifikasi diklik
        Intent notificationIntent = new Intent(this, TesCoding2.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        // Bangun notifikasi
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                .setSmallIcon(R.drawable.date2)
                .setContentTitle("Your Notification Title")
                .setContentText("Your Notification Message")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Tampilkan notifikasi
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0, builder.build());
    }
}
