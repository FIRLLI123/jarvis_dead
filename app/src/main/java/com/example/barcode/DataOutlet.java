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

public class DataOutlet extends AppCompatActivity {
    TextView idoutlet1,namasales1, namasalesforward;
    ListView listdataoutlet1;
    Button caridataoutlet1;
    EditText namaoutlet1;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    public static String LINK, namaoutletlist, idoutletlist, namasaleslist;
    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_outlet);

        LINK = Config.host + "history.php";
        namaoutletlist = "";
        idoutletlist = "";
        namasaleslist = "";


        dateFormatter = new SimpleDateFormat("yyyy/M/d", Locale.US);

        idoutlet1 = (TextView) findViewById(R.id.idoutlet);
        namasales1 = (TextView) findViewById(R.id.namasales);

        namasalesforward = (TextView) findViewById(R.id.namasalesforward);


        listdataoutlet1 = (ListView) findViewById(R.id.listdataoutlet);

        namaoutlet1 = (EditText) findViewById(R.id.namaoutlet);

        caridataoutlet1 = (Button) findViewById(R.id.caridataoutlet);
        //cekdetail1 = (Button) findViewById(R.id.cekdetail)


        caridataoutlet1.setOnClickListener(new View.OnClickListener() {
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
        list();

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

        Intent i = getIntent();
        String kiriman6 = i.getStringExtra("nama");
        namasalesforward.setText(kiriman6);


    }

    private void list(){

        //swipe_refresh.setRefreshing(true);
        aruskas.clear();
        listdataoutlet1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "datanewoutlet.php" )
                .addBodyParameter("namaoutlet", namaoutlet1.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response

                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);

                        try {
                            JSONArray jsonArray = response.optJSONArray("result");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Data_BayarEX item = new Data_BayarEX();
                                JSONObject responses    = jsonArray.getJSONObject(i);
                                HashMap<String, String> map = new HashMap<String, String>();
                                //map.put("no",         responses.optString("no"));
                                map.put("namaoutlet",         responses.optString("namaoutlet"));
                                map.put("idoutlet",       responses.optString("idoutlet"));
                                map.put("namasales",       responses.optString("namasales"));



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

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.listdataoutlet,
                new String[] {"namaoutlet","idoutlet","namasales"},
                new int[] {R.id.namaoutletlistdataoutlet, R.id.idoutletlistdataoutlet, R.id.namasaleslistdataoutlet});

        listdataoutlet1.setAdapter(simpleAdapter);

        listdataoutlet1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //no    = ((TextView) view.findViewById(R.id.no)).getText().toString();
                namaoutletlist    = ((TextView) view.findViewById(R.id.namaoutletlistdataoutlet)).getText().toString();
                idoutletlist  = ((TextView) view.findViewById(R.id.idoutletlistdataoutlet)).getText().toString();
                namasaleslist  = ((TextView) view.findViewById(R.id.namasaleslistdataoutlet)).getText().toString();



                namaoutlet1.setText(namaoutletlist);
                idoutlet1.setText(idoutletlist);
                namasales1.setText(namasaleslist);



                String a = namaoutlet1.getText().toString();
                String b = idoutlet1.getText().toString();
                String c = namasales1.getText().toString();
                String d = namasalesforward.getText().toString();
                Intent i = new Intent(getApplicationContext(), MainActivity2.class);
                i.putExtra("namaoutlet",""+a+"");
                i.putExtra("idoutlet",""+b+"");
                i.putExtra("namasales",""+c+"");
                i.putExtra("nama",""+d+"");
                startActivity(i);



                //tanggal        = ((TextView) view.findViewById(R.id.tanggal)).getText().toString();
                //ListMenu();
            }
        });
        //swipe_refresh.setRefreshing(false);
    }





}