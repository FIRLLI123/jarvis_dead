package com.example.barcode;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.example.barcode.helper.Config;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // View Object
    private Button btnkeLogin, btndatastudio, newoutlet1, telegram1, sum2, km2, cari1, hargaperdana, hargavoucher;
    private TextView idoutlet1, namaoutlet1, namasales1, tanggalabsen1, jamabsen1, namasalesbackup2, tanggal2, jam2, outlettutup1, sum1;
private LinearLayout outlettutup2, newoutlet2, klikdisini1, buttonScan, datakunjungan;
    private ProgressDialog pDialog;

    private Context context;

    TextView validasipass1,sls1, namasalesforward, namasalesforward2;

    EditText namasalesbackup1, caripassword1;

    ImageView imgdatakunjungan;
    //TextView , km1;

    // dimana y mas tempatnya?

    private static final String[] countries = new String[]{
            "ANWAR", "YUSUF MAULANA", "AKMAL MAULANA", "ARDIANSYAH", "M ZAIRIN",
            "ROHMAT", "M SANUSI", "SARYONO", "KRISTIAN", "RAHMATULLAH", "SULAIMAN",
            "ADITYA LUTVI", "SAEFUNAJIB", "ASEP NURKOMARUDIN", "SYARIF HIDAYAT", "ANCHE ARIS SUGIANTO",
            "ALEX SURYA PUTRA", "MUHAMMAD ZIAD", "RIVAI CAHYA NUGRAHA", "ABDUL ROZAK", "IBNU HARWOTO",
            "EKO SURYANTO", "RAMADHANI NUGROHO","RAHADIAN PUTRA", "FIQIH ABDULLAH", "SUHENDRA", "DIMAS ARI WICAKSONO", "RENDI H", "AANG PURNAMA", "INDRA RUKMANA",
            "OKA KAMARULSYAH", "M HANIF","Rangga","Rangga Tri Raeros","Chalid","FIRLLI"
    };

    //qr code scanner object
    private IntentIntegrator intentIntegrator;
    AutoCompleteTextView edittext991;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        context = MainActivity.this;
        pDialog = new ProgressDialog(context);


        klikdisini1 = (LinearLayout) findViewById(R.id.klikdisini);

        imgdatakunjungan = (ImageView) findViewById(R.id.imgdatakunjungan);

        datakunjungan = (LinearLayout) findViewById(R.id.datakunjungan);
        validasipass1 = (TextView) findViewById(R.id.validasipass);
        sls1 = (TextView) findViewById(R.id.sls);

        edittext991 = (AutoCompleteTextView) findViewById(R.id.edittext99);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
        edittext991.setAdapter(adapter);

        caripassword1 = (EditText) findViewById(R.id.caripassword);

        namasalesforward = (TextView) findViewById(R.id.namasalesforward);
        namasalesforward2 = (TextView) findViewById(R.id.namasalesforward2);

        // initialize object
        buttonScan = (LinearLayout) findViewById(R.id.buttonScan);
        btnkeLogin = (Button) findViewById(R.id.btnkelogin);
        btndatastudio = (Button) findViewById(R.id.btndatastudio);
        //newoutlet1 = (Button) findViewById(R.id.newoutlet);
        newoutlet2 = (LinearLayout) findViewById(R.id.newoutlet);
        telegram1 = (Button) findViewById(R.id.telegram);
        hargaperdana = (Button) findViewById(R.id.hargaperdana);
        hargavoucher = (Button) findViewById(R.id.hargavoucher);
        //sum1 = (TextView) findViewById(R.id.sum);

        sum2 = (Button) findViewById(R.id.sum);

        cari1 = (Button) findViewById(R.id.cari);

        idoutlet1 = (TextView) findViewById(R.id.idoutlet);
        namaoutlet1 = (TextView) findViewById(R.id.namaoutlet);
        namasales1 = (TextView) findViewById(R.id.namasales);
        namasalesbackup1 = (EditText) findViewById(R.id.namasalesbackup);
        tanggalabsen1 = (TextView) findViewById(R.id.tanggalabsen);
        jamabsen1 = (TextView) findViewById(R.id.jamabsen);
        //outlettutup1 = (TextView) findViewById(R.id.outlettutup);

        //outlettutup2 = (LinearLayout) findViewById(R.id.klikdisini);

        //km1 = (TextView) findViewById(R.id.km);

        km2 = (Button) findViewById(R.id.km);




        namasalesbackup2 = (TextView) findViewById(R.id.namasalesbackupkunjungan); //namaoutlet
        tanggal2 = (TextView) findViewById(R.id.tanggalkunjungan); //namaoutlet
        jam2 = (TextView) findViewById(R.id.jamkunjungan); //namaoutlet

        Intent i = getIntent();
        String kiriman = i.getStringExtra("namaoutlet");
        namaoutlet1.setText(kiriman);
        String kiriman2 = i.getStringExtra("idoutlet");
        idoutlet1.setText(kiriman2);
        String kiriman3 = i.getStringExtra("namasales");
        namasales1.setText(kiriman3);
        String kiriman4 = i.getStringExtra("namasales");
        edittext991.setText(kiriman4);
        String kiriman5 = i.getStringExtra("namasales");
        namasalesbackup1.setText(kiriman5);

        String kiriman6 = i.getStringExtra("nama");
        namasalesforward.setText(kiriman6);
        String kiriman7 = i.getStringExtra("posisi");
        namasalesforward2.setText(kiriman7);

        kunjunganterakhir();

        // attaching onclickListener
        buttonScan.setOnClickListener(this);


        btnkeLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                cariid();

                if (idoutlet1.getText().toString().length() == 0) {                    //1
                    //jika form Email belum di isi / masih kosong
                    //p0k1_i.setError("Kilometer harus diisi");
                    Toast.makeText(getApplicationContext(), "Silahkan Scan Terlebih Dahulu", Toast.LENGTH_LONG).show();
                }else if(namasalesbackup1.getText().toString().length() == 0) {
                                    //1
                    //jika form Email belum di isi / masih kosong
                    //p0k1_i.setError("Kilometer harus diisi");
                    namasalesbackup1.setError("harus diisi");Toast.makeText(getApplicationContext(), "Kolom Sales Backup Tidak Boleh Kosong", Toast.LENGTH_LONG).show();

                }else if(edittext991.getText().toString().length() == 0) {
                    //1
                    //jika form Email belum di isi / masih kosong
                    //p0k1_i.setError("Kilometer harus diisi");
                    edittext991.setError("harus diisi");Toast.makeText(getApplicationContext(), "Kolom Tidak Boleh Kosong", Toast.LENGTH_LONG).show();

                }else if(edittext991.getText().toString().equals("null")) {
                    //1
                    //jika form Email belum di isi / masih kosong
                    //p0k1_i.setError("Kilometer harus diisi");
                    edittext991.setError("harus diisi");Toast.makeText(getApplicationContext(), "Koneksi tidak ada", Toast.LENGTH_LONG).show();

                }else if(caripassword1.getText().toString().length() == 0) {
                    //1
                    //jika form Email belum di isi / masih kosong
                    //p0k1_i.setError("Kilometer harus diisi");
                    caripassword1.setError("harus diisi");Toast.makeText(getApplicationContext(), "Silahkan Isi Password", Toast.LENGTH_LONG).show();

                } else {
                    cariid();
                    save();
loginn();
//                    String a = edittext991.getText().toString();
//                    String b = idoutlet1.getText().toString();
//                    String c = namaoutlet1.getText().toString();
//                    Intent i = new Intent(getApplicationContext(), Login.class);
//                    i.putExtra("namasales", "" + a + "");
//                    i.putExtra("idoutlet", "" + b + "");
//                    i.putExtra("namaoutlet", "" + c + "");
//                    startActivity(i);
                }
            }
        });


        if (namasalesforward.getText().toString().equals("LETYA PUTRI ERIYASTA")){
            //1
            hargaperdana.setVisibility(View.VISIBLE);
            hargavoucher.setVisibility(View.VISIBLE);

            //Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
        }else{

            hargaperdana.setVisibility(View.GONE);
            hargavoucher.setVisibility(View.GONE);
        }



        tanggalabsen1.setText(getCurrentDate());
        jamabsen1.setText(jamotomatis());
        //edittext();




        klikdisini1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                showCustomDialog();

            }

        });


        cari1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                cariid();
                String aoao = edittext991.getText().toString();
                validasipass1.setText(aoao);

            }

        });

        km2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                Intent i = new Intent(getApplicationContext(), Kilometer.class);
                startActivity(i);

            }

        });

        sum2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                String b = namasalesforward2.getText().toString();
                Intent i = new Intent(getApplicationContext(), MenuSummary.class);

                i.putExtra("posisi",""+b+"");
                startActivity(i);
            }

        });


        btndatastudio.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                Intent i = new Intent(getApplicationContext(), Datastudio.class);
                startActivity(i);

            }

        });

        newoutlet2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                Intent i = new Intent(getApplicationContext(), DataOutlet.class);
                startActivity(i);



            }

        });

        hargaperdana.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                Intent i = new Intent(getApplicationContext(), HargaPerdana.class);
                startActivity(i);



            }

        });

        hargavoucher.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                Intent i = new Intent(getApplicationContext(), HargaVoucher.class);
                startActivity(i);



            }

        });

        telegram1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                if (idoutlet1.getText().toString().length() == 0) {                    //1
                    //jika form Email belum di isi / masih kosong
                    //p0k1_i.setError("Kilometer harus diisi");
                    Toast.makeText(getApplicationContext(), "Silahkan Scan Terlebih Dahulu", Toast.LENGTH_LONG).show();
                }else {
                    copyToClipBoard();

                    String pesan = idoutlet1.getText().toString();
                    //Telegram
                    String pesanketelegram =
                            "https://t.me/Jarvisrmkbot";



                    Intent i = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(pesanketelegram));
                    startActivity(i);

                    final Toast tag = Toast.makeText(getBaseContext(), "SILAHKAN DI PASTEE",Toast.LENGTH_SHORT);

                    tag.show();

                    new CountDownTimer(9000, 1000)
                    {

                        public void onTick(long millisUntilFinished) {tag.show();}
                        public void onFinish() {tag.show();}

                    }.start();

                }
                }

        });


    }

    private void cariid() {

        AndroidNetworking.post(Config.host + "cariuser.php")
                .addBodyParameter("password", caripassword1.getText().toString())
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
                        edittext991.setText((response.optString("nama")));
                        //hargabarang1.setText((response.optString("harga")));
                        //actualhero1.setText((response.optString("actual")));
                        //achhero1.setText((response.optString("ach")));
                        //gaphero1.setText((response.optString("gap")));
                        //bulan1.setText((response.optString("bulan")));
                        String aoao = edittext991.getText().toString();
                        validasipass1.setText("Akan di Kunjungin Oleh : "+aoao);
                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }

    private void loginn() {
        cariid();
        //Getting values from edit texts
        final String username = edittext991.getText().toString().trim();
        final String password = caripassword1.getText().toString().trim();
        pDialog.setMessage("Login Process...");
        showDialog();
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppVar.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //If we are getting success from server
                        if (response.contains(AppVar.LOGIN_SUCCESS)) {
                            hideDialog();
                            gotoCourseActivity();

                        } else {
                            hideDialog();
                            //Displaying an error message on toast
                            Toast.makeText(context, "Invalid username or password / Silahkan klik Cek Password", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        hideDialog();
                        Toast.makeText(context, "The server unreachable", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put(AppVar.KEY_EMAIL, username);
                params.put(AppVar.KEY_PASSWORD, password);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        Volley.newRequestQueue(this).add(stringRequest);
    }




    private void copyToClipBoard() {
        String getTextFromtext1 = idoutlet1.getText().toString();
        ClipboardManager clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("text di copy", getTextFromtext1);
        clipboardManager.setPrimaryClip(clipData);
        //Toast.makeText(this, "SILAHKAN DI PASTE", Toast.LENGTH_SHORT).show();

        final Toast tag = Toast.makeText(getBaseContext(), "SILAHKAN DI PASTEE",Toast.LENGTH_SHORT);

        tag.show();

        new CountDownTimer(9000, 1000)
        {

            public void onTick(long millisUntilFinished) {tag.show();}
            public void onFinish() {tag.show();}

        }.start();


    }


//dialog outlet tutup
    private void showCustomDialog() {
        final Dialog dialog = new Dialog(this);
        //Mengeset judul dialog
        dialog.setTitle("Add Outlet");

        //Mengeset layout
        dialog.setContentView(R.layout.outlettutup);

        //Membuat agar dialog tidak hilang saat di click di area luar dialog
        dialog.setCanceledOnTouchOutside(false);

        //Membuat dialog agar berukuran responsive
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        dialog.getWindow().setLayout((6 * width) / 7, LinearLayout.LayoutParams.WRAP_CONTENT);

        Button cancelButton = (Button) dialog.findViewById(R.id.button_cancel);
        Button saveButton = (Button) dialog.findViewById(R.id.button_save);
        Button caritutup1 = (Button) dialog.findViewById(R.id.caritutup);
        EditText idoutlettutup1 = (EditText) dialog.findViewById(R.id.idoutlettutup);
        TextView namaoutlettutup1 = (TextView) dialog.findViewById(R.id.namaoutlettutup);
        TextView namasalestutup1 = (TextView) dialog.findViewById(R.id.namasalestutup);
        TextView tanggaltutup1 = (TextView) dialog.findViewById(R.id.tanggaltutup);



        tanggaltutup1.setText(getCurrentDate());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idoutlettutup1.getText().toString().length()==0) {                    //1
                    //jika form Email belum di isi / masih kosong
                    idoutlettutup1.setError("ID Outlet harus diisi");
                }else {
                        pDialog.setMessage("Outlet Tutup Di Process...");
                        showDialog();
                        AndroidNetworking.post(Config.host + "outlettutup.php")
                                .addBodyParameter("idoutlet", idoutlettutup1.getText().toString())
                                .addBodyParameter("namaoutlet", namaoutlettutup1.getText().toString())
                                .addBodyParameter("namasales", namasalestutup1.getText().toString())
                                .addBodyParameter("tanggal", tanggaltutup1.getText().toString())
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
                                            Toast.makeText(getApplicationContext(), "Berhasil Simpan",
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

                        //dialog.dismiss();
                    }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        caritutup1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idoutlettutup1.getText().toString().length() == 0) {                    //1
                    //jika form Email belum di isi / masih kosong
                    idoutlettutup1.setError("ID Outlet harus diisi");
                } else {
                    AndroidNetworking.post(Config.host + "dataoutlet.php")
                            .addBodyParameter("idoutlet", idoutlettutup1.getText().toString())
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
                                    namaoutlettutup1.setText((response.optString("namaoutlet")));
                                    namasalestutup1.setText((response.optString("namasales")));
                                    //.setText((response.optString("jam")));


                                }

                                @Override
                                public void onError(ANError error) {

                                }
                            });


                    //dialog.dismiss();
                }
            }
        });


        //Menampilkan custom dialog
        dialog.show();

    }



    // Mendapatkan hasil scan


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null){
            if (result.getContents() == null){
                Toast.makeText(this, "Hasil tidak ditemukan", Toast.LENGTH_SHORT).show();
            }else{
                // jika qrcode berisi data
                try{
                    // converting the data json
                    JSONObject object = new JSONObject(result.getContents());
                    // atur nilai ke textviews
                    idoutlet1.setText(object.getString("idoutlet"));
                    namaoutlet1.setText(object.getString("namaoutlet"));
                    namasales1.setText(object.getString("namasales"));
                    namasalesbackup1.setText(object.getString("namasalesbackup"));
                    edittext991.setText(object.getString("namasalesbackup"));
                    sls1.setText(object.getString("namasalesbackup"));

                    imgdatakunjungan.setVisibility(View.GONE);
                    datakunjungan.setVisibility(View.VISIBLE);
                    kunjunganterakhir();

                }catch (JSONException e){
                    e.printStackTrace();
                    // jika format encoded tidak sesuai maka hasil
                    // ditampilkan ke toast
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
                }


            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View v) {
        // inisialisasi IntentIntegrator(scanQR)
        intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.initiateScan();
    }


    private void save() {
        pDialog.setMessage("Absen Process...");
        showDialog();
        AndroidNetworking.post(Config.host + "absen.php")
                .addBodyParameter("idoutlet", idoutlet1.getText().toString())
                .addBodyParameter("namaoutlet", namaoutlet1.getText().toString())
                .addBodyParameter("namasalesbackup", edittext991.getText().toString())
                .addBodyParameter("tanggal", tanggalabsen1.getText().toString())
                .addBodyParameter("jam", jamabsen1.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()

                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response

                        Log.d("response", response.toString());

                        if (response.optString("response").toString().equals("success")) {
//                            hideDialog();
//                            //gotoCourseActivity();
//                            Toast.makeText(getApplicationContext(), "Berhasil Absen",
//                                    Toast.LENGTH_LONG).show();


                        } else {
//                            hideDialog();
//                            Toast.makeText(getApplicationContext(), "failed",
//                                    Toast.LENGTH_LONG).show();
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
    private void gotoCourseActivity() {
        String a = edittext991.getText().toString();
        String b = idoutlet1.getText().toString();
        String c = namaoutlet1.getText().toString();


        String d = namasalesbackup2.getText().toString();
        String e = tanggal2.getText().toString();
        String f = jam2.getText().toString();
        Intent intent = new Intent(context, TampilanOutlet.class);
        //Intent i = new Intent(getApplicationContext(), Menuutamanew.class);
        intent.putExtra("namasales",""+a+"");
        intent.putExtra("idoutlet",""+b+"");
        intent.putExtra("namaoutlet",""+c+"");

        intent.putExtra("namasaleskunjungan",""+d+"");
        intent.putExtra("tanggalkunjungan",""+e+"");
        intent.putExtra("jamkunjungan",""+f+"");

        //startActivity(i);
        startActivity(intent);
        finish();


    }


    public String jamotomatis(){
        Calendar c1 = Calendar.getInstance();
        //SimpleDateFormat sdf1 = new SimpleDateFormat("d/M/yyyy h:m:s a");
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
        String strdate1 = sdf1.format(c1.getTime());
        return strdate1;



    }

    public String getCurrentDate(){
        final Calendar c = Calendar.getInstance();
        int year, month, day;
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DATE);
        SimpleDateFormat contoh1 = new SimpleDateFormat("EEEE");

        String hariotomatis = contoh1.format(c.getTime());

        //return day +"/" + (month+1) + "/" + year;
        //return (month+1) +"/" + day + "/" + year;
        return year +"/" + (month+1) + "/" + day;
    }



    private void kunjunganterakhir() {

        AndroidNetworking.post(Config.host + "kunjunganterakhir.php")
                .addBodyParameter("idoutlet", idoutlet1.getText().toString())
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






}