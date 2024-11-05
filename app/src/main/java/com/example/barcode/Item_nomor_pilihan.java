package com.example.barcode;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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


public class Item_nomor_pilihan extends AppCompatActivity {
    TextView tes1, namaoutletlist1, idoutletlist1, t_perdana, t_voucher;
    ListView listtest1;
    Button carisum1, cetak1, sumitem1;
    TextView namasaleslistsum21, namagudang;
    LinearLayout perdana, voucher;
    View perdana_v, voucher_v;
    EditText namabarang;

    LinearLayout klik_tanggal, cari_tanggal;
    TextView tanggalsampai;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    TextView id_stok, namaitem, stok, harga;
    LinearLayout caridatabarang;

    TextView idpilih, nomorpilih, hargapilih, statuspilih;

    private ProgressDialog pDialog;
    private Context context;


    TextView keseluruhan,tersedia,sold, hasilpilihan;

    public static String LINK,idlist, namaitemlist, stoklist, hargalist, idoutletlist, namasaleslist, tanggallist, totallist, hargalistperdana, totallistperdana, namaoutletlist, id_pilih, nomor_pilih, harga_pilih, status_pilih;
    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_nomor_pilihan);

        context = Item_nomor_pilihan.this;
        pDialog = new ProgressDialog(context);

        NumberFormat formatter = new DecimalFormat("#,###,###,###");
        dateFormatter = new SimpleDateFormat("yyyy/MM/dd", Locale.US);

        LINK = Config.host + "history.php";
        idoutletlist = "";
        namasaleslist = "";
        tanggallist = "";
        namaoutletlist = "";
        totallist = "";
        hargalistperdana = "";
        totallistperdana = "";
        idlist = "";
        namaitemlist = "";
        stoklist = "";
        hargalist = "";


        id_pilih = "";
        nomor_pilih = "";
        harga_pilih = "";
        status_pilih = "";

        //totallist = "";
        //query_kas = ""; query_total = "";
        //filter = false;
        //dateFormatter = new SimpleDateFormat("yyyy/MM/dd", Locale.US);

        keseluruhan = (TextView) findViewById(R.id.keseluruhan);
        tersedia = (TextView) findViewById(R.id.tersedia);
        sold = (TextView) findViewById(R.id.sold);

        hasilpilihan = (TextView) findViewById(R.id.hasilpilihan1);

        tes1 = (TextView) findViewById(R.id.tanggal);

        id_stok = (TextView) findViewById(R.id.id);
        namaitem = (TextView) findViewById(R.id.namaitem);
        stok = (TextView) findViewById(R.id.stok);
        harga = (TextView) findViewById(R.id.harga);

        idpilih = (TextView) findViewById(R.id.idpilih);
        nomorpilih = (TextView) findViewById(R.id.nomorpilih);
        hargapilih = (TextView) findViewById(R.id.hargapilih);
        statuspilih = (TextView) findViewById(R.id.statuspilih);

        klik_tanggal = (LinearLayout) findViewById(R.id.klik_tanggal);
        cari_tanggal = (LinearLayout) findViewById(R.id.cari_tanggal);


        caridatabarang = (LinearLayout) findViewById(R.id.caridatabarang);

        tanggalsampai = (TextView) findViewById(R.id.tanggalsampai);

        perdana = (LinearLayout) findViewById(R.id.perdana);
        voucher = (LinearLayout) findViewById(R.id.voucher);

        perdana_v = (View) findViewById(R.id.perdana_v);
        voucher_v = (View) findViewById(R.id.voucher_v);

        t_perdana = (TextView) findViewById(R.id.t_perdana);
        t_voucher = (TextView) findViewById(R.id.t_voucher);

        namaoutletlist1 = (TextView) findViewById(R.id.namaoutletlist);
        namagudang = (TextView) findViewById(R.id.namagudang);
        idoutletlist1 = (TextView) findViewById(R.id.idoutletlist);
        listtest1 = (ListView) findViewById(R.id.listsum2);

        namasaleslistsum21 = (TextView) findViewById(R.id.namasaleslistsum2);

        cetak1 = (Button) findViewById(R.id.cetak);
        sumitem1 = (Button) findViewById(R.id.sumitem);

        namabarang = (EditText) findViewById(R.id.namabarang);

        Intent i = getIntent();
        String kiriman2 = i.getStringExtra("namasales");
        namasaleslistsum21.setText(kiriman2);




        namagudang.setText("GUDANG PERDANA");

        t_perdana.setTextColor(getResources().getColor(R.color.white));
        perdana_v.setVisibility(View.VISIBLE);
        perdana_v.setBackground(getResources().getDrawable(R.color.white));

        t_voucher.setTextColor(getResources().getColor(R.color.navy));
        voucher_v.setVisibility(View.GONE);

        prosesdasboard1();

        hasilpilihan.setText("");

        keseluruhan.setBackground(getResources().getDrawable(R.drawable.custom11));
        keseluruhan.setTextColor(getResources().getColor(R.color.birutua));

        tersedia.setBackground(getResources().getDrawable(R.drawable.custom10));
        tersedia.setTextColor(getResources().getColor(R.color.abutua));

        sold.setBackground(getResources().getDrawable(R.drawable.custom10));
        sold.setTextColor(getResources().getColor(R.color.abutua));





        keseluruhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hasilpilihan.setText("");

                keseluruhan.setBackground(getResources().getDrawable(R.drawable.custom11));
                keseluruhan.setTextColor(getResources().getColor(R.color.birutua));

                tersedia.setBackground(getResources().getDrawable(R.drawable.custom10));
                tersedia.setTextColor(getResources().getColor(R.color.abutua));

                sold.setBackground(getResources().getDrawable(R.drawable.custom10));
                sold.setTextColor(getResources().getColor(R.color.abutua));



                prosesdasboard1();

            }
        });


        tersedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hasilpilihan.setText("available");
                keseluruhan.setBackground(getResources().getDrawable(R.drawable.custom10));
                keseluruhan.setTextColor(getResources().getColor(R.color.abutua));



                tersedia.setBackground(getResources().getDrawable(R.drawable.custom11));
                tersedia.setTextColor(getResources().getColor(R.color.birutua));

                sold.setBackground(getResources().getDrawable(R.drawable.custom10));
                sold.setTextColor(getResources().getColor(R.color.abutua));


                prosesdasboard1();

            }
        });


        sold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hasilpilihan.setText("sold");
                keseluruhan.setBackground(getResources().getDrawable(R.drawable.custom10));
                keseluruhan.setTextColor(getResources().getColor(R.color.abutua));



                tersedia.setBackground(getResources().getDrawable(R.drawable.custom10));
                tersedia.setTextColor(getResources().getColor(R.color.abutua));

                sold.setBackground(getResources().getDrawable(R.drawable.custom11));
                sold.setTextColor(getResources().getColor(R.color.birutua));



                prosesdasboard1();

            }
        });



        perdana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                namagudang.setText("GUDANG PERDANA");

                t_perdana.setTextColor(getResources().getColor(R.color.white));
                perdana_v.setVisibility(View.VISIBLE);
                perdana_v.setBackground(getResources().getDrawable(R.color.white));

                t_voucher.setTextColor(getResources().getColor(R.color.navy));
                voucher_v.setVisibility(View.GONE);

                list2();
                //prosesdasboard1();

            }
        });

        voucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                namagudang.setText("GUDANG VOUCHER");

                t_perdana.setTextColor(getResources().getColor(R.color.navy));
                perdana_v.setVisibility(View.GONE);


                t_voucher.setTextColor(getResources().getColor(R.color.white));
                voucher_v.setVisibility(View.VISIBLE);
                voucher_v.setBackground(getResources().getDrawable(R.color.white));

                list();

                //prosesdasboard1();

            }
        });

        tes1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showDateDialog();
            }
        });


        klik_tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        cari_tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(namagudang.getText().toString().equals("GUDANG PERDANA")){

                    list();
                }else{

                    list();

                }


            }
        });



