package chuga2310.com.lunarcalendar.uis;

import android.content.Intent;
import android.graphics.Color;
import android.icu.util.ChineseCalendar;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import chuga2310.com.lunarcalendar.Decorator.MyDecorator;
import chuga2310.com.lunarcalendar.R;
import chuga2310.com.lunarcalendar.database.DatabaseHelper;
import chuga2310.com.lunarcalendar.model.DateTimeEvent;
import chuga2310.com.lunarcalendar.model.Event;
import chuga2310.com.lunarcalendar.Decorator.LunarDecorator;

public class MainActivity extends AppCompatActivity {
    MaterialCalendarView calendarView;
    DatabaseHelper db;
    ImageView addButton;
    ArrayList<Event> listEvent;
    private TextView eventDate;
    private TextView eventMonth;
    private TextView eventTitle;
    private TextView eventContent;
    private TextView eventTime;
    private LinearLayout commingUp;
    int day, month, year;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendarView = findViewById(R.id.calendarView);
        addButton = findViewById(R.id.btnAdd);
        calendarView.state().edit().setMaximumDate(CalendarDay.from(2021,11,31))
                                    .setMinimumDate(CalendarDay.from(2008,0,1))
                                    .commit();
        calendarView.setSelectionColor(Color.parseColor("#FFC4F5FF"));

