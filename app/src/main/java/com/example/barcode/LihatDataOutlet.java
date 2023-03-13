package com.example.barcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.barcode.helper.Config;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.widget.Spinner;

public class LihatDataOutlet extends AppCompatActivity {
    public Spinner sp;

    TextView idoutlet1;
    TextView namaoutlet1;
    TextView namasales1;
    TextView electronik1;
    TextView targetelectronik1;
    TextView actualelectronik1;
    TextView achelectronik1;
    TextView gapelectronik1;

    TextView hero1;
    TextView targethero1;
    TextView actualhero1;
    TextView achhero1;
    TextView gaphero1;

    TextView stper1;
    TextView targetstper1;
    TextView actualstper1;
    TextView achstper1;
    TextView gapstper1;

    TextView insa1;
    TextView targetinsa1;
    TextView actualinsa1;
    TextView achinsa1;
    TextView gapinsa1;

    TextView soper1;
    TextView targetsoper1;
    TextView actualsoper1;
    TextView achsoper1;
    TextView gapsoper1;

    TextView stvoucher1;
    TextView targetstvoucher1;
    TextView actualstvoucher1;
    TextView achstvoucher1;
    TextView gapstvoucher1;


    TextView invoucher1;
    TextView targetinvoucher1;
    TextView actualinvoucher1;
    TextView achinvoucher1;
    TextView gapinvoucher1;

    TextView sovoucher1;
    TextView targetsovoucher1;
    TextView actualsovoucher1;
    TextView achsovoucher1;
    TextView gapsovoucher1;

    TextView stota1;
    TextView targetstota1;
    TextView actualstota1;
    TextView achstota1;
    TextView gapstota1;

    TextView usim1;
    TextView targetusim1;
    TextView actualusim1;
    TextView achusim1;
    TextView gapusim1;

    TextView comsak1;
    TextView targetcomsak1;
    TextView actualcomsak1;
    TextView achcomsak1;
    TextView gapcomsak1;

    TextView insak1;
    TextView targetinsak1;
    TextView actualinsak1;
    TextView achinsak1;
    TextView gapinsak1;

    TextView renewal1;
    TextView targetrenewal1;
    TextView actualrenewal1;
    TextView achrenewal1;
    TextView gaprenewal1;

    TextView vopackage1;
    TextView targetvopackage1;
    TextView actualvopackage1;
    TextView achvopackage1;
    TextView gapvopackage1;

    TextView digital1;
    TextView targetdigital1;
    TextView actualdigital1;
    TextView achdigital1;
    TextView gapdigital1;

    TextView bulan1;

    TextView tanggalupdate1;

    TextView id1;

