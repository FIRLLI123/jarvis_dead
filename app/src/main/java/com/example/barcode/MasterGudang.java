package com.example.barcode;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
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




public class MasterGudang extends AppCompatActivity {
    TextView keseluruhan,modem,mentah,vinternet,pinternet, ttl;

    ListView listbayartempo;
    TextView hasilpilihan1;

    Button cari,btnsumtempooutlet;

    TextView tanggal1,namasales1,ttl1,ttl21;
    public static String idlist, idoutletlist, namaoutletlist, namasaleslist, namaitemtlist, qtylist, tanggallist, keteranganlist;
    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();

    int total = 0;

    private ProgressDialog pDialog;
    private Context context;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.master_gudang);

        context = MasterGudang.this;
        pDialog = new ProgressDialog(context);


        keseluruhan = (TextView) findViewById(R.id.keseluruhan);
        modem = (TextView) findViewById(R.id.modem);
        mentah = (TextView) findViewById(R.id.mentah);
        vinternet = (TextView) findViewById(R.id.vinternet);
        pinternet = (TextView) findViewById(R.id.pinternet);

        ttl = (TextView) findViewById(R.id.ttl);

        hasilpilihan1 = (TextView) findViewById(R.id.hasilpilihan1);

        listbayartempo = (ListView) findViewById(R.id.listitemtempo);



        keseluruhan.setBackground(getResources().getDrawable(R.drawable.custom11));
        keseluruhan.setTextColor(getResources().getColor(R.color.birutua));

        modem.setBackground(getResources().getDrawable(R.drawable.custom10));
        modem.setTextColor(getResources().getColor(R.color.abutua));

        mentah.setBackground(getResources().getDrawable(R.drawable.custom10));
        mentah.setTextColor(getResources().getColor(R.color.abutua));

        vinternet.setBackground(getResources().getDrawable(R.drawable.custom10));
        vinternet.setTextColor(getResources().getColor(R.color.abutua));

        pinternet.setBackground(getResources().getDrawable(R.drawable.custom10));
        pinternet.setTextColor(getResources().getColor(R.color.abutua));



        keseluruhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                keseluruhan.setBackground(getResources().getDrawable(R.drawable.custom11));
                keseluruhan.setTextColor(getResources().getColor(R.color.birutua));

                modem.setBackground(getResources().getDrawable(R.drawable.custom10));
                modem.setTextColor(getResources().getColor(R.color.abutua));

                mentah.setBackground(getResources().getDrawable(R.drawable.custom10));
                mentah.setTextColor(getResources().getColor(R.color.abutua));

                vinternet.setBackground(getResources().getDrawable(R.drawable.custom10));
                vinternet.setTextColor(getResources().getColor(R.color.abutua));

                pinternet.setBackground(getResources().getDrawable(R.drawable.custom10));
                pinternet.setTextColor(getResources().getColor(R.color.abutua));

                prosesdasboard1();

            }
        });


        modem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hasilpilihan1.setText("MODEM");
                keseluruhan.setBackground(getResources().getDrawable(R.drawable.custom10));
                keseluruhan.setTextColor(getResources().getColor(R.color.abutua));



                modem.setBackground(getResources().getDrawable(R.drawable.custom11));
                modem.setTextColor(getResources().getColor(R.color.birutua));

                mentah.setBackground(getResources().getDrawable(R.drawable.custom10));
                mentah.setTextColor(getResources().getColor(R.color.abutua));

                vinternet.setBackground(getResources().getDrawable(R.drawable.custom10));
                vinternet.setTextColor(getResources().getColor(R.color.abutua));

                pinternet.setBackground(getResources().getDrawable(R.drawable.custom10));
                pinternet.setTextColor(getResources().getColor(R.color.abutua));

                prosesdasboard2();

            }
        });


        mentah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hasilpilihan1.setText("MENTAH");
                keseluruhan.setBackground(getResources().getDrawable(R.drawable.custom10));
                keseluruhan.setTextColor(getResources().getColor(R.color.abutua));



                modem.setBackground(getResources().getDrawable(R.drawable.custom10));
                modem.setTextColor(getResources().getColor(R.color.abutua));

                mentah.setBackground(getResources().getDrawable(R.drawable.custom11));
                mentah.setTextColor(getResources().getColor(R.color.birutua));

                vinternet.setBackground(getResources().getDrawable(R.drawable.custom10));
                vinternet.setTextColor(getResources().getColor(R.color.abutua));

                pinternet.setBackground(getResources().getDrawable(R.drawable.custom10));
                pinternet.setTextColor(getResources().getColor(R.color.abutua));

                prosesdasboard2();

            }
        });

        vinternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hasilpilihan1.setText("VINTER");
                keseluruhan.setBackground(getResources().getDrawable(R.drawable.custom10));
                keseluruhan.setTextColor(getResources().getColor(R.color.abutua));



                modem.setBackground(getResources().getDrawable(R.drawable.custom10));
                modem.setTextColor(getResources().getColor(R.color.abutua));

                mentah.setBackground(getResources().getDrawable(R.drawable.custom10));
                mentah.setTextColor(getResources().getColor(R.color.abutua));

                vinternet.setBackground(getResources().getDrawable(R.drawable.custom11));
                vinternet.setTextColor(getResources().getColor(R.color.birutua));

                pinternet.setBackground(getResources().getDrawable(R.drawable.custom10));
                pinternet.setTextColor(getResources().getColor(R.color.abutua));

                prosesdasboard2();

            }
        });



        pinternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hasilpilihan1.setText("PINTER");
                keseluruhan.setBackground(getResources().getDrawable(R.drawable.custom10));
                keseluruhan.setTextColor(getResources().getColor(R.color.abutua));



                modem.setBackground(getResources().getDrawable(R.drawable.custom10));
                modem.setTextColor(getResources().getColor(R.color.abutua));

                mentah.setBackground(getResources().getDrawable(R.drawable.custom10));
                mentah.setTextColor(getResources().getColor(R.color.abutua));

                vinternet.setBackground(getResources().getDrawable(R.drawable.custom10));
                vinternet.setTextColor(getResources().getColor(R.color.abutua));

                pinternet.setBackground(getResources().getDrawable(R.drawable.custom11));
                pinternet.setTextColor(getResources().getColor(R.color.birutua));

                prosesdasboard2();

            }
        });

        keseluruhan();
        count_keseluruhan();

    }


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    public void prosesdasboard1(){

        new CountDownTimer(1000, 1000) {

            public void onTick(long millisUntilFinished) {
                pDialog.setMessage("Loading..."+ millisUntilFinished / 1000);
                showDialog();
                pDialog.setCanceledOnTouchOutside(false);

                keseluruhan();
                count_keseluruhan();
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                hideDialog();


            }
        }.start();

    }


    public void prosesdasboard2(){

        new CountDownTimer(1000, 1000) {

            public void onTick(long millisUntilFinished) {
                pDialog.setMessage("Loading..."+ millisUntilFinished / 1000);
                showDialog();
                pDialog.setCanceledOnTouchOutside(false);

                pilihan();
                count_pilihan();
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                hideDialog();


            }
        }.start();

    }


    private void keseluruhan(){

        //swipe_refresh.setRefreshing(true);
        aruskas.clear();
        listbayartempo.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "listmastergudang.php" )
