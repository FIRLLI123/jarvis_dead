package com.example.barcode;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
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

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MenuVersi3 extends AppCompatActivity{
    public Spinner sp;

    TextView idoutmenu1;
    TextView tanggalinputperdana1;
    TextView namaoutmenu1;
    TextView namasf3, jaminput1;

    private ProgressDialog pDialog;
    private Context context;

    TextView lihatid1;

    ListView listinputperdana1;

    //SwipeRefreshLayout swipe_refresh;

    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();

    //TextView tanggal1, namasales1;

    public static String LINK, idlistperdana, namalistperdana, stoklist, hargalistperdana, totallistperdana, listketerangan;
    public static boolean filter;



    EditText idbarang1, namabarang1, alasan1, iddelete1, orderatautidak1, validasi1, validasib1, url1;
    Button cariid1;
    Button btntotal1, btninput1, btndelete1, btnrefresh1, btnupdate1, btnorder1, btnnext1, foto1, btnorderbyu7gb;

    String query_kas, query_total;

    ImageView btncamera;
    Button btnupload, btntes;
    Bitmap bitmap;
    String encodedimage;
    CircleImageView img;
    private static final String apiurl="https://rekamitrayasa.com/reka/alasanperdana.php";
    //RadioButton buka, tutup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_versi3);
        sp = (Spinner)findViewById(R.id.spinner);



        LINK = Config.host + "history.php";
        idlistperdana = "";
        namalistperdana = "";
        stoklist = "";
        //totallist = "";
        //query_kas = ""; query_total = "";
        filter = false;



        listinputperdana1        = (ListView) findViewById(R.id.listinputperdana);

        //idoutletinputperdana1 = (TextView) findViewById(R.id.idoutletinputperdana);
        tanggalinputperdana1 = (TextView) findViewById(R.id.tanggalinputperdana);
        //namaoutletinputperdana1 = (TextView) findViewById(R.id.namaoutletinputperdana);
        //namasalesinputperdana1 = (TextView) findViewById(R.id.namasalesinputperdana);

        //total1 = (TextView) findViewById(R.id.total);

        //keterangan1 = (EditText) findViewById(R.id.keterangan2);

        jaminput1 = (TextView) findViewById(R.id.jaminput);

        context = MenuVersi3.this;
        pDialog = new ProgressDialog(context);
        img=(CircleImageView)findViewById(R.id.profile_image);

        //lihatid1 = (TextView) findViewById(R.id.lihatid);

//        cariid1 = (Button) findViewById(R.id.cariid);
//        idbarang1 = (EditText) findViewById(R.id.idbarang);
//        namabarang1 = (EditText) findViewById(R.id.namabarang);
        alasan1 = (EditText) findViewById(R.id.alasan);
        orderatautidak1 = (EditText) findViewById(R.id.orderatautidak);
        validasi1 = (EditText) findViewById(R.id.validasi);
        validasib1 = (EditText) findViewById(R.id.validasib);
        url1 = (EditText) findViewById(R.id.rekamitra);
        //hargabarang1 = (EditText) findViewById(R.id.hargabarang);
        iddelete1 = (EditText) findViewById(R.id.iddelete);

        //btntotal1 = (Button) findViewById(R.id.btntotal); //total
        btninput1 = (Button) findViewById(R.id.btninput); //input
        btnorderbyu7gb = (Button) findViewById(R.id.btnorderbyu7gb); //input

        btndelete1 = (Button) findViewById(R.id.btndelete); //delete
        btnupdate1 = (Button) findViewById(R.id.btnupdate); //update
        btnrefresh1 = (Button) findViewById(R.id.btnrefresh); //refresh
        btnorder1 = (Button) findViewById(R.id.btnorder); //cetak

        btnnext1 = (Button) findViewById(R.id.btnnext); //cetak
        foto1 = (Button) findViewById(R.id.foto); //cetak


        idoutmenu1 = (TextView) findViewById(R.id.idoutletinputperdana); //idoutlet
        namaoutmenu1 = (TextView) findViewById(R.id.namaoutletinputperdana); //namaoutlet
        namasf3 = (TextView) findViewById(R.id.namasalesinputperdana); //namaasli



        Intent namasf = getIntent();
        String kiriman = namasf.getStringExtra("namasales");
        namasf3.setText(kiriman);
        String kiriman2 = namasf.getStringExtra("idoutlet");
        idoutmenu1.setText(kiriman2);
        String kiriman3 = namasf.getStringExtra("namaoutlet");
        namaoutmenu1.setText(kiriman3);



        tanggalinputperdana1.setText(getCurrentDate());
        jaminput1.setText(jamotomatis());




        btninput1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alasan1.getText().toString().length() == 0) {        //2
                    //jika form Username belum di isi / masih kosong
                    //stok1.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
                } else {

                    uploadtoserver();
                    //save();

                    KasAdapter2();

                }
            }
        });


        btnorderbyu7gb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                if ( namaoutmenu1.getText().toString().equals("Menunggu Koneksi...")){
                    Toast.makeText(getApplicationContext(), "Menunggu Koneksi",
                            Toast.LENGTH_LONG).show();
                } else {
                    validasib1.setText("order");
                    // TODO Auto-generated method stub
                    String a = idoutmenu1.getText().toString();
                    String b = namaoutmenu1.getText().toString();
                    String c = namasf3.getText().toString();
                    Intent i = new Intent(getApplicationContext(), InputPERDANAbyu.class);
                    i.putExtra("idoutlet",""+a+"");
                    i.putExtra("namaoutlet",""+b+"");
                    i.putExtra("namasales",""+c+"");
                    startActivity(i);
                    //String b = namasf2.getText().toString();
                    //validasib1.setText("ok");
                    // Intent i = new Intent(getApplicationContext(), Marsu1.class);
                    //i.putExtra("namasf", "" +a+ "");

                    // startActivity(i);

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
                    //order();
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





        btnorder1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                if ( namaoutmenu1.getText().toString().equals("Menunggu Koneksi...")){
                    Toast.makeText(getApplicationContext(), "Menunggu Koneksi",
                            Toast.LENGTH_LONG).show();
                } else {
                    validasib1.setText("order");
                    // TODO Auto-generated method stub
                    String a = idoutmenu1.getText().toString();
                    String b = namaoutmenu1.getText().toString();
                    String c = namasf3.getText().toString();
                    Intent i = new Intent(getApplicationContext(), InputPERDANA.class);
                    i.putExtra("idoutlet",""+a+"");
                    i.putExtra("namaoutlet",""+b+"");
                    i.putExtra("namasales",""+c+"");
                    startActivity(i);
                    //String b = namasf2.getText().toString();
                    //validasib1.setText("ok");
                    // Intent i = new Intent(getApplicationContext(), Marsu1.class);
                    //i.putExtra("namasf", "" +a+ "");

                    // startActivity(i);

                }



            }

        });


        btnnext1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                if ( namaoutmenu1.getText().toString().equals("Menunggu Koneksi...")){
                    Toast.makeText(getApplicationContext(), "Menunggu Koneksi",
                            Toast.LENGTH_LONG).show();
                }else if(validasi1.getText().toString().length() == 0 & validasib1.getText().toString().length() == 0){
                    Toast.makeText(getApplicationContext(), "Selesaikan validasi perdana terlebih dahulu", Toast.LENGTH_LONG).show();


                }else {

                    // TODO Auto-generated method stub
                    String a = idoutmenu1.getText().toString();
                    String b = namaoutmenu1.getText().toString();
                    String c = namasf3.getText().toString();
                    Intent i = new Intent(getApplicationContext(), MenuVersiVoucher.class);
                    i.putExtra("idoutlet",""+a+"");
                    i.putExtra("namaoutlet",""+b+"");
                    i.putExtra("namasales",""+c+"");
                    startActivity(i);
                    //String b = namasf2.getText().toString();

                    // Intent i = new Intent(getApplicationContext(), Marsu1.class);
                    //i.putExtra("namasf", "" +a+ "");

                    // startActivity(i);

                }



            }

        });

        foto1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                if ( namaoutmenu1.getText().toString().equals("Menunggu Koneksi...")){
                    Toast.makeText(getApplicationContext(), "Menunggu Koneksi",
                            Toast.LENGTH_LONG).show();
                } else if(alasan1.getText().toString().length() == 0){
                    Toast.makeText(getApplicationContext(), "silahkan pilih alasan terlebih dahulu", Toast.LENGTH_LONG).show();


                }else {

                    // TODO Auto-generated method stub
                    requestStoragePermission();

                }



            }

        });


        KasAdapter2();

        //showCustomDialog();
