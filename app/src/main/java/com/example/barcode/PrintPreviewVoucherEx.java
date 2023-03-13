package com.example.barcode;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

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
import java.util.Map;

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

import com.example.barcode.Until.BluetoothHandler;
import com.example.barcode.Until.PrinterCommands;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.zj.btsdk.BluetoothService;


import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


import de.hdodenhof.circleimageview.CircleImageView;

public class PrintPreviewVoucherEx extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, BluetoothHandler.HandlerInterface{
    TextView idoutletinputperdana1;
    TextView tanggalinputperdana1;
    TextView namaoutletinputperdana1;
    TextView namasalesinputperdana1;
    TextView url1, t_perdana, t_voucher;

    TextView transfer12;
    TextView cash12;

    LinearLayout perdana, voucher;



    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();

    public static String LINK, idlistperdana, namalistperdana, qtylistperdana, hargalistperdana, totallistperdana;
    public static boolean filter;


    private static final String TAG = PrintPreview.class.getSimpleName();

    Runnable r;public static final int RC_BLUETOOTH = 0;
    public static final int RC_CONNECT_DEVICE = 1;
    public static final int RC_ENABLE_BLUETOOTH = 2;

    NumberFormat formatter = new DecimalFormat("#,###,###,###");

    private BluetoothService mService = null;
    private boolean isPrinterReady = false;

    private List<Data_BayarEX> bayarList;
    //RecyclerView listbayar1;
    ListView listbayar1;
    private GridLayoutManager gridLayout;
    //private Adapter_Bayar adapter_bayars;

    Button test,testnonpjp, cariid1, foto1, fotobukti1, next1, kevoucher1, testransfer1;
    int subTotal = 0;
    int total = 0;

    private TextView ttl;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    Bitmap bitmap;
    String encodedimage;
    CircleImageView img;
    private static final String apiurl="https://rekamitrayasa.com/reka/buktistruk.php";



    //@SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_preview_voucher_ex);

        dateFormatter = new SimpleDateFormat("yyyy/M/d", Locale.US);


        perdana = (LinearLayout) findViewById(R.id.perdana);
        voucher = (LinearLayout) findViewById(R.id.voucher);

        t_perdana = (TextView) findViewById(R.id.t_perdana);
        t_voucher = (TextView) findViewById(R.id.t_voucher);

        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                tanggalinputperdana1 = (TextView) findViewById(R.id.tanggalinputperdana);
                String myFormat = "yyyy/MM/dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                tanggalinputperdana1.setText(sdf.format(myCalendar.getTime()));
            }
        };


        LINK = Config.host + "history.php";
        idlistperdana = "";
        namalistperdana = "";
        qtylistperdana = "";
        hargalistperdana = "";
        totallistperdana = "";
        //totallist = "";
        //query_kas = ""; query_total = "";
        filter = false;

        test = findViewById(R.id.testprint);
        testnonpjp = findViewById(R.id.testprintnonpjp);
        ttl = findViewById(R.id.testTotal);

        idoutletinputperdana1 = (EditText) findViewById(R.id.idoutletinputperdana);
        tanggalinputperdana1 = (TextView) findViewById(R.id.tanggalinputperdana);
        namaoutletinputperdana1 = (TextView) findViewById(R.id.namaoutletinputperdana);
        namasalesinputperdana1 = (TextView) findViewById(R.id.namasalesinputperdana);
        url1 = (TextView) findViewById(R.id.rekamitra);


        transfer12 = (TextView) findViewById(R.id.tr);

