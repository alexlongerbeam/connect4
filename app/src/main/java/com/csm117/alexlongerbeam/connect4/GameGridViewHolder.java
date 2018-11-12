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
public class GameGridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public View mView;

    private ImageView[] circles;

    private int rowNum;

    private GameController controller;

    public GameGridViewHolder(View v, GameController gc) {
        super(v);
        mView = v;
        controller = gc;

        circles = new ImageView[7];

        circles[0] = v.findViewById(R.id.circle0);
        circles[1] = v.findViewById(R.id.circle1);
        circles[2] = v.findViewById(R.id.circle2);
        circles[3] = v.findViewById(R.id.circle3);
        circles[4] = v.findViewById(R.id.circle4);
        circles[5] = v.findViewById(R.id.circle5);
        circles[6] = v.findViewById(R.id.circle6);

        for (int i=0; i<circles.length; i++) {
            circles[i].setOnClickListener(this);
        }
    }

    public void setCircleColors(String[] rowVals, int rowNumber) {

        rowNum = rowNumber;

        for (int i = 0; i<rowVals.length; i++) {
            if (circles[i] == null) {
                Log.d("GGVH", "setCircleColors: ALEX NULL");
                continue;
            }
            if (rowVals[i].equals("RED")) {
                circles[i].setImageResource(R.drawable.red_circle);
            }
            else if (rowVals[i].equals("BLACK")) {
                circles[i].setImageResource(R.drawable.black_circle);
            }
            else {
                circles[i].setImageResource(R.drawable.empty_circle);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.circle0:
                broadcastClick(0);
                break;
            case R.id.circle1:
                broadcastClick(1);
                break;
            case R.id.circle2:
                broadcastClick(2);
                break;
            case R.id.circle3:
                broadcastClick(3);
                break;
            case R.id.circle4:
                broadcastClick(4);
                break;
            case R.id.circle5:
                broadcastClick(5);
                break;
            case R.id.circle6:
                broadcastClick(6);
                break;


            default:
                break;


        }
    }

    private void broadcastClick(int col) {
        if (controller != null) {
            controller.onCircleClicked(rowNum, col);
        }
        else {
            Log.d("GGVH", "broadcastClick: ALEX controller null");
        }
    }
}
