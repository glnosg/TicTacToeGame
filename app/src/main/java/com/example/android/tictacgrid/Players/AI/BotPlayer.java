package com.example.android.tictacgrid.Players.AI;

import android.content.Context;

import com.example.android.tictacgrid.Players.AI.BotMoveAlgorithms.MinimaxEasyAlgorithm;
import com.example.android.tictacgrid.Players.AI.BotMoveAlgorithms.MinimaxMediumMoveAlgorithm;
import com.example.android.tictacgrid.Players.AI.BotMoveAlgorithms.MinimaxMoveAlgorithm;
import com.example.android.tictacgrid.Players.AI.BotMoveAlgorithms.MoveAlgorithm;
import com.example.android.tictacgrid.Players.Player;
import com.example.android.tictacgrid.Shapes.ShapeEmpty;
import com.example.android.tictacgrid.Shapes.ShapeO;
import com.example.android.tictacgrid.Shapes.ShapeView;
import com.example.android.tictacgrid.Shapes.ShapeX;


/**
 * Created by pawel on 03.02.18.
 */

public class BotPlayer extends Player{

    private static final int GRID_SIZE = 9;
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
