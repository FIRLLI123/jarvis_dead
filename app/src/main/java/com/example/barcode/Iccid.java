package com.example.barcode;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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
import com.example.barcode.Data.Data_BayarEX;
import com.example.barcode.helper.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
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

public class Iccid extends AppCompatActivity {
    EditText noseri1,hitung, noseri2, hasil, hasiljumlahsisah;
    ListView listdataoutlet1;
    TextView  jumlahiccid;

    private ProgressDialog pDialog;
    private Context context;

    Handler mHandler;
    Button btnupdate, cari, reset;

    TextView idoutlet, namaoutlet, namasales, tanggal, namaitem, gudang;

    public static String LINK;
    public static String snlist;
    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iccid);


        this.mHandler = new Handler();
        m_Runnable.run();
        snlist = "";

        noseri1 = (EditText) findViewById(R.id.noseri1);
        hitung = (EditText) findViewById(R.id.hitung);
        noseri2 = (EditText) findViewById(R.id.noseri2);
        hasil = (EditText) findViewById(R.id.hasil);
        jumlahiccid = (TextView) findViewById(R.id.jumlahiccid);
        hasiljumlahsisah = (EditText) findViewById(R.id.hasiljumlahsisah);

        btnupdate = (Button) findViewById(R.id.btnupdate);
        cari = (Button) findViewById(R.id.cari);
        reset = (Button) findViewById(R.id.reset);



        idoutlet = (TextView) findViewById(R.id.idoutlet);
        namaoutlet = (TextView) findViewById(R.id.namaoutlet);
        namasales = (TextView) findViewById(R.id.namasales);
        tanggal = (TextView) findViewById(R.id.tanggal);
        namaitem = (TextView) findViewById(R.id.namaitem);
        gudang = (TextView) findViewById(R.id.gudang);


        listdataoutlet1 = (ListView) findViewById(R.id.listdataoutlet);
        context = Iccid.this;
        pDialog = new ProgressDialog(context);

        Intent i = getIntent();
        String kiriman = i.getStringExtra("idoutlet");
        idoutlet.setText(kiriman);
        String kiriman2 = i.getStringExtra("namaoutlet");
        namaoutlet.setText(kiriman2);
        String kiriman3 = i.getStringExtra("namasales");
        namasales.setText(kiriman3);
        String kiriman4 = i.getStringExtra("tanggal");
        tanggal.setText(kiriman4);
        String kiriman5 = i.getStringExtra("namaitem");
        namaitem.setText(kiriman5);
        String kiriman6 = i.getStringExtra("gudang");
        gudang.setText(kiriman6);
        String kiriman7 = i.getStringExtra("qty");
        //hitung.setText(kiriman7);
        hasil.setText(kiriman7);


        list();



        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //hitungHasil();
                //save();
                //delete();

                hitungsisahiccid();
                int nilai1=Integer.valueOf(hasiljumlahsisah.getText().toString());
                //int nilai2=Integer.valueOf(hasiljumlahsisah.getText().toString());

                if (nilai1<0) {                    //1

                    Toast.makeText(getApplicationContext(), "Jumlah iccid lebih dari qty penjualan", Toast.LENGTH_LONG).show();
                }else if (noseri1.getText().toString().length() == 0) {                    //1

                    Toast.makeText(getApplicationContext(), "Kolom masih kosong", Toast.LENGTH_LONG).show();
                }else if (noseri2.getText().toString().length() == 0) {                    //1

                    Toast.makeText(getApplicationContext(), "Kolom masih kosong", Toast.LENGTH_LONG).show();
                }else if (jumlahiccid.getText().toString().equals("0")) {                    //1

                    Toast.makeText(getApplicationContext(), "Data kosong", Toast.LENGTH_LONG).show();
                }else if (noseri1.getText().toString().length() < 12) {                    //1

                    Toast.makeText(getApplicationContext(), "No. Seri Pertama Kurang", Toast.LENGTH_LONG).show();
                }else if (noseri1.getText().toString().length() == 16) {                    //1

                    Toast.makeText(getApplicationContext(), "No. Seri Pertama Kurang", Toast.LENGTH_LONG).show();
                }else if (noseri1.getText().toString().length() == 15) {                    //1

                    Toast.makeText(getApplicationContext(), "No. Seri Pertama Kurang", Toast.LENGTH_LONG).show();
                }else if (noseri1.getText().toString().length() == 14) {                    //1

                    Toast.makeText(getApplicationContext(), "No. Seri Pertama Kurang", Toast.LENGTH_LONG).show();
                }else if (noseri1.getText().toString().length() == 13) {                    //1

                    Toast.makeText(getApplicationContext(), "No. Seri Pertama Kurang", Toast.LENGTH_LONG).show();
                }else if (noseri2.getText().toString().length() < 12) {                    //1

                    Toast.makeText(getApplicationContext(), "No. Seri Kedua Kurang", Toast.LENGTH_LONG).show();
                }else if (noseri2.getText().toString().length() == 16) {                    //1

                    Toast.makeText(getApplicationContext(), "No. Seri Kedua Kurang", Toast.LENGTH_LONG).show();
                }else if (noseri2.getText().toString().length() == 15) {                    //1

                    Toast.makeText(getApplicationContext(), "No. Seri Kedua Kurang", Toast.LENGTH_LONG).show();
                }else if (noseri2.getText().toString().length() == 14) {                    //1

                    Toast.makeText(getApplicationContext(), "No. Seri Kedua Kurang", Toast.LENGTH_LONG).show();
                }else if (noseri2.getText().toString().length() == 13) {                    //1

                    Toast.makeText(getApplicationContext(), "No. Seri Kedua Kurang", Toast.LENGTH_LONG).show();
                }else if (noseri1.getText().toString().length() > 17) {                    //1

                    Toast.makeText(getApplicationContext(), "No. Seri Pertama Terlalu Banyak", Toast.LENGTH_LONG).show();
                }else if (noseri2.getText().toString().length() > 17) {                    //1

                    Toast.makeText(getApplicationContext(), "No. Seri Kedua Terlalu Banyak", Toast.LENGTH_LONG).show();
                }else if (noseri1.getText().toString().length() == 17) {                    //1

                    updateseri();
                }else if (noseri1.getText().toString().length() == 12) {                    //1

                    updateseri();
                }else {        //2
                    //updateseri();
                }



            }
        });

        cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //jumlahiccid();

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                noseri1.setText("");
                noseri2.setText("");
                //hitung.setText("0");


            }
        });


        hitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (noseri1.getText().toString().length() == 0) {                    //1

                    Toast.makeText(getApplicationContext(), "Isi kolom no seri pertama", Toast.LENGTH_LONG).show();
                }else
                    showAlertDialogButtonClicked();
                }


            });



