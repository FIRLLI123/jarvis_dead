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
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class TampilanOutlet extends AppCompatActivity {

    LinearLayout order, bayartempo,opperdana,opvoucher;
TextView namasales1, namaoutlet1, idoutlet1;
TextView namasaleskunjungan1, tanggalkunjungan1, jamkunjungan1;

    TextView namasalestele1, norstele1, pjptele1, statustele1;

    TextView itempenjualanperdana1, tanggalpenjualanperdana1, itempenjualanvoucher1, tanggalpenjualanvoucher1, selengkapnya;

    TextView jumlahtempo, jumlahdibayarkan;

    TextView tanggal;

    private ProgressDialog pDialog;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tampilan_outlet);

        order = (LinearLayout) findViewById(R.id.order);
        bayartempo = (LinearLayout) findViewById(R.id.bayartempo);
        opperdana = (LinearLayout) findViewById(R.id.opperdana);
        opvoucher = (LinearLayout) findViewById(R.id.opvoucher);


        namasales1 = (TextView) findViewById(R.id.namasales); //username
        namaoutlet1 = (TextView) findViewById(R.id.namaoutlet); //namaasli
        idoutlet1 = (TextView) findViewById(R.id.idoutlet); //namaasli

        tanggal = (TextView) findViewById(R.id.tanggal); //namaasli



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


        context = TampilanOutlet.this;
        pDialog = new ProgressDialog(context);

        order.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                    // TODO Auto-generated method stub
                    String a = idoutlet1.getText().toString();
                    String b = namaoutlet1.getText().toString();
                    String c = namasales1.getText().toString();
                    Intent i = new Intent(getApplicationContext(), MenuVersi3.class);
                    i.putExtra("idoutlet",""+a+"");
                    i.putExtra("namaoutlet",""+b+"");
                    i.putExtra("namasales",""+c+"");
                    startActivity(i);
                    //String b = namasf2.getText().toString();

                    // Intent i = new Intent(getApplicationContext(), Marsu1.class);
                    //i.putExtra("namasf", "" +a+ "");

                    // startActivity(i);




            }

        });


        bayartempo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // TODO Auto-generated method stub

                String a = idoutlet1.getText().toString();
                //String b = namaoutlet1.getText().toString();
                String c = namasales1.getText().toString();
                Intent i = new Intent(getApplicationContext(), ListBayarTempo.class);
                i.putExtra("idoutlet",""+a+"");
                i.putExtra("namasales",""+c+"");
                startActivity(i);



            }

        });




        opperdana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    // TODO Auto-generated method stub
                String a = idoutlet1.getText().toString();
                String b = namaoutlet1.getText().toString();
                String c = namasales1.getText().toString();
                String d = tanggal.getText().toString();
                    Intent i = new Intent(getApplicationContext(), Mutasi_Outlet.class);
                    i.putExtra("idoutlet",""+a+"");
                    i.putExtra("namaoutlet",""+b+"");
                    i.putExtra("namasales",""+c+"");
                    i.putExtra("tanggal",""+d+"");
                    startActivity(i);

            }
        });


tanggal.setText(getCurrentDate());


        opvoucher.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                    // TODO Auto-generated method stub
                String a = idoutlet1.getText().toString();
                String b = namaoutlet1.getText().toString();
                String c = namasales1.getText().toString();
                String d = tanggal.getText().toString();
                    Intent i = new Intent(getApplicationContext(), Print_Out_gabung.class);
                    i.putExtra("idoutlet",""+a+"");
                    i.putExtra("namaoutlet",""+b+"");
                    i.putExtra("namasales",""+c+"");
                i.putExtra("tanggal",""+d+"");
                    startActivity(i);

            }

        });




        selengkapnya.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                    // TODO Auto-generated method stub
                    String a = idoutlet1.getText().toString();
                    String b = namaoutlet1.getText().toString();
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



        Intent dasboard = getIntent();
        String kiriman1 = dasboard.getStringExtra("namasales");
        namasales1.setText(kiriman1);
        String kiriman2 = dasboard.getStringExtra("namaoutlet");
        namaoutlet1.setText(kiriman2);
        String kiriman3 = dasboard.getStringExtra("idoutlet");
        idoutlet1.setText(kiriman3);



        String kiriman4 = dasboard.getStringExtra("tanggalkunjungan");
        tanggalkunjungan1.setText(kiriman4);
        String kiriman5 = dasboard.getStringExtra("jamkunjungan");
        jamkunjungan1.setText(kiriman5);
        String kiriman6 = dasboard.getStringExtra("namasaleskunjungan");
        namasaleskunjungan1.setText(kiriman6);






        prosesdasboard();
        sudahdibayarkan();
        masihtempo();

    }


    public String getCurrentDate() {
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", new Locale("id", "ID")); // Format tanggal dalam bahasa Indonesia
        return dateFormat.format(c.getTime());
    }


    private void dasboardtele() {

        AndroidNetworking.post(Config.host + "dasboardtele.php")
                .addBodyParameter("satu", idoutlet1.getText().toString())
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
                .addBodyParameter("idoutlet", idoutlet1.getText().toString())
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
                .addBodyParameter("idoutlet", idoutlet1.getText().toString())
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
                .addBodyParameter("idoutlet", idoutlet1.getText().toString())
                .addBodyParameter("namasales", namasales1.getText().toString())
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
                .addBodyParameter("idoutlet", idoutlet1.getText().toString())
                .addBodyParameter("namasales", namasales1.getText().toString())
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