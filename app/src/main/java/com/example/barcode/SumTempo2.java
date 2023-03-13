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

public class SumTempo2 extends AppCompatActivity {
    TextView idoutlet,namasales;
    TextView id1,namaoutlet,namaitem,qty,tanggal,pembayaran;


    TextView tempo,sudahterbayar;

    TextView jumlahtempo,jumlahdibayarkan;

    ListView listbayartempo;

    public static String idlist, idoutletlist, namaoutletlist, namasaleslist, namaitemtlist, qtylist, tanggallist, keteranganlist;
    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sum_tempo2);

        idlist = "";
        idoutletlist = "";
        namaoutletlist = "";
        namasaleslist = "";
        namaitemtlist = "";
        qtylist = "";
        tanggallist = "";
        keteranganlist = "";


        idoutlet = (TextView) findViewById(R.id.idoutlet);
        namasales = (TextView) findViewById(R.id.namasales);

        id1 = (TextView) findViewById(R.id.id);
        namaoutlet = (TextView) findViewById(R.id.namaoutlet);
        namaitem = (TextView) findViewById(R.id.namaitem);
        qty = (TextView) findViewById(R.id.qty);
        tanggal = (TextView) findViewById(R.id.tanggal);
        pembayaran = (TextView) findViewById(R.id.pembayaran);



        jumlahtempo = (TextView) findViewById(R.id.jumlahtempo);
        jumlahdibayarkan = (TextView) findViewById(R.id.jumlahdibayarkan);


        tempo = (TextView) findViewById(R.id.tempo);
        sudahterbayar = (TextView) findViewById(R.id.sudahterbayar);

        listbayartempo = (ListView) findViewById(R.id.listbayartempo);


        Intent dasboard = getIntent();
//        String kiriman1 = dasboard.getStringExtra("idoutlet");
//        idoutlet.setText(kiriman1);

        String kiriman2 = dasboard.getStringExtra("namasales");
        namasales.setText(kiriman2);


        list();
        masihtempo();
        sudahdibayarkan();
    }

    private void list(){

        //swipe_refresh.setRefreshing(true);
        aruskas.clear();
        listbayartempo.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "listbayartemposum.php" )
                .addBodyParameter("namasales", namasales.getText().toString())
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
                                //map.put("idoutlet",       responses.optString("idoutlet"));
                                map.put("namaoutlet",       responses.optString("namaoutlet"));
                                //map.put("namasales",         responses.optString("namasales"));
                                map.put("namaitem",       responses.optString("namaitem"));
                                map.put("qty",       responses.optString("qty"));
                                map.put("tanggal",       responses.optString("tanggal"));
                                map.put("pembayaran",       responses.optString("pembayaran"));
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

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.listbayartempo2,
                new String[] {"id","namaoutlet","namaitem","qty","tanggal","pembayaran"},
                new int[] {R.id.idpenjualanlist, R.id.namaoutletlistbayartempo, R.id.namaitemlistbayartempo, R.id.qtylistbayartempo, R.id.tanggallistbayartempo, R.id.ket});

        listbayartempo.setAdapter(simpleAdapter);

        listbayartempo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //no    = ((TextView) view.findViewById(R.id.no)).getText().toString();
//                idlist    = ((TextView) view.findViewById(R.id.idpenjualanlist)).getText().toString();
//                namaoutletlist    = ((TextView) view.findViewById(R.id.namaoutletlistbayartempo)).getText().toString();
//                namaitemtlist  = ((TextView) view.findViewById(R.id.namaitemlistbayartempo)).getText().toString();
//                qtylist  = ((TextView) view.findViewById(R.id.qtylistbayartempo)).getText().toString();
//                tanggallist  = ((TextView) view.findViewById(R.id.tanggallistbayartempo)).getText().toString();
//                keteranganlist  = ((TextView) view.findViewById(R.id.ket)).getText().toString();
//
//
//
//
//
//                id1.setText(idlist);
//                //idoutlet.setText(idoutletlist);
//                namaoutlet.setText(namaoutletlist);
//                //namasales.setText(namasaleslist);
//                namaitem.setText(namaitemtlist);
//                qty.setText(qtylist);
//                tanggal.setText(tanggallist);
//                pembayaran.setText(keteranganlist);
//
//
//
//                String a = id1.getText().toString();
//                String b = idoutlet.getText().toString();
//                String c = namaoutlet.getText().toString();
//                String d = namasales.getText().toString();
//                String e = namaitem.getText().toString();
//                String f = qty.getText().toString();
//                String g = tanggal.getText().toString();
//                String h = pembayaran.getText().toString();
//
//
//
//
//                Intent i = new Intent(getApplicationContext(), BayarTempo.class);
//                i.putExtra("id",""+a+"");
//                i.putExtra("idoutlet",""+b+"");
//                i.putExtra("namaoutlet",""+c+"");
//                i.putExtra("namasales",""+d+"");
//                i.putExtra("namaitem",""+e+"");
//                i.putExtra("qty",""+f+"");
//                i.putExtra("tanggal",""+g+"");
//                i.putExtra("pembayaran",""+h+"");
//                startActivity(i);



                //tanggal        = ((TextView) view.findViewById(R.id.tanggal)).getText().toString();
                //ListMenu();
            }
        });
        //swipe_refresh.setRefreshing(false);
    }


    private void masihtempo() {

        AndroidNetworking.post(Config.host + "masihtemposum.php")
                //.addBodyParameter("idoutlet", idoutlet.getText().toString())
                .addBodyParameter("namasales", namasales.getText().toString())
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

        AndroidNetworking.post(Config.host + "sudahdibayarsum.php")
                //.addBodyParameter("idoutlet", idoutlet.getText().toString())
                .addBodyParameter("namasales", namasales.getText().toString())
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