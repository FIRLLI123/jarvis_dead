package com.example.barcode;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.example.barcode.Data.Data_BayarEX;
import com.example.barcode.helper.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

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

public class Sum_dokumentasi_outlet2 extends AppCompatActivity {
    TextView id,namasales,tanggal,jam,namaoutlet,kecamatan,kelurahan,alasan,digipos,belanja_vcr,belanja_sp;
    ImageView sticker,poster,layartoko,harga;
    ListView listdataoutlet;
    Button caridataoutlet;


    public static String LINK, namaoutletlist, idoutletlist, namasaleslist;
    //ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();

    private ProgressDialog pDialog;
    private Context context;
    Handler mHandler;

    ArrayList<HashMap<String, String>> list_data;
    ArrayList<HashMap<String, String>> list_data2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sum_dokumentasi_outlet2);

        this.mHandler = new Handler();
        m_Runnable.run();
        list_data = new ArrayList<HashMap<String, String>>();
        list_data2 = new ArrayList<HashMap<String, String>>();

        id = (TextView) findViewById(R.id.id);
        namasales = (TextView) findViewById(R.id.namasales);
        tanggal = (TextView) findViewById(R.id.tanggal);
        jam = (TextView) findViewById(R.id.jam);
        namaoutlet = (TextView) findViewById(R.id.namaoutlet);
        kecamatan = (TextView) findViewById(R.id.kecamatan);
        kelurahan = (TextView) findViewById(R.id.kelurahan);
        alasan = (TextView) findViewById(R.id.alasan);
        digipos = (TextView) findViewById(R.id.digipos);
        belanja_vcr = (TextView) findViewById(R.id.belanjavcr);
        belanja_sp = (TextView) findViewById(R.id.belanjasp);


        sticker=(ImageView) findViewById(R.id.sticker);
        poster=(ImageView) findViewById(R.id.poster);


        context = Sum_dokumentasi_outlet2.this;
        pDialog = new ProgressDialog(context);

        //KasAdapter3();

        Intent kolomlogin = getIntent();
        String kiriman = kolomlogin.getStringExtra("id");
        id.setText(kiriman);
    }


    private final Runnable m_Runnable = new Runnable() {
        public void run() {
            //Toast.makeText(AbsendanIzin.this, "", Toast.LENGTH_SHORT).show();


            KasAdapter3();
            KasAdapter2();

            Sum_dokumentasi_outlet2.this.mHandler.postDelayed(m_Runnable, 2000);
        }

    };


    public void prosesdasboard(){

        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
                //showDialog();
                pDialog.setMessage("Sedang load data :"+ millisUntilFinished / 1000);
                //showDialog();
                //uploadtoserver();
                //absengalengkap();
//                KasAdapter3();
//                KasAdapter2();
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

    private void KasAdapter3() {

        //swipe_refresh.setRefreshing(true);
        list_data.clear();

        AndroidNetworking.post(Config.host + "getfoto_digipost.php")
                .addBodyParameter("id", id.getText().toString())
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
                                map.put("gambar1", responses.optString("gambar1"));
                                map.put("gambar2", responses.optString("gambar2"));




                                //map.put("tanggal",      responses.optString("tanggal"));

                                list_data.add(map);
                            }

                            Glide.with(getApplicationContext())
                                    .load("https://rekamitrayasa.com/reka/images/" + list_data.get(0).get("gambar1"))
                                    .crossFade()
                                    .placeholder(R.drawable.noimage3)
                                    .into(sticker);
                            //txtnama.setText(list_data.get(0).get("nama"));
                            //txtharga.setText(list_data.get(0).get("harga_barang"));
                            //txtstock.setText(list_data.get(0).get("stock"));

                            Glide.with(getApplicationContext())
                                    .load("https://rekamitrayasa.com/reka/images/" + list_data.get(0).get("gambar2"))
                                    .crossFade()
                                    .placeholder(R.drawable.noimage3)
                                    .into(poster);
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

    private void KasAdapter2() {

        AndroidNetworking.post(Config.host + "dokumentasi2.php")
                .addBodyParameter("id", id.getText().toString())
                //.addBodyParameter("bulan1", bulan1.getText().toString())
                //.addBodyParameter("bulan2", bulan2.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response


                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
                        namasales.setText((response.optString("namasales")));
                        tanggal.setText((response.optString("tanggal")));
                        jam.setText((response.optString("jam")));
                        namaoutlet.setText((response.optString("namaoutlet")));
                        kecamatan.setText((response.optString("kecamatan")));
                        kelurahan.setText((response.optString("kelurahan")));
                        alasan.setText((response.optString("alasan")));
                        digipos.setText((response.optString("digipos")));
                        belanja_sp.setText((response.optString("belanja_sp")));
                        belanja_vcr.setText((response.optString("belanja_vcr")));

                        //nama221.setText((response.optString("nama")));

                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }




}