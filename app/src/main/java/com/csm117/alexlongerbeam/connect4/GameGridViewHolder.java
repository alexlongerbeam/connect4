package com.csm117.alexlongerbeam.connect4;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by alexlongerbeam on 11/11/18.
 */

//Class that changes the color of each circle in each row. This holds the "row" view
public class GameGridViewHolder extends RecyclerView.ViewHolder {

    public View mView;

    private ImageView[] circles;

    public GameGridViewHolder(View v) {
        super(v);
        mView = v;

        circles = new ImageView[7];

        Log.d("GGVH", "GameGridViewHolder: ALEX" + v.toString());

        circles[0] = v.findViewById(R.id.circle0);
        circles[1] = v.findViewById(R.id.circle1);
        circles[2] = v.findViewById(R.id.circle2);
        circles[3] = v.findViewById(R.id.circle3);
        circles[4] = v.findViewById(R.id.circle4);
        circles[5] = v.findViewById(R.id.circle5);
        circles[6] = v.findViewById(R.id.circle6);

        Log.d("GGVH", "GameGridViewHolder: ALEX" + circles[0].toString());

    }

    public void setCircleColors(String[] row) {
        for (int i = 0; i<row.length; i++) {
            if (circles[i] == null) {
                Log.d("GGVH", "setCircleColors: ALEX NULL");
                continue;
            }
            if (row[i].equals("RED")) {
                circles[i].setImageResource(R.drawable.red_circle);
            }
            else if (row[i].equals("BLACK")) {
                circles[i].setImageResource(R.drawable.black_circle);
            }
            else {
                circles[i].setImageResource(R.drawable.empty_circle);
            }
        }
    }
}
