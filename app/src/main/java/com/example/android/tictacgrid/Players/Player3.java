package com.example.android.tictacgrid.Players;

import android.content.Context;

import com.example.android.tictacgrid.Shapes.ShapeSquare;
import com.example.android.tictacgrid.Shapes.ShapeView;

/**
 * Created by pawel on 20.01.18.
 */

public class Player3 extends Player {

    public Player3(Context context) {
        super(context);
    }

    public Player3(Context context, String name) {
        super(context, name);
    }

    @Override
    public ShapeView getShape(boolean hasWon) {
        return new ShapeSquare(mContext, hasWon);
    }
}