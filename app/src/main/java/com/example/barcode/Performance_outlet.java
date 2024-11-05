package com.example.barcode;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Locale;

public class Performance_outlet extends AppCompatActivity implements View.OnClickListener {
LinearLayout scan, cari_outlet, ada_data, no_data;
TextView idoutlet, namaoutlet;

    LinearLayout order, bayartempo,opperdana,opvoucher;
    //TextView namasales1, namaoutlet1, idoutlet1;
    TextView namasaleskunjungan1, tanggalkunjungan1, jamkunjungan1;

    TextView namasalestele1, norstele1, pjptele1, statustele1;

    TextView itempenjualanperdana1, tanggalpenjualanperdana1, itempenjualanvoucher1, tanggalpenjualanvoucher1, selengkapnya;

    TextView jumlahtempo, jumlahdibayarkan;

    private ProgressDialog pDialog;
    private Context context;

    private IntentIntegrator intentIntegrator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.performance_outlet);

        scan = (LinearLayout) findViewById(R.id.scan);
        cari_outlet = (LinearLayout) findViewById(R.id.cari_outlet);

        ada_data = (LinearLayout) findViewById(R.id.data);
        no_data = (LinearLayout) findViewById(R.id.no_data);


        idoutlet = (TextView) findViewById(R.id.idoutlet);
        namaoutlet = (TextView) findViewById(R.id.namaoutlet);


        scan.setOnClickListener(this);


        cari_outlet.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                Intent i = new Intent(getApplicationContext(), DataOutlet_Performance.class);
                startActivity(i);



            }

        });


        Intent i = getIntent();
        String kiriman = i.getStringExtra("namaoutlet");
        namaoutlet.setText(kiriman);
        String kiriman2 = i.getStringExtra("idoutlet");
        idoutlet.setText(kiriman2);


        if ( namaoutlet.getText().toString().isEmpty()){
            no_data.setVisibility(View.VISIBLE);
            ada_data.setVisibility(View.GONE);



        }else{
            no_data.setVisibility(View.GONE);
            ada_data.setVisibility(View.VISIBLE);
            prosesdasboard();
            sudahdibayarkan();
            masihtempo();
            kunjunganterakhir();

        }



        tanggalkunjungan1 = (TextView) findViewById(R.id.tanggalkunjungan); //username
        jamkunjungan1 = (TextView) findViewById(R.id.jamkunjungan); //namaasli
        namasaleskunjungan1 = (TextView) findViewById(R.id.namasaleskunjungan); //namaasli

        jumlahtempo = (TextView) findViewById(R.id.jumlahtempo); //namaasli
        jumlahdibayarkan = (TextView) findViewById(R.id.jumlahdibayarkan); //namaasli

        namasalestele1 = (TextView) findViewById(R.id.namasalestele); //username
        norstele1 = (TextView) findViewById(R.id.norstele); //namaasli
        pjptele1 = (TextView) findViewById(R.id.pjptele); //namaasli
        statustele1 = (TextView) findViewById(R.id.statustele); //namaasli

        itempenjualanperdana1 = (TextView) findViewById(R.id.itempenjualanperdana); //username
        tanggalpenjualanperdana1 = (TextView) findViewById(R.id.tanggalpenjualanperdana); //namaasli
        itempenjualanvoucher1 = (TextView) findViewById(R.id.itempenjualanvoucher); //namaasli
        tanggalpenjualanvoucher1 = (TextView) findViewById(R.id.tanggalpenjualanvoucher); //namaasli

        selengkapnya = (TextView) findViewById(R.id.selengkapnya); //namaasli

        context = Performance_outlet.this;
        pDialog = new ProgressDialog(context);



        selengkapnya.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // TODO Auto-generated method stub
                String a = idoutlet.getText().toString();
                String b = namaoutlet.getText().toString();
                Intent i = new Intent(getApplicationContext(), DataTelegram.class);
                i.putExtra("idoutlet",""+a+"");
                i.putExtra("namaoutlet",""+b+"");
                startActivity(i);
                //String b = namasf2.getText().toString();

                // Intent i = new Intent(getApplicationContext(), Marsu1.class);
                //i.putExtra("namasf", "" +a+ "");

                // startActivity(i);

            }

        });


