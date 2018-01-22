package com.example.android.tictacgrid.Shapes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.android.tictacgrid.R;


/**
 * Created by pawel on 19.01.18.
 */

public class ShapeX extends ShapeView {

    public ShapeX(Context context) {
        super(context);
    }

    public ShapeX(Context context, boolean hasWon) {
        super(context, hasWon);
    }

    public void drawFigure(Canvas canvas) {

        float ax = (float) (getMeasuredHeight() * (0.17));
        float ay = (float) (getMeasuredWidth() * (0.17));

        float bx = (float) (getMeasuredHeight() * (0.83));
        float by = (float) (getMeasuredWidth() * (0.83));

        Paint rectPaint = new Paint();

        if (mHasWon)
            rectPaint.setColor(getResources().getColor(R.color.colorShapeXVictoryBackground));
        else
            rectPaint.setColor(getResources().getColor(R.color.colorGameGridCellBackground));

        canvas.drawRect(0, 0, getMeasuredHeight(), getMeasuredWidth(), rectPaint);


        Paint shapePaint = new Paint();
        shapePaint.setColor(getResources().getColor(R.color.colorShapeX));
        shapePaint.setStrokeWidth(10);

        canvas.drawLine(ax, ay, bx, by, shapePaint);
        canvas.drawLine(ax, by, bx, ay, shapePaint);
    }

}