package com.example.barcode;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.widget.DatePicker;
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

public class Approve_Indihome extends AppCompatActivity {
    TextView id_indihome, kode;
    TextView namasales, level, team_leader, hp;

    LinearLayout linear_fisik, linear_elektrik;
    TextView t_fisik, t_elektrik;
    View v_fisik, v_elektrik;

    TextView status;

    private ProgressDialog pDialog;
    private Context context;

    ListView listdataoutlet1;

    LinearLayout tanggal_bagan;

    TextView tanggal, tanggalsampai;
    LinearLayout klik_dari, klik_sampai, cari_sum;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    public static String LINK, idlist, namapelangganlist, alamatlist, namaitemlist, hargalist;
    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.approve_indihome);

        context = Approve_Indihome.this;
        pDialog = new ProgressDialog(context);

        dateFormatter = new SimpleDateFormat("yyyy/MM/dd", Locale.US);

        LINK = Config.host + "history.php";
        idlist = "";
        namapelangganlist = "";
        alamatlist = "";
        namaitemlist = "";
        hargalist = "";


        id_indihome = (TextView) findViewById(R.id.id_indihome);
        kode = (TextView) findViewById(R.id.kode);
        namasales = (TextView) findViewById(R.id.namasales);
        level = (TextView) findViewById(R.id.level);

        listdataoutlet1 = (ListView) findViewById(R.id.listdataoutlet);

        linear_fisik = (LinearLayout) findViewById(R.id.linear_fisik);
        linear_elektrik = (LinearLayout) findViewById(R.id.linear_elektrik);

        tanggal_bagan = (LinearLayout) findViewById(R.id.tanggal_bagan);

        tanggal = (TextView) findViewById(R.id.tanggal);
        tanggalsampai = (TextView) findViewById(R.id.tanggal_sampai);

        klik_dari = (LinearLayout) findViewById(R.id.klik_dari);
        klik_sampai = (LinearLayout) findViewById(R.id.klik_sampai);
        cari_sum = (LinearLayout) findViewById(R.id.cari_sum);

        t_fisik = (TextView) findViewById(R.id.t_fisik);
        t_elektrik = (TextView) findViewById(R.id.t_elektrik);

        v_fisik = (View) findViewById(R.id.v_fisik);
        v_elektrik = (View) findViewById(R.id.v_elektrik);



        status = (TextView) findViewById(R.id.status);



        t_fisik.setTypeface(Typeface.DEFAULT_BOLD);
        v_fisik.setVisibility(View.VISIBLE);

        t_elektrik.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        v_elektrik.setVisibility(View.INVISIBLE);

        status.setText("proses");
        prosesdasboard();

        linear_fisik.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {


                t_fisik.setTypeface(Typeface.DEFAULT_BOLD);
                v_fisik.setVisibility(View.VISIBLE);

                t_elektrik.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                v_elektrik.setVisibility(View.INVISIBLE);

                status.setText("proses");
                prosesdasboard();

                tanggal_bagan.setVisibility(View.GONE);


                tanggal.setText("-");
                tanggalsampai.setText("-");

            }

        });


        linear_elektrik.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {


                t_elektrik.setTypeface(Typeface.DEFAULT_BOLD);
                v_elektrik.setVisibility(View.VISIBLE);

                t_fisik.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                v_fisik.setVisibility(View.INVISIBLE);
                status.setText("approve");
                prosesdasboard();

                tanggal_bagan.setVisibility(View.VISIBLE);

                tanggal.setText(getCurrentDate());
                tanggalsampai.setText(getCurrentDate());

            }

        });


//        klik_dari.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showDateDialog();
//            }
//        });


        klik_dari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Membuat intent untuk mengirim email
                Intent emailIntent = new Intent(Intent.ACTION_SEND);

                // Menetapkan tipe data email
                emailIntent.setType("message/rfc822");

                // Menambahkan alamat email tujuan
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"firlliantonizi@gmail.com"});

                // Menambahkan subjek email
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subjek Email Anda");

                // Menambahkan isi pesan email
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Isi pesan email Anda");

                try {
                    // Memulai activity untuk mengirim email
                    startActivity(Intent.createChooser(emailIntent, "Pilih aplikasi email..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(view.getContext(), "Tidak ada aplikasi email yang terinstal.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        klik_sampai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog2();
            }
        });

        cari_sum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list();
            }
        });


        tanggal.setText("-");
        tanggalsampai.setText("-");

        Intent i = getIntent();
        String kiriman = i.getStringExtra("kode");
        kode.setText(kiriman);
        String kiriman2 = i.getStringExtra("namasales");
        namasales.setText(kiriman2);
        String kiriman3 = i.getStringExtra("level");
        level.setText(kiriman3);


        //list();

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
                tanggal.setText(dateFormatter.format(newDate.getTime()));
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


    public void prosesdasboard(){

        new CountDownTimer(1000, 1000) {

            public void onTick(long millisUntilFinished) {
                pDialog.setMessage("Loading.. :"+ millisUntilFinished / 1000);
                showDialog();

                //absengalengkap();
                list();
                pDialog.setCanceledOnTouchOutside(false);
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                hideDialog();



            }
        }.start();

    }

    private void list(){

        //swipe_refresh.setRefreshing(true);
        aruskas.clear();
        listdataoutlet1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "lap_indihome_approve.php" )
                .addBodyParameter("kode", kode.getText().toString())
                .addBodyParameter("status", status.getText().toString())
                .addBodyParameter("tanggaldari", tanggal.getText().toString())
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
                                map.put("id",         responses.optString("id"));
                                map.put("kode",         responses.optString("kode"));
                                map.put("nama_pelanggan",         responses.optString("nama_pelanggan"));
                                map.put("tanggal_pengajuan",         responses.optString("tanggal_pengajuan"));

                                map.put("alamat",       responses.optString("alamat"));
                                map.put("namaitem",       responses.optString("namaitem"));
                                map.put("harga1",       responses.optString("harga1"));
                                map.put("jam",         responses.optString("jam"));
                                map.put("status",         responses.optString("status"));





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

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.list_lap_indihome_1,
                new String[] {"id","kode","nama_pelanggan","tanggal_pengajuan","alamat","namaitem","harga1","jam","status"},
                new int[] {R.id.id_list,R.id.kode_sales_list,R.id.nama_pelanggan_list, R.id.tanggal_pengajuan_list, R.id.alamat_list, R.id.namaitem_list, R.id.harga_1_list, R.id.jam_list, R.id.status_list});

        listdataoutlet1.setAdapter(simpleAdapter);

        listdataoutlet1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idlist    = ((TextView) view.findViewById(R.id.id_list)).getText().toString();

                id_indihome.setText(idlist);
                String a = id_indihome.getText().toString();
                Intent i = new Intent(getApplicationContext(), Approve_Indihome_2.class);
                i.putExtra("id",""+a+"");

                startActivity(i);


            }
        });
        //swipe_refresh.setRefreshing(false);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}