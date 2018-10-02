package chuga2310.com.lunarcalendar.Decorator;

import android.graphics.Color;
import android.icu.util.ChineseCalendar;
import android.icu.util.TimeZone;
import android.util.Log;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.ArrayList;
import java.util.HashSet;

import chuga2310.com.lunarcalendar.span.CustomSpan;

public class LunarDecorator implements DayViewDecorator {

    private final String text;
    private final HashSet<CalendarDay> dates;
    public LunarDecorator(ArrayList<CalendarDay> dates,String text) {
        this.dates = new HashSet<>(dates);
        this.text = text;
    }
    @Override
    public boolean shouldDecorate(CalendarDay calendarDay) {

        return dates.contains(calendarDay);
    }

    @Override
    public void decorate(DayViewFacade view) {
            view.addSpan(new CustomSpan(Color.GRAY,text,10));
    }


}
