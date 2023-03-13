package com.example.barcode;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Absen extends AppCompatActivity {
ImageView ok1, ga1;
    private ProgressDialog pDialog;
    private Context context;
    FusedLocationProviderClient fusedLocationProviderClient;

    Button buttonabsendatang1, cek1, cek2, lokasidatang1;
    private final static int REQUEST_CODE = 100;

    TextView validasi1, username1, password1, hallo1, hallotampung1, tabsendatang1, namasales1, absendatang1, lokasiabsendatang1, absenpulang1, lokasiabsenpulang1, longitude1, latitude1, tanggal1, kecamatan1;

TextView jam2;

    ListView listtest1;

    public static String LINK, namasaleslist, tanggallist, totallist, hargalistperdana, totallistperdana;
    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.absen);

        hallo1 = (TextView) findViewById(R.id.hallo);
        validasi1 = (TextView) findViewById(R.id.validasi);
        hallotampung1 = (TextView) findViewById(R.id.hallotampung);
        username1 = (TextView) findViewById(R.id.username);
        password1 = (TextView) findViewById(R.id.password);
        tabsendatang1 = (TextView) findViewById(R.id.tabsendatang);
        namasales1 = (TextView) findViewById(R.id.namasales);
        absendatang1 =(TextView) findViewById(R.id.absendatang);
        lokasiabsendatang1 =(TextView) findViewById(R.id.lokasiabsendatang);
        absenpulang1 =(TextView) findViewById(R.id.absenpulang);
        lokasiabsenpulang1 =(TextView) findViewById(R.id.lokasiabsenpulang);
        longitude1 =(TextView) findViewById(R.id.longitude);
        latitude1 =(TextView) findViewById(R.id.latitude);
        tanggal1 =(TextView) findViewById(R.id.tanggal);
        kecamatan1 =(TextView) findViewById(R.id.kecamatan);

        jam2 =(TextView) findViewById(R.id.jam2);


        listtest1 = (ListView) findViewById(R.id.listtest);

        ok1 =(ImageView) findViewById(R.id.ok);
        ga1 =(ImageView) findViewById(R.id.ga);

        context = Absen.this;
        pDialog = new ProgressDialog(context);

        Intent kolomlogin = getIntent();
        String kiriman = kolomlogin.getStringExtra("nama");
        namasales1.setText(kiriman);
        String kiriman2 = kolomlogin.getStringExtra("username");
        username1.setText(kiriman2);

//        latitude = findViewById(R.id.lattitude);
//        longitude = findViewById(R.id.longitude);
//        address = findViewById(R.id.address);
//        city = findViewById(R.id.city);
//        country = findViewById(R.id.country);
        buttonabsendatang1 =(Button) findViewById(R.id.buttonabsendatang);
        cek1 =(Button) findViewById(R.id.cek);
        cek2 =(Button) findViewById(R.id.cek2);

        lokasidatang1 =(Button) findViewById(R.id.lokasidatang);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        buttonabsendatang1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                cariid();
//                getLastLocation();

//                    cariid();
//                    getLastLocation();
                if ( validasi1.getText().toString().equals("OKE")){
                                         //1
                    Toast.makeText(getApplicationContext(), "UNTUK HARI INI SUDAH ABSEN",
                            Toast.LENGTH_LONG).show();

                    //Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
                }else if ( kecamatan1.getText().toString().equals("BELUM TERDETEKSI")){
                    //1
                    Toast.makeText(getApplicationContext(), "SILAHKAN AKTIFKAN GPS",
                            Toast.LENGTH_LONG).show();

                    //Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
                }  else {

                    //hitungHasil();
                    //save();
//                    delete();
//                    KasAdapter2();
                    save();

                }
                    //hitungHasil();
                    //save();

                    //KasAdapter2();



            }
        });


        cek1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cariid();
                //getCurrentDate();
                //jamotomatis();
                tanggal1.setText(getCurrentDate());
                absendatang1.setText(jamotomatis());
                //getLastLocation();


            }
        });

        cek2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cariid();
                //getCurrentDate();
                //jamotomatis();

                getLastLocation();


            }
        });

        tanggal1.setText(getCurrentDate());
        absendatang1.setText(jamotomatis());
        jam2.setText(jamotomatis());
        //jamotomatis();
        getLastLocation();

        lokasidatang1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cariid();
                //getCurrentDate();
                //jamotomatis();

                getLastLocation();


            }
        });

        list();
    }


    private void list(){

        //swipe_refresh.setRefreshing(true);
        aruskas.clear();
        listtest1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "listabsendatang.php" )
                .addBodyParameter("namasales", namasales1.getText().toString())
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
                                map.put("tanggal",         responses.optString("tanggal"));
                                map.put("absendatang",       responses.optString("absendatang"));
                                map.put("kecamatan",       responses.optString("kecamatan"));



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

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.listabsen,
                new String[] {"tanggal","absendatang","kecamatan"},
                new int[] {R.id.tanggallistabsen, R.id.jamlistabsen, R.id.kecamatanlistabsen});

        listtest1.setAdapter(simpleAdapter);

        listtest1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //no    = ((TextView) view.findViewById(R.id.no)).getText().toString();



                //tanggal        = ((TextView) view.findViewById(R.id.tanggal)).getText().toString();
                //ListMenu();
            }
        });

        //swipe_refresh.setRefreshing(false);
    }


