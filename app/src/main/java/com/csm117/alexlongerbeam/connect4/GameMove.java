package com.csm117.alexlongerbeam.connect4;

import java.io.Serializable;

/**
 * Created by alexlongerbeam on 11/26/18.
 */

public class GameMove implements Serializable {

    public int column;

    public GameMove(int col) {
        column = col;
    }
}
