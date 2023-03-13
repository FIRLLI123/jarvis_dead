package com.example.barcode;

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

public class Input2EX extends AppCompatActivity {

    TextView idoutletinputperdana1;
    TextView tanggalinputperdana1;
    TextView namaoutletinputperdana1;
    TextView namasalesinputperdana1, total1, jaminput1;

    EditText keterangan1;
    private ProgressDialog pDialog;
    private Context context;

    TextView lihatid1;

    ListView listinputperdana1;

    //SwipeRefreshLayout swipe_refresh;

    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();

    //TextView tanggal1, namasales1;

    public static String LINK, idlistperdana, namalistperdana, qtylistperdana, hargalistperdana, totallistperdana, listketerangan;
    public static boolean filter;



    EditText idbarang1, namabarang1, hargabarang1, qtybarang1, iddelete1;
    Button cariid1;
    Button btntotal1, btninput1, btndelete1, btnrefresh1, btnupdate1, btncetak1;

    private static final String[] countries = new String[]{
            "ota", "p16gb", "p2.5gb", "p23gb", "p2gb",
            "p35c", "p35t", "p3c", "p3t", "p51gb",
            "p6gb", "p70gb", "p7c", "p7t", "p9gb"
    };


    String query_kas, query_total;
    AutoCompleteTextView edittext991;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input2_ex);

        edittext991 = (AutoCompleteTextView) findViewById(R.id.edittext99);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
        edittext991.setAdapter(adapter);


        LINK = Config.host + "history.php";
        idlistperdana = "";
        namalistperdana = "";
        qtylistperdana = "";
        hargalistperdana = "";
        totallistperdana = "";
        listketerangan = "";
        //totallist = "";
        //query_kas = ""; query_total = "";
        filter = false;



        listinputperdana1        = (ListView) findViewById(R.id.listinputperdana);
        idoutletinputperdana1 = (TextView) findViewById(R.id.idoutletinputperdana);
        tanggalinputperdana1 = (TextView) findViewById(R.id.tanggalinputperdana);
        namaoutletinputperdana1 = (TextView) findViewById(R.id.namaoutletinputperdana);
        namasalesinputperdana1 = (TextView) findViewById(R.id.namasalesinputperdana);

        total1 = (TextView) findViewById(R.id.total);

        keterangan1 = (EditText) findViewById(R.id.keterangan2);

        jaminput1 = (TextView) findViewById(R.id.jaminput);

        context = Input2EX.this;
        pDialog = new ProgressDialog(context);


        //lihatid1 = (TextView) findViewById(R.id.lihatid);

        cariid1 = (Button) findViewById(R.id.cariid);
        idbarang1 = (EditText) findViewById(R.id.idbarang);
        namabarang1 = (EditText) findViewById(R.id.namabarang);
        qtybarang1 = (EditText) findViewById(R.id.qtybarang);
        hargabarang1 = (EditText) findViewById(R.id.hargabarang);
        iddelete1 = (EditText) findViewById(R.id.iddelete);

        btntotal1 = (Button) findViewById(R.id.btntotal); //total
        btninput1 = (Button) findViewById(R.id.btninput); //input
        btndelete1 = (Button) findViewById(R.id.btndelete); //delete
        btnupdate1 = (Button) findViewById(R.id.btnupdate); //update
        btnrefresh1 = (Button) findViewById(R.id.btnrefresh); //refresh
        btncetak1 = (Button) findViewById(R.id.btncetak); //cetak


        Intent i = getIntent();
        String kiriman = i.getStringExtra("idoutlet");
        idoutletinputperdana1.setText(kiriman);
        String kiriman2 = i.getStringExtra("namaoutlet");
        namaoutletinputperdana1.setText(kiriman2);
        String kiriman3 = i.getStringExtra("namasales");
        namasalesinputperdana1.setText(kiriman3);



        tanggalinputperdana1.setText(getCurrentDate());
        jaminput1.setText(jamotomatis());
        cariid1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = edittext991.getText().toString();