//        cetak1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (namaoutletlist1.getText().toString().length() == 0) {                    //1
//                    //jika form Email belum di isi / masih kosong
//                    //namasaleslistsum21.setError("harus diisi");
//                    Toast.makeText(getApplicationContext(), "Silahkan Pilih Terlebih Dahulu", Toast.LENGTH_LONG).show();
//                } else {
//
//                    String a = idoutletlist1.getText().toString();
//                    String b = namaoutletlist1.getText().toString();
//                    String c = namasaleslistsum21.getText().toString();
//                    String d = tes1.getText().toString();
//                    Intent i = new Intent(getApplicationContext(), PrintPreviewEX.class);
//                    i.putExtra("idoutlet",""+a+"");
//                    i.putExtra("namaoutlet",""+b+"");
//                    i.putExtra("namasales",""+c+"");
//                    i.putExtra("tanggal",""+d+"");
//                    startActivity(i);
//
//
//                }
//
//
//            }
//        });

//        sumitem1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (namaoutletlist1.getText().toString().length() == 0) {                    //1
//                    //jika form Email belum di isi / masih kosong
//                    //namasaleslistsum21.setError("harus diisi");
//                    Toast.makeText(getApplicationContext(), "Silahkan Pilih Terlebih Dahulu", Toast.LENGTH_LONG).show();
//                } else {
//
//                    //String a = idoutletlist1.getText().toString();
//                    String b = namasaleslistsum21.getText().toString();
//                    //String c = namasalesinputperdana1.getText().toString();
//                    String d = tes1.getText().toString();
//                    Intent i = new Intent(getApplicationContext(), SumPerdanaItem.class);
//                    //i.putExtra("idoutlet",""+a+"");
//                    i.putExtra("namasales",""+b+"");
//                    //i.putExtra("namasales",""+c+"");
//                    i.putExtra("tanggal",""+d+"");
//                    startActivity(i);
//
//
//                }
//
//
//            }
//        });


        caridatabarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list();
            }
        });

        namabarang.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0)

                    list();

            }
        });

        tes1.setText(getCurrentDate());
        tanggalsampai.setText(getCurrentDate());
        //list2();

    }


    public void prosesdasboard1(){

        new CountDownTimer(1000, 1000) {

            public void onTick(long millisUntilFinished) {
                pDialog.setMessage("Loading..."+ millisUntilFinished / 1000);
                showDialog();
                pDialog.setCanceledOnTouchOutside(false);

                //keseluruhan();
                list();

                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                hideDialog();


            }
        }.start();

    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
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
                tanggalsampai.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }



    private void list() {

        //swipe_refresh.setRefreshing(true);
        aruskas.clear();
        listtest1.setAdapter(null);

        //Log.d("link", LINK );
        //AndroidNetworking.post(Config.host + "sumstok2_v2.php")
        AndroidNetworking.post(Config.host + "list_nomor_spesial.php")
                //.addBodyParameter("tanggal", tes1.getText().toString())
                .addBodyParameter("nomor", namabarang.getText().toString())
                .addBodyParameter("status", hasilpilihan.getText().toString())

                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            JSONArray jsonArray = response.optJSONArray("result");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Data_BayarEX item = new Data_BayarEX();
                                JSONObject responses = jsonArray.getJSONObject(i);
                                HashMap<String, String> map = new HashMap<String, String>();


                                map.put("id", responses.optString("id"));
                                map.put("nomor", responses.optString("nomor"));
                                map.put("harga",       responses.optString("harga"));
                                map.put("status",       responses.optString("status"));


                                aruskas.add(map);
                                //bayarList.add(item);
                            }

                            Adapter();

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

    private void Adapter() {

        CustomAdapter2 customAdapter = new CustomAdapter2(this, aruskas, R.layout.list_nomor_perdana_spesial,
                new String[]{"id","nomor", "harga", "status"},
                new int[]{R.id.idlist,R.id.nomorlist, R.id.hargalist, R.id.statuslist});

        listtest1.setAdapter(customAdapter);

        listtest1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //no    = ((TextView) view.findViewById(R.id.no)).getText().toString();
                id_pilih    = ((TextView) view.findViewById(R.id.idlist)).getText().toString();
                nomor_pilih  = ((TextView) view.findViewById(R.id.nomorlist)).getText().toString();
                harga_pilih  = ((TextView) view.findViewById(R.id.hargalist)).getText().toString();
                status_pilih  = ((TextView) view.findViewById(R.id.statuslist)).getText().toString();



                harga_pilih = harga_pilih.replace(",", "");

                idpilih.setText(id_pilih);
                nomorpilih.setText(nomor_pilih);
                hargapilih.setText(harga_pilih);
                statuspilih.setText(status_pilih);



                if (statuspilih.getText().toString().equals("sold")) {                    //1
                    //jika form Email belum di isi / masih kosong
                    //code.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Nomor Sold", Toast.LENGTH_LONG).show();
                }else {

                    String a = idpilih.getText().toString();
                    String b = nomorpilih.getText().toString();
                    String c = hargapilih.getText().toString();
                    String d = statuspilih.getText().toString();



                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("id", "" + a + "");
                    resultIntent.putExtra("nomor", "" + b + "");
                    resultIntent.putExtra("harga", "" + c + "");
                    resultIntent.putExtra("status", "" + d + "");
                    //resultIntent.putExtra("selectedBarang", selectedBarang);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        });
    }


    private class CustomAdapter2 extends SimpleAdapter {

        public CustomAdapter2(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);

            // Get the values from the data
            String status = aruskas.get(position).get("status");

            // Find the ImageView elements
            ImageView statusImageView1 = view.findViewById(R.id.available);
            ImageView statusImageView2 = view.findViewById(R.id.sold);

            // Define color codes
            int grayColor = Color.parseColor("#EAEAEA"); // Gray color for sold status
            int whiteColor = Color.parseColor("#FFFFFF"); // White color for available status

            // Set the visibility based on status
            if ("sold".equals(status)) {
                statusImageView2.setVisibility(View.VISIBLE);
                statusImageView1.setVisibility(View.GONE);
                view.setBackgroundColor(grayColor);
            } else {
                statusImageView1.setVisibility(View.VISIBLE);
                statusImageView2.setVisibility(View.GONE);
                view.setBackgroundColor(Color.WHITE);
            }

            return view;
        }
    }





    private void list2() {

        //swipe_refresh.setRefreshing(true);
        aruskas.clear();
        listtest1.setAdapter(null);

        //Log.d("link", LINK );
        //AndroidNetworking.post(Config.host + "sumstok2_v2.php")
        AndroidNetworking.post(Config.host + "pilihan_stok_perdana.php")
                //.addBodyParameter("tanggal", tes1.getText().toString())
                .addBodyParameter("namaitem", namabarang.getText().toString())
                .addBodyParameter("namasales", namasaleslistsum21.getText().toString())
                .addBodyParameter("namagudang", namagudang.getText().toString())
                .addBodyParameter("tanggaldari", namagudang.getText().toString())
                .addBodyParameter("tanggalsampai", tanggalsampai.getText().toString())
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
                                JSONObject responses = jsonArray.getJSONObject(i);
                                HashMap<String, String> map = new HashMap<String, String>();

//                                map.put("code", responses.optString("code"));
//                                map.put("namaitem", responses.optString("namaitem"));
//                                map.put("qty",       responses.optString("qty"));

                                map.put("id", responses.optString("id"));
                                map.put("namaitem", responses.optString("namaitem"));
                                map.put("sisa_stok",       responses.optString("sisa_stok"));
                                map.put("harga",       responses.optString("harga"));


                                aruskas.add(map);
                                //bayarList.add(item);
                            }

                            Adapter2();

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

    private void Adapter2() {

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.listsumstok2_v2_pilihan,
                new String[]{"id","namaitem", "sisa_stok", "harga"},
                new int[]{R.id.codelistsumstok2,R.id.namaitemlistsumstok2, R.id.qtylistsumstok2, R.id.hargalistsumstok2});

        listtest1.setAdapter(simpleAdapter);

        listtest1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //no    = ((TextView) view.findViewById(R.id.no)).getText().toString();
                idlist    = ((TextView) view.findViewById(R.id.codelistsumstok2)).getText().toString();
                namaitemlist  = ((TextView) view.findViewById(R.id.namaitemlistsumstok2)).getText().toString();
                stoklist  = ((TextView) view.findViewById(R.id.qtylistsumstok2)).getText().toString();
                hargalist  = ((TextView) view.findViewById(R.id.hargalistsumstok2)).getText().toString();



                hargalist = hargalist.replace(",", "");

                id_stok.setText(idlist);
                namaitem.setText(namaitemlist);
                stok.setText(stoklist);
                harga.setText(hargalist);



                if (stok.getText().toString().equals("0")) {                    //1
                    //jika form Email belum di isi / masih kosong
                    //code.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Stok Kosong", Toast.LENGTH_LONG).show();
                }else {

                    String a = id_stok.getText().toString();
                    String b = namaitem.getText().toString();
                    String c = stok.getText().toString();
                    String d = harga.getText().toString();



                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("id", "" + a + "");
                    resultIntent.putExtra("namaitem", "" + b + "");
                    resultIntent.putExtra("stok", "" + c + "");
                    resultIntent.putExtra("harga", "" + d + "");
                    //resultIntent.putExtra("selectedBarang", selectedBarang);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        });
    }


    public String getCurrentDate() {
        //SimpleDateFormat contoh1 = new SimpleDateFormat("yyyy/MM/dd");
        //String hariotomatis = contoh1.format(c.getTime());
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat contoh1 = new SimpleDateFormat("yyyy/MM/dd");



        String hariotomatis = contoh1.format(c.getTime());
        //tanggalinputperdana1.setText(hariotomatis);
        return hariotomatis;
        //return day +"/" + (month+1) + "/" + year;
        //return (month+1) +"/" + day + "/" + year;
        //return year + "/" + (month + 1) + "/" + day;



    }
}