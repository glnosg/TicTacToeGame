package com.example.android.tictacgrid.Players.AI;

import android.content.Context;

import com.example.android.tictacgrid.Players.AI.BotMoveAlgorithms.MinimaxMoveAlgorithm;

/**
 * Created by pawel on 04.02.18.
 */

public class BotHard extends BotPlayer {

    public BotHard(Context context) {
        super(context);
        this.moveAlgorithm = new MinimaxMoveAlgorithm();
    }

    public BotHard(Context context, String name) {
        super(context, name);
        this.moveAlgorithm = new MinimaxMoveAlgorithm();
    }
}
