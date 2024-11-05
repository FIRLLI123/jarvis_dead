package com.example.barcode;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.barcode.Data.Data_BayarEX;
import com.example.barcode.helper.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Awal3 extends AppCompatActivity {
    LinearLayout contekan1, performance1,faq1,info1, penjualan1, absen1, infostok, summary, dokumentasi, logout, tugas;
    TextView username1, namakaryawan, posisi, idkar, join, jumlah_pending;

    TextView jumlah, jumlah_perdana, jumlah_voucher;

    TextView namasales, tanggal_awal, tanggal_akhir, tanggal_sebelumnya;



    CircleImageView img;
    private ProgressDialog pDialog;
    private Context context;
    Handler mHandler;

    ArrayList<HashMap<String, String>> list_data;

    private int waktu_loading=1000;
    LinearLayout splash1, swipe;

    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mysharedpref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_STATUS = "status";



    public static String LINK, idlist, tanggallist, jamlist, kecamatanlist, absenlist, keteranganlist, statuslist, pendinglist;
    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();
    ListView listtest1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.awal3);

        jumlah_pending = (TextView) findViewById(R.id.jumlah_pending);
        jumlah_perdana = (TextView) findViewById(R.id.jumlah_perdana);
        jumlah_voucher = (TextView) findViewById(R.id.jumlah_voucher);

        tanggal_awal = (TextView) findViewById(R.id.tanggal_awal);
        tanggal_akhir = (TextView) findViewById(R.id.tanggal_akhir);
        tanggal_sebelumnya = (TextView) findViewById(R.id.tanggal_sebelumnya);

        tanggal_awal.setText(getStartOfMonth());
        tanggal_akhir.setText(getCurrentDate());
        tanggal_sebelumnya.setText(getYesterdayDate());


        NotificationScheduler.scheduleNotification(this);

        this.mHandler = new Handler();
        m_Runnable.run();

        splash1 = (LinearLayout) findViewById(R.id.splash);
        //swipe = (LinearLayout) findViewById(R.id.swipe);

//        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
//
//        splash1.startAnimation(animation);
//
//        //setContentView(R.layout.splash);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//
//
//
//                //setelah loading maka akan langsung berpindah ke home activity
////                Intent home=new Intent(Splashmanual.this, Login.class);
////                startActivity(home);
////                finish();
//
//            }
//        },waktu_loading);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        LINK = Config.host + "history.php";
        jamlist = "";
        tanggallist = "";
        kecamatanlist = "";
        absenlist = "";
        keteranganlist = "";
        statuslist = "";
        pendinglist = "";
        idlist = "";

        listtest1 = (ListView) findViewById(R.id.listtest);

        list_data = new ArrayList<HashMap<String, String>>();

        img=(CircleImageView)findViewById(R.id.imgbarang);

        absen1 = (LinearLayout) findViewById(R.id.absen99);
        tugas = (LinearLayout) findViewById(R.id.tugas);
        performance1 = (LinearLayout) findViewById(R.id.performance);
        faq1 = (LinearLayout) findViewById(R.id.faq);
        info1 = (LinearLayout) findViewById(R.id.info);
        penjualan1 = (LinearLayout) findViewById(R.id.penjualan);
        infostok = (LinearLayout) findViewById(R.id.infostok);
        summary = (LinearLayout) findViewById(R.id.summary);
        dokumentasi = (LinearLayout) findViewById(R.id.dokumentasi);
        logout = (LinearLayout) findViewById(R.id.logout);
        tugas = (LinearLayout) findViewById(R.id.tugas);


        username1 = (TextView) findViewById(R.id.username);
        namakaryawan = (TextView) findViewById(R.id.namakaryawan);
        posisi = (TextView) findViewById(R.id.posisi);
        join = (TextView) findViewById(R.id.join);
        idkar = (TextView) findViewById(R.id.idkar);







        Intent kolomlogin = getIntent();
        String kiriman = kolomlogin.getStringExtra("username");
        username1.setText(kiriman);


        context = Awal3.this;
        pDialog = new ProgressDialog(context);

        String username = username1.getText().toString().trim();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_STATUS, true);
        editor.putString(KEY_USERNAME, username);
        editor.apply();

        absen1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (namakaryawan.getText().toString().equals("-")) {
                    Toast.makeText(getApplicationContext(), "KONEKSI MU LEMAH, SILAHKAN TUNGGU ATAU LOGIN ULANG", Toast.LENGTH_LONG).show();
                } else if (posisi.getText().toString().equals("-")) {
                    Toast.makeText(getApplicationContext(), "KONEKSI MU LEMAH, SILAHKAN TUNGGU ATAU LOGIN ULANG", Toast.LENGTH_LONG).show();
                } else {
                    int pendingCount = Integer.parseInt(jumlah_pending.getText().toString());
                    if (pendingCount > 0) {
                        Toast.makeText(getApplicationContext(), "Tidak bisa absen karena masih ada tugas mu yang pending", Toast.LENGTH_LONG).show();
                    } else {
                        String a = namakaryawan.getText().toString();
                        String b = posisi.getText().toString();
                        Intent i = new Intent(getApplicationContext(), AbsendanIzin.class);
                        i.putExtra("nama", a);
                        i.putExtra("posisi", b);

                        startActivity(i);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                }
            }
        });




