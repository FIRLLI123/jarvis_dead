package com.example.barcode;



import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;


public class ReminderService extends IntentService {

    public ReminderService() {
        super("ReminderService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            // Logika untuk membuat dan menampilkan notifikasi pengingat
            sendNotification();
        }
    }

    private void sendNotification() {
        // Logika untuk membuat dan menampilkan notifikasi
        // Anda dapat menggunakan NotificationCompat untuk kompatibilitas mundur

        // Buat saluran notifikasi jika perangkat menjalankan Android Oreo atau di atasnya
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "Default Channel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        Notification.Builder builder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            builder = new Notification.Builder(this, "default")
                    .setContentTitle("Waktu Absen!")
                    .setContentText("Waktunya untuk melakukan absen.")
                    .setSmallIcon(R.drawable.date2);
        }

        NotificationManager notificationManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            notificationManager = getSystemService(NotificationManager.class);
        }
        notificationManager.notify(0, builder.build());
    }
}
