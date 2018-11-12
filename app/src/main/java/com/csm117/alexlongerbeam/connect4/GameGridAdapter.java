package com.csm117.alexlongerbeam.connect4;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by alexlongerbeam on 11/11/18.
 */

//Deals with putting the String array into the different rows
public class GameGridAdapter extends RecyclerView.Adapter<GameGridViewHolder> {

    private String[][] mGrid;

    private GameController controller;

    public GameGridAdapter(String[][] grid, GameController gc) {

        mGrid = new String[6][7];

        mGrid = grid;

        controller = gc;
    }

    @NonNull
    @Override
    public GameGridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_row, parent, false);

        return new GameGridViewHolder(v, controller);
    }

    @Override
    public void onBindViewHolder(@NonNull GameGridViewHolder holder, int position) {
        holder.setCircleColors(mGrid[position], position);
    }

    @Override
    public int getItemCount() {
        return mGrid.length;
    }
}
