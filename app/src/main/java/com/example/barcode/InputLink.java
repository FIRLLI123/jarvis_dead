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

public class InputLink extends AppCompatActivity {

    TextView idoutletinputlink1;
    TextView tanggalinputlink1;
    TextView namaoutletinputlink1;
    TextView namasalesinputlink1, itemlink1;
    TextView rp;

    EditText link1, iddelete1, saldolink1;
    Button btntotal1, btninput1, btndelete1, btnrefresh1, btnupdate1, btncetak1;

    private ProgressDialog pDialog;
    private Context context;



    ListView listinputperdana1;

    public static String LINK, idlistlink, listitemlink, listlink;
    public static boolean filter;

    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_link);

        idoutletinputlink1 = (TextView) findViewById(R.id.idoutletinputlink);
        tanggalinputlink1 = (TextView) findViewById(R.id.tanggalinputlink);
        namaoutletinputlink1 = (TextView) findViewById(R.id.namaoutletinputlink);
        namasalesinputlink1 = (TextView) findViewById(R.id.namasalesinputlink);
        rp = (TextView) findViewById(R.id.rp);
        link1 = (EditText) findViewById(R.id.link);
        itemlink1 = (TextView) findViewById(R.id.itemlink);

        context = InputLink.this;
        pDialog = new ProgressDialog(context);

        LINK = Config.host + "history.php";
        idlistlink = "";
        listitemlink = "";
        listlink = "";
        //totallist = "";
        //query_kas = ""; query_total = "";
        filter = false;

        listinputperdana1        = (ListView) findViewById(R.id.listinputperdana);

        //btntotal1 = (Button) findViewById(R.id.btntotal); //total
        btninput1 = (Button) findViewById(R.id.btninput); //input
        btndelete1 = (Button) findViewById(R.id.btndelete); //delete
        btnupdate1 = (Button) findViewById(R.id.btnupdate); //update
        btnrefresh1 = (Button) findViewById(R.id.btnrefresh); //refresh
        btncetak1 = (Button) findViewById(R.id.btncetak); //cetak


        iddelete1 = (EditText) findViewById(R.id.iddelete);



        Intent i = getIntent();
        String kiriman = i.getStringExtra("idoutlet");
        idoutletinputlink1.setText(kiriman);
        String kiriman2 = i.getStringExtra("namaoutlet");
        namaoutletinputlink1.setText(kiriman2);
        String kiriman3 = i.getStringExtra("namasales");
        namasalesinputlink1.setText(kiriman3);



        tanggalinputlink1.setText(getCurrentDate());

        btninput1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (link1.getText().toString().length() == 0) {                    //1
                    //jika form Email belum di isi / masih kosong
                    link1.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else {

                    //hitungHasil();
                    save();

                    KasAdapter2();

                }
            }
        });


        btncetak1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String a = idoutletinputlink1.getText().toString();
                String b = namaoutletinputlink1.getText().toString();
                String c = namasalesinputlink1.getText().toString();
                String d = tanggalinputlink1.getText().toString();
                Intent i = new Intent(getApplicationContext(), PrintPreviewLink.class);
                i.putExtra("idoutlet",""+a+"");
                i.putExtra("namaoutlet",""+b+"");
                i.putExtra("namasales",""+c+"");
                i.putExtra("tanggal",""+d+"");
                startActivity(i);

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


        btnupdate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //hitungHasil();
                //save();
                //delete();
                update();

            }
        });


        link1.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Tidak perlu melakukan apapun sebelum teks berubah
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    try {
                        // Parse angka dari inputan link1
                        double parsed = Double.parseDouble(s.toString());

                        // Format angka menjadi format Rupiah
                        String formatted = NumberFormat.getCurrencyInstance(new Locale("id", "ID")).format(parsed);

                        // Set teks dengan format Rupiah di TextView rp
                        rp.setText(formatted);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Jika input kosong, tampilkan Rp 0
                    rp.setText("Rp 0");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Tidak perlu melakukan apapun setelah teks berubah
            }
        });


    }

    private void KasAdapter2(){

        //swipe_refresh.setRefreshing(true);
        aruskas.clear(); listinputperdana1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "listinputlink2.php" )
                .addBodyParameter("idoutlet", idoutletinputlink1.getText().toString())
                .addBodyParameter("namasales", namasalesinputlink1.getText().toString())
                .addBodyParameter("tanggal", tanggalinputlink1.getText().toString())
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
                                map.put("harga",       responses.optString("harga"));

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


    private void Adapter(){

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.listinputlink,
                new String[] {"id","item","harga"},
                new int[] {R.id.idlistlink, R.id.listitemlink, R.id.listlink});

        listinputperdana1.setAdapter(simpleAdapter);
        listinputperdana1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //no    = ((TextView) view.findViewById(R.id.no)).getText().toString();
                idlistlink    = ((TextView) view.findViewById(R.id.idlistlink)).getText().toString();
                listitemlink  = ((TextView) view.findViewById(R.id.listitemlink)).getText().toString();
                listlink  = ((TextView) view.findViewById(R.id.listlink)).getText().toString();



                iddelete1.setText(idlistlink);
                //listitemlink.setText(listitemlink);
                link1.setText(listlink);


                //tanggal        = ((TextView) view.findViewById(R.id.tanggal)).getText().toString();
                //ListMenu();
            }
        });

        //swipe_refresh.setRefreshing(false);
    }


    private void save() {
        //pDialog.setMessage("Login Process...");
        //showDialog();
        AndroidNetworking.post(Config.host + "inputlink.php")
                .addBodyParameter("idoutlet", idoutletinputlink1.getText().toString())
                .addBodyParameter("namaoutlet", namaoutletinputlink1.getText().toString())
                .addBodyParameter("namasales", namasalesinputlink1.getText().toString())
                .addBodyParameter("tanggal", tanggalinputlink1.getText().toString())

                .addBodyParameter("item", itemlink1.getText().toString())
                .addBodyParameter("harga", link1.getText().toString())



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


    private void update() {
        pDialog.setMessage("Update Process...");
        showDialog();
        AndroidNetworking.post(Config.host + "updatelink.php")
                .addBodyParameter("id", iddelete1.getText().toString())
                //.addBodyParameter("namaoutlet", namaoutletinputlink1.getText().toString())
                //.addBodyParameter("namasales", namasalesinputlink1.getText().toString())
                //.addBodyParameter("tanggal", tanggalinputlink1.getText().toString())

                .addBodyParameter("item", itemlink1.getText().toString())
                .addBodyParameter("harga", link1.getText().toString())
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


    private void delete() {
        pDialog.setMessage("Delete Process...");
        showDialog();
        AndroidNetworking.post(Config.host + "deletelink.php")
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

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


}