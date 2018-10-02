package chuga2310.com.lunarcalendar.Decorator;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.ArrayList;
import java.util.HashSet;

import chuga2310.com.lunarcalendar.span.CustomDotSpan;

public class MyDecorator implements DayViewDecorator {
    private final int color;
    private final HashSet<CalendarDay> dates;
    public MyDecorator(int color, ArrayList<CalendarDay> dates) {
        this.color = color;
        this.dates = new HashSet<>(dates);
    }
    @Override
    public boolean shouldDecorate(CalendarDay calendarDay) {
        return dates.contains(calendarDay);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new CustomDotSpan(5, color));
    }

}
