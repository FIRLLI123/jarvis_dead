package com.example.barcode;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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

public class Performance_nota extends AppCompatActivity {
    TextView tes1, tanggalsampai,detaildata, t_perdana, t_voucher,t_sales, t_ds,detaildata_voucher;
    ListView listtest1, listtest2;
    Button cekdetail1;
    EditText namasaleslist1;
    LinearLayout carisum1;

    View perdana_v, voucher_v;

    LinearLayout perdana, voucher, sales, ds;

    TextView pilihan;

    TextView tanggal_min_1, tanggalsampai_min_1;
    TextView bulan_min_1, bulan_1;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    public static String LINK, namasaleslist, tanggallist, totallist, hargalistperdana, totallistperdana;
    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.performance_nota);
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

        perdana = (LinearLayout) findViewById(R.id.perdana);
        voucher = (LinearLayout) findViewById(R.id.voucher);

        sales = (LinearLayout) findViewById(R.id.sales);
        ds = (LinearLayout) findViewById(R.id.ds);

        perdana_v = (View) findViewById(R.id.perdana_v);
        voucher_v = (View) findViewById(R.id.voucher_v);

        t_perdana = (TextView) findViewById(R.id.t_perdana);
        t_voucher = (TextView) findViewById(R.id.t_voucher);

        t_sales = (TextView) findViewById(R.id.t_sales);
        t_ds = (TextView) findViewById(R.id.t_ds);


        tes1 = (TextView) findViewById(R.id.tanggal);
        tanggalsampai = (TextView) findViewById(R.id.tanggalsampai);

        tanggal_min_1 = (TextView) findViewById(R.id.tanggal_min_1);
        tanggalsampai_min_1 = (TextView) findViewById(R.id.tanggalsampai_min_1);

        bulan_min_1 = (TextView) findViewById(R.id.bulan_min_1);
        bulan_1 = (TextView) findViewById(R.id.bulan_1);

        listtest1 = (ListView) findViewById(R.id.listtest);
        listtest2 = (ListView) findViewById(R.id.listtest2);

        namasaleslist1 = (EditText) findViewById(R.id.namasaleslist);

        carisum1 = (LinearLayout) findViewById(R.id.carisum);
        cekdetail1 = (Button) findViewById(R.id.cekdetail);
        detaildata = (TextView) findViewById(R.id.detaildata);
        detaildata_voucher = (TextView) findViewById(R.id.detaildata_voucher);


        tes1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        tanggalsampai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog2();
            }
        });

        carisum1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list();
            }
        });

        t_perdana.setTextColor(getResources().getColor(R.color.white));
//        perdana_v.setVisibility(View.VISIBLE);
//        perdana_v.setBackground(getResources().getDrawable(R.color.white));

        t_voucher.setTextColor(getResources().getColor(R.color.navy));
        //voucher_v.setVisibility(View.GONE);

//                list();
//                listtest1.setVisibility(View.VISIBLE);
//                listtest2.setVisibility(View.GONE);

        //list();
        listtest1.setVisibility(View.VISIBLE);
        listtest2.setVisibility(View.GONE);


        perdana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //namagudang.setText("GUDANG PERDANA");

                t_perdana.setTextColor(getResources().getColor(R.color.white));
                perdana.setBackground(getResources().getDrawable(R.drawable.kota_nav2y));
                voucher.setBackground(getResources().getDrawable(R.drawable.kotak_transparan_putih2));
//                perdana_v.setBackground(getResources().getDrawable(R.color.white));

                t_voucher.setTextColor(getResources().getColor(R.color.navy));
                //detaildata.setVisibility(View.VISIBLE);
                //detaildata_voucher.setVisibility(View.GONE);
                //voucher_v.setVisibility(View.GONE);

//                list();
//                listtest1.setVisibility(View.VISIBLE);
//                listtest2.setVisibility(View.GONE);

                list();
                listtest1.setVisibility(View.VISIBLE);
                listtest2.setVisibility(View.GONE);


                //prosesdasboard1();

            }
        });

        voucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                t_perdana.setTextColor(getResources().getColor(R.color.navy));
                perdana.setBackground(getResources().getDrawable(R.drawable.kotak_transparan_putih2));
                voucher.setBackground(getResources().getDrawable(R.drawable.kota_nav2y));
                //perdana_v.setVisibility(View.GONE);


                t_voucher.setTextColor(getResources().getColor(R.color.white));
                //detaildata.setVisibility(View.GONE);
                //detaildata_voucher.setVisibility(View.VISIBLE);
//                voucher_v.setVisibility(View.VISIBLE);
//                voucher_v.setBackground(getResources().getDrawable(R.color.white));

