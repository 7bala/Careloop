package com.example.careloop;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class BodyProgressView extends View {

    private Paint paint = new Paint();

    public BodyProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.GREEN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Draw a rectangle just to test
        canvas.drawRect(50, 50, getWidth() - 50, getHeight() - 50, paint);
    }
}