//    @Override
//    public void onBackPressed() {
//        String a = username1.getText().toString();
////        String b = idoutlogin1.getText().toString();
////        String c = namaoutlogin1.getText().toString();
//        Intent intent = new Intent(context, Awal2.class);
//        //Intent i = new Intent(getApplicationContext(), Menuutamanew.class);
//        intent.putExtra("username",""+a+"");
////        intent.putExtra("idoutlet",""+b+"");
////        intent.putExtra("namaoutlet",""+c+"");
//        //startActivity(i);
//        startActivity(intent);
//        finish();
//    }

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

    public String jamotomatis(){
        Calendar c1 = Calendar.getInstance();
        //SimpleDateFormat sdf1 = new SimpleDateFormat("d/M/yyyy h:m:s a");
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
        String strdate1 = sdf1.format(c1.getTime());
        return strdate1;


    }

    private void save() {
        //pDialog.setMessage("Login Process...");
        //showDialog();
        AndroidNetworking.post(Config.host + "inputabsendatang.php")
                .addBodyParameter("namasales",  namasales1.getText().toString())
                .addBodyParameter("absendatang", absendatang1.getText().toString())
                .addBodyParameter("lokasiabsendatang", lokasiabsendatang1.getText().toString())
                .addBodyParameter("longlat", longitude1.getText().toString())
                .addBodyParameter("latitude", latitude1.getText().toString())
                .addBodyParameter("tanggal", tanggal1.getText().toString())
                .addBodyParameter("kecamatan", kecamatan1.getText().toString())



                .setPriority(Priority.MEDIUM)
                .build()

                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response

                        Log.d("response", response.toString());

                        if (response.optString("response").toString().equals("success")) {
                            hideDialog();
                            //gotoCourseActivity();
                            Toast.makeText(getApplicationContext(), "Berhasil Absen",
                                    Toast.LENGTH_LONG).show();
                            validasi1.setText("OKE");
                            list();
//                            ok1.setVisibility(View.VISIBLE);
//                            tabsendatang1.setTextColor(getResources().getColor(R.color.hijau));
//                            tabsendatang1.setText("TELAH ABSEN");

                        } else {
                            hideDialog();
                            Toast.makeText(getApplicationContext(), "failed",
                                    Toast.LENGTH_LONG).show();
//                            ga1.setVisibility(View.VISIBLE);
//                            tabsendatang1.setTextColor(getResources().getColor(R.color.merah));
//                            tabsendatang1.setText("GAGAL ABSEN");
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    private void getLastLocation(){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){


            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {

                            if (location != null){

                                try {
                                    Geocoder geocoder = new Geocoder(Absen.this, Locale.getDefault());
                                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    latitude1.setText(""+addresses.get(0).getLatitude());
                                    longitude1.setText(""+addresses.get(0).getLongitude());
                                    lokasiabsendatang1.setText(""+addresses.get(0).getAddressLine(0));
                                    kecamatan1.setText(""+addresses.get(0).getLocality());
//                                    country.setText("Country: "+addresses.get(0).getCountryName());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                            }

                        }
                    });


        }else {

            askPermission();


        }


    }

    private void askPermission() {

        ActivityCompat.requestPermissions(Absen.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @org.jetbrains.annotations.NotNull String[] permissions, @NonNull @org.jetbrains.annotations.NotNull int[] grantResults) {

        if (requestCode == REQUEST_CODE){

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){


                getLastLocation();

            }else {


                Toast.makeText(Absen.this,"Please provide the required permission",Toast.LENGTH_SHORT).show();

            }



        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private void cariid() {

        AndroidNetworking.post(Config.host + "cariuser.php")
                .addBodyParameter("password", password1.getText().toString())
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
                        hallo1.setText((response.optString("nama")));
                        namasales1.setText((response.optString("nama")));
                        //hargabarang1.setText((response.optString("harga")));
                        //actualhero1.setText((response.optString("actual")));
                        //achhero1.setText((response.optString("ach")));
                        //gaphero1.setText((response.optString("gap")));
                        //bulan1.setText((response.optString("bulan")));
                        String aoao = hallo1.getText().toString();
                        hallotampung1.setText("Hallo : "+aoao);
                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }


}