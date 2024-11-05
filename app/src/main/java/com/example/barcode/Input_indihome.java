package com.example.barcode;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Typeface;
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

public class Input_indihome extends AppCompatActivity {
    EditText nama_pelanggan, alamat, hp, email, namaitem, harga_1, harga_2, lokasi, berita, link_1, link_2, no_pasang;
    TextView kode, namasales, tanggal_pengajuan, tanggal_approve, tanggal_pasang, jam, kategori;
    TextView foto1, foto2;
    Bitmap bitmap, bitmap2;
    LinearLayout p1, p2, back;
    ImageView klik_img_1, klik_img_2, img4;
    String encodedimage;
    String encodedimage2;

    Button pilih_item;

    private String selectedPicture = "";
    private final int PICK_IMAGE_REQUEST = 71;

    private ProgressDialog pDialog;
    private Context context;

    LinearLayout btninput;

    private static final int REQUEST_SELECT_BARANG = 1;
    private static final int GALLERY_REQUEST_CODE = 123;


    private static final String apiurl = "https://rekamitrayasa.com/reka/input_indihome.php";



    LinearLayout linear_fisik, linear_elektrik;
    TextView t_fisik, t_elektrik;
    View v_fisik, v_elektrik;


    ArrayList<HashMap<String, String>> list_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_indihome);




        linear_fisik = (LinearLayout) findViewById(R.id.linear_fisik);
        linear_elektrik = (LinearLayout) findViewById(R.id.linear_elektrik);

        t_fisik = (TextView) findViewById(R.id.t_fisik);
        t_elektrik = (TextView) findViewById(R.id.t_elektrik);

        v_fisik = (View) findViewById(R.id.v_fisik);
        v_elektrik = (View) findViewById(R.id.v_elektrik);

        nama_pelanggan = (EditText) findViewById(R.id.nama_pelanggan);
        alamat = (EditText) findViewById(R.id.alamat);
        hp = (EditText) findViewById(R.id.hp);
        email = (EditText) findViewById(R.id.email);
        namaitem = (EditText) findViewById(R.id.namaitem);
        harga_1 = (EditText) findViewById(R.id.harga_1);
        harga_2 = (EditText) findViewById(R.id.harga_2);
        lokasi = (EditText) findViewById(R.id.lokasi);
        berita = (EditText) findViewById(R.id.berita);
        link_1 = (EditText) findViewById(R.id.link_1);
        link_2 = (EditText) findViewById(R.id.link_2);
        no_pasang = (EditText) findViewById(R.id.no_pasang);


        kode = (TextView) findViewById(R.id.kode);
        namasales = (TextView) findViewById(R.id.namasales);
        tanggal_pengajuan = (TextView) findViewById(R.id.tanggal_pengajuan);
        tanggal_approve = (TextView) findViewById(R.id.tanggal_approve);
        tanggal_pasang = (TextView) findViewById(R.id.tanggal_pasang);
        jam = (TextView) findViewById(R.id.jam);
        kategori = (TextView) findViewById(R.id.kategori);

        foto1 = (TextView) findViewById(R.id.foto1);
        foto2 = (TextView) findViewById(R.id.foto2);

        btninput = (LinearLayout) findViewById(R.id.btninput);
        pilih_item = (Button) findViewById(R.id.pilih_item);

        p1 = (LinearLayout) findViewById(R.id.p1);
        p2 = (LinearLayout) findViewById(R.id.p2);

        back = (LinearLayout) findViewById(R.id.back);

        klik_img_1 =(ImageView) findViewById(R.id.klik_img_1);
        klik_img_2 =(ImageView) findViewById(R.id.klik_img_2);

        context = Input_indihome.this;
        pDialog = new ProgressDialog(context);



        Intent kolomlogin = getIntent();
        String kiriman = kolomlogin.getStringExtra("kode");
        kode.setText(kiriman);
        String kiriman2 = kolomlogin.getStringExtra("namasales");
        namasales.setText(kiriman2);

        tanggal_pengajuan.setText(getCurrentDate());
        jam.setText(jamotomatis());


        klik_img_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {


                // TODO Auto-generated method stub
                requestStoragePermission();


            }

        });


        klik_img_2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {


                // TODO Auto-generated method stub
                requestStoragePermission2();


            }

        });


        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {


                // TODO Auto-generated method stub
                onBackPressed();


            }

        });

        pilih_item.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {


                // Ketika tombol untuk memilih barang di klik
                Intent intent = new Intent(getApplicationContext(), Item_indihome.class);
                String a = kategori.getText().toString();
                intent.putExtra("kategori", "" + a + "");
//                String b = bidang_.getText().toString();
//                intent.putExtra("bidang", "" + b + "");
                startActivityForResult(intent, REQUEST_SELECT_BARANG);
            }

        });



        btninput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nama_pelanggan.getText().toString().length() == 0) {                    //1
                    //jika form Email belum di isi / masih kosong
                    nama_pelanggan.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if (hp.getText().toString().length() == 0) {        //2
                    //jika form Username belum di isi / masih kosong
                    hp.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if (email.getText().toString().length() == 0) {        //2
                    //jika form Username belum di isi / masih kosong
                    email.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Item tidak tersedia", Toast.LENGTH_LONG).show();
                }else if (namaitem.getText().toString().length() == 0) {        //2
                    //jika form Username belum di isi / masih kosong
                    namaitem.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Item tidak tersedia", Toast.LENGTH_LONG).show();
                }else if (harga_1.getText().toString().length() == 0) {        //2
                    //jika form Username belum di isi / masih kosong
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if (lokasi.getText().toString().equals("null")) {        //2
                    //jika form Username belum di isi / masih kosong
                    Toast.makeText(getApplicationContext(), "Item Tidak Tersedia di Gudang mu", Toast.LENGTH_LONG).show();
//                }else if (berita.getText().toString().equals("null")) {        //2
//                    //jika form Username belum di isi / masih kosong
//                    Toast.makeText(getApplicationContext(), "Item Tidak Tersedia di Gudang mu", Toast.LENGTH_LONG).show();
                }else {
                    prosesdasboard();
                    //uploadtoserver();
                }

            }
        });



        t_fisik.setTypeface(Typeface.DEFAULT_BOLD);
        v_fisik.setVisibility(View.VISIBLE);

        t_elektrik.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        v_elektrik.setVisibility(View.INVISIBLE);

        kategori.setText("indihome");
        namaitem.setText("");
        foto1.setText("Foto KTP");
        foto2.setText("Foto Selfie");


        linear_fisik.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {


                t_fisik.setTypeface(Typeface.DEFAULT_BOLD);
                v_fisik.setVisibility(View.VISIBLE);

                t_elektrik.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                v_elektrik.setVisibility(View.INVISIBLE);

                kategori.setText("indihome");
                namaitem.setText("");
                foto1.setText("Foto KTP");
                foto2.setText("Foto Selfie");

                nama_pelanggan.setText("");
                alamat.setText("");
                hp.setText("");
                email.setText("");
                harga_1.setText("");
                harga_2.setText("");
                lokasi.setText("");
                berita.setText("");





            }

        });


        linear_elektrik.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {


                t_elektrik.setTypeface(Typeface.DEFAULT_BOLD);
                v_elektrik.setVisibility(View.VISIBLE);

                t_fisik.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                v_fisik.setVisibility(View.INVISIBLE);
                namaitem.setText("");

                kategori.setText("orbit");
                foto1.setText("Foto Item");
                foto2.setText("Foto Imei");



                nama_pelanggan.setText("");
                hp.setText("");
                email.setText("");
                alamat.setText("");
                harga_1.setText("");
                harga_2.setText("");
                lokasi.setText("");
                berita.setText("");


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

                uploadtoserver();

//                Intent i = new Intent(getApplicationContext(), Sum_dokumentasi_outlet_digi.class);
//                startActivity(i);

            }
        }.start();

    }

    private void requestStoragePermission() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE) // Mengubah izin dari CAMERA menjadi READ_EXTERNAL_STORAGE
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        // permission is granted
                        openGallery(); // Panggil metode untuk membuka galeri
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
                        openGallery2();

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
        AlertDialog.Builder builder = new AlertDialog.Builder(Input_indihome.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to access storage. You can grant them in app settings.");
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



    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }



