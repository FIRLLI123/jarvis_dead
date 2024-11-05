package com.example.barcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Performance_P_Transaksi extends AppCompatActivity {
    public Spinner bulanspinner;

    ListView listtest1;
    TextView namasales, namabulan, bulan;

    Button carisum;

    private SimpleDateFormat dateFormatter;

    public static String LINK, namasaleslist, bulanlist;
    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.performance_p_transaksi);



        dateFormatter = new SimpleDateFormat("MMMM", Locale.US);

        bulan = (TextView) findViewById(R.id.bulan);

        bulanspinner = (Spinner)findViewById(R.id.bulanspinner);
        namasaleslist = "";
        bulanlist = "";



        listtest1 = (ListView) findViewById(R.id.listtest);
        namasales = (TextView) findViewById(R.id.namasales);
        namabulan = (TextView) findViewById(R.id.namabulan);

        carisum = (Button) findViewById(R.id.carisum);


        namabulan.setText(getCurrentDate());
        bulan.setText(getCurrentMonth());





        List<String> item = new ArrayList<>();

        //item.add("--SELECT--");
        item.add("JANUARI");
        item.add("FEBRUARI");
        item.add("MARET");
        item.add("APRIL");
        item.add("MEI");
        item.add("JUNI");
        item.add("JULI");
        item.add("AGUSTUS");
        item.add("SEPTEMBER");
        item.add("OKTOBER");
        item.add("NOVEMBER");
        item.add("DESEMBER");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Performance_P_Transaksi.this,android.R.layout.simple_spinner_dropdown_item, item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        bulanspinner.setAdapter(adapter);

        bulanspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //alasan1.setText(sp.getSelectedItem().toString());

                namabulan.setText(bulanspinner.getSelectedItem().toString());




            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        carisum.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {


                list();

            }

        });

        list();


    }


    public String getCurrentMonth() {
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", new Locale("id", "ID")); // MMMM untuk nama bulan lengkap
        return monthFormat.format(c.getTime());
    }

    public String getCurrentDate() {
        //SimpleDateFormat contoh1 = new SimpleDateFormat("yyyy/MM/dd");
        //String hariotomatis = contoh1.format(c.getTime());
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat contoh1 = new SimpleDateFormat("MMMM");
//        int year, month, day;
//        year = c.get(Calendar.YEAR);
//        month = c.get(Calendar.MONTH);
//        day = c.get(Calendar.DATE);
        //SimpleDateFormat contoh1 = new SimpleDateFormat("EEEE, yyyy/MM/dd");

        String hariotomatis = contoh1.format(c.getTime());
        //tanggalinputperdana1.setText(hariotomatis);
        return hariotomatis;
        //return day +"/" + (month+1) + "/" + year;
        //return (month+1) +"/" + day + "/" + year;
        //return year + "/" + (month + 1) + "/" + day;



    }



    private void list(){

        //swipe_refresh.setRefreshing(true);
        aruskas.clear();
        listtest1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "list_detail_outlet.php" )
                //.addBodyParameter("satu", namasales.getText().toString())
                .addBodyParameter("tiga", bulan.getText().toString())

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
                                map.put("empat",         responses.optString("empat"));
                                map.put("sembilan",       responses.optString("sembilan"));
                                map.put("sepuluh",       responses.optString("sepuluh"));
                                map.put("sebelas",       responses.optString("sebelas"));
                                map.put("tiga",       responses.optString("tiga"));


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

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.list_detail_outlet,
                new String[] {"empat","sembilan","sepuluh","sebelas","tiga"},
                new int[] {R.id.namasaleslisttest,R.id.sembilanlist,R.id.sepuluhlist,R.id.sebelaslist,R.id.tigalist});

        listtest1.setAdapter(simpleAdapter);

        listtest1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //no    = ((TextView) view.findViewById(R.id.no)).getText().toString();
                namasaleslist    = ((TextView) view.findViewById(R.id.namasaleslisttest)).getText().toString();
                bulanlist  = ((TextView) view.findViewById(R.id.tigalist)).getText().toString();



                namasales.setText(namasaleslist);
                namabulan.setText(bulanlist);


                //String a = tes1.getText().toString();
                String b = namasales.getText().toString();
                String c = namabulan.getText().toString();
                Intent i = new Intent(getApplicationContext(), Performance_P_Transaksi2.class);
                //i.putExtra("tanggal",""+a+"");
                i.putExtra("namasales",""+b+"");
                i.putExtra("bulan",""+c+"");
                startActivity(i);



                //tanggal        = ((TextView) view.findViewById(R.id.tanggal)).getText().toString();
                //ListMenu();
            }
        });

        //swipe_refresh.setRefreshing(false);
    }


}