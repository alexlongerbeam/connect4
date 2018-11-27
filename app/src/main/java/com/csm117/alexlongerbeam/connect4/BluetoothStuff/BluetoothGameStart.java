package com.csm117.alexlongerbeam.connect4.BluetoothStuff;


import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import java.io.Serializable;

/**
 * Created by alexlongerbeam on 11/26/18.
 */

public class BluetoothGameStart implements Serializable {
    public BluetoothSocket socket;

    public BluetoothGameStart(BluetoothSocket s) {
        socket = s;
    }
}