//    private void openCamera() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent, 111);
//    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }

    private void openGallery2() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 112);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = null;
            try {
                Uri selectedImage = data.getData();
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                klik_img_1.setImageBitmap(bitmap);
                encodebitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (requestCode == 112 && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap2 = null;
            try {
                Uri selectedImage = data.getData();
                bitmap2 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                klik_img_2.setImageBitmap(bitmap2);
                encodebitmap2(bitmap2);
            } catch (IOException e) {
                e.printStackTrace();
            }



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

        }else if (requestCode == REQUEST_SELECT_BARANG) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    String a1 = data.getStringExtra("namaitem");
                    namaitem.setText(a1);

                    String a2 = data.getStringExtra("harga_1");
                    harga_1.setText(a2);

                    String a3 = data.getStringExtra("harga_2");
                    harga_2.setText(a3);

                }
            }


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

        final String kode1 = kode.getText().toString().trim();
        final String namasales1 = namasales.getText().toString().trim();
        final String nama_pelanggan1 = nama_pelanggan.getText().toString().trim();
        final String alamat1 = alamat.getText().toString().trim();
        final String hp1 = hp.getText().toString().trim();
        final String email1 = email.getText().toString().trim();

        final String url_1 = link_1.getText().toString().trim();
        final String url_2 = link_2.getText().toString().trim();

        final String namaitem1 = namaitem.getText().toString().trim();
        final String harga1_1 = harga_1.getText().toString().trim();
        final String harga2_1 = harga_2.getText().toString().trim();

        final String lokasi1 = lokasi.getText().toString().trim();
        //final String status1 = status.getText().toString().trim();
        final String berita1 = berita.getText().toString().trim();

        final String tanggal_pengajuan1 = tanggal_pengajuan.getText().toString().trim();
        final String tanggal_approve1 = tanggal_approve.getText().toString().trim();
        final String tanggal_pasang1 = tanggal_pasang.getText().toString().trim();

        final String jam1 = jam.getText().toString().trim();
        final String kategori1 = kategori.getText().toString().trim();
        final String no_pasang1 = no_pasang.getText().toString().trim();




        StringRequest request = new StringRequest(Request.Method.POST, apiurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //t1.setText("");
                //alasan1.setText("");
                klik_img_1.setImageResource(R.drawable.noimage);
                klik_img_2.setImageResource(R.drawable.noimage);

                link_1.setText("https://rekamitrayasa.com/reka/images/");
                link_2.setText("https://rekamitrayasa.com/reka/images/");

                Toast.makeText(getApplicationContext(), "SIMPAN VALIDASI PERDANA DAN FOTO BERHASIL", Toast.LENGTH_SHORT).show();
                String a = kode.getText().toString();
                Intent intent = new Intent(Input_indihome.this, Dashboard_indihome.class);
                intent.putExtra("kode", "" + a + "");

                startActivity(intent);
                finish();
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

                map.put("a1", kode1);
                map.put("a2", namasales1);
                map.put("a3", nama_pelanggan1);
                map.put("t1", alamat1);
                map.put("a4", hp1);
                map.put("a5", email1);

                map.put("upload", encodedimage);
                map.put("u1", url_1);
                map.put("upload_2", encodedimage2);
                map.put("u2", url_2);

                map.put("a6", namaitem1);
                map.put("a7", harga1_1);
                map.put("a8", harga2_1);
                map.put("a9", lokasi1);
                map.put("a10", berita1);

                map.put("a11", tanggal_pengajuan1);
                map.put("a12", tanggal_approve1);
                map.put("a13", tanggal_pasang1);
                map.put("a14", jam1);
                map.put("a15", kategori1);
                map.put("a16", no_pasang1);





                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);


    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


}