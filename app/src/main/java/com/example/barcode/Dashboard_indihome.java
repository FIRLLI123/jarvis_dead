package com.example.barcode;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.barcode.helper.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class Dashboard_indihome extends AppCompatActivity {
    TextView kode;
    TextView namasales, level, team_leader, hp, status;
    LinearLayout input, laporan, datadiri, approve, logout;
    TextView tanggal_pengajuan, jam;

    TextView proses_indihome, approve_indihome, proses_orbit, approve_orbit;

    private ProgressDialog pDialog;
    private Context context;


    TextView jumlah_pending;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_indihome);


        this.mHandler = new Handler();
        m_Runnable.run();

        context = Dashboard_indihome.this;
        pDialog = new ProgressDialog(context);

        jumlah_pending = (TextView) findViewById(R.id.jumlah_pending);
        kode = (TextView) findViewById(R.id.kode);
        namasales = (TextView) findViewById(R.id.namasales);
        level = (TextView) findViewById(R.id.level);
        team_leader = (TextView) findViewById(R.id.team_leader);
        hp = (TextView) findViewById(R.id.hp);
        status = (TextView) findViewById(R.id.status);

        input = (LinearLayout) findViewById(R.id.input);
        laporan = (LinearLayout) findViewById(R.id.laporan);
        datadiri = (LinearLayout) findViewById(R.id.datadiri);
        approve = (LinearLayout) findViewById(R.id.approve);
        logout = (LinearLayout) findViewById(R.id.logout);

        proses_indihome = (TextView) findViewById(R.id.proses_indihome);
        approve_indihome = (TextView) findViewById(R.id.approve_indihome);
        proses_orbit = (TextView) findViewById(R.id.proses_orbit);
        approve_orbit= (TextView) findViewById(R.id.approve_orbit);

        tanggal_pengajuan = (TextView) findViewById(R.id.tanggal_pengajuan);
        jam = (TextView) findViewById(R.id.jam);

        Intent kolomlogin = getIntent();
        String kiriman = kolomlogin.getStringExtra("kode");
        kode.setText(kiriman);

        KasAdapter();
        KasAdapter2();




        input.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // TODO Auto-generated method stub

                String a = kode.getText().toString();
                String b = namasales.getText().toString();
                Intent i = new Intent(getApplicationContext(), Input_indihome.class);
                i.putExtra("kode",""+a+"");
                i.putExtra("namasales",""+b+"");
                startActivity(i);



            }

        });


        laporan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // TODO Auto-generated method stub

                String a = kode.getText().toString();
                String b = namasales.getText().toString();
                String c = level.getText().toString();
                Intent i = new Intent(getApplicationContext(), Laporan_indihome.class);
                i.putExtra("kode",""+a+"");
                i.putExtra("namasales",""+b+"");
                i.putExtra("level",""+c+"");
                startActivity(i);



            }

        });


        approve.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // TODO Auto-generated method stub

                if (level.getText().toString().equals("Developer")) {

                    String a = kode.getText().toString();
                    String b = namasales.getText().toString();
                    String c = level.getText().toString();
                    Intent i = new Intent(getApplicationContext(), Approve_Indihome.class);
                    i.putExtra("kode", "" + a + "");
                    i.putExtra("namasales", "" + b + "");
                    i.putExtra("level", "" + c + "");
                    startActivity(i);


                }else if (level.getText().toString().equals("Manager")) {


                    String a = kode.getText().toString();
                    String b = namasales.getText().toString();
                    String c = level.getText().toString();
                    Intent i = new Intent(getApplicationContext(), Approve_Indihome.class);
                    i.putExtra("kode", "" + a + "");
                    i.putExtra("namasales", "" + b + "");
                    i.putExtra("level", "" + c + "");
                    startActivity(i);


                } else {


                    Toast.makeText(getApplicationContext(), "Akses Dibatasi", Toast.LENGTH_LONG).show();
                }


            }

        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Menampilkan dialog konfirmasi
                AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard_indihome.this);
                builder.setMessage("Apakah Anda ingin logout?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                resetSharedPreferences();
                                // Kembali ke activity Login_V3
                                Intent intent = new Intent(Dashboard_indihome.this, Login_indihome.class);
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






        this.mHandler = new Handler();
        m_Runnable.run();


    }


    private void resetSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Hapus semua data yang tersimpan
        editor.apply();
    }


    public void gambar_gerak2() {
        ImageView gerak2 = findViewById(R.id.gerak2);

        // Pengecekan apakah aktivitas masih aktif sebelum memuat gambar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (!isDestroyed()) {
                GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(gerak2);
                Glide.with(this)
                        .load(R.drawable.draft2)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(imageViewTarget);
            }
        }
    }


    private final Runnable m_Runnable = new Runnable() {
        public void run() {
            //Toast.makeText(AbsendanIzin.this, "", Toast.LENGTH_SHORT).show();

            prosesdasboard();
            count_pending();


            jumlah_pending = (TextView) findViewById(R.id.jumlah_pending);

            String jumlahPendingStr = jumlah_pending.getText().toString();
            int jumlahPending = Integer.parseInt(jumlahPendingStr);

            if (jumlahPending > 0) {
                gambar_gerak2();
            } else {
                // Tidak melakukan apa-apa jika jumlah_pending <= 0
            }

            Dashboard_indihome.this.mHandler.postDelayed(m_Runnable, 2000);
        }

    };

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public void prosesdasboard(){

        new CountDownTimer(1000, 1000) {

            public void onTick(long millisUntilFinished) {
                pDialog.setMessage("TUNGGU SEBENTAR, SEDANG VERIFIKASI DATA :"+ millisUntilFinished / 1000);
                //showDialog();

                //absengalengkap();
                KasAdapter();
                KasAdapter2();
                indihome();
                orbit();
                pDialog.setCanceledOnTouchOutside(false);
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                hideDialog();


            }
        }.start();

    }



    private void KasAdapter() {

        AndroidNetworking.post(Config.host + "dashboard_indihome.php")
                .addBodyParameter("kode", kode.getText().toString())
                //.addBodyParameter("bulan1", bulan1.getText().toString())
                //.addBodyParameter("bulan2", bulan2.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response


                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
                        //username.setText((response.optString("username")));
                        namasales.setText((response.optString("namasales")));
                        level.setText((response.optString("level")));
                        team_leader.setText((response.optString("team_leader")));
                        hp.setText((response.optString("hp")));
                        status.setText((response.optString("status")));
                        //tanggal_pengajuan.setText((response.optString("tanggal_pengajuan")));
                        //nama221.setText((response.optString("nama")));

                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }



    private void KasAdapter2() {

        AndroidNetworking.post(Config.host + "sum_dashboard_indihome.php")
                .addBodyParameter("kode", kode.getText().toString())

                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response


                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);

                        tanggal_pengajuan.setText((response.optString("tanggal_pengajuan")));
                        jam.setText((response.optString("jam")));
                        //nama221.setText((response.optString("nama")));

                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }


    private void indihome() {

        AndroidNetworking.post(Config.host + "sum_dashboard_indihome_2.php")
                .addBodyParameter("kode", kode.getText().toString())

                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response


                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);

                        proses_indihome.setText((response.optString("proses_indihome")));
                        approve_indihome.setText((response.optString("approve_indihome")));
