package com.csm117.alexlongerbeam.connect4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by alexlongerbeam on 11/11/18.
 */

//The main file for the entire Game Activity, all game logic will go here, or another Game Controller class
public class GameActivity extends AppCompatActivity {


    private RecyclerView gameBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);

        gameBoard = findViewById(R.id.game_grid);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        gameBoard.setLayoutManager(layoutManager);
        String[][] input = {{"RED", "HJ", "NOT", "BLACK", "RED", "GHJ"}, {"RED", "HJ", "NOT", "BLACK", "RED", "GHJ"}};

        GameGridAdapter adapter = new GameGridAdapter(input);
        gameBoard.setAdapter(adapter);

    }

    //Call this to update the game board
    public void updateGameBoard(String[][] board) {
        GameGridAdapter adapter = new GameGridAdapter(board);
        gameBoard.setAdapter(adapter);
    }


}