//        contekan1.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View arg0) {
//
//
//                if ( posisi.getText().toString().equals("GRAPARI CIBINONG")){
//                    Toast.makeText(getApplicationContext(), "Akses Terkunci ",
//                            Toast.LENGTH_LONG).show();
//
//                    //Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
//                }else {
//                    Intent i = new Intent(getApplicationContext(), Contekan.class);
//                    startActivity(i);
//                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                }
//
//            }
//
//
//
//
//
//        });

        performance1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                if ( posisi.getText().toString().equals("GRAPARI CIBINONG")){
                    Toast.makeText(getApplicationContext(), "Akses Terkunci ",
                            Toast.LENGTH_LONG).show();

                    //Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
                }else {

                    String a = namakaryawan.getText().toString();
                    String b = posisi.getText().toString();
                    String c = idkar.getText().toString();
                    String d = join.getText().toString();
                    Intent i = new Intent(getApplicationContext(), Performance.class);
                    //Intent i = new Intent(getApplicationContext(), Menuutamanew.class);
                    i.putExtra("nama",""+a+"");
                    i.putExtra("posisi",""+b+"");
                    i.putExtra("join",""+c+"");
                    i.putExtra("idkar",""+d+"");

                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }

        });



        penjualan1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                if ( namakaryawan.getText().toString().equals("namakaryawan")){
                    Toast.makeText(getApplicationContext(), "KONEKSI MU LEMAH, SILAHKAN LOGIN ULANG",
                            Toast.LENGTH_LONG).show();

                    //Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
                }else if ( posisi.getText().toString().equals("posisi")){
                    Toast.makeText(getApplicationContext(), "KONEKSI MU LEMAH, SILAHKAN LOGIN ULANG",
                            Toast.LENGTH_LONG).show();

                    //Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
                }else if ( posisi.getText().toString().equals("GRAPARI CIBINONG")){
                    Toast.makeText(getApplicationContext(), "Akses Terkunci",
                            Toast.LENGTH_LONG).show();

                    //Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
                }else {
                    String a = namakaryawan.getText().toString();
                    String b = posisi.getText().toString();
                    //String b = posisi.getText().toString();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    //Intent i = new Intent(getApplicationContext(), Menuutamanew.class);
                    i.putExtra("nama",""+a+"");
                    i.putExtra("posisi",""+b+"");
                    //i.putExtra("posisi",""+b+"");

                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


                }

            }

        });


        faq1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                Intent i = new Intent(getApplicationContext(), FAQ.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }



        });


        info1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                String a = namakaryawan.getText().toString();
                String b = posisi.getText().toString();
                String c = join.getText().toString();
                String d = idkar.getText().toString();
                Intent i = new Intent(getApplicationContext(), Info_Perusahaan.class);
                //Intent i = new Intent(getApplicationContext(), Menuutamanew.class);
                i.putExtra("nama",""+a+"");
                i.putExtra("posisi",""+b+"");
                i.putExtra("join",""+c+"");
                i.putExtra("idkar",""+d+"");

                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }

        });




        infostok.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {



                String a = namakaryawan.getText().toString();
                String b = posisi.getText().toString();
                String c = join.getText().toString();
                String d = idkar.getText().toString();
                Intent i = new Intent(getApplicationContext(), Info_Stok.class);
                //Intent i = new Intent(getApplicationContext(), Menuutamanew.class);
                i.putExtra("nama",""+a+"");
                i.putExtra("posisi",""+b+"");
                i.putExtra("join",""+c+"");
                i.putExtra("idkar",""+d+"");

                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }



        });

        summary.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {



                String a = namakaryawan.getText().toString();
                String b = posisi.getText().toString();
                String c = join.getText().toString();
                String d = idkar.getText().toString();
                Intent i = new Intent(getApplicationContext(), Summary_Penjualan.class);
                //Intent i = new Intent(getApplicationContext(), Menuutamanew.class);
                i.putExtra("nama",""+a+"");
                i.putExtra("posisi",""+b+"");
                i.putExtra("join",""+c+"");
                i.putExtra("idkar",""+d+"");

                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }



        });



        info1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                String a = namakaryawan.getText().toString();
                String b = posisi.getText().toString();
                String c = join.getText().toString();
                String d = idkar.getText().toString();
                Intent i = new Intent(getApplicationContext(), Info_Perusahaan.class);
                //Intent i = new Intent(getApplicationContext(), Menuutamanew.class);
                i.putExtra("nama",""+a+"");
                i.putExtra("posisi",""+b+"");
                i.putExtra("join",""+c+"");
                i.putExtra("idkar",""+d+"");

                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }

        });

        dokumentasi.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {



                String a = namakaryawan.getText().toString();
                String b = posisi.getText().toString();
                String c = join.getText().toString();
                String d = idkar.getText().toString();
                Intent i = new Intent(getApplicationContext(), dokumentasi_menu.class);
                //Intent i = new Intent(getApplicationContext(), Menuutamanew.class);
                i.putExtra("nama",""+a+"");
                i.putExtra("posisi",""+b+"");
                i.putExtra("join",""+c+"");
                i.putExtra("idkar",""+d+"");

                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }



        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Menampilkan dialog konfirmasi
                AlertDialog.Builder builder = new AlertDialog.Builder(Awal3.this);
                builder.setMessage("Apakah Anda ingin logout?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                resetSharedPreferences();
                                // Kembali ke activity Login_V3
                                Intent intent = new Intent(Awal3.this, LoginV2.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss(); // Menutup dialog
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });



        tugas.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                if ( posisi.getText().toString().equals("SALES FORCE")){

                    String a = namakaryawan.getText().toString();
                    Intent i = new Intent(getApplicationContext(), Menu_Tugas.class);
                    //Intent i = new Intent(getApplicationContext(), Menuutamanew.class);
                    i.putExtra("namasales",""+a+"");

                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    //Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
                }if ( posisi.getText().toString().equals("MANAGER")){

                    String a = namakaryawan.getText().toString();
                    Intent i = new Intent(getApplicationContext(), Menu_Tugas_manager.class);
                    //Intent i = new Intent(getApplicationContext(), Menuutamanew.class);
                    //i.putExtra("namasales",""+a+"");

                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    //Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
                }else {

                    Toast.makeText(getApplicationContext(), "Akses Terkunci ",
                            Toast.LENGTH_LONG).show();
                }
            }

        });
        showLoadingDialog();

