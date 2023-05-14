package com.example.gsb_menu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class SquareView extends View {

    private Paint paint;
    private String text;

    public SquareView(Context context) {
        super(context);
        init();
    }

    public SquareView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SquareView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        text = "";
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        int size = Math.min(width, height);
        int centerX = width / 2;
        int centerY = height / 2;
        paint.setColor(Color.WHITE);
        canvas.drawRect(centerX - size / 2, centerY - size / 2, centerX + size / 2, centerY + size / 2, paint);
        paint.setColor(Color.BLACK);
        paint.setTextSize(size / 2);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text, centerX, centerY + size / 4, paint);
    }

    public void setText(String text) {
        this.text = text;
        invalidate();
    }
}
