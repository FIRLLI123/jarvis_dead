package com.example.barcode;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Sum_Penjual_Gabung_2 extends AppCompatActivity {
    TextView tes1, namaoutletlist1, idoutletlist1, tanggalsampai, t_perdana, t_voucher;
    ListView listtest1, listtest2;
    Button carisum1, cetak1, sumitem1, cetak_voucher;
    EditText namasaleslistsum21;

    View perdana_v, voucher_v;

    LinearLayout perdana, voucher;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    public static String LINK, idoutletlist, namasaleslist, tanggallist, totallist, hargalistperdana, totallistperdana, namaoutletlist;
    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sum_penjual_gabung2);

        NumberFormat formatter = new DecimalFormat("#,###,###,###");

        LINK = Config.host + "history.php";
        idoutletlist = "";
        namasaleslist = "";
        tanggallist = "";
        namaoutletlist = "";
        totallist = "";
        hargalistperdana = "";
        totallistperdana = "";
        //totallist = "";
        //query_kas = ""; query_total = "";
        //filter = false;
        dateFormatter = new SimpleDateFormat("yyyy/MM/dd", Locale.US);

        perdana = (LinearLayout) findViewById(R.id.perdana);
        voucher = (LinearLayout) findViewById(R.id.voucher);

        perdana_v = (View) findViewById(R.id.perdana_v);
        voucher_v = (View) findViewById(R.id.voucher_v);

        t_perdana = (TextView) findViewById(R.id.t_perdana);
        t_voucher = (TextView) findViewById(R.id.t_voucher);


        tes1 = (TextView) findViewById(R.id.tanggal);
        tanggalsampai = (TextView) findViewById(R.id.tanggalsampai);

        namaoutletlist1 = (TextView) findViewById(R.id.namaoutletlist);
        idoutletlist1 = (TextView) findViewById(R.id.idoutletlist);
        listtest1 = (ListView) findViewById(R.id.listsum2);
        listtest2 = (ListView) findViewById(R.id.listsum3);

        namasaleslistsum21 = (EditText) findViewById(R.id.namasaleslistsum2);

        cetak1 = (Button) findViewById(R.id.cetak);
        cetak_voucher = (Button) findViewById(R.id.cetak_voucher);
        sumitem1 = (Button) findViewById(R.id.sumitem);

        Intent i = getIntent();
        String kiriman = i.getStringExtra("tanggal");
        tes1.setText(kiriman);

        String kiriman3 = i.getStringExtra("tanggalsampai");
        tanggalsampai.setText(kiriman3);

        String kiriman2 = i.getStringExtra("namasales");
        namasaleslistsum21.setText(kiriman2);

        //list();

        tes1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showDateDialog();
            }
        });



        cetak1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (namaoutletlist1.getText().toString().length() == 0) {                    //1
                    //jika form Email belum di isi / masih kosong
                    //namasaleslistsum21.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Silahkan Pilih Terlebih Dahulu", Toast.LENGTH_LONG).show();
                } else {

                    String a = idoutletlist1.getText().toString();
                    String b = namaoutletlist1.getText().toString();
                    String c = namasaleslistsum21.getText().toString();
                    String d = tes1.getText().toString();
                    Intent i = new Intent(getApplicationContext(), Input_perdanaTransfer.class);
                    i.putExtra("idoutlet",""+a+"");
                    i.putExtra("namaoutlet",""+b+"");
                    i.putExtra("namasales",""+c+"");
                    i.putExtra("tanggal",""+d+"");
                    startActivity(i);
                }



            }
        });

        cetak_voucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (namaoutletlist1.getText().toString().length() == 0) {                    //1
                    //jika form Email belum di isi / masih kosong
                    //namasaleslistsum21.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Silahkan Pilih Terlebih Dahulu", Toast.LENGTH_LONG).show();
                } else {

                    String a = idoutletlist1.getText().toString();
                    String b = namaoutletlist1.getText().toString();
                    String c = namasaleslistsum21.getText().toString();
                    String d = tes1.getText().toString();
                    Intent i = new Intent(getApplicationContext(), Input_voucherTransfer.class);
                    i.putExtra("idoutlet",""+a+"");
                    i.putExtra("namaoutlet",""+b+"");
                    i.putExtra("namasales",""+c+"");
                    i.putExtra("tanggal",""+d+"");
                    startActivity(i);
                }



            }
        });

        sumitem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (namaoutletlist1.getText().toString().length() == 0) {                    //1
                    //jika form Email belum di isi / masih kosong
                    //namasaleslistsum21.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Silahkan Pilih Terlebih Dahulu", Toast.LENGTH_LONG).show();
                } else {

                    //String a = idoutletlist1.getText().toString();
                    String b = namasaleslistsum21.getText().toString();
                    //String c = namasalesinputperdana1.getText().toString();
                    String d = tes1.getText().toString();
                    Intent i = new Intent(getApplicationContext(), Sum_Penjualan_Item.class);
                    //i.putExtra("idoutlet",""+a+"");
                    i.putExtra("namasales",""+b+"");
                    //i.putExtra("namasales",""+c+"");
                    i.putExtra("tanggal",""+d+"");
                    startActivity(i);


                }



            }
        });




        t_perdana.setTextColor(getResources().getColor(R.color.white));
        perdana.setBackground(getResources().getDrawable(R.drawable.kota_navy));
        voucher.setBackground(getResources().getDrawable(R.drawable.kotak_transparan_putih));
