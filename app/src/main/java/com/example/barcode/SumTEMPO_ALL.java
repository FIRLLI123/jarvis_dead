package com.example.barcode;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.List;
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

import com.example.barcode.Until.BluetoothHandler;
import com.example.barcode.Until.PrinterCommands;
import com.zj.btsdk.BluetoothService;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
public class SumTEMPO_ALL extends AppCompatActivity {
    TextView keseluruhan,tempo,sudahterbayar,hasilpilihan1,hasilpilihan2;

    TextView tanggaldari,tanggalsampai;

    ListView listbayartempo;

    Button cari,btnsumtempooutlet;

    TextView tanggal1,namasales1,ttl1, ttl21;
    public static String idlist, idoutletlist, namaoutletlist, namasaleslist, namaitemtlist, qtylist, tanggallist, keteranganlist;
    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();

    int total = 0;

    private ProgressDialog pDialog;
    private Context context;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sum_tempo_all);

        context = SumTEMPO_ALL.this;
        pDialog = new ProgressDialog(context);

        dateFormatter = new SimpleDateFormat("yyyy/MM/dd", Locale.US);


        keseluruhan = (TextView) findViewById(R.id.keseluruhan);
        tempo = (TextView) findViewById(R.id.tempo);
        sudahterbayar = (TextView) findViewById(R.id.sudahterbayar);
        hasilpilihan1 = (TextView) findViewById(R.id.hasilpilihan1);
        hasilpilihan2 = (TextView) findViewById(R.id.hasilpilihan2);


        tanggaldari = (TextView) findViewById(R.id.tanggaldari);
        tanggalsampai = (TextView) findViewById(R.id.tanggalsampai);


        listbayartempo = (ListView) findViewById(R.id.listitemtempo);

        cari = (Button) findViewById(R.id.cari);
        btnsumtempooutlet = (Button) findViewById(R.id.btnsumtempooutlet);

        ttl1 = (TextView) findViewById(R.id.ttl);


        hasilpilihan1.setText("TEMPO");
        hasilpilihan2.setText("SUDAH TERBAYAR");


        keseluruhan.setBackground(getResources().getDrawable(R.drawable.custom11));
        keseluruhan.setTextColor(getResources().getColor(R.color.birutua));

        tempo.setBackground(getResources().getDrawable(R.drawable.custom10));
        tempo.setTextColor(getResources().getColor(R.color.abutua));

        sudahterbayar.setBackground(getResources().getDrawable(R.drawable.custom10));
        sudahterbayar.setTextColor(getResources().getColor(R.color.abutua));

        tanggaldari.setText(getCurrentDate());
        tanggalsampai.setText(getCurrentDate());

        tanggaldari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog1();
            }
        });


        tanggalsampai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog2();
            }
        });



        keseluruhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               hasilpilihan1.setText("TEMPO");
                hasilpilihan2.setText("SUDAH TERBAYAR");
                keseluruhan.setBackground(getResources().getDrawable(R.drawable.custom11));
                keseluruhan.setTextColor(getResources().getColor(R.color.birutua));



                tempo.setBackground(getResources().getDrawable(R.drawable.custom10));
                tempo.setTextColor(getResources().getColor(R.color.abutua));

                sudahterbayar.setBackground(getResources().getDrawable(R.drawable.custom10));
                sudahterbayar.setTextColor(getResources().getColor(R.color.abutua));

                btnsumtempooutlet.setText("LIHAT DETAIL BERDASARKAN OUTLET");

                keseluruhan();

            }
        });


        tempo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hasilpilihan1.setText("TEMPO");
                hasilpilihan2.setText("TEMPO");
                keseluruhan.setBackground(getResources().getDrawable(R.drawable.custom10));
                keseluruhan.setTextColor(getResources().getColor(R.color.abutua));



                tempo.setBackground(getResources().getDrawable(R.drawable.custom11));
                tempo.setTextColor(getResources().getColor(R.color.birutua));

                sudahterbayar.setBackground(getResources().getDrawable(R.drawable.custom10));
                sudahterbayar.setTextColor(getResources().getColor(R.color.abutua));

                btnsumtempooutlet.setText("LIHAT DETAIL BERDASARKAN OUTLET TEMPO");

                prosesdasboard2();
            }
        });


        sudahterbayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hasilpilihan1.setText("SUDAH TERBAYAR");
                hasilpilihan2.setText("SUDAH TERBAYAR");

                keseluruhan.setBackground(getResources().getDrawable(R.drawable.custom10));
                keseluruhan.setTextColor(getResources().getColor(R.color.abutua));



                tempo.setBackground(getResources().getDrawable(R.drawable.custom10));
                tempo.setTextColor(getResources().getColor(R.color.abutua));

                sudahterbayar.setBackground(getResources().getDrawable(R.drawable.custom11));
                sudahterbayar.setTextColor(getResources().getColor(R.color.birutua));


                btnsumtempooutlet.setText("LIHAT DETAIL BERDASARKAN OUTLET SUDAH TERBAYAR");

                prosesdasboard2();
            }
        });