//                list2();
//                listtest1.setVisibility(View.GONE);
//                listtest2.setVisibility(View.VISIBLE);


                list2();
                listtest1.setVisibility(View.GONE);
                listtest2.setVisibility(View.VISIBLE);


                //prosesdasboard1();

            }
        });






        sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //namagudang.setText("GUDANG PERDANA");

                t_sales.setTextColor(getResources().getColor(R.color.white));
                sales.setBackground(getResources().getDrawable(R.drawable.kota_nav2y));
                ds.setBackground(getResources().getDrawable(R.drawable.kotak_transparan_putih2));
//                perdana_v.setBackground(getResources().getDrawable(R.color.white));

                t_ds.setTextColor(getResources().getColor(R.color.navy));
                //detaildata.setVisibility(View.VISIBLE);
                //detaildata_voucher.setVisibility(View.GONE);
                //voucher_v.setVisibility(View.GONE);

//                list();
//                listtest1.setVisibility(View.VISIBLE);
//                listtest2.setVisibility(View.GONE);

                list();
                listtest1.setVisibility(View.VISIBLE);
                listtest2.setVisibility(View.GONE);


                //prosesdasboard1();

            }
        });

        ds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                t_sales.setTextColor(getResources().getColor(R.color.navy));
                sales.setBackground(getResources().getDrawable(R.drawable.kotak_transparan_putih2));
                ds.setBackground(getResources().getDrawable(R.drawable.kota_nav2y));
                //perdana_v.setVisibility(View.GONE);


                t_ds.setTextColor(getResources().getColor(R.color.white));
                //detaildata.setVisibility(View.GONE);
                //detaildata_voucher.setVisibility(View.VISIBLE);
//                voucher_v.setVisibility(View.VISIBLE);
//                voucher_v.setBackground(getResources().getDrawable(R.color.white));

//                list2();
//                listtest1.setVisibility(View.GONE);
//                listtest2.setVisibility(View.VISIBLE);


                list2();
                listtest1.setVisibility(View.GONE);
                listtest2.setVisibility(View.VISIBLE);


                //prosesdasboard1();

            }
        });




        detaildata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = tes1.getText().toString();
                String b = namasaleslist1.getText().toString();
                //String c = namasalesinputperdana1.getText().toString();
                Intent i = new Intent(getApplicationContext(), detailallperdana.class);
                i.putExtra("tanggal",""+a+"");
                //i.putExtra("namasales",""+b+"");
                startActivity(i);
            }
        });


        detaildata_voucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = tes1.getText().toString();
                String b = namasaleslist1.getText().toString();
                //String c = namasalesinputperdana1.getText().toString();
                Intent i = new Intent(getApplicationContext(), DetailVoucher.class);
                i.putExtra("tanggal",""+a+"");
                //i.putExtra("namasales",""+b+"");
                startActivity(i);
            }
        });

        cekdetail1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent i = new Intent(getApplicationContext(), SumPerdana2.class);
