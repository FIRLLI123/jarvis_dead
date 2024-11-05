package com.example.barcode;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.text.BreakIterator;
import java.util.Calendar;

public class BackgroundService extends Service {

    private static final long INTERVAL = 2 * 60 * 1000; // Interval: 2 menit
    private BreakIterator in;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Jalankan metode untuk mengecek waktu dan menampilkan notifikasi jika diperlukan
        checkAndNotify();
        // Atur alarm untuk menjalankan layanan kembali di masa depan
        scheduleNextAlarm();
        // Service akan dijalankan kembali jika dihentikan oleh sistem
        return START_STICKY;
    }

    private void checkAndNotify() {
        // Mendapatkan waktu saat ini
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Cek jika waktu adalah setelah jam 12 siang dan in = "--:--"
        String waktuAbsen = in.getText().toString();
        if (waktuAbsen.equals("--:--") && (hourOfDay > 12 || (hourOfDay == 12 && minute >= 0))) {
            // Tampilkan notifikasi
            showNotification("Waktunya Absen", "Saatnya untuk absen sekarang!");
        }
    }

    private void showNotification(String title, String message) {
        // Tampilkan notifikasi di sini
    }

    private void scheduleNextAlarm() {
        // Atur alarm untuk menjalankan layanan kembali di masa depan
        // Misalnya, gunakan AlarmManager untuk menjadwalkan layanan ini untuk dijalankan kembali setelah INTERVAL
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Metode ini tidak digunakan karena kita tidak akan mengikat layanan ke aktivitas
        return null;
    }
}
