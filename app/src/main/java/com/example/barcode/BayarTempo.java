package com.example.barcode;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.barcode.Data.Data_BayarEX;
import com.example.barcode.helper.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

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

public class BayarTempo extends AppCompatActivity {
    TextView id1,idoutlet,namaoutlet,namasales,namaitem,qty,tanggal,pembayaran,tanggalsekarang;
    Button verifikasi;

TextView pembayaran2;
    private ProgressDialog pDialog;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bayar_tempo);

        id1 = (TextView) findViewById(R.id.id);
        idoutlet = (TextView) findViewById(R.id.idoutlet);
        namaoutlet = (TextView) findViewById(R.id.namaoutlet);
        namasales = (TextView) findViewById(R.id.namasales);
        namaitem = (TextView) findViewById(R.id.namaitem);
        qty = (TextView) findViewById(R.id.qty);
        tanggal = (TextView) findViewById(R.id.tanggal);
        tanggalsekarang = (TextView) findViewById(R.id.tanggalsekarang);
        pembayaran = (TextView) findViewById(R.id.pembayaran);

        pembayaran2 = (TextView) findViewById(R.id.pembayaran2);

        verifikasi = (Button) findViewById(R.id.btnverifikasi);

        Intent dasboard = getIntent();

        String kiriman1 = dasboard.getStringExtra("id");
        id1.setText(kiriman1);
        String kiriman2 = dasboard.getStringExtra("idoutlet");
        idoutlet.setText(kiriman2);
        String kiriman3 = dasboard.getStringExtra("namaoutlet");
        namaoutlet.setText(kiriman3);
        String kiriman4 = dasboard.getStringExtra("namasales");
        namasales.setText(kiriman4);
        String kiriman5 = dasboard.getStringExtra("namaitem");
        namaitem.setText(kiriman5);
        String kiriman6 = dasboard.getStringExtra("qty");
        qty.setText(kiriman6);
        String kiriman7 = dasboard.getStringExtra("tanggal");
        tanggal.setText(kiriman7);
        String kiriman8 = dasboard.getStringExtra("pembayaran");
        pembayaran.setText(kiriman8);


        context = BayarTempo.this;
        pDialog = new ProgressDialog(context);

        verifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatetempo();
            }
        });

        tanggalsekarang.setText(getCurrentDate());

        prosesdasboard();

    }


    private void savebayartempo() {
        pDialog.setMessage("Process...");
        showDialog();
        pDialog.setCanceledOnTouchOutside(false);
        AndroidNetworking.post(Config.host + "inputbayartempo.php")
                .addBodyParameter("idoutlet", idoutlet.getText().toString())
                .addBodyParameter("namaoutlet", namaoutlet.getText().toString())
                .addBodyParameter("namasales", namasales.getText().toString())
                .addBodyParameter("namaitem", namaitem.getText().toString())
                .addBodyParameter("qty", qty.getText().toString())

                .addBodyParameter("tanggaltempo", tanggal.getText().toString())
                .addBodyParameter("tanggalsekarang", tanggalsekarang.getText().toString())



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
                            Toast.makeText(getApplicationContext(), "Berhasil disimpan",
                                    Toast.LENGTH_LONG).show();


                        } else {
                            hideDialog();
                            Toast.makeText(getApplicationContext(), "failed",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    public String getCurrentDate() {
        //SimpleDateFormat contoh1 = new SimpleDateFormat("yyyy/MM/dd");
        //String hariotomatis = contoh1.format(c.getTime());
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat contoh1 = new SimpleDateFormat("yyyy/MM/dd");
//        int year, month, day;
//        year = c.get(Calendar.YEAR);
//        month = c.get(Calendar.MONTH);
//        day = c.get(Calendar.DATE);
        //SimpleDateFormat contoh1 = new SimpleDateFormat("EEEE, yyyy/MM/dd");

        String hariotomatis = contoh1.format(c.getTime());
        //tanggalinputperdana1.setText(hariotomatis);
        return hariotomatis;
        //return day +"/" + (month+1) + "/" + year;
        //return (month+1) +"/" + day + "/" + year;
        //return year + "/" + (month + 1) + "/" + day;



    }


    public void prosesdasboard(){

        new CountDownTimer(2000, 1000) {

            public void onTick(long millisUntilFinished) {
                pDialog.setMessage("TUNGGU SEBENTAR, SEDANG VERIFIKASI DATA :"+ millisUntilFinished / 1000);
                showDialog();
                pDialog.setCanceledOnTouchOutside(false);
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                if ( pembayaran.getText().toString().equals("SUDAH TERBAYAR")) {
                    //1
                    verifikasi.setEnabled(false);
                    verifikasi.setBackgroundColor(getResources().getColor(R.color.abu));
                    hideDialog();
                }else{

                    hideDialog();

                }

            }
        }.start();

    }



    private void updatetempo() {
        pDialog.setMessage("Update Process...");
        showDialog();
        pDialog.setCanceledOnTouchOutside(false);
        AndroidNetworking.post(Config.host + "updatetempo.php")
                .addBodyParameter("id", id1.getText().toString())
                .addBodyParameter("pembayaran", pembayaran2.getText().toString())
//                .addBodyParameter("qty", qtybarang1.getText().toString())
//                .addBodyParameter("harga", hargabarang1.getText().toString())
//                .addBodyParameter("total", total1.getText().toString())
//                .addBodyParameter("keterangan", keterangan1.getText().toString())
//                .addBodyParameter("jam", jaminput1.getText().toString())
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
                            Toast.makeText(getApplicationContext(), "Verifikasi Bayar Tempo Berhasil",
                                    Toast.LENGTH_LONG).show();

                            savebayartempo();


                        } else {
                            hideDialog();
                            Toast.makeText(getApplicationContext(), "failed",
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