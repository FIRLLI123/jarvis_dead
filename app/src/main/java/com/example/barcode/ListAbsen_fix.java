package com.example.barcode;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.barcode.Data.Data_BayarEX;
import com.example.barcode.helper.Config;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ListAbsen_fix extends AppCompatActivity {
    ImageView pending, approve, ditolak;
    TextView statuslistabsen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_absen_fix);

        pending = (ImageView) findViewById(R.id.pending);
        approve = (ImageView) findViewById(R.id.approve);
        ditolak = (ImageView) findViewById(R.id.ditolak);

        statuslistabsen = (TextView) findViewById(R.id.statuslistabsen);


        if (statuslistabsen.getText().toString().equals("PENDING")) {
            //1
            pending.setVisibility(View.VISIBLE);
            approve.setVisibility(View.GONE);
            ditolak.setVisibility(View.GONE);

            //Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
        } else if (statuslistabsen.getText().toString().equals("APPROVE")) {
            //1
            pending.setVisibility(View.GONE);
            approve.setVisibility(View.VISIBLE);
            ditolak.setVisibility(View.GONE);

            //Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
        } else if (statuslistabsen.getText().toString().equals("DITOLAK")) {
            //1
            pending.setVisibility(View.GONE);
            approve.setVisibility(View.GONE);
            ditolak.setVisibility(View.VISIBLE);

            //Toast.makeText(getApplicationContext(), "Silahkan pilih terlebih dahulu", Toast.LENGTH_LONG).show();
        } else {

        }
    }
}