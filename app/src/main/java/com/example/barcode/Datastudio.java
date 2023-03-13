package com.example.barcode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.barcode.helper.Config;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Locale;

public class Datastudio extends AppCompatActivity {
    TextView id1,link11,link21;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datastudio);


        id1 = (TextView) findViewById(R.id.id);
        link11 = (TextView) findViewById(R.id.monitoring);
        link21 = (TextView) findViewById(R.id.performance);

        datastudio();

        Linkify.addLinks(link11, Linkify.ALL);
        Linkify.addLinks(link21, Linkify.ALL);
    }



    private void datastudio() {
        AndroidNetworking.post(Config.host + "datastudio.php")
                .addBodyParameter("id", id1.getText().toString())
                //.addBodyParameter("bulan", sp.getSelectedItem().toString())
                //.addBodyParameter("bulan", bulan1.getText().toString())
                //.addBodyParameter("bulan1", bulan1.getText().toString())
                //.addBodyParameter("bulan2", bulan2.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response

                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
                        //idoutlet1.setText((response.optString("idoutlet")));
                        //namaoutlet1.setText((response.optString("namaoutlet")));
                        link11.setText((response.optString("monitoring")));
                        link21.setText((response.optString("performance")));
                        Linkify.addLinks(link11, Linkify.ALL);
                        Linkify.addLinks(link21, Linkify.ALL);
                        //actualhero1.setText((response.optString("actual")));
                        //achhero1.setText((response.optString("ach")));
                        //gaphero1.setText((response.optString("gap")));
                        //bulan1.setText((response.optString("bulan")));

                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }
}