//                .addBodyParameter("tanggaldari", tanggaldari.getText().toString())
//                .addBodyParameter("tanggalsampai", tanggalsampai.getText().toString())
//                .addBodyParameter("pembayaran1", tanggalsampai.getText().toString())
//                .addBodyParameter("pembayaran2", tanggalsampai.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

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
                            JSONArray jsonArray = response.optJSONArray("result");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Data_BayarEX item = new Data_BayarEX();
                                JSONObject responses    = jsonArray.getJSONObject(i);
                                HashMap<String, String> map = new HashMap<String, String>();
                                //map.put("no",         responses.optString("no"));

                                map.put("namaitem",       responses.optString("namaitem"));
                                //map.put("qty",       responses.optString("qty"));
                                map.put("qty",       rupiahFormat.format(responses.optDouble("qty")));


                                //total += Integer.parseInt(responses.getString("qty"));
                                //map.put("keterangan",       responses.optString("keterangan"));



                                //total += Integer.parseInt(responses.getString("harga"))* Integer.parseInt(responses.getString("qty"));
                                //map.put("tanggal",      responses.optString("tanggal"));

                                aruskas.add(map);
                                //bayarList.add(item);
                            }


                            Adapter();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


//                        String hasilttl = String.valueOf(total);
//                        ttl1.setText(hasilttl);
//                        total = 0;

