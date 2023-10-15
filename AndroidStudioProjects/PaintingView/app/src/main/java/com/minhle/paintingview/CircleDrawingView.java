package com.minhle.paintingview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class CircleDrawingView extends View {
    private final int paintColor = Color.BLACK;
    private Paint drawPaint;

    private List<Point> circlePoints;
    public CircleDrawingView(Context context, AttributeSet attrs) {
        super(context, attrs); setupPaint(); // same as before
        circlePoints = new ArrayList<Point>();
    }
    // Draw each circle onto the view
    @Override
    protected void onDraw(Canvas canvas) {
        for (Point p : circlePoints) {
            canvas.drawCircle(p.x, p.y, 5, drawPaint);
        }
    }
    // Append new circle each time user presses on screen
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX(); float touchY = event.getY();
        circlePoints.add(new Point(Math.round(touchX), Math.round(touchY)));
        // indicate view should be redrawn
        postInvalidate();
        return true;
    }

    private void setupPaint() {
        // same as before
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(5);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        drawPaint.setStyle(Paint.Style.FILL);
        // change to fill // ...
    }

}