package com.example.barcode;

import android.Manifest;
import android.app.DatePickerDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.barcode.Data.Data_Bayar;
import com.example.barcode.helper.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import de.hdodenhof.circleimageview.CircleImageView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class PrintPreviewVoucher extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, BluetoothHandler.HandlerInterface{
    TextView idoutletinputperdana1;
    TextView tanggalinputperdana1;
    TextView namaoutletinputperdana1;
    TextView namasalesinputperdana1;
    TextView url1;

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

    private List<Data_Bayar> bayarList;
    //RecyclerView listbayar1;
    ListView listbayar1;
    private GridLayoutManager gridLayout;
    //private Adapter_Bayar adapter_bayars;

    Button test, cariid1;
    int subTotal = 0;
    int total = 0;

    private TextView ttl;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    Bitmap bitmap;
    String encodedimage;
    CircleImageView img;
    private static final String apiurl="https://rekamitrayasa.com/reka/buktistruk.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_preview_voucher);

        dateFormatter = new SimpleDateFormat("yyyy/mm/dd", Locale.US);

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
        ttl = findViewById(R.id.testTotal);

        idoutletinputperdana1 = (TextView) findViewById(R.id.idoutletinputperdana);
        tanggalinputperdana1 = (TextView) findViewById(R.id.tanggalinputperdana);
        namaoutletinputperdana1 = (TextView) findViewById(R.id.namaoutletinputperdana);
        namasalesinputperdana1 = (TextView) findViewById(R.id.namasalesinputperdana);

        cariid1 = (Button) findViewById(R.id.cariid);

        Intent i = getIntent();
        String kiriman = i.getStringExtra("idoutlet");
        idoutletinputperdana1.setText(kiriman);
        String kiriman2 = i.getStringExtra("namaoutlet");
        namaoutletinputperdana1.setText(kiriman2);
        //String kiriman3 = i.getStringExtra("namasales");
        //namasalesinputperdana1.setText(kiriman3);
        String kiriman4 = i.getStringExtra("tanggal");
        tanggalinputperdana1.setText(kiriman4);



        setupBluetooth();

        // listbayar1 = (RecyclerView) findViewById(R.id.listBayar);
        listbayar1 = (ListView) findViewById(R.id.listBayar);
        bayarList = new ArrayList<>();

        gridLayout = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        //listbayar1.setLayoutManager(gridLayout);

        //adapter_bayars = new Adapter_Bayar(this, bayarList);
        //recyclerView.setAdapter(adapter_bayars);

        tanggalinputperdana1.setText(getCurrentDate());
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

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                printDemo();
            }
        });

    }

    protected void printDemo() {
        if (!mService.isAvailable()) {
            Log.i(TAG, "printText: perangkat tidak support bluetooth");
            return;
        }
        if (isPrinterReady) {

            String a = namaoutletinputperdana1.getText().toString();
            String b = tanggalinputperdana1.getText().toString();

            // TOTAL LINE 32

            mService.write(PrinterCommands.ESC_ENTER);
            mService.write(PrinterCommands.besar);

            mService.write(PrinterCommands.ESC_ALIGN_CENTER);

            mService.write(PrinterCommands.biasa);
            mService.sendMessage("PT.REKA MITRAYASA KOMUNIKATAMA", "");
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

                Data_Bayar data = bayarList.get(i);
                int quantity = Integer.parseInt(bayarList.get(i).getJumlah());
                int sub = quantity * Integer.parseInt(bayarList.get(i).getHarga());
                String Ssub = ""+formatter.format(sub);
                String Snama = ""+bayarList.get(i).getNama();

                int Shh = Integer.parseInt(bayarList.get(i).getHarga());
                String Sjml = ""+quantity+" x @"+formatter.format(Shh);

                int isi = (32 - (Sjml.length() + Ssub.length()));
                String pr = Sjml + new String(new char[isi]).replace("\0", " ") + Ssub;
                mService.write(PrinterCommands.ESC_ALIGN_LEFT);
                mService.sendMessage(""+Snama, "");

                mService.write(PrinterCommands.ESC_ALIGN_CENTER);
                mService.sendMessage(""+pr, "");

                subTotal += sub;


            }

            mService.sendMessage("................................", "");

            String subp = ""+formatter.format(subTotal);
            int isi = (32 - ("SubTotal".length() + subp.length()));
            String pr = "SubTotal" + new String(new char[isi]).replace("\0", " ") + subp;

            mService.sendMessage(""+pr, "");
            subTotal = 0;

            mService.write(PrinterCommands.FEED_PAPER_AND_CUT);
            mService.write(PrinterCommands.ESC_ENTER);

            mService.write(PrinterCommands.ESC_ALIGN_CENTER);
            mService.sendMessage("TERIMA KASIH", "");

            mService.write(PrinterCommands.ESC_ALIGN_CENTER);
            mService.sendMessage("Harga Sudah Termasuk PPN 10%", "");

            mService.write(PrinterCommands.FEED_PAPER_AND_CUT);

            mService.sendMessage("Barang yang sudah dibeli tidak  bisa di kembalikan/di tukar.", "");
            mService.write(PrinterCommands.FEED_PAPER_AND_CUT);
            mService.write(PrinterCommands.FEED_PAPER_AND_CUT);
            mService.write(PrinterCommands.ESC_ENTER);



        } else {
            if (mService.isBTopen())
                startActivityForResult(new Intent(PrintPreviewVoucher.this, DeviceActivity.class), RC_CONNECT_DEVICE);
            else
                requestBluetooth();
        }

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

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "listinputvoucher.php" )
                .addBodyParameter("idoutlet", idoutletinputperdana1.getText().toString())
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
                                Data_Bayar item = new Data_Bayar();
                                JSONObject responses    = jsonArray.getJSONObject(i);
                                HashMap<String, String> map = new HashMap<String, String>();
                                //map.put("no",         responses.optString("no"));
                                map.put("id",         responses.optString("id"));
                                map.put("item",       responses.optString("item"));
                                map.put("qty",       responses.optString("qty"));
                                map.put("harga",       responses.optString("harga"));
                                map.put("total",       responses.optString("total"));


                                item.setId(responses.getString("id"));
                                item.setNama(responses.getString("item"));
                                item.setHarga(responses.getString("harga"));
                                item.setJumlah(responses.getString("qty"));

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

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.listinputperdanaprint,
                new String[] {"item","qty","harga", "total"},
                new int[] {R.id.namalistperdana, R.id.qtylistperdana, R.id.hargalistperdana, R.id.totallistperdana});

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


}