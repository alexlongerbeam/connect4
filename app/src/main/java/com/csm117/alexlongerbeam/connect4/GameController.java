package com.csm117.alexlongerbeam.connect4;

/**
 * Created by alexlongerbeam on 11/12/18.
 */

public class GameController {
    /*
    This will be the class for all the logic of the game. Will get told when a move is made, and
    will then tell GameActivity to update the game board
     */

    private GameActivity activity;

    private String[][] currentBoard;

    public GameController(GameActivity a, String[][] initialBoard) {
        activity = a;
        currentBoard = initialBoard;
    }

    public void onCircleClicked(int row, int col) {
        currentBoard[row][col] = "RED";
        activity.updateGameBoard(currentBoard);
    }

    public void resetBoard() {
        for (int i=0; i<currentBoard.length; i++) {
            for (int j=0; j<currentBoard[0].length; j++){
                currentBoard[i][j] = "null";
            }
        }
        activity.updateGameBoard(currentBoard);
    }
}
