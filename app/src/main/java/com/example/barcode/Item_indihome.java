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
import android.widget.LinearLayout;
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

public class Item_indihome extends AppCompatActivity {
    TextView namapaket_teks,harga1_teks, harga2_teks, kategori;
    ListView listdataoutlet1;
    Button caridataoutlet1;
    EditText namapaket;
    LinearLayout caridatapaket;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    public static String LINK, namaitemlist, harga1list, harga2list, kategorilist;
    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_indihome);

        LINK = Config.host + "history.php";
        namaitemlist = "";
        harga1list = "";
        harga2list = "";
        kategorilist = "";


        dateFormatter = new SimpleDateFormat("yyyy/M/d", Locale.US);

        namapaket_teks = (TextView) findViewById(R.id.namapaket_teks);
        harga1_teks = (TextView) findViewById(R.id.harga1_teks);
        harga2_teks = (TextView) findViewById(R.id.harga2_teks);


        kategori = (TextView) findViewById(R.id.kategori);

        //namasalesforward = (TextView) findViewById(R.id.namasalesforward);



        listdataoutlet1 = (ListView) findViewById(R.id.listdataoutlet);

        namapaket = (EditText) findViewById(R.id.namapaket);

        caridatapaket = (LinearLayout) findViewById(R.id.caridatapaket);
        //cekdetail1 = (Button) findViewById(R.id.cekdetail)


        caridatapaket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list();
            }
        });

//        namaoutlet1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                list();
//            }
//        });


        namapaket.addTextChangedListener(new TextWatcher() {

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

        Intent i = getIntent();
        String kiriman6 = i.getStringExtra("kategori");
        kategori.setText(kiriman6);


        list();

    }

    private void list(){

        //swipe_refresh.setRefreshing(true);
        aruskas.clear();
        listdataoutlet1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "item_indihome.php" )
                .addBodyParameter("namaitem", namapaket.getText().toString())
                .addBodyParameter("kategori", kategori.getText().toString())
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
                                map.put("namaitem",         responses.optString("namaitem"));
                                map.put("harga_1",       responses.optString("harga_1"));
                                map.put("harga_2",       responses.optString("harga_2"));



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

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.listdatapaket_indihome,
                new String[] {"namaitem","harga_1","harga_2"},
                new int[] {R.id.namapaketlist, R.id.hargapaketlist, R.id.hargapaketlist2});

        listdataoutlet1.setAdapter(simpleAdapter);

        listdataoutlet1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //no    = ((TextView) view.findViewById(R.id.no)).getText().toString();
                namaitemlist    = ((TextView) view.findViewById(R.id.namapaketlist)).getText().toString();
                harga1list  = ((TextView) view.findViewById(R.id.hargapaketlist)).getText().toString();
                harga2list  = ((TextView) view.findViewById(R.id.hargapaketlist2)).getText().toString();


                harga1list = harga1list.replace(",", "");
                harga2list = harga2list.replace(",", "");

                namapaket_teks.setText(namaitemlist);
                harga1_teks.setText(String.valueOf(harga1list)); // Set as plain numeric value
                harga2_teks.setText(String.valueOf(harga2list)); // Set as plain numeric value


                    String a = namapaket_teks.getText().toString();
                    String b = harga1_teks.getText().toString();
                    String c = harga2_teks.getText().toString();


                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("namaitem",""+a+"");
                    resultIntent.putExtra("harga_1",""+b+"");
                    resultIntent.putExtra("harga_2",""+c+"");
                    //resultIntent.putExtra("selectedBarang", selectedBarang);
                    setResult(RESULT_OK, resultIntent);
                    finish();



            }
        });
        //swipe_refresh.setRefreshing(false);
    }





}