package chuga2310.com.lunarcalendar.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

import chuga2310.com.lunarcalendar.model.Event;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "event_db";
    private static final int DATABASE_VERSION = 1;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Event.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Event.TABLE_NAME);
        onCreate(db);
    }

    public void addEvent(Event event){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Event.COL_TITLE, event.getTitle());
        values.put(Event.COL_CONTENT, event.getContent());
        values.put(Event.COL_DATE, event.getDate());
        values.put(Event.COL_TIME, event.getTime());
        db.insert(Event.TABLE_NAME, null,values);
        db.close();
    }

    public ArrayList<Event> getAllEvent(){
        SQLiteDatabase db= this.getReadableDatabase();
        ArrayList<Event> listEvent = new ArrayList<>();
        String query = "SELECT * FROM "+ Event.TABLE_NAME + " ORDER BY " + Event.COL_ID + " DESC";
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            do {
                Event event = new Event();
                event.setId(cursor.getInt(cursor.getColumnIndex(Event.COL_ID)));
                event.setTitle(cursor.getString(cursor.getColumnIndex(Event.COL_TITLE)));
                event.setContent(cursor.getString(cursor.getColumnIndex(Event.COL_CONTENT)));
                event.setDate(cursor.getString(cursor.getColumnIndex(Event.COL_DATE)));
                event.setTime(cursor.getString(cursor.getColumnIndex(Event.COL_TIME)));
                listEvent.add(event);
            }while (cursor.moveToNext());
        }
        db.close();
        return listEvent;
    }
    public  Event getEvent(int id){
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor = db.query(Event.TABLE_NAME,
                new String[]{Event.COL_ID, Event.COL_TIME, Event.COL_TITLE, Event.COL_CONTENT, Event.COL_DATE}, Event.COL_ID + "=?",
                new String[]{String.valueOf(id)},null,null,null,null);
        if (cursor!=null){
            cursor.moveToFirst();
        }
        Event event = new Event();
        event.setId(cursor.getInt(cursor.getColumnIndex(Event.COL_ID)));
        event.setTitle(cursor.getString(cursor.getColumnIndex(Event.COL_TITLE)));
        event.setContent(cursor.getString(cursor.getColumnIndex(Event.COL_CONTENT)));
        event.setDate(cursor.getString(cursor.getColumnIndex(Event.COL_DATE)));
        event.setTime(cursor.getString(cursor.getColumnIndex(Event.COL_TIME)));
        cursor.close();
        db.close();
        return event;
    }
}
