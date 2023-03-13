package com.example.barcode;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

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

public class SumaryRupiah2 extends AppCompatActivity {
    TextView transferperdana1;
    TextView cashperdana1;
    TextView totalperdana1;

    TextView transfervoucher1;
    TextView cashvoucher1;
    TextView totalvoucher1;

    TextView subtotaltransfer1;
    TextView subtotalcash1;
    TextView grandtotal1;


    TextView tanggal1;

    TextView namasalesinputperdana1;
    Button carisum1, jumlahkan1;

//----------------------

    TextView transferperdana12;
    TextView cashperdana12;
    TextView totalperdana12;


    TextView transfervoucher12;
    TextView cashvoucher12;
    TextView totalvoucher12;



    TextView subtotaltransfer12;
    TextView subtotalcash12;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;


    private ProgressDialog pDialog;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sumary_rupiah2);

        NumberFormat formatter = new DecimalFormat("#,###,###,###");
        dateFormatter = new SimpleDateFormat("yyyy/MM/dd", Locale.US);

        transferperdana1 = (TextView) findViewById(R.id.tfperdana);
        cashperdana1 = (TextView) findViewById(R.id.cashperdana);
        totalperdana1 = (TextView) findViewById(R.id.totalperdana);

        transfervoucher1 = (TextView) findViewById(R.id.tfvoucher);
        cashvoucher1 = (TextView) findViewById(R.id.cashvoucher);
        totalvoucher1 = (TextView) findViewById(R.id.totalvoucher);

        subtotaltransfer1 = (TextView) findViewById(R.id.subtotaltf);
        subtotalcash1 = (TextView) findViewById(R.id.subtotalcash);
        grandtotal1 = (TextView) findViewById(R.id.grandtotal);

        subtotaltransfer12 = (TextView) findViewById(R.id.subtotaltf12);
        subtotalcash12 = (TextView) findViewById(R.id.subtotalcash12);



        tanggal1 = (TextView) findViewById(R.id.tanggal);

        namasalesinputperdana1 = (TextView) findViewById(R.id.namasales);

        carisum1 = (Button) findViewById(R.id.carisumbayar);
        jumlahkan1 = (Button) findViewById(R.id.jumlahkan);

        context = SumaryRupiah2.this;
        pDialog = new ProgressDialog(context);


        //--------------------------

        transferperdana12 = (TextView) findViewById(R.id.tfperdana12);
        cashperdana12 = (TextView) findViewById(R.id.cashperdana12);
        totalperdana12 = (TextView) findViewById(R.id.totalperdana12);

        transfervoucher12 = (TextView) findViewById(R.id.tfvoucher12);
        cashvoucher12 = (TextView) findViewById(R.id.cashvoucher12);
        totalvoucher12 = (TextView) findViewById(R.id.totalvoucher12);







        //tanggal1.setText(getCurrentDate());
//        tanggal1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showDateDialog();
//            }
//        });

//        carisum1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                transferperdana();
//                transfervoucher();
//
//
//
//                updateaplikasi();
//            }
//        });



        Intent i = getIntent();
        String kiriman2 = i.getStringExtra("namasales");
        namasalesinputperdana1.setText(kiriman2);
        //String kiriman3 = i.getStringExtra("namasales");
        //namasalesinputperdana1.setText(kiriman3);
        String kiriman4 = i.getStringExtra("tanggal");
        tanggal1.setText(kiriman4);




        transferperdana();
        transfervoucher();



        updateaplikasi();

