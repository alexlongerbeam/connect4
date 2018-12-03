package com.csm117.alexlongerbeam.connect4;

import android.bluetooth.BluetoothDevice;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by alexlongerbeam on 12/2/18.
 */

public class DeviceListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private View mView;

    private TextView nameView;

    private HomeActivity activity;

    private BluetoothDevice mDevice;

    public DeviceListViewHolder(View itemView, HomeActivity a) {
        super(itemView);
        mView = itemView;

        activity = a;

        nameView = mView.findViewById(R.id.device_name);

        nameView.setOnClickListener(this);
    }

    private void setName(String name) {
        nameView.setText(name);
    }

    public void setDevice(BluetoothDevice d) {
        mDevice = d;
        setName(d.getName());
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.device_name:
                Log.d("ItemViewHolder", "onClick: Item clicked");
                activity.onDeviceClicked(mDevice);
                break;

            default:
                break;
        }
    }
}
