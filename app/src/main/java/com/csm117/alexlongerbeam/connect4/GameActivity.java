package com.csm117.alexlongerbeam.connect4;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.csm117.alexlongerbeam.connect4.BluetoothStuff.BluetoothController;
import com.csm117.alexlongerbeam.connect4.BluetoothStuff.BluetoothGameStart;

/**
 * Created by alexlongerbeam on 11/11/18.
 */

//The main file for the entire Game Activity, all game logic will go here, or another Game Controller class
public class GameActivity extends AppCompatActivity implements View.OnClickListener{


    private RecyclerView gameBoard;

    private GameController controller;

    private ImageView myColor;

    private TextView statusText;

    private Button endGame;
    private Button restartGame;

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

        Bundle b = getIntent().getExtras();

        String role = b.getString("Role");

        gameBoard = findViewById(R.id.game_grid);

        myColor = findViewById(R.id.your_color);
        statusText = findViewById(R.id.game_status);
        endGame = findViewById(R.id.end_game);
        restartGame = findViewById(R.id.restart);

        endGame.setOnClickListener(this);
        restartGame.setOnClickListener(this);

        boolean starter;
        if (role.equals("Start")) {
            starter = true;
        } else {
            starter = false;
        }

        controller = new GameController(this, INITIAL_BOARD, starter);

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

    public void setButtonsVisible(boolean visible) {
        int v = visible ? View.VISIBLE : View.INVISIBLE;

        endGame.setVisibility(v);
        restartGame.setVisibility(v);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.end_game:
                controller.endGame();
                break;
            case R.id.restart:
                controller.sendReset();
                setButtonsVisible(false);
                break;
            default:
                break;
        }
    }

    public void endActivity() {
        finish();
    }

    public void setStatusText(String text) {
        statusText.setText(text);
    }

    public void setMyColorRed(boolean red) {
        if (red) {
            myColor.setImageResource(R.drawable.red_circle);
        } else {
            myColor.setImageResource(R.drawable.yellow_circle);
        }
    }



}