//------------------------ TOTAL PERDANA





    }




    public void updateaplikasi(){
//        pDialog.setMessage("TUNGGU SEBENTAR, SEDANG VERIFIKASI DATA");
//        showDialog();




        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
                pDialog.setMessage("TUNGGU SEBENTAR, SEDANG VERIFIKASI DATA :"+ millisUntilFinished / 1000);
                showDialog();
                pDialog.setCanceledOnTouchOutside(false);
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                hideDialog();

                totalperdana12.setText(addNumbers());
                String resultRupiah = " " + formatRupiah(Double.parseDouble(addNumbers()));
                totalperdana1.setText(resultRupiah);



                totalvoucher12.setText(addNumbersvoucher());
                String resultRupiah2 = " " + formatRupiah(Double.parseDouble(addNumbersvoucher()));
                totalvoucher1.setText(resultRupiah2);


                subtotaltransfer12.setText(addNumberssubtotal());
                String resultRupiah3 = " " + formatRupiah(Double.parseDouble(addNumberssubtotal()));
                subtotaltransfer1.setText(resultRupiah3);


                subtotalcash12.setText(addNumberssubtotalcash());
                String resultRupiah4 = " " + formatRupiah(Double.parseDouble(addNumberssubtotalcash()));
                subtotalcash1.setText(resultRupiah4);



                String resultRupiah5 = " " + formatRupiah(Double.parseDouble(addNumbersgrandtotal()));
                grandtotal1.setText(resultRupiah5);

                transferperdana12.addTextChangedListener(new TextWatcher() {

                    public void beforeTextChanged(CharSequence s, int start, int count,
                                                  int after) {
                        // TODO Auto-generated method stub
                    }

                    public void onTextChanged(CharSequence s, int start, int before,
                                              int count) {
                        totalperdana12.setText(addNumbers());
                        String resultRupiah = " " + formatRupiah(Double.parseDouble(addNumbers()));
                        totalperdana1.setText(resultRupiah);

                    }

                    public void afterTextChanged(Editable s) {
                        // TODO Auto-generated method stub
                    }
                });

                cashperdana12.addTextChangedListener(new TextWatcher() {

                    public void beforeTextChanged(CharSequence s, int start, int count,
                                                  int after) {
                        // TODO Auto-generated method stub
                    }

                    public void onTextChanged(CharSequence s, int start, int before,
                                              int count) {

                        totalperdana12.setText(addNumbers());
                        String resultRupiah = " " + formatRupiah(Double.parseDouble(addNumbers()));
                        totalperdana1.setText(resultRupiah);

                    }

                    public void afterTextChanged(Editable s) {
                        // TODO Auto-generated method stub
                    }
                });



                //------------------------ TOTAL VOUCHER


                transfervoucher12.addTextChangedListener(new TextWatcher() {

                    public void beforeTextChanged(CharSequence s, int start, int count,
                                                  int after) {
                        // TODO Auto-generated method stub
                    }

                    public void onTextChanged(CharSequence s, int start, int before,
                                              int count) {
                        totalvoucher12.setText(addNumbersvoucher());
                        String resultRupiah = " " + formatRupiah(Double.parseDouble(addNumbersvoucher()));
                        totalvoucher1.setText(resultRupiah);

                    }

                    public void afterTextChanged(Editable s) {
                        // TODO Auto-generated method stub
                    }
                });

                cashvoucher12.addTextChangedListener(new TextWatcher() {

                    public void beforeTextChanged(CharSequence s, int start, int count,
                                                  int after) {
                        // TODO Auto-generated method stub
                    }

                    public void onTextChanged(CharSequence s, int start, int before,
                                              int count) {

                        totalvoucher12.setText(addNumbersvoucher());
                        String resultRupiah = " " + formatRupiah(Double.parseDouble(addNumbersvoucher()));
                        totalvoucher1.setText(resultRupiah);

                    }

                    public void afterTextChanged(Editable s) {
                        // TODO Auto-generated method stub
                    }
                });




                //------------------------ SUB TOTAL TRANSFER


                transferperdana12.addTextChangedListener(new TextWatcher() {

                    public void beforeTextChanged(CharSequence s, int start, int count,
                                                  int after) {
                        // TODO Auto-generated method stub
                    }

                    public void onTextChanged(CharSequence s, int start, int before,
                                              int count) {
                        subtotaltransfer12.setText(addNumberssubtotal());
                        String resultRupiah = " " + formatRupiah(Double.parseDouble(addNumberssubtotal()));
                        subtotaltransfer1.setText(resultRupiah);

                    }

                    public void afterTextChanged(Editable s) {
                        // TODO Auto-generated method stub
                    }
                });

                transfervoucher12.addTextChangedListener(new TextWatcher() {

                    public void beforeTextChanged(CharSequence s, int start, int count,
                                                  int after) {
                        // TODO Auto-generated method stub
                    }

                    public void onTextChanged(CharSequence s, int start, int before,
                                              int count) {

                        subtotaltransfer12.setText(addNumberssubtotal());
                        String resultRupiah = " " + formatRupiah(Double.parseDouble(addNumberssubtotal()));
                        subtotaltransfer1.setText(resultRupiah);

                    }

                    public void afterTextChanged(Editable s) {
                        // TODO Auto-generated method stub
                    }
                });






                //------------------------ SUB TOTAL CASH


                cashperdana12.addTextChangedListener(new TextWatcher() {

                    public void beforeTextChanged(CharSequence s, int start, int count,
                                                  int after) {
                        // TODO Auto-generated method stub
                    }

                    public void onTextChanged(CharSequence s, int start, int before,
                                              int count) {
                        subtotalcash12.setText(addNumberssubtotalcash());
                        String resultRupiah = " " + formatRupiah(Double.parseDouble(addNumberssubtotalcash()));
                        subtotalcash1.setText(resultRupiah);

                    }

                    public void afterTextChanged(Editable s) {
                        // TODO Auto-generated method stub
                    }
                });

                cashvoucher12.addTextChangedListener(new TextWatcher() {

                    public void beforeTextChanged(CharSequence s, int start, int count,
                                                  int after) {
                        // TODO Auto-generated method stub
                    }

                    public void onTextChanged(CharSequence s, int start, int before,
                                              int count) {

                        subtotalcash12.setText(addNumberssubtotalcash());
                        String resultRupiah = " " + formatRupiah(Double.parseDouble(addNumberssubtotalcash()));
                        subtotalcash1.setText(resultRupiah);


                    }

                    public void afterTextChanged(Editable s) {
                        // TODO Auto-generated method stub
                    }
                });




                //------------------------ GRAND TOTAL


                subtotaltransfer12.addTextChangedListener(new TextWatcher() {

                    public void beforeTextChanged(CharSequence s, int start, int count,
                                                  int after) {
                        // TODO Auto-generated method stub
                    }

                    public void onTextChanged(CharSequence s, int start, int before,
                                              int count) {
                        //subtotalcash12.setText(addNumberssubtotalcash());
                        String resultRupiah = " " + formatRupiah(Double.parseDouble(addNumbersgrandtotal()));
                        grandtotal1.setText(resultRupiah);

                    }

                    public void afterTextChanged(Editable s) {
                        // TODO Auto-generated method stub
                    }
                });

                subtotalcash12.addTextChangedListener(new TextWatcher() {

                    public void beforeTextChanged(CharSequence s, int start, int count,
                                                  int after) {
                        // TODO Auto-generated method stub
                    }

                    public void onTextChanged(CharSequence s, int start, int before,
                                              int count) {

                        //subtotalcash12.setText(addNumberssubtotalcash());
                        String resultRupiah = " " + formatRupiah(Double.parseDouble(addNumbersgrandtotal()));
                        grandtotal1.setText(resultRupiah);


                    }

                    public void afterTextChanged(Editable s) {
                        // TODO Auto-generated method stub
                    }
                });
            }
        }.start();

    }


    public void updateaplikasi2(){
//        pDialog.setMessage("TUNGGU SEBENTAR, SEDANG VERIFIKASI DATA");
//        showDialog();

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


    private String addNumbers() {
        int number1;
        int number2;
        if (transferperdana12.getText().toString() != "null" && transferperdana12.getText().length() > 0) {
            number1 = Integer.parseInt(transferperdana12.getText().toString());
        } else {
            number1 = 0;
        }
        if (cashperdana12.getText().toString() != "" && cashperdana12.getText().length() > 0) {
            number2 = Integer.parseInt(cashperdana12.getText().toString());
        } else {
            number2 = 0;
        }

        return Integer.toString(number2 + number1);
    }




    private String addNumbersvoucher() {
        int number1;
        int number2;
        if (transfervoucher12.getText().toString() != "" && transfervoucher12.getText().length() > 0) {
            number1 = Integer.parseInt(transfervoucher12.getText().toString());
        } else {
            number1 = 0;
        }
        if (cashvoucher12.getText().toString() != "" && cashvoucher12.getText().length() > 0) {
            number2 = Integer.parseInt(cashvoucher12.getText().toString());
        } else {
            number2 = 0;
        }

        return Integer.toString(number2 + number1);
    }

    private String addNumberssubtotalcash() {
        int number1;
        int number2;
        if (cashperdana12.getText().toString() != "" && cashperdana12.getText().length() > 0) {
            number1 = Integer.parseInt(cashperdana12.getText().toString());
        } else {
            number1 = 0;
        }
        if (cashvoucher12.getText().toString() != "" && cashvoucher12.getText().length() > 0) {
            number2 = Integer.parseInt(cashvoucher12.getText().toString());
        } else {
            number2 = 0;
        }

        return Integer.toString(number2 + number1);
    }



    private String addNumberssubtotal() {
        int number1;
        int number2;
        if (transferperdana12.getText().toString() != "" && transferperdana12.getText().length() > 0) {
            number1 = Integer.parseInt(transferperdana12.getText().toString());
        } else {
            number1 = 0;
        }
        if (transfervoucher12.getText().toString() != "" && transfervoucher12.getText().length() > 0) {
            number2 = Integer.parseInt(transfervoucher12.getText().toString());
        } else {
            number2 = 0;
        }

        return Integer.toString(number2 + number1);
    }


    private String addNumbersgrandtotal() {
        int number1;
        int number2;
        if (subtotaltransfer12.getText().toString() != "" && subtotaltransfer12.getText().length() > 0) {
            number1 = Integer.parseInt(subtotaltransfer12.getText().toString());
        } else {
            number1 = 0;
        }
        if (subtotalcash12.getText().toString() != "" && subtotalcash12.getText().length() > 0) {
            number2 = Integer.parseInt(subtotalcash12.getText().toString());
        } else {
            number2 = 0;
        }

        return Integer.toString(number2 + number1);
    }





    private String formatRupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
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


    public String getCurrentDate() {
        //SimpleDateFormat contoh1 = new SimpleDateFormat("yyyy/MM/dd");
        //String hariotomatis = contoh1.format(c.getTime());
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat contoh1 = new SimpleDateFormat("yyyy/MM/dd");
//        int year, month, day;
//        year = c.get(Calendar.YEAR);
//        month = c.get(Calendar.MONTH);
//        day = c.get(Calendar.DATE);
        //SimpleDateFormat contoh1 = new SimpleDateFormat("EEEE, yyyy/MM/dd");

        String hariotomatis = contoh1.format(c.getTime());
        //tanggalinputperdana1.setText(hariotomatis);
        return hariotomatis;
        //return day +"/" + (month+1) + "/" + year;
        //return (month+1) +"/" + day + "/" + year;
        //return year + "/" + (month + 1) + "/" + day;



    }

    private void transferperdana() {

        AndroidNetworking.post(Config.host + "tfperdanabayar.php")
                //.addBodyParameter("idoutlet", idoutletinputperdana1.getText().toString())
                .addBodyParameter("namasales", namasalesinputperdana1.getText().toString())
                .addBodyParameter("tanggal", tanggal1.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response


                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);



                        transferperdana12.setText((response.optString("transfer")));
                        transferperdana1.setText(
                                rupiahFormat.format(response.optDouble("transfer")));


                        cashperdana12.setText((response.optString("cash")));
                        cashperdana1.setText(
                                rupiahFormat.format(response.optDouble("cash")));
//                        tanggal1.setText((response.optString("tanggal")));
//                        jam1.setText((response.optString("jam")));

                        if ( transferperdana12.getText().toString().equals("null")){
                            transferperdana12.setText("0");
                            cashperdana12.setText("0");
                        }else{


                        }

                    }

                    @Override
                    public void onError(ANError error) {

                    }




                });

    }


    private void transfervoucher() {

        AndroidNetworking.post(Config.host + "tfvoucherbayar.php")
                //.addBodyParameter("idoutlet", idoutletinputperdana1.getText().toString())
                .addBodyParameter("namasales", namasalesinputperdana1.getText().toString())
                .addBodyParameter("tanggal", tanggal1.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response


                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);



                        transfervoucher12.setText((response.optString("transfer")));
                        transfervoucher1.setText(
                                rupiahFormat.format(response.optDouble("transfer")));
                        cashvoucher12.setText((response.optString("cash")));
                        cashvoucher1.setText(
                                rupiahFormat.format(response.optDouble("cash")));
//                        tanggal1.setText((response.optString("tanggal")));
//                        jam1.setText((response.optString("jam")));

                        if ( transfervoucher12.getText().toString().equals("null")){
                            transfervoucher12.setText("0");
                            cashvoucher12.setText("0");
                        }else{


                        }

                    }

                    @Override
                    public void onError(ANError error) {

                    }




                });

    }


}