//        transfer1 = (TextView) findViewById(R.id.tr);
        cash12 = (TextView) findViewById(R.id.cs);


        cariid1 = (Button) findViewById(R.id.cariid);
        next1 = (Button) findViewById(R.id.next);

        foto1 = (Button) findViewById(R.id.foto);
        fotobukti1 = (Button) findViewById(R.id.fotobukti);

        kevoucher1 = (Button) findViewById(R.id.kevoucher);

        testransfer1 = (Button) findViewById(R.id.testransfer);


        img=(CircleImageView)findViewById(R.id.profile_image);

        Intent i = getIntent();
        String kiriman = i.getStringExtra("idoutlet");
        idoutletinputperdana1.setText(kiriman);
        String kiriman2 = i.getStringExtra("namaoutlet");
        namaoutletinputperdana1.setText(kiriman2);
        String kiriman3 = i.getStringExtra("namasales");
        namasalesinputperdana1.setText(kiriman3);
        String kiriman4 = i.getStringExtra("tanggal");
        tanggalinputperdana1.setText(kiriman4);

        test.setVisibility(View.VISIBLE);
        testnonpjp.setVisibility(View.GONE);


        perdana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //namagudang.setText("GUDANG PERDANA");

                t_perdana.setTextColor(getResources().getColor(R.color.white));
                perdana.setBackground(getResources().getDrawable(R.drawable.kota_nav2y));
                voucher.setBackground(getResources().getDrawable(R.drawable.kotak_transparan_putih2));
                t_voucher.setTextColor(getResources().getColor(R.color.navy));

                test.setVisibility(View.VISIBLE);
                testnonpjp.setVisibility(View.GONE);

                list();


            }
        });

        voucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                t_perdana.setTextColor(getResources().getColor(R.color.navy));
                perdana.setBackground(getResources().getDrawable(R.drawable.kotak_transparan_putih2));
                voucher.setBackground(getResources().getDrawable(R.drawable.kota_nav2y));
                t_voucher.setTextColor(getResources().getColor(R.color.white));

                test.setVisibility(View.GONE);
                testnonpjp.setVisibility(View.VISIBLE);

                list_noppn();

            }
        });



        setupBluetooth();

        // listbayar1 = (RecyclerView) findViewById(R.id.listBayar);
        listbayar1 = (ListView) findViewById(R.id.listBayar);
        bayarList = new ArrayList<Data_BayarEX>();

        gridLayout = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        //listbayar1.setLayoutManager(gridLayout);

        //adapter_bayars = new Adapter_Bayar(this, bayarList);
        //recyclerView.setAdapter(adapter_bayars);

        //tanggalinputperdana1.setText(getCurrentDate());
        tanggalinputperdana1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });



        list();

        cariid1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                list2();
                list();

            }
        });

        kevoucher1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub
                String a = idoutletinputperdana1.getText().toString();
                String b = namaoutletinputperdana1.getText().toString();
                String c = namasalesinputperdana1.getText().toString();
                Intent i = new Intent(getApplicationContext(), InputLink.class);
                i.putExtra("idoutlet",""+a+"");
                i.putExtra("namaoutlet",""+b+"");
                i.putExtra("namasales",""+c+"");
                startActivity(i);

            }
        });

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                printDemo();
            }
        });


        testnonpjp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                printDemo_nonpjp();
            }
        });


        foto1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                    // TODO Auto-generated method stub
                    requestStoragePermission();

            }

        });

        fotobukti1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // TODO Auto-generated method stub
                uploadtoserver();

            }

        });

        testransfer1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // TODO Auto-generated method stub
                transfer();

            }

        });


        transfer();

    }




    protected void printDemo() {
        if (!mService.isAvailable()) {
            Log.i(TAG, "printText: perangkat tidak support bluetooth");
            return;
        }
        if (isPrinterReady) {

            String a = namaoutletinputperdana1.getText().toString();
            String b = tanggalinputperdana1.getText().toString();
            String c = idoutletinputperdana1.getText().toString();
            String d = namasalesinputperdana1.getText().toString();

            String e = transfer12.getText().toString();
            String f = cash12.getText().toString();
            // TOTAL LINE 32

            mService.write(PrinterCommands.ESC_ENTER);
            mService.write(PrinterCommands.besar);

            mService.write(PrinterCommands.ESC_ALIGN_CENTER);

            mService.write(PrinterCommands.biasa);
            //mService.sendMessage("Wisma Barcode Jakarta Barat", "");
            mService.sendMessage("PT.REKA MITRAYASA KOMUNIKATAMA", "");
           //mService.sendMessage("MOONEE SHOP", "");
//            mService.sendMessage("AMINO OFFICIAL SHOP", "");
             //mService.sendMessage("IP SOLUTION DYNAMIC", "");
//            mService.sendMessage("YBIT STORE", "");
//            mService.sendMessage("Shared Hosting Premium", "");
            mService.write(PrinterCommands.FEED_PAPER_AND_CUT);
            mService.write(PrinterCommands.small);

            mService.sendMessage("Jl.Mampang Prapatan Raya No.98 C", "");
            mService.sendMessage("Jakarta Selatan 12790 Indonesia", "");
            mService.sendMessage("NPWP. 01.764.602.7-014.000", "");
            mService.sendMessage("Tel.(62-21)79191965", "");
            mService.sendMessage("Fax. (62-21)7902523", "");
            mService.write(PrinterCommands.FEED_LINE);
            mService.sendMessage("Struk", "");



            mService.write(PrinterCommands.ESC_ALIGN_LEFT);
            mService.sendMessage(""+d, ""); //Namasales
            mService.write(PrinterCommands.ESC_ALIGN_LEFT);
            mService.sendMessage(""+c, ""); //idoutlet
            mService.write(PrinterCommands.ESC_ALIGN_LEFT);
            mService.sendMessage(""+a, ""); //namaoutlet
            mService.write(PrinterCommands.ESC_ALIGN_LEFT);
            mService.sendMessage(""+b, ""); //tanggal
            mService.write(PrinterCommands.small);

            String ans = "no" +"nama";

            int n = (32 - ("ITEM".length() + "HARGA".length()));
            ans = "ITEM" + new String(new char[n]).replace("\0", " ") + "HARGA";

            mService.sendMessage(""+ans, "");
            mService.sendMessage("................................", "");
            for(int i=0; i<bayarList.size(); i++) {

                Data_BayarEX data = bayarList.get(i);
                int quantity = Integer.parseInt(bayarList.get(i).getJumlah());
                //int ket = Integer.parseInt(bayarList.get(i).getKeterangan());
                int sub = quantity * Integer.parseInt(bayarList.get(i).getHarga());
                String Ssub = ""+formatter.format(sub);
                String Snama = ""+bayarList.get(i).getNama();
                String Sket = ""+bayarList.get(i).getKeterangan();
                String Spem = ""+bayarList.get(i).getpembayaran();
                //Double ket = Double.parseDouble(bayarList.get(i).getKeterangan());

                int Shh = Integer.parseInt(bayarList.get(i).getHarga());
                String Sjml = ""+quantity+" x @"+formatter.format(Shh);

                int isi = (32 - (Sjml.length() + Ssub.length()));
                String pr = Sjml + new String(new char[isi]).replace("\0", " ") + Ssub;
                mService.write(PrinterCommands.ESC_ALIGN_LEFT);
                mService.sendMessage(""+Snama, "");

                mService.write(PrinterCommands.ESC_ALIGN_CENTER);
                mService.sendMessage(""+pr, "");

                mService.write(PrinterCommands.ESC_ALIGN_LEFT);
                mService.sendMessage(""+Sket, "");

//                mService.write(PrinterCommands.ESC_ALIGN_LEFT);
//                mService.sendMessage("Pembayaran : ("+Spem+")", "");

                mService.write(PrinterCommands.ESC_ALIGN_LEFT);
                mService.sendMessage(" ", "");

                subTotal += sub;



            }

            mService.sendMessage("................................", "");

            String subp = ""+formatter.format(subTotal);
            int isi = (32 - ("SubTotal".length() + subp.length()));
            String pr = "SubTotal" + new String(new char[isi]).replace("\0", " ") + subp;

            mService.sendMessage(""+pr, "");
            subTotal = 0;


            mService.write(PrinterCommands.ESC_ALIGN_LEFT);
            mService.sendMessage("TRANSFER : "+e, ""); //transfer
            mService.write(PrinterCommands.ESC_ALIGN_LEFT);
            mService.sendMessage("CASH : "+f, ""); //cash

           // mService.write(PrinterCommands.FEED_PAPER_AND_CUT);
            mService.write(PrinterCommands.ESC_ENTER);

            mService.write(PrinterCommands.ESC_ALIGN_CENTER);
            mService.sendMessage("TERIMA KASIH", "");

            mService.write(PrinterCommands.ESC_ALIGN_CENTER);
            mService.sendMessage("Harga Sudah Termasuk PPN 11%", "");



//            mService.write(PrinterCommands.FEED_LINE);
//            mService.sendMessage("Klik NoIndihome untuk Informasi Berlangganan dan Pengaturan Layanan.", "");
//            mService.sendMessage("Klik Domain untuk mengecek DDNS telah berfungsi, tanpa setting di modem.", "");
//            mService.sendMessage("DDNS hanya berfungsi dengan baik pada paket IndiHome Gamer.", "");
//            mService.sendMessage("Optional = Anda bisa mensetting DDNS Client di modem.", "");
//            mService.sendMessage("Info lebih lanjut, silahkan kirim email ke : admin@ip-solutiondynamic.com", "");

//                        mService.write(PrinterCommands.FEED_LINE);
//            mService.sendMessage("Klik Hostinger untuk Informasi Berlangganan dan Pengaturan Layanan.", "");
//            mService.sendMessage("Klik Domain untuk mengecek DDNS telah berfungsi, tanpa setting di website.", "");
//            mService.sendMessage("DDNS hanya berfungsi dengan baik pada paket Hostinger Premium.", "");
//            mService.sendMessage("Optional = Anda bisa mensetting DDNS Hostinger @rekamitrayasa.com.", "");
//            mService.sendMessage("Info lebih lanjut, silahkan kirim email ke : abusee@hostinger.com", "");



//
//
//
            mService.sendMessage("Barang yang sudah dibeli tidak  bisa di kembalikan/di tukar.", "");
            mService.write(PrinterCommands.FEED_PAPER_AND_CUT);
            mService.write(PrinterCommands.FEED_PAPER_AND_CUT);
            mService.write(PrinterCommands.ESC_ENTER);



        } else {
            if (mService.isBTopen())
                startActivityForResult(new Intent(PrintPreviewVoucherEx.this, DeviceActivity.class), RC_CONNECT_DEVICE);
            else
                requestBluetooth();
        }

    }


    protected void printDemo_nonpjp() {
        if (!mService.isAvailable()) {
            Log.i(TAG, "printText: perangkat tidak support bluetooth");
            return;
        }
        if (isPrinterReady) {

            String a = namaoutletinputperdana1.getText().toString();
            String b = tanggalinputperdana1.getText().toString();
            String c = idoutletinputperdana1.getText().toString();
            String d = namasalesinputperdana1.getText().toString();

            String e = transfer12.getText().toString();
            String f = cash12.getText().toString();
            // TOTAL LINE 32

            mService.write(PrinterCommands.ESC_ENTER);
            mService.write(PrinterCommands.besar);

            mService.write(PrinterCommands.ESC_ALIGN_CENTER);

            mService.write(PrinterCommands.biasa);
            //mService.sendMessage("Wisma Barcode Jakarta Barat", "");
            //mService.sendMessage("PT.REKA MITRAYASA KOMUNIKATAMA", "");
            //mService.sendMessage("MOONEE SHOP", "");
//            mService.sendMessage("AMINO OFFICIAL SHOP", "");
            //mService.sendMessage("IP SOLUTION DYNAMIC", "");
//            mService.sendMessage("YBIT STORE", "");
//            mService.sendMessage("Shared Hosting Premium", "");
            mService.write(PrinterCommands.FEED_PAPER_AND_CUT);
            mService.write(PrinterCommands.small);

//            mService.sendMessage("Jl.Mampang Prapatan Raya No.98 C", "");
//            mService.sendMessage("Jakarta Selatan 12790 Indonesia", "");
//            mService.sendMessage("NPWP. 01.764.602.7-014.000", "");
//            mService.sendMessage("Tel.(62-21)79191965", "");
//            mService.sendMessage("Fax. (62-21)7902523", "");
            mService.write(PrinterCommands.FEED_LINE);
            mService.sendMessage("Struk", "");



            mService.write(PrinterCommands.ESC_ALIGN_LEFT);
            mService.sendMessage(""+d, ""); //Namasales
            mService.write(PrinterCommands.ESC_ALIGN_LEFT);
            mService.sendMessage(""+c, ""); //idoutlet
            mService.write(PrinterCommands.ESC_ALIGN_LEFT);
            mService.sendMessage(""+a, ""); //namaoutlet
            mService.write(PrinterCommands.ESC_ALIGN_LEFT);
            mService.sendMessage(""+b, ""); //tanggal
            mService.write(PrinterCommands.small);

            String ans = "no" +"nama";

            int n = (32 - ("ITEM".length() + "HARGA".length()));
            ans = "ITEM" + new String(new char[n]).replace("\0", " ") + "HARGA";

            mService.sendMessage(""+ans, "");
            mService.sendMessage("................................", "");
            for(int i=0; i<bayarList.size(); i++) {

                Data_BayarEX data = bayarList.get(i);
                int quantity = Integer.parseInt(bayarList.get(i).getJumlah());
                //int ket = Integer.parseInt(bayarList.get(i).getKeterangan());
                int sub = quantity * Integer.parseInt(bayarList.get(i).getHarga());
                String Ssub = ""+formatter.format(sub);
                String Snama = ""+bayarList.get(i).getNama();
                String Sket = ""+bayarList.get(i).getKeterangan();
                String Spem = ""+bayarList.get(i).getpembayaran();
                //Double ket = Double.parseDouble(bayarList.get(i).getKeterangan());

                int Shh = Integer.parseInt(bayarList.get(i).getHarga());
                String Sjml = ""+quantity+" x @"+formatter.format(Shh);

                int isi = (32 - (Sjml.length() + Ssub.length()));
                String pr = Sjml + new String(new char[isi]).replace("\0", " ") + Ssub;
                mService.write(PrinterCommands.ESC_ALIGN_LEFT);
                mService.sendMessage(""+Snama, "");

                mService.write(PrinterCommands.ESC_ALIGN_CENTER);
                mService.sendMessage(""+pr, "");

                mService.write(PrinterCommands.ESC_ALIGN_LEFT);
                mService.sendMessage(""+Sket, "");

//                mService.write(PrinterCommands.ESC_ALIGN_LEFT);
//                mService.sendMessage("Pembayaran : ("+Spem+")", "");

                mService.write(PrinterCommands.ESC_ALIGN_LEFT);
                mService.sendMessage(" ", "");

                subTotal += sub;



            }

            mService.sendMessage("................................", "");

            String subp = ""+formatter.format(subTotal);
            int isi = (32 - ("SubTotal".length() + subp.length()));
            String pr = "SubTotal" + new String(new char[isi]).replace("\0", " ") + subp;

            mService.sendMessage(""+pr, "");
            subTotal = 0;


//            mService.write(PrinterCommands.ESC_ALIGN_LEFT);
//            mService.sendMessage("TRANSFER : "+e, ""); //transfer
//            mService.write(PrinterCommands.ESC_ALIGN_LEFT);
//            mService.sendMessage("CASH : "+f, ""); //cash

            // mService.write(PrinterCommands.FEED_PAPER_AND_CUT);
            mService.write(PrinterCommands.ESC_ENTER);

            mService.write(PrinterCommands.ESC_ALIGN_CENTER);
            mService.sendMessage("TERIMA KASIH", "");

//            mService.write(PrinterCommands.ESC_ALIGN_CENTER);
//            mService.sendMessage("Harga Sudah Termasuk PPN 11%", "");



//            mService.write(PrinterCommands.FEED_LINE);
//            mService.sendMessage("Klik NoIndihome untuk Informasi Berlangganan dan Pengaturan Layanan.", "");
//            mService.sendMessage("Klik Domain untuk mengecek DDNS telah berfungsi, tanpa setting di modem.", "");
//            mService.sendMessage("DDNS hanya berfungsi dengan baik pada paket IndiHome Gamer.", "");
//            mService.sendMessage("Optional = Anda bisa mensetting DDNS Client di modem.", "");
//            mService.sendMessage("Info lebih lanjut, silahkan kirim email ke : admin@ip-solutiondynamic.com", "");

//                        mService.write(PrinterCommands.FEED_LINE);
//            mService.sendMessage("Klik Hostinger untuk Informasi Berlangganan dan Pengaturan Layanan.", "");
//            mService.sendMessage("Klik Domain untuk mengecek DDNS telah berfungsi, tanpa setting di website.", "");
//            mService.sendMessage("DDNS hanya berfungsi dengan baik pada paket Hostinger Premium.", "");
//            mService.sendMessage("Optional = Anda bisa mensetting DDNS Hostinger @rekamitrayasa.com.", "");
//            mService.sendMessage("Info lebih lanjut, silahkan kirim email ke : abusee@hostinger.com", "");



//
//
//
            mService.sendMessage("Barang yang sudah dibeli tidak  bisa di kembalikan/di tukar.", "");
            mService.write(PrinterCommands.FEED_PAPER_AND_CUT);
            mService.write(PrinterCommands.FEED_PAPER_AND_CUT);
            mService.write(PrinterCommands.ESC_ENTER);



        } else {
            if (mService.isBTopen())
                startActivityForResult(new Intent(PrintPreviewVoucherEx.this, DeviceActivity.class), RC_CONNECT_DEVICE);
            else
                requestBluetooth();
        }

    }



