package com.example.barcode;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.barcode.helper.Config;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class Awal2 extends AppCompatActivity {
LinearLayout contekan1, performance1,faq1,info1, penjualan1, absen1;
TextView username1, namakaryawan, posisi;

    CircleImageView img;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.awal2);




        absen1 = (LinearLayout) findViewById(R.id.absen99);
        contekan1 = (LinearLayout) findViewById(R.id.contekan);
        performance1 = (LinearLayout) findViewById(R.id.performance);
        faq1 = (LinearLayout) findViewById(R.id.faq);
        info1 = (LinearLayout) findViewById(R.id.info);
        penjualan1 = (LinearLayout) findViewById(R.id.penjualan);

        username1 = (TextView) findViewById(R.id.username);
        namakaryawan = (TextView) findViewById(R.id.namakaryawan);
        posisi = (TextView) findViewById(R.id.posisi);

        context = Awal2.this;

        Intent kolomlogin = getIntent();
        String kiriman = kolomlogin.getStringExtra("username");
        username1.setText(kiriman);

        absen1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                if ( namakaryawan.getText().toString().equals("namakaryawan")){
                    Toast.makeText(getApplicationContext(), "KONEKSI MU LEMAH, SILAHKAN LOGIN ULANG",
                            Toast.LENGTH_LONG).show();

                    //Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
                }else if ( posisi.getText().toString().equals("posisi")){
                    Toast.makeText(getApplicationContext(), "KONEKSI MU LEMAH, SILAHKAN LOGIN ULANG",
                            Toast.LENGTH_LONG).show();

                    //Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
                }else {
                    String a = namakaryawan.getText().toString();
                    String b = posisi.getText().toString();
                    Intent i = new Intent(getApplicationContext(), AbsendanIzin.class);
                    //Intent i = new Intent(getApplicationContext(), Menuutamanew.class);
                    i.putExtra("nama",""+a+"");
                    i.putExtra("posisi",""+b+"");

                    startActivity(i);
                }

            }

        });

        contekan1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {


                if ( posisi.getText().toString().equals("GRAPARI CIBINONG")){
                    Toast.makeText(getApplicationContext(), "Akses Terkunci ",
                            Toast.LENGTH_LONG).show();

                    //Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
                }else {
                    Intent i = new Intent(getApplicationContext(), Contekan.class);
                    startActivity(i);
                }

            }





        });

        performance1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                if ( posisi.getText().toString().equals("GRAPARI CIBINONG")){
                    Toast.makeText(getApplicationContext(), "Akses Terkunci ",
                            Toast.LENGTH_LONG).show();

                    //Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
                }else {

                    Intent i = new Intent(getApplicationContext(), Performance.class);
                    startActivity(i);
                }
            }

        });



        penjualan1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                if ( namakaryawan.getText().toString().equals("namakaryawan")){
                    Toast.makeText(getApplicationContext(), "KONEKSI MU LEMAH, SILAHKAN LOGIN ULANG",
                            Toast.LENGTH_LONG).show();

                    //Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
                }else if ( posisi.getText().toString().equals("posisi")){
                    Toast.makeText(getApplicationContext(), "KONEKSI MU LEMAH, SILAHKAN LOGIN ULANG",
                            Toast.LENGTH_LONG).show();

                    //Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
                }else if ( posisi.getText().toString().equals("GRAPARI CIBINONG")){
                    Toast.makeText(getApplicationContext(), "Akses Terkunci",
                            Toast.LENGTH_LONG).show();

                    //Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
                }else {
                    String a = namakaryawan.getText().toString();
                    String b = posisi.getText().toString();
                    //String b = posisi.getText().toString();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    //Intent i = new Intent(getApplicationContext(), Menuutamanew.class);
                    i.putExtra("nama",""+a+"");
                    i.putExtra("posisi",""+b+"");
                    //i.putExtra("posisi",""+b+"");

                    startActivity(i);


            }

            }

        });


        faq1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                Intent i = new Intent(getApplicationContext(), FAQ.class);
                startActivity(i);

            }



        });


        info1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                Intent i = new Intent(getApplicationContext(), Infodancontact.class);
                startActivity(i);

            }

        });

        KasAdapter2();
    }

    private void KasAdapter2() {

        AndroidNetworking.post(Config.host + "users.php")
                .addBodyParameter("username", username1.getText().toString())
                //.addBodyParameter("bulan1", bulan1.getText().toString())
                //.addBodyParameter("bulan2", bulan2.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response


                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
                        username1.setText((response.optString("username")));
                        namakaryawan.setText((response.optString("nama")));
                        posisi.setText((response.optString("posisi")));
                        //nama221.setText((response.optString("nama")));

                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }

}