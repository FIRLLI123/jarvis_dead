package com.example.barcode;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.barcode.helper.Config;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Login extends AppCompatActivity {
    TextView idoutlogin1;
    TextView namaoutlogin1;
    TextView update1, namasales1;

    //EditText e1;
    //EditText e2;
    String text1, text2;

    private EditText e1;
    private EditText e2;
    private Context context;
    private AppCompatButton buttonLogin;
    private ProgressDialog pDialog;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        context = Login.this;

        pDialog = new ProgressDialog(context);
        e1 = (EditText) findViewById(R.id.editText2);//kolom login
        e2 = (EditText) findViewById(R.id.editText3);//kolom password
        button = (Button) findViewById(R.id.button);//button login
        idoutlogin1 = (TextView) findViewById(R.id.idoutlogin);//text id outlet
        namaoutlogin1 = (TextView) findViewById(R.id.namaoutlogin); //text namaoutlet
        update1 = (TextView) findViewById(R.id.update); //text untuk update aplkasi
        namasales1 = (TextView) findViewById(R.id.namasales); //id untuk update

//        Intent kolomlogin = getIntent();
//        String kiriman = kolomlogin.getStringExtra("namasales");
//        e1.setText(kiriman);
//        String kiriman2 = kolomlogin.getStringExtra("idoutlet");
//        idoutlogin1.setText(kiriman2);
//        String kiriman3 = kolomlogin.getStringExtra("namaoutlet");
//        namaoutlogin1.setText(kiriman3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginn();

                //e1 = (EditText) findViewById(R.id.editText2);//kolom login

            }
        });

        updateaplikasi();

    }

    private void loginn() {
        //Getting values from edit texts
        final String username = e1.getText().toString().trim();
        final String password = e2.getText().toString().trim();
        pDialog.setMessage("Login Process...");
        showDialog();

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppVar2.LOGIN_URL,
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
                            Toast.makeText(context, "Invalid username or password", Toast.LENGTH_LONG).show();
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
                params.put(AppVar2.KEY_EMAIL, username);
                params.put(AppVar2.KEY_PASSWORD, password);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        Volley.newRequestQueue(this).add(stringRequest);

    }

    private void gotoCourseActivity() {
        String a = e1.getText().toString();
        String b = idoutlogin1.getText().toString();
        String c = namaoutlogin1.getText().toString();
        Intent intent = new Intent(context, Awal2.class);
        //Intent i = new Intent(getApplicationContext(), Menuutamanew.class);
        intent.putExtra("username",""+a+"");
//        intent.putExtra("idoutlet",""+b+"");
//        intent.putExtra("namaoutlet",""+c+"");
        //startActivity(i);
        startActivity(intent);
        finish();
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    public void teks1(){
        e1 = (EditText) findViewById(R.id.editText2);

        String a = e1.getText().toString();
    }


    public void updateaplikasi(){
//        pDialog.setMessage("TUNGGU SEBENTAR, SEDANG VERIFIKASI DATA");
//        showDialog();
        KasAdapter2();
        new CountDownTimer(2000, 1000) {

            public void onTick(long millisUntilFinished) {
                pDialog.setMessage("TUNGGU SEBENTAR, SEDANG VERIFIKASI DATA :"+ millisUntilFinished / 1000);
                showDialog();
                pDialog.setCanceledOnTouchOutside(false);
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                hideDialog();

                if (update1.getText().toString().equals("15")) {
//            buttonabsendatang1.setEnabled(false);
//            buttonabsendatang1.setBackgroundColor(getResources().getColor(R.color.abu));
                    Toast.makeText(getApplicationContext(), "JARVIS siap di gunakan",
                            Toast.LENGTH_LONG).show();

                    //1
                    //jika form Email belum di isi / masih kosong
                    //link1.setError("harus diisi");
                    // Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if (update1.getText().toString().equals("null")) {
//            buttonabsendatang1.setEnabled(false);
//            buttonabsendatang1.setBackgroundColor(getResources().getColor(R.color.abu));
                    pDialog.setMessage("Koneksi mu Bermasalah, Silahkan Keluar Jarvis Terlebih Dahulu lalu Cek Koneksi mu");
                    showDialog();

                    //1
                    //jika form Email belum di isi / masih kosong
                    //link1.setError("harus diisi");
                    // Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else {

                    validasiaplikasi();

                }

                //mTextField.setText("done!");
            }
        }.start();

        //






    }




    private void KasAdapter2() {
//        pDialog.setMessage("TUNGGU SEBENTAR, SEDANG VERIFIKASI DATA");
//        showDialog();
        AndroidNetworking.post(Config.host + "updateaplikasi.php")
                .addBodyParameter("id", namasales1.getText().toString())
                //.addBodyParameter("tanggal", tanggal1.getText().toString())
                //.addBodyParameter("bulan1", bulan1.getText().toString())
                //.addBodyParameter("bulan2", bulan2.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response



                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
                        //username1.setText((response.optString("username")));
                        update1.setText((response.optString("update")));
                        //nama221.setText((response.optString("nama")));
                        hideDialog();
                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });





    }


    public void validasiaplikasi() {
        final Dialog dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setTitle("Hallo Team Reka Mitra");
        AlertDialog alertDialog;
        alertDialog = new AlertDialog.Builder(this)

                .setTitle(R.string.app_name)
                .setMessage("Jarvis mu versi lama, silahkan update terlebih dahulu ya...")
                .setPositiveButton("Update sekarang", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent i = new Intent();
                        i.setAction(Intent.ACTION_VIEW);
                        //i.addCategory(Intent.CATEGORY_APP_BROWSER);
                        i.setData(Uri.parse("http://rekamitrayasa.com/download"));
                        startActivity(i);

                    }
                })
                .setNegativeButton("Keluar Aplikasi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);


    }




}