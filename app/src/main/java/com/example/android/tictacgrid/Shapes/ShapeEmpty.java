package com.example.android.tictacgrid.Shapes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;


/**
 * Created by pawel on 20.01.18.
 */

public class ShapeEmpty extends ShapeView {

    public ShapeEmpty(Context context) {
        super(context);
    }

    @Override
    void drawFigure(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#FAFAFA"));
    }
}