        eventDate = (TextView) findViewById(R.id.eventDate);
        eventMonth = (TextView) findViewById(R.id.eventMonth);
        eventTitle = (TextView) findViewById(R.id.eventTitle);
        eventContent = (TextView) findViewById(R.id.eventContent);
        eventTime = (TextView) findViewById(R.id.eventTime);
        commingUp = findViewById(R.id.commingUp);
        db = new DatabaseHelper(MainActivity.this);
        Calendar calendar = Calendar.getInstance();
        day= calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH)+1;
        year =calendar.get(Calendar.YEAR);

        listEvent = db.getAllEvent();
        ArrayList<CalendarDay> listCalendarDay = new ArrayList<>();
        try {
            for (Event x: listEvent){
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date date = sdf.parse(x.getDate());
                CalendarDay cal = CalendarDay.from(date);
                listCalendarDay.add(cal);
            }
            MyDecorator myDecorator = new MyDecorator(Color.RED, listCalendarDay);
            calendarView.addDecorator(myDecorator);

        }catch (Exception e){

        }

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView materialCalendarView, @NonNull CalendarDay calendarDay, boolean b) {
                day= calendarDay.getDay();
                month = calendarDay.getMonth()+1;
                year =calendarDay.getYear();
            }
        });
        calendarView.setSelectedDate(calendar);
        setOnclick();

        if (listEvent.size()>0){

            setTextForUpcoming();
        }else{
            commingUp.setVisibility(View.INVISIBLE);
        }
        setupLunar();
    }
    private void setOnclick(){
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEvent.class);
                intent.putExtra("day", day);
                intent.putExtra("month",month);
                intent.putExtra("year",year);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==1){
            if (resultCode == RESULT_OK){
                finish();
                startActivity(getIntent());
            }
            if (resultCode ==RESULT_CANCELED){
            }
        }
    }
    private void setTextForUpcoming(){
       Date today = Calendar.getInstance().getTime();

        ArrayList<DateTimeEvent> listDateTime = new ArrayList<>();
        try {
            for (Event x: listEvent){
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                Date date = sdf.parse(x.getDate()+" " +x.getTime());
                int id = x.getId();

                DateTimeEvent dateTimeEvent = new DateTimeEvent(id, date);
                listDateTime.add(dateTimeEvent);
            }
        }catch (Exception e){

        }

        Collections.sort(listDateTime, new Comparator<DateTimeEvent>() {
            @Override
            public int compare(DateTimeEvent o1, DateTimeEvent o2) {
                if(o1.getDate() ==null || o2.getDate()==null){
                    return 0;
                }
                return o1.getDate().compareTo(o2.getDate());
            }
        });
        for (int i =0; i<listEvent.size(); i++){
            if (listDateTime.get(i).getDate().after(today)){
                int id = listDateTime.get(i).getId();
                Event  event = db.getEvent(id);
                eventTitle.setText(event.getTitle());
                eventContent.setText(event.getContent());
                eventTime.setText(event.getTime());
                commingUp.setVisibility(View.VISIBLE);
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    Date date = sdf.parse(event.getDate());

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    eventDate.setText(cal.get(Calendar.DAY_OF_MONTH)+"");
                    switch (cal.get(Calendar.MONTH)+1){
                        case 1: eventMonth.setText("Tháng 1");break;
                        case 2: eventMonth.setText("Tháng 2");break;
                        case 3: eventMonth.setText("Tháng 3");break;
                        case 4: eventMonth.setText("Tháng 4");break;
                        case 5: eventMonth.setText("Tháng 5");break;
                        case 6: eventMonth.setText("Tháng 6");break;
                        case 7: eventMonth.setText("Tháng 7");break;
                        case 8: eventMonth.setText("Tháng 8");break;
                        case 9: eventMonth.setText("Tháng 9");break;
                        case 10: eventMonth.setText("Tháng 10");break;
                        case 11: eventMonth.setText("Tháng 11");break;
                        case 12: eventMonth.setText("Tháng 12");break;
                    }

                }catch (Exception e){

                }
                break;
            }
        }

    }
    ArrayList<Date> listDate = new ArrayList<>();
    ArrayList<CalendarDay> listCalendarDay = new ArrayList<>();
    private void setupLunar(){

       CalendarDay day = calendarView.getCurrentDate();

        int month = day.getMonth();
        int year = day.getYear();
        if ((month+1) ==1 ||(month+1) ==3 ||(month+1) ==5 ||(month+1) ==7||(month+1) ==8 ||(month+1) ==10 ||(month+1) ==12){
            for (int i = 1; i<=31;i++){
                Date date = stringToDate(i+"-"+(month+1)+"-"+year);
                listDate.add(date);
            }
        }else if((month+1)==4||(month+1)==6||(month+1)==9||(month+1)==11){
            for (int i = 1; i<=30;i++){
                Date date = stringToDate(i+"-"+(month+1)+"-"+year);
                listDate.add(date);
            }
        }else if ((month+1)==2){
            if (year%4 ==0){
                for (int i = 1; i<=29;i++){
                    Date date = stringToDate(i+"-"+(month+1)+"-"+year);
                    listDate.add(date);
                }
            }else{
                for (int i = 1; i<=28;i++){
                    Date date = stringToDate(i+"-"+(month+1)+"-"+year);
                    listDate.add(date);
                }
            }
        }

        for (Date x: listDate){
            listCalendarDay.add(CalendarDay.from(x));
            ChineseCalendar cnCal = new ChineseCalendar(x);
            LunarDecorator decorator = new LunarDecorator(listCalendarDay,cnCal.get(ChineseCalendar.DAY_OF_MONTH)+"");
            calendarView.addDecorator(decorator);
            listCalendarDay.remove(0);
        }




        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView materialCalendarView, CalendarDay calendarDay) {
                listDate.clear();
                listCalendarDay.clear();
                int month = calendarDay.getMonth();
                int year = calendarDay.getYear();
                if ((month+1) ==1 ||(month+1) ==3 ||(month+1) ==5 ||(month+1) ==7||(month+1) ==8 ||(month+1) ==10 ||(month+1) ==12){
                    for (int i = 1; i<=31;i++){
                        Date date = stringToDate(i+"-"+(month+1)+"-"+year);
                        listDate.add(date);
                    }
                }else if((month+1)==4||(month+1)==6||(month+1)==9||(month+1)==11){
                    for (int i = 1; i<=30;i++){
                        Date date = stringToDate(i+"-"+(month+1)+"-"+year);
                        listDate.add(date);
                    }
                }else if ((month+1)==2){
                    if (year%4 ==0){
                        for (int i = 1; i<=29;i++){
                            Date date = stringToDate(i+"-"+(month+1)+"-"+year);
                            listDate.add(date);
                        }
                    }else{
                        for (int i = 1; i<=28;i++){
                            Date date = stringToDate(i+"-"+(month+1)+"-"+year);
                            listDate.add(date);
                        }
                    }
                }
                for (Date x: listDate){
                    listCalendarDay.add(CalendarDay.from(x));
                    ChineseCalendar cnCal = new ChineseCalendar(x);
                    LunarDecorator decorator = new LunarDecorator(listCalendarDay,cnCal.get(ChineseCalendar.DAY_OF_MONTH)+"");
                    calendarView.addDecorator(decorator);
                    listCalendarDay.remove(0);
                }

            }
        });

    }
    private Date stringToDate(String s){
        Date date;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            date = sdf.parse(s);
        }catch (Exception e){
            return null;
        }

        return date;
    }
}