    Button btnbulan1;
    Button cekstatus1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lihat_data_outlet);

        sp = (Spinner)findViewById(R.id.spinner);

        btnbulan1 = (Button) findViewById(R.id.btnbulan);
        cekstatus1 = (Button) findViewById(R.id.cekstatus);
        bulan1 = (TextView) findViewById(R.id.bulan);

        idoutlet1 = (TextView) findViewById(R.id.idoutlet);
        namaoutlet1 = (TextView) findViewById(R.id.namaoutlet);
        namasales1 = (TextView) findViewById(R.id.namasales);

        electronik1 = (TextView) findViewById(R.id.electronik);
        targetelectronik1 = (TextView) findViewById(R.id.targetelectronik);
        actualelectronik1 = (TextView) findViewById(R.id.actualelectronik);
        achelectronik1 = (TextView) findViewById(R.id.achelectronik);
        gapelectronik1 = (TextView) findViewById(R.id.gapelectronik);



        hero1 = (TextView) findViewById(R.id.hero);
        targethero1 = (TextView) findViewById(R.id.targethero);
        actualhero1 = (TextView) findViewById(R.id.actualhero);
        achhero1 = (TextView) findViewById(R.id.achhero);
        gaphero1 = (TextView) findViewById(R.id.gaphero);


        stper1 = (TextView) findViewById(R.id.stper);
        targetstper1 = (TextView) findViewById(R.id.targetstper);
        actualstper1 = (TextView) findViewById(R.id.actualstper);
        achstper1 = (TextView) findViewById(R.id.achstper);
        gapstper1 = (TextView) findViewById(R.id.gapstper);

        insa1 = (TextView) findViewById(R.id.insa);
        targetinsa1 = (TextView) findViewById(R.id.targetinsa);
        actualinsa1 = (TextView) findViewById(R.id.actualinsa);
        achinsa1 = (TextView) findViewById(R.id.achinsa);
        gapinsa1 = (TextView) findViewById(R.id.gapinsa);

        soper1 = (TextView) findViewById(R.id.soper);
        targetsoper1 = (TextView) findViewById(R.id.targetsoper);
        actualsoper1 = (TextView) findViewById(R.id.actualsoper);
        achsoper1 = (TextView) findViewById(R.id.achsoper);
        gapsoper1 = (TextView) findViewById(R.id.gapsoper);

        stvoucher1 = (TextView) findViewById(R.id.stvoucher);
        targetstvoucher1 = (TextView) findViewById(R.id.targetstvoucher);
        actualstvoucher1 = (TextView) findViewById(R.id.actualstvoucher);
        achstvoucher1 = (TextView) findViewById(R.id.achstvoucher);
        gapstvoucher1 = (TextView) findViewById(R.id.gapstvoucher);

        invoucher1 = (TextView) findViewById(R.id.invoucher);
        targetinvoucher1 = (TextView) findViewById(R.id.targetinvoucher);
        actualinvoucher1 = (TextView) findViewById(R.id.actualinvoucher);
        achinvoucher1 = (TextView) findViewById(R.id.achinvoucher);
        gapinvoucher1 = (TextView) findViewById(R.id.gapinvoucher);

        sovoucher1 = (TextView) findViewById(R.id.sovoucher);
        targetsovoucher1 = (TextView) findViewById(R.id.targetsovoucher);
        actualsovoucher1 = (TextView) findViewById(R.id.actualsovoucher);
        achsovoucher1 = (TextView) findViewById(R.id.achsovoucher);
        gapsovoucher1 = (TextView) findViewById(R.id.gapsovoucher);


        stota1 = (TextView) findViewById(R.id.stota);
        targetstota1 = (TextView) findViewById(R.id.targetstota);
        actualstota1 = (TextView) findViewById(R.id.actualstota);
        achstota1 = (TextView) findViewById(R.id.achstota);
        gapstota1 = (TextView) findViewById(R.id.gapstota);

        usim1 = (TextView) findViewById(R.id.usim);
        targetusim1 = (TextView) findViewById(R.id.targetusim);
        actualusim1 = (TextView) findViewById(R.id.actualusim);
        achusim1 = (TextView) findViewById(R.id.achusim);
        gapusim1 = (TextView) findViewById(R.id.gapusim);

        comsak1 = (TextView) findViewById(R.id.comsak);
        targetcomsak1 = (TextView) findViewById(R.id.targetcomsak);
        actualcomsak1 = (TextView) findViewById(R.id.actualcomsak);
        achcomsak1 = (TextView) findViewById(R.id.achcomsak);
        gapcomsak1 = (TextView) findViewById(R.id.gapcomsak);

        insak1 = (TextView) findViewById(R.id.insak);
        targetinsak1 = (TextView) findViewById(R.id.targetinsak);
        actualinsak1 = (TextView) findViewById(R.id.actualinsak);
        achinsak1 = (TextView) findViewById(R.id.achinsak);
        gapinsak1 = (TextView) findViewById(R.id.gapinsak);

        renewal1 = (TextView) findViewById(R.id.renewal);
        targetrenewal1 = (TextView) findViewById(R.id.targetrenewal);
        actualrenewal1 = (TextView) findViewById(R.id.actualrenewal);
        achrenewal1 = (TextView) findViewById(R.id.achrenewal);
        gaprenewal1 = (TextView) findViewById(R.id.gaprenewal);

        vopackage1 = (TextView) findViewById(R.id.vopackage);
        targetvopackage1 = (TextView) findViewById(R.id.targetvopackage);
        actualvopackage1 = (TextView) findViewById(R.id.actualvopackage);
        achvopackage1 = (TextView) findViewById(R.id.achvopackage);
        gapvopackage1 = (TextView) findViewById(R.id.gapvopackage);

        digital1 = (TextView) findViewById(R.id.digital);
        targetdigital1 = (TextView) findViewById(R.id.targetdigital);
        actualdigital1 = (TextView) findViewById(R.id.actualdigital);
        achdigital1 = (TextView) findViewById(R.id.achdigital);
        gapdigital1 = (TextView) findViewById(R.id.gapdigital);


        tanggalupdate1 = (TextView) findViewById(R.id.tanggalupdate);
        id1 = (TextView) findViewById(R.id.id);
