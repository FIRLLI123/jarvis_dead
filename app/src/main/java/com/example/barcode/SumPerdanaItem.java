package com.example.barcode;

import android.Manifest;
import android.app.DatePickerDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
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

import com.example.barcode.Until.BluetoothHandler;
import com.example.barcode.Until.PrinterCommands;
import com.zj.btsdk.BluetoothService;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class SumPerdanaItem extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, BluetoothHandler.HandlerInterface {


    TextView transferperdana1;
    TextView cashperdana1;
    TextView totalperdana1;

    TextView transferperdana12;
    TextView cashperdana12;
    TextView totalperdana12;


    //-----------------------------
    TextView tanggal1,namasales1,ttl1, ttl21;
    ListView listtest1;
    Button carisumitem1, cetak1;
    //EditText namasaleslist1;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;




    private static final String TAG = SumPerdanaItem.class.getSimpleName();

    Runnable r;public static final int RC_BLUETOOTH = 0;
    public static final int RC_CONNECT_DEVICE = 1;
    public static final int RC_ENABLE_BLUETOOTH = 2;

    NumberFormat formatter = new DecimalFormat("#,###,###,###");

    private BluetoothService mService = null;
    private boolean isPrinterReady = false;

    private List<Data_BayarEX> bayarList;

    int subTotal = 0;
    int total = 0;
    int totalstring = 0;

    public static String LINK, namasaleslist, tanggallist, itemlist, qtylist, hargalist,  totallist;
    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sum_perdana_item);

        transferperdana1 = (TextView) findViewById(R.id.tfperdana);
        cashperdana1 = (TextView) findViewById(R.id.cashperdana);
        totalperdana1 = (TextView) findViewById(R.id.totalperdana);



        transferperdana12 = (TextView) findViewById(R.id.tfperdana12);
        cashperdana12 = (TextView) findViewById(R.id.cashperdana12);
        totalperdana12 = (TextView) findViewById(R.id.totalperdana12);

        //------------------------------------

        bayarList = new ArrayList<Data_BayarEX>();
        //NumberFormat formatter = new DecimalFormat("#,###,###,###");

        LINK = Config.host + "history.php";
        namasaleslist = "";
        tanggallist = "";
        itemlist = "";
        qtylist = "";
        hargalist = "";
        totallist = "";
        //query_kas = ""; query_total = "";
        //filter = false;
        dateFormatter = new SimpleDateFormat("yyyy/MM/dd", Locale.US);

        tanggal1 = (TextView) findViewById(R.id.tanggal);
        listtest1 = (ListView) findViewById(R.id.listtest);

        namasales1 = (TextView) findViewById(R.id.namasales);

        ttl1 = (TextView) findViewById(R.id.ttl);


        ttl21 = (TextView) findViewById(R.id.ttl2);

        carisumitem1 = (Button) findViewById(R.id.carisumitem);
        cetak1 = (Button) findViewById(R.id.cetak);

        Intent i = getIntent();
        String kiriman2 = i.getStringExtra("namasales");
        namasales1.setText(kiriman2);
        //String kiriman3 = i.getStringExtra("namasales");
        //namasalesinputperdana1.setText(kiriman3);
        String kiriman4 = i.getStringExtra("tanggal");
        tanggal1.setText(kiriman4);

        setupBluetooth();

        tanggal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });


        cetak1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int nilai1=Integer.valueOf(ttl21.getText().toString());
                int nilai2=Integer.valueOf(totalperdana12.getText().toString());

//                Intent i = new Intent(getApplicationContext(), SumPerdana2.class);
//                    startActivity(i);

                if (namasales1.getText().toString().length() == 0) {                    //1
                    //jika form Email belum di isi / masih kosong
                    //iddelete1.setError("");
                    Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
                }else if (nilai1!=nilai2) {                    //1
                    //jika form Email belum di isi / masih kosong
                    //iddelete1.setError("");
                    Toast.makeText(getApplicationContext(), "Tidak bisa Cetak, Nominal Penjualan dan Nominal Transfer Tidak Sama", Toast.LENGTH_LONG).show();
                }  else {

                    printDemo();

                }
            }
        });

        carisumitem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent i = new Intent(getApplicationContext(), SumPerdana2.class);
