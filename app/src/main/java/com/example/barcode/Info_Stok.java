package com.example.barcode;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.example.barcode.helper.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class Info_Stok extends AppCompatActivity {
    LinearLayout pengambilanbarang,stok,barangselisih,barangrusak,gudang,input_pengambilan, stok_penjualan;

    TextView username1, namakaryawan, posisi, idkar, join;
    CircleImageView img;

    ArrayList<HashMap<String, String>> list_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_stok);



        pengambilanbarang = (LinearLayout) findViewById(R.id.pengambilanbarang);
        stok = (LinearLayout) findViewById(R.id.stok);
        barangselisih = (LinearLayout) findViewById(R.id.barangselisih);
        barangrusak = (LinearLayout) findViewById(R.id.barangrusak);
        gudang = (LinearLayout) findViewById(R.id.gudang);

        input_pengambilan = (LinearLayout) findViewById(R.id.input_pengambilan);

        stok_penjualan = (LinearLayout) findViewById(R.id.stok_penjualan);

        list_data = new ArrayList<HashMap<String, String>>();

        img=(CircleImageView)findViewById(R.id.imgbarang);

        username1 = (TextView) findViewById(R.id.username);
        namakaryawan = (TextView) findViewById(R.id.namakaryawan);
        posisi = (TextView) findViewById(R.id.posisi);
        join = (TextView) findViewById(R.id.join);
        idkar = (TextView) findViewById(R.id.idkar);

        pengambilanbarang.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                Intent i = new Intent(getApplicationContext(), SumRiwayatStok.class);
                startActivity(i);




            }



        });

        stok.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                Intent i = new Intent(getApplicationContext(), SumStokPerdana.class);
                startActivity(i);

                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }



        });


        barangrusak.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                Intent i = new Intent(getApplicationContext(), Sum_barang_rusak.class);
                startActivity(i);

            }



        });

        barangselisih.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                Intent i = new Intent(getApplicationContext(), SumStokSelisih.class);
                startActivity(i);

            }



        });

        gudang.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                Intent i = new Intent(getApplicationContext(), MasterGudang.class);
                startActivity(i);

                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }



        });

        input_pengambilan.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                String a = namakaryawan.getText().toString();

                Intent i = new Intent(getApplicationContext(), Pengambilan_barang.class);
                //Intent i = new Intent(getApplicationContext(), Menuutamanew.class);
                i.putExtra("namasales",""+a+"");

                startActivity(i);

            }



        });


        stok_penjualan.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                String a = namakaryawan.getText().toString();

                Intent i = new Intent(getApplicationContext(), Penjualan_group_item.class);
                //Intent i = new Intent(getApplicationContext(), Menuutamanew.class);
                i.putExtra("namasales",""+a+"");

                startActivity(i);

            }



        });





        Intent kolomlogin = getIntent();
        String kiriman1 = kolomlogin.getStringExtra("nama");
        namakaryawan.setText(kiriman1);
        String kiriman2 = kolomlogin.getStringExtra("posisi");
        posisi.setText(kiriman2);
        String kiriman3 = kolomlogin.getStringExtra("join");
        join.setText(kiriman3);
        String kiriman4 = kolomlogin.getStringExtra("idkar");
        idkar.setText(kiriman4);


        if (posisi.getText().toString().equals("SALES FORCE")){
            //1
            gudang.setVisibility(View.GONE);
            input_pengambilan.setVisibility(View.GONE);


            //Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
        }else{

            gudang.setVisibility(View.VISIBLE);
            input_pengambilan.setVisibility(View.VISIBLE);
        }



        KasAdapter3();

    }



    private void KasAdapter3() {

        //swipe_refresh.setRefreshing(true);
        list_data.clear();
//        listinputperdana1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post(Config.host + "getfoto.php")
                .addBodyParameter("nama", namakaryawan.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response



                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
/*
                        text_masuk.setText(
                                rupiahFormat.format(response.optDouble("yes")));
                        text_keluar.setText(
                                rupiahFormat.format( response.optDouble("oke") ));
                        text_total.setText(
                                rupiahFormat.format( response.optDouble("saldo") ));

**/

                        try {
                            JSONArray jsonArray = response.optJSONArray("barang");
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject responses = jsonArray.getJSONObject(i);
                                HashMap<String, String> map = new HashMap<String, String>();
                                //map.put("no",         responses.optString("no"));
                                map.put("foto", responses.optString("foto"));


                                //map.put("tanggal",      responses.optString("tanggal"));

                                list_data.add(map);
                            }

                            Glide.with(getApplicationContext())
                                    .load("http://rekamitrayasa.com/reka/foto/" + list_data.get(0).get("foto"))
                                    .crossFade()
                                    .placeholder(R.drawable.noimage3)
                                    .into(img);
                            //txtnama.setText(list_data.get(0).get("nama"));
                            //txtharga.setText(list_data.get(0).get("harga_barang"));
                            //txtstock.setText(list_data.get(0).get("stock"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }
}