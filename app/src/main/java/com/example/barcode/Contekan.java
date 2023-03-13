package com.example.barcode;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;

import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

//import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class Contekan extends AppCompatActivity {
    private ImageView imgbarang;
   TextView txtnama, txtharga, txtstock;

    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    ArrayList<HashMap<String, String>> list_data;

Button cek1;


    //public Spinner sp;

    //ImageView g10,g2,g3,g4,g5,g6,g7,g8,g9,g11;

    TextView txt_hasil;
    Spinner spinner_pendidikan;
    ProgressDialog pDialog;
    Adapter2 adapter;
    List<Dataa> listPendidikan = new ArrayList<Dataa>();

    public static final String url = "https://rekamitrayasa.com/reka/spinner.php";

    private static final String TAG = Contekan.class.getSimpleName();

    public static final String TAG_ID = "id";
    public static final String TAG_PENDIDIKAN = "nama";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contekan);

        String url = "http://rekamitrayasa.com/reka/getdata.php";
        imgbarang = (ImageView) findViewById(R.id.imgbarang);

        txtnama = (TextView) findViewById(R.id.txtnama);
        cek1 = (Button) findViewById(R.id.cek);


        requestQueue = Volley.newRequestQueue(Contekan.this);

        list_data = new ArrayList<HashMap<String, String>>();

        //txt_hasil = (TextView) findViewById(R.id.txt_hasil);
        spinner_pendidikan = (Spinner) findViewById(R.id.spinner_pendidikan);

        txtnama = (TextView) findViewById(R.id.txtnama);

        spinner_pendidikan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                txtnama.setText("" + listPendidikan.get(position).getPendidikan());
                KasAdapter2();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
                //KasAdapter2();
            }
        });

        adapter = new Adapter2(Contekan.this, listPendidikan);
        spinner_pendidikan.setAdapter((SpinnerAdapter) adapter);




        callData();
        //KasAdapter2();

