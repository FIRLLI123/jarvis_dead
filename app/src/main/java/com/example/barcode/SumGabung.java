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

public class SumGabung extends AppCompatActivity {
    TextView tes1;
    ListView listtest1;
    ListView listtest21;
    ListView listtest31;
    Button carisum1, cekdetail1;
    EditText namasaleslist1;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    public static String LINK, namasaleslist, tanggallist, totallist, hargalistperdana, totallistperdana;
    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();

    ArrayList<HashMap<String, String>> aruskas2 = new ArrayList<HashMap<String, String>>();

    ArrayList<HashMap<String, String>> aruskas3 = new ArrayList<HashMap<String, String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sum_gabung);
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
        dateFormatter = new SimpleDateFormat("yyyy/M/d", Locale.US);

        tes1 = (TextView) findViewById(R.id.tanggal);
        listtest1 = (ListView) findViewById(R.id.listtest);
        listtest21 = (ListView) findViewById(R.id.listtest2);
        listtest31 = (ListView) findViewById(R.id.listtest3);

        namasaleslist1 = (EditText) findViewById(R.id.namasaleslist);

        carisum1 = (Button) findViewById(R.id.carisum);
        cekdetail1 = (Button) findViewById(R.id.cekdetail);


        tes1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        carisum1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //list();
                list2();
            }
        });


        cekdetail1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent i = new Intent(getApplicationContext(), SumGabung.class);
//                    startActivity(i);

                if (namasaleslist1.getText().toString().length() == 0) {                    //1
                    //jika form Email belum di isi / masih kosong
                    //iddelete1.setError("");
                    Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
                }  else {
                    String a = tes1.getText().toString();
                    String b = namasaleslist1.getText().toString();
                    //String c = namasalesinputperdana1.getText().toString();
                    Intent i = new Intent(getApplicationContext(), SumGabung.class);
                    i.putExtra("tanggal",""+a+"");
                    i.putExtra("namasales",""+b+"");
                    startActivity(i);
                }
            }
        });
        tes1.setText(getCurrentDate());
        list();
        list2();
    }

    private void showDateDialog(){

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
                tes1.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }

    public String getCurrentDate() {
        final Calendar c = Calendar.getInstance();
        int year, month, day;
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DATE);
        SimpleDateFormat contoh1 = new SimpleDateFormat("EEEE");

        String hariotomatis = contoh1.format(c.getTime());

        //return day +"/" + (month+1) + "/" + year;
        //return (month+1) +"/" + day + "/" + year;
        return year + "/" + (month + 1) + "/" + day;
    }


    private void list(){

        //swipe_refresh.setRefreshing(true);
        aruskas.clear();
        listtest1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "sumfull.php" )
                .addBodyParameter("tanggal", tes1.getText().toString())
                .addBodyParameter("namasales", namasaleslist1.getText().toString())
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
                                map.put("tanggal",       responses.optString("tanggal"));
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

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.listtest,
                new String[] {"namasales","tanggal","total"},
                new int[] {R.id.namasaleslisttest, R.id.tanggallisttest, R.id.totallisttest});

        listtest1.setAdapter(simpleAdapter);

        listtest1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //no    = ((TextView) view.findViewById(R.id.no)).getText().toString();
                namasaleslist    = ((TextView) view.findViewById(R.id.namasaleslisttest)).getText().toString();
                tanggallist  = ((TextView) view.findViewById(R.id.tanggallisttest)).getText().toString();



                namasaleslist1.setText(namasaleslist);
                tes1.setText(tanggallist);


                String a = tes1.getText().toString();
                String b = namasaleslist1.getText().toString();
                //String c = namasalesinputperdana1.getText().toString();
                Intent i = new Intent(getApplicationContext(), SumGabung.class);
                i.putExtra("tanggal",""+a+"");
                i.putExtra("namasales",""+b+"");
                startActivity(i);


                //tanggal        = ((TextView) view.findViewById(R.id.tanggal)).getText().toString();
                //ListMenu();
            }
        });

        //swipe_refresh.setRefreshing(false);
    }


    private void list2(){

        //swipe_refresh.setRefreshing(true);
        aruskas2.clear();
        listtest21.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "sumfullvoucher.php" )
                .addBodyParameter("tanggal", tes1.getText().toString())
                .addBodyParameter("namasales", namasaleslist1.getText().toString())
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
                                map.put("tanggal",       responses.optString("tanggal"));
                                map.put("total",       rupiahFormat.format(responses.optDouble("total")));



                                //total += Integer.parseInt(responses.getString("harga"))* Integer.parseInt(responses.getString("qty"));
                                //map.put("tanggal",      responses.optString("tanggal"));

                                aruskas2.add(map);
                                //bayarList.add(item);
                            }

                            Adapter2();

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

    private void Adapter2(){

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.listtest2,
                new String[] {"namasales","tanggal","total"},
                new int[] {R.id.namasaleslisttest, R.id.tanggallisttest, R.id.totallisttest});

        listtest1.setAdapter(simpleAdapter);

        listtest1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //no    = ((TextView) view.findViewById(R.id.no)).getText().toString();
                namasaleslist    = ((TextView) view.findViewById(R.id.namasaleslisttest)).getText().toString();
                tanggallist  = ((TextView) view.findViewById(R.id.tanggallisttest)).getText().toString();



                namasaleslist1.setText(namasaleslist);
                tes1.setText(tanggallist);


                String a = tes1.getText().toString();
                String b = namasaleslist1.getText().toString();
                //String c = namasalesinputperdana1.getText().toString();
                Intent i = new Intent(getApplicationContext(), SumGabung.class);
                i.putExtra("tanggal",""+a+"");
                i.putExtra("namasales",""+b+"");
                startActivity(i);


                //tanggal        = ((TextView) view.findViewById(R.id.tanggal)).getText().toString();
                //ListMenu();
            }
        });

        //swipe_refresh.setRefreshing(false);
    }
