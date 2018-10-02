package chuga2310.com.lunarcalendar.span;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

public class CustomSpan implements LineBackgroundSpan {
    private final String text;
    private final int color;
    private final int fix;
    public CustomSpan(int color,String text,int fix){
        this.text = text;
        this.color =color;
        this.fix = fix;
//        >10 fix = 10 < 10 fix=6
    }



    public void drawBackground(Canvas canvas, Paint paint, int left, int right, int top, int baseline, int bottom, CharSequence charSequence, int start, int end, int lineNum) {
        int oldColor = paint.getColor();
        float oldTextSize = paint.getTextSize();
        paint.setColor(this.color);
        paint.setTextSize(oldTextSize-18);

        canvas.drawText(text,(float)((left+right)/2-fix), ((float)(canvas.getWidth() *0.7)),paint);
        paint.setColor(oldColor);
        paint.setTextSize(oldTextSize);

    }
}
