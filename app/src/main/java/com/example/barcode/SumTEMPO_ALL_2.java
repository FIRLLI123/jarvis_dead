package com.example.barcode;

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

import java.text.DecimalFormat;
import java.text.NumberFormat;
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


public class SumTEMPO_ALL_2 extends AppCompatActivity {
    ListView listtest1;
    TextView idoutlet,namaoutlet,qty;

    Button caridataoutlet1;

    EditText namaoutlet1;


    TextView hasilpilihan1,hasilpilihan2;

    TextView tanggaldari,tanggalsampai;

    public static String LINK,idoutletlist, namaoutletlist, qtylist;
    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sum_tempo_all2);

        NumberFormat formatter = new DecimalFormat("#,###,###,###");

        LINK = Config.host + "history.php";
        idoutletlist = "";
        namaoutletlist = "";
        qtylist = "";



        listtest1 = (ListView) findViewById(R.id.listtest);
        idoutlet = (TextView) findViewById(R.id.idoutlet);
        namaoutlet = (TextView) findViewById(R.id.namaoutlet);
        qty = (TextView) findViewById(R.id.qty);
        namaoutlet1 = (EditText) findViewById(R.id.namaoutletcari);

        caridataoutlet1 = (Button) findViewById(R.id.caridataoutlet);
        hasilpilihan1 = (TextView) findViewById(R.id.hasilpilihan1);
        hasilpilihan2 = (TextView) findViewById(R.id.hasilpilihan2);


        tanggaldari = (TextView) findViewById(R.id.tanggaldari);
        tanggalsampai = (TextView) findViewById(R.id.tanggalsampai);



        namaoutlet1.addTextChangedListener(new TextWatcher() {

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


        caridataoutlet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list();
            }
        });

        Intent i = getIntent();
        String kiriman = i.getStringExtra("tanggaldari");
        tanggaldari.setText(kiriman);
        String kiriman2 = i.getStringExtra("tanggalsampai");
        tanggalsampai.setText(kiriman2);

        String kiriman3 = i.getStringExtra("hasilpilihan1");
        hasilpilihan1.setText(kiriman3);
        String kiriman4 = i.getStringExtra("hasilpilihan2");
        hasilpilihan2.setText(kiriman4);


        list();
    }

    private void list(){

        //swipe_refresh.setRefreshing(true);
        aruskas.clear();
        listtest1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "listtempooutlet.php" )
                .addBodyParameter("namaoutlet", namaoutlet1.getText().toString())
                .addBodyParameter("tanggaldari", tanggaldari.getText().toString())
                .addBodyParameter("tanggalsampai", tanggalsampai.getText().toString())
                .addBodyParameter("hasilpilihan1", hasilpilihan1.getText().toString())
                .addBodyParameter("hasilpilihan2", hasilpilihan2.getText().toString())
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
                                map.put("idoutlet",         responses.optString("idoutlet"));
                                map.put("namaoutlet",         responses.optString("namaoutlet"));
                                map.put("qty",       responses.optString("qty"));
//                                map.put("total",       rupiahFormat.format(responses.optDouble("total")));



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

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.listtempooutlet,
                new String[] {"idoutlet","namaoutlet","qty"},
                new int[] {R.id.idoutletlisttempooutlet,R.id.namaoutletlisttempooutlet,R.id.qtylisttempooutlet});

        listtest1.setAdapter(simpleAdapter);

        listtest1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idoutletlist    = ((TextView) view.findViewById(R.id.idoutletlisttempooutlet)).getText().toString();
                namaoutletlist    = ((TextView) view.findViewById(R.id.namaoutletlisttempooutlet)).getText().toString();
                qtylist  = ((TextView) view.findViewById(R.id.qtylisttempooutlet)).getText().toString();

                idoutlet.setText(idoutletlist);
                namaoutlet.setText(namaoutletlist);
                qty.setText(qtylist);

                String a = idoutlet.getText().toString();
                String b = namaoutlet.getText().toString();
                String c = tanggaldari.getText().toString();
                String d = tanggalsampai.getText().toString();
                String e = hasilpilihan1.getText().toString();
                String f = hasilpilihan2.getText().toString();
                Intent i = new Intent(getApplicationContext(), SumTEMPO_ALL_3.class);
                i.putExtra("idoutlet",""+a+"");
                i.putExtra("namaoutlet",""+b+"");
                i.putExtra("tanggaldari",""+c+"");
                i.putExtra("tanggalsampai",""+d+"");
                i.putExtra("hasilpilihan1",""+e+"");
                i.putExtra("hasilpilihan2",""+f+"");
                startActivity(i);


                //tanggal        = ((TextView) view.findViewById(R.id.tanggal)).getText().toString();
                //ListMenu();
            }
        });

        //swipe_refresh.setRefreshing(false);
    }







}