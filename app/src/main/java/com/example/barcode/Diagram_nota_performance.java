package com.example.barcode;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.bumptech.glide.Glide;
import com.example.barcode.Data.Data_BayarEX;
import com.example.barcode.helper.Config;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

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

import de.hdodenhof.circleimageview.CircleImageView;

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

public class Diagram_nota_performance extends AppCompatActivity {
    TextView tes1, tanggalsampai, detaildata, t_perdana, t_voucher, t_sales, t_ds, detaildata_voucher, kategori, id_kar;
    ListView listtest1, listtest2;
    Button cekdetail1;
    EditText namasaleslist1;
    LinearLayout carisum1;
    LinearLayout atas;
    CardView tengah;
    View perdana_v, voucher_v;

    private ProgressDialog pDialog;
    private Context context;

    LinearLayout perdana, voucher, sales, ds;

    TextView pilihan;

    TextView tanggal_min_1, tanggalsampai_min_1;
    TextView bulan_min_1, bulan_1;
    TextView namasales;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    CircleImageView img;

    ArrayList<HashMap<String, String>> list_data;
    Handler mHandler;

    LineChart lineChart, lineChart2;

    TextView bulan;

    public static String LINK, namasaleslist, tanggallist, totallist, hargalistperdana, totallistperdana;
    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> aruskas2 = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagram_nota_performance);
        NumberFormat formatter = new DecimalFormat("#,###,###,###");

        context = Diagram_nota_performance.this;
        pDialog = new ProgressDialog(context);

        this.mHandler = new Handler();
        m_Runnable.run();

        lineChart = findViewById(R.id.line_chart);

        img = (CircleImageView) findViewById(R.id.imgbarang);

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

        bulan = (TextView) findViewById(R.id.bulan);

        id_kar = (TextView) findViewById(R.id.id_kar);

        kategori = (TextView) findViewById(R.id.kategori);

        perdana = (LinearLayout) findViewById(R.id.perdana);
        voucher = (LinearLayout) findViewById(R.id.voucher);


        perdana_v = (View) findViewById(R.id.perdana_v);
        voucher_v = (View) findViewById(R.id.voucher_v);

        t_perdana = (TextView) findViewById(R.id.t_perdana);
        t_voucher = (TextView) findViewById(R.id.t_voucher);


        tes1 = (TextView) findViewById(R.id.tanggal);
        tanggalsampai = (TextView) findViewById(R.id.tanggalsampai);


        namasales = (TextView) findViewById(R.id.namasales);

        listtest1 = (ListView) findViewById(R.id.listtest);
        listtest2 = (ListView) findViewById(R.id.listtest2);


        carisum1 = (LinearLayout) findViewById(R.id.carisum);

        atas = (LinearLayout) findViewById(R.id.atas);

        tengah = (CardView) findViewById(R.id.tengah);



        Intent i = getIntent();
        String kiriman = i.getStringExtra("namasales");
        namasales.setText(kiriman);





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
        kategori.setText("PERDANA");


        perdana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //KasAdapter2();

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

                // Load animasi dari XML


                list();
                listtest1.setVisibility(View.VISIBLE);
                listtest2.setVisibility(View.GONE);
                kategori.setText("PERDANA");

                //prosesdasboard1();


            }
        });

        Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        atas.startAnimation(slideUp);
//        tengah.startAnimation(slideUp);

//        Animation slideUpAtas = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
//        slideUpAtas.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//                // Method kosong, tidak digunakan dalam kasus ini
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                // Animasi atas selesai, mulai animasi tengah
//                Animation slideUpTengah = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
//                tengah.startAnimation(slideUpTengah);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//                // Method kosong, tidak digunakan dalam kasus ini
//            }
//        });
//
//        atas.startAnimation(slideUpAtas);






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
                kategori.setText("VOUCHER");


                //prosesdasboard1();

            }
        });