//                        proses_orbit.setText((response.optString("proses_orbit")));
//                        approve_orbit.setText((response.optString("approve_orbit")));
                        //nama221.setText((response.optString("nama")));

                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }



    private void orbit() {

        AndroidNetworking.post(Config.host + "sum_dashboard_indihome_3.php")
                .addBodyParameter("kode", kode.getText().toString())

                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response


                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);

//                        proses_indihome.setText((response.optString("proses_indihome")));
//                        approve_indihome.setText((response.optString("approve_indihome")));
                        proses_orbit.setText((response.optString("proses_orbit")));
                        approve_orbit.setText((response.optString("approve_orbit")));
                        //nama221.setText((response.optString("nama")));

                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }


    private void count_pending() {
//        pDialog.setMessage("Process...");
//        showDialog();
//        pDialog.setCanceledOnTouchOutside(false);
        AndroidNetworking.post(Config.host + "count_proses_indihome.php")
//                .addBodyParameter("tanggaldari", tanggal_dari.getText().toString())
//                .addBodyParameter("tanggalsampai", tanggal_sampai.getText().toString())
                //.addBodyParameter("proses", perusahaan.getText().toString())

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
                        jumlah_pending.setText((response.optString("id")));

                        //actualhero1.setText((response.optString("actual")));
                        //achhero1.setText((response.optString("ach")));
                        //gaphero1.setText((response.optString("gap")));
                        //bulan1.setText((response.optString("bulan")));

                        hideDialog();

                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }




}