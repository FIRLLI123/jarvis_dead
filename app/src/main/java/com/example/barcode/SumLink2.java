package com.example.barcode;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
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

public class SumLink2 extends AppCompatActivity {
    TextView tes1, namaoutletlist1, idoutletlist1;
    ListView listtest1;
    Button carisum1, cetak1;
    EditText namasaleslistsum21;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    public static String LINK, idoutletlist, namasaleslist, tanggallist, totallist, hargalistperdana, totallistperdana, namaoutletlist;
    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sum_link2);

        NumberFormat formatter = new DecimalFormat("#,###,###,###");

        LINK = Config.host + "history.php";
        idoutletlist = "";
        namasaleslist = "";
        tanggallist = "";
        namaoutletlist = "";
        totallist = "";
        hargalistperdana = "";
        totallistperdana = "";
        //totallist = "";
        //query_kas = ""; query_total = "";
        //filter = false;
        dateFormatter = new SimpleDateFormat("yyyy/MM/dd", Locale.US);

        tes1 = (TextView) findViewById(R.id.tanggal);

        namaoutletlist1 = (TextView) findViewById(R.id.namaoutletlist);
        idoutletlist1 = (TextView) findViewById(R.id.idoutletlist);
        listtest1 = (ListView) findViewById(R.id.listsum2);

        namasaleslistsum21 = (EditText) findViewById(R.id.namasaleslistsum2);

        cetak1 = (Button) findViewById(R.id.cetak);

        Intent i = getIntent();
        String kiriman = i.getStringExtra("tanggal");
        tes1.setText(kiriman);
        String kiriman2 = i.getStringExtra("namasales");
        namasaleslistsum21.setText(kiriman2);

        //list();

        tes1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showDateDialog();
            }
        });


        cetak1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (namaoutletlist1.getText().toString().length() == 0) {                    //1
                    //jika form Email belum di isi / masih kosong
                    //namasaleslistsum21.setError("harus diisi");
                    Toast.makeText(getApplicationContext(), "Silahkan Pilih Terlebih Dahulu", Toast.LENGTH_LONG).show();
                } else {

                    String a = idoutletlist1.getText().toString();
                    String b = namaoutletlist1.getText().toString();
                    String c = namasaleslistsum21.getText().toString();
                    String d = tes1.getText().toString();
                    Intent i = new Intent(getApplicationContext(), PrintPreviewLink.class);
                    i.putExtra("idoutlet", "" + a + "");
                    i.putExtra("namaoutlet", "" + b + "");
                    i.putExtra("namasales",""+c+"");
                    i.putExtra("tanggal", "" + d + "");
                    startActivity(i);


                }


            }
        });

        list();


    }

    private void list() {

        //swipe_refresh.setRefreshing(true);
        aruskas.clear();
        listtest1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post(Config.host + "sumlink2.php")
                .addBodyParameter("tanggal", tes1.getText().toString())
                .addBodyParameter("namasales", namasaleslistsum21.getText().toString())
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
                                //map.put("no",         responses.optString("no"));

                                map.put("idoutlet", responses.optString("idoutlet"));
                                map.put("namaoutlet", responses.optString("namaoutlet"));
                                map.put("item", responses.optString("item"));
                                //map.put("qty", responses.optString("qty"));
                                map.put("harga", responses.optString("harga"));
                                //map.put("total", rupiahFormat.format(responses.optDouble("total")));
                                //map.put("total", responses.optString("total"));


                                //total += Integer.parseInt(responses.getString("harga"))* Integer.parseInt(responses.getString("qty"));
                                //map.put("tanggal",      responses.optString("tanggal"));

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

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.listsumlink2,
                new String[]{"idoutlet", "namaoutlet", "item", "harga"},
                new int[]{R.id.idoutletlistsum2, R.id.namaoutletlistsum2, R.id.namalistsum2, R.id.totallistsumlink2});

        listtest1.setAdapter(simpleAdapter);

        listtest1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //no    = ((TextView) view.findViewById(R.id.no)).getText().toString();
                //namasaleslist = ((TextView) view.findViewById(R.id.namasaleslisttest)).getText().toString();
                idoutletlist = ((TextView) view.findViewById(R.id.idoutletlistsum2)).getText().toString();
                namaoutletlist = ((TextView) view.findViewById(R.id.namaoutletlistsum2)).getText().toString();


                //namasaleslistsum21.setText(namasaleslist);
                idoutletlist1.setText(idoutletlist);
                namaoutletlist1.setText(namaoutletlist);


                //tanggal        = ((TextView) view.findViewById(R.id.tanggal)).getText().toString();
                //ListMenu();
            }
        });


    }
}