package com.example.barcode;

import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
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

public class Izin_fix extends AppCompatActivity {
    public Spinner sp;

    private ProgressDialog pDialog;
    private Context context;

    ListView listtest1;

    TextView namasales, jam, lokasilengkap, longitude, latitude, tanggal, kecamatan, keterangan, status, absen,posisi;

    TextView tanggalbayangan, jambayangan;
    TextView in,out;

    TextView tanggaldari,tanggalsampai;

    Button btnabsen, caritanggal;
    FusedLocationProviderClient fusedLocationProviderClient;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    public static String LINK, tanggallist, jamlist, kecamatanlist, absenlist, keteranganlist, statuslist, pendinglist;
    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.izin_fix);

        LINK = Config.host + "history.php";
        jamlist = "";
        tanggallist = "";
        kecamatanlist = "";
        absenlist = "";
        keteranganlist = "";
        statuslist = "";
        pendinglist = "";


        dateFormatter = new SimpleDateFormat("yyyy/MM/dd", Locale.US);


        sp = (Spinner)findViewById(R.id.spinner);

        namasales = (TextView) findViewById(R.id.namasales);
        jam = (TextView) findViewById(R.id.jam);
        lokasilengkap = (TextView) findViewById(R.id.lokasilengkap);
        longitude = (TextView) findViewById(R.id.longitude);
        latitude = (TextView) findViewById(R.id.latitude);
        tanggal = (TextView) findViewById(R.id.tanggal);
        kecamatan = (TextView) findViewById(R.id.kecamatan);
        keterangan = (TextView) findViewById(R.id.keterangan);
        status = (TextView) findViewById(R.id.status);
        absen = (TextView) findViewById(R.id.absen);

        posisi = (TextView) findViewById(R.id.posisi);

        jambayangan = (TextView) findViewById(R.id.jambayangan);
        tanggalbayangan = (TextView) findViewById(R.id.tanggalbayangan);

        caritanggal = (Button) findViewById(R.id.caritanggal);

        tanggaldari = (TextView) findViewById(R.id.tanggaldari);
        tanggalsampai = (TextView) findViewById(R.id.tanggalsampai);

        in = (TextView) findViewById(R.id.in);
        out = (TextView) findViewById(R.id.out);

        listtest1 = (ListView) findViewById(R.id.listtest);



        context = Izin_fix.this;
        pDialog = new ProgressDialog(context);

        Intent kolomlogin = getIntent();
        String kiriman1 = kolomlogin.getStringExtra("nama");
        namasales.setText(kiriman1);

        String kiriman2 = kolomlogin.getStringExtra("posisi");
        posisi.setText(kiriman2);

        btnabsen =(Button) findViewById(R.id.btnabsen);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        tanggal.setText(getCurrentDate());
        //jam.setText(jamotomatis());

        tanggalbayangan.setText(getCurrentDateBayangan());
        jambayangan.setText(jamotomatisBayangan());



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


        getLastLocation();



        caritanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listtangal();
            }
        });

        btnabsen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                if ( kecamatan.getText().toString().equals("BELUM TERDETEKSI")){
                    //1
                    Toast.makeText(getApplicationContext(), "SILAHKAN AKTIFKAN GPS",
                            Toast.LENGTH_LONG).show();

                    //Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
                }else if ( in.getText().toString().length() == 8){
                    //1
                    Toast.makeText(getApplicationContext(), "ANDA SUDAH ABSEN",
                            Toast.LENGTH_LONG).show();

                    //Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
                }else if (keterangan.getText().toString().length() == 0) {        //2

                    Toast.makeText(getApplicationContext(), "Silahkan isi terlebih dahulu", Toast.LENGTH_LONG).show();


                }  else {


                    absengak();



                }

            }
        });


        list();
        inouttimer();

        List<String> item = new ArrayList<>();

        //item.add("--SELECT--");
        item.add("SAKIT");
        item.add("LAINNYA");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Izin_fix.this,android.R.layout.simple_spinner_dropdown_item, item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ;
        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //alasan1.setText(sp.getSelectedItem().toString());

                if (sp.getSelectedItem().toString().trim().equals("LAINNYA")) {
                    keterangan.setVisibility(View.VISIBLE);
                    keterangan.setText("");
                    keterangan.setHint("Silahkan isi alasan izin");
                    //startActivity(new Intent(date.this,MainActivity.class));
                } else {
                    keterangan.setVisibility(View.GONE);
                    keterangan.setText(sp.getSelectedItem().toString());
                }
