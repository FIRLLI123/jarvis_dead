package com.example.barcode;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;

import com.androidnetworking.error.ANError;

import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.example.barcode.Data.Data_BayarEX;
import com.example.barcode.helper.Config;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Laporan_indihome_2 extends AppCompatActivity {

    TextView id, nama_pelanggan, tanggal_pengajuan, jam, alamat, namaitem, harga, no_pasang, hp;
    ImageView foto1, foto2;
    Button update_no;

    private ProgressDialog pDialog;
    private Context context;


    Handler mHandler;
    ArrayList<HashMap<String, String>> list_data;
    ArrayList<HashMap<String, String>> list_data2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laporan_indihome_2);



        context = Laporan_indihome_2.this;
        pDialog = new ProgressDialog(context);



        this.mHandler = new Handler();
        m_Runnable.run();
        list_data = new ArrayList<HashMap<String, String>>();
        list_data2 = new ArrayList<HashMap<String, String>>();



        id = (TextView) findViewById(R.id.id_indihome);
        nama_pelanggan = (TextView) findViewById(R.id.nama_pelanggan);
        tanggal_pengajuan = (TextView) findViewById(R.id.tanggal_pengajuan);
        jam = (TextView) findViewById(R.id.jam);
        alamat = (TextView) findViewById(R.id.alamat);
        namaitem = (TextView) findViewById(R.id.namaitem);
        harga = (TextView) findViewById(R.id.harga);
        hp = (TextView) findViewById(R.id.nohp);

        foto1 = (ImageView) findViewById(R.id.foto1);
        foto2 = (ImageView) findViewById(R.id.foto2);

        no_pasang = (TextView) findViewById(R.id.no_pasang);
        update_no = (Button) findViewById(R.id.update_no);




        Intent i = getIntent();
        String kiriman = i.getStringExtra("id");
        id.setText(kiriman);



        hp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mendapatkan teks dari TextView hp
                String hpValue = hp.getText().toString();

                // Mendapatkan ClipboardManager dari sistem
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // Membuat objek ClipData untuk menyimpan data yang akan disalin
                ClipData clip = ClipData.newPlainText("Nomor HP", hpValue);
                // Menyalin data ke clipboard
                clipboard.setPrimaryClip(clip);

                // Menampilkan pesan bahwa nomor telah disalin
                Toast.makeText(getApplicationContext(), "Nomor telah disalin", Toast.LENGTH_SHORT).show();
            }
        });


    }


    private final Runnable m_Runnable = new Runnable() {
        public void run() {
            //Toast.makeText(AbsendanIzin.this, "", Toast.LENGTH_SHORT).show();

            prosesdasboard();

            Laporan_indihome_2.this.mHandler.postDelayed(m_Runnable, 2000);
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
                //showDialog();
                pDialog.setMessage("Sedang load data :"+ millisUntilFinished / 1000);
                //showDialog();

                KasAdapter3();
                KasAdapter2();
                pDialog.setCanceledOnTouchOutside(false);
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                hideDialog();


            }
        }.start();

    }



    private void KasAdapter3() {

        //swipe_refresh.setRefreshing(true);
        list_data.clear();

        AndroidNetworking.post(Config.host + "getfoto_indihome.php")
                .addBodyParameter("id", id.getText().toString())
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
                                map.put("foto1", responses.optString("foto1"));
                                map.put("foto2", responses.optString("foto2"));




                                //map.put("tanggal",      responses.optString("tanggal"));

                                list_data.add(map);
                            }

                            Glide.with(getApplicationContext())
                                    .load("https://rekamitrayasa.com/reka/images/" + list_data.get(0).get("foto1"))
                                    .crossFade()
                                    .placeholder(R.drawable.noimage3)
                                    .into(foto1);
                            //txtnama.setText(list_data.get(0).get("nama"));
                            //txtharga.setText(list_data.get(0).get("harga_barang"));
                            //txtstock.setText(list_data.get(0).get("stock"));

                            Glide.with(getApplicationContext())
                                    .load("https://rekamitrayasa.com/reka/images/" + list_data.get(0).get("foto2"))
                                    .crossFade()
                                    .placeholder(R.drawable.noimage3)
                                    .into(foto2);
                            //txtnama.setText(list_data.get(0).get("nama"));
                            //txtharga.setText(list_data.get(0).get("harga_barang"));
                            //txtstock.setText(list_data.get(0).get("stock"));



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

        AndroidNetworking.post(Config.host + "lap_indihome_2.php")
                .addBodyParameter("id", id.getText().toString())
                //.addBodyParameter("bulan1", bulan1.getText().toString())
                //.addBodyParameter("bulan2", bulan2.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response


                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
                        nama_pelanggan.setText((response.optString("nama_pelanggan")));
                        alamat.setText((response.optString("alamat")));
                        namaitem.setText((response.optString("namaitem")));
                        harga.setText((response.optString("harga1")));
                        tanggal_pengajuan.setText((response.optString("tanggal_pengajuan")));
                        jam.setText((response.optString("jam")));
                        no_pasang.setText((response.optString("no_pasang")));
                        hp.setText((response.optString("hp")));


                        //nama221.setText((response.optString("nama")));

                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });



    }

}