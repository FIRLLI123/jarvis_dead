package com.example.barcode;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import org.w3c.dom.Text;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.view.View.OnClickListener;

public class Call_Center extends AppCompatActivity {
LinearLayout call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_center);


        call = (LinearLayout) findViewById(R.id.call);


        call.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                String noHp = "6285288844555";
                String pesan = "Hallo Admin rekamitra, bisa bantu saya?";


                String pakePesandanNomor =
                        "https://api.whatsapp.com/send?phone=" + noHp + "&text=" + pesan;
                Intent i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(pakePesandanNomor));
                startActivity(i);

            }

        });

    }
}