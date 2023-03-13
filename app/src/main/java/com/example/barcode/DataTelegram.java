package com.example.barcode;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;

import com.androidnetworking.error.ANError;

import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.barcode.helper.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class DataTelegram extends AppCompatActivity {
    TextView idoutlet, namaoutlet;


    TextView satu_t, dua_t, tiga_t, empat_t, lima_t, enam_t, tujuh_t, delapan_t, sembilan_t, sepuluh_t;
    TextView sebelas_t, duabelas_t, tigabelas_t, empatbelas_t, limabelas_t, enambelas_t, tujuhbelas_t, delapanbelas_t, sembilanbelas_t, duapuluh_t;
    TextView duasatu_t, duadua_t,duatiga_t, duaempat_t,dualima_t, duaenam_t,duatujuh_t, duadelapan_t,duasembilan_t, tigapuluh_t;

    TextView tigasatu_t, tigadua_t,tigatiga_t, tigaempat_t,tigalima_t, tigaenam_t,tigatujuh_t, tigadelapan_t,tigasembilan_t, empatpuluh_t;
    TextView empatsatu_t, empatdua_t,empattiga_t, empatempat_t,empatlima_t, empatenam_t,empattujuh_t, empatdelapan_t,empatsembilan_t, limapuluh_t;
    TextView limasatu_t, limadua_t,limatiga_t, limaempat_t,limalima_t, limaenam_t,limatujuh_t, limadelapan_t,limasembilan_t, enampuluh_t;
    TextView enamsatu_t, enamdua_t,enamtiga_t, enamempat_t,enamlima_t, enamenam_t,enamtujuh_t, enamdelapan_t,enamsembilan_t, tujuhpuluh_t;
    TextView tujuhsatu_t, tujuhdua_t,tujuhtiga_t, tujuhempat_t,tujuhlima_t, tujuhenam_t,tujuhtujuh_t, tujuhdelapan_t,tujuhsembilan_t, delapanpuluh_t;
    TextView delapansatu_t, delapandua_t,delapantiga_t, delapanempat_t,delapanlima_t, delapanenam_t,delapantujuh_t, delapandelapan_t,delapansembilan_t, sembilanpuluh_t;
    TextView sembilansatu_t, sembilandua_t,sembilantiga_t, sembilanempat_t,sembilanlima_t, sembilanenam_t,sembilantujuh_t, sembilandelapan_t,sembilansembilan_t, seratus_t;
    TextView seratussatu_t, seratusdua_t,seratustiga_t, seratusempat_t,seratuslima_t, seratusenam_t,seratustujuh_t, seratusdelapan_t,seratussembilan_t, seratussepuluh_t;
    TextView seratussebelas_t, seratusduabelas_t,seratustigabelas_t, seratusempatbelas_t;


    private ProgressDialog pDialog;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_telegram);



        context = DataTelegram.this;
        pDialog = new ProgressDialog(context);




        idoutlet = (TextView) findViewById(R.id.idoutlet);
        namaoutlet = (TextView) findViewById(R.id.namaoutlet);

        satu_t = (TextView) findViewById(R.id.satu_t);
        dua_t = (TextView) findViewById(R.id.dua_t);
        tiga_t = (TextView) findViewById(R.id.tiga_t);
        empat_t = (TextView) findViewById(R.id.empat_t);
        lima_t = (TextView) findViewById(R.id.lima_t);
        enam_t = (TextView) findViewById(R.id.enam_t);
        tujuh_t = (TextView) findViewById(R.id.tujuh_t);
        delapan_t = (TextView) findViewById(R.id.delapan_t);
        sembilan_t = (TextView) findViewById(R.id.sembilan_t);
        sepuluh_t = (TextView) findViewById(R.id.sepuluh_t);
        sebelas_t = (TextView) findViewById(R.id.sebelas_t);
        duabelas_t = (TextView) findViewById(R.id.duabelas_t);
        tigabelas_t = (TextView) findViewById(R.id.tigabelas_t);
        empatbelas_t = (TextView) findViewById(R.id.empatbelas_t);
        limabelas_t = (TextView) findViewById(R.id.limabelas_t);
        enambelas_t = (TextView) findViewById(R.id.enambelas_t);
        tujuhbelas_t = (TextView) findViewById(R.id.tujuhbelas_t);
        delapanbelas_t = (TextView) findViewById(R.id.delapanbelas_t);
        sembilanbelas_t = (TextView) findViewById(R.id.sembilanbelas_t);
        duapuluh_t = (TextView) findViewById(R.id.duapuluh_t);

        duasatu_t = (TextView) findViewById(R.id.duasatu_t);
        duadua_t = (TextView) findViewById(R.id.duadua_t);
        duatiga_t = (TextView) findViewById(R.id.duatiga);
        duaempat_t = (TextView) findViewById(R.id.duaempat);
        dualima_t = (TextView) findViewById(R.id.dualima);
        duaenam_t = (TextView) findViewById(R.id.duaenam);
        duatujuh_t = (TextView) findViewById(R.id.duatujuh);
        duadelapan_t = (TextView) findViewById(R.id.duadelapan);
        duasembilan_t = (TextView) findViewById(R.id.duasembilan);
        tigapuluh_t = (TextView) findViewById(R.id.tigapuluh);

        tigasatu_t = (TextView) findViewById(R.id.tigasatu);
        tigadua_t = (TextView) findViewById(R.id.tigadua);
        tigatiga_t = (TextView) findViewById(R.id.tigatiga);
        tigaempat_t = (TextView) findViewById(R.id.tigaempat);
        tigalima_t = (TextView) findViewById(R.id.tigalima);
        tigaenam_t = (TextView) findViewById(R.id.tigaenam);
        tigatujuh_t = (TextView) findViewById(R.id.tigatujuh);
        tigadelapan_t = (TextView) findViewById(R.id.tigadelapan);
        tigasembilan_t = (TextView) findViewById(R.id.tigasembilan);
        empatpuluh_t = (TextView) findViewById(R.id.empatpuluh);

        empatsatu_t = (TextView) findViewById(R.id.empatsatu);
        empatdua_t = (TextView) findViewById(R.id.empatdua);
        empattiga_t = (TextView) findViewById(R.id.empattiga);
        empatempat_t = (TextView) findViewById(R.id.empatempat);
        empatlima_t = (TextView) findViewById(R.id.empatlima);
        empatenam_t = (TextView) findViewById(R.id.empatenam);
        empattujuh_t = (TextView) findViewById(R.id.empattujuh);
        empatdelapan_t = (TextView) findViewById(R.id.empatdelapan);
        empatsembilan_t = (TextView) findViewById(R.id.empatsembilan);
        limapuluh_t = (TextView) findViewById(R.id.limapuluh);

        limasatu_t = (TextView) findViewById(R.id.limasatu);
        limadua_t = (TextView) findViewById(R.id.limadua);
        limatiga_t = (TextView) findViewById(R.id.limatiga);
        limaempat_t = (TextView) findViewById(R.id.limaempat);
        limalima_t = (TextView) findViewById(R.id.limalima);
        limaenam_t = (TextView) findViewById(R.id.limaenam);
        limatujuh_t = (TextView) findViewById(R.id.limatujuh);
        limadelapan_t = (TextView) findViewById(R.id.limadelapan);
        limasembilan_t = (TextView) findViewById(R.id.limasembilan);
        enampuluh_t = (TextView) findViewById(R.id.enampuluh);

        enamsatu_t = (TextView) findViewById(R.id.enamsatu);
        enamdua_t = (TextView) findViewById(R.id.enamdua);
        enamtiga_t = (TextView) findViewById(R.id.enamtiga);
        enamempat_t = (TextView) findViewById(R.id.enamempat);
        enamlima_t = (TextView) findViewById(R.id.enamlima);
        enamenam_t = (TextView) findViewById(R.id.enamenam);
        enamtujuh_t = (TextView) findViewById(R.id.enamtujuh);
        enamdelapan_t = (TextView) findViewById(R.id.enamdelapan);
        enamsembilan_t = (TextView) findViewById(R.id.enamsembilan);
        tujuhpuluh_t = (TextView) findViewById(R.id.tujuhpuluh);

        tujuhsatu_t = (TextView) findViewById(R.id.tujuhsatu);
        tujuhdua_t = (TextView) findViewById(R.id.tujuhdua);
        tujuhtiga_t = (TextView) findViewById(R.id.tujuhtiga);
        tujuhempat_t = (TextView) findViewById(R.id.tujuhempat);
        tujuhlima_t = (TextView) findViewById(R.id.tujuhlima);
        tujuhenam_t = (TextView) findViewById(R.id.tujuhenam);
        tujuhtujuh_t = (TextView) findViewById(R.id.tujuhtujuh);
        tujuhdelapan_t = (TextView) findViewById(R.id.tujuhdelapan);
        tujuhsembilan_t = (TextView) findViewById(R.id.tujuhsembilan);
        delapanpuluh_t = (TextView) findViewById(R.id.delapanpuluh);

        delapansatu_t = (TextView) findViewById(R.id.delapansatu);
        delapandua_t = (TextView) findViewById(R.id.delapandua);
        delapantiga_t = (TextView) findViewById(R.id.delapantiga);
        delapanempat_t = (TextView) findViewById(R.id.delapanempat);
        delapanlima_t = (TextView) findViewById(R.id.delapanlima);
        delapanenam_t = (TextView) findViewById(R.id.delapanenam);
        delapantujuh_t = (TextView) findViewById(R.id.delapantujuh);
        delapandelapan_t = (TextView) findViewById(R.id.delapandelapan);
        delapansembilan_t = (TextView) findViewById(R.id.delapansembilan);
        sembilanpuluh_t = (TextView) findViewById(R.id.sembilanpuluh);

        sembilansatu_t = (TextView) findViewById(R.id.sembilansatu);
        sembilandua_t = (TextView) findViewById(R.id.sembilandua);
        sembilantiga_t = (TextView) findViewById(R.id.sembilantiga);
        sembilanempat_t = (TextView) findViewById(R.id.sembilanempat);
        sembilanlima_t = (TextView) findViewById(R.id.sembilanlima);
        sembilanenam_t = (TextView) findViewById(R.id.sembilanenam);
        sembilantujuh_t = (TextView) findViewById(R.id.sembilantujuh);
        sembilandelapan_t = (TextView) findViewById(R.id.sembilandelapan);
        sembilansembilan_t = (TextView) findViewById(R.id.sembilansembilan);
        seratus_t = (TextView) findViewById(R.id.seratus);

        seratussatu_t = (TextView) findViewById(R.id.seratussatu);
        seratusdua_t = (TextView) findViewById(R.id.seratusdua);
        seratustiga_t = (TextView) findViewById(R.id.seratustiga);
        seratusempat_t = (TextView) findViewById(R.id.seratusempat);
        seratuslima_t = (TextView) findViewById(R.id.seratuslima);
        seratusenam_t = (TextView) findViewById(R.id.seratusenam);
        seratustujuh_t = (TextView) findViewById(R.id.seratustujuh);
        seratusdelapan_t = (TextView) findViewById(R.id.seratusdelapan);
        seratussembilan_t = (TextView) findViewById(R.id.seratussembilan);
        seratussepuluh_t = (TextView) findViewById(R.id.seratussepuluh);

        seratussebelas_t = (TextView) findViewById(R.id.seratussebelas);
        seratusduabelas_t = (TextView) findViewById(R.id.seratusduabelas);
        seratustigabelas_t = (TextView) findViewById(R.id.seratustigabelas);
        seratusempatbelas_t = (TextView) findViewById(R.id.seratusempatbelas);

        Intent dasboard = getIntent();
        String kiriman1 = dasboard.getStringExtra("idoutlet");
        idoutlet.setText(kiriman1);

        String kiriman2 = dasboard.getStringExtra("namaoutlet");
        namaoutlet.setText(kiriman2);

        prosesdasboard();



    }

    public void prosesdasboard(){
        tele();
        new CountDownTimer(2000, 1000) {

            public void onTick(long millisUntilFinished) {
                pDialog.setMessage("TUNGGU SEBENTAR, SEDANG VERIFIKASI DATA :"+ millisUntilFinished / 1000);
                showDialog();
                pDialog.setCanceledOnTouchOutside(false);
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                hideDialog();


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


    private void tele() {

        AndroidNetworking.post(Config.host + "tele.php")
                .addBodyParameter("satu", idoutlet.getText().toString())
//                .addBodyParameter("namasales", namasalesinputperdana1.getText().toString())
//                .addBodyParameter("tanggal", tanggalinputperdana1.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response


                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);

                        satu_t.setText((response.optString("satu")));
                        dua_t.setText((response.optString("dua")));
                        tiga_t.setText((response.optString("tiga")));
                        empat_t.setText((response.optString("empat")));
                        lima_t.setText((response.optString("lima")));
                        enam_t.setText((response.optString("enam")));
                        tujuh_t.setText((response.optString("tujuh")));
                        delapan_t.setText((response.optString("delapan")));
                        sembilan_t.setText((response.optString("sembilan")));
                        sepuluh_t.setText((response.optString("sepuluh")));
                        sebelas_t.setText((response.optString("sebelas")));
                        duabelas_t.setText((response.optString("duabelas")));
                        tigabelas_t.setText((response.optString("tigabelas")));
                        empatbelas_t.setText((response.optString("empatbelas")));
                        limabelas_t.setText((response.optString("limabelas")));
                        enambelas_t.setText((response.optString("enambelas")));
                        tujuhbelas_t.setText((response.optString("tujuhbelas")));
                        delapanbelas_t.setText((response.optString("delapanbelas")));
                        sembilanbelas_t.setText((response.optString("sembilanbelas")));
                        duapuluh_t.setText((response.optString("duapuluh")));
                        duasatu_t.setText((response.optString("duasatu")));
                        duadua_t.setText((response.optString("duadua")));

                        duatiga_t.setText((response.optString("duatiga")));
                        duaempat_t.setText((response.optString("duaempat")));
                        dualima_t.setText((response.optString("dualima")));
                        duaenam_t.setText((response.optString("duaenam")));
                        duatujuh_t.setText((response.optString("duatujuh")));
                        duadelapan_t.setText((response.optString("duadelapan")));
                        duasembilan_t.setText((response.optString("duasembilan")));
                        tigapuluh_t.setText((response.optString("tigapuluh")));

                        tigasatu_t.setText((response.optString("tigasatu")));
                        tigadua_t.setText((response.optString("tigadua")));
                        tigatiga_t.setText((response.optString("tigatiga")));
                        tigaempat_t.setText((response.optString("tigaempat")));
                        tigalima_t.setText((response.optString("tigalima")));
                        tigaenam_t.setText((response.optString("tigaenam")));
                        tigatujuh_t.setText((response.optString("tigatujuh")));
                        tigadelapan_t.setText((response.optString("tigadelapan")));
                        tigasembilan_t.setText((response.optString("tigasembilan")));
                        empatpuluh_t.setText((response.optString("empatpuluh")));

                        empatsatu_t.setText((response.optString("empatsatu")));
                        empatdua_t.setText((response.optString("empatdua")));
                        empattiga_t.setText((response.optString("empattiga")));
                        empatempat_t.setText((response.optString("empatempat")));
                        empatlima_t.setText((response.optString("empatlima")));
                        empatenam_t.setText((response.optString("empatenam")));
                        empattujuh_t.setText((response.optString("empattujuh")));
                        empatdelapan_t.setText((response.optString("empatdelapan")));
                        empatsembilan_t.setText((response.optString("empatsembilan")));
                        limapuluh_t.setText((response.optString("limapuluh")));

                        limasatu_t.setText((response.optString("limasatu")));
                        limadua_t.setText((response.optString("limadua")));
                        limatiga_t.setText((response.optString("limatiga")));
                        limaempat_t.setText((response.optString("limaempat")));
                        limalima_t.setText((response.optString("limalima")));
                        limaenam_t.setText((response.optString("limaenam")));
                        limatujuh_t.setText((response.optString("limatujuh")));
                        limadelapan_t.setText((response.optString("limadelapan")));
                        limasembilan_t.setText((response.optString("limasembilan")));
                        enampuluh_t.setText((response.optString("enampuluh")));

                        enamsatu_t.setText((response.optString("enamsatu")));
                        enamdua_t.setText((response.optString("enamdua")));
                        enamtiga_t.setText((response.optString("enamtiga")));
                        enamempat_t.setText((response.optString("enamempat")));
                        enamlima_t.setText((response.optString("enamlima")));
                        enamenam_t.setText((response.optString("enamenam")));
                        enamtujuh_t.setText((response.optString("enamtujuh")));
                        enamdelapan_t.setText((response.optString("enamdelapan")));
                        enamsembilan_t.setText((response.optString("enamsembilan")));
                        tujuhpuluh_t.setText((response.optString("tujuhpuluh")));

                        tujuhsatu_t.setText((response.optString("tujuhsatu")));
                        tujuhdua_t.setText((response.optString("tujuhdua")));
                        tujuhtiga_t.setText((response.optString("tujuhtiga")));
                        tujuhempat_t.setText((response.optString("tujuhempat")));
                        tujuhlima_t.setText((response.optString("tujuhlima")));
                        tujuhenam_t.setText((response.optString("tujuhenam")));
                        tujuhtujuh_t.setText((response.optString("tujuhtujuh")));
                        tujuhdelapan_t.setText((response.optString("tujuhdelapan")));
                        tujuhsembilan_t.setText((response.optString("tujuhsembilan")));
                        delapanpuluh_t.setText((response.optString("delapanpuluh")));

                        delapansatu_t.setText((response.optString("delapansatu")));;
                        delapandua_t.setText((response.optString("delapandua")));
                        delapantiga_t.setText((response.optString("delapantiga")));
                        delapanempat_t.setText((response.optString("delapanempat")));
                        delapanlima_t.setText((response.optString("delapanlima")));
                        delapanenam_t.setText((response.optString("delapanenam")));
                        delapantujuh_t.setText((response.optString("delapantujuh")));
                        delapandelapan_t.setText((response.optString("delapandelapan")));
                        delapansembilan_t.setText((response.optString("delapansembilan")));
                        sembilanpuluh_t.setText((response.optString("sembilanpuluh")));

                        sembilansatu_t.setText((response.optString("sembilansatu")));
                        sembilandua_t.setText((response.optString("sembilandua")));
                        sembilantiga_t.setText((response.optString("sembilantiga")));
                        sembilanempat_t.setText((response.optString("sembilanempat")));
                        sembilanlima_t.setText((response.optString("sembilanlima")));
                        sembilanenam_t.setText((response.optString("sembilanenam")));
                        sembilantujuh_t.setText((response.optString("sembilantujuh")));
                        sembilandelapan_t.setText((response.optString("sembilandelapan")));
                        sembilansembilan_t.setText((response.optString("sembilansembilan")));
                        seratus_t.setText((response.optString("seratus")));

                        seratussatu_t.setText((response.optString("seratussatu")));
                        seratusdua_t.setText((response.optString("seratusdua")));
                        seratustiga_t.setText((response.optString("seratustiga")));
                        seratusempat_t.setText((response.optString("seratusempat")));
                        seratuslima_t.setText((response.optString("seratuslima")));
                        seratusenam_t.setText((response.optString("seratusenam")));
                        seratustujuh_t.setText((response.optString("seratustujuh")));
                        seratusdelapan_t.setText((response.optString("seratusdelapan")));
                        seratussembilan_t.setText((response.optString("seratussembilan")));
                        seratussepuluh_t.setText((response.optString("seratussepuluh")));

                        seratussebelas_t.setText((response.optString("seratussebelas")));
                        seratusduabelas_t.setText((response.optString("seratusduabelas")));
                        seratustigabelas_t.setText((response.optString("seratustigabelas")));
                        seratusempatbelas_t.setText((response.optString("seratusempatbelas")));



                    }

                    @Override
                    public void onError(ANError error) {

                    }




                });

    }


}