//        buka = (RadioButton) findViewById(R.id.buka);
//        tutup = (RadioButton) findViewById(R.id.tutup);
//
//        buka.setOnClickListener(this);
//        tutup.setOnClickListener(this);


        List<String> item = new ArrayList<>();

        //item.add("--SELECT--");
        item.add("STOK MASIH ADA");
        item.add("TIDAK ADA UANG");
        item.add("MEMBELI DI TEMPAT LAIN");
        item.add("NON TELKO");
        item.add("LAINNYA");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MenuVersi3.this,android.R.layout.simple_spinner_dropdown_item, item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
;
        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //alasan1.setText(sp.getSelectedItem().toString());




                    if (sp.getSelectedItem().toString().trim().equals("LAINNYA")) {
                        alasan1.setVisibility(View.VISIBLE);
                        alasan1.setText("");
                        alasan1.setHint("Lainnya");
                        //startActivity(new Intent(date.this,MainActivity.class));
                    } else {
                        alasan1.setVisibility(View.GONE);
                        alasan1.setText(sp.getSelectedItem().toString());
                    }
//                        startActivity(new Intent(date.this,august
//                                .class));


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        order();

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
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuVersi3.this);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==111 && resultCode==RESULT_OK)
        {
            bitmap=(Bitmap)data.getExtras().get("data");
            img.setImageBitmap(bitmap);
            encodebitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

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
        final String idoutmenu=idoutmenu1.getText().toString().trim();
        final String namaoutmenu=namaoutmenu1.getText().toString().trim();
        final String namasf=namasf3.getText().toString().trim();
        final String tanggalinputperdana=tanggalinputperdana1.getText().toString().trim();
        final String alasan=alasan1.getText().toString().trim();
        final String jaminput=jaminput1.getText().toString().trim();
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
                String a = alasan1.getText().toString();
                validasi1.setText(a);
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
                map.put("t5",alasan);
                map.put("t6",jaminput);
                map.put("upload",encodedimage);
                map.put("t7",url);
                return map;
            }
        };

        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);


    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(R.string.app_name)
                .setMessage("Kamu yakin ingin keluar?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
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
                .addBodyParameter("idoutlet", idoutmenu1.getText().toString())
                .addBodyParameter("namasales", namasf3.getText().toString())
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

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.listalasanperdana,
                new String[] {"id","alasan"},
                new int[] {R.id.idlistperdana, R.id.namalistperdana});

        listinputperdana1.setAdapter(simpleAdapter);
        listinputperdana1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //no    = ((TextView) view.findViewById(R.id.no)).getText().toString();
                idlistperdana    = ((TextView) view.findViewById(R.id.idlistperdana)).getText().toString();
                //namalistperdana  = ((TextView) view.findViewById(R.id.namalistperdana)).getText().toString();
                stoklist  = ((TextView) view.findViewById(R.id.namalistperdana)).getText().toString();



                iddelete1.setText(idlistperdana);
                //namabarang1.setText(namalistperdana);
                //alasan1.setText(stoklist);
                validasi1.setText(idlistperdana);


                //tanggal        = ((TextView) view.findViewById(R.id.tanggal)).getText().toString();
                //ListMenu();
            }
        });

        //swipe_refresh.setRefreshing(false);
    }

    /*private void save() {
        //pDialog.setMessage("Login Process...");
        //showDialog();
        AndroidNetworking.post(Config.host + "inputalasanperdana.php")
                .addBodyParameter("idoutlet", idoutmenu1.getText().toString())
                .addBodyParameter("namaoutlet", namaoutmenu1.getText().toString())
                .addBodyParameter("namasales", namasf3.getText().toString())
                .addBodyParameter("tanggal", tanggalinputperdana1.getText().toString())

                .addBodyParameter("alasan", alasan1.getText().toString())
                //.addBodyParameter("stok", alasan1.getText().toString())
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
                            String a = orderatautidak1.getText().toString();
                            validasi1.setText(a);

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
    }*/


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
        AndroidNetworking.post(Config.host + "deletealasanperdana.php")
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


    public void order() {
            String idoutletalert = namaoutmenu1.getText().toString();
        String namasalesalert = namasf3.getText().toString();
        String a = validasib1.getText().toString();
        new AlertDialog.Builder(this)

                .setIcon(R.drawable.lihatdatamenu)
                .setTitle(R.string.app_name)
                .setMessage("Hallo "+namasalesalert+", apakah outlet "+idoutletalert+" ingin order perdana?")
                .setPositiveButton("ORDER", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        validasib1.setText("next");

                        String a = idoutmenu1.getText().toString();
                        String b = namaoutmenu1.getText().toString();
                        String c = namasf3.getText().toString();
                        Intent i = new Intent(getApplicationContext(), InputPERDANA.class);
                        i.putExtra("idoutlet",""+a+"");
                        i.putExtra("namaoutlet",""+b+"");
                        i.putExtra("namasales",""+c+"");
                        startActivity(i);
                    }
                })
                .setNegativeButton("TIDAK ORDER", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }




//    @Override
//    public void onClick(View view) {
//
//        int id = view.getId();
//        if (id == R.id.buka) {
//
//
//            orderatautidak1.setText("order");
//            validasi1.setText("order");
//        }
//        else if (id == R.id.tutup){
//            orderatautidak1.setText("tidak order");
//            validasi1.setText("");
//
//        }
//
//
//
//
//
//    }


}