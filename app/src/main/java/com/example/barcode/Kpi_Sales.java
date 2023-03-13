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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class Kpi_Sales extends AppCompatActivity {
    public Spinner  sp_namasales, sp_bulan;

    TextView bulan;


    TextView satu_t, dua_t, tiga_t, empat_t, lima_t, enam_t, tujuh_t, delapan_t, sembilan_t, sepuluh_t;
    TextView sebelas_t, duabelas_t, tigabelas_t, empatbelas_t, limabelas_t, enambelas_t, tujuhbelas_t, delapanbelas_t, sembilanbelas_t, duapuluh_t;
    TextView duasatu_t, duadua_t,duatiga_t, duaempat_t,dualima_t, duaenam_t,duatujuh_t, duadelapan_t,duasembilan_t, tigapuluh_t;

    TextView tigasatu_t, tigadua_t,tigatiga_t, tigaempat_t,tigalima_t, tigaenam_t,tigatujuh_t, tigadelapan_t,tigasembilan_t, empatpuluh_t;
    TextView empatsatu_t, empatdua_t,empattiga_t, empatempat_t,empatlima_t, empatenam_t,empattujuh_t, empatdelapan_t,empatsembilan_t, limapuluh_t;
    TextView limasatu_t, limadua_t,limatiga_t, limaempat_t,limalima_t, limaenam_t,limatujuh_t, limadelapan_t,limasembilan_t, enampuluh_t;
    TextView enamsatu_t, enamdua_t,enamtiga_t, enamempat_t,enamlima_t, enamenam_t,enamtujuh_t, enamdelapan_t,enamsembilan_t, tujuhpuluh_t;
    TextView tujuhsatu_t, tujuhdua_t,tujuhtiga_t, tujuhempat_t,tujuhlima_t, tujuhenam_t,tujuhtujuh_t, tujuhdelapan_t,tujuhsembilan_t, delapanpuluh_t;

    TextView namakaryawan;

    private ProgressDialog pDialog;
    private Context context;

    CircleImageView img;
    Handler mHandler;

    ArrayList<HashMap<String, String>> list_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kpi_sales);




        this.mHandler = new Handler();
        //m_Runnable.run();

        context = Kpi_Sales.this;
        pDialog = new ProgressDialog(context);

        list_data = new ArrayList<HashMap<String, String>>();




        satu_t = (TextView) findViewById(R.id.satu);
        dua_t = (TextView) findViewById(R.id.dua);
        tiga_t = (TextView) findViewById(R.id.tiga);
        empat_t = (TextView) findViewById(R.id.empat);
        lima_t = (TextView) findViewById(R.id.lima);
        enam_t = (TextView) findViewById(R.id.enam);
        tujuh_t = (TextView) findViewById(R.id.tujuh);
        delapan_t = (TextView) findViewById(R.id.delapan);
        sembilan_t = (TextView) findViewById(R.id.sembilan);
        sepuluh_t = (TextView) findViewById(R.id.sepuluh);
        sebelas_t = (TextView) findViewById(R.id.sebelas);
        duabelas_t = (TextView) findViewById(R.id.duabelas);
        tigabelas_t = (TextView) findViewById(R.id.tigabelas);
        empatbelas_t = (TextView) findViewById(R.id.empatbelas);
        limabelas_t = (TextView) findViewById(R.id.limabelas);
        enambelas_t = (TextView) findViewById(R.id.enambelas);
        tujuhbelas_t = (TextView) findViewById(R.id.tujuhbelas);
        delapanbelas_t = (TextView) findViewById(R.id.delapanbelas);
        sembilanbelas_t = (TextView) findViewById(R.id.sembilanbelas);
        duapuluh_t = (TextView) findViewById(R.id.duapuluh);

        duasatu_t = (TextView) findViewById(R.id.duasatu);
        duadua_t = (TextView) findViewById(R.id.duadua);
        duatiga_t = (TextView) findViewById(R.id.duatiga);
        duaempat_t = (TextView) findViewById(R.id.duaempat);
        dualima_t = (TextView) findViewById(R.id.dualima);
        duaenam_t = (TextView) findViewById(R.id.duaenam);
        duatujuh_t = (TextView) findViewById(R.id.duatujuh);
        duadelapan_t = (TextView) findViewById(R.id.duadelapan);
        duasembilan_t = (TextView) findViewById(R.id.duasembilan);
        tigapuluh_t = (TextView) findViewById(R.id.tigapuluh);

        tigasatu_t = (TextView) findViewById(R.id.tigasatu);
        tigadua_t = (TextView) findViewById(R.id.tigadua);
        tigatiga_t = (TextView) findViewById(R.id.tigatiga);
        tigaempat_t = (TextView) findViewById(R.id.tigaempat);
        tigalima_t = (TextView) findViewById(R.id.tigalima);
        tigaenam_t = (TextView) findViewById(R.id.tigaenam);
        tigatujuh_t = (TextView) findViewById(R.id.tigatujuh);
        tigadelapan_t = (TextView) findViewById(R.id.tigadelapan);
        tigasembilan_t = (TextView) findViewById(R.id.tigasembilan);
        empatpuluh_t = (TextView) findViewById(R.id.empatpuluh);

        empatsatu_t = (TextView) findViewById(R.id.empatsatu);
        empatdua_t = (TextView) findViewById(R.id.empatdua);
        empattiga_t = (TextView) findViewById(R.id.empattiga);
        empatempat_t = (TextView) findViewById(R.id.empatempat);
        empatlima_t = (TextView) findViewById(R.id.empatlima);
        empatenam_t = (TextView) findViewById(R.id.empatenam);
        empattujuh_t = (TextView) findViewById(R.id.empattujuh);
        empatdelapan_t = (TextView) findViewById(R.id.empatdelapan);
        empatsembilan_t = (TextView) findViewById(R.id.empatsembilan);
        limapuluh_t = (TextView) findViewById(R.id.limapuluh);

        limasatu_t = (TextView) findViewById(R.id.limasatu);
        limadua_t = (TextView) findViewById(R.id.limadua);
        limatiga_t = (TextView) findViewById(R.id.limatiga);
        limaempat_t = (TextView) findViewById(R.id.limaempat);
        limalima_t = (TextView) findViewById(R.id.limalima);
        limaenam_t = (TextView) findViewById(R.id.limaenam);
        limatujuh_t = (TextView) findViewById(R.id.limatujuh);
        limadelapan_t = (TextView) findViewById(R.id.limadelapan);
        limasembilan_t = (TextView) findViewById(R.id.limasembilan);
        enampuluh_t = (TextView) findViewById(R.id.enampuluh);

        enamsatu_t = (TextView) findViewById(R.id.enamsatu);
        enamdua_t = (TextView) findViewById(R.id.enamdua);
        enamtiga_t = (TextView) findViewById(R.id.enamtiga);
        enamempat_t = (TextView) findViewById(R.id.enamempat);
        enamlima_t = (TextView) findViewById(R.id.enamlima);
        enamenam_t = (TextView) findViewById(R.id.enamenam);
        enamtujuh_t = (TextView) findViewById(R.id.enamtujuh);
        enamdelapan_t = (TextView) findViewById(R.id.enamdelapan);
        enamsembilan_t = (TextView) findViewById(R.id.enamsembilan);
        tujuhpuluh_t = (TextView) findViewById(R.id.tujuhpuluh);

        tujuhsatu_t = (TextView) findViewById(R.id.tujuhsatu);
        tujuhdua_t = (TextView) findViewById(R.id.tujuhdua);
        tujuhtiga_t = (TextView) findViewById(R.id.tujuhtiga);
        tujuhempat_t = (TextView) findViewById(R.id.tujuhempat);
        tujuhlima_t = (TextView) findViewById(R.id.tujuhlima);
        tujuhenam_t = (TextView) findViewById(R.id.tujuhenam);
        tujuhtujuh_t = (TextView) findViewById(R.id.tujuhtujuh);

        namakaryawan = (TextView) findViewById(R.id.namakaryawan);

        Intent dasboard = getIntent();
        String kiriman1 = dasboard.getStringExtra("bulan");
        dua_t.setText(kiriman1);

        String kiriman2 = dasboard.getStringExtra("namasales");
        satu_t.setText(kiriman2);

        String kiriman3 = dasboard.getStringExtra("namasales");
        namakaryawan.setText(kiriman3);




        img=(CircleImageView)findViewById(R.id.imgbarang);

        prosesdasboard();
        KasAdapter3();








    }

    public String getCurrentMonth() {
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", new Locale("id", "ID")); // MMMM untuk nama bulan lengkap
        return monthFormat.format(c.getTime());
    }

    private final Runnable m_Runnable = new Runnable() {
        public void run() {
            //Toast.makeText(AbsendanIzin.this, "", Toast.LENGTH_SHORT).show();

            //namakaryawan = (TextView) findViewById(R.id.namakaryawan);
            //prosesdasboard();
            list_data = new ArrayList<HashMap<String, String>>();
            KasAdapter3();

        }

    };



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




    public void prosesdasboard(){
        kpisales();
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


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }



    private void kpisales() {

        AndroidNetworking.post(Config.host + "kpisalesjarvis.php")
                .addBodyParameter("satu", satu_t.getText().toString())
                .addBodyParameter("dua", dua_t.getText().toString())
//                .addBodyParameter("namasales", namasalesinputperdana1.getText().toString())
//                .addBodyParameter("tanggal", tanggalinputperdana1.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response


                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);

                        satu_t.setText((response.optString("satu")));
                        dua_t.setText((response.optString("dua")));
                        tiga_t.setText((response.optString("tiga")));
                        empat_t.setText((response.optString("empat")));
                        lima_t.setText((response.optString("lima")));
                        enam_t.setText((response.optString("enam")));
                        tujuh_t.setText((response.optString("tujuh")));
                        delapan_t.setText((response.optString("delapan")));
                        sembilan_t.setText((response.optString("sembilan")));
                        sepuluh_t.setText((response.optString("sepuluh")));
                        sebelas_t.setText((response.optString("sebelas")));
                        duabelas_t.setText((response.optString("duabelas")));
                        tigabelas_t.setText((response.optString("tigabelas")));
                        empatbelas_t.setText((response.optString("empatbelas")));
                        limabelas_t.setText((response.optString("limabelas")));
                        enambelas_t.setText((response.optString("enambelas")));
                        tujuhbelas_t.setText((response.optString("tujuhbelas")));
                        delapanbelas_t.setText((response.optString("delapanbelas")));
                        sembilanbelas_t.setText((response.optString("sembilanbelas")));
                        duapuluh_t.setText((response.optString("duapuluh")));
                        duasatu_t.setText((response.optString("duasatu")));
                        duadua_t.setText((response.optString("duadua")));

                        duatiga_t.setText((response.optString("duatiga")));
