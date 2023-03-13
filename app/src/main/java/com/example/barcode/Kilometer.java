package com.example.barcode;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;

import com.androidnetworking.error.ANError;

import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.barcode.helper.Config;
import com.google.zxing.integration.android.IntentIntegrator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Kilometer extends AppCompatActivity {
    TextView hasilkm1,tanggal1;

    EditText kmawal1, kmakhir1;
    Button inputkm1, carikm1,carikmawal1,inputkmawal1;
    private ProgressDialog pDialog;
    private Context context;

    TextView lihatid1;

    ListView listinputperdana1;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    //SwipeRefreshLayout swipe_refresh;

    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();

    //TextView tanggal1, namasales1;

    public static String LINK, tanggallist, kmawallist, kmakhirlist, totalkmlist, listketerangan;
    public static boolean filter;


    String query_kas, query_total;


    private static final String[] countries = new String[]{
            "ANWAR", "YUSUF MAULANA", "AKMAL MAULANA", "ARDIANSYAH", "M ZAIRIN", "TUTUT SETIADI",
            "ROHMAT", "M SANUSI", "SARYONO", "KRISTIAN", "RAHMATULLAH", "SULAIMAN", "M SIHABUDIN",
            "ADITYA LUTVI", "SAEFUNAJIB", "ASEP NURKOMARUDIN", "M JOFAN", "SYARIF HIDAYAT", "ANCHE ARIS SUGIANTO",
            "ALEX SURYA PUTRA", "MUHAMMAD ZIAD", "RIVAI CAHYA NUGRAHA", "ABDUL ROZAK", "IBNU HARWOTO",
            "EKO SURYANTO", "RAMADHANI NUGROHO","RAHADIAN PUTRA", "FIQIH ABDULLAH", "SUHENDRA",
            "LUCKY DARMAWAN", "DIMAS ARI WICAKSONO", "RENDI H", "AANG PURNAMA", "INDRA RUKMANA",
            "OKA KAMARULSYAH", "M HANIF","KETUT ANOM JATMIKA","CHANDRA HADI LESMANA","ANDY BUDIMAN","FIRLLI"
    };

    //qr code scanner object
    private IntentIntegrator intentIntegrator;
    AutoCompleteTextView edittext991;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kilometer);


        dateFormatter = new SimpleDateFormat("yyyy/M/d", Locale.US);

        context = Kilometer.this;
        pDialog = new ProgressDialog(context);

        edittext991 = (AutoCompleteTextView) findViewById(R.id.edittext99);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
        edittext991.setAdapter(adapter);


        LINK = Config.host + "history.php";
        //idlistperdana = "";
        tanggallist = "";
        kmawallist = "";
        kmakhirlist = "";
        totalkmlist = "";
        //listketerangan = "";
        //totallist = "";
        //query_kas = ""; query_total = "";
        filter = false;



        listinputperdana1        = (ListView) findViewById(R.id.listinputperdana);
        kmawal1        = (EditText) findViewById(R.id.kmawal);
        kmakhir1        = (EditText) findViewById(R.id.kmakhir);
        tanggal1 = (TextView) findViewById(R.id.tanggal);

        hasilkm1 = (TextView) findViewById(R.id.hasilkm);
        inputkm1 = (Button) findViewById(R.id.inputkm);
        inputkmawal1 = (Button) findViewById(R.id.inputkmawal);

        carikm1 = (Button) findViewById(R.id.carikm);
        carikmawal1 = (Button) findViewById(R.id.carikmawal);


        tanggal1.setText(getCurrentDate());

        carikm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edittext991.getText().toString().length() == 0) {                    //1
                    //jika form Email belum di isi / masih kosong
                    edittext991.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else {
                    KasAdapter2();
                    //hitungHasil();
                }

            }
        });

        carikmawal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edittext991.getText().toString().length() == 0) {                    //1
                    //jika form Email belum di isi / masih kosong
                    edittext991.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else {
                    KasAdapter3();
                }
            }
        });

        inputkm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kmawal1.getText().toString().length() == 0) {                    //1
                    //jika form Email belum di isi / masih kosong
                    kmawal1.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else if (kmakhir1.getText().toString().length() == 0) {        //2
                    //jika form Username belum di isi / masih kosong
                    kmakhir1.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if (edittext991.getText().toString().length() == 0) {        //2
                    //jika form Username belum di isi / masih kosong
                    edittext991.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }  else {

                    hitungHasil();
                    save();

                    //KasAdapter2();

                }
            }
        });

        tanggal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();

            }
        });

        inputkmawal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edittext991.getText().toString().length() == 0) {                    //1
                    //jika form Email belum di isi / masih kosong
                    edittext991.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else if (kmawal1.getText().toString().length() == 0) {        //2
                    //jika form Username belum di isi / masih kosong
                    kmawal1.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }  else {
                    save2();
                }
            }
        });

    }

    private void KasAdapter2(){
        pDialog.setMessage("Process...");
        showDialog();
        //swipe_refresh.setRefreshing(true);
        aruskas.clear(); listinputperdana1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "km.php" )
                .addBodyParameter("tanggal", tanggal1.getText().toString())
                .addBodyParameter("namasales", edittext991.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response


                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);

                        //namabarang1.setText((response.optString("tanggal")));
                        edittext991.setText((response.optString("namasales")));
                        kmawal1.setText((response.optString("kmawal")));
                        kmakhir1.setText((response.optString("kmakhir")));
                        hasilkm1.setText((response.optString("totalkm")));

                        hideDialog();
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    private void KasAdapter3(){
        pDialog.setMessage("Process...");
        showDialog();
        //swipe_refresh.setRefreshing(true);
        aruskas.clear(); listinputperdana1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "kmawal.php" )
                .addBodyParameter("tanggal", tanggal1.getText().toString())
                .addBodyParameter("namasales", edittext991.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response


                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);

                        //namabarang1.setText((response.optString("tanggal")));
                        edittext991.setText((response.optString("namasales")));
                        kmawal1.setText((response.optString("kmawal")));
                        //kmakhir1.setText((response.optString("kmakhir")));
                        //hasilkm1.setText((response.optString("totalkm")));

                        hideDialog();
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
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
                tanggal1.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }

    public String getCurrentDate() {
        final Calendar c = Calendar.getInstance();
        int year, month, day;
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DATE);
        SimpleDateFormat contoh1 = new SimpleDateFormat("EEEE");

        String hariotomatis = contoh1.format(c.getTime());

        //return day +"/" + (month+1) + "/" + year;
        //return (month+1) +"/" + day + "/" + year;
        return year + "/" + (month + 1) + "/" + day;
    }



    private void save() {
        pDialog.setMessage("Input Process...");
        showDialog();
        AndroidNetworking.post(Config.host + "inputkm.php")
                .addBodyParameter("tanggal", tanggal1.getText().toString())
                .addBodyParameter("namasales", edittext991.getText().toString())
                .addBodyParameter("kmawal", kmawal1.getText().toString())
                .addBodyParameter("kmakhir", kmakhir1.getText().toString())
                .addBodyParameter("totalkm", hasilkm1.getText().toString())



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
                            Toast.makeText(getApplicationContext(), "Berhasil disimpan",
                                    Toast.LENGTH_LONG).show();


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


    private void save2() {
        pDialog.setMessage("Input Kilometer Awal Process...");
        showDialog();
        AndroidNetworking.post(Config.host + "inputkmawal.php")
                .addBodyParameter("tanggal", tanggal1.getText().toString())
                .addBodyParameter("namasales", edittext991.getText().toString())
                .addBodyParameter("kmawal", kmawal1.getText().toString())
                //.addBodyParameter("kmakhir", kmakhir1.getText().toString())
                //.addBodyParameter("totalkm", hasilkm1.getText().toString())



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
                            Toast.makeText(getApplicationContext(), "Berhasil disimpan",
                                    Toast.LENGTH_LONG).show();


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

    private void hitungHasil() {
        int item1 = Integer.parseInt(kmawal1.getText().toString());
        int hitem1 = Integer.parseInt(kmakhir1.getText().toString());
        int hasilitem1 = hitem1 - item1;
        hasilkm1.setText(String.valueOf(hasilitem1));
    }

}