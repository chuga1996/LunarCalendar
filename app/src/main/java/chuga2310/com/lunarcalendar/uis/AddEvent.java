package chuga2310.com.lunarcalendar.uis;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import chuga2310.com.lunarcalendar.R;
import chuga2310.com.lunarcalendar.database.DatabaseHelper;
import chuga2310.com.lunarcalendar.model.Event;

public class AddEvent extends Activity {
    private EditText edtTitle;
    private EditText edtContent;
    private TextView edtTime;
    private TextView edtDate;
    private Button btnOk;
    private Button btnCancel;
    String title, content, time, date;
    int mHour, mMinute, mDate,mMonth, mYear;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        db = new DatabaseHelper(this);
        edtTitle = (EditText) findViewById(R.id.edtTitle);
        edtContent = (EditText) findViewById(R.id.edtContent);
        edtTime =  findViewById(R.id.edtTime);
        edtDate =findViewById(R.id.edtDate);
        btnOk = (Button) findViewById(R.id.btnOk);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        int day, month, year;
        day = getIntent().getIntExtra("day",1);
        month =getIntent().getIntExtra("month",10);
        year =getIntent().getIntExtra("year",2018);
        String ngay, thang;
        if (day<10){
            ngay = "0"+day;
        }else{
            ngay =day+"";
        }
        if (month<10){
            thang = "0"+month;

        }else{
            thang = month+"";
        }
        edtDate.setText(ngay +"-" +thang+"-" +year);
        edtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                mHour = cal.get(Calendar.HOUR);
                mMinute = cal.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddEvent.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mHour = hourOfDay;
                        mMinute = minute;
                        String gio,phut;
                        if (mHour<10){
                            gio = "0"+mHour;
                        }else{
                            gio =mHour+"";
                        }
                        if (mMinute<10){
                            phut = "0"+mMinute;

                        }else{
                            phut = mMinute+"";
                        }
                        edtTime.setText(gio+":"+phut);
                    }
                },mHour,mMinute,false);
                timePickerDialog.show();
            }
        });
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                mDate = cal.get(Calendar.DATE);
                mMonth = cal.get(Calendar.MONTH);
                mYear = cal.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddEvent.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mYear = year;
                        mMonth = month+1;
                        mDate = dayOfMonth;
                        String ngay, thang;
                        if (mDate<10){
                            ngay = "0"+mDate;
                        }else{
                            ngay =mDate+"";
                        }
                        if (mMonth<10){
                            thang = "0"+mMonth;

                        }else{
                            thang = mMonth+"";
                        }
                        edtDate.setText(ngay +"-" +thang+"-" +mYear);
                    }
                }, mYear,mMonth,mDate);
                datePickerDialog.show();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = edtDate.getText().toString();
                time = edtTime.getText().toString();
                content = edtContent.getText().toString();
                title =edtTitle.getText().toString();
                Event event = new Event(date, title,content,time);
                db.addEvent(event);
                setResult(RESULT_OK);
                finish();

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}
