package com.example.android.tictacgrid.Players.AI;

import android.content.Context;
import android.util.Log;

import com.example.android.tictacgrid.Players.AI.BotMoveAlgorithms.MinimaxEasyAlgorithm;
import com.example.android.tictacgrid.Players.AI.BotMoveAlgorithms.MinimaxMediumMoveAlgorithm;
import com.example.android.tictacgrid.Players.AI.BotMoveAlgorithms.MinimaxMoveAlgorithm;
import com.example.android.tictacgrid.Players.AI.BotMoveAlgorithms.MoveAlgorithm;
import com.example.android.tictacgrid.Players.Player;
import com.example.android.tictacgrid.Shapes.ShapeEmpty;
import com.example.android.tictacgrid.Shapes.ShapeView;


/**
 * Created by pawel on 03.02.18.
 */

public class BotPlayer extends Player {

    private int[] currentStateOfGameGrid;
    protected MoveAlgorithm moveAlgorithm;

    public BotPlayer(Context context) {
        super(context);
    }

    public BotPlayer(Context context, String name, int playerNumber) {
        super(context, name, playerNumber);
        setMoveAlgorithm();
    }

    public void setMoveAlgorithm() {
        switch (this.getPlayerName()) {
            case("EasyBot"):
                this.moveAlgorithm = new MinimaxEasyAlgorithm();
                break;
            case("MediumBot"):
                this.moveAlgorithm = new MinimaxMediumMoveAlgorithm();
                break;
            case("HardBot"):
                this.moveAlgorithm = new MinimaxMoveAlgorithm();
                break;
        }
    }

    public int makeMove(ShapeView[] currentViewsInGameGrid) {

        currentStateOfGameGrid = new int[currentViewsInGameGrid.length];
        int numberOfEmptyFields = 0;

        for (int i = 0; i < currentViewsInGameGrid.length; ++i) {

            ShapeView botShape = this.getShape(false);

            if (currentViewsInGameGrid[i] instanceof ShapeEmpty) {
                currentStateOfGameGrid[i] = 0;
                numberOfEmptyFields++;
            } else if (currentViewsInGameGrid[i].getClass().equals(botShape.getClass())) {
                currentStateOfGameGrid[i] = 1;
            } else {
                currentStateOfGameGrid[i] = -1;
            }
        }

       if (numberOfEmptyFields == currentViewsInGameGrid.length)  {
           return (int) (Math.random() * currentViewsInGameGrid.length);
       } else {
           return moveAlgorithm.move(currentStateOfGameGrid);
       }
    }
}
