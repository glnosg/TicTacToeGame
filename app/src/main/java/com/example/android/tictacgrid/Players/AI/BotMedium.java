package com.example.android.tictacgrid.Players.AI;

import android.content.Context;

import com.example.android.tictacgrid.Players.AI.BotMoveAlgorithms.MinimaxMediumMoveAlgorithm;

/**
 * Created by pawel on 04.02.18.
 */

public class BotMedium extends BotPlayer{

    public BotMedium(Context context) {
        super(context);
        this.moveAlgorithm = new MinimaxMediumMoveAlgorithm();
    }

    public BotMedium(Context context, String name) {
        super(context, name);
        this.moveAlgorithm = new MinimaxMediumMoveAlgorithm();
    }
}
