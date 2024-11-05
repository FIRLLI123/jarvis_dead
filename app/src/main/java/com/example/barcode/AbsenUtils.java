package com.example.barcode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Calendar;

public class AbsenUtils {

    public static boolean hasTodayAbsen(JSONArray jsonArray) {
        // Dapatkan tanggal hari ini
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Bulan dimulai dari 0
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String todayDate = year + "/" + String.format("%02d", month) + "/" + String.format("%02d", day);

        // Memeriksa setiap entri absen untuk tanggal hari ini
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject responses = jsonArray.getJSONObject(i);
                    String tanggalAbsen = responses.optString("tanggal");
                    // Jika ada entri absen untuk tanggal hari ini, kembalikan true
                    if (tanggalAbsen.equals(todayDate)) {
                        return true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        // Jika tidak ada entri absen untuk tanggal hari ini, kembalikan false
        return false;
    }

    public static boolean isAfter4_30PM() {
        // Dapatkan waktu saat ini
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY); // Jam dalam format 24 jam
        int minute = calendar.get(Calendar.MINUTE);

        // Periksa apakah sudah setelah jam 16:30
        return hour > 16 || (hour == 16 && minute >= 30);
    }
}