//        sp = (Spinner) findViewById(R.id.spinner);
//
//        g10 = (ImageView) findViewById(R.id.p10);
//        g2 = (ImageView) findViewById(R.id.p2);
//        g3 = (ImageView) findViewById(R.id.p3);
//        g4 = (ImageView) findViewById(R.id.p4);
//        g5 = (ImageView) findViewById(R.id.p5);
//        g6 = (ImageView) findViewById(R.id.p6);
//        g7 = (ImageView) findViewById(R.id.p7);
//        g8 = (ImageView) findViewById(R.id.p8);
//        g9 = (ImageView) findViewById(R.id.p9);
//        g11 = (ImageView) findViewById(R.id.p11);
//
//
//        List<String> item = new ArrayList<>();
//
//        //item.add("--SELECT--");
//        item.add("-- SILAHKAN PILIH --");
//        item.add("DIGISTAR PERDANA");
//        item.add("MIGRASI USIM 4G");
//        item.add("CUAN COMBO FIT");
//        item.add("KIOS 4G");
//
//        item.add("PROGRAM MEJENG");
//        item.add("RENCENG PERDANA");
//        item.add("PRODUK ORBIT");
//        item.add("SPESIFIKASI ORBIT");
//        item.add("UBER TELKOMSEL");
//        item.add("SOBAT CUAN SEJAHTERA");
//
//
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Contekan.this,android.R.layout.simple_spinner_dropdown_item, item);
//
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        sp.setAdapter(adapter);
//
//        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                //alasan1.setText(sp.getSelectedItem().toString());
//
//
//
//
//                if (sp.getSelectedItem().toString().trim().equals("-- SILAHKAN PILIH --")) {
//                    g10.setVisibility(View.GONE);
//                    g2.setVisibility(View.GONE);
//                    g3.setVisibility(View.GONE);
//                    g4.setVisibility(View.GONE);
//                    g5.setVisibility(View.GONE);
//                    g6.setVisibility(View.GONE);
//                    g7.setVisibility(View.GONE);
//                    g8.setVisibility(View.GONE);
//                    g9.setVisibility(View.GONE);
//                    g11.setVisibility(View.GONE);
//
//                    //startActivity(new Intent(date.this,MainActivity.class));
//                }else if (sp.getSelectedItem().toString().trim().equals("DIGISTAR PERDANA")) {
//                    g10.setVisibility(View.VISIBLE);
//                    g2.setVisibility(View.GONE);
//                    g3.setVisibility(View.GONE);
//                    g4.setVisibility(View.GONE);
//                    g5.setVisibility(View.GONE);
//                    g6.setVisibility(View.GONE);
//                    g7.setVisibility(View.GONE);
//                    g8.setVisibility(View.GONE);
//                    g9.setVisibility(View.GONE);
//                    g11.setVisibility(View.GONE);
//
//                    //startActivity(new Intent(date.this,MainActivity.class));
//                }else if (sp.getSelectedItem().toString().trim().equals("MIGRASI USIM 4G")) {
//                    g10.setVisibility(View.GONE);
//                    g2.setVisibility(View.VISIBLE);
//                    g3.setVisibility(View.GONE);
//                    g4.setVisibility(View.GONE);
//                    g5.setVisibility(View.GONE);
//                    g6.setVisibility(View.GONE);
//                    g7.setVisibility(View.GONE);
//                    g8.setVisibility(View.GONE);
//                    g9.setVisibility(View.GONE);
//                    g11.setVisibility(View.GONE);
//
//                    //startActivity(new Intent(date.this,MainActivity.class));
//                }else if (sp.getSelectedItem().toString().trim().equals("CUAN COMBO FIT")) {
//                    g10.setVisibility(View.GONE);
//                    g2.setVisibility(View.GONE);
//                    g3.setVisibility(View.VISIBLE);
//                    g4.setVisibility(View.GONE);
//                    g5.setVisibility(View.GONE);
//                    g6.setVisibility(View.GONE);
//                    g7.setVisibility(View.GONE);
//                    g8.setVisibility(View.GONE);
//                    g9.setVisibility(View.GONE);
//                    g11.setVisibility(View.GONE);
//
//                    //startActivity(new Intent(date.this,MainActivity.class));
//                }else if (sp.getSelectedItem().toString().trim().equals("KIOS 4G")) {
//                    g10.setVisibility(View.GONE);
//                    g2.setVisibility(View.GONE);
//                    g3.setVisibility(View.GONE);
//                    g4.setVisibility(View.VISIBLE);
//                    g5.setVisibility(View.GONE);
//                    g6.setVisibility(View.GONE);
//                    g7.setVisibility(View.GONE);
//                    g8.setVisibility(View.GONE);
//                    g9.setVisibility(View.GONE);
//                    g11.setVisibility(View.GONE);
//
//                    //startActivity(new Intent(date.this,MainActivity.class));
//                }else if (sp.getSelectedItem().toString().trim().equals("PROGRAM MEJENG")) {
//                    g10.setVisibility(View.GONE);
//                    g2.setVisibility(View.GONE);
//                    g3.setVisibility(View.GONE);
//                    g4.setVisibility(View.GONE);
//                    g5.setVisibility(View.VISIBLE);
//                    g6.setVisibility(View.GONE);
//                    g7.setVisibility(View.GONE);
//                    g8.setVisibility(View.GONE);
//                    g9.setVisibility(View.GONE);
//                    g11.setVisibility(View.GONE);
//                    //startActivity(new Intent(date.this,MainActivity.class));
//                }else if (sp.getSelectedItem().toString().trim().equals("RENCENG PERDANA")) {
//                    g10.setVisibility(View.GONE);
//                    g2.setVisibility(View.GONE);
//                    g3.setVisibility(View.GONE);
//                    g4.setVisibility(View.GONE);
//                    g5.setVisibility(View.GONE);
//                    g6.setVisibility(View.VISIBLE);
//                    g7.setVisibility(View.GONE);
//                    g8.setVisibility(View.GONE);
//                    g9.setVisibility(View.GONE);
//                    g11.setVisibility(View.GONE);;
//
//                    //startActivity(new Intent(date.this,MainActivity.class));
//                }else if (sp.getSelectedItem().toString().trim().equals("PRODUK ORBIT")) {
//                    g10.setVisibility(View.GONE);
//                    g2.setVisibility(View.GONE);
//                    g3.setVisibility(View.GONE);
//                    g4.setVisibility(View.GONE);
//                    g5.setVisibility(View.GONE);
//                    g6.setVisibility(View.GONE);
//                    g7.setVisibility(View.GONE);
//                    g8.setVisibility(View.GONE);
//                    g9.setVisibility(View.GONE);
//                    g11.setVisibility(View.VISIBLE);
//
//                    //startActivity(new Intent(date.this,MainActivity.class));
//                }else if (sp.getSelectedItem().toString().trim().equals("SPESIFIKASI ORBIT")) {
//                    g10.setVisibility(View.GONE);
//                    g2.setVisibility(View.GONE);
//                    g3.setVisibility(View.GONE);
//                    g4.setVisibility(View.GONE);
//                    g5.setVisibility(View.GONE);
//                    g6.setVisibility(View.GONE);
//                    g7.setVisibility(View.VISIBLE);
//                    g8.setVisibility(View.GONE);
//                    g9.setVisibility(View.GONE);
//                    g11.setVisibility(View.GONE);
//
//                    //startActivity(new Intent(date.this,MainActivity.class));
//                }else if (sp.getSelectedItem().toString().trim().equals("UBER TELKOMSEL")) {
//                    g10.setVisibility(View.GONE);
//                    g2.setVisibility(View.GONE);
//                    g3.setVisibility(View.GONE);
//                    g4.setVisibility(View.GONE);
//                    g5.setVisibility(View.GONE);
//                    g6.setVisibility(View.GONE);
//                    g7.setVisibility(View.GONE);
//                    g8.setVisibility(View.VISIBLE);
//                    g9.setVisibility(View.GONE);
//                    g11.setVisibility(View.GONE);
//
//                    //startActivity(new Intent(date.this,MainActivity.class));
//                }else if (sp.getSelectedItem().toString().trim().equals("SOBAT CUAN SEJAHTERA")) {
//                    g10.setVisibility(View.GONE);
//                    g2.setVisibility(View.GONE);
//                    g3.setVisibility(View.GONE);
//                    g4.setVisibility(View.GONE);
//                    g5.setVisibility(View.GONE);
//                    g6.setVisibility(View.GONE);
//                    g7.setVisibility(View.GONE);
//                    g8.setVisibility(View.GONE);
//                    g9.setVisibility(View.VISIBLE);
//                    g11.setVisibility(View.GONE);
//
//                    //startActivity(new Intent(date.this,MainActivity.class));
//                } else {
//
//                }
////                        startActivity(new Intent(date.this,august
////                                .class));
//
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

    }

    private void callData() {
        listPendidikan.clear();

        pDialog = new ProgressDialog(Contekan.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading...");
        showDialog();

        // Creating volley request obj
        JsonArrayRequest jArr = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e(TAG, response.toString());

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);

                                Dataa item = new Dataa();

                                item.setId(obj.getString(TAG_ID));
                                item.setPendidikan(obj.getString(TAG_PENDIDIKAN));

                                listPendidikan.add(item);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();

                        hideDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(Contekan.this, error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jArr);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void KasAdapter2() {

        //swipe_refresh.setRefreshing(true);
        list_data.clear();
//        listinputperdana1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post(Config.host + "getdata.php")
                .addBodyParameter("nama", txtnama.getText().toString())
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
                                map.put("gambar", responses.optString("gambar"));


                                //map.put("tanggal",      responses.optString("tanggal"));

                                list_data.add(map);
                            }

                            Glide.with(getApplicationContext())
                                    .load("http://rekamitrayasa.com/reka/img/" + list_data.get(0).get("gambar"))
                                    .crossFade()
                                    .placeholder(R.drawable.noimage3)
                                    .into(imgbarang);
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