// Inisialisasi Line Chart
        lineChart = findViewById(R.id.line_chart);
        lineChart2 = findViewById(R.id.line_chart2);







        tes1.setText(getCurrentDate());
        tanggalsampai.setText(getCurrentDate());
        KasAdapter2();
        list();
        //list2();

        //prosesdasboard();

    }



    private final Runnable m_Runnable = new Runnable() {
        public void run() {
            //Toast.makeText(AbsendanIzin.this, "", Toast.LENGTH_SHORT).show();


            prosesdasboard();

            Diagram_nota_performance.this.mHandler.postDelayed(m_Runnable, 2000);
        }

    };


    public void prosesdasboard(){

        new CountDownTimer(2000, 1000) {

            public void onTick(long millisUntilFinished) {
                pDialog.setMessage("TUNGGU SEBENTAR, SEDANG VERIFIKASI DATA :"+ millisUntilFinished / 1000);
                //showDialog();

                //absengalengkap();
                KasAdapter3();

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


                SimpleDateFormat monthFormatter = new SimpleDateFormat("MMMM", new Locale("id", "ID"));

                bulan.setText(monthFormatter.format(newDate.getTime()));


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


                SimpleDateFormat monthFormatter = new SimpleDateFormat("MMMM", new Locale("id", "ID"));

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


    private void list() {
        aruskas.clear();
        listtest1.setAdapter(null);

        AndroidNetworking.post(Config.host + "sum_pertanggal_perdana.php")
                .addBodyParameter("tanggaldari", tes1.getText().toString())
                .addBodyParameter("tanggalsampai", tanggalsampai.getText().toString())
                .addBodyParameter("namasales", namasales.getText().toString())
                .addBodyParameter("kategori", kategori.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);

                        try {
                            JSONArray jsonArray = response.optJSONArray("result");
                            List<Entry> achEntries = new ArrayList<>();
                            List<String> dates = new ArrayList<>(); // Daftar untuk menyimpan tanggal

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject responses = jsonArray.getJSONObject(i);
                                HashMap<String, String> map = new HashMap<String, String>();

                                String tanggal = responses.optString("tanggal");
                                String qty = responses.optString("qty");
                                String total = responses.optString("total_input");
                                String total_target = responses.optString("total_target");
                                String ach = responses.optString("ach");

                                map.put("tanggal", tanggal);
                                map.put("qty", qty);
                                map.put("total_input", total);
                                map.put("total_target", total_target);
                                map.put("ach", ach);

                                // Tambahkan tanggal ke daftar
                                dates.add(tanggal);

                                // Tambahkan data ke entries (index ke sumbu X dan ach ke sumbu Y)
                                achEntries.add(new Entry(i, Float.parseFloat(ach.replace("%", ""))));

                                aruskas.add(map);
                            }

                            // Buat LineDataSet dan tambahkan ke objek LineData
                            LineDataSet achDataSet = new LineDataSet(achEntries, "ACH (%)");
                            achDataSet.setLineWidth(1f); // Mengatur ketebalan garis
                            achDataSet.setColor(Color.RED); // Mengatur warna garis
                            achDataSet.setValueTextColor(Color.BLACK); // Mengatur warna teks nilai
                            achDataSet.setValueTextSize(10f); // Mengatur ukuran teks nilai
                            achDataSet.setDrawValues(true); // Menampilkan nilai pada garis
                            achDataSet.setValueTypeface(Typeface.DEFAULT_BOLD); // Mengatur font tebal
                            achDataSet.setDrawFilled(true); // Mengisi area di bawah garis
                            achDataSet.setFillColor(Color.parseColor("#F39191")); // Mengatur warna fill

                            // Set ValueFormatter untuk menambahkan % pada nilai
                            achDataSet.setValueFormatter(new PercentValueFormatter());

                            LineData achLineData = new LineData(achDataSet);

                            // Set data ke Line Chart
                            lineChart.setData(achLineData);
                            lineChart.setBackgroundColor(Color.WHITE); // Mengatur warna latar belakang LineChart
                            lineChart.setDrawGridBackground(false); // Menghapus grid background jika diinginkan
                            lineChart.getDescription().setEnabled(false); // Menghilangkan deskripsi chart

                            // Atur label sumbu X dengan tanggal
                            XAxis xAxis = lineChart.getXAxis();
                            xAxis.setValueFormatter(new DayValueFormatter(dates)); // Gunakan ValueFormatter khusus
                            xAxis.setGranularity(1f);
                            xAxis.setGranularityEnabled(true);
                            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

                            // Menonaktifkan sumbu kanan jika tidak diperlukan
                            lineChart.getAxisRight().setEnabled(false);

                            lineChart.invalidate(); // Refresh chart

                            Adapter();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // Tangani error
                    }
                });
    }



    private void Adapter() {
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.list_pertanggal,
                new String[]{"tanggal", "qty", "total_input", "total_target", "ach"},
                new int[]{R.id.tanggallist, R.id.qtylist, R.id.belanjalist, R.id.total_target_list, R.id.ach_list});

        listtest1.setAdapter(simpleAdapter);
    }

    // ValueFormatter khusus untuk menampilkan hanya hari
    public class DayValueFormatter extends ValueFormatter {
        private final List<String> dates;

        public DayValueFormatter(List<String> dates) {
            this.dates = dates;
        }

        @Override
        public String getFormattedValue(float value) {
            int index = (int) value;
            if (index < 0 || index >= dates.size()) {
                return "";
            }
            String fullDate = dates.get(index);
            String[] dateParts = fullDate.split("/");
            if (dateParts.length < 3) {
                return fullDate; // Jika format tanggal tidak sesuai, tampilkan tanggal penuh
            }
            return dateParts[2]; // Mengambil bagian hari dari format yyyy-mm-dd
        }
    }

    // ValueFormatter khusus untuk menambahkan simbol % ke nilai
    public class PercentValueFormatter extends ValueFormatter {
        @Override
        public String getFormattedValue(float value) {
            return String.format(Locale.getDefault(), "%.0f%%", value);
        }
    }






    private void list2() {
        aruskas.clear();
        listtest2.setAdapter(null);

        AndroidNetworking.post(Config.host + "sum_pertanggal_voucher.php")
                .addBodyParameter("tanggaldari", tes1.getText().toString())
                .addBodyParameter("tanggalsampai", tanggalsampai.getText().toString())
                .addBodyParameter("namasales", namasales.getText().toString())
                .addBodyParameter("kategori", kategori.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);

                        try {
                            JSONArray jsonArray = response.optJSONArray("result");
                            List<Entry> achEntries = new ArrayList<>();
                            List<String> dates = new ArrayList<>(); // Daftar untuk menyimpan tanggal

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject responses = jsonArray.getJSONObject(i);
                                HashMap<String, String> map = new HashMap<String, String>();

                                String tanggal = responses.optString("tanggal");
                                String qty = responses.optString("qty");
                                String total = responses.optString("total_input");
                                String total_target = responses.optString("total_target");
                                String ach = responses.optString("ach");

                                map.put("tanggal", tanggal);
                                map.put("qty", qty);
                                map.put("total_input", total);
                                map.put("total_target", total_target);
                                map.put("ach", ach);

                                // Tambahkan tanggal ke daftar
                                dates.add(tanggal);

                                // Tambahkan data ke entries (index ke sumbu X dan ach ke sumbu Y)
                                achEntries.add(new Entry(i, Float.parseFloat(ach.replace("%", ""))));

                                aruskas.add(map);
                            }

                            // Buat LineDataSet dan tambahkan ke objek LineData
                            LineDataSet achDataSet = new LineDataSet(achEntries, "ACH (%)");
                            achDataSet.setLineWidth(1f); // Mengatur ketebalan garis
                            achDataSet.setColor(Color.RED); // Mengatur warna garis
                            achDataSet.setValueTextColor(Color.BLACK); // Mengatur warna teks nilai
                            achDataSet.setValueTextSize(10f); // Mengatur ukuran teks nilai
                            achDataSet.setDrawValues(true); // Menampilkan nilai pada garis
                            achDataSet.setValueTypeface(Typeface.DEFAULT_BOLD); // Mengatur font tebal
                            achDataSet.setDrawFilled(true); // Mengisi area di bawah garis
                            achDataSet.setFillColor(Color.parseColor("#F39191")); // Mengatur warna fill

                            // Set ValueFormatter untuk menambahkan % pada nilai
                            achDataSet.setValueFormatter(new PercentValueFormatter2());

                            LineData achLineData = new LineData(achDataSet);

                            // Set data ke Line Chart
                            lineChart.setData(achLineData);
                            lineChart.setBackgroundColor(Color.WHITE); // Mengatur warna latar belakang LineChart
                            lineChart.setDrawGridBackground(false); // Menghapus grid background jika diinginkan
                            lineChart.getDescription().setEnabled(false); // Menghilangkan deskripsi chart

                            // Atur label sumbu X dengan tanggal
                            XAxis xAxis = lineChart.getXAxis();
                            xAxis.setValueFormatter(new DayValueFormatter2(dates)); // Gunakan ValueFormatter khusus
                            xAxis.setGranularity(1f);
                            xAxis.setGranularityEnabled(true);
                            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

                            // Menonaktifkan sumbu kanan jika tidak diperlukan
                            lineChart.getAxisRight().setEnabled(false);

                            lineChart.invalidate(); // Refresh chart

                            Adapter2();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // Tangani error
                    }
                });
    }


    private void Adapter2() {
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.list_pertanggal,
                new String[]{"tanggal", "qty", "total_input", "total_target", "ach"},
                new int[]{R.id.tanggallist, R.id.qtylist, R.id.belanjalist, R.id.total_target_list, R.id.ach_list});

        listtest2.setAdapter(simpleAdapter);
    }

    // ValueFormatter khusus untuk menampilkan hanya hari
    public class DayValueFormatter2 extends ValueFormatter {
        private final List<String> dates;

        public DayValueFormatter2(List<String> dates) {
            this.dates = dates;
        }

        @Override
        public String getFormattedValue(float value) {
            int index = (int) value;
            if (index < 0 || index >= dates.size()) {
                return "";
            }
            String fullDate = dates.get(index);
            String[] dateParts = fullDate.split("/");
            if (dateParts.length < 3) {
                return fullDate; // Jika format tanggal tidak sesuai, tampilkan tanggal penuh
            }
            return dateParts[2]; // Mengambil bagian hari dari format yyyy-mm-dd
        }
    }

    // ValueFormatter khusus untuk menambahkan simbol % ke nilai
    public class PercentValueFormatter2 extends ValueFormatter {
        @Override
        public String getFormattedValue(float value) {
            return String.format(Locale.getDefault(), "%.0f%%", value);
        }
    }


    //----------------------------------------------------------
    //----------------------------------------------------------



    private void KasAdapter3() {

        //swipe_refresh.setRefreshing(true);
        aruskas2.clear();
//        listinputperdana1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post(Config.host + "getfoto.php")
                .addBodyParameter("nama", namasales.getText().toString())
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

                                aruskas2.add(map);
                            }

                            Glide.with(getApplicationContext())
                                    .load("http://rekamitrayasa.com/reka/foto/" + aruskas2.get(0).get("foto"))
                                    .crossFade()
                                    .placeholder(R.drawable.noimage3)
                                    .into(img);
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

        AndroidNetworking.post(Config.host + "user_nama.php")
                .addBodyParameter("nama", namasales.getText().toString())
                //.addBodyParameter("bulan1", bulan1.getText().toString())
                //.addBodyParameter("bulan2", bulan2.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response


                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
                        id_kar.setText((response.optString("id_karyawan")));
                        //nama221.setText((response.optString("nama")));

                        list();

                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }


}