//        cari.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                keseluruhan();
//
//
//            }
//        });

        btnsumtempooutlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





                String c = tanggaldari.getText().toString();
                String d = tanggalsampai.getText().toString();
                String e = hasilpilihan1.getText().toString();
                String f = hasilpilihan2.getText().toString();
                Intent i = new Intent(getApplicationContext(), SumTEMPO_ALL_2.class);
                i.putExtra("tanggaldari",""+c+"");
                i.putExtra("tanggalsampai",""+d+"");
                i.putExtra("hasilpilihan1",""+e+"");
                i.putExtra("hasilpilihan2",""+f+"");
                startActivity(i);


            }
        });



        prosesdasboard1();

    }

    private void showDateDialog1(){

        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Initiate DatePicker dialog
         */
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                tanggaldari.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }


    private void showDateDialog2(){

        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Initiate DatePicker dialog
         */
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                tanggalsampai.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }


    public String getCurrentDate() {
        //SimpleDateFormat contoh1 = new SimpleDateFormat("yyyy/MM/dd");
        //String hariotomatis = contoh1.format(c.getTime());
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat contoh1 = new SimpleDateFormat("yyyy/MM/dd");
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


    public void prosesdasboard1(){

        new CountDownTimer(2000, 1000) {

            public void onTick(long millisUntilFinished) {
                pDialog.setMessage("Loading..."+ millisUntilFinished / 1000);
                showDialog();
                pDialog.setCanceledOnTouchOutside(false);

                keseluruhan();
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                hideDialog();


            }
        }.start();

    }




    public void prosesdasboard2(){

        new CountDownTimer(1000, 1000) {

            public void onTick(long millisUntilFinished) {
                pDialog.setMessage("Loading... :"+ millisUntilFinished / 1000);
                showDialog();
                pDialog.setCanceledOnTouchOutside(false);

                tempo();
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                hideDialog();


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

    private void keseluruhan(){

        //swipe_refresh.setRefreshing(true);
        aruskas.clear();
        listbayartempo.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "listsumtempoall.php" )
                .addBodyParameter("tanggaldari", tanggaldari.getText().toString())
                .addBodyParameter("tanggalsampai", tanggalsampai.getText().toString())
//                .addBodyParameter("pembayaran1", tanggalsampai.getText().toString())
//                .addBodyParameter("pembayaran2", tanggalsampai.getText().toString())
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

                                map.put("item",       responses.optString("item"));
                                map.put("qty",       responses.optString("qty"));


                                total += Integer.parseInt(responses.getString("qty"));
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


                        String hasilttl = String.valueOf(total);
                        ttl1.setText(hasilttl);
                        total = 0;

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

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.listsumtempoall,
                new String[] {"item","qty"},
                new int[] {R.id.namaitemlistsumtempoall, R.id.qtylistsumtempoall});

        listbayartempo.setAdapter(simpleAdapter);

        listbayartempo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //no    = ((TextView) view.findViewById(R.id.no)).getText().toString();







                //tanggal        = ((TextView) view.findViewById(R.id.tanggal)).getText().toString();
                //ListMenu();
            }
        });
        //swipe_refresh.setRefreshing(false);
    }



    private void tempo(){

        //swipe_refresh.setRefreshing(true);
        aruskas.clear();
        listbayartempo.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "listsumtempodanbayar.php" )
                .addBodyParameter("tanggaldari", tanggaldari.getText().toString())
                .addBodyParameter("tanggalsampai", tanggalsampai.getText().toString())
                .addBodyParameter("pembayaran", hasilpilihan1.getText().toString())
                //.addBodyParameter("pembayaran2", tanggalsampai.getText().toString())
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

                                map.put("item",       responses.optString("item"));
                                map.put("qty",       responses.optString("qty"));

                                //map.put("keterangan",       responses.optString("keterangan"));

                                total += Integer.parseInt(responses.getString("qty"));


                                //total += Integer.parseInt(responses.getString("harga"))* Integer.parseInt(responses.getString("qty"));
                                //map.put("tanggal",      responses.optString("tanggal"));

                                aruskas.add(map);
                                //bayarList.add(item);
                            }

                            Adapter2();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        String hasilttl = String.valueOf(total);
                        ttl1.setText(hasilttl);
                        total = 0;

//                        ttl.setText("Total : Rp "+formatter.format(total));
//                        total = 0;
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    private void Adapter2(){

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.listsumtempoall,
                new String[] {"item","qty","total"},
                new int[] {R.id.namaitemlistsumtempoall, R.id.qtylistsumtempoall});

        listbayartempo.setAdapter(simpleAdapter);

        listbayartempo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //no    = ((TextView) view.findViewById(R.id.no)).getText().toString();







                //tanggal        = ((TextView) view.findViewById(R.id.tanggal)).getText().toString();
                //ListMenu();
            }
        });
        //swipe_refresh.setRefreshing(false);
    }



}