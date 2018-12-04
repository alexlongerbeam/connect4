package com.csm117.alexlongerbeam.connect4.BluetoothStuff;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.os.Message;
import android.util.Log;

import com.csm117.alexlongerbeam.connect4.GameActivity;
import com.csm117.alexlongerbeam.connect4.GameController;
import com.csm117.alexlongerbeam.connect4.GameMove;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Created by alexlongerbeam on 11/26/18.
 */

public class BluetoothController extends Thread {
    private static final String TAG = "Bluetooth Controller";
    private GameController gameController;

    private BluetoothSocket mSocket;
    private InputStream mmInStream;
    private OutputStream mmOutStream;

    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    private static BluetoothController mController;

    public static BluetoothController getInstance() {
        if (mController == null) {
            mController = new BluetoothController();
        }
        return mController;
    }

    private BluetoothController() {

    }

    public void setGameController(GameController gc) {
        gameController = gc;
    }
    public void setSocket(BluetoothSocket socket) {
        mSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        // Get the input and output streams; using temp objects because
        // member streams are final.
        try {
            tmpIn = socket.getInputStream();
        } catch (IOException e) {
            Log.e(TAG, "Error occurred when creating input stream", e);
        }
        try {
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
            Log.e(TAG, "Error occurred when creating output stream", e);
        }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;

        try {
            oos = new ObjectOutputStream(mmOutStream);
        } catch (IOException e) {
            Log.e(TAG, "Error occurred when creating object output stream", e);
        }

        try {
            ois = new ObjectInputStream(mmInStream);
        } catch (IOException e) {
            Log.e(TAG, "Error occurred when creating object input stream", e);
        }



    }
    public void run() {

        while (true) {
            try {
                GameMove move = (GameMove) ois.readObject();
                gameController.setMostRecentMove(move);
            } catch (IOException | ClassNotFoundException e) {
                Log.i("ERROR", "Run error:"+e.getLocalizedMessage());
            }
        }
    }

    // Call this from the main activity to send data to the remote device.
    public void writeMove(GameMove move) {
        Log.d(TAG, "writeMove: ALEX WRITE MOVE");
        try {
            oos.writeObject(move);
        }catch(Exception e){
            Log.e(TAG, "Error WRITE ObjectOutputStream: "+e.getLocalizedMessage());
        }
    }


    // Call this method from the main activity to shut down the connection.
    public void cancel() {
        try {
            mSocket.close();
        } catch (IOException e) {
            Log.e(TAG, "Could not close the connect socket", e);
        }
    }
}


