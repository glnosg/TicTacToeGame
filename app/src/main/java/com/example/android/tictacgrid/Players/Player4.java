package com.example.android.tictacgrid.Players;

import android.content.Context;

import com.example.android.tictacgrid.Shapes.ShapeTriangle;
import com.example.android.tictacgrid.Shapes.ShapeView;

/**
 * Created by pawel on 20.01.18.
 */

public class Player4 extends Player {

    public Player4(Context context) {
        super(context);
    }

    public Player4(Context context, String name) {
        super(context, name);
    }

    @Override
    public ShapeView getShape() {
        return new ShapeTriangle(mContext);
    }
}