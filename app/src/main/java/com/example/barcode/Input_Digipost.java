package com.example.barcode;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
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
import com.bumptech.glide.Glide;
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
import java.io.IOException;
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

public class Input_Digipost extends AppCompatActivity implements View.OnClickListener {

    EditText idoutlet, namaoutlet, kecamatan, kelurahan, alasan, digipost, belanja_vcr, belanja_sp, url1, url2, url3, url4;
    Bitmap bitmap, bitmap2, bitmap3, bitmap4;
    String encodedimage;
    String encodedimage2;

    CircleImageView img, img2, img3, img4; //gambarnya
    Button btninput, foto, foto2, foto3, foto4; //button upload nya
    private static final String apiurl = "https://rekamitrayasa.com/reka/input_digipost.php";

    RadioButton d_s, d_b;
    RadioButton b_v_s, b_v_b;
    RadioButton b_sp_s, b_sp_b;

    TextView tanggal, jam, namasales, id;
    private String selectedPicture = "";
    private final int PICK_IMAGE_REQUEST = 71;

    private ProgressDialog pDialog;
    private Context context;
    Handler mHandler;

    ArrayList<HashMap<String, String>> list_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_digipost);





        list_data = new ArrayList<HashMap<String, String>>();


        idoutlet = (EditText) findViewById(R.id.idoutlet);
        namaoutlet = (EditText) findViewById(R.id.namaoutlet);
        kecamatan = (EditText) findViewById(R.id.kecamatan);
        kelurahan = (EditText) findViewById(R.id.kelurahan);


        digipost = (EditText) findViewById(R.id.digipost);
        belanja_vcr = (EditText) findViewById(R.id.belanja_vcr);
        belanja_sp = (EditText) findViewById(R.id.belanja_sp);

        url1 = (EditText) findViewById(R.id.foto_satu);
        url2 = (EditText) findViewById(R.id.foto_dua);


        img = (CircleImageView) findViewById(R.id.profile_image);
        img2 = (CircleImageView) findViewById(R.id.profile_image_2);


        btninput = (Button) findViewById(R.id.btninput); //input
        foto = (Button) findViewById(R.id.foto); //input
        foto2 = (Button) findViewById(R.id.foto_2); //input


        d_s = (RadioButton) findViewById(R.id.d_s);
        d_b = (RadioButton) findViewById(R.id.d_b);

        d_s.setOnClickListener(this);
        d_b.setOnClickListener(this);



        b_v_s = (RadioButton) findViewById(R.id.b_v_s);
        b_v_b = (RadioButton) findViewById(R.id.b_v_b);

        b_v_s.setOnClickListener(this);
        b_v_b.setOnClickListener(this);



        b_sp_s = (RadioButton) findViewById(R.id.b_sp_s);
        b_sp_b = (RadioButton) findViewById(R.id.b_sp_b);

        b_sp_s.setOnClickListener(this);
        b_sp_b.setOnClickListener(this);



        namasales = (TextView) findViewById(R.id.namasales);
        tanggal = (TextView) findViewById(R.id.tanggal);
        jam = (TextView) findViewById(R.id.jam);
        id = (TextView) findViewById(R.id.id);


        tanggal.setText(getCurrentDate());
        jam.setText(jamotomatis());


        context = Input_Digipost.this;
        pDialog = new ProgressDialog(context);


        btninput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (namaoutlet.getText().toString().length() == 0) {                    //1
                    //jika form Email belum di isi / masih kosong
                    namaoutlet.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if (kecamatan.getText().toString().length() == 0) {        //2
                    //jika form Username belum di isi / masih kosong
                    kecamatan.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if (kelurahan.getText().toString().length() == 0) {        //2
                    //jika form Username belum di isi / masih kosong
                    kelurahan.setError("Item tidak tersedia");
                    Toast.makeText(getApplicationContext(), "Item tidak tersedia", Toast.LENGTH_LONG).show();
                }else if (idoutlet.getText().toString().length() == 0) {        //2
                    //jika form Username belum di isi / masih kosong
                    kelurahan.setError("Item tidak tersedia");
                    Toast.makeText(getApplicationContext(), "Item tidak tersedia", Toast.LENGTH_LONG).show();
                }else if (digipost.getText().toString().length() == 0) {        //2
                    //jika form Username belum di isi / masih kosong
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if (belanja_vcr.getText().toString().equals("null")) {        //2
                    //jika form Username belum di isi / masih kosong
                    Toast.makeText(getApplicationContext(), "Item Tidak Tersedia di Gudang mu", Toast.LENGTH_LONG).show();
                }else if (belanja_sp.getText().toString().equals("null")) {        //2
                    //jika form Username belum di isi / masih kosong
                    Toast.makeText(getApplicationContext(), "Item Tidak Tersedia di Gudang mu", Toast.LENGTH_LONG).show();
                } else {
                    prosesdasboard();
                    uploadtoserver();
                }

            }
        });


        Intent kolomlogin = getIntent();
        String kiriman = kolomlogin.getStringExtra("namasales");
        namasales.setText(kiriman);
        String kiriman3 = kolomlogin.getStringExtra("idoutlet");
        idoutlet.setText(kiriman3);


        foto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {


                // TODO Auto-generated method stub
                requestStoragePermission();


            }

        });


        foto2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {


                // TODO Auto-generated method stub
                requestStoragePermission2();


            }

        });

        dataoutlet();
    }




    public void prosesdasboard(){

        new CountDownTimer(2000, 1000) {

            public void onTick(long millisUntilFinished) {
                showDialog();
                pDialog.setMessage("Mengupload Foto :"+ millisUntilFinished / 1000);
                //showDialog();
                //uploadtoserver();
                //absengalengkap();
                //KasAdapter3();
                pDialog.setCanceledOnTouchOutside(false);

                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                hideDialog();
                Intent i = new Intent(getApplicationContext(), Sum_dokumentasi_outlet_digi.class);
                startActivity(i);

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



    private void dataoutlet() {

        AndroidNetworking.post(Config.host + "panggil_dataoutlet.php")
                .addBodyParameter("idoutlet", idoutlet.getText().toString())
//                .addBodyParameter("namasales", namasalesinputperdana1.getText().toString())
//                .addBodyParameter("tanggal", tanggalinputperdana1.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response



                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);

                        namaoutlet.setText((response.optString("namaoutlet")));
                        kecamatan.setText((response.optString("kecamatan")));
                        kelurahan.setText((response.optString("kelurahan")));






                    }

                    @Override
                    public void onError(ANError error) {

                    }




                });

    }



    public String jamotomatis(){
        Calendar c1 = Calendar.getInstance();
        //SimpleDateFormat sdf1 = new SimpleDateFormat("d/M/yyyy h:m:s a");
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
        String strdate1 = sdf1.format(c1.getTime());
        return strdate1;



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


    private void requestStoragePermission2() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        // permission is granted
                        openCamera2();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(Input_Digipost.this);
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

    private void openCamera2() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 112);
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 111 && resultCode == RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            img.setImageBitmap(bitmap);
            encodebitmap(bitmap);

        } else if (requestCode == 112 && resultCode == RESULT_OK) {

            bitmap2 = (Bitmap) data.getExtras().get("data");
            img2.setImageBitmap(bitmap2);
            encodebitmap2(bitmap2);


        } else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                selectedPicture = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                byte[] bytesImage = Base64.decode(selectedPicture, Base64.DEFAULT);
                Glide.with(getApplicationContext())
                        .load(bytesImage)
                        .asBitmap()
                        .into(img4);
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

