package com.example.barcode;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
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
import android.widget.ImageView;
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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class InputPERDANAbyu extends AppCompatActivity implements View.OnClickListener {

    TextView idoutletinputperdana1;
    TextView tanggalinputperdana1;
    TextView namaoutletinputperdana1;
    TextView namasalesinputperdana1, total1, jaminput1, stok1, gudang1, idbarangkeluar1, totalformatrp1, subtotal1, subtotal21;
    TextView statusiccid;

    TextView id_spesial, status_spesial;

    EditText keterangan1, pembayaran1, sisahstok1, validasijumlahkan1;
    EditText validasipenjualan1;
    private ProgressDialog pDialog;
    private Context context;

    TextView lihatid1;

    ListView listinputperdana1;

    ImageView ceklis;

    Handler mHandler;

    //SwipeRefreshLayout swipe_refresh;

    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();

    //TextView tanggal1, namasales1;

    public static String LINK, idlistperdana, namalistperdana, qtylistperdana, hargalistperdana, totallistperdana, listketerangan, listpembayaran;
    public static boolean filter;



    EditText idbarang1, namabarang1, hargabarang1, qtybarang1, iddelete1, cashh1, transfer1;
    Button cariid1;
    Button btntotal1, btninput1, btndelete1, btnrefresh1, btnupdate1, btncetak1, lihatstok1;
    Button teshapus1, pilihnoseri;
    RadioButton cash, tempo;

    Button pilihnomor;

    private static final String[] countries = new String[]{
            "ota", "p16gb", "p2.5gb", "p23gb", "p2gb",
            "p35c", "p35t", "p3c", "p3t", "p51gb",
            "p6gb", "p70gb", "p7c", "p7t", "p9gb"
    };


    NumberFormat formatter = new DecimalFormat("#,###,###,###");

    int total = 0;

    private static final int REQUEST_SELECT_BARANG = 1;

    String query_kas, query_total;
    AutoCompleteTextView edittext991;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_perdanabyu);

        this.mHandler = new Handler();
        m_Runnable.run();

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
        listpembayaran = "";
        //query_kas = ""; query_total = "";
        filter = false;



        listinputperdana1        = (ListView) findViewById(R.id.listinputperdana);
        idoutletinputperdana1 = (TextView) findViewById(R.id.idoutletinputperdana);
        tanggalinputperdana1 = (TextView) findViewById(R.id.tanggalinputperdana);
        namaoutletinputperdana1 = (TextView) findViewById(R.id.namaoutletinputperdana);
        namasalesinputperdana1 = (TextView) findViewById(R.id.namasalesinputperdana);
        gudang1 = (TextView) findViewById(R.id.gudang);
        idbarangkeluar1 = (TextView) findViewById(R.id.idstok);
        //idbarangkeluar1 = (TextView) findViewById(R.id.idbarangkeluar);


        id_spesial = (TextView) findViewById(R.id.id_spesial);
        status_spesial = (TextView) findViewById(R.id.status_spesial);

        stok1 = (TextView) findViewById(R.id.stok);

        total1 = (TextView) findViewById(R.id.total);
        subtotal1 = (TextView) findViewById(R.id.subtotal);
        subtotal21 = (TextView) findViewById(R.id.subtotal2);

        statusiccid = (TextView) findViewById(R.id.statusiccid);

        ceklis = (ImageView) findViewById(R.id.ceklis);



        totalformatrp1 = (TextView) findViewById(R.id.totalformatrp);

        keterangan1 = (EditText) findViewById(R.id.keterangan2);
        validasijumlahkan1 = (EditText) findViewById(R.id.validasijumlahkan);

        cashh1 = (EditText) findViewById(R.id.cashh);
        transfer1 = (EditText) findViewById(R.id.transfer);


        jaminput1 = (TextView) findViewById(R.id.jaminput);

        context = InputPERDANAbyu.this;
        pDialog = new ProgressDialog(context);


        //lihatid1 = (TextView) findViewById(R.id.lihatid);

        cariid1 = (Button) findViewById(R.id.cariid);
        idbarang1 = (EditText) findViewById(R.id.idbarang);
        namabarang1 = (EditText) findViewById(R.id.namabarang);
        qtybarang1 = (EditText) findViewById(R.id.qtybarang);
        hargabarang1 = (EditText) findViewById(R.id.hargabarang);
        iddelete1 = (EditText) findViewById(R.id.iddelete);

        sisahstok1 = (EditText) findViewById(R.id.sisahstok);

        pembayaran1 = (EditText) findViewById(R.id.pembayaran);
        validasipenjualan1 = (EditText) findViewById(R.id.validasicaripenjualan);



        btntotal1 = (Button) findViewById(R.id.btntotal); //total
        btninput1 = (Button) findViewById(R.id.btninput); //input
        btndelete1 = (Button) findViewById(R.id.btndelete); //delete
        btnupdate1 = (Button) findViewById(R.id.btnupdate); //update
        btnrefresh1 = (Button) findViewById(R.id.btnrefresh); //refresh
        btncetak1 = (Button) findViewById(R.id.btncetak); //cetak
        lihatstok1 = (Button) findViewById(R.id.lihatstok); //cetak
        teshapus1 = (Button) findViewById(R.id.teshapus); //cetak

        pilihnoseri = (Button) findViewById(R.id.pilihnoseri); //cetak
        pilihnomor = (Button) findViewById(R.id.pilihnomor);


        Intent i = getIntent();
        String kiriman = i.getStringExtra("idoutlet");
        idoutletinputperdana1.setText(kiriman);
        String kiriman2 = i.getStringExtra("namaoutlet");
        namaoutletinputperdana1.setText(kiriman2);
        String kiriman3 = i.getStringExtra("namasales");
        namasalesinputperdana1.setText(kiriman3);



        tanggalinputperdana1.setText(getCurrentDate());
        jaminput1.setText(jamotomatis());

        teshapus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                caripenjualan();


            }
        });

        cariid1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = edittext991.getText().toString();
                idbarang1.setText(a);
                cariidharga();
                cariid();

                // Delay execution of cari_stok() for 1 second (1000 milliseconds)
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        cari_stok();
                    }
                }, 400);
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
                }else if (namabarang1.getText().toString().equals("null")) {        //2
                    //jika form Username belum di isi / masih kosong
                    namabarang1.setError("Item tidak tersedia");
                    Toast.makeText(getApplicationContext(), "Item tidak tersedia", Toast.LENGTH_LONG).show();
                }else if (pembayaran1.getText().toString().length() == 0) {        //2
                    //jika form Username belum di isi / masih kosong
                    //keterangan1.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if (namabarang1.getText().toString().equals("null")) {        //2
                    //jika form Username belum di isi / masih kosong
                    //keterangan1.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Item Tidak Tersedia di Gudang mu", Toast.LENGTH_LONG).show();
                }else if (stok1.getText().toString().equals("null")) {        //2
                    //jika form Username belum di isi / masih kosong
                    //keterangan1.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Item Tidak Tersedia di Gudang Mu", Toast.LENGTH_LONG).show();
                } else {

                    hitungHasil();
//                    inputgak();

                    inputdanjumlahkanaplikasi();

                }
            }
        });

        btntotal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nilai1=Integer.valueOf(sisahstok1.getText().toString());

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
                }else if (namabarang1.getText().toString().equals("null")) {        //2
                    //jika form Username belum di isi / masih kosong
                    namabarang1.setError("Item tidak tersedia");
                    Toast.makeText(getApplicationContext(), "Item tidak tersedia", Toast.LENGTH_LONG).show();
                }else if (pembayaran1.getText().toString().length() == 0) {        //2
                    //jika form Username belum di isi / masih kosong
                    //keterangan1.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Silahkan Pilih Pembayaran Terlebuh Dahulu", Toast.LENGTH_LONG).show();
                } else {

                    jumlahkanaplikasi();
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

                    hapusaplikasicari();

                    //cariidhapus();
                    //hitungstokjikahapus();
                    //updatestokjikahapus();
                    //delete();
                    //KasAdapter2();
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
                //update();

            }
        });


        btncetak1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String a = idoutletinputperdana1.getText().toString();
                String b = namaoutletinputperdana1.getText().toString();
                String c = namasalesinputperdana1.getText().toString();
                String d = tanggalinputperdana1.getText().toString();
                Intent i = new Intent(getApplicationContext(), Input_perdanaTransferbyu.class);
                i.putExtra("idoutlet",""+a+"");
                i.putExtra("namaoutlet",""+b+"");
                i.putExtra("namasales",""+c+"");
                i.putExtra("tanggal",""+d+"");
                startActivity(i);



            }
        });

        pilihnoseri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (namabarang1.getText().toString().length() == 0) {                    //1
                    //jika form Email belum di isi / masih kosong
                    //iddelete1.setError("");
                    Toast.makeText(getApplicationContext(), "Silahkan pilih item terlebih dahulu", Toast.LENGTH_LONG).show();
                }else if (namabarang1.getText().toString().equals("null")) {                    //1
                    //jika form Email belum di isi / masih kosong
                    //iddelete1.setError("");
                    Toast.makeText(getApplicationContext(), "Silahkan pilih item yang tersedia", Toast.LENGTH_LONG).show();
                }else if (qtybarang1.getText().toString().length() == 0) {                    //1
                    //jika form Email belum di isi / masih kosong
                    //iddelete1.setError("");
                    Toast.makeText(getApplicationContext(), "Silahkan inpu qty", Toast.LENGTH_LONG).show();
                }else if (statusiccid.getText().toString().equals("TERVERIFIKASI")) {                    //1
                    //jika form Email belum di isi / masih kosong
                    //iddelete1.setError("");
                    Toast.makeText(getApplicationContext(), "Iccid Sudah Terinput", Toast.LENGTH_LONG).show();
                }  else {

                    String a = idoutletinputperdana1.getText().toString();
                    String b = namaoutletinputperdana1.getText().toString();
                    String c = namasalesinputperdana1.getText().toString();
                    String d = tanggalinputperdana1.getText().toString();
                    String e = namabarang1.getText().toString();
                    String f = gudang1.getText().toString();
                    String g = qtybarang1.getText().toString();
                    Intent i = new Intent(getApplicationContext(), Iccid.class);
                    i.putExtra("idoutlet",""+a+"");
                    i.putExtra("namaoutlet",""+b+"");
                    i.putExtra("namasales",""+c+"");
                    i.putExtra("tanggal",""+d+"");
                    i.putExtra("namaitem",""+e+"");
                    i.putExtra("gudang",""+f+"");
                    i.putExtra("qty",""+g+"");
                    startActivity(i);

                    statusiccid.setText("TERVERIFIKASI");
                    ceklis.setVisibility(View.VISIBLE);

                    //cariidhapus();
                    //hitungstokjikahapus();
                    //updatestokjikahapus();
                    //delete();
                    //KasAdapter2();
                }



            }
        });


        lihatstok1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Ketika tombol untuk memilih barang di klik
                Intent intent = new Intent(getApplicationContext(), Item_Perdana_pilihan.class);
                String a = namasalesinputperdana1.getText().toString();
                String b = gudang1.getText().toString();
                intent.putExtra("namasales", "" + a + "");
                intent.putExtra("gudang", "" + b + "");
