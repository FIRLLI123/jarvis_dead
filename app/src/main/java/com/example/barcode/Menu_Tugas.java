package com.example.barcode;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.barcode.Data.Data_BayarEX;
import com.example.barcode.helper.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/*
import id.coretech.printerproject.API.Server;
import id.coretech.printerproject.Adapter.Adapter_Bayar;
import id.coretech.printerproject.App.AppController;
import id.coretech.printerproject.Data.Data_Bayar;
import id.coretech.printerproject.Until.BluetoothHandler;
import id.coretech.printerproject.Until.PrinterCommands;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
**/

import com.example.barcode.Until.BluetoothHandler;
import com.example.barcode.Until.PrinterCommands;
import com.zj.btsdk.BluetoothService;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class Menu_Tugas extends AppCompatActivity {
TextView jumlah_pending_perdana, jumlah_pending_voucher;
LinearLayout perdana, voucher;

TextView namasales, tanggal_awal, tanggal_akhir, tanggal_sebelumnya;


    private ProgressDialog pDialog;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_tugas);

        context = Menu_Tugas.this;
        pDialog = new ProgressDialog(context);

        jumlah_pending_perdana = (TextView) findViewById(R.id.jumlah_pending_perdana);
        jumlah_pending_voucher = (TextView) findViewById(R.id.jumlah_pending_voucher);

        perdana = (LinearLayout) findViewById(R.id.perdana);
        voucher = (LinearLayout) findViewById(R.id.voucher);

        tanggal_awal = (TextView) findViewById(R.id.tanggal_awal);
        tanggal_akhir = (TextView) findViewById(R.id.tanggal_akhir);
        tanggal_sebelumnya = (TextView) findViewById(R.id.tanggal_sebelumnya);

        namasales = (TextView) findViewById(R.id.namasales);

        tanggal_awal.setText(getStartOfMonth());
        tanggal_akhir.setText(getCurrentDate());
        tanggal_sebelumnya.setText(getYesterdayDate());


        Intent i = getIntent();
        String kiriman3 = i.getStringExtra("namasales");
        namasales.setText(kiriman3);

        t_pending();
        t_pending_voucher();


        perdana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String a = namasales.getText().toString();
                Intent i = new Intent(getApplicationContext(), Tugas.class);
                //Intent i = new Intent(getApplicationContext(), Menuutamanew.class);
                i.putExtra("namasales",""+a+"");

                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


                //hari_pending();

            }
        });


        voucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String a = namasales.getText().toString();
                Intent i = new Intent(getApplicationContext(), Tugas_Voucher.class);
                //Intent i = new Intent(getApplicationContext(), Menuutamanew.class);
                i.putExtra("namasales",""+a+"");

                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                //hari_pending();

            }
        });

    }





    public String getCurrentDay() {
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", new Locale("id", "ID")); // EEEE untuk nama hari lengkap
        return dayFormat.format(c.getTime());
    }

    public String getCurrentMonth() {
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", new Locale("id", "ID")); // MMMM untuk nama bulan lengkap
        return monthFormat.format(c.getTime());
    }

    public String getCurrentYear() {
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        return yearFormat.format(c.getTime());
    }


    public String getStartOfMonth() {
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1); // Mengatur hari menjadi hari pertama bulan ini
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", new Locale("id", "ID")); // Format tanggal dalam bahasa Indonesia
        return dateFormat.format(c.getTime());
    }

    public String getCurrentDate() {
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", new Locale("id", "ID")); // Format tanggal dalam bahasa Indonesia
        return dateFormat.format(c.getTime());
    }


    public String getYesterdayDate() {
        final Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, -1); // Mengurangi satu hari dari tanggal saat ini
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", new Locale("id", "ID")); // Format tanggal dalam bahasa Indonesia
        return dateFormat.format(c.getTime());
    }


    private void t_pending() {

        AndroidNetworking.post(Config.host + "count_tugas_pending.php")
                .addBodyParameter("namasales", namasales.getText().toString())
                .addBodyParameter("tanggaldari", tanggal_awal.getText().toString())
                .addBodyParameter("tanggalsebelumnya", tanggal_sebelumnya.getText().toString())
                .addBodyParameter("tanggalsampai", tanggal_akhir.getText().toString())

//                .addBodyParameter("namasales", namasalesinputperdana1.getText().toString())
//                .addBodyParameter("tanggal", tanggalinputperdana1.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response



                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);

                        jumlah_pending_perdana.setText((response.optString("tugas_hari_ini")));


                    }

                    @Override
                    public void onError(ANError error) {

                    }




                });

    }




    private void t_pending_voucher() {

        AndroidNetworking.post(Config.host + "count_tugas_pending_voucher.php")
                .addBodyParameter("namasales", namasales.getText().toString())
                .addBodyParameter("tanggaldari", tanggal_awal.getText().toString())
                .addBodyParameter("tanggalsebelumnya", tanggal_sebelumnya.getText().toString())
                .addBodyParameter("tanggalsampai", tanggal_akhir.getText().toString())
//                .addBodyParameter("namasales", namasalesinputperdana1.getText().toString())
//                .addBodyParameter("tanggal", tanggalinputperdana1.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response



                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);

                        jumlah_pending_voucher.setText((response.optString("tugas_hari_ini")));



                    }

                    @Override
                    public void onError(ANError error) {

                    }




                });

    }



    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}