//                perdana_v.setBackground(getResources().getDrawable(R.color.white));

        t_voucher.setTextColor(getResources().getColor(R.color.navy));

//                list();
//                listtest1.setVisibility(View.VISIBLE);
//                listtest2.setVisibility(View.GONE);

        //list();
        listtest1.setVisibility(View.VISIBLE);
        listtest2.setVisibility(View.GONE);


        cetak1.setVisibility(View.VISIBLE);
        cetak_voucher.setVisibility(View.GONE);


        perdana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //namagudang.setText("GUDANG PERDANA");

                t_perdana.setTextColor(getResources().getColor(R.color.white));
                perdana.setBackground(getResources().getDrawable(R.drawable.kota_navy));
                voucher.setBackground(getResources().getDrawable(R.drawable.kotak_transparan_putih));
//                perdana_v.setBackground(getResources().getDrawable(R.color.white));

                t_voucher.setTextColor(getResources().getColor(R.color.navy));

//                list();
//                listtest1.setVisibility(View.VISIBLE);
//                listtest2.setVisibility(View.GONE);

                list();
                listtest1.setVisibility(View.VISIBLE);
                listtest2.setVisibility(View.GONE);


                cetak1.setVisibility(View.VISIBLE);
                cetak_voucher.setVisibility(View.GONE);

                //prosesdasboard1();

            }
        });

        voucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                t_perdana.setTextColor(getResources().getColor(R.color.navy));
                perdana.setBackground(getResources().getDrawable(R.drawable.kotak_transparan_putih));
                voucher.setBackground(getResources().getDrawable(R.drawable.kota_navy));
                //perdana_v.setVisibility(View.GONE);


                t_voucher.setTextColor(getResources().getColor(R.color.white));

