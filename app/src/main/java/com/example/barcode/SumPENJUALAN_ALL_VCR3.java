package com.example.barcode;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
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

public class SumPENJUALAN_ALL_VCR3 extends AppCompatActivity {

    TextView idoutlet,namasales;
    TextView id1,namaoutlet,namaitem,qty,tanggal,pembayaran;

    TextView tanggaldari,tanggalsampai;

    TextView tempo,sudahterbayar;

    TextView jumlahtempo,jumlahdibayarkan;

    ListView listtempooutlet;

    public static String idlist, idoutletlist, namaoutletlist, namasaleslist, namaitemtlist, qtylist, tanggallist, keteranganlist;
    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sum_penjualan_all_vcr3);




        idlist = "";
        idoutletlist = "";
        namaoutletlist = "";
        namasaleslist = "";
        namaitemtlist = "";
        qtylist = "";
        tanggallist = "";
        keteranganlist = "";


        idoutlet = (TextView) findViewById(R.id.idoutlet);
        namaoutlet = (TextView) findViewById(R.id.namaoutlet);


        tanggaldari = (TextView) findViewById(R.id.tanggaldari);
        tanggalsampai = (TextView) findViewById(R.id.tanggalsampai);

        jumlahtempo = (TextView) findViewById(R.id.jumlahtempo);
        jumlahdibayarkan = (TextView) findViewById(R.id.jumlahdibayarkan);


        tempo = (TextView) findViewById(R.id.tempo);
        sudahterbayar = (TextView) findViewById(R.id.sudahterbayar);

        listtempooutlet = (ListView) findViewById(R.id.listtempooutlet);


        Intent dasboard = getIntent();
        String kiriman1 = dasboard.getStringExtra("idoutlet");
        idoutlet.setText(kiriman1);

        String kiriman2 = dasboard.getStringExtra("namaoutlet");
        namaoutlet.setText(kiriman2);

        String kiriman3 = dasboard.getStringExtra("tanggaldari");
        tanggaldari.setText(kiriman3);

        String kiriman4 = dasboard.getStringExtra("tanggalsampai");
        tanggalsampai.setText(kiriman4);

        list();

    }

    private void list(){

        //swipe_refresh.setRefreshing(true);
        aruskas.clear();
        listtempooutlet.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "listpenjualanvoucheroutlet2.php" )
                .addBodyParameter("idoutlet", idoutlet.getText().toString())
                .addBodyParameter("tanggaldari", tanggaldari.getText().toString())
                .addBodyParameter("tanggalsampai", tanggalsampai.getText().toString())
                //.addBodyParameter("namasales", namasales.getText().toString())
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

                                map.put("item",       responses.optString("item"));
                                map.put("qty",       responses.optString("qty"));
                                map.put("namasales",       responses.optString("namasales"));
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

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.listtempooutlet2,
                new String[] {"item","qty","namasales"},
                new int[] {R.id.namaitemlisttempooutlet2, R.id.qtylisttempooutlet2, R.id.namasaleslisttempooutlet2});

        listtempooutlet.setAdapter(simpleAdapter);
    }





}