package ca.ualberta.cs.lonelytwitter;

import java.util.Date;

public class Mood1 extends CurrentMood {
    private String Mood;

    Mood1(){
        super();
        this.Mood = "Sad";
    }
    Mood1(Date date){
        super(date);
        this.Mood = "Sad";
    }

    @Override
    public String getMood() {
        return this.Mood;
    }
}
