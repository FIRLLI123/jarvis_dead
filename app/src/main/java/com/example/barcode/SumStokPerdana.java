package com.example.barcode;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

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

public class SumStokPerdana extends AppCompatActivity {
    TextView tes1;
    ListView listtest1;
    Button carisum1, cekdetail1;
    EditText namasaleslist1;
    LinearLayout sf,ds,lainnya;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    public static String LINK, namasaleslist, tanggallist, totallist, hargalistperdana, totallistperdana;
    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sum_stok_perdana);

        NumberFormat formatter = new DecimalFormat("#,###,###,###");

        LINK = Config.host + "history.php";
        namasaleslist = "";
        tanggallist = "";
        totallist = "";
        hargalistperdana = "";
        totallistperdana = "";
        //totallist = "";
        //query_kas = ""; query_total = "";
        //filter = false;
        dateFormatter = new SimpleDateFormat("yyyy/MM/dd", Locale.US);

        tes1 = (TextView) findViewById(R.id.tanggal);
        listtest1 = (ListView) findViewById(R.id.listtest);

        namasaleslist1 = (EditText) findViewById(R.id.namasaleslist);

        carisum1 = (Button) findViewById(R.id.carisum);
        cekdetail1 = (Button) findViewById(R.id.cekdetail);

        sf = (LinearLayout) findViewById(R.id.sf);
        ds = (LinearLayout) findViewById(R.id.ds);
        lainnya = (LinearLayout) findViewById(R.id.lainnya);



        ds.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {




                Intent i = new Intent(getApplicationContext(), SumStokPerdanaDS.class);

                startActivity(i);
                overridePendingTransition(R.anim.shrink_in, R.anim.shrink_out);

            }



        });


        lainnya.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(getApplicationContext(), SumStokPerdanaLainnya.class);
                startActivity(i);
                overridePendingTransition(R.anim.shrink_in, R.anim.shrink_out);
            }
        });





//        tes1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showDateDialog();
//            }
//        });

//        carisum1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                list();
//            }
//        });


//        cekdetail1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////                Intent i = new Intent(getApplicationContext(), SumPerdana2.class);
////                    startActivity(i);
//
//                if (namasaleslist1.getText().toString().length() == 0) {                    //1
//                    //jika form Email belum di isi / masih kosong
//                    //iddelete1.setError("");
//                    Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
//                }  else {
//                    String a = tes1.getText().toString();
//                    String b = namasaleslist1.getText().toString();
//                    //String c = namasalesinputperdana1.getText().toString();
//                    Intent i = new Intent(getApplicationContext(), SumPerdana2.class);
//                    i.putExtra("tanggal",""+a+"");
//                    i.putExtra("namasales",""+b+"");
//                    startActivity(i);
//                }
//            }
//        });
        //tes1.setText(getCurrentDate());
        list();


    }


    private void list(){

        //swipe_refresh.setRefreshing(true);
        aruskas.clear();
        listtest1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "sumstok.php" )
                //.addBodyParameter("tanggal", tes1.getText().toString())
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
                                map.put("namasales",         responses.optString("namasales"));
                                //map.put("namaitem",       responses.optString("tanggal"));
                                map.put("total",       rupiahFormat.format(responses.optDouble("total")));



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

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.liststok,
                new String[] {"namasales","total"},
                new int[] {R.id.namasalesliststok, R.id.totalliststok});

        listtest1.setAdapter(simpleAdapter);

        listtest1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //no    = ((TextView) view.findViewById(R.id.no)).getText().toString();
                namasaleslist    = ((TextView) view.findViewById(R.id.namasalesliststok)).getText().toString();
                //tanggallist  = ((TextView) view.findViewById(R.id.tanggallisttest)).getText().toString();



                namasaleslist1.setText(namasaleslist);
                //tes1.setText(tanggallist);


                //String a = tes1.getText().toString();
                String b = namasaleslist1.getText().toString();
                //String c = namasalesinputperdana1.getText().toString();
                Intent i = new Intent(getApplicationContext(), SumStokPerdana2_V2.class);
                //i.putExtra("tanggal",""+a+"");
                i.putExtra("namasales",""+b+"");
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

                //tanggal        = ((TextView) view.findViewById(R.id.tanggal)).getText().toString();
                //ListMenu();
            }
        });




       //swipe_refresh.setRefreshing(false);
    }
}