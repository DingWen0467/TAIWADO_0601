package com.taiwado.taiwado;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import static com.taiwado.taiwado.CalenderActivity.Kyuuka;

@RequiresApi(api = Build.VERSION_CODES.N)
public class DateActivity extends LinearLayout {

    private ImageView buttonPrev,buttonNext;
    private TextView txtDate;
    private GridView calender_grid;
    private Calendar curDate = Calendar.getInstance();
    private String displayFormat;
    public NewCaledarListener Listener;


    public DateActivity(Context context) {
        super(context);

    }
    public DateActivity(Context context, AttributeSet attrs){
        super(context,attrs);
        initControl(context,attrs);
    }
    public DateActivity(Context context,AttributeSet attrs,int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        initControl(context,attrs);
    }
    private void initControl(Context context,AttributeSet attrs){
        bindControl(context);
        bindControlEvent();

        TypedArray ta = getContext().obtainStyledAttributes(attrs,R.styleable.NewCalendar);

        try {
            String format = ta.getString(R.styleable.NewCalendar_dateFormat);
            displayFormat = format;
            if (displayFormat == null){
                displayFormat = "yyyy年MM月dd日";
            }
        }
        finally {
            ta.recycle();
        }
        renderCalender();
    }
    private void bindControl(Context context){
        LayoutInflater  inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.activity_date,this);
        buttonPrev = (ImageView)findViewById(R.id.buttonPrev);
        buttonNext = (ImageView)findViewById(R.id.buttonNext);
        txtDate = (TextView)findViewById(R.id.txtDate);
        calender_grid =(GridView)findViewById(R.id.calender_grid);
    }
    private void bindControlEvent(){
        buttonPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                curDate.add(Calendar.MONTH, -1);
                renderCalender();
            }
        });

        buttonNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                curDate.add(Calendar.MONTH, 1);
                renderCalender();
            }
        });
    }
    private void renderCalender(){
        SimpleDateFormat sdf = new SimpleDateFormat(displayFormat);
        txtDate.setText(sdf.format(curDate.getTime()));

        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar) curDate.clone();

        calendar.set(Calendar.DAY_OF_MONTH,1);
        int prevDays = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        calendar.add(calendar.DAY_OF_MONTH,-prevDays);

        int MaxCellCount = 6*7;
        while (cells.size() < MaxCellCount){
            cells.add(calendar.getTime());
            calendar.add(calendar.DAY_OF_MONTH,1);
        }

        calender_grid.setAdapter(new CalendarAdapter(getContext(),cells));
        calender_grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                if (Listener == null){
                    return false;
                }else{
                    Listener.onItemLongPress((Date)parent.getItemAtPosition(position));
                    return true;
                }

            }
        });
    }

    private class CalendarAdapter extends ArrayAdapter<Date>{
        LayoutInflater inflater;
        public CalendarAdapter(@NonNull Context context, ArrayList<Date> days) {
            super(context, R.layout.calendar_text_day,days);
            inflater = LayoutInflater.from(context);
        }

        public View getView(int position, View convertView, ViewGroup parent){

            Date date = getItem(position);
            if (convertView == null){
                convertView = inflater.inflate(R.layout.calendar_text_day,parent,false);
            }
            int day = date.getDate();
            ((TextView)convertView).setText(String.valueOf(day));
            Date now = new Date();

            boolean isTheSameMonth = false;
            if (date.getMonth() == now.getMonth()) {
                isTheSameMonth = true;
            }
            if (isTheSameMonth) {
                ((TextView)convertView).setTextColor(Color.parseColor("#000000"));
            }else {
                ((TextView)convertView).setTextColor(Color.parseColor("#666666"));
            }

            if (now.getDate() == date.getDate() && now.getMonth() == date.getMonth() && now.getYear() == date.getYear()){
                ((TextView)convertView).setTextColor(Color.parseColor("#00ff00"));
                ((Calendar_day_textView)convertView).isToday = true;
            }
            if (isTheSameMonth){
                if (Kyuuka[day] != null ){
                    ((TextView)convertView).setTextColor(Color.parseColor("#ff0000"));
                    ((Calendar_day_textView)convertView).isHolidayALL = true;
                }

            }

            return convertView;
        }

    }
    public interface NewCaledarListener{
        void onItemLongPress (Date day);

    }

}
