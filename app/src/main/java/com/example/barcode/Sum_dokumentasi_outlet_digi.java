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

public class Sum_dokumentasi_outlet_digi extends AppCompatActivity {
    TextView namaoutlet, id_2;
    ListView listdataoutlet;
    Button caridataoutlet;


    public static String LINK, namaoutletlist, idoutletlist, namasaleslist;
    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sum_dokumentasi_outlet_digi);

        LINK = Config.host + "history.php";
        namaoutletlist = "";
        idoutletlist = "";
        namasaleslist = "";

        namaoutlet = (EditText) findViewById(R.id.namaoutlet);
        id_2 = (TextView) findViewById(R.id.id);
        caridataoutlet = (Button) findViewById(R.id.caridataoutlet);

        listdataoutlet = (ListView) findViewById(R.id.listdataoutlet);

        namaoutlet.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0)
                    list();
            }
        });

        caridataoutlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list();
            }
        });

        list();

    }

    private void list(){

        //swipe_refresh.setRefreshing(true);
        aruskas.clear();
        listdataoutlet.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "dokumentasi_outlet_digi.php" )
                .addBodyParameter("namaoutlet", namaoutlet.getText().toString())
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
                                map.put("id",         responses.optString("id"));
                                map.put("namaoutlet",         responses.optString("namaoutlet"));
                                map.put("kecamatan",       responses.optString("kecamatan"));
                                map.put("tanggal",       responses.optString("tanggal"));



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
                        // handle errors
                    }
                });
    }

    private void Adapter(){

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.list_dokumentasi,
                new String[] {"id","namaoutlet","kecamatan","tanggal"},
                new int[] {R.id.idlist,R.id.namaoutletlist, R.id.kecamatanlist, R.id.tanggallist});

        listdataoutlet.setAdapter(simpleAdapter);

        listdataoutlet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //no    = ((TextView) view.findViewById(R.id.no)).getText().toString();
                idoutletlist    = ((TextView) view.findViewById(R.id.idlist)).getText().toString();




                id_2.setText(idoutletlist);
//                idoutlet.setText(idoutletlist);
//                namasales.setText(namasaleslist);



                String a = id_2.getText().toString();
                Intent i = new Intent(getApplicationContext(), Sum_dokumentasi_outlet2_digi.class);
                i.putExtra("id",""+a+"");
                startActivity(i);



                //tanggal        = ((TextView) view.findViewById(R.id.tanggal)).getText().toString();
                //ListMenu();
            }
        });
        //swipe_refresh.setRefreshing(false);
    }




}