package ca.ualberta.cs.lonelytwitter;

public class ImportantTweet extends Tweet {

    ImportantTweet(){
        super();
    }

    ImportantTweet(String message){
        super(message);
    }

    @Override
    public Boolean isImportant(){
        return true;
    }
}