//            img.setImageResource(R.drawable.noimage);
//            img2.setImageResource(R.drawable.noimage);
//            img3.setImageResource(R.drawable.noimage);
//            img4.setImageResource(R.drawable.noimage);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void encodebitmap(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] byteofimages = byteArrayOutputStream.toByteArray();
        encodedimage = android.util.Base64.encodeToString(byteofimages, Base64.DEFAULT);
        //encodedimage=android.util.Base64.encodeToString(byteofimages, Base64.DEFAULT);
    }

    private void encodebitmap2(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] byteofimages = byteArrayOutputStream.toByteArray();
        encodedimage2 = android.util.Base64.encodeToString(byteofimages, Base64.DEFAULT);
        //encodedimage=android.util.Base64.encodeToString(byteofimages, Base64.DEFAULT);
    }




    private void uploadtoserver() {

        final String idoutlet1 = idoutlet.getText().toString().trim();
        final String namasales1 = namasales.getText().toString().trim();
        final String tanggal1 = tanggal.getText().toString().trim();
        final String jam1 = jam.getText().toString().trim();

        final String namaoutlet1 = namaoutlet.getText().toString().trim();
        final String kecamatan1 = kecamatan.getText().toString().trim();
        final String kelurahan1 = kelurahan.getText().toString().trim();
        //final String alasan1 = alasan.getText().toString().trim();


        final String digipost1 = digipost.getText().toString().trim();
        final String belanja_vcr1 = belanja_vcr.getText().toString().trim();
        final String belanja_sp1 = belanja_sp.getText().toString().trim();

        final String url_1 = url1.getText().toString().trim();
        final String url_2 = url2.getText().toString().trim();


        StringRequest request = new StringRequest(Request.Method.POST, apiurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //t1.setText("");
                //alasan1.setText("");
                img.setImageResource(R.drawable.noimage);
                img2.setImageResource(R.drawable.noimage);

                url1.setText("https://rekamitrayasa.com/reka/images/");
                url2.setText("https://rekamitrayasa.com/reka/images/");

                Toast.makeText(getApplicationContext(), "SIMPAN VALIDASI PERDANA DAN FOTO BERHASIL", Toast.LENGTH_SHORT).show();
//                String a = alasan1.getText().toString();
//                validasi1.setText(a);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
//                map.put("t1",idoutmenu);
//                map.put("t2",namaoutmenu);
//                map.put("t3",namasf);
//                map.put("t4",tanggalinputperdana);
//                map.put("t5",alasan);

                map.put("m0", idoutlet1);
                map.put("m1", namasales1);
                map.put("m2", tanggal1);
                map.put("m3", jam1);

                map.put("t1", namaoutlet1);
                map.put("t2", kecamatan1);
                map.put("t3", kelurahan1);

                map.put("t4", digipost1);
                map.put("t5", belanja_vcr1);
                map.put("t6", belanja_sp1);


                map.put("upload", encodedimage);
                map.put("t7", url_1);
                map.put("upload_2", encodedimage2);
                map.put("t8", url_2);

                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);


    }


    @Override
    public void onClick(View view) {

        int id = view.getId();

        //DIGIPOST
        if (id == R.id.d_s) {

            digipost.setText("SUDAH");

        } else if (id == R.id.d_b) {
            digipost.setText("BELUM");





            // BELANJA VCR
        } else if (id == R.id.b_v_s) {
            belanja_vcr.setText("SUDAH");


        } else if (id == R.id.b_v_b) {
            belanja_vcr.setText("BELUM");






            //BELANJA SP

        }else if (id == R.id.b_sp_s) {
            belanja_sp.setText("SUDAH");


        } else if (id == R.id.b_sp_b) {
            belanja_sp.setText("BELUM");


        }




    }





}