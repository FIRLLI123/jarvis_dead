package com.example.barcode;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;

import com.androidnetworking.error.ANError;

import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.barcode.helper.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class HargaVoucher extends AppCompatActivity {

    EditText idbarang, namabarang, hargabarang;
    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();
    ListView listinputperdana1;
    Button tambah, update, refresh, cariid;
    //TextView tanggal1, namasales1;

    private ProgressDialog pDialog;
    private Context context;

    public static String LINK, idbaranglist, namabaranglist, hargabaranglist;
    public static boolean filter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.harga_voucher);

        LINK = Config.host + "history.php";
        idbaranglist = "";
        namabaranglist = "";
        hargabaranglist = "";


        filter = false;


        context = HargaVoucher.this;
        pDialog = new ProgressDialog(context);

        listinputperdana1        = (ListView) findViewById(R.id.listinputperdana);
        idbarang = (EditText) findViewById(R.id.idbarang);
        namabarang = (EditText) findViewById(R.id.namabarang);
        hargabarang = (EditText) findViewById(R.id.hargabarang);

        tambah = (Button) findViewById(R.id.tambah);
        update = (Button) findViewById(R.id.update);
        refresh = (Button) findViewById(R.id.refresh);
        cariid = (Button) findViewById(R.id.cariid);



        list();


        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();


            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();


            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list();


            }
        });

        cariid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cariid();


            }
        });

    }

    private void cariid() {

        AndroidNetworking.post(Config.host + "barangvoucherid.php")
                .addBodyParameter("id", idbarang.getText().toString())
                //.addBodyParameter("bulan", sp.getSelectedItem().toString())
                //.addBodyParameter("bulan", bulan1.getText().toString())
                //.addBodyParameter("bulan1", bulan1.getText().toString())
                //.addBodyParameter("bulan2", bulan2.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response

                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
                        //idoutlet1.setText((response.optString("idoutlet")));
                        //namaoutlet1.setText((response.optString("namaoutlet")));
                        //namabarang1.setText((response.optString("item")));
                        namabarang.setText((response.optString("item")));
                        hargabarang.setText((response.optString("harga")));
                        //actualhero1.setText((response.optString("actual")));
                        //achhero1.setText((response.optString("ach")));
                        //gaphero1.setText((response.optString("gap")));
                        //bulan1.setText((response.optString("bulan")));

                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }


    private void list(){

        //swipe_refresh.setRefreshing(true);
        aruskas.clear(); listinputperdana1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "hargavoucher.php" )
//                .addBodyParameter("idbarang", idbarang.getText().toString())
//                .addBodyParameter("namabarang", namabarang.getText().toString())
//                .addBodyParameter("hargabarang", hargabarang.getText().toString())
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
                            JSONArray jsonArray = response.optJSONArray("result");
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject responses    = jsonArray.getJSONObject(i);
                                HashMap<String, String> map = new HashMap<String, String>();
                                //map.put("no",         responses.optString("no"));
                                map.put("id",         responses.optString("id"));
                                map.put("item",       responses.optString("item"));
                                map.put("harga",       responses.optString("harga"));

                                //map.put("tanggal",      responses.optString("tanggal"));

                                aruskas.add(map);
                            }

                            Adapter();

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


    private void Adapter(){

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.listharga,
                new String[] {"id","item","harga"},
                new int[] {R.id.idbaranglistharga, R.id.namabaranglistharga, R.id.hargabaranglistharga});

        listinputperdana1.setAdapter(simpleAdapter);
        listinputperdana1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //no    = ((TextView) view.findViewById(R.id.no)).getText().toString();
                idbaranglist    = ((TextView) view.findViewById(R.id.idbaranglistharga)).getText().toString();
                namabaranglist  = ((TextView) view.findViewById(R.id.namabaranglistharga)).getText().toString();
                hargabaranglist  = ((TextView) view.findViewById(R.id.hargabaranglistharga)).getText().toString();



                idbarang.setText(idbaranglist);
                namabarang.setText(namabaranglist);
                hargabarang.setText(hargabaranglist);

                //tanggal        = ((TextView) view.findViewById(R.id.tanggal)).getText().toString();
                //ListMenu();
            }
        });

        //swipe_refresh.setRefreshing(false);
    }


    private void save() {
        pDialog.setMessage("Process...");
        showDialog();
        pDialog.setCanceledOnTouchOutside(false);
        AndroidNetworking.post(Config.host + "inputhargavoucher.php")
                .addBodyParameter("id", idbarang.getText().toString())
                .addBodyParameter("item", namabarang.getText().toString())
                .addBodyParameter("harga", hargabarang.getText().toString())

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

                            list();
                            hideDialog();
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


    private void update() {
        pDialog.setMessage("Process...");
        showDialog();
        pDialog.setCanceledOnTouchOutside(false);
        AndroidNetworking.post(Config.host + "updatehargavoucher.php")
                .addBodyParameter("id", idbarang.getText().toString())
                .addBodyParameter("item", namabarang.getText().toString())
                .addBodyParameter("harga", hargabarang.getText().toString())

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
                            Toast.makeText(getApplicationContext(), "Berhasil Update",
                                    Toast.LENGTH_LONG).show();
                            list();
                            hideDialog();
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