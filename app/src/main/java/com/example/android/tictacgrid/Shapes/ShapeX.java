package com.example.android.tictacgrid.Shapes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;


/**
 * Created by pawel on 19.01.18.
 */

public class ShapeX extends ShapeView {

    public ShapeX(Context context) {
        super(context);
    }

    public void drawFigure(Canvas canvas) {

        Log.d("ShapeX", "in drawFigure");

        float ax = (float) (getMeasuredHeight() * (0.17));
        float ay = (float) (getMeasuredWidth() * (0.17));

        float bx = (float) (getMeasuredHeight() * (0.83));
        float by = (float) (getMeasuredWidth() * (0.83));

        Log.d("MyView", "Measured width: " + getMeasuredWidth());
        Log.d("MyView", "Measured height: " + getMeasuredHeight());

        Paint rectPaint = new Paint();
        rectPaint.setColor(Color.parseColor("#FAFAFA"));

        canvas.drawRect(0, 0, getMeasuredHeight(), getMeasuredWidth(), rectPaint);


        Paint shapePaint = new Paint();
        shapePaint.setColor(Color.parseColor("#80CBC4"));
        shapePaint.setStrokeWidth(10);

        canvas.drawLine(ax, ay, bx, by, shapePaint);
        canvas.drawLine(ax, by, bx, ay, shapePaint);
    }

}