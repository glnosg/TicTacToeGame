package com.example.android.tictacgrid.Players;

import android.content.Context;

import com.example.android.tictacgrid.Shapes.ShapeO;
import com.example.android.tictacgrid.Shapes.ShapeView;

/**
 * Created by pawel on 20.01.18.
 */

public class Player2 extends Player {

    public Player2(Context context) {
        super(context);
    }

    public Player2(Context context, String name) {
        super(context, name);
    }

    @Override
    public ShapeView getShape(boolean hasWon) {
        return new ShapeO(mContext, hasWon);
    }
}
