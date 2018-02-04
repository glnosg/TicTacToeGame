package com.example.android.tictacgrid.Players.AI;


import android.content.Context;

import com.example.android.tictacgrid.Players.AI.BotMoveAlgorithms.RandomMoveAlgorithm;

/**
 * Created by pawel on 03.02.18.
 */

public class BotEasy extends BotPlayer {

    public BotEasy(Context context) {
        super(context);
        this.moveAlgorithm = new RandomMoveAlgorithm();
    }

    public BotEasy(Context context, String name) {
        super(context, name);
        this.moveAlgorithm = new RandomMoveAlgorithm();
    }
}
