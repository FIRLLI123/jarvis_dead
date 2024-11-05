package com.example.barcode;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
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
import java.util.Map;

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

public class Tugas extends AppCompatActivity {
    TextView idoutlet, namaoutlet;
    TextView namasales, hari, bulan, tahun;
    LinearLayout kiri, kanan;
    TextView teks_kiri, teks_kanan;
    TextView tanggal_awal, tanggal_akhir, tanggal_sebelumnya;
    TextView tugas_hari_ini, tugas_pending;
    Button kunjungi;

    TextView namasalesbackup2, jam2, tanggal2;

    LinearLayout blank_gambar;


    ListView listpjp;
    public static String idlist, idoutletlist, namaoutletlist, namasaleslist, namaitemtlist, qtylist, tanggallist, keteranganlist;
    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();


    private ProgressDialog pDialog;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tugas);

        context = Tugas.this;
        pDialog = new ProgressDialog(context);
        listpjp = (ListView) findViewById(R.id.listpjp);

        blank_gambar =(LinearLayout) findViewById(R.id.blank_gambar);

        kunjungi =(Button) findViewById(R.id.kunjungi);

        idoutlet = (TextView) findViewById(R.id.idoutlet);
        namaoutlet = (TextView) findViewById(R.id.namaoutlet);

        namasales = (TextView) findViewById(R.id.namasales);
        hari = (TextView) findViewById(R.id.hari);
        bulan = (TextView) findViewById(R.id.bulan);
        tahun = (TextView) findViewById(R.id.tahun);

        tanggal_awal = (TextView) findViewById(R.id.tanggal_awal);
        tanggal_akhir = (TextView) findViewById(R.id.tanggal_akhir);
        tanggal_sebelumnya = (TextView) findViewById(R.id.tanggal_sebelumnya);

        kiri = (LinearLayout) findViewById(R.id.kiri);
        kanan = (LinearLayout) findViewById(R.id.kanan);

        teks_kiri = (TextView) findViewById(R.id.teks_kiri);
        teks_kanan = (TextView) findViewById(R.id.teks_kanan);

        tugas_hari_ini = (TextView) findViewById(R.id.tugas_hari_ini);
        tugas_pending = (TextView) findViewById(R.id.tugas_pending);



        namasalesbackup2 = (TextView) findViewById(R.id.namasalesbackup2);
        jam2 = (TextView) findViewById(R.id.jam2);
        tanggal2 = (TextView) findViewById(R.id.tanggal2);


        // Dapatkan tanggal saat ini
        Calendar calendar = Calendar.getInstance();

        // Mengatur hari, bulan, dan tahun saat ini
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currentMonth = calendar.get(Calendar.MONTH) + 1; // Bulan dimulai dari 0, jadi tambahkan 1
        int currentYear = calendar.get(Calendar.YEAR);


        Intent i = getIntent();
        String kiriman3 = i.getStringExtra("namasales");
        namasales.setText(kiriman3);

        // Set the current date values
        hari.setText(getCurrentDay());
        bulan.setText(getCurrentMonth());
        tahun.setText(getCurrentYear());

        tanggal_awal.setText(getStartOfMonth());
        tanggal_akhir.setText(getCurrentDate());
        tanggal_sebelumnya.setText(getYesterdayDate());


        kiri.setBackground(getResources().getDrawable(R.drawable.custom11));
        teks_kiri.setTextColor(getResources().getColor(R.color.birutua));

        kanan.setBackground(getResources().getDrawable(R.drawable.custom10));
        teks_kanan.setTextColor(getResources().getColor(R.color.abutua));

        t_hari_ini();
        t_pending();
        hari_ini();

        kiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                kiri.setBackground(getResources().getDrawable(R.drawable.custom11));
                teks_kiri.setTextColor(getResources().getColor(R.color.birutua));

                kanan.setBackground(getResources().getDrawable(R.drawable.custom10));
                teks_kanan.setTextColor(getResources().getColor(R.color.abutua));

                prosesdasboard_hari_ini();
                //hari_ini();

            }
        });


        kanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                kanan.setBackground(getResources().getDrawable(R.drawable.custom11));
                teks_kanan.setTextColor(getResources().getColor(R.color.birutua));

                kiri.setBackground(getResources().getDrawable(R.drawable.custom10));
                teks_kiri.setTextColor(getResources().getColor(R.color.abutua));

                prosesdasboard_pending();
                //hari_pending();

            }
        });



        kunjungi.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {



//                String a = namaoutlet1.getText().toString();
//                String b = idoutlet1.getText().toString();
//                String c = namasales1.getText().toString();
//                String d = namasalesforward.getText().toString();
//                Intent i = new Intent(getApplicationContext(), MainActivity2.class);
//                i.putExtra("namaoutlet",""+a+"");
//                i.putExtra("idoutlet",""+b+"");
//                i.putExtra("namasales",""+c+"");
//                i.putExtra("nama",""+d+"");
//                startActivity(i);


                if (namaoutlet.getText().toString().equals("-")) {
                    Toast.makeText(getApplicationContext(), "SILAHKAN PILIH OUTLET", Toast.LENGTH_LONG).show();
                }else {
                    String a = namasales.getText().toString();
                    String b = idoutlet.getText().toString();
                    String c = namaoutlet.getText().toString();
                    String d = namasales.getText().toString();
                    String e = tanggal2.getText().toString();
                    String f = jam2.getText().toString();
                    Intent i = new Intent(getApplicationContext(), TampilanOutlet.class);
                    i.putExtra("namasales",""+a+"");
                    i.putExtra("idoutlet",""+b+"");
                    i.putExtra("namaoutlet",""+c+"");

                    i.putExtra("namasaleskunjungan",""+d+"");
                    i.putExtra("tanggalkunjungan",""+e+"");
                    i.putExtra("jamkunjungan",""+f+"");

                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }

            }

        });

    }



    public void prosesdasboard_hari_ini(){

        new CountDownTimer(1000, 1000) {

            public void onTick(long millisUntilFinished) {
                pDialog.setMessage("Loading..."+ millisUntilFinished / 1000);
                showDialog();
                pDialog.setCanceledOnTouchOutside(false);

                t_hari_ini();
                t_pending();
                hari_ini();

                //count_keseluruhan();
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                hideDialog();


            }
        }.start();

    }


    public void prosesdasboard_pending(){

        new CountDownTimer(1000, 1000) {

            public void onTick(long millisUntilFinished) {
                pDialog.setMessage("Loading..."+ millisUntilFinished / 1000);
                showDialog();
                pDialog.setCanceledOnTouchOutside(false);

                t_hari_ini();
                t_pending();
                hari_pending();

                //count_keseluruhan();
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                hideDialog();


            }
        }.start();

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






    private void hari_ini() {
        //swipe_refresh.setRefreshing(true);
        aruskas.clear();
        listpjp.setAdapter(null);

        AndroidNetworking.post(Config.host + "tugas_hari_ini.php")
                .addBodyParameter("namasales", namasales.getText().toString())
                .addBodyParameter("tanggaldari", tanggal_akhir.getText().toString())
                .addBodyParameter("tanggalsampai", tanggal_akhir.getText().toString())
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
                                JSONObject responses = jsonArray.getJSONObject(i);
                                HashMap<String, String> map = new HashMap<>();

                                map.put("idoutlet", responses.optString("idoutlet"));
                                map.put("namaoutlet", responses.optString("namaoutlet"));
                                map.put("hasil", responses.optString("hasil"));
                                aruskas.add(map);
                            }

                            Adapter();

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

    private void Adapter() {

        if (aruskas.isEmpty()) {
            // Jika aruskas kosong, tampilkan blank_gambar
            // Misalnya:
            blank_gambar.setVisibility(View.VISIBLE);
            listpjp.setVisibility(View.GONE);
        } else {
            blank_gambar.setVisibility(View.GONE);
            listpjp.setVisibility(View.VISIBLE);

            CustomAdapter customAdapter = new CustomAdapter(this, aruskas, R.layout.listtugas,
                    new String[]{"idoutlet", "namaoutlet", "hasil"},
                    new int[]{R.id.idoutlettugas, R.id.namaoutlettugas, R.id.hasiltugas});

            listpjp.setAdapter(customAdapter);

            listpjp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    namaoutletlist = ((TextView) view.findViewById(R.id.namaoutlettugas)).getText().toString();
                    idoutletlist = ((TextView) view.findViewById(R.id.idoutlettugas)).getText().toString();
                    //namasaleslist  = ((TextView) view.findViewById(R.id.namasaleslistdataoutlet)).getText().toString();


                    namaoutlet.setText(namaoutletlist);
                    idoutlet.setText(idoutletlist);


                    kunjunganterakhir();


                    // Handle item click
                }
            });

        } //swipe_refresh.setRefreshing(false);
    }

    private class CustomAdapter extends SimpleAdapter {

        public CustomAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);

            // Get the values from the data
            String hasil = aruskas.get(position).get("hasil");

            // Get the ImageViews
            ImageView statusImg = view.findViewById(R.id.statusimg);
            ImageView statusImg2 = view.findViewById(R.id.statusimg2);

            // Set the visibility based on the value of hasil
            if ("SELESAI".equals(hasil)) {
                statusImg.setVisibility(View.VISIBLE);
                statusImg2.setVisibility(View.GONE);
            } else {
                statusImg.setVisibility(View.GONE);
                statusImg2.setVisibility(View.VISIBLE);
            }

            return view;
        }
    }





    private void hari_pending() {
        aruskas.clear();
        listpjp.setAdapter(null);

        AndroidNetworking.post(Config.host + "tugas_pending.php")
                .addBodyParameter("namasales", namasales.getText().toString())
                .addBodyParameter("tanggaldari", tanggal_awal.getText().toString())
                .addBodyParameter("tanggalsebelumnya", tanggal_sebelumnya.getText().toString())
                .addBodyParameter("tanggalsampai", tanggal_akhir.getText().toString())
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
                                JSONObject responses = jsonArray.getJSONObject(i);
                                HashMap<String, String> map = new HashMap<>();

                                map.put("idoutlet", responses.optString("idoutlet"));
                                map.put("namaoutlet", responses.optString("namaoutlet"));
                                map.put("hasil", responses.optString("hasil"));
                                aruskas.add(map);
                            }

                            AdapterHariPending();

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

    private void AdapterHariPending() {

        if (aruskas.isEmpty()) {
            blank_gambar.setVisibility(View.VISIBLE);
            listpjp.setVisibility(View.GONE);
        } else {
            blank_gambar.setVisibility(View.GONE);
            listpjp.setVisibility(View.VISIBLE);

            CustomAdapterHariPending customAdapter = new CustomAdapterHariPending(this, aruskas, R.layout.listtugas,
                    new String[]{"idoutlet", "namaoutlet", "hasil"},
                    new int[]{R.id.idoutlettugas, R.id.namaoutlettugas, R.id.hasiltugas});

            listpjp.setAdapter(customAdapter);

            listpjp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    namaoutletlist = ((TextView) view.findViewById(R.id.namaoutlettugas)).getText().toString();
                    idoutletlist = ((TextView) view.findViewById(R.id.idoutlettugas)).getText().toString();
                    //namasaleslist  = ((TextView) view.findViewById(R.id.namasaleslistdataoutlet)).getText().toString();

                    namaoutlet.setText(namaoutletlist);
                    idoutlet.setText(idoutletlist);

                    kunjunganterakhir();

                    // Handle item click
                }
            });

        }
    }

    private class CustomAdapterHariPending extends SimpleAdapter {

        public CustomAdapterHariPending(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);

            // Get the values from the data
            String hasil = aruskas.get(position).get("hasil");

            // Get the ImageViews
            ImageView statusImg = view.findViewById(R.id.statusimg);
            ImageView statusImg2 = view.findViewById(R.id.statusimg2);

            // Set the visibility based on the value of hasil
            if ("SELESAI".equals(hasil)) {
                statusImg.setVisibility(View.VISIBLE);
                statusImg2.setVisibility(View.GONE);
                gambar_gerak2();
            } else {
                statusImg.setVisibility(View.GONE);
                statusImg2.setVisibility(View.VISIBLE);
            }

            return view;
        }
    }









    private void t_hari_ini() {

        AndroidNetworking.post(Config.host + "count_tugas_hari_ini.php")
                .addBodyParameter("namasales", namasales.getText().toString())
                .addBodyParameter("tanggaldari", tanggal_akhir.getText().toString())
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

                        tugas_hari_ini.setText((response.optString("tugas_hari_ini")));


                    }

                    @Override
                    public void onError(ANError error) {

                    }




                });

    }



    private void t_pending() {

        AndroidNetworking.post(Config.host + "count_tugas_pending.php")
                .addBodyParameter("namasales", namasales.getText().toString())
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

                        tugas_pending.setText((response.optString("tugas_hari_ini")));


                    }

                    @Override
                    public void onError(ANError error) {

                    }




                });

    }



    private void t_all() {

        AndroidNetworking.post(Config.host + "count_tugas.php")
                .addBodyParameter("namasales", namasales.getText().toString())
                .addBodyParameter("tanggaldari", tanggal_awal.getText().toString())
                .addBodyParameter("tanggalsebelumnya", tanggal_sebelumnya.getText().toString())
//                .addBodyParameter("namasales", namasalesinputperdana1.getText().toString())
//                .addBodyParameter("tanggal", tanggalinputperdana1.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response



                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);

                        tugas_pending.setText((response.optString("tugas_hari_ini")));


                    }

                    @Override
                    public void onError(ANError error) {

                    }




                });

    }



    private void kunjunganterakhir() {

        AndroidNetworking.post(Config.host + "kunjunganterakhir.php")
                .addBodyParameter("idoutlet", idoutlet.getText().toString())
                //.addBodyParameter("bulan1", bulan1.getText().toString())
                //.addBodyParameter("bulan2", bulan2.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response



                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
                        //idoutmenu1.setText((response.optString("idoutlet")));
                        namasalesbackup2.setText((response.optString("namasalesbackup")));
                        tanggal2.setText((response.optString("tanggal")));
                        jam2.setText((response.optString("jam")));



                    }

                    @Override
                    public void onError(ANError error) {

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



    public void gambar_gerak2() {
        ImageView gerak2 = findViewById(R.id.gerak2);

        // Pengecekan apakah aktivitas masih aktif sebelum memuat gambar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (!isDestroyed()) {
                GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(gerak2);
                Glide.with(this)
                        .load(R.drawable.happy)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(imageViewTarget);
            }
        }
    }

}