//                        ttl.setText("Total : Rp "+formatter.format(total));
//                        total = 0;
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    private void Adapter(){

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.listmastergudang,
                new String[] {"namaitem","qty"},
                new int[] {R.id.namaitemlistsumtempoall, R.id.qtylistsumtempoall});

        listbayartempo.setAdapter(simpleAdapter);

        listbayartempo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //no    = ((TextView) view.findViewById(R.id.no)).getText().toString();







                //tanggal        = ((TextView) view.findViewById(R.id.tanggal)).getText().toString();
                //ListMenu();
            }
        });
        //swipe_refresh.setRefreshing(false);
    }

    private void pilihan(){

        //swipe_refresh.setRefreshing(true);
        aruskas.clear();
        listbayartempo.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "listmastergudanglist.php" )
                .addBodyParameter("kategori", hasilpilihan1.getText().toString())
//                .addBodyParameter("tanggalsampai", tanggalsampai.getText().toString())
//                .addBodyParameter("pembayaran1", tanggalsampai.getText().toString())
//                .addBodyParameter("pembayaran2", tanggalsampai.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

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
                            JSONArray jsonArray = response.optJSONArray("result");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Data_BayarEX item = new Data_BayarEX();
                                JSONObject responses    = jsonArray.getJSONObject(i);
                                HashMap<String, String> map = new HashMap<String, String>();
                                //map.put("no",         responses.optString("no"));

                                map.put("namaitem",       responses.optString("namaitem"));
                                map.put("qty",       rupiahFormat.format(responses.optDouble("qty")));



                                //total += Integer.parseInt(responses.getString("qty"));
                                //map.put("keterangan",       responses.optString("keterangan"));



                                //total += Integer.parseInt(responses.getString("harga"))* Integer.parseInt(responses.getString("qty"));
                                //map.put("tanggal",      responses.optString("tanggal"));

                                aruskas.add(map);
                                //bayarList.add(item);
                            }


                            Adapter2();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


//                        String hasilttl = String.valueOf(total);
//                        ttl1.setText(hasilttl);
//                        total = 0;

//                        ttl.setText("Total : Rp "+formatter.format(total));
//                        total = 0;
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    private void Adapter2(){

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.listmastergudang,
                new String[] {"namaitem","qty"},
                new int[] {R.id.namaitemlistsumtempoall, R.id.qtylistsumtempoall});

        listbayartempo.setAdapter(simpleAdapter);

        listbayartempo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        //swipe_refresh.setRefreshing(false);
    }


    private void count_keseluruhan() {

        AndroidNetworking.post(Config.host + "count_gudang.php")

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
                        ttl.setText((response.optString("id")));



                    }

                    @Override
                    public void onError(ANError error) {

                    }




                });

    }


    private void count_pilihan() {

        AndroidNetworking.post(Config.host + "count_gudang_list.php")
                .addBodyParameter("kategori", hasilpilihan1.getText().toString())
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
                        ttl.setText((response.optString("id")));



                    }

                    @Override
                    public void onError(ANError error) {

                    }




                });

    }

}