//
//    private void list3(){
//
//        //swipe_refresh.setRefreshing(true);
//        aruskas.clear();
//        listtest31.setAdapter(null);
//
//        //Log.d("link", LINK );
//        AndroidNetworking.post( Config.host + "sumfull.php" )
//                .addBodyParameter("tanggal", tes1.getText().toString())
//                .addBodyParameter("namasales", namasaleslist1.getText().toString())
//                .setPriority(Priority.MEDIUM)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        // do anything with response
//
//
//
//                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
///*
//                        text_masuk.setText(
//                                rupiahFormat.format(response.optDouble("yes")));
//                        text_keluar.setText(
//                                rupiahFormat.format( response.optDouble("oke") ));
//                        text_total.setText(
//                                rupiahFormat.format( response.optDouble("saldo") ));
//**/
//
//                        try {
//                            JSONArray jsonArray = response.optJSONArray("result");
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                Data_BayarEX item = new Data_BayarEX();
//                                JSONObject responses    = jsonArray.getJSONObject(i);
//                                HashMap<String, String> map = new HashMap<String, String>();
//                                //map.put("no",         responses.optString("no"));
//                                map.put("namasales",         responses.optString("namasales"));
//                                map.put("tanggal",       responses.optString("tanggal"));
//                                map.put("total",       rupiahFormat.format(responses.optDouble("total")));
//
//
//
//                                //total += Integer.parseInt(responses.getString("harga"))* Integer.parseInt(responses.getString("qty"));
//                                //map.put("tanggal",      responses.optString("tanggal"));
//
//                                aruskas.add(map);
//                                //bayarList.add(item);
//                            }
//
//                            Adapter3();
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
////                        ttl.setText("Total : Rp "+formatter.format(total));
////                        total = 0;
//                    }
//                    @Override
//                    public void onError(ANError error) {
//                        // handle error
//                    }
//                });
//    }
//
//    private void Adapter3(){
//
//        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.listtest,
//                new String[] {"namasales","tanggal","total"},
//                new int[] {R.id.namasaleslisttest, R.id.tanggallisttest, R.id.totallisttest});
//
//        listtest1.setAdapter(simpleAdapter);
//
//        listtest1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                //no    = ((TextView) view.findViewById(R.id.no)).getText().toString();
//                namasaleslist    = ((TextView) view.findViewById(R.id.namasaleslisttest)).getText().toString();
//                tanggallist  = ((TextView) view.findViewById(R.id.tanggallisttest)).getText().toString();
//
//
//
//                namasaleslist1.setText(namasaleslist);
//                tes1.setText(tanggallist);
//
//
//                String a = tes1.getText().toString();
//                String b = namasaleslist1.getText().toString();
//                //String c = namasalesinputperdana1.getText().toString();
//                Intent i = new Intent(getApplicationContext(), SumGabung.class);
//                i.putExtra("tanggal",""+a+"");
//                i.putExtra("namasales",""+b+"");
//                startActivity(i);
//
//
//                //tanggal        = ((TextView) view.findViewById(R.id.tanggal)).getText().toString();
//                //ListMenu();
//            }
//        });
//
//        //swipe_refresh.setRefreshing(false);
//    }



}