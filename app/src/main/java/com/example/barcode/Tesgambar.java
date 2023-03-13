package com.example.barcode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class Tesgambar extends AppCompatActivity {
    private ImageView imgbarang;
    private TextView txtnama, txtharga, txtstock;

    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    ArrayList<HashMap<String, String>> list_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tesgambar);

        String url = "http://rekamitrayasa.com/reka/getdata.php"; //sesuaikan dengan ip pc anda
        imgbarang = (ImageView)findViewById(R.id.imgbarang);
        txtnama = (TextView)findViewById(R.id.txtnama);
        txtharga = (TextView)findViewById(R.id.txtharga);
        txtstock = (TextView)findViewById(R.id.txtstock);

        requestQueue = Volley.newRequestQueue(Tesgambar.this);

        list_data = new ArrayList<HashMap<String, String>>();

        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("barang");
                    for (int a = 0; a < jsonArray.length(); a ++){
                        JSONObject json = jsonArray.getJSONObject(a);
                        HashMap<String, String> map  = new HashMap<String, String>();
                        map.put("id", json.getString("id"));
                        map.put("nama", json.getString("nama"));
                        map.put("gambar", json.getString("gambar"));
                        //map.put("harga_barang", json.getString("harga_barang"));
                        //map.put("stock", json.getString("stock"));
                        list_data.add(map);
                    }
                    Glide.with(getApplicationContext())
                            .load("http://rekamitrayasa.com/reka/img" + list_data.get(0).get("gambar"))
                            .crossFade()
                            .placeholder(R.mipmap.ic_launcher)
                            .into(imgbarang);
                    txtnama.setText(list_data.get(0).get("nama"));
                    //txtharga.setText(list_data.get(0).get("harga_barang"));
                    //txtstock.setText(list_data.get(0).get("stock"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Tesgambar.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(stringRequest);

    }
}