//                    startActivity(i);

                if (namasaleslist1.getText().toString().length() == 0) {                    //1
                    //jika form Email belum di isi / masih kosong
                    //iddelete1.setError("");
                    Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
                }  else {
                    String a = tes1.getText().toString();
                    String b = namasaleslist1.getText().toString();
                    //String c = namasalesinputperdana1.getText().toString();
                    Intent i = new Intent(getApplicationContext(), SumPerdana2.class);
                    i.putExtra("tanggal",""+a+"");
                    i.putExtra("namasales",""+b+"");
                    startActivity(i);
                }
            }
        });
        tes1.setText(getCurrentDate());
        tanggalsampai.setText(getCurrentDate());
        list();
        //list2();
    }

    private void showDateDialog(){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tes1.setText(dateFormatter.format(newDate.getTime()));

                Calendar minDate = (Calendar) newDate.clone();
                minDate.add(Calendar.MONTH, -1);
                tanggal_min_1.setText(dateFormatter.format(minDate.getTime()));

                SimpleDateFormat monthFormatter = new SimpleDateFormat("MMMM", new Locale("id", "ID"));

                // Set bulan_min_1 ke bulan sebelumnya
                bulan_min_1.setText(monthFormatter.format(minDate.getTime()));

                // Set bulan_1 ke bulan saat ini
                bulan_1.setText(monthFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }


    private void showDateDialog2(){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tanggalsampai.setText(dateFormatter.format(newDate.getTime()));

                Calendar minDate = (Calendar) newDate.clone();
                minDate.add(Calendar.MONTH, -1);
                tanggalsampai_min_1.setText(dateFormatter.format(minDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
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


    private void list(){

        //swipe_refresh.setRefreshing(true);
        aruskas.clear();
        listtest1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "mom_nota_perdana.php" )
                .addBodyParameter("tanggal", tes1.getText().toString())
                .addBodyParameter("tanggalsampai", tanggalsampai.getText().toString())
                .addBodyParameter("tanggal_min_1", tanggal_min_1.getText().toString())
                .addBodyParameter("tanggalsampai_min_1", tanggalsampai_min_1.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {


                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
                        try {
                            JSONArray jsonArray = response.optJSONArray("result");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Data_BayarEX item = new Data_BayarEX();
                                JSONObject responses    = jsonArray.getJSONObject(i);
                                HashMap<String, String> map = new HashMap<String, String>();
                                //map.put("no",         responses.optString("no"));
                                map.put("namasales",         responses.optString("namasales"));
                                map.put("total",         responses.optString("total"));
                                map.put("m_min_1",       responses.optString("m_min_1"));
                                map.put("m_1",       responses.optString("m_1"));
                                map.put("selisih",       responses.optString("selisih"));
                                map.put("ach",       responses.optString("ach"));




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

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.list_mom_nota,
                new String[] {"namasales","total","m_min_1","m_1","selisih","ach"},
                new int[] {R.id.namasaleslisttest,R.id.totallist, R.id.m_min_1_list, R.id.m1_list, R.id.selisihlist, R.id.achlist}) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                return view;

            }
        };



        listtest1.setAdapter(simpleAdapter);

        listtest1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                namasaleslist    = ((TextView) view.findViewById(R.id.namasaleslisttest)).getText().toString();

                namasaleslist1.setText(namasaleslist);

                String b = namasaleslist1.getText().toString();
                Intent i = new Intent(getApplicationContext(), Diagram_nota_performance.class);
                i.putExtra("namasales",""+b+"");

                startActivity(i);

            }
        });

        //swipe_refresh.setRefreshing(false);
    }

    private void list2(){

        //swipe_refresh.setRefreshing(true);
        aruskas.clear();
        listtest1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "mom_nota_voucher.php" )
                .addBodyParameter("tanggal", tes1.getText().toString())
                .addBodyParameter("tanggalsampai", tanggalsampai.getText().toString())
                .addBodyParameter("tanggal_min_1", tanggal_min_1.getText().toString())
                .addBodyParameter("tanggalsampai_min_1", tanggalsampai_min_1.getText().toString())
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
                                map.put("total",         responses.optString("total"));
                                map.put("m_min_1",       responses.optString("m_min_1"));
                                map.put("m_1",       responses.optString("m_1"));
                                map.put("selisih",       responses.optString("selisih"));
                                map.put("ach",       responses.optString("ach"));




                                //total += Integer.parseInt(responses.getString("harga"))* Integer.parseInt(responses.getString("qty"));
                                //map.put("tanggal",      responses.optString("tanggal"));

                                aruskas.add(map);
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

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.list_mom_nota,
                new String[] {"namasales","total","m_min_1","m_1","selisih","ach"},
                new int[] {R.id.namasaleslisttest,R.id.totallist, R.id.m_min_1_list, R.id.m1_list, R.id.selisihlist, R.id.achlist}) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView nomorTextView = view.findViewById(R.id.listnomor);
                LinearLayout listbulat = view.findViewById(R.id.listbulat);



                return view;

            }
        };



        listtest2.setAdapter(simpleAdapter);


        listtest2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //no    = ((TextView) view.findViewById(R.id.no)).getText().toString();
                namasaleslist    = ((TextView) view.findViewById(R.id.namasaleslisttest)).getText().toString();
                //tanggallist  = ((TextView) view.findViewById(R.id.tanggallisttest)).getText().toString();



                namasaleslist1.setText(namasaleslist);
                //tes1.setText(tanggallist);



                //String a = tes1.getText().toString();
                String b = namasaleslist1.getText().toString();
                //String c = tanggalsampai.getText().toString();
                //String c = namasalesinputperdana1.getText().toString();
                Intent i = new Intent(getApplicationContext(), Diagram_nota_performance.class);
                //i.putExtra("tanggal",""+a+"");
                i.putExtra("namasales",""+b+"");
                //i.putExtra("tanggalsampai",""+c+"");
                startActivity(i);



                //tanggal        = ((TextView) view.findViewById(R.id.tanggal)).getText().toString();
                //ListMenu();
            }
        });

        //swipe_refresh.setRefreshing(false);
    }


    //----------------------------------------------------------
    //----------------------------------------------------------









}