//                String b = bidang_.getText().toString();
//                intent.putExtra("bidang", "" + b + "");
                startActivityForResult(intent, REQUEST_SELECT_BARANG);

            }
        });

        pilihnomor.setEnabled(false);
        pilihnomor.setBackgroundColor(getResources().getColor(R.color.abumuda));

        pilihnomor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(namabarang1.getText().toString().equals("Perdana Spesial (Segel)")) {

                    // Ketika tombol untuk memilih barang di klik
                    Intent intent = new Intent(getApplicationContext(), Item_nomor_pilihan.class);
//                String a = namasalesinputperdana1.getText().toString();
//                String b = gudang1.getText().toString();
//                intent.putExtra("namasales", "" + a + "");
//                intent.putExtra("gudang", "" + b + "");
//                String b = bidang_.getText().toString();
//                intent.putExtra("bidang", "" + b + "");
                    startActivityForResult(intent, 11);
                }else{

                    pilihnomor.setEnabled(false);
                    pilihnomor.setBackgroundColor(getResources().getColor(R.color.abumuda));}
            }
        });






        //swipe_refresh   = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        KasAdapter2();


        cash = (RadioButton) findViewById(R.id.cash);
        tempo = (RadioButton) findViewById(R.id.tempo);

        cash.setOnClickListener(this);
        tempo.setOnClickListener(this);

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


        transfer1.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                cashh1.setText(addNumbers());
            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });

        subtotal21.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                cashh1.setText(addNumbers());

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);




        if (requestCode == REQUEST_SELECT_BARANG) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    String a1 = data.getStringExtra("id");
                    idbarang1.setText(a1);
                    edittext991.setText(a1);

                    String a3 = data.getStringExtra("namaitem");
                    namabarang1.setText(a3);

                    String a4 = data.getStringExtra("stok");
                    stok1.setText(a4);

                    String a5 = data.getStringExtra("harga");
                    hargabarang1.setText(a5);

                    // Lakukan sesuatu dengan selectedBarang
                }
            }else{


            }

        }else if (requestCode == 11) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    String a1 = data.getStringExtra("id");
                    id_spesial.setText(a1);


                    String a3 = data.getStringExtra("nomor");
                    keterangan1.setText(a3);

                    String a4 = data.getStringExtra("harga");
                    hargabarang1.setText(a4);

                    String a5 = data.getStringExtra("status");
                    status_spesial.setText(a5);

                    // Lakukan sesuatu dengan selectedBarang
                }
            }else{


            }

        }
    }


    private final Runnable m_Runnable = new Runnable() {
        public void run() {
            //Toast.makeText(AbsendanIzin.this, "", Toast.LENGTH_SHORT).show();

            pilihnomor = (Button) findViewById(R.id.pilihnomor);
            namabarang1 = (EditText) findViewById(R.id.namabarang);
            keterangan1 = (EditText) findViewById(R.id.keterangan2);

            if(namabarang1.getText().toString().equals("Perdana Spesial (Segel)")){

                pilihnomor.setEnabled(true);
                pilihnomor.setBackgroundColor(getResources().getColor(R.color.merah));
                keterangan1.setEnabled(false);

            }else{

                pilihnomor.setEnabled(false);
                pilihnomor.setBackgroundColor(getResources().getColor(R.color.abumuda));
                keterangan1.setEnabled(true);
            }



            InputPERDANAbyu.this.mHandler.postDelayed(m_Runnable, 2000);
        }

    };


    private String addNumbers() {
        int number1;
        int number2;
        if (transfer1.getText().toString() != "" && transfer1.getText().length() > 0) {
            number1 = Integer.parseInt(transfer1.getText().toString());
        } else {
            number1 = 0;
        }
        if (subtotal21.getText().toString() != "" && subtotal21.getText().length() > 0) {
            number2 = Integer.parseInt(subtotal21.getText().toString());
        } else {
            number2 = 0;
        }

        return Integer.toString(number2 - number1);
    }

    public void inputgak() {
        String namaoutlet = namaoutletinputperdana1.getText().toString();
        String namasalesalert = namasalesinputperdana1.getText().toString();
        String namaitemalert = namabarang1.getText().toString();
        String qtyalert = qtybarang1.getText().toString();
        String hargaalert = hargabarang1.getText().toString();
        String stokalert = stok1.getText().toString();
        String sisahstokalert = sisahstok1.getText().toString();
        String totalalert = totalformatrp1.getText().toString();

        //String a = validasib1.getText().toString();
        final Dialog dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setTitle("Hallo Team Reka Mitra");
        AlertDialog alertDialog;
        alertDialog = new AlertDialog.Builder(this)

                .setIcon(R.drawable.lihatdatamenu)
                .setTitle(R.string.app_name)
                .setMessage("Hallo "+namasalesalert+", Silahkan Di cek Kembali ya \n" +
                        "Nama Outlet : "+namaoutlet+"\nMembeli : "+namaitemalert+" "+qtyalert+" pcs\nDengan Harga : "+hargaalert+"\nStok Tersedia : "+stokalert+"\nDan Sisah Stok Menjadi : "+sisahstokalert+"\nTotal Keseluruhan Rp."+totalalert+",-")
                .setPositiveButton("OKE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        save();
                        KasAdapter2();



                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        idbarang1.setText("");
                        namabarang1.setText("");
                        stok1.setText("");
                        sisahstok1.setText("0");
                        pembayaran1.setText("");
                        qtybarang1.setText("");
                        hargabarang1.setText("");
                        //keterangan1.setText("");
                        total1.setText("0");
                        validasijumlahkan1.setText("");
                        KasAdapter2();
                        dialog.cancel();
                    }
                })
                .show();
        alertDialog.setCanceledOnTouchOutside(false);
    }


    public void jumlahkanaplikasi(){
//        pDialog.setMessage("TUNGGU SEBENTAR, SEDANG VERIFIKASI DATA");
//        showDialog();
        hitungHasil();
        hitungstok();
        int nilai1=Integer.valueOf(sisahstok1.getText().toString());
        new CountDownTimer(2000, 1000) {

            public void onTick(long millisUntilFinished) {
                pDialog.setMessage("TUNGGU SEBENTAR, SEDANG VERIFIKASI DATA :"+ millisUntilFinished / 1000);
                showDialog();
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                hideDialog();

                if (nilai1<0) {
//            buttonabsendatang1.setEnabled(false);
//            buttonabsendatang1.setBackgroundColor(getResources().getColor(R.color.abu));
                    validasijumlahkan1.setText("");
                    alertstok();
                    qtybarang1.setText("");

                    //1
                    //jika form Email belum di isi / masih kosong
                    //link1.setError("harus diisi");
                    // Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else {

                    validasijumlahkan1.setText("TERVALIDASI");

                }

                //mTextField.setText("done!");
            }
        }.start();

        //






    }


    public void inputdanjumlahkanaplikasi(){
//        pDialog.setMessage("TUNGGU SEBENTAR, SEDANG VERIFIKASI DATA");
//        showDialog();
        //jumlahkanaplikasi();

        hitungHasil();
        hitungstok();
        int nilai1=Integer.valueOf(sisahstok1.getText().toString());
        new CountDownTimer(1000, 1000) {

            public void onTick(long millisUntilFinished) {
                pDialog.setMessage("TUNGGU SEBENTAR, SEDANG VERIFIKASI DATA :"+ millisUntilFinished / 1000);
                showDialog();
                pDialog.setCanceledOnTouchOutside(false);
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                hideDialog();

                if (nilai1<0) {
//            buttonabsendatang1.setEnabled(false);
//            buttonabsendatang1.setBackgroundColor(getResources().getColor(R.color.abu));
                    validasijumlahkan1.setText("");
                    alertstok();
                    qtybarang1.setText("");

                    //1
                    //jika form Email belum di isi / masih kosong
                    //link1.setError("harus diisi");
                    // Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else {

                    inputgak();

                }



                //mTextField.setText("done!");
            }
        }.start();

        //






    }

    public void alertstok() {
        final Dialog dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setTitle("Hallo Team Reka Mitra");
        AlertDialog alertDialog;
        alertDialog = new AlertDialog.Builder(this)



//        String idoutletalert = namaoutmenu1.getText().toString();
//        String namasalesalert = namasf3.getText().toString();
//        String a = validasib1.getText().toString();
                //new AlertDialog.Builder(this)

                //.setIcon(R.drawable.lihatdatamenu)
                .setTitle(R.string.app_name)
                .setMessage("STOK KURANG")
                .setPositiveButton("OKE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        idbarang1.setText("");
                        namabarang1.setText("");
                        stok1.setText("");
                        sisahstok1.setText("0");
                        pembayaran1.setText("");
                        qtybarang1.setText("");
                        hargabarang1.setText("");
                        keterangan1.setText("");
                        total1.setText("0");
                        dialog.cancel();



                    }
                })
