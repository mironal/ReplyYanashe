package jp.mironal.java.app.replayyanashe;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class ReplyHelper {

    private final Twitter twitter = new TwitterFactory().getInstance();
    
    
    public void reply(Status status, String text) throws TwitterException{
        if( status == null ){
            throw new NullPointerException("status is null.");
        }
        if( text == null ){
            throw new NullPointerException("text is null.");
        }
        
        StatusUpdate update = new StatusUpdate(text);
        update.inReplyToStatusId(status.getId());
        twitter.updateStatus(update);
    }
}