//        prosesdasboard();
//        sudahdibayarkan();
//        masihtempo();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null){
            if (result.getContents() == null){
                Toast.makeText(this, "Hasil tidak ditemukan", Toast.LENGTH_SHORT).show();
            }else{
                // jika qrcode berisi data
                try{
                    // converting the data json
                    JSONObject object = new JSONObject(result.getContents());
                    // atur nilai ke textviews
                    idoutlet.setText(object.getString("idoutlet"));
                    namaoutlet.setText(object.getString("namaoutlet"));
                    //namasales1.setText(object.getString("namasales"));
                    //namasalesbackup1.setText(object.getString("namasalesbackup"));
                    //edittext991.setText(object.getString("namasalesbackup"));
                    //sls1.setText(object.getString("namasalesbackup"));


                    no_data.setVisibility(View.GONE);
                    ada_data.setVisibility(View.VISIBLE);
                    prosesdasboard();
                    sudahdibayarkan();
                    masihtempo();
                    kunjunganterakhir();

                }catch (JSONException e){
                    e.printStackTrace();
                    // jika format encoded tidak sesuai maka hasil
                    // ditampilkan ke toast
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
                }


            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }



    @Override
    public void onClick(View view) {


        intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.initiateScan();
    }



    private void kunjunganterakhir() {

        AndroidNetworking.post(Config.host + "kunjunganterakhir.php")
                .addBodyParameter("idoutlet", idoutlet.getText().toString())
                //.addBodyParameter("bulan1", bulan1.getText().toString())
                //.addBodyParameter("bulan2", bulan2.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response


                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
                        //idoutmenu1.setText((response.optString("idoutlet")));
                        namasaleskunjungan1.setText((response.optString("namasalesbackup")));




                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }



    private void dasboardtele() {

        AndroidNetworking.post(Config.host + "dasboardtele.php")
                .addBodyParameter("satu", idoutlet.getText().toString())
//                .addBodyParameter("namasales", namasalesinputperdana1.getText().toString())
//                .addBodyParameter("tanggal", tanggalinputperdana1.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response



                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);

                        namasalestele1.setText((response.optString("sepuluh")));
                        norstele1.setText((response.optString("dua")));
                        pjptele1.setText((response.optString("delapan")));
                        statustele1.setText((response.optString("sebelas")));



                        if ( namasalestele1.getText().toString().equals("null")){
                            namasalestele1.setText("BELUM TERDATA");

                        }else if ( norstele1.getText().toString().equals("null")){
                            norstele1.setText("BELUM TERDATA");

                        }else if ( pjptele1.getText().toString().equals("null")){
                            pjptele1.setText("BELUM TERDATA");

                        }else if ( statustele1.getText().toString().equals("null")){
                            statustele1.setText("BELUM TERDATA");

                        }else{


                        }


                    }

                    @Override
                    public void onError(ANError error) {

                    }




                });

    }


    private void terakhirperdana() {

        AndroidNetworking.post(Config.host + "terakhirperdana.php")
                .addBodyParameter("idoutlet", idoutlet.getText().toString())
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
                        tanggalpenjualanperdana1.setText((response.optString("tanggal")));

                        if ( tanggalpenjualanperdana1.getText().toString().equals("null")){
                            tanggalpenjualanperdana1.setText("BELUM ADA TRANSAKSI");

                        }else{


                        }

                    }

                    @Override
                    public void onError(ANError error) {

                    }




                });

    }


    private void terakhirvoucher() {

        AndroidNetworking.post(Config.host + "terakhirvoucher.php")
                .addBodyParameter("idoutlet", idoutlet.getText().toString())
//                .addBodyParameter("namasales", namasalesinputperdana1.getText().toString())
//                .addBodyParameter("tanggal", tanggalinputperdana1.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response


                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);

                        //itempenjualanvoucher1.setText((response.optString("item")));
                        tanggalpenjualanvoucher1.setText((response.optString("tanggal")));

                        if ( tanggalpenjualanvoucher1.getText().toString().equals("null")){
                            tanggalpenjualanvoucher1.setText("BELUM ADA TRANSAKSI");

                        }else{


                        }
                    }

                    @Override
                    public void onError(ANError error) {

                    }




                });

    }



    public void prosesdasboard(){
        dasboardtele();
        terakhirperdana();
        terakhirvoucher();
        new CountDownTimer(2000, 1000) {

            public void onTick(long millisUntilFinished) {
                pDialog.setMessage("TUNGGU SEBENTAR, SEDANG VERIFIKASI DATA :"+ millisUntilFinished / 1000);
                showDialog();
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


    private void masihtempo() {

        AndroidNetworking.post(Config.host + "masihtempo.php")
                .addBodyParameter("idoutlet", idoutlet.getText().toString())
                //.addBodyParameter("namasales", namasales.getText().toString())
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
                        jumlahtempo.setText((response.optString("pembayaran")));
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


    private void sudahdibayarkan() {

        AndroidNetworking.post(Config.host + "sudahdibayar.php")
                .addBodyParameter("idoutlet", idoutlet.getText().toString())
                //.addBodyParameter("namasales", namasales.getText().toString())
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
                        jumlahdibayarkan.setText((response.optString("pembayaran")));
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


}