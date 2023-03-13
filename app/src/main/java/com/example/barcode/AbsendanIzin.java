package com.example.barcode;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class AbsendanIzin extends AppCompatActivity {

    private ImageView imgbarang;

    TextView namasales, posisi, hadir, cuti, izin, approve, pending, tolak, selengkapnya;

    TextView tanggaldari, tanggalsampai, bulan, approvegalengkap;
    LinearLayout kehadiran, absenizin,absencuti, absenapprove;
    private ProgressDialog pDialog;
    private Context context;
    Handler mHandler;

    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    CircleImageView img;

    ArrayList<HashMap<String, String>> list_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.absen_dan_izin);

        this.mHandler = new Handler();
        m_Runnable.run();

        imgbarang = (ImageView) findViewById(R.id.imgbarang);
        img=(CircleImageView)findViewById(R.id.imgbarang);
        requestQueue = Volley.newRequestQueue(AbsendanIzin.this);

        list_data = new ArrayList<HashMap<String, String>>();


        namasales = (TextView) findViewById(R.id.namasales);
        posisi = (TextView) findViewById(R.id.posisi);

        selengkapnya = (TextView) findViewById(R.id.selengkapnya);

        tanggaldari = (TextView) findViewById(R.id.tanggaldari);
        tanggalsampai = (TextView) findViewById(R.id.tanggalsampai);
        bulan = (TextView) findViewById(R.id.bulan);

        hadir = (TextView) findViewById(R.id.hadir);
        cuti = (TextView) findViewById(R.id.cuti);
        izin = (TextView) findViewById(R.id.izin);
        approve = (TextView) findViewById(R.id.approve);
        pending = (TextView) findViewById(R.id.pending);
        tolak = (TextView) findViewById(R.id.tolak);

        kehadiran = (LinearLayout) findViewById(R.id.kehadiran);
        absenizin = (LinearLayout) findViewById(R.id.absenizin);
        absencuti = (LinearLayout) findViewById(R.id.absencuti);
        absenapprove = (LinearLayout) findViewById(R.id.absenapprove);

        approvegalengkap = (TextView) findViewById(R.id.approvegalengkap);

        Intent kolomlogin = getIntent();

        String kiriman1 = kolomlogin.getStringExtra("nama");
        namasales.setText(kiriman1);

        String kiriman2 = kolomlogin.getStringExtra("posisi");
        posisi.setText(kiriman2);


        if ( posisi.getText().toString().equals("MANAGER")){
            //1
            absenapprove.setVisibility(View.VISIBLE);

            //Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
        }else{

            absenapprove.setVisibility(View.GONE);
        }



        kehadiran.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {


                    String a = namasales.getText().toString();
                    String b = posisi.getText().toString();
                    Intent i = new Intent(getApplicationContext(), Absen_fix.class);
                    //Intent i = new Intent(getApplicationContext(), Menuutamanew.class);
                    i.putExtra("nama",""+a+"");
                    i.putExtra("posisi",""+b+"");

                    startActivity(i);
            }

        });

        selengkapnya.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {


                String a = namasales.getText().toString();
                String b = posisi.getText().toString();
                Intent i = new Intent(getApplicationContext(), SumAbsen.class);
                //Intent i = new Intent(getApplicationContext(), Menuutamanew.class);
                i.putExtra("nama",""+a+"");
                i.putExtra("posisi",""+b+"");

                startActivity(i);
            }

        });


        absenizin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {


                String a = namasales.getText().toString();
                String b = posisi.getText().toString();
                Intent i = new Intent(getApplicationContext(), Izin_fix.class);
                //Intent i = new Intent(getApplicationContext(), Menuutamanew.class);
                i.putExtra("nama",""+a+"");
                i.putExtra("posisi",""+b+"");

                startActivity(i);
            }

        });


        absencuti.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {


                String a = namasales.getText().toString();
                String b = posisi.getText().toString();
                Intent i = new Intent(getApplicationContext(), Cuti_fix.class);
                //Intent i = new Intent(getApplicationContext(), Menuutamanew.class);
                i.putExtra("nama",""+a+"");
                i.putExtra("posisi",""+b+"");

                startActivity(i);
            }

        });

        absenapprove.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                String a = namasales.getText().toString();
                String b = posisi.getText().toString();
                Intent i = new Intent(getApplicationContext(), ListApprove.class);
                //Intent i = new Intent(getApplicationContext(), Menuutamanew.class);
                //i.putExtra("nama",""+a+"");
                i.putExtra("posisi",""+b+"");

                startActivity(i);
            }

        });

        context = AbsendanIzin.this;
        pDialog = new ProgressDialog(context);

        //prosesdasboard();
        //prosesdasboard2();
        //hitung();

        KasAdapter2();


    }

    private final Runnable m_Runnable = new Runnable() {
        public void run() {
            //Toast.makeText(AbsendanIzin.this, "", Toast.LENGTH_SHORT).show();
            hadir = (TextView) findViewById(R.id.hadir);
            cuti = (TextView) findViewById(R.id.cuti);
            izin = (TextView) findViewById(R.id.izin);
            approve = (TextView) findViewById(R.id.approve);
            pending = (TextView) findViewById(R.id.pending);
            tolak = (TextView) findViewById(R.id.tolak);

            kehadiran = (LinearLayout) findViewById(R.id.kehadiran);
            absenizin = (LinearLayout) findViewById(R.id.absenizin);
            absencuti = (LinearLayout) findViewById(R.id.absencuti);
            absenapprove = (LinearLayout) findViewById(R.id.absenapprove);

            approvegalengkap = (TextView) findViewById(R.id.approvegalengkap);

            prosesdasboard();

            AbsendanIzin.this.mHandler.postDelayed(m_Runnable, 2000);
        }

    };


    public void prosesdasboard(){
        bulan();
        new CountDownTimer(2000, 1000) {

            public void onTick(long millisUntilFinished) {
                pDialog.setMessage("TUNGGU SEBENTAR, SEDANG VERIFIKASI DATA :"+ millisUntilFinished / 1000);
                //showDialog();
                absenhadir();
                absencuti();
                absenizin();
                absenapprove();
                absenpending();
                absenditolak();
                absengalengkap();
                //absengalengkap();
                KasAdapter2();
                pDialog.setCanceledOnTouchOutside(false);
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                hideDialog();


            }
        }.start();

    }

    public void prosesdasboard2(){
        //absengalengkap();
        new CountDownTimer(2000, 1000) {

            public void onTick(long millisUntilFinished) {
                pDialog.setMessage("TUNGGU SEBENTAR, SEDANG VERIFIKASI DATA :"+ millisUntilFinished / 1000);
                showDialog();


                hitung();
                pDialog.setCanceledOnTouchOutside(false);
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                hideDialog();


            }
        }.start();

    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void bulan() {

        AndroidNetworking.post(Config.host + "bulan.php")
                //.addBodyParameter("namasales", namasales.getText().toString())
//                .addBodyParameter("namasales", namasalesinputperdana1.getText().toString())
//                .addBodyParameter("tanggal", tanggalinputperdana1.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response



                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);

                        //itempenjualanperdana1.setText((response.optString("item")));
                        tanggaldari.setText((response.optString("tanggaldari")));
                        tanggalsampai.setText((response.optString("tanggalsampai")));
                        bulan.setText((response.optString("bulan")));



                    }

                    @Override
                    public void onError(ANError error) {

                    }




                });

    }


    private void absenhadir() {

        AndroidNetworking.post(Config.host + "count_absenhadir.php")
                .addBodyParameter("namasales", namasales.getText().toString())
                .addBodyParameter("tanggaldari", tanggaldari.getText().toString())
                .addBodyParameter("tanggalsampai", tanggalsampai.getText().toString())
//                .addBodyParameter("namasales", namasalesinputperdana1.getText().toString())
//                .addBodyParameter("tanggal", tanggalinputperdana1.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response



                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);

                        //itempenjualanperdana1.setText((response.optString("item")));
                        hadir.setText((response.optString("absen")));

                        if ( hadir.getText().toString().equals("null")){
                            hadir.setText("0");

                        }else{


                        }

                    }

                    @Override
                    public void onError(ANError error) {

                    }




                });

    }


    private void absencuti() {

        AndroidNetworking.post(Config.host + "count_absencuti.php")
                .addBodyParameter("namasales", namasales.getText().toString())
                .addBodyParameter("tanggaldari", tanggaldari.getText().toString())
                .addBodyParameter("tanggalsampai", tanggalsampai.getText().toString())
//                .addBodyParameter("namasales", namasalesinputperdana1.getText().toString())
//                .addBodyParameter("tanggal", tanggalinputperdana1.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response



                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);

                        //itempenjualanperdana1.setText((response.optString("item")));
                        cuti.setText((response.optString("absen")));

                        if ( izin.getText().toString().equals("null")){
                            izin.setText("0");

                        }else{


                        }

                    }

                    @Override
                    public void onError(ANError error) {

                    }


                });
    }


    private void absenizin() {

        AndroidNetworking.post(Config.host + "count_absenizin.php")
                .addBodyParameter("namasales", namasales.getText().toString())
                .addBodyParameter("tanggaldari", tanggaldari.getText().toString())
                .addBodyParameter("tanggalsampai", tanggalsampai.getText().toString())
//                .addBodyParameter("namasales", namasalesinputperdana1.getText().toString())
//                .addBodyParameter("tanggal", tanggalinputperdana1.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response



                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);

                        //itempenjualanperdana1.setText((response.optString("item")));
                        izin.setText((response.optString("absen")));

                        if ( izin.getText().toString().equals("null")){
                            izin.setText("0");

                        }else{


                        }

                    }

                    @Override
                    public void onError(ANError error) {

                    }




                });

    }


    private void absenapprove() {

        AndroidNetworking.post(Config.host + "count_absenapprove.php")
                .addBodyParameter("namasales", namasales.getText().toString())
                .addBodyParameter("tanggaldari", tanggaldari.getText().toString())
                .addBodyParameter("tanggalsampai", tanggalsampai.getText().toString())
//                .addBodyParameter("namasales", namasalesinputperdana1.getText().toString())
//                .addBodyParameter("tanggal", tanggalinputperdana1.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response



                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);

                        //itempenjualanperdana1.setText((response.optString("item")));
                        approve.setText((response.optString("absen")));

                        if ( approve.getText().toString().equals("null")){
                            approve.setText("0");

                        }else{


                        }

                    }

                    @Override
                    public void onError(ANError error) {

                    }




                });

    }


    private void absenpending() {

        AndroidNetworking.post(Config.host + "count_absenpending.php")
                .addBodyParameter("namasales", namasales.getText().toString())
                .addBodyParameter("tanggaldari", tanggaldari.getText().toString())
                .addBodyParameter("tanggalsampai", tanggalsampai.getText().toString())
//                .addBodyParameter("namasales", namasalesinputperdana1.getText().toString())
//                .addBodyParameter("tanggal", tanggalinputperdana1.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response



                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);

                        //itempenjualanperdana1.setText((response.optString("item")));
                        pending.setText((response.optString("absen")));

                        if ( pending.getText().toString().equals("null")){
                            pending.setText("0");

                        }else{


                        }

                    }

                    @Override
                    public void onError(ANError error) {

                    }




                });

    }

    private void absenditolak() {

        AndroidNetworking.post(Config.host + "count_absentolak.php")
                .addBodyParameter("namasales", namasales.getText().toString())
                .addBodyParameter("tanggaldari", tanggaldari.getText().toString())
                .addBodyParameter("tanggalsampai", tanggalsampai.getText().toString())
//                .addBodyParameter("namasales", namasalesinputperdana1.getText().toString())
//                .addBodyParameter("tanggal", tanggalinputperdana1.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response



                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);

                        //itempenjualanperdana1.setText((response.optString("item")));
                        tolak.setText((response.optString("absen")));

                        if ( tolak.getText().toString().equals("null")){
                            tolak.setText("0");

                        }else{


                        }

                    }

                    @Override
                    public void onError(ANError error) {

                    }




                });

    }


    private void absengalengkap() {

        AndroidNetworking.post(Config.host + "count_absengalengkap.php")
                .addBodyParameter("namasales", namasales.getText().toString())
                .addBodyParameter("tanggaldari", tanggaldari.getText().toString())
                .addBodyParameter("tanggalsampai", tanggalsampai.getText().toString())
//                .addBodyParameter("namasales", namasalesinputperdana1.getText().toString())
//                .addBodyParameter("tanggal", tanggalinputperdana1.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response



                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);

                        //itempenjualanperdana1.setText((response.optString("item")));
                        approvegalengkap.setText((response.optString("absen")));

                        if ( approvegalengkap.getText().toString().equals("null")){
                            approvegalengkap.setText("0");

                        }else{


                        }
                        hitung();
                    }

                    @Override
                    public void onError(ANError error) {

                    }




                });

    }


    private void hitung() {
        int item1 = Integer.parseInt(hadir.getText().toString()); //pulang
        int item2 = Integer.parseInt(approvegalengkap.getText().toString()); //datang
        int hasilitem1 = item2 - item1;
        approvegalengkap.setText(String.valueOf(hasilitem1));
    }



    private void KasAdapter2() {

        //swipe_refresh.setRefreshing(true);
        list_data.clear();
//        listinputperdana1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post(Config.host + "getfoto.php")
                .addBodyParameter("nama", namasales.getText().toString())
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