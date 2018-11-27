package com.csm117.alexlongerbeam.connect4;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.csm117.alexlongerbeam.connect4.BluetoothStuff.BluetoothController;
import com.csm117.alexlongerbeam.connect4.BluetoothStuff.BluetoothGameStart;

/**
 * Created by alexlongerbeam on 11/11/18.
 */

//The main file for the entire Game Activity, all game logic will go here, or another Game Controller class
public class GameActivity extends AppCompatActivity implements View.OnClickListener{


    private RecyclerView gameBoard;

    private GameController controller;

    private Button resetButton;

    private final String[][] INITIAL_BOARD = {{"null", "null", "null", "null", "null", "null", "null"},
            {"null", "null", "null", "null", "null", "null", "null"},
            {"null", "null", "null", "null", "null", "null", "null"},
            {"null", "null", "null", "null", "null", "null", "null"},
            {"null", "null", "null", "null", "null", "null", "null"},
            {"null", "null", "null", "null", "null", "null", "null"},
            {"null", "null", "null", "null", "null", "null", "null"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);

        gameBoard = findViewById(R.id.game_grid);

        resetButton = findViewById(R.id.reset_button);
        resetButton.setOnClickListener(this);

        //get bt socket from intent
        BluetoothGameStart startInfo = (BluetoothGameStart) getIntent().getSerializableExtra("BTGameStart");

        controller = new GameController(this, INITIAL_BOARD, startInfo.socket);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        gameBoard.setLayoutManager(layoutManager);


        GameGridAdapter adapter = new GameGridAdapter(INITIAL_BOARD, controller);
        gameBoard.setAdapter(adapter);





    }

    //Call this to update the game board
    public void updateGameBoard(String[][] board) {
        GameGridAdapter adapter = new GameGridAdapter(board, controller);
        gameBoard.setAdapter(adapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.reset_button:
                controller.resetBoard();
                break;

            default:
                break;


        }
    }


}
