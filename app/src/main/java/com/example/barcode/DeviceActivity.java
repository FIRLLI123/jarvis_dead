package com.example.barcode;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.zj.btsdk.BluetoothService;

import java.util.Set;


public class DeviceActivity extends AppCompatActivity {

    ListView lvPairedDevice;
    ListView lvNewDevice;
    TextView tvNewDevice;
    TextView tvPairedDevice;
    Button scan;

    public static final String EXTRA_DEVICE_ADDRESS = "device_address";
    private BluetoothService mService = null;
    private ArrayAdapter<String> newDeviceAdapter;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    newDeviceAdapter.add(device.getName() + "\n" + device.getAddress());
                } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                    setTitle("Pilih Perangkat");
                    if (newDeviceAdapter.getCount() == 0) {
                        newDeviceAdapter.add("Perangkat tidak ditemukan");
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        setTitle("Perangkat Bluetooth");
        //ButterKnife.bind(this);

        lvNewDevice = (ListView)findViewById(R.id.new_devices);
        lvPairedDevice = (ListView)findViewById(R.id.paired_devices);
        tvNewDevice = (TextView) findViewById(R.id.title_new_devices);
        tvPairedDevice = (TextView) findViewById(R.id.title_paired_devices);
        scan = (Button) findViewById(R.id.button_scan);

        ArrayAdapter<String> pairedDeviceAdapter = new ArrayAdapter<>(this, R.layout.device_name);
        lvPairedDevice.setAdapter(pairedDeviceAdapter);
        lvPairedDevice.setOnItemClickListener(mDeviceClickListener);

        newDeviceAdapter = new ArrayAdapter<>(this, R.layout.device_name);
        lvNewDevice.setAdapter(newDeviceAdapter);
        lvNewDevice.setOnItemClickListener(mDeviceClickListener);

        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, intentFilter);

        intentFilter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, intentFilter);

        mService = new BluetoothService(this, null);

        Set<BluetoothDevice> pairedDevice = mService.getPairedDev();

        if (pairedDevice.size() > 0) {
            tvPairedDevice.setVisibility(View.VISIBLE);
            for (BluetoothDevice device : pairedDevice) {
                pairedDeviceAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        } else {
            String noDevice = "Tidak ada perangkat terhubung!";
            pairedDeviceAdapter.add(noDevice);
        }

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  startPlacePickerActivity();

                doDiscovery();
                view.setVisibility(View.GONE);

            }
        });

    }


    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mService.cancelDiscovery();

            String info = ((TextView) view).getText().toString();
            String address = info.substring(info.length() - 17);

            Intent intent = new Intent();
            intent.putExtra(EXTRA_DEVICE_ADDRESS, address);

            setResult(RESULT_OK, intent);
            finish();
        }
    };

    private void doDiscovery() {
        setTitle("Mencari perangkat...");
        tvNewDevice.setVisibility(View.VISIBLE);

        if (mService.isDiscovering()) {
            mService.cancelDiscovery();
        }

        mService.startDiscovery();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mService != null) {
            mService.cancelDiscovery();
        }
        mService = null;
        unregisterReceiver(mReceiver);
    }
}
