package com.example.android.tictacgrid.Shapes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.android.tictacgrid.R;

/**
 * Created by pawel on 20.01.18.
 */

public class ShapeSquare extends ShapeView {

    public ShapeSquare(Context context) {
        super(context);
    }

    public void drawFigure(Canvas canvas) {

        float centerX = getMeasuredWidth() / 2;
        float centerY = getMeasuredHeight() / 2;


        float[] primary = {
                (float) (centerX - (getMeasuredHeight() * (0.34))),
                (float) (centerY - (getMeasuredHeight() * (0.34))),
                (float) (centerX + (getMeasuredHeight() * (0.34))),
                (float) (centerY + (getMeasuredHeight() * (0.34)))
        };

        float[] scaled = {
                (float) (centerX - (getMeasuredHeight() * (0.29))),
                (float) (centerY - (getMeasuredHeight() * (0.29))),
                (float) (centerX + (getMeasuredHeight() * (0.29))),
                (float) (centerY + (getMeasuredHeight() * (0.29)))
        };

        Paint rectPaint = new Paint();
        rectPaint.setColor(getResources().getColor(R.color.colorGameGridCellBackground));

        canvas.drawRect(0, 0, getMeasuredHeight(), getMeasuredWidth(), rectPaint);


        Paint shapePaint = new Paint();
        shapePaint.setColor(getResources().getColor(R.color.colorShapeSquare));
        shapePaint.setStrokeWidth(10);

        canvas.drawRect(primary[0], primary[1], primary[2], primary[3], shapePaint);
        canvas.drawRect(scaled[0], scaled[1], scaled[2], scaled[3], rectPaint);
    }
}