//                    startActivity(i);

                if (namasales1.getText().toString().length() == 0) {                    //1
                    //jika form Email belum di isi / masih kosong
                    //iddelete1.setError("");
                    Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
                }  else {
                    aruskas.clear();
                    listtest1.setAdapter(null);
                    //listtest1.clear();
                    bayarList.clear();
                    list();

                    transferperdana();

                }
            }
        });

        //tanggal1.setText(getCurrentDate());
        list();

        transferperdana();

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


    protected void printDemo() {
        if (!mService.isAvailable()) {
            Log.i(TAG, "printText: perangkat tidak support bluetooth");
            return;
        }
        if (isPrinterReady) {

            String a = tanggal1.getText().toString();
            String b = namasales1.getText().toString();

            String c = transferperdana1.getText().toString();
            String d = cashperdana1.getText().toString();
            //String c = idoutletinputperdana1.getText().toString();
            // TOTAL LINE 32

            mService.write(PrinterCommands.ESC_ENTER);
            mService.write(PrinterCommands.besar);

            mService.write(PrinterCommands.ESC_ALIGN_CENTER);

            mService.write(PrinterCommands.biasa);
            //mService.sendMessage("Wisma Barcode Jakarta Barat", "");
            mService.sendMessage("RINCIAN SETORAN PERDANA", "");
            //mService.sendMessage("WISMA BARCODE JAKARTA BARAT", "");
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
                //String Sket = ""+bayarList.get(i).getKeterangan();
                //Double ket = Double.parseDouble(bayarList.get(i).getKeterangan());

                int Shh = Integer.parseInt(bayarList.get(i).getHarga());
                String Sjml = ""+quantity+" x @"+formatter.format(Shh);

                int isi = (32 - (Sjml.length() + Ssub.length()));
                String pr = Sjml + new String(new char[isi]).replace("\0", " ") + Ssub;
                mService.write(PrinterCommands.ESC_ALIGN_LEFT);
                mService.sendMessage(""+Snama, "");

                mService.write(PrinterCommands.ESC_ALIGN_CENTER);
                mService.sendMessage(""+pr, "");

//                mService.write(PrinterCommands.ESC_ALIGN_LEFT);
//                mService.sendMessage(""+Sket, "");

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
//            aruskas.clear();
//            listtest1.setAdapter(null);
            bayarList.clear();

            mService.write(PrinterCommands.ESC_ALIGN_LEFT);
            mService.sendMessage("Transfer : "+c, ""); //TR4ANSFER
            mService.write(PrinterCommands.ESC_ALIGN_LEFT);
            mService.sendMessage("Cash :"+d, ""); //tanggal
            mService.write(PrinterCommands.small);

            mService.write(PrinterCommands.FEED_PAPER_AND_CUT);
            mService.write(PrinterCommands.ESC_ENTER);

            mService.write(PrinterCommands.ESC_ALIGN_CENTER);
            mService.sendMessage("TERIMA KASIH", "");

//            mService.write(PrinterCommands.ESC_ALIGN_CENTER);
//            mService.sendMessage("Harga Sudah Termasuk PPN 10%", "");

            mService.write(PrinterCommands.FEED_PAPER_AND_CUT);

            //mService.sendMessage("Barang yang sudah dibeli tidak  bisa di kembalikan/di tukar.", "");
            mService.write(PrinterCommands.FEED_PAPER_AND_CUT);
            mService.write(PrinterCommands.FEED_PAPER_AND_CUT);
            mService.write(PrinterCommands.ESC_ENTER);



        } else {
            if (mService.isBTopen())
                startActivityForResult(new Intent(SumPerdanaItem.this, DeviceActivity.class), RC_CONNECT_DEVICE);
            else
                requestBluetooth();
        }

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

    private void list(){

        //swipe_refresh.setRefreshing(true);
        aruskas.clear();
        listtest1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "sumitemex.php" )
                .addBodyParameter("tanggal", tanggal1.getText().toString())
                .addBodyParameter("namasales", namasales1.getText().toString())
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

                                map.put("item",       responses.optString("item"));
                                map.put("qty",       responses.optString("qty"));
                                map.put("harga",       rupiahFormat.format(responses.optDouble("harga")));
                                map.put("total",       rupiahFormat.format(responses.optDouble("total")));


                                //item.setId(responses.getString("id"));
                                item.setNama(responses.getString("item"));
                                item.setHarga(responses.getString("harga"));
                                item.setJumlah(responses.getString("qty"));
                                //item.setKeterangan(responses.getString("keterangan"));

                                 total += Integer.parseInt(responses.getString("total"));
                                //map.put("tanggal",      responses.optString("tanggal"));
                                totalstring += Integer.parseInt(responses.getString("total"));



                                aruskas.add(map);
                                bayarList.add(item);
                            }

                            Adapter();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        ttl1.setText("Rp "+formatter.format(total));
                        String hasil3 = String.valueOf(totalstring);
                        ttl21.setText(hasil3);
                        total = 0;
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    private void Adapter(){

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.listsumitem,
                new String[] {"item","qty","harga","total"},
                new int[] {R.id.itemlistitem, R.id.qtylistitem, R.id.hargalistitem, R.id.totallistitem});

        listtest1.setAdapter(simpleAdapter);

        listtest1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //tanggal        = ((TextView) view.findViewById(R.id.tanggal)).getText().toString();
                //ListMenu();
            }
        });

        //swipe_refresh.setRefreshing(false);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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



    private void transferperdana() {

        AndroidNetworking.post(Config.host + "tfperdanabayar.php")
                //.addBodyParameter("idoutlet", idoutletinputperdana1.getText().toString())
                .addBodyParameter("namasales", namasales1.getText().toString())
                .addBodyParameter("tanggal", tanggal1.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response


                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);



                        transferperdana12.setText((response.optString("transfer")));
                        transferperdana1.setText(
                                rupiahFormat.format(response.optDouble("transfer")));


                        cashperdana12.setText((response.optString("cash")));
                        cashperdana1.setText(
                                rupiahFormat.format(response.optDouble("cash")));

                        totalperdana12.setText((response.optString("total")));
                        totalperdana1.setText(
                                rupiahFormat.format(response.optDouble("total")));

//                        tanggal1.setText((response.optString("tanggal")));
//                        jam1.setText((response.optString("jam")));

                        if ( transferperdana12.getText().toString().equals("null")){
                            transferperdana12.setText("0");
                            cashperdana12.setText("0");
                        }else{


                        }

                    }

                    @Override
                    public void onError(ANError error) {

                    }




                });

    }


}