//                        startActivity(new Intent(date.this,august
//                                .class));


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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


    private void list(){

        //swipe_refresh.setRefreshing(true);
        aruskas.clear();
        listtest1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "listabsen_fix.php" )
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
                                map.put("tanggal",         responses.optString("tanggal"));
                                map.put("jam",       responses.optString("jam"));
                                map.put("kecamatan",       responses.optString("kecamatan"));
                                map.put("absen",       responses.optString("absen"));
                                map.put("keterangan",       responses.optString("keterangan"));
                                map.put("status",       responses.optString("status"));



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

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.list_absen_fix,
                new String[] {"tanggal","jam","kecamatan","absen","keterangan","status"},
                new int[] {R.id.tanggallistabsen, R.id.jamlistabsen, R.id.kecamatanlistabsen, R.id.absenlistabsen, R.id.keteranganlistabsen, R.id.statuslistabsen});

        listtest1.setAdapter(simpleAdapter);

        listtest1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //no    = ((TextView) view.findViewById(R.id.no)).getText().toString();
                statuslist = ((TextView) view.findViewById(R.id.statuslistabsen)).getText().toString();


                //tanggal        = ((TextView) view.findViewById(R.id.tanggal)).getText().toString();
                //ListMenu();


            }
        });







        //TextView status = (TextView) view.findViewById(R.id.statuslistabsen);
        //statuslist    = ((TextView) findViewById(R.id.statuslistabsen)).getText().toString();
        ImageView imgMenu = (ImageView)findViewById(R.id.pending);
        ImageView imgMenu2 = (ImageView)findViewById(R.id.approve);
        ImageView imgMenu3 = (ImageView)findViewById(R.id.ditolak);





        //swipe_refresh.setRefreshing(false);
    }


    private void listtangal(){

        //swipe_refresh.setRefreshing(true);
        aruskas.clear();
        listtest1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "listabsen_fix_tanggal.php" )
                .addBodyParameter("namasales", namasales.getText().toString())
                .addBodyParameter("tanggaldari", tanggaldari.getText().toString())
                .addBodyParameter("tanggalsampai", tanggalsampai.getText().toString())
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
                                map.put("jam",       responses.optString("jam"));
                                map.put("kecamatan",       responses.optString("kecamatan"));
                                map.put("absen",       responses.optString("absen"));
                                map.put("keterangan",       responses.optString("keterangan"));
                                map.put("status",       responses.optString("status"));



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

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.list_absen_fix,
                new String[]{"tanggal", "jam", "kecamatan", "absen", "keterangan", "status"},
                new int[]{R.id.tanggallistabsen, R.id.jamlistabsen, R.id.kecamatanlistabsen, R.id.absenlistabsen, R.id.keteranganlistabsen, R.id.statuslistabsen});

        listtest1.setAdapter(simpleAdapter);

        listtest1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //no    = ((TextView) view.findViewById(R.id.no)).getText().toString();
                statuslist = ((TextView) view.findViewById(R.id.statuslistabsen)).getText().toString();


                //tanggal        = ((TextView) view.findViewById(R.id.tanggal)).getText().toString();
                //ListMenu();


            }
        });
    }



    public void absengak() {

        String namasalesalert = namasales.getText().toString();
        String absenalert = absen.getText().toString();


        //String a = validasib1.getText().toString();
        final Dialog dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setTitle("ABSENSI");
        AlertDialog alertDialog;
        alertDialog = new AlertDialog.Builder(this)

                .setIcon(R.drawable.titik)
                .setTitle(R.string.app_name)
                .setMessage("Hallo "+namasalesalert+", anda akan melakukan absen "+absenalert+" ?")
                .setPositiveButton("OKE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        prosesdasboard1();



                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                })
                .show();
        alertDialog.setCanceledOnTouchOutside(false);
    }

    public void prosesdasboard1(){
        save();
        list();
        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
                pDialog.setMessage("Loading..."+ millisUntilFinished / 1000);
                showDialog();
                pDialog.setCanceledOnTouchOutside(false);

                in();
                out();
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                hideDialog();


            }
        }.start();

    }


    public void inouttimer(){

        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
                pDialog.setMessage("Loading..."+ millisUntilFinished / 1000);
                showDialog();
                pDialog.setCanceledOnTouchOutside(false);
                in();
                out();
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                hideDialog();


            }
        }.start();

    }



    public String getCurrentDateBayangan() {
        //SimpleDateFormat contoh1 = new SimpleDateFormat("yyyy/MM/dd");
        //String hariotomatis = contoh1.format(c.getTime());
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat contoh1 = new SimpleDateFormat("EEEE, dd-MMMM-yyyy", Locale.getDefault());
//        int year, month, day;
//        year = c.get(Calendar.YEAR);
//        month = c.get(Calendar.MONTH);
//        day = c.get(Calendar.DATE);
        //SimpleDateFormat contoh1 = new SimpleDateFormat("EEEE, yyyy/MM/dd");

        String hariotomatis = contoh1.format(c.getTime());
        //hari.setText(hariotomatis);
        return hariotomatis;
        //return day +"/" + (month+1) + "/" + year;
        //return (month+1) +"/" + day + "/" + year;
        //return year + "/" + (month + 1) + "/" + day;

    }

    public String jamotomatisBayangan(){
        Calendar c1 = Calendar.getInstance();
        //SimpleDateFormat sdf1 = new SimpleDateFormat("d/M/yyyy h:m:s a");
        //SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm a");
        String strdate1 = sdf1.format(c1.getTime());
        return strdate1;


    }


    public String getCurrentDate() {
        //SimpleDateFormat contoh1 = new SimpleDateFormat("yyyy/MM/dd");
        //String hariotomatis = contoh1.format(c.getTime());
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat contoh1 = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
//        int year, month, day;
//        year = c.get(Calendar.YEAR);
//        month = c.get(Calendar.MONTH);
//        day = c.get(Calendar.DATE);
        //SimpleDateFormat contoh1 = new SimpleDateFormat("EEEE, yyyy/MM/dd");

        String hariotomatis = contoh1.format(c.getTime());
        //hari.setText(hariotomatis);
        return hariotomatis;
        //return day +"/" + (month+1) + "/" + year;
        //return (month+1) +"/" + day + "/" + year;
        //return year + "/" + (month + 1) + "/" + day;

    }

    public String jamotomatis(){
        Calendar c1 = Calendar.getInstance();
        //SimpleDateFormat sdf1 = new SimpleDateFormat("d/M/yyyy h:m:s a");
        //SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm:ss");
        String strdate1 = sdf1.format(c1.getTime());
        return strdate1;


    }

    private void getLastLocation(){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){


            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {

                            if (location != null){

                                try {
                                    Geocoder geocoder = new Geocoder(Izin_fix.this, Locale.getDefault());
                                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    latitude.setText(""+addresses.get(0).getLatitude());
                                    longitude.setText(""+addresses.get(0).getLongitude());
                                    lokasilengkap.setText(""+addresses.get(0).getAddressLine(0));
                                    kecamatan.setText(""+addresses.get(0).getLocality());
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

        ActivityCompat.requestPermissions(Izin_fix.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @org.jetbrains.annotations.NotNull String[] permissions, @NonNull @org.jetbrains.annotations.NotNull int[] grantResults) {

        if (requestCode == REQUEST_CODE){

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){


                getLastLocation();

            }else {


                Toast.makeText(Izin_fix.this,"Please provide the required permission",Toast.LENGTH_SHORT).show();

            }



        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



    private void save() {
        //pDialog.setMessage("Login Process...");
        //showDialog();
        AndroidNetworking.post(Config.host + "inputabsen.php")
                .addBodyParameter("namasales",  namasales.getText().toString())
                .addBodyParameter("jam", jam.getText().toString())
                .addBodyParameter("lokasilengkap", lokasilengkap.getText().toString())
                .addBodyParameter("longitude", longitude.getText().toString())
                .addBodyParameter("latitude", latitude.getText().toString())
                .addBodyParameter("tanggal", tanggal.getText().toString())
                .addBodyParameter("kecamatan", kecamatan.getText().toString())
                .addBodyParameter("keterangan", keterangan.getText().toString())
                .addBodyParameter("status", status.getText().toString())
                .addBodyParameter("absen", absen.getText().toString())
                .addBodyParameter("posisi", posisi.getText().toString())



                .setPriority(Priority.MEDIUM)
                .build()

                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response

                        Log.d("response", response.toString());

                        if (response.optString("response").toString().equals("success")) {
                            //hideDialog();
                            //gotoCourseActivity();
                            Toast.makeText(getApplicationContext(), "Data Anda sedang diteruskan ke Manager Anda, untuk dilakukan verifikasi terlebih dahulu",
                                    Toast.LENGTH_LONG).show();




//                            ok1.setVisibility(View.VISIBLE);
//                            tabsendatang1.setTextColor(getResources().getColor(R.color.hijau));
//                            tabsendatang1.setText("TELAH ABSEN");

                        } else {
                            //hideDialog();
                            Toast.makeText(getApplicationContext(), "HARI INI KAMU SUDAH ABSEN",
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


    private void in() {

        AndroidNetworking.post(Config.host + "cekabsenin.php")
                .addBodyParameter("namasales", namasales.getText().toString())
                .addBodyParameter("tanggal", tanggal.getText().toString())
                //.addBodyParameter("bulan1", bulan1.getText().toString())
                //.addBodyParameter("bulan2", bulan2.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response


                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
                        //username1.setText((response.optString("username")));
                        in.setText((response.optString("jam")));
                        //nama221.setText((response.optString("nama")));
                        //hideDialog();

                        if (in.getText().toString().equals("null")) {
                            //1
                            in.setText("--:--");


                            //Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
                        } else {
//                            btnabsen.setText("ABSEN PULANG");
//                            absen.setText("pulang");

                        }

                    }


                    @Override
                    public void onError(ANError error) {

                    }


                });

    }
    private void out() {

        AndroidNetworking.post(Config.host + "cekabsenout.php")
                .addBodyParameter("namasales", namasales.getText().toString())
                .addBodyParameter("tanggal", tanggal.getText().toString())
                //.addBodyParameter("bulan1", bulan1.getText().toString())
                //.addBodyParameter("bulan2", bulan2.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response



                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
                        //username1.setText((response.optString("username")));
                        out.setText((response.optString("jam")));
                        //nama221.setText((response.optString("nama")));
                        //hideDialog();

                        if ( out.getText().toString().equals("null")){
                            //1
                            out.setText("--:--");

                            //Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
                        }else{
//                                btnabsen.setText("ABSEN PULANG");
//                                absen.setText("pulang");

                        }

                    }




                    @Override
                    public void onError(ANError error) {

                    }



                });





    }


}