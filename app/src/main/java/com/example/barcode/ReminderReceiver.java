package com.example.barcode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ReminderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Logika untuk menampilkan notifikasi pengingat
        NotificationHelper.showNotification(context, "Waktu Absen!", "Waktunya untuk melakukan absen.");
    }
}