//                        duaempat_t.setText((response.optString("duaempat")));
//                        dualima_t.setText((response.optString("dualima")));
//                        duaenam_t.setText((response.optString("duaenam")));
//                        duatujuh_t.setText((response.optString("duatujuh")));
//                        duadelapan_t.setText((response.optString("duadelapan")));
//                        duasembilan_t.setText((response.optString("duasembilan")));
//                        tigapuluh_t.setText((response.optString("tigapuluh")));

                        tigasatu_t.setText((response.optString("tigasatu")));
                        tigadua_t.setText((response.optString("tigadua")));
                        tigatiga_t.setText((response.optString("tigatiga")));
                        tigaempat_t.setText((response.optString("tigaempat")));
                        tigalima_t.setText((response.optString("tigalima")));
                        tigaenam_t.setText((response.optString("tigaenam")));
                        tigatujuh_t.setText((response.optString("tigatujuh")));
                        tigadelapan_t.setText((response.optString("tigadelapan")));
                        tigasembilan_t.setText((response.optString("tigasembilan")));
                        empatpuluh_t.setText((response.optString("empatpuluh")));

                        empatsatu_t.setText((response.optString("empatsatu")));
                        empatdua_t.setText((response.optString("empatdua")));
                        empattiga_t.setText((response.optString("empattiga")));
                        empatempat_t.setText((response.optString("empatempat")));
                        empatlima_t.setText((response.optString("empatlima")));
                        empatenam_t.setText((response.optString("empatenam")));
                        empattujuh_t.setText((response.optString("empattujuh")));
                        empatdelapan_t.setText((response.optString("empatdelapan")));
                        empatsembilan_t.setText((response.optString("empatsembilan")));
                        limapuluh_t.setText((response.optString("limapuluh")));

                        limasatu_t.setText((response.optString("limasatu")));
                        limadua_t.setText((response.optString("limadua")));
                        limatiga_t.setText((response.optString("limatiga")));
                        limaempat_t.setText((response.optString("limaempat")));
                        limalima_t.setText((response.optString("limalima")));
                        limaenam_t.setText((response.optString("limaenam")));
                        limatujuh_t.setText((response.optString("limatujuh")));
                        limadelapan_t.setText((response.optString("limadelapan")));
                        limasembilan_t.setText((response.optString("limasembilan")));
                        enampuluh_t.setText((response.optString("enampuluh")));

                        enamsatu_t.setText((response.optString("enamsatu")));
                        enamdua_t.setText((response.optString("enamdua")));
                        enamtiga_t.setText((response.optString("enamtiga")));
                        enamempat_t.setText((response.optString("enamempat")));
                        enamlima_t.setText((response.optString("enamlima")));
                        enamenam_t.setText((response.optString("enamenam")));
                        enamtujuh_t.setText((response.optString("enamtujuh")));
                        enamdelapan_t.setText((response.optString("enamdelapan")));
                        enamsembilan_t.setText((response.optString("enamsembilan")));
                        tujuhpuluh_t.setText((response.optString("tujuhpuluh")));

                        tujuhsatu_t.setText((response.optString("tujuhsatu")));
                        tujuhdua_t.setText((response.optString("tujuhdua")));
                        tujuhtiga_t.setText((response.optString("tujuhtiga")));
                        tujuhempat_t.setText((response.optString("tujuhempat")));
                        tujuhlima_t.setText((response.optString("tujuhlima")));
                        tujuhenam_t.setText((response.optString("tujuhenam")));
                        tujuhtujuh_t.setText((response.optString("tujuhtujuh")));





                    }

                    @Override
                    public void onError(ANError error) {

                    }




                });

    }




}