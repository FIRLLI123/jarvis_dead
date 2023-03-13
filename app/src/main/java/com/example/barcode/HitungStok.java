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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
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
import java.util.List;
import java.util.Locale;

public class HitungStok extends AppCompatActivity implements View.OnClickListener {

    TextView idoutletinputperdana1;
    TextView tanggalinputperdana1;
    TextView namaoutletinputperdana1;
    TextView namasalesinputperdana1, jaminput1;



    private ProgressDialog pDialog;
    private Context context;

    TextView lihatid1;

    ListView listinputperdana1;

    //SwipeRefreshLayout swipe_refresh;

    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();

    //TextView tanggal1, namasales1;

    public static String LINK, idlistperdana, namalistperdana, stoklist, hargalistperdana, totallistperdana, listketerangan;
    public static boolean filter;



    EditText idbarang1, namabarang1, stok1, iddelete1;
    Button cariid1;
    Button btntotal1, btninput1, btndelete1, btnrefresh1, btnupdate1, btncetak1;

    String query_kas, query_total;

    RadioButton buka, tutup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitung_stok);

        LINK = Config.host + "history.php";
        idlistperdana = "";
        namalistperdana = "";
        stoklist = "";
        //totallist = "";
        //query_kas = ""; query_total = "";
        filter = false;



        listinputperdana1        = (ListView) findViewById(R.id.listinputperdana);

        idoutletinputperdana1 = (TextView) findViewById(R.id.idoutletinputperdana);
        tanggalinputperdana1 = (TextView) findViewById(R.id.tanggalinputperdana);
        namaoutletinputperdana1 = (TextView) findViewById(R.id.namaoutletinputperdana);
        namasalesinputperdana1 = (TextView) findViewById(R.id.namasalesinputperdana);

        //total1 = (TextView) findViewById(R.id.total);

        //keterangan1 = (EditText) findViewById(R.id.keterangan2);

        jaminput1 = (TextView) findViewById(R.id.jaminput);

        context = HitungStok.this;
        pDialog = new ProgressDialog(context);


        //lihatid1 = (TextView) findViewById(R.id.lihatid);

        cariid1 = (Button) findViewById(R.id.cariid);
        idbarang1 = (EditText) findViewById(R.id.idbarang);
        namabarang1 = (EditText) findViewById(R.id.namabarang);
        stok1 = (EditText) findViewById(R.id.stok);
        //hargabarang1 = (EditText) findViewById(R.id.hargabarang);
        iddelete1 = (EditText) findViewById(R.id.iddelete);

        //btntotal1 = (Button) findViewById(R.id.btntotal); //total
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
                } else if (stok1.getText().toString().length() == 0) {        //2
                    //jika form Username belum di isi / masih kosong
                    //stok1.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
                } else if (stok1.getText().equals("order")) {        //2
                    //jika form Username belum di isi / masih kosong
                    //stok1.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Jika outlet order silahkan langsung ke form order", Toast.LENGTH_LONG).show();
                }  else {


                    save();

                    KasAdapter2();

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



                //save();
                //delete();
                update();

            }
        });

        btncetak1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idbarang1.getText().toString().length() == 0) {                    //1
                    //jika form Email belum di isi / masih kosong
                    idbarang1.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Silahkan Input Stok Terlebih Dahulu", Toast.LENGTH_LONG).show();
                } else if (namabarang1.getText().toString().length() == 0) {        //2
                    //jika form Username belum di isi / masih kosong
                    namabarang1.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Silahkan Input Stok Terlebih Dahulu", Toast.LENGTH_LONG).show();
                } else if (stok1.getText().toString().length() == 0) {        //2
                    //jika form Username belum di isi / masih kosong
                    stok1.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Silahkan Input Stok Terlebih Dahulu", Toast.LENGTH_LONG).show();
                } else {

                    String a = namasalesinputperdana1.getText().toString();
                    String b = idoutletinputperdana1.getText().toString();
                    String c = namaoutletinputperdana1.getText().toString();
                    Intent intent = new Intent(context, MenuUtamanew.class);
                    //Intent i = new Intent(getApplicationContext(), Menuutamanew.class);
                    intent.putExtra("namasales",""+a+"");
                    intent.putExtra("idoutlet",""+b+"");
                    intent.putExtra("namaoutlet",""+c+"");
                    //startActivity(i);
                    startActivity(intent);
                    finish();
                    //save();

                    //KasAdapter2();

                }
            }
        });



        KasAdapter2();

        showCustomDialog();
        buka = (RadioButton) findViewById(R.id.buka);
        tutup = (RadioButton) findViewById(R.id.tutup);

        buka.setOnClickListener(this);
        tutup.setOnClickListener(this);

    }

    private void showCustomDialog() {
        final Dialog dialog = new Dialog(this);
        //Mengeset judul dialog
        dialog.setTitle("Massage Attention");

        //Mengeset layout
        dialog.setContentView(R.layout.infoinputstok);

        //Membuat agar dialog tidak hilang saat di click di area luar dialog
        dialog.setCanceledOnTouchOutside(false);

        //Membuat dialog agar berukuran responsive
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        dialog.getWindow().setLayout((6 * width) / 7, LinearLayout.LayoutParams.WRAP_CONTENT);

        Button oke = (Button) dialog.findViewById(R.id.oke);

        oke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        //Menampilkan custom dialog
        dialog.show();

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
        AndroidNetworking.post( Config.host + "listalasanperdana.php" )
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
                                map.put("alasan",       responses.optString("alasan"));
                                //map.put("stok",       responses.optString("stok"));

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

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.listinputstok,
                new String[] {"id","alasan"},
                new int[] {R.id.idlistperdana, R.id.namalistperdana, R.id.stoklist});

        listinputperdana1.setAdapter(simpleAdapter);
        listinputperdana1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //no    = ((TextView) view.findViewById(R.id.no)).getText().toString();
                idlistperdana    = ((TextView) view.findViewById(R.id.idlistperdana)).getText().toString();
                namalistperdana  = ((TextView) view.findViewById(R.id.namalistperdana)).getText().toString();
                stoklist  = ((TextView) view.findViewById(R.id.stoklist)).getText().toString();



                iddelete1.setText(idlistperdana);
                namabarang1.setText(namalistperdana);
                stok1.setText(stoklist);


                //tanggal        = ((TextView) view.findViewById(R.id.tanggal)).getText().toString();
                //ListMenu();
            }
        });

        //swipe_refresh.setRefreshing(false);
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



    private void cariid() {

        AndroidNetworking.post(Config.host + "barangstok.php")
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
        AndroidNetworking.post(Config.host + "inputstok.php")
                .addBodyParameter("idoutlet", idoutletinputperdana1.getText().toString())
                .addBodyParameter("namaoutlet", namaoutletinputperdana1.getText().toString())
                .addBodyParameter("namasales", namasalesinputperdana1.getText().toString())
                .addBodyParameter("tanggal", tanggalinputperdana1.getText().toString())

                .addBodyParameter("item", namabarang1.getText().toString())
                .addBodyParameter("stok", stok1.getText().toString())
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
        AndroidNetworking.post(Config.host + "deletestok.php")
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
        AndroidNetworking.post(Config.host + "updatestok.php")
                .addBodyParameter("id", iddelete1.getText().toString())
                .addBodyParameter("item", namabarang1.getText().toString())
                .addBodyParameter("stok", stok1.getText().toString())
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


    @Override
    public void onClick(View view) {

        int id = view.getId();
        if (id == R.id.buka) {

            idbarang1.setText("");
            namabarang1.setText("");
            stok1.setText("");
        }
        else if (id == R.id.tutup){
            idbarang1.setText(" ");
            namabarang1.setText("Semua Item");
            stok1.setText("0");


        }





    }

}