/*
        Intent idoutlet = getIntent();
        String kiriman = idoutlet.getStringExtra("idoutlet");
        idoutlet1.setText(kiriman);
**/
        //KasAdapter2();

        //warna();


        List<String> item = new ArrayList<>();
        //item.add("PILIH BULAN");
        item.add("Januari");
        item.add("Februari");
        item.add("Maret");
        item.add("April");
        item.add("Mei");
        item.add("Juni");
        item.add("Juli");
        item.add("Agustus");
        item.add("Oktober");
        item.add("November");
        item.add("Desember");

        //Untuk membuat adapter list kota
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(LihatDataOutlet.this,android.R.layout.simple_spinner_dropdown_item, item);

        //Untuk menentukan model adapter nya
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);

        btnbulan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    electronik();
                    heropackage();
                    stperdana();
                    insa();
                    soperdana();
                    stvoucher();
                    invoucher();
                    sovoucher();
                    comsak();
                    insak();
                    renewal();
                    digital();
                    vopackage();
                    stota();
                    usim();


                    warnaitem1();
                    warnaitem2();
                warnaitem3();
                warnaitem4();
                warnaitem5();
                warnaitem6();
                warnaitem7();
                warnaitem8();
                warnaitem9();
                warnaitem10();
                warnaitem11();
                warnaitem12();
                warnaitem13();
                warnaitem14();
                warnaitem15();


      }
   });

        cekstatus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gapelectronik1.getText().equals("null")){
                    Toast.makeText(getApplicationContext(), "Data tidak ada", Toast.LENGTH_LONG).show();
                }else {
                    //warna();
                }
            }
        });

        //KasAdapter2();

        Intent i = getIntent();
        String kiriman = i.getStringExtra("idoutlet");
        idoutlet1.setText(kiriman);
        String kiriman2 = i.getStringExtra("namaoutlet");
        namaoutlet1.setText(kiriman2);
        String kiriman3 = i.getStringExtra("namasales");
        namasales1.setText(kiriman3);

        tanggalupdate();

    }
    public void cekstatus(){
        if(gapelectronik1.getText().equals("null")){
            Toast.makeText(getApplicationContext(), "Data tidak ada", Toast.LENGTH_LONG).show();
        }else {
            //warna();
        }


    }

    private void tanggalupdate() {

        AndroidNetworking.post(Config.host + "tanggalupdate.php")
                .addBodyParameter("id", id1.getText().toString())
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
                        tanggalupdate1.setText((response.optString("tanggal")));


                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }



    private void electronik() {

        AndroidNetworking.post(Config.host + "electronik.php")
                .addBodyParameter("idoutlet", idoutlet1.getText().toString())
                .addBodyParameter("bulan", sp.getSelectedItem().toString())
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
                            electronik1.setText((response.optString("parameter")));
                            targetelectronik1.setText((response.optString("target")));
                            actualelectronik1.setText((response.optString("actual")));
                            achelectronik1.setText((response.optString("ach")));
                            gapelectronik1.setText((response.optString("gap")));
                            //bulan1.setText((response.optString("bulan")));

                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }

    private void heropackage() {

        AndroidNetworking.post(Config.host + "hero.php")
                .addBodyParameter("idoutlet", idoutlet1.getText().toString())
                .addBodyParameter("bulan", sp.getSelectedItem().toString())
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
                        hero1.setText((response.optString("parameter")));
                        targethero1.setText((response.optString("target")));
                        actualhero1.setText((response.optString("actual")));
                        achhero1.setText((response.optString("ach")));
                        gaphero1.setText((response.optString("gap")));
                        //bulan1.setText((response.optString("bulan")));

                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }

    private void stperdana() {

        AndroidNetworking.post(Config.host + "stperdana.php")
                .addBodyParameter("idoutlet", idoutlet1.getText().toString())
                .addBodyParameter("bulan", sp.getSelectedItem().toString())
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
                        stper1.setText((response.optString("parameter")));
                        targetstper1.setText((response.optString("target")));
                        actualstper1.setText((response.optString("actual")));
                        achstper1.setText((response.optString("ach")));
                        gapstper1.setText((response.optString("gap")));
                        //bulan1.setText((response.optString("bulan")));

                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }

    private void insa() {

        AndroidNetworking.post(Config.host + "insa.php")
                .addBodyParameter("idoutlet", idoutlet1.getText().toString())
                .addBodyParameter("bulan", sp.getSelectedItem().toString())
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
                        insa1.setText((response.optString("parameter")));
                        targetinsa1.setText((response.optString("target")));
                        actualinsa1.setText((response.optString("actual")));
                        achinsa1.setText((response.optString("ach")));
                        gapinsa1.setText((response.optString("gap")));
                        //bulan1.setText((response.optString("bulan")));

                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }

    private void soperdana() {

        AndroidNetworking.post(Config.host + "soperdana.php")
                .addBodyParameter("idoutlet", idoutlet1.getText().toString())
                .addBodyParameter("bulan", sp.getSelectedItem().toString())
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
                        soper1.setText((response.optString("parameter")));
                        targetsoper1.setText((response.optString("target")));
                        actualsoper1.setText((response.optString("actual")));
                        achsoper1.setText((response.optString("ach")));
                        gapsoper1.setText((response.optString("gap")));
                        //bulan1.setText((response.optString("bulan")));

                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }

    private void stvoucher() {

        AndroidNetworking.post(Config.host + "stvoucher.php")
                .addBodyParameter("idoutlet", idoutlet1.getText().toString())
                .addBodyParameter("bulan", sp.getSelectedItem().toString())
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
                        stvoucher1.setText((response.optString("parameter")));
                        targetstvoucher1.setText((response.optString("target")));
                        actualstvoucher1.setText((response.optString("actual")));
                        achstvoucher1.setText((response.optString("ach")));
                        gapstvoucher1.setText((response.optString("gap")));
                        //bulan1.setText((response.optString("bulan")));

                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }

    private void invoucher() {

        AndroidNetworking.post(Config.host + "invo.php")
                .addBodyParameter("idoutlet", idoutlet1.getText().toString())
                .addBodyParameter("bulan", sp.getSelectedItem().toString())
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
                        invoucher1.setText((response.optString("parameter")));
                        targetinvoucher1.setText((response.optString("target")));
                        actualinvoucher1.setText((response.optString("actual")));
                        achinvoucher1.setText((response.optString("ach")));
                        gapinvoucher1.setText((response.optString("gap")));
                        //bulan1.setText((response.optString("bulan")));

                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }

    private void sovoucher() {

        AndroidNetworking.post(Config.host + "sovoucher.php")
                .addBodyParameter("idoutlet", idoutlet1.getText().toString())
                .addBodyParameter("bulan", sp.getSelectedItem().toString())
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
                        sovoucher1.setText((response.optString("parameter")));
                        targetsovoucher1.setText((response.optString("target")));
                        actualsovoucher1.setText((response.optString("actual")));
                        achsovoucher1.setText((response.optString("ach")));
                        gapsovoucher1.setText((response.optString("gap")));
                        //bulan1.setText((response.optString("bulan")));

                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }

    private void comsak() {

        AndroidNetworking.post(Config.host + "comsak.php")
                .addBodyParameter("idoutlet", idoutlet1.getText().toString())
                .addBodyParameter("bulan", sp.getSelectedItem().toString())
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
                        comsak1.setText((response.optString("parameter")));
                        targetcomsak1.setText((response.optString("target")));
                        actualcomsak1.setText((response.optString("actual")));
                        achcomsak1.setText((response.optString("ach")));
                        gapcomsak1.setText((response.optString("gap")));
                        //bulan1.setText((response.optString("bulan")));

                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }

    private void insak() {

        AndroidNetworking.post(Config.host + "insak.php")
                .addBodyParameter("idoutlet", idoutlet1.getText().toString())
                .addBodyParameter("bulan", sp.getSelectedItem().toString())
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
                        insak1.setText((response.optString("parameter")));
                        targetinsak1.setText((response.optString("target")));
                        actualinsak1.setText((response.optString("actual")));
                        achinsak1.setText((response.optString("ach")));
                        gapinsak1.setText((response.optString("gap")));
                        //bulan1.setText((response.optString("bulan")));

                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }

    private void renewal() {

        AndroidNetworking.post(Config.host + "renewal.php")
                .addBodyParameter("idoutlet", idoutlet1.getText().toString())
                .addBodyParameter("bulan", sp.getSelectedItem().toString())
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
                        renewal1.setText((response.optString("parameter")));
                        targetrenewal1.setText((response.optString("target")));
                        actualrenewal1.setText((response.optString("actual")));
                        achrenewal1.setText((response.optString("ach")));
                        gaprenewal1.setText((response.optString("gap")));
                        //bulan1.setText((response.optString("bulan")));

                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }

    private void digital() {

        AndroidNetworking.post(Config.host + "digital.php")
                .addBodyParameter("idoutlet", idoutlet1.getText().toString())
                .addBodyParameter("bulan", sp.getSelectedItem().toString())
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
                        digital1.setText((response.optString("parameter")));
                        targetdigital1.setText((response.optString("target")));
                        actualdigital1.setText((response.optString("actual")));
                        achdigital1.setText((response.optString("ach")));
                        gapdigital1.setText((response.optString("gap")));
                        //bulan1.setText((response.optString("bulan")));

                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }

    private void vopackage() {

        AndroidNetworking.post(Config.host + "vopackage.php")
                .addBodyParameter("idoutlet", idoutlet1.getText().toString())
                .addBodyParameter("bulan", sp.getSelectedItem().toString())
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
                        vopackage1.setText((response.optString("parameter")));
                        targetvopackage1.setText((response.optString("target")));
                        actualvopackage1.setText((response.optString("actual")));
                        achvopackage1.setText((response.optString("ach")));
                        gapvopackage1.setText((response.optString("gap")));
                        //bulan1.setText((response.optString("bulan")));

                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }

    private void stota() {

        AndroidNetworking.post(Config.host + "stota.php")
                .addBodyParameter("idoutlet", idoutlet1.getText().toString())
                .addBodyParameter("bulan", sp.getSelectedItem().toString())
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
                        stota1.setText((response.optString("parameter")));
                        targetstota1.setText((response.optString("target")));
                        actualstota1.setText((response.optString("actual")));
                        achstota1.setText((response.optString("ach")));
                        gapstota1.setText((response.optString("gap")));
                        //bulan1.setText((response.optString("bulan")));

                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }

    private void usim() {

        AndroidNetworking.post(Config.host + "usim.php")
                .addBodyParameter("idoutlet", idoutlet1.getText().toString())
                .addBodyParameter("bulan", sp.getSelectedItem().toString())
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
                        usim1.setText((response.optString("parameter")));
                        targetusim1.setText((response.optString("target")));
                        actualusim1.setText((response.optString("actual")));
                        achusim1.setText((response.optString("ach")));
                        gapusim1.setText((response.optString("gap")));
                        //bulan1.setText((response.optString("bulan")));

                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }



    public void warnaitem1(){
        int item1 = Integer.parseInt(gapelectronik1.getText().toString());
        if(item1<0) {                    //1
            //jika form Email belum di isi / masih kosong
            gapelectronik1.setTextColor(getResources().getColor(R.color.merah));
        }else{
            gapelectronik1.setTextColor(getResources().getColor(R.color.black));

        }
    }


    public void warnaitem2(){
        int item2 = Integer.parseInt(gaphero1.getText().toString());
        if(item2<0) {                    //1
            //jika form Email belum di isi / masih kosong
            gaphero1.setTextColor(getResources().getColor(R.color.merah));
        }else{
            gaphero1.setTextColor(getResources().getColor(R.color.black));

        }
    }

    public void warnaitem3(){
        int item3 = Integer.parseInt(gapstper1.getText().toString());
        if(item3<0) {                    //1
            //jika form Email belum di isi / masih kosong
            gapstper1.setTextColor(getResources().getColor(R.color.merah));
        }else{
            gapstper1.setTextColor(getResources().getColor(R.color.black));

        }
    }


    public void warnaitem4(){
        int item4 = Integer.parseInt(gapinsa1.getText().toString());
        if(item4<0) {                    //1
            //jika form Email belum di isi / masih kosong
            gapinsa1.setTextColor(getResources().getColor(R.color.merah));
        }else{
            gapinsa1.setTextColor(getResources().getColor(R.color.black));

        }
    }

    public void warnaitem5(){
        int item5 = Integer.parseInt(gapsoper1.getText().toString());
        if(item5<0) {                    //1
            //jika form Email belum di isi / masih kosong
            gapsoper1.setTextColor(getResources().getColor(R.color.merah));
        }else{
            gapsoper1.setTextColor(getResources().getColor(R.color.black));

        }
    }


    public void warnaitem6(){
        int item6 = Integer.parseInt(gapstvoucher1.getText().toString());
        if(item6<0) {                    //1
            //jika form Email belum di isi / masih kosong
            gapstvoucher1.setTextColor(getResources().getColor(R.color.merah));
        }else{
            gapstvoucher1.setTextColor(getResources().getColor(R.color.black));

        }
    }

    public void warnaitem7(){
        int item7 = Integer.parseInt(gapinvoucher1.getText().toString());
        if(item7<0) {                    //1
            //jika form Email belum di isi / masih kosong
            gapinvoucher1.setTextColor(getResources().getColor(R.color.merah));
        }else{
            gapinvoucher1.setTextColor(getResources().getColor(R.color.black));

        }
    }


    public void warnaitem8(){
        int item8 = Integer.parseInt(gapsovoucher1.getText().toString());
        if(item8<0) {                    //1
            //jika form Email belum di isi / masih kosong
            gapsovoucher1.setTextColor(getResources().getColor(R.color.merah));
        }else{
            gapsovoucher1.setTextColor(getResources().getColor(R.color.black));

        }
    }

    public void warnaitem9(){
        int item9 = Integer.parseInt(gapcomsak1.getText().toString());
        if(item9<0) {                    //1
            //jika form Email belum di isi / masih kosong
            gapcomsak1.setTextColor(getResources().getColor(R.color.merah));
        }else{
            gapcomsak1.setTextColor(getResources().getColor(R.color.black));

        }
    }


    public void warnaitem10(){
        int item10 = Integer.parseInt(gapinsak1.getText().toString());
        if(item10<0) {                    //1
            //jika form Email belum di isi / masih kosong
            gapinsak1.setTextColor(getResources().getColor(R.color.merah));
        }else{
            gapinsak1.setTextColor(getResources().getColor(R.color.black));

        }
    }

    public void warnaitem11(){
        int item11 = Integer.parseInt(gaprenewal1.getText().toString());
        if(item11<0) {                    //1
            //jika form Email belum di isi / masih kosong
            gaprenewal1.setTextColor(getResources().getColor(R.color.merah));
        }else{
            gaprenewal1.setTextColor(getResources().getColor(R.color.black));

        }
    }


    public void warnaitem12(){
        int item12 = Integer.parseInt(gapdigital1.getText().toString());
        if(item12<0) {                    //1
            //jika form Email belum di isi / masih kosong
            gapdigital1.setTextColor(getResources().getColor(R.color.merah));
        }else{
            gapdigital1.setTextColor(getResources().getColor(R.color.black));

        }
    }

    public void warnaitem13(){
        int item13 = Integer.parseInt(gapvopackage1.getText().toString());
        if(item13<0) {                    //1
            //jika form Email belum di isi / masih kosong
            gapvopackage1.setTextColor(getResources().getColor(R.color.merah));
        }else{
            gapvopackage1.setTextColor(getResources().getColor(R.color.black));

        }
    }


    public void warnaitem14(){
        int item14 = Integer.parseInt(gapstota1.getText().toString());
        if(item14<0) {                    //1
            //jika form Email belum di isi / masih kosong
            gapstota1.setTextColor(getResources().getColor(R.color.merah));
        }else{
            gapstota1.setTextColor(getResources().getColor(R.color.black));

        }
    }

    public void warnaitem15(){
        int item15 = Integer.parseInt(gapusim1.getText().toString());
        if(item15<0) {                    //1
            //jika form Email belum di isi / masih kosong
            gapusim1.setTextColor(getResources().getColor(R.color.merah));
        }else{
            gapusim1.setTextColor(getResources().getColor(R.color.black));

        }
    }


}