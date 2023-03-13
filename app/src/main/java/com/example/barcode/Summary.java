package com.example.barcode;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.barcode.helper.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;


public class Summary extends AppCompatActivity {
    TextView text_masuk, text_keluar, text_total, namasf3;

    ListView listinput1;

    SwipeRefreshLayout swipe_refresh;

    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();

    TextView tanggal1, namasales1;
    Button btncari;

    public static TextView text_filter;
    public static String LINK, idlist, namaoutletlist, totallist;
    public static boolean filter;

    String query_kas, query_total;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private TextView tvDateResult;
    private Button btDatePicker;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary);



        LINK = Config.host + "history.php";
        idlist = "";
        namaoutletlist = "";
        totallist = "";
        //query_kas = ""; query_total = "";
        filter = false;

        dateFormatter = new SimpleDateFormat("yyyy/mm/dd", Locale.US);

        //sf = (EditText) findViewById(R.id.editText1);
        namasales1 = (TextView) findViewById(R.id.namasales);
        //bln = (EditText) findViewById(R.id.editText2);
        //btnsf = (Button) findViewById(R.id.btnemua);
        listinput1        = (ListView) findViewById(R.id.listinput);
        swipe_refresh   = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        tanggal1 = (TextView) findViewById(R.id.tanggal);

        //btDatePicker = (Button) findViewById(R.id.bt_datepicker);



        tanggal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });



        tanggal1.setText(getCurrentDate());

        Intent namasf2 = getIntent();
        String kiriman = namasf2.getStringExtra("namasales");

        namasales1.setText(kiriman);



        KasAdapter2();

        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                query_kas   =
                        "SELECT * FROM history WHERE namasales='namasales' and tanggal='tanggal'";

                LINK = Config.host + "history.php";
                KasAdapter2();
                //text_filter.setVisibility(View.GONE);
            }
        });




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
                tanggal1.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }


    public String getCurrentDate(){
        final Calendar c = Calendar.getInstance();
        int year, month, day;
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DATE);
        SimpleDateFormat contoh1 = new SimpleDateFormat("EEEE");

        String hariotomatis = contoh1.format(c.getTime());

        //return day +"/" + (month+1) + "/" + year;
        return year +"/" + (month+1) + "/" + day;
        //return (month+1) +"/" + day + "/" + year;



    }


    private void KasAdapter2(){

        swipe_refresh.setRefreshing(true);
        aruskas.clear(); listinput1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "history.php" )
                .addBodyParameter("namasales", namasales1.getText().toString())
                .addBodyParameter("tanggal", tanggal1.getText().toString())
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
                                map.put("namaoutlet",       responses.optString("namaoutlet"));
                                map.put("total",       responses.optString("total"));

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

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.listinput,
                new String[] {"id", "namaoutlet", "total"},
                new int[] {R.id.idlist, R.id.namaoutletlist, R.id.totallist});

        listinput1.setAdapter(simpleAdapter);
        listinput1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //no    = ((TextView) view.findViewById(R.id.no)).getText().toString();
                idlist    = ((TextView) view.findViewById(R.id.idlist)).getText().toString();
                namaoutletlist  = ((TextView) view.findViewById(R.id.namaoutletlist)).getText().toString();
                totallist  = ((TextView) view.findViewById(R.id.totallist)).getText().toString();

                //tanggal        = ((TextView) view.findViewById(R.id.tanggal)).getText().toString();
                //ListMenu();
            }
        });

        swipe_refresh.setRefreshing(false);
    }




}