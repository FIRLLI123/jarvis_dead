package com.example.barcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.barcode.helper.Config;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.Locale;

public class Menuvalidasi extends AppCompatActivity {
    TextView namasf2, namasf3, idoutmenu1, e1, namaoutmenu1, namasalesbackup1, tanggal1, jam1, nama221;

    Button order1, opperdana1, opvoucher1;

    //View input, lihatdata, inputvoucher, inputlink, inputstok1, inputop1, inputopvc1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuvalidasi);



        namasf2 = (TextView) findViewById(R.id.nama); //username
        namasf3 = (TextView) findViewById(R.id.nama2); //namaasli
        nama221 = (TextView) findViewById(R.id.nama22); //namaasli
        idoutmenu1 = (TextView) findViewById(R.id.idoutmenu); //idoutlet
        namaoutmenu1 = (TextView) findViewById(R.id.namaoutmenu); //namaoutlet
        namasalesbackup1 = (TextView) findViewById(R.id.namasalesbackup); //namaoutlet
        tanggal1 = (TextView) findViewById(R.id.tanggal); //namaoutlet
        jam1 = (TextView) findViewById(R.id.jam); //namaoutlet





        Intent namasf = getIntent();
        String kiriman = namasf.getStringExtra("namasales");
        namasf2.setText(kiriman);
        nama221.setText(kiriman);

        String kiriman2 = namasf.getStringExtra("idoutlet");
        idoutmenu1.setText(kiriman2);
        String kiriman3 = namasf.getStringExtra("namaoutlet");
        namaoutmenu1.setText(kiriman3);

        String kiriman4 = namasf.getStringExtra("namasales");
        namasf3.setText(kiriman4);

        order1 = (Button) findViewById(R.id.order);
        opperdana1 = (Button) findViewById(R.id.opperdana);
        opvoucher1 = (Button) findViewById(R.id.opvoucher);

//        input = (View) findViewById(R.id.imageButton1);
//        inputvoucher = (View) findViewById(R.id.imageButton3);
//        inputlink = (View) findViewById(R.id.imageButton4);
//        inputop1 = (View) findViewById(R.id.imageButton2);
//        //inputstok1 = (View) findViewById(R.id.imageButton5);
//        inputopvc1 = (View) findViewById(R.id.imageButton6);


        order1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                if ( namasf3.getText().toString().equals("Menunggu Koneksi...")){
                    Toast.makeText(getApplicationContext(), "Menunggu Koneksi",
                            Toast.LENGTH_LONG).show();
                } else {

                    // TODO Auto-generated method stub
                    String a = idoutmenu1.getText().toString();
                    String b = namaoutmenu1.getText().toString();
                    String c = namasf3.getText().toString();
                    Intent i = new Intent(getApplicationContext(), MenuVersi3.class);
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

        opperdana1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( namasf3.getText().toString().equals("Menunggu Koneksi...")){
                    Toast.makeText(getApplicationContext(), "Menunggu Koneksi",
                            Toast.LENGTH_LONG).show();
                } else {

                    // TODO Auto-generated method stub
                    String a = idoutmenu1.getText().toString();
                    String b = namaoutmenu1.getText().toString();
                    String c = namasf3.getText().toString();
                    Intent i = new Intent(getApplicationContext(), Opperdana.class);
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




        opvoucher1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                if ( namasf3.getText().toString().equals("Menunggu Koneksi...")){
                    Toast.makeText(getApplicationContext(), "Menunggu Koneksi",
                            Toast.LENGTH_LONG).show();
                } else {

                    // TODO Auto-generated method stub
                    String a = idoutmenu1.getText().toString();
                    String b = namaoutmenu1.getText().toString();
                    String c = namasf3.getText().toString();
                    Intent i = new Intent(getApplicationContext(), Opvoucher.class);
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



        //KasAdapter2();
        kunjunganterakhir();
        //nama2.setT

    }


    private void KasAdapter2() {

        AndroidNetworking.post(Config.host + "users.php")
                .addBodyParameter("username", namasf2.getText().toString())
                //.addBodyParameter("bulan1", bulan1.getText().toString())
                //.addBodyParameter("bulan2", bulan2.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response


                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
                        namasf2.setText((response.optString("username")));
                        namasf3.setText((response.optString("nama")));
                        nama221.setText((response.optString("nama")));

                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }


    private void kunjunganterakhir() {

        AndroidNetworking.post(Config.host + "kunjunganterakhir.php")
                .addBodyParameter("idoutlet", idoutmenu1.getText().toString())
                //.addBodyParameter("bulan1", bulan1.getText().toString())
                //.addBodyParameter("bulan2", bulan2.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response


                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
                        idoutmenu1.setText((response.optString("idoutlet")));
                        namasalesbackup1.setText((response.optString("namasalesbackup")));
                        tanggal1.setText((response.optString("tanggal")));
                        jam1.setText((response.optString("jam")));


                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });

    }




}