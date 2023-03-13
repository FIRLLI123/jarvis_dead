package com.example.barcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MenuSummary extends AppCompatActivity {
    LinearLayout sumperdana1, sumvoucher1, sumlink1, stokperdana1, riwayatstok1;
    LinearLayout sumopperdana1, sumopvoucher1, sumrupiah1, sumtempo1, sumbayar1, sumtempoall, sumoutlet, mastergudang;
    TextView namasalesforward;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_summary);

        sumperdana1 = (LinearLayout) findViewById(R.id.sumperdana);
        sumvoucher1 = (LinearLayout) findViewById(R.id.sumvoucher);
        sumlink1 = (LinearLayout) findViewById(R.id.sumlink);

        sumopperdana1 = (LinearLayout) findViewById(R.id.sumopperdana);
        sumopvoucher1 = (LinearLayout) findViewById(R.id.sumopvoucher);


        sumrupiah1 = (LinearLayout) findViewById(R.id.sumrupiah);

        stokperdana1 = (LinearLayout) findViewById(R.id.stokperdana);
        riwayatstok1 = (LinearLayout) findViewById(R.id.riwayatstok);

        sumtempo1 = (LinearLayout) findViewById(R.id.sumtempo);
        sumbayar1 = (LinearLayout) findViewById(R.id.sumbayar);

        sumtempoall = (LinearLayout) findViewById(R.id.sumtempoall);

        sumoutlet = (LinearLayout) findViewById(R.id.sumoutlet);

        mastergudang = (LinearLayout) findViewById(R.id.mastergudang);

        namasalesforward = (TextView) findViewById(R.id.namasalesforward);

        Intent i = getIntent();
        String kiriman6 = i.getStringExtra("posisi");
        namasalesforward.setText(kiriman6);



        sumperdana1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                    Intent i = new Intent(getApplicationContext(), SumPerdana.class);
                    startActivity(i);
            }

        });

        sumvoucher1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(getApplicationContext(), SumVoucher.class);
                startActivity(i);
            }

        });

        sumlink1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(getApplicationContext(), SumLink.class);
                startActivity(i);
            }

        });




        sumopperdana1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(getApplicationContext(), SumStokSelisih.class);
                startActivity(i);
            }

        });

        sumopvoucher1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(getApplicationContext(), Sum_barang_rusak.class);
                startActivity(i);
            }

        });


        stokperdana1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(getApplicationContext(), SumStokPerdana.class);
                startActivity(i);
            }

        });

        riwayatstok1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(getApplicationContext(), SumRiwayatStok.class);
                startActivity(i);
            }

        });


        sumrupiah1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(getApplicationContext(), SumaryRupiah.class);
                startActivity(i);
            }

        });


        sumtempo1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(getApplicationContext(), Sumtempo.class);
                startActivity(i);
            }

        });

        sumbayar1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(getApplicationContext(), SumBayarTempo.class);
                startActivity(i);
            }

        });



        sumtempoall.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(getApplicationContext(), SumTEMPO_ALL.class);
                startActivity(i);
            }

        });


        sumoutlet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(getApplicationContext(), SumPENJUALAN_ALL.class);
                startActivity(i);
            }

        });


        mastergudang.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(getApplicationContext(), MasterGudang.class);
                startActivity(i);
            }

        });


        if (namasalesforward.getText().toString().equals("SALES FORCE")){
            //1
            mastergudang.setVisibility(View.GONE);


            //Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
        }else{

            mastergudang.setVisibility(View.VISIBLE);
        }


    }
}