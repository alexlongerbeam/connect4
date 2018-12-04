package com.csm117.alexlongerbeam.connect4;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.csm117.alexlongerbeam.connect4.BluetoothStuff.BluetoothController;
//import com.plattysoft.leonids.ParticleSystem;
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

    private int[] heights;

    private int numCols;

    private boolean yourTurn;

    public GameController(GameActivity a, String[][] initialBoard, boolean turn) {
        activity = a;
        currentBoard = initialBoard;
        numCols = initialBoard[0].length;
        heights = new int[numCols];
        yourTurn = turn;

        BluetoothController.getInstance().setGameController(this);
        BluetoothController.getInstance().start();
    }

    public void onCircleClicked(int row, int col) {
        if(yourTurn)
        {
            if (heights[col] <= 6) {
                currentBoard[6 - heights[col]][col] = "RED";
                activity.updateGameBoard(currentBoard);
                boolean winFlag = checkWin(6 - heights[col], col, "RED");
                heights[col] += 1;
                /* WRITE MOVE WITH BLUETOOTH*/
                BluetoothController.getInstance().writeMove(new GameMove(col));
                if (winFlag)
                    wonGame();
                yourTurn = false;
            }
        }
    }

    public void moveReceived(GameMove m) {
        Log.d("GameController", "ALEX moveReceived: " + m.column);
        if (yourTurn == false) {
            if (heights[m.column] <= 6) {
                currentBoard[6 - heights[m.column]][m.column] = "BLACK";
                activity.updateGameBoard(currentBoard);
                boolean winFlag = checkWin(6 - heights[m.column], m.column, "BLACK");
                heights[m.column] += 1;
                if (winFlag)
                    lostGame();
                yourTurn = true;
            }
        }
    }

    public void resetBoard() {
        for (int i=0; i<currentBoard.length; i++) {
            for (int j=0; j<currentBoard[0].length; j++){
                currentBoard[i][j] = "null";
                heights[j] = 0;
            }
        }
        activity.updateGameBoard(currentBoard);
    }

    public boolean checkFull() {
        for (int i = 0; i < heights.length; ++i) {
            if (heights[i] < 6)
                return false;
        }
        return true;
    }

    public boolean checkWin(int row, int col, String color) {
        boolean vertFlag = true;
        //Vertical Win
        if (row <= 3) {
            for (int i = row; i <= row + 3; ++i) {
//               Log.d("Connect4", "Row" + i);
//               Log.d("Connect4", "Col" + col);
                if (!color.equals(currentBoard[i][col]))
                    vertFlag = false;
            }
            if (vertFlag == true) {
                for (int i = row; i <= row + 3; ++i) {
                    currentBoard[i][col] = "GREEN";
                }
                activity.updateGameBoard(currentBoard);
                return true;
            }
        }
        int horizontalCount = 0;
        //Horizontal Check
        for(int i = col - 3; i <= col +3;i++)
        {
            if(i >= 0 && i < 7)
            {
//               Log.d("Connect4", "Row" + row);
//               Log.d("Connect4", "Col" + i);

                if(color.equals(currentBoard[row][i]))
                {
                    horizontalCount++;
                }
                else
                {
                    horizontalCount = 0;
                }
                if(horizontalCount == 4)
                {
                    for (int j = i; j >= i - 3; --j) {
                        currentBoard[row][j] = "GREEN";
                    }
                    activity.updateGameBoard(currentBoard);
                    return true;
                }
            }
        }
        //Left to Right Downwards Diagonal Check
        int lrDiag = 0;
        for(int i = - 3; i <=  3;i++)
        {
            if(row + i >= 0 && row + i < 7 && col + i >= 0 && col + i < 7)
            {
//                Log.d("Connect4", "Row" + (row + i));
//                Log.d("Connect4", "Col" + (col + i));

                if(color.equals(currentBoard[row + i][col + i]))
                {
                    lrDiag++;
                }
                else
                {
                    lrDiag = 0;
                }
                if(lrDiag == 4)
                {
                    for (int j = 0; j >= -3; --j) {
                        currentBoard[row + i + j][col + i + j] = "GREEN";
                    }
                    activity.updateGameBoard(currentBoard);
                    return true;
                }
            }
        }
        //Right to Left Upwards Diagonal Check
        int rlDiag = 0;
        int currR, currC;
        for(int i = - 3; i <=  3;i++)
        {
            currR = row + i;
            currC = col - i;
            if(currR >= 0 && currR < 7 && currC >= 0 && currC < 7)
            {
//                Log.d("Connect4", "Row" + (currR));
//                Log.d("Connect4", "Col" + (currC));

                if(color.equals(currentBoard[currR][currC]))
                {
                    rlDiag++;
                }
                else
                {
                    rlDiag = 0;
                }
                if(rlDiag == 4)
                {
                    for (int j = 0; j >= -3; --j) {
                        currentBoard[currR + j][currC - j] = "GREEN";
                    }
                    activity.updateGameBoard(currentBoard);
                    return true;
                }
            }
        }
        return false;
    }


    /*
    public boolean checkWinP1(int row, int col) {
       boolean vertFlag = true;
       //Vertical Win
       if (row <= 3) {
           for (int i = row; i <= row + 3; ++i) {
//               Log.d("Connect4", "Row" + i);
//               Log.d("Connect4", "Col" + col);
               if (currentBoard[i][col] != "RED")
                   vertFlag = false;
           }
           if (vertFlag == true) {
               for (int i = row; i <= row + 3; ++i) {
                   currentBoard[i][col] = "GREEN";
               }
               activity.updateGameBoard(currentBoard);
               return true;
           }
       }
       int horizontalCount = 0;
       //Horizontal Check
       for(int i = col - 3; i <= col +3;i++)
       {
           if(i >= 0 && i < 7)
           {
//               Log.d("Connect4", "Row" + row);
//               Log.d("Connect4", "Col" + i);

               if(currentBoard[row][i] == "RED")
               {
                   horizontalCount++;
               }
               else
               {
                   horizontalCount = 0;
               }
               if(horizontalCount == 4)
               {
                   for (int j = i; j >= i - 3; --j) {
                       currentBoard[row][j] = "GREEN";
                   }
                   activity.updateGameBoard(currentBoard);
                   return true;
               }
           }
       }
        //Left to Right Downwards Diagonal Check
        int lrDiag = 0;
        for(int i = - 3; i <=  3;i++)
        {
            if(row + i >= 0 && row + i < 7 && col + i >= 0 && col + i < 7)
            {
//                Log.d("Connect4", "Row" + (row + i));
//                Log.d("Connect4", "Col" + (col + i));

                if(currentBoard[row + i][col + i] == "RED")
                {
                    lrDiag++;
                }
                else
                {
                    lrDiag = 0;
                }
                if(lrDiag == 4)
                {
                    for (int j = 0; j >= -3; --j) {
                        currentBoard[row + i + j][col + i + j] = "GREEN";
                    }
                    activity.updateGameBoard(currentBoard);
                    return true;
                }
            }
        }
        //Right to Left Upwards Diagonal Check
        int rlDiag = 0;
        int currR, currC;
        for(int i = - 3; i <=  3;i++)
        {
            currR = row + i;
            currC = col - i;
            if(currR >= 0 && currR < 7 && currC >= 0 && currC < 7)
            {
//                Log.d("Connect4", "Row" + (currR));
//                Log.d("Connect4", "Col" + (currC));

                if(currentBoard[currR][currC] == "RED")
                {
                    rlDiag++;
                }
                else
                {
                    rlDiag = 0;
                }
                if(rlDiag == 4)
                {
                    for (int j = 0; j >= -3; --j) {
                        currentBoard[currR + j][currC - j] = "GREEN";
                    }
                    activity.updateGameBoard(currentBoard);
                    return true;
                }
            }
        }
       return false;
    }


    public boolean checkWinP2(int row, int col) {
        boolean vertFlag = true;
        //Vertical Win
        if (row <= 3) {
            for (int i = row; i <= row + 3; ++i) {
//               Log.d("Connect4", "Row" + i);
//               Log.d("Connect4", "Col" + col);
                if (currentBoard[i][col] != "BLACK")
                    vertFlag = false;
            }
            if (vertFlag == true) {
                for (int i = row; i <= row + 3; ++i) {
                    currentBoard[i][col] = "GREEN";
                }
                activity.updateGameBoard(currentBoard);
                return true;
            }
        }
        int horizontalCount = 0;
        //Horizontal Check
        for(int i = col - 3; i <= col +3;i++)
        {
            if(i >= 0 && i < 7)
            {
                Log.d("Connect4", "Row" + row);
                Log.d("Connect4", "Col" + i);

                if(currentBoard[row][i] == "BLACK")
                {
                    horizontalCount++;
                }
                else
                {
                    horizontalCount = 0;
                }
                if(horizontalCount == 4)
                {
                    for (int j = i; j >= i - 3; --j) {
                        currentBoard[row][j] = "GREEN";
                    }
                    activity.updateGameBoard(currentBoard);
                    return true;
                }
            }
        }
        //Left to Right Downwards Diagonal Check
        int lrDiag = 0;
        for(int i = - 3; i <=  3;i++)
        {
            if(row + i >= 0 && row + i < 7 && col + i >= 0 && col + i < 7)
            {
//                Log.d("Connect4", "Row" + (row + i));
//                Log.d("Connect4", "Col" + (col + i));

                if(currentBoard[row + i][col + i] == "BLACK")
                {
                    lrDiag++;
                }
                else
                {
                    lrDiag = 0;
                }
                if(lrDiag == 4)
                {
                    for (int j = 0; j >= -3; --j) {
                        currentBoard[row + i + j][col + i + j] = "GREEN";
                    }
                    activity.updateGameBoard(currentBoard);
                    return true;
                }
            }
        }
        //Right to Left Upwards Diagonal Check
        int rlDiag = 0;
        int currR, currC;
        for(int i = - 3; i <=  3;i++)
        {
            currR = row + i;
            currC = col - i;
            if(currR >= 0 && currR < 7 && currC >= 0 && currC < 7)
            {
//                Log.d("Connect4", "Row" + (currR));
//                Log.d("Connect4", "Col" + (currC));

                if(currentBoard[currR][currC] == "BLACK")
                {
                    rlDiag++;
                }
                else
                {
                    rlDiag = 0;
                }
                if(rlDiag == 4)
                {
                    for (int j = 0; j >= -3; --j) {
                        currentBoard[currR + j][currC - j] = "GREEN";
                    }
                    activity.updateGameBoard(currentBoard);
                    return true;
                }
            }
        }
        return false;
    }

*/

    public void wonGame() {
//        new ParticleSystem(activity, 80, R.drawable.confeti2, 10000)
//                .setSpeedModuleAndAngleRange(0f, 0.3f, 180, 180)
//                .setRotationSpeed(144)
//                .setAcceleration(0.00005f, 90)
//                .emit(activity.findViewById(R.id.emiter_top_right), 8);
//
//        new ParticleSystem(activity, 80, R.drawable.confeti3, 10000)
//                .setSpeedModuleAndAngleRange(0f, 0.3f, 0, 0)
//                .setRotationSpeed(144)
//                .setAcceleration(0.00005f, 90)
//                .emit(activity.findViewById(R.id.emiter_top_left), 8);
    }

    public void lostGame() {

    }
}