//        noseri1.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void afterTextChanged(Editable s) {}
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start,
//                                      int before, int count) {
//
//                //list();
//                    //jumlahiccid();
//
//
//            }
//        });


        hitung.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0){
                    //jumlahiccid();
                }

            }
        });

//        noseri2.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void afterTextChanged(Editable s) {}
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start,
//                                      int before, int count) {
//                if(s.length() != 0)
//                    //list2();
//                jumlahiccid();
//            }
//        });


        hitung.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
               // if(s.length() != 0)
                    //list2();
                //jumlahiccid();
                hitungiccid();

            }
        });

//        Intent i = getIntent();
//        String kiriman = i.getStringExtra("jumlahangka");
//        hitung.setText(kiriman);


    }

    private final Runnable m_Runnable = new Runnable() {
        public void run() {
            //Toast.makeText(AbsendanIzin.this, "", Toast.LENGTH_SHORT).show();
            noseri1 = (EditText) findViewById(R.id.noseri1);
            hitung = (EditText) findViewById(R.id.hitung);
            noseri2 = (EditText) findViewById(R.id.noseri2);
            hasil = (EditText) findViewById(R.id.hasil);
            jumlahiccid = (TextView) findViewById(R.id.jumlahiccid);
            hasiljumlahsisah = (EditText) findViewById(R.id.hasiljumlahsisah);

            btnupdate = (Button) findViewById(R.id.btnupdate);


            idoutlet = (TextView) findViewById(R.id.idoutlet);
            namaoutlet = (TextView) findViewById(R.id.namaoutlet);
            namasales = (TextView) findViewById(R.id.namasales);
            tanggal = (TextView) findViewById(R.id.tanggal);
            namaitem = (TextView) findViewById(R.id.namaitem);
            gudang = (TextView) findViewById(R.id.gudang);



            listdataoutlet1 = (ListView) findViewById(R.id.listdataoutlet);

            //jumlahiccid();

            Iccid.this.mHandler.postDelayed(m_Runnable, 1000);
        }

    };

    private void list(){
        pDialog.setMessage("Mencari Data");
        showDialog();
        //swipe_refresh.setRefreshing(true);
        aruskas.clear();
        listdataoutlet1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "dataiccid.php" )
                .addBodyParameter("noseri1", noseri1.getText().toString())
                .addBodyParameter("noseri2", noseri2.getText().toString())
                .addBodyParameter("namasales", namasales.getText().toString())
                .addBodyParameter("namaitem", namaitem.getText().toString())
                .addBodyParameter("gudang", gudang.getText().toString())
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
                                map.put("sn",         responses.optString("sn"));

                                //total += Integer.parseInt(responses.getString("harga"))* Integer.parseInt(responses.getString("qty"));
                                //map.put("tanggal",      responses.optString("tanggal"));

                                aruskas.add(map);
                                //bayarList.add(item);
                                //jumlahiccid();
                            }

                            Adapter();
                            hideDialog();

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



    private void list2(){

        pDialog.setMessage("Mencari Data");
        showDialog();
        //swipe_refresh.setRefreshing(true);
        aruskas.clear();
        listdataoutlet1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "dataiccid2.php" )
                .addBodyParameter("noseri1", noseri1.getText().toString())
                .addBodyParameter("noseri2", noseri2.getText().toString())
                .addBodyParameter("namasales", namasales.getText().toString())
                .addBodyParameter("namaitem", namaitem.getText().toString())
                .addBodyParameter("gudang", gudang.getText().toString())
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
                                map.put("sn",         responses.optString("sn"));

                                //total += Integer.parseInt(responses.getString("harga"))* Integer.parseInt(responses.getString("qty"));
                                //map.put("tanggal",      responses.optString("tanggal"));

                                aruskas.add(map);
                                //bayarList.add(item);
                            }

                            Adapter();
                            hideDialog();

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

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.listiccid,
                new String[] {"sn"},
                new int[] {R.id.listsn});

        listdataoutlet1.setAdapter(simpleAdapter);

        listdataoutlet1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //no    = ((TextView) view.findViewById(R.id.no)).getText().toString();
                snlist    = ((TextView) view.findViewById(R.id.listsn)).getText().toString();

                //int item1 = Integer.parseInt(noseri1.getText().toString());
                //int item2 = Integer.parseInt(snlist.getText().toString());
                noseri1.setText(snlist);
                hitung.setText("1");
                jumlahiccid.setText("1");



