package com.example.barcode;

import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.barcode.Data.Data_BayarEX;
import com.example.barcode.helper.Config;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Detail_Absen extends AppCompatActivity {

    TextView idabsen, namasales, jam, lokasilengkap, longitude, latitude, tanggal, kecamatan, keterangan, statusabsen, absen,posisi;
ImageView pending, tolak, approve;
Button btndelete;
    private ProgressDialog pDialog;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_absen);

        context = Detail_Absen.this;
        pDialog = new ProgressDialog(context);
        idabsen = (TextView) findViewById(R.id.idabsen);
        namasales = (TextView) findViewById(R.id.namasales);
        jam = (TextView) findViewById(R.id.jam);
        lokasilengkap = (TextView) findViewById(R.id.lokasilengkap);

        tanggal = (TextView) findViewById(R.id.tanggal);

        keterangan = (TextView) findViewById(R.id.keterangan);
        statusabsen = (TextView) findViewById(R.id.statusabsen);
        absen = (TextView) findViewById(R.id.absen);

        pending = (ImageView) findViewById(R.id.pendingabsen);
        tolak = (ImageView) findViewById(R.id.tolakabsen);
        approve = (ImageView) findViewById(R.id.approveabsen);

        btndelete = (Button) findViewById(R.id.btndelete);

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteabsen();



            }
        });


        Intent kolomlogin = getIntent();
        String kiriman1 = kolomlogin.getStringExtra("id");
        idabsen.setText(kiriman1);





        statusaben();
    }


    private void statusaben() {

        AndroidNetworking.post(Config.host + "statusabsen.php")
                .addBodyParameter("id", idabsen.getText().toString())
                //.addBodyParameter("bulan1", bulan1.getText().toString())
                //.addBodyParameter("bulan2", bulan2.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response


                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
                        //username1.setText((response.optString("username")));
                        namasales.setText((response.optString("namasales")));
                        jam.setText((response.optString("jam")));
                        lokasilengkap.setText((response.optString("lokasilengkap")));
                        tanggal.setText((response.optString("tanggal")));
                        keterangan.setText((response.optString("keterangan")));
                        statusabsen.setText((response.optString("statusabsen")));
                        absen.setText((response.optString("absen")));


                        //nama221.setText((response.optString("nama")));
                        //hideDialog();

                        if (statusabsen.getText().toString().equals("PENDING")) {
                            //1
                            pending.setVisibility(View.VISIBLE);
                            tolak.setVisibility(View.GONE);
                            approve.setVisibility(View.GONE);

                            //Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
                        } else if (statusabsen.getText().toString().equals("APPROVE")) {
                            //1

                            approve.setVisibility(View.VISIBLE);
                            tolak.setVisibility(View.GONE);
                            pending.setVisibility(View.GONE);

                            //Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
                        }else if (statusabsen.getText().toString().equals("DITOLAK")) {
                            //1

                            tolak.setVisibility(View.VISIBLE);
                            pending.setVisibility(View.GONE);
                            approve.setVisibility(View.GONE);

                            btndelete.setEnabled(true);
                            btndelete.setBackgroundColor(getResources().getColor(R.color.merah));

                            //Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
                        }else{


                        }

                    }


                    @Override
                    public void onError(ANError error) {

                    }


                });

    }






    private void deleteabsen() {
        pDialog.setMessage("Delete Process...");
        showDialog();
        pDialog.setCanceledOnTouchOutside(false);
        AndroidNetworking.post(Config.host + "deletestatusabsen.php")
                .addBodyParameter("id", idabsen.getText().toString())

                .setPriority(Priority.MEDIUM)
                .build()

                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response

                        Log.d("response", response.toString());

                        if (response.optString("response").toString().equals("success")) {
                            hideDialog();
                            //gotoCourseActivity();
                            Toast.makeText(getApplicationContext(), "Delete data absen berhasil, Silahkan absen kembali",
                                    Toast.LENGTH_LONG).show();



                        } else {
                            hideDialog();
                            Toast.makeText(getApplicationContext(), "gagal hapus",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
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