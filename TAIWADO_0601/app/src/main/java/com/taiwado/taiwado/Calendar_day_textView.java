package com.taiwado.taiwado;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * Created by tei on 2017/05/25.
 */

public class Calendar_day_textView extends android.support.v7.widget.AppCompatTextView {

    public boolean isToday = false;
    public boolean isHolidayALL = false;
    public boolean isHolidayAM  = false;
    public boolean isHolidayPM  = false;
    private Paint paint = new Paint();
    private Paint paintHoliday = new Paint();

    public  Calendar_day_textView(Context context) {
        super(context);
    }

    public Calendar_day_textView(Context context, AttributeSet attrs){
        super(context,attrs);
        initControl(context);
    }

    public Calendar_day_textView(Context context,AttributeSet attrs,int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        initControl(context);
    }

    private void initControl(Context context){

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#00ff00"));

        paintHoliday.setStyle(Paint.Style.FILL_AND_STROKE);
        paintHoliday.setColor(Color.parseColor("#ff0000"));
        paintHoliday.setAlpha(100);

    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isHolidayALL){
            canvas.translate(getWidth() / 2, getHeight() / 2);
            canvas.drawCircle(0, 0, getWidth() / 2, paintHoliday);
        }else {
            if (isToday){
                canvas.translate(getWidth() /2 ,getHeight() / 2);
                canvas.drawCircle(0,0,getWidth() / 2,paint);
            }
        }
    }
}

