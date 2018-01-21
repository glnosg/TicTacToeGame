package com.example.android.tictacgrid.Shapes;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import android.graphics.Canvas;

/**
 * Created by pawel on 14.01.18.
 */


public abstract class ShapeView extends View {

    public ShapeView(Context context) {
        super(context);
    }

    public ShapeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
                MeasureSpec.getSize(heightMeasureSpec));
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawFigure(canvas);
    }

    abstract void drawFigure(Canvas canvas);

}
