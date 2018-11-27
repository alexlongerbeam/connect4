package com.csm117.alexlongerbeam.connect4;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.csm117.alexlongerbeam.connect4.BluetoothStuff.BluetoothGameStart;
import com.csm117.alexlongerbeam.connect4.BluetoothStuff.ClientConnectionThread;
import com.csm117.alexlongerbeam.connect4.BluetoothStuff.ServerConnectionThread;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    TextView playButton;

    private BluetoothAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        playButton = (TextView) findViewById(R.id.play_button);

        playButton.setOnClickListener(this);

        adapter = BluetoothAdapter.getDefaultAdapter();

        if (adapter == null) {
            Log.d("HomeActivity", "onCreate: Bluetooth is not supported");
        }
    }


    private void startServer() {
        if (!adapter.isEnabled()) {
            Intent bluetoothOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(bluetoothOn, 1);
        }

        startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE), 2);

        ServerConnectionThread serverThread = new ServerConnectionThread(adapter);
        serverThread.run(this);
    }

    private void startClient() {
        if (!adapter.isEnabled()) {
            Intent bluetoothOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(bluetoothOn, 1);
        }

        //TODO: Add list of discovered devices, then make a connection with that added device

        //ClientConnectionThread clientThread = new ClientConnectionThread(device, adapter);

    }

    public void socketFound(BluetoothSocket socket) {
        BluetoothGameStart startInfo = new BluetoothGameStart(socket);
        Intent gameIntent = new Intent(this, GameActivity.class);
        gameIntent.putExtra("BTGameStart", startInfo);
        startActivity(gameIntent);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.play_button:
                startGame();
                break;
            case R.id.start_button:
                startServer();
                break;
            case R.id.join_button:
                startClient();
                break;
            default:
                break;


        }
    }

    private void startGame() {
        Intent gameIntent = new Intent(this, GameActivity.class);
        startActivity(gameIntent);
    }
}
