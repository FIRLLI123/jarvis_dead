package com.example.barcode;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Calendar;
import java.util.Timer;

public class AbsenNotificationService extends Service {
    private static final int NOTIFICATION_ID = 1;
    private static final long INTERVAL_HOUR = 3600000; // 1 hour in milliseconds

    private Timer timer;
    private boolean isRunning = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Dapatkan data tambahan (extra) dari Intent
        String namaUser = intent.getStringExtra("userData");
        // Sekarang Anda dapat menggunakan data ini untuk menampilkan notifikasi ke pengguna dengan nama pengguna yang sesuai
        String message = "Halo, " + namaUser + "! Anda telah menerima notifikasi dari layanan.";
        // Panggil metode untuk menampilkan notifikasi
        showNotification("Notifikasi Tes", message);
        return START_STICKY;
    }

    private void showNotification(String title, String message) {
        // Buatkan intent untuk membuka activity saat notifikasi diklik
        Intent intent = new Intent(this, LoginV2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        // Buatkan notifikasi
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "channel_id")
                .setSmallIcon(R.drawable.date2)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        // Dapatkan instance dari NotificationManager
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Periksa jika perangkat sudah menggunakan versi Android Oreo atau di atasnya
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channel_id", "Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        // Tampilkan notifikasi
        notificationManager.notify(0, notificationBuilder.build());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
        isRunning = false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

//    private void checkAbsenAndTime() {
//        // Lakukan pemeriksaan kondisi absen dan waktu di sini
//        // Jika belum ada absen dan sudah lewat jam 16:30, tampilkan notifikasi
//        if (!hasTodayAbsen(jsonArray) && isAfter4_30PM()) {
//            showNotification("Peringatan Absen", "Anda belum melakukan absen hari ini. Silakan lakukan absen.");
//        }
//    }

    private boolean hasTodayAbsen(JSONArray jsonArray) {
        // Dapatkan tanggal hari ini
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Bulan dimulai dari 0
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String todayDate = year + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);

        // Lakukan permintaan ke server atau akses database lokal untuk memeriksa apakah ada absen untuk tanggal hari ini
        // Misalnya, jika Anda memiliki array atau daftar data absen, Anda dapat melakukan iterasi untuk memeriksa keberadaan entri absen untuk tanggal hari ini
        // Mengembalikan true jika ada absen untuk tanggal hari ini, dan false jika tidak
        // Anda perlu menyesuaikan ini dengan cara akses data absen di aplikasi Anda
        return false; // Ganti dengan logika Anda
    }

    private boolean isAfter4_30PM() {
        // Dapatkan waktu saat ini
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY); // Jam dalam format 24 jam
        int minute = calendar.get(Calendar.MINUTE);

        // Periksa apakah sudah setelah jam 16:30
        return hour > 16 || (hour == 16 && minute >= 30);
    }


}