//                list2();
//                listtest1.setVisibility(View.GONE);
//                listtest2.setVisibility(View.VISIBLE);


                list2();
                listtest1.setVisibility(View.GONE);
                listtest2.setVisibility(View.VISIBLE);


                cetak1.setVisibility(View.GONE);
                cetak_voucher.setVisibility(View.VISIBLE);

                //prosesdasboard1();

            }
        });

        list();
        //list2();

    }



    private void list() {

        //swipe_refresh.setRefreshing(true);
        aruskas.clear();
        listtest1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post(Config.host + "sum2ex.php")
                .addBodyParameter("tanggal", tes1.getText().toString())
                .addBodyParameter("tanggalsampai", tanggalsampai.getText().toString())
                .addBodyParameter("namasales", namasaleslistsum21.getText().toString())
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
                                JSONObject responses = jsonArray.getJSONObject(i);
                                HashMap<String, String> map = new HashMap<String, String>();
                                //map.put("no",         responses.optString("no"));

                                map.put("idoutlet", responses.optString("idoutlet"));
                                map.put("namaoutlet", responses.optString("namaoutlet"));
                                map.put("item",       responses.optString("item"));
                                map.put("qty",       responses.optString("qty"));
                                map.put("harga",       responses.optString("harga"));
                                map.put("pembayaran",       responses.optString("pembayaran"));
                                map.put("total",       rupiahFormat.format(responses.optDouble("total")));
                                //map.put("total", responses.optString("total"));


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

    private void Adapter() {

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.listsum2,
                new String[]{"idoutlet","namaoutlet", "item", "qty", "harga", "pembayaran", "total"},
                new int[]{R.id.idoutletlistsum2,R.id.namaoutletlistsum2, R.id.namalistsum2, R.id.qtylistsum2, R.id.hargalistsum2, R.id.pembayaranlistsum2, R.id.totallistsum2});

        listtest1.setAdapter(simpleAdapter);

        listtest1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //no    = ((TextView) view.findViewById(R.id.no)).getText().toString();
                //namasaleslist = ((TextView) view.findViewById(R.id.namasaleslisttest)).getText().toString();
                idoutletlist = ((TextView) view.findViewById(R.id.idoutletlistsum2)).getText().toString();
                namaoutletlist = ((TextView) view.findViewById(R.id.namaoutletlistsum2)).getText().toString();



                //namasaleslistsum21.setText(namasaleslist);
                idoutletlist1.setText(idoutletlist);
                namaoutletlist1.setText(namaoutletlist);


                //tanggal        = ((TextView) view.findViewById(R.id.tanggal)).getText().toString();
                //ListMenu();
            }
        });
    }


    //--------------------------------------------------------------------------------------------------------------------------------






    private void list2() {

        //swipe_refresh.setRefreshing(true);
        aruskas.clear();
        listtest2.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post(Config.host + "sumvoucher2ex.php")
                .addBodyParameter("tanggal", tes1.getText().toString())
                .addBodyParameter("tanggalsampai", tanggalsampai.getText().toString())
                .addBodyParameter("namasales", namasaleslistsum21.getText().toString())
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
                                JSONObject responses = jsonArray.getJSONObject(i);
                                HashMap<String, String> map = new HashMap<String, String>();
                                //map.put("no",         responses.optString("no"));

                                map.put("idoutlet", responses.optString("idoutlet"));
                                map.put("namaoutlet", responses.optString("namaoutlet"));
                                map.put("item",       responses.optString("item"));
                                map.put("qty",       responses.optString("qty"));
                                map.put("harga",       responses.optString("harga"));
                                map.put("pembayaran",       responses.optString("pembayaran"));
                                map.put("total",       rupiahFormat.format(responses.optDouble("total")));
                                //map.put("total", responses.optString("total"));


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

    private void Adapter2() {

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.listsum2,
                new String[]{"idoutlet","namaoutlet", "item", "qty", "harga", "pembayaran", "total"},
                new int[]{R.id.idoutletlistsum2,R.id.namaoutletlistsum2, R.id.namalistsum2, R.id.qtylistsum2, R.id.hargalistsum2, R.id.pembayaranlistsum2, R.id.totallistsum2});

        listtest2.setAdapter(simpleAdapter);

        listtest2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //no    = ((TextView) view.findViewById(R.id.no)).getText().toString();
                //namasaleslist = ((TextView) view.findViewById(R.id.namasaleslisttest)).getText().toString();
                idoutletlist = ((TextView) view.findViewById(R.id.idoutletlistsum2)).getText().toString();
                namaoutletlist = ((TextView) view.findViewById(R.id.namaoutletlistsum2)).getText().toString();



                //namasaleslistsum21.setText(namasaleslist);
                idoutletlist1.setText(idoutletlist);
                namaoutletlist1.setText(namaoutletlist);


                //tanggal        = ((TextView) view.findViewById(R.id.tanggal)).getText().toString();
                //ListMenu();
            }
        });
    }




}