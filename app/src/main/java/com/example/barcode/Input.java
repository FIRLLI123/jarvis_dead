package com.example.barcode;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Input extends AppCompatActivity  {

    private ProgressDialog pDialog;

    private Context context;


    TextView idoutletinput1;
    TextView tanggalinput1;
    TextView namaoutletinput1;
    TextView namasalesinput1;
    TextView iditem1;
    TextView p0k1;
    TextView hp0k1;
    TextView p2gb1;
    TextView hp2gb1;
    TextView p6gb1;
    TextView hp6gb1;
    TextView p9gb1;
    TextView hp9gb1;
    TextView p16gb1;
    TextView hp16gb1;
    TextView p35gb1;
    TextView hp35gb1;
    TextView p51gb1;
    TextView hp51gb1;
    TextView p70gb1;
    TextView hp70gb1;
    TextView v0k1;
    TextView hv0k1;
    TextView v15gb1;
    TextView hv15gb1;
    TextView v25gb1;
    TextView hv25gb1;
    TextView v35gb1;
    TextView hv35gb1;
    TextView v4gb1;
    TextView hv4gb1;
    TextView v10gb1;
    TextView hv10gb1;
    TextView v14gb1;
    TextView hv14gb1;
    TextView v70gb1;
    TextView hv70gb1;
    TextView link1;
    TextView total1;


    EditText p0k1_i;
    EditText p2gb1_i;
    EditText p6gb1_i;
    EditText p9gb1_i;
    EditText p16gb1_i;
    EditText p35gb1_i;
    EditText p51gb1_i;
    EditText p70gb1_i;
    EditText v0k1_i;
    EditText v15gb1_i;
    EditText v25gb1_i;
    EditText v35gb1_i;
    EditText v4gb1_i;
    EditText v10gb1_i;
    EditText v14gb1_i;
    EditText v70gb1_i;





    TextView idtotalp0k1_i;
    TextView idtotalp2gb1_i;
    TextView idtotalp6gb1_i;
    TextView idtotalp9gb1_i;
    TextView idtotalp16gb1_i;
    TextView idtotalp35gb1_i;
    TextView idtotalp51gb1_i;
    TextView idtotalp70gb1_i;
    TextView idtotalv0k1_i;
    TextView idtotalv15gb1_i;
    TextView idtotalv25gb1_i;
    TextView idtotalv35gb1_i;
    TextView idtotalv4gb1_i;
    TextView idtotalv10gb1_i;
    TextView idtotalv14gb1_i;
    TextView idtotalv70gb1_i;

    Button btntotal1, btninput1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input);
        context = Input.this;
        pDialog = new ProgressDialog(context);

        //item dan harga
        idoutletinput1 = (TextView) findViewById(R.id.idoutletinput);
        tanggalinput1 = (TextView) findViewById(R.id.tanggalinput);
        namaoutletinput1 = (TextView) findViewById(R.id.namaoutletinput);
        namasalesinput1 = (TextView) findViewById(R.id.namasalesinput);
        iditem1 = (TextView) findViewById(R.id.iditem);
        p0k1 = (TextView) findViewById(R.id.p0k);
        hp0k1 = (TextView) findViewById(R.id.hp0k);
        p2gb1 = (TextView) findViewById(R.id.p2gb);
        hp2gb1 = (TextView) findViewById(R.id.hp2gb);
        p6gb1 = (TextView) findViewById(R.id.p6gb);
        hp6gb1 = (TextView) findViewById(R.id.hp6gb);
        p9gb1 = (TextView) findViewById(R.id.p9gb);
        hp9gb1 = (TextView) findViewById(R.id.hp9gb);
        p16gb1 = (TextView) findViewById(R.id.p16gb);
        hp16gb1 = (TextView) findViewById(R.id.hp16gb);
        p35gb1 = (TextView) findViewById(R.id.p35gb);
        hp35gb1 = (TextView) findViewById(R.id.hp35gb);
        p51gb1 = (TextView) findViewById(R.id.p51gb);
        hp51gb1 = (TextView) findViewById(R.id.hp51gb);
        p70gb1 = (TextView) findViewById(R.id.p70gb);
        hp70gb1 = (TextView) findViewById(R.id.hp70gb);
        v0k1 = (TextView) findViewById(R.id.v0k);
        hv0k1 = (TextView) findViewById(R.id.hv0k);
        v15gb1 = (TextView) findViewById(R.id.v15gb);
        hv15gb1 = (TextView) findViewById(R.id.hv15gb);
        v25gb1 = (TextView) findViewById(R.id.v25gb);
        hv25gb1 = (TextView) findViewById(R.id.hv25gb);
        v35gb1 = (TextView) findViewById(R.id.v35gb);
        hv35gb1 = (TextView) findViewById(R.id.hv35gb);
        v4gb1 = (TextView) findViewById(R.id.v4gb);
        hv4gb1 = (TextView) findViewById(R.id.hv4gb);
        v10gb1 = (TextView) findViewById(R.id.v10gb);
        hv10gb1 = (TextView) findViewById(R.id.hv10gb);
        v14gb1 = (TextView) findViewById(R.id.v14gb);
        hv14gb1 = (TextView) findViewById(R.id.hv14gb);
        v70gb1 = (TextView) findViewById(R.id.v70gb);
        hv70gb1 = (TextView) findViewById(R.id.hv70gb);
        link1 = (TextView) findViewById(R.id.link);
        total1 = (TextView) findViewById(R.id.total);


        //input data
        p0k1_i = (EditText) findViewById(R.id.p0k_i);
        p2gb1_i = (EditText) findViewById(R.id.p2gb_i);
        p6gb1_i = (EditText) findViewById(R.id.p6gb_i);
        p9gb1_i = (EditText) findViewById(R.id.p9gb_i);
        p16gb1_i = (EditText) findViewById(R.id.p16gb_i);
        p35gb1_i = (EditText) findViewById(R.id.p35gb_i);
        p51gb1_i = (EditText) findViewById(R.id.p51gb_i);
        p70gb1_i = (EditText) findViewById(R.id.p70gb_i);
        v0k1_i = (EditText) findViewById(R.id.v0k_i);
        v15gb1_i = (EditText) findViewById(R.id.v15gb_i);
        v25gb1_i = (EditText) findViewById(R.id.v25gb_i);
        v35gb1_i = (EditText) findViewById(R.id.v35gb_i);
        v4gb1_i = (EditText) findViewById(R.id.v4gb_i);
        v10gb1_i = (EditText) findViewById(R.id.v10gb_i);
        v14gb1_i = (EditText) findViewById(R.id.v14gb_i);
        v70gb1_i = (EditText) findViewById(R.id.v70gb_i);



        idtotalp0k1_i = (TextView) findViewById(R.id.idtotalp0k_i);
        idtotalp2gb1_i = (TextView) findViewById(R.id.idtotalp2gb_i);
        idtotalp6gb1_i = (TextView) findViewById(R.id.idtotalp6gb_i);
        idtotalp9gb1_i = (TextView) findViewById(R.id.idtotalp9gb_i);
        idtotalp16gb1_i = (TextView) findViewById(R.id.idtotalp16gb_i);
        idtotalp35gb1_i = (TextView) findViewById(R.id.idtotalp35gb_i);
        idtotalp51gb1_i = (TextView) findViewById(R.id.idtotalp51gb_i);
        idtotalp70gb1_i = (TextView) findViewById(R.id.idtotalp70gb_i);
        idtotalv0k1_i = (TextView) findViewById(R.id.idtotalv0k_i);
        idtotalv15gb1_i = (TextView) findViewById(R.id.idtotalv15gb_i);
        idtotalv25gb1_i = (TextView) findViewById(R.id.idtotalv25gb_i);
        idtotalv35gb1_i = (TextView) findViewById(R.id.idtotalv35gb_i);
        idtotalv4gb1_i = (TextView) findViewById(R.id.idtotalv4gb_i);
        idtotalv10gb1_i = (TextView) findViewById(R.id.idtotalv10gb_i);
        idtotalv14gb1_i = (TextView) findViewById(R.id.idtotalv14gb_i);
        idtotalv70gb1_i = (TextView) findViewById(R.id.idtotalv70gb_i);

        //p0k1_i.addTextChangedListener(textWatcher());

        btntotal1 = (Button) findViewById(R.id.btntotal); //total
        btninput1 = (Button) findViewById(R.id.btninput); //input


        Intent i = getIntent();
        String kiriman = i.getStringExtra("idoutlet");
        idoutletinput1.setText(kiriman);
        String kiriman2 = i.getStringExtra("namaoutlet");
        namaoutletinput1.setText(kiriman2);
        String kiriman3 = i.getStringExtra("namasales");
        namasalesinput1.setText(kiriman3);

        tanggalinput1.setText(getCurrentDate());

        itemdanharga();
        //idoutlet();



        btntotal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(p0k1_i.getText().toString().length()==0) {                    //1
                    //jika form Email belum di isi / masih kosong
                    p0k1_i.setError("Kilometer harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if(p2gb1_i.getText().toString().length()==0){        //2
                    //jika form Username belum di isi / masih kosong
                    p2gb1_i.setError("harus diisi");Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if(p6gb1_i.getText().toString().length()==0){        //2
                    //jika form Username belum di isi / masih kosong
                    p6gb1_i.setError("harus diisi");Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if(p9gb1_i.getText().toString().length()==0){        //2
                    //jika form Username belum di isi / masih kosong
                    p9gb1_i.setError("harus diisi");Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if(p16gb1_i.getText().toString().length()==0){        //2
                    //jika form Username belum di isi / masih kosong
                    p16gb1_i.setError("harus diisi");Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if(p35gb1_i.getText().toString().length()==0){        //2
                    //jika form Username belum di isi / masih kosong
                    p35gb1_i.setError("harus diisi");Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if(p51gb1_i.getText().toString().length()==0){        //2
                    //jika form Username belum di isi / masih kosong
                    p51gb1_i.setError("harus diisi");Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if(p70gb1_i.getText().toString().length()==0){        //2
                    //jika form Username belum di isi / masih kosong
                    p70gb1_i.setError("harus diisi");Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if(v0k1_i.getText().toString().length()==0){        //2
                    //jika form Username belum di isi / masih kosong
                    v0k1_i.setError("harus diisi");Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if(v15gb1_i.getText().toString().length()==0){        //2
                    //jika form Username belum di isi / masih kosong
                    v15gb1_i.setError("harus diisi");Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if(v25gb1_i.getText().toString().length()==0){        //2
                    //jika form Username belum di isi / masih kosong
                    v25gb1_i.setError("harus diisi");Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if(v35gb1_i.getText().toString().length()==0){        //2
                    //jika form Username belum di isi / masih kosong
                    v35gb1_i.setError("harus diisi");Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if(v4gb1_i.getText().toString().length()==0){        //2
                    //jika form Username belum di isi / masih kosong
                    v4gb1_i.setError("harus diisi");Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if(v10gb1_i.getText().toString().length()==0){        //2
                    //jika form Username belum di isi / masih kosong
                    v10gb1_i.setError("harus diisi");Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if(v14gb1_i.getText().toString().length()==0){        //2
                    //jika form Username belum di isi / masih kosong
                    v14gb1_i.setError("harus diisi");Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if(v70gb1_i.getText().toString().length()==0){        //2
                    //jika form Username belum di isi / masih kosong
                    v70gb1_i.setError("harus diisi");Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if(link1.getText().toString().length()==0){        //2
                    //jika form Username belum di isi / masih kosong
                    link1.setError("harus diisi");Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else {

                    hitungHasil();
                    //save();
                }
            }
        });



        btninput1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(p0k1_i.getText().toString().length()==0) {                    //1
                    //jika form Email belum di isi / masih kosong
                    p0k1_i.setError("Kilometer harus diisi");
                    Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if(p2gb1_i.getText().toString().length()==0){        //2
                    //jika form Username belum di isi / masih kosong
                    p2gb1_i.setError("harus diisi");Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if(p6gb1_i.getText().toString().length()==0){        //2
                    //jika form Username belum di isi / masih kosong
                    p6gb1_i.setError("harus diisi");Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if(p9gb1_i.getText().toString().length()==0){        //2
                    //jika form Username belum di isi / masih kosong
                    p9gb1_i.setError("harus diisi");Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if(p16gb1_i.getText().toString().length()==0){        //2
                    //jika form Username belum di isi / masih kosong
                    p16gb1_i.setError("harus diisi");Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if(p35gb1_i.getText().toString().length()==0){        //2
                    //jika form Username belum di isi / masih kosong
                    p35gb1_i.setError("harus diisi");Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if(p51gb1_i.getText().toString().length()==0){        //2
                    //jika form Username belum di isi / masih kosong
                    p51gb1_i.setError("harus diisi");Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if(p70gb1_i.getText().toString().length()==0){        //2
                    //jika form Username belum di isi / masih kosong
                    p70gb1_i.setError("harus diisi");Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if(v0k1_i.getText().toString().length()==0){        //2
                    //jika form Username belum di isi / masih kosong
                    v0k1_i.setError("harus diisi");Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if(v15gb1_i.getText().toString().length()==0){        //2
                    //jika form Username belum di isi / masih kosong
                    v15gb1_i.setError("harus diisi");Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if(v25gb1_i.getText().toString().length()==0){        //2
                    //jika form Username belum di isi / masih kosong
                    v25gb1_i.setError("harus diisi");Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if(v35gb1_i.getText().toString().length()==0){        //2
                    //jika form Username belum di isi / masih kosong
                    v35gb1_i.setError("harus diisi");Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if(v4gb1_i.getText().toString().length()==0){        //2
                    //jika form Username belum di isi / masih kosong
                    v4gb1_i.setError("harus diisi");Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if(v10gb1_i.getText().toString().length()==0){        //2
                    //jika form Username belum di isi / masih kosong
                    v10gb1_i.setError("harus diisi");Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if(v14gb1_i.getText().toString().length()==0){        //2
                    //jika form Username belum di isi / masih kosong
                    v14gb1_i.setError("harus diisi");Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if(v70gb1_i.getText().toString().length()==0){        //2
                    //jika form Username belum di isi / masih kosong
                    v70gb1_i.setError("harus diisi");Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if(link1.getText().toString().length()==0){        //2
                    //jika form Username belum di isi / masih kosong
                    link1.setError("harus diisi");Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else {

                    hitungHasil();
                    save();
                }
            }
        });



    }



//langsung terhitung oleh edittext
    /*
    private TextWatcher textWatcher () {
        return new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hitungHasil();

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                hitungHasil();
            }

            @Override
            public void afterTextChanged(Editable s) {
                hitungHasil();
            }
        };
}
**/


    public String getCurrentDate(){
        final Calendar c = Calendar.getInstance();
        int year, month, day;
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DATE);
        SimpleDateFormat contoh1 = new SimpleDateFormat("EEEE");

        String hariotomatis = contoh1.format(c.getTime());

        //return day +"/" + (month+1) + "/" + year;
        //return (month+1) +"/" + day + "/" + year;
        return year +"/" + (month+1) + "/" + day;
    }

    private String getDateToday(){
        DateFormat dateFormat=new SimpleDateFormat("yyyy/mm/dd");
        Date date=new Date();
        String today=dateFormat.format(date);
        return today;

    }

    private void itemdanharga() {

        AndroidNetworking.post(Config.host + "itemdanharga.php")
                .addBodyParameter("iditem", iditem1.getText().toString())
                //.addBodyParameter("bulan1", bulan1.getText().toString())
                //.addBodyParameter("bulan2", bulan2.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response


                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
                        //idoutletinput1.setText((response.optString("idoutletinput")));      //1
                        //tanggalinput1.setText((response.optString("tanggalinput1")));           //2
                        //namaoutletinput1.setText((response.optString("namaoutletinput")));  //3
                        //namasalesinput1.setText((response.optString("namasalesinput")));   //4
                        iditem1.setText((response.optString("iditem")));  //5
                        p0k1.setText((response.optString("p0k")));   //6
                        hp0k1.setText((response.optString("hp0k")));  //7
                        p2gb1.setText((response.optString("p2gb")));  //8
                        hp2gb1.setText((response.optString("hp2gb")));  //9
                        p6gb1.setText((response.optString("p6gb")));    //10
                        hp6gb1.setText((response.optString("hp6gb")));  //11
                        p9gb1.setText((response.optString("p9gb")));   //12
                        hp9gb1.setText((response.optString("hp9gb")));  //13
                        p16gb1.setText((response.optString("p16gb")));  //14
                        hp16gb1.setText((response.optString("hp16gb")));  //15
                        p35gb1.setText((response.optString("p35gb")));   //16
                        hp35gb1.setText((response.optString("hp35gb")));  //17
                        p51gb1.setText((response.optString("p51gb")));   //18
                        hp51gb1.setText((response.optString("hp51gb")));  //19
                        p70gb1.setText((response.optString("p70gb")));  //20
                        hp70gb1.setText((response.optString("hp70gb")));  //21
                        v0k1.setText((response.optString("v0k")));   //22
                        hv0k1.setText((response.optString("hv0k")));  //23
                        v15gb1.setText((response.optString("v15gb")));  //24
                        hv15gb1.setText((response.optString("hv15gb")));  //25
                        v25gb1.setText((response.optString("v25gb")));    //26
                        hv25gb1.setText((response.optString("hv25gb")));  //27
                        v35gb1.setText((response.optString("v35gb")));   //28
                        hv35gb1.setText((response.optString("hv35gb")));  //29
                        v4gb1.setText((response.optString("v4gb")));  //30
                        hv4gb1.setText((response.optString("hv4gb")));  //31
                        v10gb1.setText((response.optString("v10gb")));  //32
                        hv10gb1.setText((response.optString("hv10gb")));  //33
                        v14gb1.setText((response.optString("v14gb")));   //34
                        hv14gb1.setText((response.optString("hv14gb")));   //35
                        v70gb1.setText((response.optString("v70gb")));   //36
                        hv70gb1.setText((response.optString("hv70gb")));   //37




                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }


    private void idoutlet() {

        AndroidNetworking.post(Config.host + "datamatsu.php")
                .addBodyParameter("idoutletinput", idoutletinput1.getText().toString())
                //.addBodyParameter("bulan1", bulan1.getText().toString())
                //.addBodyParameter("bulan2", bulan2.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response


                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
                        idoutletinput1.setText((response.optString("idoutletinput")));      //1
                        //tanggalinput1.setText((response.optString("tanggalinput1")));           //2
                        namaoutletinput1.setText((response.optString("namaoutletinput")));  //3
                        namasalesinput1.setText((response.optString("namasalesinput")));   //4




                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });


    }



    private void hitungHasil(){
        int item1 = Integer.parseInt(p0k1_i.getText().toString());
        int hitem1 = Integer.parseInt(hp0k1.getText().toString());
        int hasilitem1= item1*hitem1;
        idtotalp0k1_i.setText(String.valueOf(hasilitem1));

        int item2 = Integer.parseInt(p2gb1_i.getText().toString());
        int hitem2 = Integer.parseInt(hp2gb1.getText().toString());
        int hasilitem2= item2*hitem2;
        idtotalp2gb1_i.setText(String.valueOf(hasilitem2));

        int item3 = Integer.parseInt(p6gb1_i.getText().toString());
        int hitem3 = Integer.parseInt(hp6gb1.getText().toString());
        int hasilitem3= item3*hitem3;
        idtotalp6gb1_i.setText(String.valueOf(hasilitem3));

        int item4 = Integer.parseInt(p9gb1_i.getText().toString());
        int hitem4 = Integer.parseInt(hp9gb1.getText().toString());
        int hasilitem4= item4*hitem4;
        idtotalp9gb1_i.setText(String.valueOf(hasilitem4));

        int item5 = Integer.parseInt(p16gb1_i.getText().toString());
        int hitem5 = Integer.parseInt(hp16gb1.getText().toString());
        int hasilitem5= item5*hitem5;
        idtotalp16gb1_i.setText(String.valueOf(hasilitem5));

        int item6 = Integer.parseInt(p35gb1_i.getText().toString());
        int hitem6 = Integer.parseInt(hp35gb1.getText().toString());
        int hasilitem6= item6*hitem6;
        idtotalp35gb1_i.setText(String.valueOf(hasilitem6));

        int item7 = Integer.parseInt(p51gb1_i.getText().toString());
        int hitem7 = Integer.parseInt(hp51gb1.getText().toString());
        int hasilitem7= item7*hitem7;
        idtotalp51gb1_i.setText(String.valueOf(hasilitem7));

        int item8 = Integer.parseInt(p70gb1_i.getText().toString());
        int hitem8 = Integer.parseInt(hp70gb1.getText().toString());
        int hasilitem8= item8*hitem8;
        idtotalp70gb1_i.setText(String.valueOf(hasilitem8));

        int item9 = Integer.parseInt(v0k1_i.getText().toString());
        int hitem9 = Integer.parseInt(hv0k1.getText().toString());
        int hasilitem9= item9*hitem9;
        idtotalv0k1_i.setText(String.valueOf(hasilitem9));

        int item10 = Integer.parseInt(v15gb1_i.getText().toString());
        int hitem10 = Integer.parseInt(hv15gb1.getText().toString());
        int hasilitem10= item10*hitem10;
        idtotalv15gb1_i.setText(String.valueOf(hasilitem10));

        int item11 = Integer.parseInt(v25gb1_i.getText().toString());
        int hitem11 = Integer.parseInt(hv25gb1.getText().toString());
        int hasilitem11= item11*hitem11;
        idtotalv25gb1_i.setText(String.valueOf(hasilitem11));

        int item12 = Integer.parseInt(v35gb1_i.getText().toString());
        int hitem12 = Integer.parseInt(hv35gb1.getText().toString());
        int hasilitem12= item12*hitem12;
        idtotalv35gb1_i.setText(String.valueOf(hasilitem12));

        int item13 = Integer.parseInt(v4gb1_i.getText().toString());
        int hitem13 = Integer.parseInt(hv4gb1.getText().toString());
        int hasilitem13= item13*hitem13;
        idtotalv4gb1_i.setText(String.valueOf(hasilitem13));

        int item14 = Integer.parseInt(v10gb1_i.getText().toString());
        int hitem14 = Integer.parseInt(hv10gb1.getText().toString());
        int hasilitem14= item14*hitem14;
        idtotalv10gb1_i.setText(String.valueOf(hasilitem14));

        int item15 = Integer.parseInt(v14gb1_i.getText().toString());
        int hitem15 = Integer.parseInt(hv14gb1.getText().toString());
        int hasilitem15= item15*hitem15;
        idtotalv14gb1_i.setText(String.valueOf(hasilitem15));

        int item16 = Integer.parseInt(v70gb1_i.getText().toString());
        int hitem16 = Integer.parseInt(hv70gb1.getText().toString());
        int hasilitem16= item16*hitem16;
        idtotalv70gb1_i.setText(String.valueOf(hasilitem16));

        int hasillink = Integer.parseInt(link1.getText().toString());


        int  hasilitemtotal = hasilitem1+hasilitem2+hasilitem3+hasilitem4+hasilitem5+hasilitem6+hasilitem7+hasilitem8+
                            hasilitem9+hasilitem10+hasilitem11+hasilitem12+hasilitem13+hasilitem14+hasilitem15+hasilitem16+hasillink;
        total1.setText(String.valueOf(hasilitemtotal));

    }


    private void save() {
        pDialog.setMessage("Login Process...");
        showDialog();
        AndroidNetworking.post(Config.host + "input.php")
                .addBodyParameter("namasales", namasalesinput1.getText().toString())
                .addBodyParameter("idoutlet", idoutletinput1.getText().toString())
                .addBodyParameter("namaoutlet", namaoutletinput1.getText().toString())
                .addBodyParameter("tanggal", tanggalinput1.getText().toString())

                .addBodyParameter("item1", p0k1_i.getText().toString())
                .addBodyParameter("item2", p2gb1_i.getText().toString())
                .addBodyParameter("item3", p6gb1_i.getText().toString())
                .addBodyParameter("item4", p9gb1_i.getText().toString())
                .addBodyParameter("item5", p16gb1_i.getText().toString())
                .addBodyParameter("item6", p35gb1_i.getText().toString())
                .addBodyParameter("item7", p51gb1_i.getText().toString())
                .addBodyParameter("item8", p70gb1_i.getText().toString())
                .addBodyParameter("item9", v0k1_i.getText().toString())
                .addBodyParameter("item10", v15gb1_i.getText().toString())
                .addBodyParameter("item11", v25gb1_i.getText().toString())
                .addBodyParameter("item12", v35gb1_i.getText().toString())
                .addBodyParameter("item13", v4gb1_i.getText().toString())
                .addBodyParameter("item14", v10gb1_i.getText().toString())
                .addBodyParameter("item15", v14gb1_i.getText().toString())
                .addBodyParameter("item16", v70gb1_i.getText().toString())
                .addBodyParameter("link", link1.getText().toString())
                .addBodyParameter("total", total1.getText().toString())





                .setPriority(Priority.MEDIUM)
                .build()

                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response

                        Log.d("response", response.toString());

                        if (response.optString("response").toString().equals("success")) {
                            hideDialog();
                            gotoCourseActivity();
                            Toast.makeText(getApplicationContext(), "Berhasil disimpan",
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


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }
    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    private void gotoCourseActivity() {
        String a = namasalesinput1.getText().toString();
        Intent intent = new Intent(context, Summary.class);
        intent.putExtra("namasales",""+a+"");
        startActivity(intent);
        finish();


    }

}