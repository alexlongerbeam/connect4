package com.csm117.alexlongerbeam.connect4;

import android.bluetooth.BluetoothDevice;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by alexlongerbeam on 12/2/18.
 */

public class DeviceViewHolderAdapter extends RecyclerView.Adapter<DeviceListViewHolder> {

    HomeActivity activity;

    List<BluetoothDevice> mDevices;

    public DeviceViewHolderAdapter(List<BluetoothDevice> i, HomeActivity a) {
        mDevices = i;
        activity = a;
    }

    @NonNull
    @Override
    public DeviceListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.device_view, parent, false);

        return new DeviceListViewHolder(v, activity);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceListViewHolder holder, int position) {
        holder.setDevice(mDevices.get(position));
    }

    @Override
    public int getItemCount() {
        return mDevices.size();
    }
}
