package com.example.android.tictacgrid.Shapes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

/**
 * Created by pawel on 20.01.18.
 */

public class ShapeO extends ShapeView {

    public ShapeO(Context context) {
        super(context);
        Log.d("ShapeO", "in ShapeO constructor");
    }

    public void drawFigure(Canvas canvas) {

        Log.d("ShapeO", "in drawFigure");

        float cx = getMeasuredHeight();
        float cy = getMeasuredWidth();

        float r = getMeasuredHeight()/3;

        Paint shapePaint = new Paint();
        shapePaint.setColor(Color.parseColor("#EF9A9A"));
        shapePaint.setStrokeWidth(10);

        Paint rectPaint = new Paint();
        rectPaint.setColor(Color.parseColor("#FAFAFA"));

        canvas.drawRect(0, 0, cx, cy, rectPaint);
        canvas.drawCircle(cx/2, cy/2, r, shapePaint);
        canvas.drawCircle(cx/2, cy/2, (float) (r * 0.85), rectPaint);
    }
}
