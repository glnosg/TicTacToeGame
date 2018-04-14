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

public class ShapeCircle extends ShapeView {

    public ShapeCircle(Context context) {
        super(context);
    }

    public ShapeCircle(Context context, boolean hasWon) {
        super(context, hasWon);
    }

    public void drawFigure(Canvas canvas) {

        float cx = getMeasuredHeight();
        float cy = getMeasuredWidth();

        float r = (float) (getMeasuredHeight() / 2.7);

        Paint rectPaint = new Paint();

        if (mHasWon)
            rectPaint.setColor(getResources().getColor(R.color.colorShapeOVictoryBackground));
        else
            rectPaint.setColor(getResources().getColor(R.color.colorGameGridCellBackground));

        canvas.drawRect(0, 0, cx, cy, rectPaint);

        Paint shapePaint = new Paint();
        shapePaint.setColor(getResources().getColor(R.color.colorShapeO));
        shapePaint.setStrokeWidth(10);

        canvas.drawCircle(cx/2, cy/2, r, shapePaint);
        canvas.drawCircle(cx/2, cy/2, (float) (r * 0.88), rectPaint);
    }
}
