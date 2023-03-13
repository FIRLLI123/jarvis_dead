package com.example.barcode;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

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
import java.util.List;
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

public class SumPENJUALAN_ALL extends AppCompatActivity {

    TextView totalperdana, totalunitperdana, totaloutletperdana;
    TextView totalvoucher, totalunitvoucher, totaloutletvoucher;
    TextView totallink, totaloutletlink;

    Button cari, detailitemperdana, detailitemvoucher;

    Button btnperdana,btnvoucher,btnlink;


    TextView tanggaldari,tanggalsampai;

    private ProgressDialog pDialog;
    private Context context;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    private SimpleDateFormat dateFormatterlink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sum_penjualan_all);


        context = SumPENJUALAN_ALL.this;
        pDialog = new ProgressDialog(context);

        totalperdana = (TextView) findViewById(R.id.totalperdana);
        totalunitperdana = (TextView) findViewById(R.id.totalunitperdana);
        totaloutletperdana = (TextView) findViewById(R.id.totaloutletperdana);

        totalvoucher = (TextView) findViewById(R.id.totalvoucher);
        totalunitvoucher = (TextView) findViewById(R.id.totalunitvoucher);
        totaloutletvoucher = (TextView) findViewById(R.id.totaloutletvoucher);

        totallink = (TextView) findViewById(R.id.totallink);
        totaloutletlink = (TextView) findViewById(R.id.totaloutletlink);


//---------------------------------------------------------------------------------


        tanggaldari = (TextView) findViewById(R.id.tanggaldari);
        tanggalsampai = (TextView) findViewById(R.id.tanggalsampai);

        dateFormatter = new SimpleDateFormat("yyyy/MM/dd", Locale.US);

        dateFormatterlink = new SimpleDateFormat("yyyy/MM/dd", Locale.US);

        cari = (Button) findViewById(R.id.cari);
        detailitemperdana = (Button) findViewById(R.id.detailitemperdana);
        detailitemvoucher = (Button) findViewById(R.id.detailitemvoucher);




        btnperdana = (Button) findViewById(R.id.btnperdana);
        btnvoucher = (Button) findViewById(R.id.btnvoucher);
        btnlink = (Button) findViewById(R.id.btnlink);


        cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                prosesdasboard();

            }
        });


        btnperdana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String c = tanggaldari.getText().toString();
                String d = tanggalsampai.getText().toString();
                Intent i = new Intent(getApplicationContext(), SumPENJUALAN_ALL_2.class);
                i.putExtra("tanggaldari",""+c+"");
                i.putExtra("tanggalsampai",""+d+"");
                startActivity(i);




            }
        });





        btnvoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String c = tanggaldari.getText().toString();
                String d = tanggalsampai.getText().toString();
                Intent i = new Intent(getApplicationContext(), SumPENJUALAN_ALL_VCR2.class);
                i.putExtra("tanggaldari",""+c+"");
                i.putExtra("tanggalsampai",""+d+"");
                startActivity(i);


            }
        });


        btnlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String c = tanggaldari.getText().toString();
                String d = tanggalsampai.getText().toString();
                Intent i = new Intent(getApplicationContext(), SumPENJUALAN_ALL_LINK2.class);
                i.putExtra("tanggaldari",""+c+"");
                i.putExtra("tanggalsampai",""+d+"");
                startActivity(i);


            }
        });



        detailitemperdana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String c = tanggaldari.getText().toString();
                String d = tanggalsampai.getText().toString();
                Intent i = new Intent(getApplicationContext(), SumITEM_ALL_PERDANA.class);
                i.putExtra("tanggaldari",""+c+"");
                i.putExtra("tanggalsampai",""+d+"");
                startActivity(i);


            }
        });


        detailitemvoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String c = tanggaldari.getText().toString();
                String d = tanggalsampai.getText().toString();
                Intent i = new Intent(getApplicationContext(), SumITEM_ALL_VOUCHER.class);
                i.putExtra("tanggaldari",""+c+"");
                i.putExtra("tanggalsampai",""+d+"");
                startActivity(i);



            }
        });




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

        prosesdasboard();

    }

    public void prosesdasboard(){

        new CountDownTimer(2000, 1000) {

            public void onTick(long millisUntilFinished) {
                pDialog.setMessage("TUNGGU SEBENTAR, SEDANG VERIFIKASI DATA :"+ millisUntilFinished / 1000);
                showDialog();
                pDialog.setCanceledOnTouchOutside(false);
                perdana();
                voucher();
                //voucher();
                link();
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


    private void perdana() {

        AndroidNetworking.post(Config.host + "totalperdanaall.php")
                //.addBodyParameter("idoutlet", idoutletinputperdana1.getText().toString())
                .addBodyParameter("tanggaldari", tanggaldari.getText().toString())
                .addBodyParameter("tanggalsampai", tanggalsampai.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response


                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);




                        totalperdana.setText(
                                rupiahFormat.format(response.optDouble("total")));
                        totalunitperdana.setText((response.optString("qty")));
                        totaloutletperdana.setText((response.optString("namaoutlet")));

                        if ( totalperdana.getText().toString().equals("NaN")){
                            totalperdana.setText("0");

                        }else{


                        }

                    }

                    @Override
                    public void onError(ANError error) {

                    }




                });

    }



    private void voucher() {

        AndroidNetworking.post(Config.host + "totalvoucherall.php")
                //.addBodyParameter("idoutlet", idoutletinputperdana1.getText().toString())
                .addBodyParameter("tanggaldari", tanggaldari.getText().toString())
                .addBodyParameter("tanggalsampai", tanggalsampai.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response


                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);




                        totalvoucher.setText(
                                rupiahFormat.format(response.optDouble("total")));
                        totalunitvoucher.setText((response.optString("qty")));
                        totaloutletvoucher.setText((response.optString("namaoutlet")));



                        if ( totalvoucher.getText().toString().equals("NaN")){
                            totalvoucher.setText("0");

                        }else{


                        }

                    }

                    @Override
                    public void onError(ANError error) {

                    }




                });

    }


    private void link() {

        AndroidNetworking.post(Config.host + "totallinkall.php")
                //.addBodyParameter("idoutlet", idoutletinputperdana1.getText().toString())
                .addBodyParameter("tanggaldari", tanggaldari.getText().toString())
                .addBodyParameter("tanggalsampai", tanggalsampai.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response



                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);




                        totallink.setText(
                                rupiahFormat.format(response.optDouble("harga")));
                        totaloutletlink.setText((response.optString("namaoutlet")));


                        if ( totallink.getText().toString().equals("NaN")){
                            totallink.setText("0");

                        }else{


                        }

                    }

                    @Override
                    public void onError(ANError error) {

                    }




                });

    }




}