//    protected void printDemo() {
//        if (!mService.isAvailable()) {
//            Log.i(TAG, "printText: perangkat tidak support bluetooth");
//            return;
//        }
//        if (isPrinterReady) {
//
//            String a = namaoutletinputperdana1.getText().toString();
//            String b = tanggalinputperdana1.getText().toString();
//            String c = idoutletinputperdana1.getText().toString();
//            String d = namasalesinputperdana1.getText().toString();
//            // TOTAL LINE 32
//
//            mService.write(PrinterCommands.ESC_ENTER);
//            mService.write(PrinterCommands.besar);
//
//            mService.write(PrinterCommands.ESC_ALIGN_CENTER);
//
//            mService.write(PrinterCommands.biasa);
//            //mService.sendMessage("Wisma Barcode Jakarta Barat", "");
//            //mService.sendMessage("PT.REKA MITRAYASA KOMUNIKATAMA", "");
//            mService.sendMessage("MOONEE SHOP", "");
////            mService.sendMessage("AMINO OFFICIAL SHOP", "");
//            //mService.sendMessage("IP SOLUTION DYNAMIC", "");
////            mService.sendMessage("YBIT STORE", "");
////            mService.sendMessage("Shared Hosting Premium", "");
//            mService.write(PrinterCommands.FEED_PAPER_AND_CUT);
//            mService.write(PrinterCommands.small);
//
////            mService.sendMessage("Jl.Mampang Prapatan Raya No.98 C", "");
////            mService.sendMessage("Jakarta Selatan 12790 Indonesia", "");
////            mService.sendMessage("NPWP. 01.764.602.7-014.000", "");
////            mService.sendMessage("Tel.(62-21)79191965", "");
////            mService.sendMessage("Fax. (62-21)7902523", "");
//            mService.write(PrinterCommands.FEED_LINE);
//            mService.sendMessage("Struk", "");
//
//
//
////            mService.write(PrinterCommands.ESC_ALIGN_LEFT);
////            mService.sendMessage(""+d, ""); //Namasales
////            mService.write(PrinterCommands.ESC_ALIGN_LEFT);
////            mService.sendMessage(""+c, ""); //idoutlet
////            mService.write(PrinterCommands.ESC_ALIGN_LEFT);
////            mService.sendMessage(""+a, ""); //namaoutlet
//            mService.write(PrinterCommands.ESC_ALIGN_LEFT);
//            mService.sendMessage(""+b, ""); //tanggal
//            mService.write(PrinterCommands.small);
//
//            String ans = "no" +"nama";
//
//            int n = (32 - ("ITEM".length() + "HARGA".length()));
//            ans = "ITEM" + new String(new char[n]).replace("\0", " ") + "HARGA";
//
//            mService.sendMessage(""+ans, "");
//            mService.sendMessage("................................", "");
//            for(int i=0; i<bayarList.size(); i++) {
//
//                Data_BayarEX data = bayarList.get(i);
//                int quantity = Integer.parseInt(bayarList.get(i).getJumlah());
//                //int ket = Integer.parseInt(bayarList.get(i).getKeterangan());
//                int sub = quantity * Integer.parseInt(bayarList.get(i).getHarga());
//                String Ssub = ""+formatter.format(sub);
//                String Snama = ""+bayarList.get(i).getNama();
//                String Sket = ""+bayarList.get(i).getKeterangan();
//                String Spem = ""+bayarList.get(i).getpembayaran();
//                //Double ket = Double.parseDouble(bayarList.get(i).getKeterangan());
//
//                int Shh = Integer.parseInt(bayarList.get(i).getHarga());
//                String Sjml = ""+quantity+" x @"+formatter.format(Shh);
//
//                int isi = (32 - (Sjml.length() + Ssub.length()));
//                String pr = Sjml + new String(new char[isi]).replace("\0", " ") + Ssub;
//                mService.write(PrinterCommands.ESC_ALIGN_LEFT);
//                mService.sendMessage(""+Snama, "");
//
//                mService.write(PrinterCommands.ESC_ALIGN_CENTER);
//                mService.sendMessage(""+pr, "");
//
//                mService.write(PrinterCommands.ESC_ALIGN_LEFT);
//                mService.sendMessage(""+Sket, "");
//
////                mService.write(PrinterCommands.ESC_ALIGN_LEFT);
////                mService.sendMessage("Pembayaran : ("+Spem+")", "");
//
//                mService.write(PrinterCommands.ESC_ALIGN_LEFT);
//                mService.sendMessage(" ", "");
//
//                subTotal += sub;
//
//
//
//            }
//
//            mService.sendMessage("................................", "");
//
//            String subp = ""+formatter.format(subTotal);
//            int isi = (32 - ("SubTotal".length() + subp.length()));
//            String pr = "SubTotal" + new String(new char[isi]).replace("\0", " ") + subp;
//
//            mService.sendMessage(""+pr, "");
//            subTotal = 0;
//
//            // mService.write(PrinterCommands.FEED_PAPER_AND_CUT);
//            mService.write(PrinterCommands.ESC_ENTER);
//
//            mService.write(PrinterCommands.ESC_ALIGN_CENTER);
//            mService.sendMessage("TERIMA KASIH", "");
//
////            mService.write(PrinterCommands.ESC_ALIGN_CENTER);
////            mService.sendMessage("Harga Sudah Termasuk PPN 11%", "");
//
//
//
////            mService.write(PrinterCommands.FEED_LINE);
////            mService.sendMessage("Klik NoIndihome untuk Informasi Berlangganan dan Pengaturan Layanan.", "");
////            mService.sendMessage("Klik Domain untuk mengecek DDNS telah berfungsi, tanpa setting di modem.", "");
////            mService.sendMessage("DDNS hanya berfungsi dengan baik pada paket IndiHome Gamer.", "");
////            mService.sendMessage("Optional = Anda bisa mensetting DDNS Client di modem.", "");
////            mService.sendMessage("Info lebih lanjut, silahkan kirim email ke : admin@ip-solutiondynamic.com", "");
//
////                        mService.write(PrinterCommands.FEED_LINE);
////            mService.sendMessage("Klik Hostinger untuk Informasi Berlangganan dan Pengaturan Layanan.", "");
////            mService.sendMessage("Klik Domain untuk mengecek DDNS telah berfungsi, tanpa setting di website.", "");
////            mService.sendMessage("DDNS hanya berfungsi dengan baik pada paket Hostinger Premium.", "");
////            mService.sendMessage("Optional = Anda bisa mensetting DDNS Hostinger @rekamitrayasa.com.", "");
////            mService.sendMessage("Info lebih lanjut, silahkan kirim email ke : abusee@hostinger.com", "");
//
//
//
////
////
////
////            mService.sendMessage("Barang yang sudah dibeli tidak  bisa di kembalikan/di tukar.", "");
//            mService.write(PrinterCommands.FEED_PAPER_AND_CUT);
//            mService.write(PrinterCommands.FEED_PAPER_AND_CUT);
//            mService.write(PrinterCommands.ESC_ENTER);
//
//
//
//        } else {
//            if (mService.isBTopen())
//                startActivityForResult(new Intent(PrintPreviewVoucherEx.this, DeviceActivity.class), RC_CONNECT_DEVICE);
//            else
//                requestBluetooth();
//        }
//
//    }

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
                tanggalinputperdana1.setText(dateFormatter.format(newDate.getTime()));
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
        listbayar1.setAdapter(null);
        bayarList.clear();

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "listinputvoucherEXEX2_ppn.php" )
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
                                Data_BayarEX item = new Data_BayarEX();
                                JSONObject responses    = jsonArray.getJSONObject(i);
                                HashMap<String, String> map = new HashMap<String, String>();
                                //map.put("no",         responses.optString("no"));
                                map.put("id",         responses.optString("id"));
                                map.put("item",       responses.optString("item"));
                                map.put("qty",       responses.optString("qty"));
                                map.put("harga",       responses.optString("harga"));
                                map.put("total",       responses.optString("total"));
                                map.put("keterangan",       responses.optString("keterangan"));
                                map.put("pembayaran",       responses.optString("pembayaran"));



                                item.setId(responses.getString("id"));
                                item.setNama(responses.getString("item"));
                                item.setHarga(responses.getString("harga"));
                                item.setJumlah(responses.getString("qty"));
                                item.setKeterangan(responses.getString("keterangan"));
                                item.setpembayaran(responses.getString("pembayaran"));

                                total += Integer.parseInt(responses.getString("harga"))* Integer.parseInt(responses.getString("qty"));
                                //map.put("tanggal",      responses.optString("tanggal"));

                                aruskas.add(map);
                                bayarList.add(item);


                            }

                            Adapter();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        ttl.setText("Total : Rp "+formatter.format(total));
                        total = 0;
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    private void Adapter(){

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.listinputperdanaprintexex,
                new String[] {"item","qty","harga", "total", "keterangan", "pembayaran"},
                new int[] {R.id.namalistperdana, R.id.qtylistperdana, R.id.hargalistperdana, R.id.totallistperdana, R.id.listketerangan, R.id.listpembayaran});

        listbayar1.setAdapter(simpleAdapter);

        //swipe_refresh.setRefreshing(false);
    }




    private void list_noppn(){

        //swipe_refresh.setRefreshing(true);
        aruskas.clear();
        listbayar1.setAdapter(null);
        bayarList.clear();

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "listinputvoucherEXEX2_noppn.php" )
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
                                Data_BayarEX item = new Data_BayarEX();
                                JSONObject responses    = jsonArray.getJSONObject(i);
                                HashMap<String, String> map = new HashMap<String, String>();
                                //map.put("no",         responses.optString("no"));
                                map.put("id",         responses.optString("id"));
                                map.put("item",       responses.optString("item"));
                                map.put("qty",       responses.optString("qty"));
                                map.put("harga",       responses.optString("harga"));
                                map.put("total",       responses.optString("total"));
                                map.put("keterangan",       responses.optString("keterangan"));
                                map.put("pembayaran",       responses.optString("pembayaran"));



                                item.setId(responses.getString("id"));
                                item.setNama(responses.getString("item"));
                                item.setHarga(responses.getString("harga"));
                                item.setJumlah(responses.getString("qty"));
                                item.setKeterangan(responses.getString("keterangan"));
                                item.setpembayaran(responses.getString("pembayaran"));

                                total += Integer.parseInt(responses.getString("harga"))* Integer.parseInt(responses.getString("qty"));
                                //map.put("tanggal",      responses.optString("tanggal"));

                                aruskas.add(map);
                                bayarList.add(item);
                            }

                            Adapter2();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        ttl.setText("Total : Rp "+formatter.format(total));
                        total = 0;
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    private void Adapter2(){

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.listinputperdanaprintexex,
                new String[] {"item","qty","harga", "total", "keterangan", "pembayaran"},
                new int[] {R.id.namalistperdana, R.id.qtylistperdana, R.id.hargalistperdana, R.id.totallistperdana, R.id.listketerangan, R.id.listpembayaran});

        listbayar1.setAdapter(simpleAdapter);

        //swipe_refresh.setRefreshing(false);
    }















    private void list2(){

        AndroidNetworking.post(Config.host + "dataoutlet.php")
                .addBodyParameter("idoutlet", idoutletinputperdana1.getText().toString())
                //.addBodyParameter("bulan1", bulan1.getText().toString())
                //.addBodyParameter("bulan2", bulan2.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response


                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
                        //idoutletinputperdana1.setText((response.optString("id")));
                        namaoutletinputperdana1.setText((response.optString("namaoutlet")));


                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });
    }


    private void transfer() {

        AndroidNetworking.post(Config.host + "tfvoucher.php")
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



                        //transfer12.setText((response.optString("transfer")));
                        transfer12.setText(
                                rupiahFormat.format(response.optDouble("transfer")));
                        //cash12.setText((response.optString("cash")));
                        cash12.setText(
                                rupiahFormat.format(response.optDouble("cash")));
//                        tanggal1.setText((response.optString("tanggal")));
//                        jam1.setText((response.optString("jam")));

                    }

                    @Override
                    public void onError(ANError error) {

                    }




                });

    }


    private void requestStoragePermission() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        // permission is granted
                        openCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PrintPreviewVoucherEx.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 111);
    }

    @AfterPermissionGranted(RC_BLUETOOTH)
    private void setupBluetooth() {
        String[] params = {Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN};
        if (!EasyPermissions.hasPermissions(this, params)) {
            EasyPermissions.requestPermissions(this, "You need bluetooth permission",
                    RC_BLUETOOTH, params);
            return;
        }
        mService = new BluetoothService(this, new BluetoothHandler(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RC_ENABLE_BLUETOOTH:
                if (resultCode == RESULT_OK) {
                    Log.i(TAG, "onActivityResult: bluetooth aktif");
                } else
                    Log.i(TAG, "onActivityResult: bluetooth harus aktif untuk menggunakan fitur ini");
                break;
            case RC_CONNECT_DEVICE:
                if (resultCode == RESULT_OK) {
                    String address = data.getExtras().getString(DeviceActivity.EXTRA_DEVICE_ADDRESS);
                    BluetoothDevice mDevice = mService.getDevByMac(address);
                    mService.connect(mDevice);
                    //Toast.makeText(MainActivity.this,""+address,Toast.LENGTH_LONG).show();
                }
                break;
            case 111:
                if(resultCode==RESULT_OK){
                    bitmap=(Bitmap)data.getExtras().get("data");
                    img.setImageBitmap(bitmap);
                    encodebitmap(bitmap);
                }
                super.onActivityResult(requestCode, resultCode, data);

                break;




        }


    }

    private void requestBluetooth() {
        if (mService != null) {
            if (!mService.isBTopen()) {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent, RC_ENABLE_BLUETOOTH);
            }
        }
    }

    @Override
    public void onDeviceConnected() {
        isPrinterReady = true;
    }

    @Override
    public void onDeviceConnecting() {

    }

    @Override
    public void onDeviceConnectionLost() {
        isPrinterReady = false;
    }

    @Override
    public void onDeviceUnableToConnect() {

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }






