package com.csm117.alexlongerbeam.connect4;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.csm117.alexlongerbeam.connect4.BluetoothStuff.BluetoothController;
import com.csm117.alexlongerbeam.connect4.BluetoothStuff.BluetoothGameStart;
import com.csm117.alexlongerbeam.connect4.BluetoothStuff.ClientConnectionThread;
import com.csm117.alexlongerbeam.connect4.BluetoothStuff.ServerConnectionThread;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    Button startButton;

    Button joinButton;

    TextView statusText;

    private BluetoothAdapter adapter;

    private static final String TAG = "Home Activity";

    IntentFilter btFilter;

    RecyclerView dList;

    ArrayList<BluetoothDevice> foundDevices;

    private boolean isStarter;

    private final int PERMISSION_CODE = 25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        startButton = findViewById(R.id.start_button);
        joinButton = findViewById(R.id.join_button);
        statusText = findViewById(R.id.status_text);


        startButton.setOnClickListener(this);
        joinButton.setOnClickListener(this);

        adapter = BluetoothAdapter.getDefaultAdapter();

        if (adapter == null) {
            Log.d("HomeActivity", "onCreate: Bluetooth is not supported");
        }

        // Register for broadcasts when a device is discovered.
        btFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);

        dList = findViewById(R.id.device_list);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        dList.setLayoutManager(layoutManager);

        foundDevices = new ArrayList<BluetoothDevice>();

        DeviceViewHolderAdapter a = new DeviceViewHolderAdapter(foundDevices, this);
        dList.setAdapter(a);
    }

    // Create a BroadcastReceiver for ACTION_FOUND.
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: ALEX");
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                Log.d(TAG, "onReceive: ALEX NAME: " + deviceName);
                if (deviceName != null) {
                    foundDevices.add(device);
                    updateDeviceList();
                }

            }
        }
    };

    private void updateDeviceList() {
        DeviceViewHolderAdapter a = new DeviceViewHolderAdapter(foundDevices, this);
        dList.setAdapter(a);
    }

    public void onDeviceClicked(BluetoothDevice d) {
        Log.d(TAG, "onDeviceClicked: ALEX Device clicked: " + d.getName());
        ClientConnectionThread clientThread = new ClientConnectionThread(d, adapter, this);
        clientThread.start();
    }


    private void startServer() {
        if (!adapter.isEnabled()) {
            Intent bluetoothOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(bluetoothOn, 1);
            Log.d(TAG, "startServer: ALEX no adapter");
        }

        isStarter= true;

        if (!checkLocationPermission()) {
            return;
        }

        startServerThread();

    }

    private void startServerThread() {
        Log.d(TAG, "startServer: ALEX");
        startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE), 2);
        Log.d(TAG, "startServer: ALEX made discoverable");
        statusText.setText("Waiting for connection...");
        statusText.setVisibility(View.VISIBLE);
        ServerConnectionThread serverThread = new ServerConnectionThread(adapter, this);
        serverThread.start();
    }

    private void startClient() {
        if (!adapter.isEnabled()) {
            Intent bluetoothOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(bluetoothOn, 1);
        }
        isStarter = false;

        if (!checkLocationPermission()) {
            return;
        }

        startClientDiscovery();

    }

    private void startClientDiscovery() {
        Log.d(TAG, "startClient: ALEX starting discovery");
        registerReceiver(mReceiver, btFilter);
        adapter.startDiscovery();
        statusText.setText("Scanning for devices...");
        statusText.setVisibility(View.VISIBLE);
        Log.d(TAG, "startClient: ALEX after discovery");
    }

    public void socketFound(BluetoothSocket socket) {
        BluetoothController.getInstance().setSocket(socket);
        Intent gameIntent = new Intent(this, GameActivity.class);

        Bundle b = new Bundle();

        String role;
        if (isStarter) {
            role = "Start";
        } else {
            role = "Client";
        }

        b.putString("Role", role);

        gameIntent.putExtras(b);

        startActivity(gameIntent);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.start_button:
                Log.d(TAG, "onClick: ALEX start server");
                startServer();
                break;
            case R.id.join_button:
                startClient();
                break;
            default:
                break;


        }
    }

    public boolean checkLocationPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // No explanation needed; request the permission
            Log.d(TAG, "checkLocationPermission: asking for location permission");
            ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_CODE);


            return false;
        } else {
            // Permission has already been granted
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.d(TAG, "onRequestPermissionsResult: ALEX location permission granted");

                    if (isStarter) {
                        startServerThread();
                    } else {
                        startClientDiscovery();
                    }
                } else {
                    Log.d(TAG, "onRequestPermissionsResult: ALEX LOCATION permission not allowed");
                }
                return;
            }

        }
    }

}
