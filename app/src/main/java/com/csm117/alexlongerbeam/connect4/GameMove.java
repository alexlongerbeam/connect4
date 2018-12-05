package com.csm117.alexlongerbeam.connect4;

import java.io.Serializable;

/**
 * Created by alexlongerbeam on 11/26/18.
 */

public class GameMove implements Serializable {

    public static final int RESET = -1;

    public static final int END = -2;

    public int column;

    public GameMove(int col) {
        column = col;
    }
}