t_pending();
t_pending_voucher();


        KasAdapter2();
        gambar_gerak2();
        //this.moveTaskToBack(true);
        //KasAdapter3();




    }

    private void showLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Tunggu sebentar...")
                .setCancelable(false);

        final AlertDialog dialog = builder.create();
        dialog.show();

        // Dismiss the dialog after 3 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 3000); // 3000 milliseconds = 3 seconds
    }


    public String getCurrentDay() {
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", new Locale("id", "ID")); // EEEE untuk nama hari lengkap
        return dayFormat.format(c.getTime());
    }

    public String getCurrentMonth() {
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", new Locale("id", "ID")); // MMMM untuk nama bulan lengkap
        return monthFormat.format(c.getTime());
    }

    public String getCurrentYear() {
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        return yearFormat.format(c.getTime());
    }


    public String getStartOfMonth() {
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1); // Mengatur hari menjadi hari pertama bulan ini
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", new Locale("id", "ID")); // Format tanggal dalam bahasa Indonesia
        return dateFormat.format(c.getTime());
    }

    public String getCurrentDate() {
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", new Locale("id", "ID")); // Format tanggal dalam bahasa Indonesia
        return dateFormat.format(c.getTime());
    }


    public String getYesterdayDate() {
        final Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, -1); // Mengurangi satu hari dari tanggal saat ini
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", new Locale("id", "ID")); // Format tanggal dalam bahasa Indonesia
        return dateFormat.format(c.getTime());
    }

    public void gambar_gerak2() {
        ImageView gerak2 = findViewById(R.id.gerak2);

        // Pengecekan apakah aktivitas masih aktif sebelum memuat gambar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (!isDestroyed()) {
                GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(gerak2);
                Glide.with(this)
                        .load(R.drawable.draft3)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(imageViewTarget);
            }
        }
    }



    private void resetSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Hapus semua data yang tersimpan
        editor.apply();
    }

    @Override
    public void onBackPressed() {

        this.moveTaskToBack(true);
        //Toast.makeText(this,"Silahkan Klik Logout untuk keluar aplikasi", Toast.LENGTH_LONG).show();
        //finish();
    }

    private final Runnable m_Runnable = new Runnable() {
        public void run() {
            //Toast.makeText(AbsendanIzin.this, "", Toast.LENGTH_SHORT).show();


            prosesdasboard();

            jumlah_pending = (TextView) findViewById(R.id.jumlah_pending);

            String jumlahPendingStr = jumlah_pending.getText().toString();
            int jumlahPending = Integer.parseInt(jumlahPendingStr);

            if (jumlahPending > 0) {
                gambar_gerak2();
            } else {
                // Tidak melakukan apa-apa jika jumlah_pending <= 0
            }

            Awal3.this.mHandler.postDelayed(m_Runnable, 2000);
        }

    };





    public void prosesdasboard(){

        new CountDownTimer(2000, 1000) {

            public void onTick(long millisUntilFinished) {
                pDialog.setMessage("TUNGGU SEBENTAR, SEDANG VERIFIKASI DATA :"+ millisUntilFinished / 1000);
                //showDialog();

                //absengalengkap();
                KasAdapter3();
                t_pending();
                t_pending_voucher();

                hitung_jumlah_pending();

                pDialog.setCanceledOnTouchOutside(false);
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

    private void KasAdapter3() {

        //swipe_refresh.setRefreshing(true);
        list_data.clear();
//        listinputperdana1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post(Config.host + "getfoto.php")
                .addBodyParameter("nama", namakaryawan.getText().toString())
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
                            JSONArray jsonArray = response.optJSONArray("barang");
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject responses = jsonArray.getJSONObject(i);
                                HashMap<String, String> map = new HashMap<String, String>();
                                //map.put("no",         responses.optString("no"));
                                map.put("foto", responses.optString("foto"));


                                //map.put("tanggal",      responses.optString("tanggal"));

                                list_data.add(map);
                            }

                            Glide.with(getApplicationContext())
                                    .load("http://rekamitrayasa.com/reka/foto/" + list_data.get(0).get("foto"))
                                    .crossFade()
                                    .placeholder(R.drawable.noimage3)
                                    .into(img);
                            //txtnama.setText(list_data.get(0).get("nama"));
                            //txtharga.setText(list_data.get(0).get("harga_barang"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }


    private void KasAdapter2() {

        AndroidNetworking.post(Config.host + "users.php")
                .addBodyParameter("username", username1.getText().toString())
                //.addBodyParameter("bulan1", bulan1.getText().toString())
                //.addBodyParameter("bulan2", bulan2.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response


                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
                        username1.setText((response.optString("username")));
                        namakaryawan.setText((response.optString("nama")));
                        posisi.setText((response.optString("posisi")));
                        idkar.setText((response.optString("id_karyawan")));
                        join.setText((response.optString("join")));
                        //nama221.setText((response.optString("nama")));

                        list();

                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }


    private void list() {
        // Bersihkan data sebelum mengisi kembali
        aruskas.clear();
        listtest1.setAdapter(null);

        AndroidNetworking.post(Config.host + "listabsen_fix.php")
                .addBodyParameter("namasales", namakaryawan.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.optJSONArray("result");

                            // Tampilkan notifikasi jika tidak ada data absen untuk tanggal hari ini

                            String nama = namakaryawan.getText().toString();
                            if (!hasTodayAbsen(jsonArray) && isAfter9AM()) {
                                showNotification("Hallo " + nama,
                                        "Kamu belum absen hari ini, " +
                                                "batas toleransi di 08:00, " +
                                                "Segera lakukan absen ya.");
                            }


                            // Proses data absen jika ada
                            if (jsonArray != null) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject responses = jsonArray.getJSONObject(i);
                                    Data_BayarEX item = new Data_BayarEX();
                                    HashMap<String, String> map = new HashMap<String, String>();
                                    map.put("id", responses.optString("id"));
                                    map.put("tanggal", responses.optString("tanggal"));
                                    map.put("jam", responses.optString("jam"));
                                    map.put("kecamatan", responses.optString("kecamatan"));
                                    map.put("absen", responses.optString("absen"));
                                    map.put("keterangan", responses.optString("keterangan"));
                                    map.put("status", responses.optString("status"));
                                    aruskas.add(map);
                                }
                            }

                            Adapter();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    // Method untuk memeriksa apakah ada data absen untuk tanggal hari ini
                    private boolean hasTodayAbsen(JSONArray jsonArray) throws JSONException {
                        // Mendapatkan tanggal hari ini
                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH) + 1; // Month is zero-based, so add 1
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        final String todayDate = year + "/" + String.format("%02d", month) + "/" + String.format("%02d", day);

                        // Memeriksa setiap entri absen untuk tanggal hari ini
                        if (jsonArray != null) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject responses = jsonArray.getJSONObject(i);
                                String tanggalAbsen = responses.optString("tanggal");
                                // Jika ada entri absen untuk tanggal hari ini, kembalikan true
                                if (tanggalAbsen.equals(todayDate)) {
                                    return true;
                                }
                            }
                        }
                        // Jika tidak ada entri absen untuk tanggal hari ini, kembalikan false
                        return false;
                    }

                    // Method untuk memeriksa apakah sudah setelah jam 16:30
                    private boolean isAfter9AM() {
                        Calendar calendar = Calendar.getInstance();
                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);
                        // Cek apakah jam saat ini setelah atau sama dengan jam 09:00
                        return hour >= 8 || (hour == 8 && minute >= 0);
                    }

                    @Override
                    public void onError(ANError error) {
                        // Handle error
                    }
                });
    }






    private void Adapter(){

        Awal3.CustomAdapter customAdapter = new Awal3.CustomAdapter(this, aruskas, R.layout.list_absen_fix,
                new String[]{"id","tanggal", "jam", "kecamatan", "absen", "keterangan", "status"},
                new int[]{R.id.idlistabsen, R.id.tanggallistabsen, R.id.jamlistabsen, R.id.kecamatanlistabsen, R.id.absenlistabsen, R.id.keteranganlistabsen, R.id.statuslistabsen});

        listtest1.setAdapter(customAdapter);

        listtest1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //no    = ((TextView) view.findViewById(R.id.no)).getText().toString();
                idlist = ((TextView) view.findViewById(R.id.idlistabsen)).getText().toString();
                statuslist = ((TextView) view.findViewById(R.id.statuslistabsen)).getText().toString();

                //idabsen.setText(idlist);
                //tes1.setText(tanggallist);



//                String a = idabsen.getText().toString();
//                //String b = namasaleslist1.getText().toString();
//                //String c = namasalesinputperdana1.getText().toString();
//                Intent i = new Intent(getApplicationContext(), Detail_Absen.class);
//                i.putExtra("id",""+a+"");
//                startActivity(i);

            }
        });


        // Rest of your code.


    }

    private class CustomAdapter extends SimpleAdapter {

        public CustomAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);

            // Get the values from the data
            String absenListAbsen = aruskas.get(position).get("absen");
            String jamListAbsen = aruskas.get(position).get("jam");

            // Check conditions and set text color accordingly
            TextView absenListAbsenTextView = view.findViewById(R.id.absenlistabsen);
            TextView jamListAbsenTextView = view.findViewById(R.id.jamlistabsen);
            if (absenListAbsen.equals("DATANG")) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                try {
                    Date jamAbsen = sdf.parse(jamListAbsen);
                    Date thresholdTime = sdf.parse("08:00:00");

                    if (jamAbsen.after(thresholdTime)) {
                        jamListAbsenTextView.setTextColor(Color.RED);
                    } else {
                        jamListAbsenTextView.setTextColor(Color.BLACK);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                // Set default text color for other values of absenListAbsen
                jamListAbsenTextView.setTextColor(Color.BLACK);
            }

            return view;
        }
    }


    private void showNotification(String title, String message) {
        Intent intent = new Intent(this, Awal3.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = getString(R.string.notification_channel_id);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.logorekamitra)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent);

        // Since Android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, notificationBuilder.build());
    }



    private void t_pending() {

        AndroidNetworking.post(Config.host + "count_tugas_pending.php")
                .addBodyParameter("namasales", namakaryawan.getText().toString())
                .addBodyParameter("tanggaldari", tanggal_awal.getText().toString())
                .addBodyParameter("tanggalsebelumnya", tanggal_sebelumnya.getText().toString())
                .addBodyParameter("tanggalsampai", tanggal_akhir.getText().toString())
//                .addBodyParameter("namasales", namasalesinputperdana1.getText().toString())
//                .addBodyParameter("tanggal", tanggalinputperdana1.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response



                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);

                        jumlah_perdana.setText((response.optString("tugas_hari_ini")));


                    }

                    @Override
                    public void onError(ANError error) {

                    }




                });

    }




    private void t_pending_voucher() {

        AndroidNetworking.post(Config.host + "count_tugas_pending_voucher.php")
                .addBodyParameter("namasales", namakaryawan.getText().toString())
                .addBodyParameter("tanggaldari", tanggal_awal.getText().toString())
                .addBodyParameter("tanggalsebelumnya", tanggal_sebelumnya.getText().toString())
                .addBodyParameter("tanggalsampai", tanggal_akhir.getText().toString())
//                .addBodyParameter("namasales", namasalesinputperdana1.getText().toString())
//                .addBodyParameter("tanggal", tanggalinputperdana1.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response



                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);

                        jumlah_voucher.setText((response.optString("tugas_hari_ini")));


                    }

                    @Override
                    public void onError(ANError error) {

                    }




                });

    }


    private void hitung_jumlah_pending() {
        int item1 = Integer.parseInt(jumlah_perdana.getText().toString());
        int item2 = Integer.parseInt(jumlah_voucher.getText().toString());
        int hasilitem1 = item1 + item2;
        jumlah_pending.setText(String.valueOf(hasilitem1));
    }


}