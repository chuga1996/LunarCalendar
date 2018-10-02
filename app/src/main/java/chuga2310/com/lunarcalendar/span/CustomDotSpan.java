package chuga2310.com.lunarcalendar.span;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

public class CustomDotSpan implements LineBackgroundSpan {
    public static final float DEFAULT_RADIUS = 3.0F;
    private final float radius;
    private final int color;

    public CustomDotSpan() {
        this.radius = 3.0F;
        this.color = 0;
    }

    public CustomDotSpan(int color) {
        this.radius = 3.0F;
        this.color = color;
    }

    public CustomDotSpan(float radius) {
        this.radius = radius;
        this.color = 0;
    }

    public CustomDotSpan(float radius, int color) {
        this.radius = radius;
        this.color = color;
    }

    public void drawBackground(Canvas canvas, Paint paint, int left, int right, int top, int baseline, int bottom, CharSequence charSequence, int start, int end, int lineNum) {
        int oldColor = paint.getColor();
        if (this.color != 0) {
            paint.setColor(this.color);
        }

        canvas.drawCircle((float)((left + right) / 2), (float)top - this.radius, this.radius, paint);
        paint.setColor(oldColor);
    }
}