//                String a = namaoutlet1.getText().toString();
//
//                Intent i = new Intent(getApplicationContext(), MainActivity2.class);
//                i.putExtra("namaoutlet",""+a+"");
//                startActivity(i);


                //tanggal        = ((TextView) view.findViewById(R.id.tanggal)).getText().toString();
                //ListMenu();
            }
        });
        //swipe_refresh.setRefreshing(false);

}

    private void updateseri() {
        pDialog.setMessage("Update Process...");
        showDialog();
        pDialog.setCanceledOnTouchOutside(false);
        AndroidNetworking.post(Config.host + "updateperdanaiccid.php")
                .addBodyParameter("noseri1", noseri1.getText().toString())
                .addBodyParameter("noseri2", noseri2.getText().toString())

                .addBodyParameter("tanggaljual", tanggal.getText().toString())
                .addBodyParameter("idoutlet", idoutlet.getText().toString())
                .addBodyParameter("namaoutlet", namaoutlet.getText().toString())
                //.addBodyParameter("status", status.getText().toString())
//                .addBodyParameter("qty", qtybarang1.getText().toString())
//                .addBodyParameter("harga", hargabarang1.getText().toString())
//                .addBodyParameter("total", total1.getText().toString())
//                .addBodyParameter("keterangan", keterangan1.getText().toString())
//                .addBodyParameter("jam", jaminput1.getText().toString())
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
                            Toast.makeText(getApplicationContext(), "INPUT NO SERI BERHASIL",
                                    Toast.LENGTH_LONG).show();

                            hitungsisahiccid2();
                            jumlahiccid.setText("0");
                            noseri1.setText("");
                            noseri2.setText("");
                            //hitung.setText("0");
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


    private void jumlahiccid() {

        AndroidNetworking.post(Config.host + "jumlahiccid.php")
                .addBodyParameter("noseri1", noseri1.getText().toString())
                .addBodyParameter("noseri2", noseri2.getText().toString())
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
                        //namabarang1.setText((response.optString("item")));
                        jumlahiccid.setText((response.optString("sn")));
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


//    private void showCustomDialog() {
//        final Dialog dialog = new Dialog(this);
//        //Mengeset judul dialog
//        dialog.setTitle("ADD JUMLAH");
//
//        //Mengeset layout
//        dialog.setContentView(R.layout.numeric);
//
//        //Membuat agar dialog tidak hilang saat di click di area luar dialog
//        dialog.setCanceledOnTouchOutside(false);
//
//        //Membuat dialog agar berukuran responsive
//        DisplayMetrics metrics = getResources().getDisplayMetrics();
//        int width = metrics.widthPixels;
//        dialog.getWindow().setLayout((6 * width) / 7, LinearLayout.LayoutParams.WRAP_CONTENT);
//
//        Button cancelButton = (Button) dialog.findViewById(R.id.button_cancel);
//        Button saveButton = (Button) dialog.findViewById(R.id.button_save);
//        EditText jumlahangka = (EditText) dialog.findViewById(R.id.jumlahangka);
//
//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(jumlahangka.getText().toString().length()==0) {                    //1
//                    //jika form Email belum di isi / masih kosong
//                    jumlahangka.setError("Harus diisi");
//                }else {
//
//                    String a = jumlahangka.getText().toString();
//
//                    Intent i = new Intent(getApplicationContext(), Iccid.class);
//                    i.putExtra("jumlahangka",""+a+"");
//                    startActivity(i);
//                    return;
//                }
//            }
//        });
//
//        cancelButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//
//        //Menampilkan custom dialog
//        dialog.show();
//
//    }


    public void showAlertDialogButtonClicked() {

        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Name");

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.numeric, null);
        builder.setView(customLayout);

        // add a button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText jumlahangka = customLayout.findViewById(R.id.jumlahangka);
                if(jumlahangka.getText().toString().length()==0) {                    //1
                    //jika form Email belum di isi / masih kosong
                    jumlahangka.setError("Harus diisi");
                }else {



                    hitung.setText(jumlahangka.getText().toString());
                    //jumlahiccid();
                    //list2();
                    jumlahiccid.setText(jumlahangka.getText().toString());

                }


                // send data from the AlertDialog to the Activity


            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // do something with the data coming from the AlertDialog
    private void sendDialogDataToActivity(String data) {
        //Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
        //hitung.setText(this, data);
    }



    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(R.string.app_name)
                //.setMessage("Kamu yakin ingin keluar?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    EditText hasil = findViewById(R.id.hasil);
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int nilai1=Integer.valueOf(hasil.getText().toString());
                        if (nilai1>0) {                    //1

                            Toast.makeText(getApplicationContext(), "No seri belum selesai semua", Toast.LENGTH_LONG).show();
                        }else {        //2
                            finish();
                        }


                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    private void hitungiccid() {
        long item1 = Long.parseLong(noseri1.getText().toString());
        long item2 = Long.parseLong(hitung.getText().toString());
        long hasilitem1 = item1 + item2 - 1;
        noseri2.setText(String.valueOf(hasilitem1));
    }

    private void hitungsisahiccid() {
        int item1 = Integer.parseInt(hasil.getText().toString());
        int item2 = Integer.parseInt(jumlahiccid.getText().toString());
        int hasilitem1 = item1 - item2;
        hasiljumlahsisah.setText(String.valueOf(hasilitem1));
    }

    private void hitungsisahiccid2() {
        int item1 = Integer.parseInt(hasil.getText().toString());
        int item2 = Integer.parseInt(jumlahiccid.getText().toString());
        int hasilitem1 = item1 - item2;
        hasil.setText(String.valueOf(hasilitem1));
    }

}
