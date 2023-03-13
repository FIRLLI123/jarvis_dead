package com.example.barcode;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.barcode.helper.Config;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Menuabsen extends AppCompatActivity {
Button buttonabsendatang1, buttonabsenpulang1, cek1;
TextView username1, namakaryawan1, validasidatang1, validasipulang1, tanggal1, namakaryawan3;
    private ProgressDialog pDialog;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuabsen);

        buttonabsendatang1 = (Button) findViewById(R.id.buttonabsendatang);
        buttonabsenpulang1 = (Button) findViewById(R.id.buttonabsenpulang);

        cek1 = (Button) findViewById(R.id.cek);

        username1 = (TextView) findViewById(R.id.username);
        namakaryawan1 = (TextView) findViewById(R.id.namakaryawan);
        namakaryawan3 = (TextView) findViewById(R.id.namakaryawan2);

        validasidatang1 = (TextView) findViewById(R.id.validasidatang);
        validasipulang1 = (TextView) findViewById(R.id.validasipulang);

        tanggal1 = (TextView) findViewById(R.id.tanggal);
        context = Menuabsen.this;
        pDialog = new ProgressDialog(context);
        Intent kolomlogin = getIntent();
        String kiriman = kolomlogin.getStringExtra("nama");
        namakaryawan1.setText(kiriman);
        String kiriman2 = kolomlogin.getStringExtra("username");
        username1.setText(kiriman2);
        String kiriman3 = kolomlogin.getStringExtra("nama");
        namakaryawan3.setText("Hallo, "+kiriman3+" Silahkan Absen yaaaa");


        pDialog.setMessage("Sabar ya, Kita cek koneksi dulu...");
        showDialog();


        buttonabsendatang1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validasidatang1.getText().toString().length() == 8) {
//            buttonabsendatang1.setEnabled(false);
//            buttonabsendatang1.setBackgroundColor(getResources().getColor(R.color.abu));
                    Toast.makeText(getApplicationContext(), "Hari ini kamu sudah absen datang",
                            Toast.LENGTH_LONG).show();

                    //1
                    //jika form Email belum di isi / masih kosong
                    //link1.setError("harus diisi");
                    // Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if (validasidatang1.getText().toString().equals("menunggu koneksi datang")) {
//            buttonabsendatang1.setEnabled(false);
//            buttonabsendatang1.setBackgroundColor(getResources().getColor(R.color.abu));
                    Toast.makeText(getApplicationContext(), "Cek koneksi kamu yaaa",
                            Toast.LENGTH_LONG).show();

                    //1
                    //jika form Email belum di isi / masih kosong
                    //link1.setError("harus diisi");
                    // Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if (namakaryawan1.getText().toString().equals("namakaryawan")) {
//            buttonabsendatang1.setEnabled(false);
//            buttonabsendatang1.setBackgroundColor(getResources().getColor(R.color.abu));
                    Toast.makeText(getApplicationContext(), "Cek koneksi kamu yaaa",
                            Toast.LENGTH_LONG).show();

                    //1
                    //jika form Email belum di isi / masih kosong
                    //link1.setError("harus diisi");
                    // Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else {

                    String a = namakaryawan1.getText().toString();
                    String b = username1.getText().toString();
                    Intent i = new Intent(getApplicationContext(), Absen.class);
                    //Intent i = new Intent(getApplicationContext(), Menuutamanew.class);
                    i.putExtra("nama", "" + a + "");
                    i.putExtra("username", "" + b + "");
//        intent.putExtra("idoutlet",""+b+"");
//        intent.putExtra("namaoutlet",""+c+"");
                    //startActivity(i);
                    startActivity(i);
                    //finish();

                }
            }
        });



        buttonabsenpulang1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validasipulang1.getText().toString().length() == 8) {
//            buttonabsendatang1.setEnabled(false);
//            buttonabsendatang1.setBackgroundColor(getResources().getColor(R.color.abu));
                    Toast.makeText(getApplicationContext(), "Hari ini kamu sudah absen pulang",
                            Toast.LENGTH_LONG).show();

                    //1
                    //jika form Email belum di isi / masih kosong
                    //link1.setError("harus diisi");
                    // Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if (validasipulang1.getText().toString().equals("menunggu koneksi pulang")) {
//            buttonabsendatang1.setEnabled(false);
//            buttonabsendatang1.setBackgroundColor(getResources().getColor(R.color.abu));
                    Toast.makeText(getApplicationContext(), "Cek koneksi kamu yaaa",
                            Toast.LENGTH_LONG).show();

                    //1
                    //jika form Email belum di isi / masih kosong
                    //link1.setError("harus diisi");
                    // Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else {

                    String a = namakaryawan1.getText().toString();
                    String b = username1.getText().toString();
                    Intent i = new Intent(getApplicationContext(), Absenpulang.class);
                    //Intent i = new Intent(getApplicationContext(), Menuutamanew.class);
                    i.putExtra("nama", "" + a + "");
                    i.putExtra("username", "" + b + "");
//        intent.putExtra("idoutlet",""+b+"");
//        intent.putExtra("namaoutlet",""+c+"");
                    //startActivity(i);
                    startActivity(i);
                    //finish();

                }
            }
        });

        cek1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                KasAdapter2();
//                KasAdapter3();

            }
        });

        tanggal1.setText(getCurrentDate());
        KasAdapter2();
        KasAdapter3();
//        validasidatang();
//        validasipulang();

    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
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


    public void validasidatang(){
        if (validasidatang1.getText().toString().equals("null")) {
//            buttonabsendatang1.setEnabled(false);
//            buttonabsendatang1.setBackgroundColor(getResources().getColor(R.color.abu));
            Toast.makeText(getApplicationContext(), "Menunggu Koneksi",
                    Toast.LENGTH_LONG).show();

            //1
            //jika form Email belum di isi / masih kosong
            //link1.setError("harus diisi");
            // Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
        } else {
            buttonabsendatang1.setEnabled(true);
            buttonabsendatang1.setBackgroundColor(getResources().getColor(R.color.merah));
            //hitungHasil();


        }
    }


    public void validasipulang(){
        if (validasipulang1.getText().toString().length() == 8) {
            buttonabsenpulang1.setEnabled(false);
            buttonabsenpulang1.setBackgroundColor(getResources().getColor(R.color.abu));


            //1
            //jika form Email belum di isi / masih kosong
            //link1.setError("harus diisi");
            // Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
        } else{
            buttonabsenpulang1.setEnabled(true);
            buttonabsenpulang1.setBackgroundColor(getResources().getColor(R.color.merah));
            //hitungHasil();


        }
    }

    private void KasAdapter2() {

        AndroidNetworking.post(Config.host + "cekabsendatang.php")
                .addBodyParameter("namasales", namakaryawan1.getText().toString())
                .addBodyParameter("tanggal", tanggal1.getText().toString())
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
                        validasidatang1.setText((response.optString("absendatang")));
                        //nama221.setText((response.optString("nama")));
                        hideDialog();
                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });





    }

    private void KasAdapter3() {

        AndroidNetworking.post(Config.host + "cekabsenpulang.php")
                .addBodyParameter("namasales", namakaryawan1.getText().toString())
                .addBodyParameter("tanggal", tanggal1.getText().toString())
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
                        validasipulang1.setText((response.optString("absenpulang")));
                        //nama221.setText((response.optString("nama")));
                        hideDialog();
                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });





    }




}