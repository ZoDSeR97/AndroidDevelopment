package com.minhle.paintingview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ToggleButton;

public class SimpleDrawingView extends View {
    private final int paintColor = Color.BLACK;
    private Paint drawPaint;


    public SimpleDrawingView(Context context, AttributeSet atts){
        super(context,atts);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setupPaint();
    }
    private void setupPaint(){
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(5);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

    }

    private Path path = new Path();
    // Get x and y and append them to the path
    public boolean onTouchEvent(MotionEvent event) {
        float pointX = event.getX();
        float pointY = event.getY();
        // Checks for the event that occurs
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Starts a new line in the path
                path.moveTo(pointX, pointY);
                break;
            case MotionEvent.ACTION_MOVE:
                // Draws line between last point and this point
                path.lineTo(pointX, pointY);
                break;
            default:
                return false;
        }

        postInvalidate(); // Indicate view should be redrawn
        return true; // Indicate we've consumed the touch
    }
    // Draws the path created during the touch events
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, drawPaint);
    }

}