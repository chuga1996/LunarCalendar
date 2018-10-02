package chuga2310.com.lunarcalendar.model;

import java.util.Date;

public class DateTimeEvent {
    private int id;
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public DateTimeEvent() {

    }

    public DateTimeEvent(int id, Date date) {

        this.id = id;
        this.date = date;
    }
}
