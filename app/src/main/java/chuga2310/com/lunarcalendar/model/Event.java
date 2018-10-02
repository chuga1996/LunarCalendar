package chuga2310.com.lunarcalendar.model;

import java.util.Date;

public class Event {
    private int id;
    private String date;
    private String title;
    private String content;
    private String time;

    public static final String TABLE_NAME = "Event";
    public static final String COL_ID = "id";
    public static final String COL_DATE = "date";
    public static final String COL_TITLE = "title";
    public static final String COL_CONTENT = "content";
    public static final String COL_TIME = "time";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( "
                                        + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                        + COL_DATE + " TEXT, "
                                        + COL_TITLE + " TEXT, "
                                        + COL_CONTENT + " TEXT, "
                                        + COL_TIME + " TEXT "
                                        + " ) ";

    public Event(int id, String date, String title, String content, String time) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.content = content;
        this.time = time;
    }

    public Event(String date, String title, String content, String time) {
        this.date = date;
        this.title = title;
        this.content = content;
        this.time = time;
    }

    public Event() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
