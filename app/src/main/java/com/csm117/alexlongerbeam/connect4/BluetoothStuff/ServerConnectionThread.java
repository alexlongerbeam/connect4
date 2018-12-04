package com.csm117.alexlongerbeam.connect4.BluetoothStuff;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.csm117.alexlongerbeam.connect4.HomeActivity;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by alexlongerbeam on 11/26/18.
 */

public class ServerConnectionThread extends Thread {

    private final BluetoothServerSocket mmServerSocket;

    private final String TAG = "ServerConenctionThread";

    private final UUID key;


    private HomeActivity activity;

    public ServerConnectionThread(BluetoothAdapter adapter, HomeActivity a) {
        // Use a temporary object that is later assigned to mmServerSocket
        // because mmServerSocket is final.
        BluetoothServerSocket tmp = null;

        activity = a;

        Log.d(TAG, "ServerConnectionThread: ALEX Starting server");

        //key = UUID.fromString("connect4btkey");

        key = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

        try {
            // MY_UUID is the app's UUID string, also used by the client code.
            tmp = adapter.listenUsingRfcommWithServiceRecord("Connect4", key);
        } catch (IOException e) {
            Log.d(TAG, "ServerConnectionThread: Connection failed");
        }
        mmServerSocket = tmp;
    }

    public void run() {
        BluetoothSocket socket = null;
        // Keep listening until exception occurs or a socket is returned.
        Log.d(TAG, "run: ALEX Running server thread");
        while (true) {
            try {
                socket = mmServerSocket.accept();
            } catch (IOException e) {
                Log.e(TAG, "Socket's accept() method failed", e);
                break;
            }

            if (socket != null) {
                // A connection was accepted. Perform work associated with
                // the connection in a separate thread.
                activity.socketFound(socket);
                break;
            }
        }
    }

    // Closes the connect socket and causes the thread to finish.
    public void cancel() {
        try {
            mmServerSocket.close();
        } catch (IOException e) {
            Log.e(TAG, "Could not close the connect socket", e);
        }
    }
}
