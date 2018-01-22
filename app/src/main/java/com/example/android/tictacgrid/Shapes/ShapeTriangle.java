package com.example.android.tictacgrid.Shapes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.example.android.tictacgrid.R;


/**
 * Created by pawel on 20.01.18.
 */

public class ShapeTriangle extends ShapeView {

    public ShapeTriangle(Context context) {
        super(context);
    }

    public ShapeTriangle(Context context, boolean hasWon) {
        super(context, hasWon);
    }

    public void drawFigure(Canvas canvas) {

        float ax = (float) (getMeasuredHeight() * (0.15));
        float ay = (float) (getMeasuredWidth() * (0.17));

        float bx = (float) (getMeasuredHeight() * (0.85));
        float by = (float) (getMeasuredWidth() * (0.81));

        float centerX = getMeasuredWidth() / 2;

        Paint rectPaint = new Paint();

        if (mHasWon)
            rectPaint.setColor(getResources().getColor(R.color.colorShapeTriangleVictoryBackground));
        else
            rectPaint.setColor(getResources().getColor(R.color.colorGameGridCellBackground));

        canvas.drawRect(0, 0, getMeasuredHeight(), getMeasuredWidth(), rectPaint);

        Paint shapePaint = new Paint();
        shapePaint.setColor(getResources().getColor(R.color.colorShapeTriangle));
        shapePaint.setStrokeWidth(10);

        Path path = new Path();
        path.moveTo(ax, by);
        path.lineTo(bx, by);
        path.lineTo(centerX, ay);
        path.lineTo(ax, by);
        path.close();

        canvas.drawPath(path, shapePaint);
    }
}
