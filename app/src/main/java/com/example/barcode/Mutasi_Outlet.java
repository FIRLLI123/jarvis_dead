package com.example.barcode;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.example.barcode.Data.Data_BayarEX;
import com.example.barcode.helper.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class Mutasi_Outlet extends AppCompatActivity {
    TextView namasales, namaoutlet, idoutlet, tanggaldari, tanggalsampai, tanggal2, jam;
    TextView sumtotal;
    ListView listtest;
    LinearLayout carisum;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    public static String LINK, tanggallist, itemlist, qtylist, hargalist, totallist;
    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mutasi_outlet);

        namasales = (TextView) findViewById(R.id.namasales); //username
        namaoutlet = (TextView) findViewById(R.id.namaoutlet); //namaasli
        idoutlet = (TextView) findViewById(R.id.idoutlet); //namaasli
        tanggaldari = (TextView) findViewById(R.id.tanggaldari);
        tanggalsampai = (TextView) findViewById(R.id.tanggalsampai);//namaasli
        sumtotal = (TextView) findViewById(R.id.sumtotal);//namaasli
        tanggal2 = (TextView) findViewById(R.id.tanggal2);//namaasli
        jam = (TextView) findViewById(R.id.jam);//namaasli

        dateFormatter = new SimpleDateFormat("yyyy/MM/dd", Locale.US);

        carisum = (LinearLayout) findViewById(R.id.carisum);//namaasli

        listtest = (ListView) findViewById(R.id.listtest);

        carisum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list();
                sumtotal();
            }
        });

        tanggaldari.setOnClickListener(new View.OnClickListener() {
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

        Intent dasboard = getIntent();
        String kiriman1 = dasboard.getStringExtra("namasales");
        namasales.setText(kiriman1);
        String kiriman2 = dasboard.getStringExtra("namaoutlet");
        namaoutlet.setText(kiriman2);
        String kiriman3 = dasboard.getStringExtra("idoutlet");
        idoutlet.setText(kiriman3);
        String kiriman4 = dasboard.getStringExtra("tanggal");
        tanggaldari.setText(kiriman4);
        String kiriman5 = dasboard.getStringExtra("tanggal");
        tanggalsampai.setText(kiriman5);

        tanggal2.setText(getCurrentDate());
        jam.setText(getCurrentTime());

        list();
        sumtotal();

    }

    public String getCurrentDate() {
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", new Locale("id", "ID")); // Format tanggal dalam bahasa Indonesia
        return dateFormat.format(c.getTime());
    }

    public String getCurrentTime() {
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss 'WIB'", new Locale("id", "ID")); // Format waktu dalam bahasa Indonesia
        return timeFormat.format(c.getTime());
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
        listtest.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "listmutasioutlet.php" )
                .addBodyParameter("tanggaldari", tanggaldari.getText().toString())
                .addBodyParameter("tanggalsampai", tanggalsampai.getText().toString())
                .addBodyParameter("idoutlet", idoutlet.getText().toString())
                .addBodyParameter("namasales", namasales.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response



                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);





                        try {
                            JSONArray jsonArray = response.optJSONArray("result");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Data_BayarEX item = new Data_BayarEX();
                                JSONObject responses    = jsonArray.getJSONObject(i);
                                HashMap<String, String> map = new HashMap<String, String>();
                                //map.put("no",         responses.optString("no"));
                                map.put("tanggal",       responses.optString("tanggal"));
                                map.put("item",         responses.optString("item"));
                                map.put("qty",         responses.optString("qty"));
                                map.put("harga",         responses.optString("harga"));
                                map.put("total",         responses.optString("total"));



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

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.listmutasioutlet,
                new String[] {"tanggal","item","qty","harga","total"},
                new int[] {R.id.tanggallist, R.id.itemlist, R.id.qtylist, R.id.hargalist, R.id.totallist});

        listtest.setAdapter(simpleAdapter);

        listtest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {




            }
        });

        //swipe_refresh.setRefreshing(false);
    }


    private void sumtotal() {

        AndroidNetworking.post(Config.host + "sumtotalmutasi.php")
                .addBodyParameter("tanggaldari", tanggaldari.getText().toString())
                .addBodyParameter("tanggalsampai", tanggalsampai.getText().toString())
                .addBodyParameter("idoutlet", idoutlet.getText().toString())
                .addBodyParameter("namasales", namasales.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response

                        String totalSumRp = response.optString("total_sum_rp");

                        if (totalSumRp.equals("null") || totalSumRp.isEmpty()) {
                            totalSumRp = "Rp 0";
                        }

                        sumtotal.setText(totalSumRp);
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        sumtotal.setText("Error");
                    }
                });
    }


}