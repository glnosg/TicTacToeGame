package com.example.android.tictacgrid.Shapes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.example.android.tictacgrid.R;

/**
 * Created by pawel on 20.01.18.
 */

public class ShapeO extends ShapeView {

    public ShapeO(Context context) {
        super(context);
    }

    public void drawFigure(Canvas canvas) {

        float cx = getMeasuredHeight();
        float cy = getMeasuredWidth();

        float r = getMeasuredHeight()/3;

        Paint shapePaint = new Paint();
        shapePaint.setColor(getResources().getColor(R.color.colorShapeO));
        shapePaint.setStrokeWidth(10);

        Paint rectPaint = new Paint();
        rectPaint.setColor(getResources().getColor(R.color.colorGameGridCellBackground));

        canvas.drawRect(0, 0, cx, cy, rectPaint);
        canvas.drawCircle(cx/2, cy/2, r, shapePaint);
        canvas.drawCircle(cx/2, cy/2, (float) (r * 0.85), rectPaint);
    }
}