//                .setNegativeButton("Keluar Aplikasi", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                })
                .show();
        alertDialog.setCanceledOnTouchOutside(false);
    }


    public void hapusaplikasicari(){
//        pDialog.setMessage("TUNGGU SEBENTAR, SEDANG VERIFIKASI DATA");
//        showDialog();

        cariidhapus();
        //hitungstok();
        int nilai1=Integer.valueOf(sisahstok1.getText().toString());
        new CountDownTimer(4000, 1000) {

            public void onTick(long millisUntilFinished) {
                pDialog.setMessage("TUNGGU SEBENTAR, SEDANG PROSES PENGEMBALIAN STOK :"+ millisUntilFinished / 1000);
                showDialog();
                pDialog.setCanceledOnTouchOutside(false);

                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                hideDialog();
//delete();
//updatestokjikahapus();

                caripenjualan();


                //mTextField.setText("done!");
            }
        }.start();

        //






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
        AndroidNetworking.post( Config.host + "listinputperdanaEXEX2.php" )
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
                                map.put("pembayaran",       responses.optString("pembayaran"));

                                //map.put("tanggal",      responses.optString("tanggal"));

                                total += Integer.parseInt(responses.getString("total"));

                                aruskas.add(map);
                            }

                            Adapter();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        subtotal1.setText("Rp "+formatter.format(total));
                        //cashh1.setText(String.valueOf(total));
                        subtotal21.setText(String.valueOf(total));
                        total = 0;

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }


    private void Adapter(){

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.listinputperdanaexex,
                new String[] {"id","item","qty","harga", "total", "keterangan", "pembayaran"},
                new int[] {R.id.idlistperdana, R.id.namalistperdana, R.id.qtylistperdana, R.id.hargalistperdana, R.id.totallistperdana, R.id.listketerangan, R.id.listpembayaran});

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
                listpembayaran  = ((TextView) view.findViewById(R.id.listpembayaran)).getText().toString();


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

    private void cariidharga() {

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
                        //namabarang1.setText((response.optString("item")));
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


    private void cariid() {

        AndroidNetworking.post(Config.host + "barangperdanaidstok.php")
                .addBodyParameter("code", idbarang1.getText().toString())
                .addBodyParameter("namagudang", gudang1.getText().toString())
                .addBodyParameter("namasales", namasalesinputperdana1.getText().toString())
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
                        namabarang1.setText((response.optString("namaitem")));
                        //stok1.setText((response.optString("qty")));
                        idbarangkeluar1.setText((response.optString("id")));
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


    private void cari_stok() {

        AndroidNetworking.post(Config.host + "cari_stok_perdana.php")
                .addBodyParameter("namaitem", namabarang1.getText().toString())
                .addBodyParameter("namasales", namasalesinputperdana1.getText().toString())
                //.addBodyParameter("namagudang", gudang1.getText().toString())
                //.addBodyParameter("namasales", namasalesinputperdana1.getText().toString())
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
                        //namabarang1.setText((response.optString("namaitem")));
                        stok1.setText((response.optString("sisa_stok")));
                        //idbarangkeluar1.setText((response.optString("id")));
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

    private void cariidhapus() {

        AndroidNetworking.post(Config.host + "barangperdanahapusstok.php")
                .addBodyParameter("namaitem", namabarang1.getText().toString())
                //.addBodyParameter("namagudang", gudang1.getText().toString())
                .addBodyParameter("namasales", namasalesinputperdana1.getText().toString())
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
                        //namabarang1.setText((response.optString("namaitem")));
                        stok1.setText((response.optString("qty")));
                        idbarangkeluar1.setText((response.optString("id")));
                        //actualhero1.setText((response.optString("actual")));
                        //achhero1.setText((response.optString("ach")));
                        //gaphero1.setText((response.optString("gap")));
                        //bulan1.setText((response.optString("bulan")));
                        hitungstokjikahapus();
                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }

    private void caripenjualan() {

        AndroidNetworking.post(Config.host + "penjualanperdana.php")
                //.addBodyParameter("namaitem", namabarang1.getText().toString())
                //.addBodyParameter("namagudang", gudang1.getText().toString())
                .addBodyParameter("id", iddelete1.getText().toString())
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

                        //iddelete1.setText((response.optString("id")));
                        validasipenjualan1.setText((response.optString("item")));



                        //int nilai1=Integer.valueOf(sisahstok1.getText().toString());
                        new CountDownTimer(4000, 1000) {

                            public void onTick(long millisUntilFinished) {
                                pDialog.setMessage("TUNGGU SEBENTAR : "+ millisUntilFinished / 1000);
                                showDialog();
                                pDialog.setCanceledOnTouchOutside(false);
                                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                            }

                            public void onFinish() {
                                if (validasipenjualan1.getText().toString().equals("null")) {

                                    Toast.makeText(getApplicationContext(), "Data Penjualan ini sudah terhapus silahkan refresh",
                                            Toast.LENGTH_LONG).show();
                                    hideDialog();
                                    iddelete1.setText("");
                                    KasAdapter2();
                                    //1
                                    //jika form Email belum di isi / masih kosong
                                    //link1.setError("harus diisi");
                                    // Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                                }else {

                                    delete();
                                    KasAdapter2();
                                }


                                //mTextField.setText("done!");
                            }
                        }.start();

//
                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }


    private void save() {
        pDialog.setMessage("Process...");
        showDialog();
        pDialog.setCanceledOnTouchOutside(false);
        AndroidNetworking.post(Config.host + "inputperdanaEXEX.php")
                .addBodyParameter("idoutlet", idoutletinputperdana1.getText().toString())
                .addBodyParameter("namaoutlet", namaoutletinputperdana1.getText().toString())
                .addBodyParameter("namasales", namasalesinputperdana1.getText().toString())
                .addBodyParameter("tanggal", tanggalinputperdana1.getText().toString())

                .addBodyParameter("item", namabarang1.getText().toString())
                .addBodyParameter("qtysebelumnya", stok1.getText().toString())
                .addBodyParameter("qty", qtybarang1.getText().toString())
                .addBodyParameter("stoksaatini", sisahstok1.getText().toString())
                .addBodyParameter("harga", hargabarang1.getText().toString())
                //.addBodyParameter("total", total1.getText().toString())
                .addBodyParameter("keterangan", keterangan1.getText().toString())
                .addBodyParameter("jam", jaminput1.getText().toString())
                .addBodyParameter("pembayaran", pembayaran1.getText().toString())
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
                            updatestok();
                            statusiccid.setText("BELUM TERINPUT");
                            ceklis.setVisibility(View.GONE);

                            update_spesial_sold();
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


    private void riwayathapus() {
        pDialog.setMessage("Process...");
        showDialog();
        pDialog.setCanceledOnTouchOutside(false);
        AndroidNetworking.post(Config.host + "inputhapus.php")
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
                .addBodyParameter("pembayaran", pembayaran1.getText().toString())


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
                            Toast.makeText(getApplicationContext(), "Berhasil disimpan ke riwayat hapus",
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
        pDialog.setCanceledOnTouchOutside(false);
        AndroidNetworking.post(Config.host + "deleteperdanaEXEX.php")
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
                            Toast.makeText(getApplicationContext(), "Delete Data Penjualan Berhasil",
                                    Toast.LENGTH_LONG).show();
                            updatestokjikahapus();
                            KasAdapter2();
                            riwayathapus();
                            update_spesial_available();
                            //


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



    private void updatestok() {
        pDialog.setMessage("Update Process...");
        showDialog();
        pDialog.setCanceledOnTouchOutside(false);
        AndroidNetworking.post(Config.host + "updateperdanaEXEX.php")
                .addBodyParameter("id", idbarangkeluar1.getText().toString())
                .addBodyParameter("qty", sisahstok1.getText().toString())
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
                            Toast.makeText(getApplicationContext(), "PERUBAHAN STOK BERHASIL",
                                    Toast.LENGTH_LONG).show();
                            idbarang1.setText("");
                            namabarang1.setText("");
                            stok1.setText("");
                            sisahstok1.setText("0");
                            //pembayaran1.setText("");
                            qtybarang1.setText("");
                            hargabarang1.setText("");
                            keterangan1.setText("");
                            total1.setText("0");
                            validasijumlahkan1.setText("");
                            KasAdapter2();
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


    private void updatestokjikahapus() {
        pDialog.setMessage("Update pengembalian stok Process...");
        showDialog();
        pDialog.setCanceledOnTouchOutside(false);
        AndroidNetworking.post(Config.host + "updateperdanahapusstok.php")
                .addBodyParameter("id", idbarangkeluar1.getText().toString())
                .addBodyParameter("qty", sisahstok1.getText().toString())
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
                            Toast.makeText(getApplicationContext(), "PENGEMBALIAN STOK BERHASIL",
                                    Toast.LENGTH_LONG).show();
                            //delete();
                            idbarang1.setText("");
                            namabarang1.setText("");
                            stok1.setText("");
                            //pembayaran1.setText("");
                            sisahstok1.setText("0");
                            qtybarang1.setText("");
                            hargabarang1.setText("");
                            //keterangan1.setText("");
                            total1.setText("0");



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

        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
        int item1 = Integer.parseInt(qtybarang1.getText().toString());
        int hitem1 = Integer.parseInt(hargabarang1.getText().toString());
        int hasilitem1 = item1 * hitem1;
        total1.setText(String.valueOf(hasilitem1));
        totalformatrp1.setText(rupiahFormat.format(hasilitem1));



    }


    @Override
    public void onClick(View view) {

        int id = view.getId();
        if (id == R.id.cash) {

//            idbarang1.setText("");
//            namabarang1.setText("");
            pembayaran1.setText("CASH");
            hargabarang1.setEnabled(true);
            String a = edittext991.getText().toString();
            idbarang1.setText(a);
            cariidharga();
            cariid();
        }
        else if (id == R.id.tempo){
            pembayaran1.setText("TEMPO");
            hargabarang1.setText("0");
            hargabarang1.setEnabled(false);


        }



    }


    private void hitungstok() {
        int item1 = Integer.parseInt(stok1.getText().toString());
        int item2 = Integer.parseInt(qtybarang1.getText().toString());
        int hasilitem1 = item1 - item2;
        sisahstok1.setText(String.valueOf(hasilitem1));
    }



    private void hitungstokjikahapus() {
        int item1 = Integer.parseInt(stok1.getText().toString());
        int item2 = Integer.parseInt(qtybarang1.getText().toString());
        int hasilitem1 = item1 + item2;
        sisahstok1.setText(String.valueOf(hasilitem1));
    }


    private void update_spesial_available() {
        //pDialog.setMessage("Update Process...");
        //showDialog();
        pDialog.setCanceledOnTouchOutside(false);
        AndroidNetworking.post(Config.host + "update_spesial_available.php")
                .addBodyParameter("nomor", keterangan1.getText().toString())

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
                            Toast.makeText(getApplicationContext(), "AVAILABLE",
                                    Toast.LENGTH_LONG).show();

                            id_spesial.setText("");
                            keterangan1.setText("");

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



    private void update_spesial_sold() {
        //pDialog.setMessage("Update Process...");
        //showDialog();
        pDialog.setCanceledOnTouchOutside(false);
        AndroidNetworking.post(Config.host + "update_spesial_sold.php")
                .addBodyParameter("nomor", keterangan1.getText().toString())
                //.addBodyParameter("qty", sisahstok1.getText().toString())
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
                            Toast.makeText(getApplicationContext(), "SOLD",
                                    Toast.LENGTH_LONG).show();

                            id_spesial.setText("");
                            keterangan1.setText("");
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


}
