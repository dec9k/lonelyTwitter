package ca.ualberta.cs.lonelytwitter;

import java.util.Date;

public abstract class CurrentMood {
    private Date date;
    private String Mood;

    CurrentMood(){
        setDate(new Date());
    }

    CurrentMood(Date date){
        setDate(date);
    }

    public void setDate(Date date){
        this.date = date;
    }

    public abstract String getMood();

    public Date getDate(){
        return this.date;
    }
}
