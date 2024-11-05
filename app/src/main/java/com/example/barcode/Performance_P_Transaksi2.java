package com.example.barcode;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
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
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class Performance_P_Transaksi2 extends AppCompatActivity {

    TextView idoutlet, namasales, sum_satu, sum_dua, sum_tiga, sum_empat, sum_lima, sum_enam, sum_tujuh,bulan,pjp_text;

    ListView listtest1;

    ImageView search;

    Spinner pjp;

    private ProgressDialog pDialog;
    private Context context;
    Handler mHandler;

    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    TextView namakaryawan;



    CircleImageView img;


    ArrayList<HashMap<String, String>> list_data;

    //ArrayList<HashMap<String, String>> list_data;

    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.performance_p_transaksi2);

        //list_data = new ArrayList<HashMap<String, String>>();

        this.mHandler = new Handler();
        m_Runnable.run();
        list_data = new ArrayList<HashMap<String, String>>();

        context = Performance_P_Transaksi2.this;
        pDialog = new ProgressDialog(context);

        search = (ImageView) findViewById(R.id.search);

        listtest1 = (ListView) findViewById(R.id.listtest);
        img=(CircleImageView)findViewById(R.id.imgbarang);

        pjp=(Spinner) findViewById(R.id.pjp);


        //namakaryawan = (TextView) findViewById(R.id.namakaryawan);

        idoutlet = (TextView) findViewById(R.id.idoutlet);
        namasales = (TextView) findViewById(R.id.namasales);
        bulan = (TextView) findViewById(R.id.bulan);
        sum_satu = (TextView) findViewById(R.id.sum_satu);
        sum_dua = (TextView) findViewById(R.id.sum_dua);
        sum_tiga = (TextView) findViewById(R.id.sum_tiga);
        sum_empat = (TextView) findViewById(R.id.sum_empat);
        sum_lima = (TextView) findViewById(R.id.sum_lima);
        sum_enam = (TextView) findViewById(R.id.sum_enam);
        sum_tujuh = (TextView) findViewById(R.id.sum_tujuh);
        pjp_text=(TextView) findViewById(R.id.pjp_text);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                sum_detail();

                list();


            }
        });

        Intent kolomlogin = getIntent();

        String kiriman1 = kolomlogin.getStringExtra("namasales");
        namasales.setText(kiriman1);

        String kiriman2 = kolomlogin.getStringExtra("bulan");
        bulan.setText(kiriman2);

        sum_detail();

        //list();
        prosesdasboard();


        List<String> item = new ArrayList<>();

        item.add("SENIN");
        item.add("SELASA");
        item.add("RABU");
        item.add("KAMIS");
        item.add("JUMAT");
        item.add("SABTU");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Performance_P_Transaksi2.this,android.R.layout.simple_spinner_dropdown_item, item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        pjp.setAdapter(adapter);

        pjp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //alasan1.setText(sp.getSelectedItem().toString());

                pjp_text.setText(pjp.getSelectedItem().toString());




            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    private final Runnable m_Runnable = new Runnable() {
        public void run() {
            //Toast.makeText(AbsendanIzin.this, "", Toast.LENGTH_SHORT).show();
            namasales = (TextView) findViewById(R.id.namasales);
            list_data = new ArrayList<HashMap<String, String>>();
            KasAdapter3();

            Performance_P_Transaksi2.this.mHandler.postDelayed(m_Runnable, 2000);
        }

    };

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public void prosesdasboard(){
        list();
        new CountDownTimer(2000, 1000) {

            public void onTick(long millisUntilFinished) {
                pDialog.setMessage("TUNGGU SEBENTAR, SEDANG VERIFIKASI DATA :"+ millisUntilFinished / 1000);
                showDialog();
                pDialog.setCanceledOnTouchOutside(false);
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                hideDialog();
                KasAdapter3();


            }
        }.start();

    }


    private void KasAdapter3() {

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

    private void sum_detail() {

        AndroidNetworking.post(Config.host + "count_detail_perdana.php")
                .addBodyParameter("empat", namasales.getText().toString())
                .addBodyParameter("tiga", bulan.getText().toString())

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
                        sum_satu.setText((response.optString("satu")));
                        sum_dua.setText((response.optString("sembilan")));
                        sum_tiga.setText((response.optString("sepuluh")));
                        sum_empat.setText((response.optString("sebelas")+"%"));
                        sum_lima.setText(
                                rupiahFormat.format(response.optDouble("tigabelas")));
                        sum_enam.setText(
                                rupiahFormat.format(response.optDouble("empatbelas")));
                        sum_tujuh.setText((response.optString("limabelas")+"%"));





                    }

                    @Override
                    public void onError(ANError error) {

                    }




                });

    }


    private void list(){

        //swipe_refresh.setRefreshing(true);
        aruskas.clear();
        listtest1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "list_detail_outlet_nama.php" )
                .addBodyParameter("satu", idoutlet.getText().toString())
                .addBodyParameter("empat", namasales.getText().toString())
                .addBodyParameter("tiga", bulan.getText().toString())
                .addBodyParameter("enambelas", pjp_text.getText().toString())

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
                                Data_BayarEX item = new Data_BayarEX();
                                JSONObject responses    = jsonArray.getJSONObject(i);
                                HashMap<String, String> map = new HashMap<String, String>();
                                //map.put("no",         responses.optString("no"));
                                map.put("satu",         responses.optString("satu"));
                                map.put("dua",       responses.optString("dua"));
                                map.put("lima",       responses.optString("lima"));
                                map.put("sembilan",       responses.optString("sembilan"));
                                map.put("sepuluh",       responses.optString("sepuluh"));
                                map.put("sebelas",       responses.optString("sebelas"));
                                map.put("duabelas",       responses.optString("duabelas"));
                                map.put("tigabelas",       rupiahFormat.format(responses.optDouble("tigabelas")));
                                map.put("empatbelas",       rupiahFormat.format(responses.optDouble("empatbelas")));
                                map.put("limabelas",       responses.optString("limabelas"));





                                //total += Integer.parseInt(responses.getString("harga"))* Integer.parseInt(responses.getString("qty"));
                                //map.put("tanggal",      responses.optString("tanggal"));

                                aruskas.add(map);
                                //bayarList.add(item);
                            }

                            Adapter();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.list_detail_outlet_nama,
                new String[] {"satu","dua","lima","sembilan","sepuluh","sebelas","duabelas","tigabelas","empatbelas","limabelas"},
                new int[] {R.id.satulist,R.id.dualist,R.id.limalist,R.id.sembilanlist,R.id.sepuluhlist,R.id.sebelaslist,R.id.duabelaslist,R.id.tigabelaslist,R.id.empatbelaslist,R.id.limabelaslist});

        listtest1.setAdapter(simpleAdapter);

        listtest1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {





            }
        });

        //swipe_refresh.setRefreshing(false);
    }



}