package com.example.android.tictacgrid.Players.AI;

import android.content.Context;

import com.example.android.tictacgrid.Players.AI.BotMoveAlgorithms.MoveAlgorithm;
import com.example.android.tictacgrid.Players.Player2;
import com.example.android.tictacgrid.Shapes.ShapeEmpty;
import com.example.android.tictacgrid.Shapes.ShapeO;
import com.example.android.tictacgrid.Shapes.ShapeView;
import com.example.android.tictacgrid.Shapes.ShapeX;


/**
 * Created by pawel on 03.02.18.
 */

public abstract class BotPlayer extends Player2 {

    private static final int GRID_SIZE = 9;
    private int[] currentStateOfGameGrid;
    protected MoveAlgorithm moveAlgorithm;

    public BotPlayer(Context context) {
        super(context);
    }

    public BotPlayer(Context context, String name) {
        super(context, name);
    }

    public void setMoveAlgorithm(MoveAlgorithm algorithm) {
        this.moveAlgorithm = algorithm;
    }

    public int makeMove(ShapeView[] currentViewsInGameGrig) {

        currentStateOfGameGrid = new int[currentViewsInGameGrig.length];
        int numberOfEmptyFields = 0;

        for (int i = 0; i < currentViewsInGameGrig.length; ++i) {

            if (currentViewsInGameGrig[i] instanceof ShapeEmpty) {
                currentStateOfGameGrid[i] = 0;
                numberOfEmptyFields++;
            } else if (currentViewsInGameGrig[i] instanceof ShapeX) {
                currentStateOfGameGrid[i] = -1;
            } else if (currentViewsInGameGrig[i] instanceof ShapeO) {
                currentStateOfGameGrid[i] = 1;
            }
        }

       if (numberOfEmptyFields == GRID_SIZE)  {
           return (int) (Math.random() * GRID_SIZE);
       } else {
           return moveAlgorithm.move(currentStateOfGameGrid);
       }
    }
}