idbarang1.setText(a);
                cariid();


            }
        });

        btninput1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idbarang1.getText().toString().length() == 0) {                    //1
                    //jika form Email belum di isi / masih kosong
                    idbarang1.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else if (namabarang1.getText().toString().length() == 0) {        //2
                    //jika form Username belum di isi / masih kosong
                    namabarang1.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else if (qtybarang1.getText().toString().length() == 0) {        //2
                    //jika form Username belum di isi / masih kosong
                    qtybarang1.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else if (hargabarang1.getText().toString().length() == 0) {        //2
                    //jika form Username belum di isi / masih kosong
                    hargabarang1.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if (keterangan1.getText().toString().length() == 0) {        //2
                    //jika form Username belum di isi / masih kosong
                    keterangan1.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else {

                    hitungHasil();
                    save();
                    KasAdapter2();

                }
            }
        });

        btntotal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idbarang1.getText().toString().length() == 0) {                    //1
                    //jika form Email belum di isi / masih kosong
                    idbarang1.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else if (namabarang1.getText().toString().length() == 0) {        //2
                    //jika form Username belum di isi / masih kosong
                    namabarang1.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else if (qtybarang1.getText().toString().length() == 0) {        //2
                    //jika form Username belum di isi / masih kosong
                    qtybarang1.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else if (hargabarang1.getText().toString().length() == 0) {        //2
                    //jika form Username belum di isi / masih kosong
                    hargabarang1.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else if (keterangan1.getText().toString().length() == 0) {        //2
                    //jika form Username belum di isi / masih kosong
                    keterangan1.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Nomor Seri boleh kosong", Toast.LENGTH_LONG).show();
                } else {

                    hitungHasil();
                    //save();
                }
            }
        });

        btndelete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iddelete1.getText().toString().length() == 0) {                    //1
                    //jika form Email belum di isi / masih kosong
                    //iddelete1.setError("");
                    Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
                }  else {

                    //hitungHasil();
                    //save();
                    delete();
                    KasAdapter2();
                }
            }
        });

        btnrefresh1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //hitungHasil();
                //save();
                //delete();
                KasAdapter2();

            }
        });

        btnupdate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                hitungHasil();
                //save();
                //delete();
                update();

            }
        });


        btncetak1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String a = idoutletinputperdana1.getText().toString();
                String b = namaoutletinputperdana1.getText().toString();
                String c = namasalesinputperdana1.getText().toString();
                String d = tanggalinputperdana1.getText().toString();
                Intent i = new Intent(getApplicationContext(), PrintPreviewEX.class);
                i.putExtra("idoutlet",""+a+"");
                i.putExtra("namaoutlet",""+b+"");
                i.putExtra("namasales",""+c+"");
                i.putExtra("tanggal",""+d+"");
                startActivity(i);

            }
        });
        //swipe_refresh   = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        KasAdapter2();
        /*
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                query_kas   =
                        "SELECT * FROM input2 WHERE idoutlet='idoutlet' and tanggal='tanggal'";

                LINK = Config.host + "listinputperdana.php";
                KasAdapter2();
                //text_filter.setVisibility(View.GONE);
            }
        });
**/

    }


    public String jamotomatis(){
        Calendar c1 = Calendar.getInstance();
        //SimpleDateFormat sdf1 = new SimpleDateFormat("d/M/yyyy h:m:s a");
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
        String strdate1 = sdf1.format(c1.getTime());
        return strdate1;



    }


    private void KasAdapter2(){

        //swipe_refresh.setRefreshing(true);
        aruskas.clear(); listinputperdana1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "listinputperdanaEX2.php" )
                .addBodyParameter("idoutlet", idoutletinputperdana1.getText().toString())
                .addBodyParameter("namasales", namasalesinputperdana1.getText().toString())
                .addBodyParameter("tanggal", tanggalinputperdana1.getText().toString())
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

                                JSONObject responses    = jsonArray.getJSONObject(i);
                                HashMap<String, String> map = new HashMap<String, String>();
                                //map.put("no",         responses.optString("no"));
                                map.put("id",         responses.optString("id"));
                                map.put("item",       responses.optString("item"));
                                map.put("qty",       responses.optString("qty"));
                                map.put("harga",       responses.optString("harga"));
                                map.put("total",       rupiahFormat.format(responses.optDouble("total")));
                                map.put("keterangan",       responses.optString("keterangan"));

                                //map.put("tanggal",      responses.optString("tanggal"));

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


    private void Adapter(){

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.listinputperdanaex,
                new String[] {"id","item","qty","harga", "total", "keterangan"},
                new int[] {R.id.idlistperdana, R.id.namalistperdana, R.id.qtylistperdana, R.id.hargalistperdana, R.id.totallistperdana, R.id.listketerangan});

        listinputperdana1.setAdapter(simpleAdapter);
        listinputperdana1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //no    = ((TextView) view.findViewById(R.id.no)).getText().toString();
                idlistperdana    = ((TextView) view.findViewById(R.id.idlistperdana)).getText().toString();
                namalistperdana  = ((TextView) view.findViewById(R.id.namalistperdana)).getText().toString();
                qtylistperdana  = ((TextView) view.findViewById(R.id.qtylistperdana)).getText().toString();
                hargalistperdana  = ((TextView) view.findViewById(R.id.hargalistperdana)).getText().toString();
                totallistperdana  = ((TextView) view.findViewById(R.id.totallistperdana)).getText().toString();
                listketerangan  = ((TextView) view.findViewById(R.id.listketerangan)).getText().toString();


                iddelete1.setText(idlistperdana);
                namabarang1.setText(namalistperdana);
                qtybarang1.setText(qtylistperdana);
                hargabarang1.setText(hargalistperdana);
                total1.setText(totallistperdana);
                keterangan1.setText(listketerangan);

                //tanggal        = ((TextView) view.findViewById(R.id.tanggal)).getText().toString();
                //ListMenu();
            }
        });

        //swipe_refresh.setRefreshing(false);
    }

    public String getCurrentDate() {
        //SimpleDateFormat contoh1 = new SimpleDateFormat("yyyy/MM/dd");
        //String hariotomatis = contoh1.format(c.getTime());
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat contoh1 = new SimpleDateFormat("yyyy/M/d");
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


    private void cariid() {

        AndroidNetworking.post(Config.host + "barangperdanaid.php")
                .addBodyParameter("id", idbarang1.getText().toString())
                //.addBodyParameter("bulan", sp.getSelectedItem().toString())
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
                        namabarang1.setText((response.optString("item")));
                        hargabarang1.setText((response.optString("harga")));
                        //actualhero1.setText((response.optString("actual")));
                        //achhero1.setText((response.optString("ach")));
                        //gaphero1.setText((response.optString("gap")));
                        //bulan1.setText((response.optString("bulan")));

                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }


    private void save() {
        //pDialog.setMessage("Login Process...");
        //showDialog();
        AndroidNetworking.post(Config.host + "inputperdanaEX.php")
                .addBodyParameter("idoutlet", idoutletinputperdana1.getText().toString())
                .addBodyParameter("namaoutlet", namaoutletinputperdana1.getText().toString())
                .addBodyParameter("namasales", namasalesinputperdana1.getText().toString())
                .addBodyParameter("tanggal", tanggalinputperdana1.getText().toString())

                .addBodyParameter("item", namabarang1.getText().toString())
                .addBodyParameter("qty", qtybarang1.getText().toString())
                .addBodyParameter("harga", hargabarang1.getText().toString())
                .addBodyParameter("total", total1.getText().toString())
                .addBodyParameter("keterangan", keterangan1.getText().toString())
                .addBodyParameter("jam", jaminput1.getText().toString())



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


    private void delete() {
        pDialog.setMessage("Delete Process...");
        showDialog();
        AndroidNetworking.post(Config.host + "deleteperdanaEX.php")
                .addBodyParameter("id", iddelete1.getText().toString())

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
                            Toast.makeText(getApplicationContext(), "Delete Berhasil",
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




    private void gotoCourseActivity() {
        String a = namasalesinputperdana1.getText().toString();
        Intent intent = new Intent(context, Summary.class);
        intent.putExtra("namasales", "" + a + "");
        startActivity(intent);
        finish();


    }



    private void update() {
        pDialog.setMessage("Update Process...");
        showDialog();
        AndroidNetworking.post(Config.host + "updateperdanaEX.php")
                .addBodyParameter("id", iddelete1.getText().toString())
                .addBodyParameter("item", namabarang1.getText().toString())
                .addBodyParameter("qty", qtybarang1.getText().toString())
                .addBodyParameter("harga", hargabarang1.getText().toString())
                .addBodyParameter("total", total1.getText().toString())
                .addBodyParameter("keterangan", keterangan1.getText().toString())
                .addBodyParameter("jam", jaminput1.getText().toString())
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
                            Toast.makeText(getApplicationContext(), "Update Berhasil",
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



    private void hitungHasil() {
        int item1 = Integer.parseInt(qtybarang1.getText().toString());
        int hitem1 = Integer.parseInt(hargabarang1.getText().toString());
        int hasilitem1 = item1 * hitem1;
        total1.setText(String.valueOf(hasilitem1));
    }


}