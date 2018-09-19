package ca.ualberta.cs.lonelytwitter;

import java.util.Date;

public class Mood2 extends CurrentMood {
    private String Mood;

    Mood2(){
        super();
        this.Mood = "Angry";
    }
    Mood2(Date date){
        super(date);
        this.Mood = "Angry";
    }

    @Override
    public String getMood() {
        return this.Mood;
    }
}
