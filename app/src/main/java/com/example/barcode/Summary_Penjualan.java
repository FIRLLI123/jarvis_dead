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

import de.hdodenhof.circleimageview.CircleImageView;

public class Summary_Penjualan extends AppCompatActivity {
    TextView username1, namakaryawan, posisi, idkar, join;
    CircleImageView img;

    LinearLayout jual_perdana, jual_voucher, jual_link, sumoutlet, bayar, tempo, tempo_bayar, sum_tempo, rank_sales, rank_ds;

    ArrayList<HashMap<String, String>> list_data;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary_penjualan);

        jual_perdana = (LinearLayout) findViewById(R.id.jual_perdana);
        jual_voucher = (LinearLayout) findViewById(R.id.jual_voucher);
        jual_link = (LinearLayout) findViewById(R.id.jual_link);
        sumoutlet = (LinearLayout) findViewById(R.id.sumoutlet);
        bayar = (LinearLayout) findViewById(R.id.bayar);
        tempo = (LinearLayout) findViewById(R.id.tempo);
        tempo_bayar = (LinearLayout) findViewById(R.id.tempo_bayar);
        sum_tempo = (LinearLayout) findViewById(R.id.sum_tempo);
        rank_sales = (LinearLayout) findViewById(R.id.rank_sales);
        rank_ds = (LinearLayout) findViewById(R.id.rank_ds);


        list_data = new ArrayList<HashMap<String, String>>();

        img=(CircleImageView)findViewById(R.id.imgbarang);

        username1 = (TextView) findViewById(R.id.username);
        namakaryawan = (TextView) findViewById(R.id.namakaryawan);
        posisi = (TextView) findViewById(R.id.posisi);
        join = (TextView) findViewById(R.id.join);
        idkar = (TextView) findViewById(R.id.idkar);


        Intent kolomlogin = getIntent();
        String kiriman1 = kolomlogin.getStringExtra("nama");
        namakaryawan.setText(kiriman1);
        String kiriman2 = kolomlogin.getStringExtra("posisi");
        posisi.setText(kiriman2);
        String kiriman3 = kolomlogin.getStringExtra("join");
        join.setText(kiriman3);
        String kiriman4 = kolomlogin.getStringExtra("idkar");
        idkar.setText(kiriman4);



        jual_perdana.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {


                String a = posisi.getText().toString();

                //String c = namasalesinputperdana1.getText().toString();
                Intent i = new Intent(getApplicationContext(), Sum_Penjualan_Gabung.class);

                i.putExtra("posisi",""+a+"");
                startActivity(i);

            }



        });


        jual_voucher.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                String a = posisi.getText().toString();

                //String c = namasalesinputperdana1.getText().toString();
                Intent i = new Intent(getApplicationContext(), Sum_Penjual_Gabung_2.class);

                i.putExtra("posisi",""+a+"");
                startActivity(i);

            }



        });



        jual_link.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                Intent i = new Intent(getApplicationContext(), SumLink.class);
                startActivity(i);

            }



        });


        sumoutlet.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                Intent i = new Intent(getApplicationContext(), SumPENJUALAN_ALL.class);
                startActivity(i);

            }



        });


        bayar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                Intent i = new Intent(getApplicationContext(), SumaryRupiah.class);
                startActivity(i);

            }



        });

        tempo.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                Intent i = new Intent(getApplicationContext(), Sumtempo.class);
                startActivity(i);

            }



        });

        tempo_bayar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                Intent i = new Intent(getApplicationContext(), SumBayarTempo.class);
                startActivity(i);

            }



        });

        sum_tempo.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                Intent i = new Intent(getApplicationContext(), SumTEMPO_ALL.class);
                startActivity(i);

            }






        });

        rank_sales.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                Intent i = new Intent(getApplicationContext(), Rank_sales.class);
                startActivity(i);

            }






        });



        rank_ds.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                Intent i = new Intent(getApplicationContext(), Rank_ds.class);
                startActivity(i);

            }






        });



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