//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if(requestCode==111 && resultCode==RESULT_OK)
//        {
//            bitmap=(Bitmap)data.getExtras().get("data");
//            img.setImageBitmap(bitmap);
//            encodebitmap(bitmap);
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    private void encodebitmap(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);

        byte[] byteofimages=byteArrayOutputStream.toByteArray();
        encodedimage=android.util.Base64.encodeToString(byteofimages, Base64.DEFAULT);
        //encodedimage=android.util.Base64.encodeToString(byteofimages, Base64.DEFAULT);
    }



    private void uploadtoserver()
    {
        final String idoutmenu=idoutletinputperdana1.getText().toString().trim();
        final String namaoutmenu=namaoutletinputperdana1.getText().toString().trim();
        final String namasf=namasalesinputperdana1.getText().toString().trim();
        final String tanggalinputperdana=tanggalinputperdana1.getText().toString().trim();
        //final String alasan=alasan1.getText().toString().trim();
        //final String jaminput=jaminput1.getText().toString().trim();
        final String url=url1.getText().toString().trim();

        StringRequest request=new StringRequest(Request.Method.POST, apiurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                //t1.setText("");
                //alasan1.setText("");
                img.setImageResource(R.drawable.noimage);
                url1.setText("https://rekamitrayasa.com/reka/images/");
                Toast.makeText(getApplicationContext(),"SIMPAN VALIDASI PERDANA DAN FOTO BERHASIL",Toast.LENGTH_SHORT).show();
                kevoucher1.setEnabled(true);
                kevoucher1.setBackgroundColor(getResources().getColor(R.color.merah));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String,String> map=new HashMap<String, String>();
                map.put("t1",idoutmenu);
                map.put("t2",namaoutmenu);
                map.put("t3",namasf);
                map.put("t4",tanggalinputperdana);
                map.put("upload",encodedimage);
                map.put("t6",url);
                return map;
            }
        };

        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);


    }


}