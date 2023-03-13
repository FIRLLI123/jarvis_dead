package com.example.barcode;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
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

public class ListApprove extends AppCompatActivity {
    TextView tanggaldari,tanggalsampai;

    TextView namasales, idabsen, jam, tanggal, kecamatan, keterangan, statusabsen, absen;

    TextView status, posisi;

    private ProgressDialog pDialog;
    private Context context;
    Button btnsumabsen;

    ListView listtest1;

    Button caritanggal, btnapprove, btntolak, btnadmin, btnsales;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    public static String LINK,idlist,namasaleslist, tanggallist, jamlist, kecamatanlist, absenlist, keteranganlist, statuslist, pendinglist;
    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();

    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_approve);

        this.mHandler = new Handler();
        m_Runnable.run();


        context = ListApprove.this;
        pDialog = new ProgressDialog(context);

        LINK = Config.host + "history.php";
        idlist = "";
        namasaleslist = "";
        jamlist = "";
        tanggallist = "";
        kecamatanlist = "";
        absenlist = "";
        keteranganlist = "";
        statuslist = "";
        pendinglist = "";


        dateFormatter = new SimpleDateFormat("yyyy/MM/dd", Locale.US);

        tanggaldari = (TextView) findViewById(R.id.tanggaldari);
        tanggalsampai = (TextView) findViewById(R.id.tanggalsampai);

        namasales = (TextView) findViewById(R.id.namasales);
        idabsen = (TextView) findViewById(R.id.idabsen);
        jam = (TextView) findViewById(R.id.jam);
        tanggal = (TextView) findViewById(R.id.tanggal);
        kecamatan = (TextView) findViewById(R.id.kecamatan);
        keterangan = (TextView) findViewById(R.id.keterangan);
        //statusabsen = (TextView) findViewById(R.id.idabsen);
        absen = (TextView) findViewById(R.id.absen);

        status = (TextView) findViewById(R.id.status);

        posisi = (TextView) findViewById(R.id.posisi);



        listtest1 = (ListView) findViewById(R.id.listbayartempo);

        caritanggal = (Button) findViewById(R.id.caritanggal);
        btnapprove = (Button) findViewById(R.id.btnapprove);
        btntolak = (Button) findViewById(R.id.btntolak);
        btnadmin = (Button) findViewById(R.id.btnadmin);
        btnsales = (Button) findViewById(R.id.btnsales);

        btnsumabsen = (Button) findViewById(R.id.btnsumabsen);

        Intent kolomlogin = getIntent();
        String kiriman1 = kolomlogin.getStringExtra("nama");
        namasales.setText(kiriman1);

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


        caritanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listtangal();
            }
        });

        btnsumabsen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SumABSEN_approve.class);
                startActivity(i);
            }
        });



        posisi.setText("ADMIN SUPPORT");

        btnsales.setBackground(getResources().getDrawable(R.drawable.custom10));
        btnsales.setTextColor(getResources().getColor(R.color.abutua));


        btnadmin.setBackground(getResources().getDrawable(R.drawable.custom11));
        btnadmin.setTextColor(getResources().getColor(R.color.birutua));

        btnadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                posisi.setText("ADMIN SUPPORT");

                btnsales.setBackground(getResources().getDrawable(R.drawable.custom10));
                btnsales.setTextColor(getResources().getColor(R.color.abutua));


                btnadmin.setBackground(getResources().getDrawable(R.drawable.custom11));
                btnadmin.setTextColor(getResources().getColor(R.color.birutua));

                listtangal();
            }
        });

        btnsales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                posisi.setText("SALES FORCE");

                btnsales.setBackground(getResources().getDrawable(R.drawable.custom11));
                btnsales.setTextColor(getResources().getColor(R.color.birutua));



                btnadmin.setBackground(getResources().getDrawable(R.drawable.custom10));
                btnadmin.setTextColor(getResources().getColor(R.color.abutua));

                listtangal();
            }
        });






        btnapprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                status.setText("APPROVE");

                if (namasales.getText().toString().length() == 0) {
                    //1
                    Toast.makeText(getApplicationContext(), "SILAHKAN PILIH NAMA KARYAWAN TERLEBIH DAHULU",
                            Toast.LENGTH_LONG).show();

                    //Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
                }else{

                    absengak();
                    //Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
                }

            }
        });

        btntolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                status.setText("DITOLAK");

                if (namasales.getText().toString().length() == 0) {
                    //1
                    Toast.makeText(getApplicationContext(), "SILAHKAN PILIH NAMA KARYAWAN TERLEBIH DAHULU",
                            Toast.LENGTH_LONG).show();

                    //Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
                }else{

                    absengak();
                    //Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
                }

            }
        });

        //listtangal();

    }


    private final Runnable m_Runnable = new Runnable() {
        public void run() {
            tanggaldari = (TextView) findViewById(R.id.tanggaldari);
            tanggalsampai = (TextView) findViewById(R.id.tanggalsampai);

            namasales = (TextView) findViewById(R.id.namasales);
            idabsen = (TextView) findViewById(R.id.idabsen);
            jam = (TextView) findViewById(R.id.jam);
            tanggal = (TextView) findViewById(R.id.tanggal);
            kecamatan = (TextView) findViewById(R.id.kecamatan);
            keterangan = (TextView) findViewById(R.id.keterangan);
            //statusabsen = (TextView) findViewById(R.id.idabsen);
            absen = (TextView) findViewById(R.id.absen);

            status = (TextView) findViewById(R.id.status);

            posisi = (TextView) findViewById(R.id.posisi);



            listtest1 = (ListView) findViewById(R.id.listbayartempo);

            caritanggal = (Button) findViewById(R.id.caritanggal);
            btnapprove = (Button) findViewById(R.id.btnapprove);
            btntolak = (Button) findViewById(R.id.btntolak);
            btnadmin = (Button) findViewById(R.id.btnadmin);
            btnsales = (Button) findViewById(R.id.btnsales);

            btnsumabsen = (Button) findViewById(R.id.btnsumabsen);

            //Toast.makeText(ListApprove.this, "", Toast.LENGTH_SHORT).show();
            listtangal();
            ListApprove.this.mHandler.postDelayed(m_Runnable, 10000);
        }
    };

    public void prosesdasboard2(){

        new CountDownTimer(1000, 1000) {

            public void onTick(long millisUntilFinished) {
                pDialog.setMessage("Loading... :"+ millisUntilFinished / 1000);
                showDialog();
                pDialog.setCanceledOnTouchOutside(false);

                updatestatusabsen();
                listtangal();
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                hideDialog();


            }
        }.start();

    }


    public void absengak() {

        String namasalesalert = namasales.getText().toString();
        String statusalert = status.getText().toString();


        //String a = validasib1.getText().toString();
        final Dialog dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setTitle("ABSENSI");
        AlertDialog alertDialog;
        alertDialog = new AlertDialog.Builder(this)

                .setIcon(R.drawable.titik)
                .setTitle(R.string.app_name)
                .setMessage("Yakin untuk Nama karyawan "+namasalesalert+", anda akan melakukan "+statusalert+" ?")
                .setPositiveButton("OKE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        prosesdasboard2();



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


    private void updatestatusabsen() {
        pDialog.setMessage("Process...");
        showDialog();
        pDialog.setCanceledOnTouchOutside(false);
        AndroidNetworking.post(Config.host + "updatestatusabsen.php")
                .addBodyParameter("id", idabsen.getText().toString())
                .addBodyParameter("status", status.getText().toString())



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
                            Toast.makeText(getApplicationContext(), "Berhasil merubah",
                                    Toast.LENGTH_LONG).show();

                            save();


                        } else {
                            hideDialog();
                            Toast.makeText(getApplicationContext(), "failed",
                                    Toast.LENGTH_LONG).show();
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

    public void prosesdasboard1(){

        new CountDownTimer(2000, 1000) {

            public void onTick(long millisUntilFinished) {
                pDialog.setMessage("Loading... :"+ millisUntilFinished / 1000);
                showDialog();
                pDialog.setCanceledOnTouchOutside(false);

                listtangal();
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                hideDialog();


            }
        }.start();

    }

    private void listtangal(){

        //swipe_refresh.setRefreshing(true);
        aruskas.clear();
        listtest1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "listabsen_fix_tanggal_list.php" )
                //.addBodyParameter("namasales", namasales.getText().toString())
                .addBodyParameter("tanggaldari", tanggaldari.getText().toString())
                .addBodyParameter("tanggalsampai", tanggalsampai.getText().toString())
                .addBodyParameter("posisi", posisi.getText().toString())
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
                                map.put("namasales",         responses.optString("namasales"));
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

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.list_absen_fix_list,
                new String[]{"id","namasales","tanggal", "jam", "kecamatan", "absen", "keterangan", "status"},
                new int[]{R.id.idlistabsen,R.id.namasaleslistabsen,R.id.tanggallistabsen, R.id.jamlistabsen, R.id.kecamatanlistabsen, R.id.absenlistabsen, R.id.keteranganlistabsen, R.id.statuslistabsen});

        listtest1.setAdapter(simpleAdapter);

        listtest1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //no    = ((TextView) view.findViewById(R.id.no)).getText().toString();
                idlist = ((TextView) view.findViewById(R.id.idlistabsen)).getText().toString();
                namasaleslist = ((TextView) view.findViewById(R.id.namasaleslistabsen)).getText().toString();
                tanggallist = ((TextView) view.findViewById(R.id.tanggallistabsen)).getText().toString();
                jamlist = ((TextView) view.findViewById(R.id.jamlistabsen)).getText().toString();
                kecamatanlist = ((TextView) view.findViewById(R.id.kecamatanlistabsen)).getText().toString();
                absenlist = ((TextView) view.findViewById(R.id.absenlistabsen)).getText().toString();
                keteranganlist = ((TextView) view.findViewById(R.id.keteranganlistabsen)).getText().toString();
                //statuslist = ((TextView) view.findViewById(R.id.statuslistabsen)).getText().toString();


                namasales.setText(namasaleslist);
                idabsen.setText(idlist);
                jam.setText(jamlist);
                tanggal.setText(tanggallist);
                kecamatan.setText(kecamatanlist);
                keterangan.setText(keteranganlist);
                //status.setText(idlist);
                absen.setText(absenlist);


            }
        });
    }



    private void save() {
        //pDialog.setMessage("Login Process...");
        //showDialog();
        AndroidNetworking.post(Config.host + "inputabsenapproval.php")
                .addBodyParameter("namasales",  namasales.getText().toString())
                .addBodyParameter("jam", jam.getText().toString())
                .addBodyParameter("tanggal", tanggal.getText().toString())
                .addBodyParameter("kecamatan", kecamatan.getText().toString())
                .addBodyParameter("keterangan", keterangan.getText().toString())
                .addBodyParameter("statusabsen", status.getText().toString())
                .addBodyParameter("absen", absen.getText().toString())



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
                            Toast.makeText(getApplicationContext(), "Masuk ke Summary APPROVAL",
                                    Toast.LENGTH_LONG).show();




//                            ok1.setVisibility(View.VISIBLE);
//                            tabsendatang1.setTextColor(getResources().getColor(R.color.hijau));
//                            tabsendatang1.setText("TELAH ABSEN");

                        } else {
                            //hideDialog();
                            Toast.makeText(getApplicationContext(), "FAILED",
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




}