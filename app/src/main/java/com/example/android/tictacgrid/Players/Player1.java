package com.example.android.tictacgrid.Players;

import android.content.Context;

import com.example.android.tictacgrid.Shapes.ShapeView;
import com.example.android.tictacgrid.Shapes.ShapeX;

/**
 * Created by pawel on 20.01.18.
 */

public class Player1 extends Player {

    public Player1(Context context) {
        super(context);
    }

    public Player1(Context context, String name) {
        super(context, name);
    }

    @Override
    public ShapeView getShape() {
        return new ShapeX(mContext);
    }
}
