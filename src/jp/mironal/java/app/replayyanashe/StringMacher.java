package jp.mironal.java.app.replayyanashe;

import twitter4j.Status;
import twitter4j.TwitterException;


public abstract class StringMacher implements OnMatchListener {

    private String word;
    
    private ReplyHelper replyHelper = new ReplyHelper();
    
    StringMacher(String word) {
        this.word = word;
    }
    

    @Override
    public void onMatch(Status status) {

        String word = makeReplyWord();
        try {
            if( false ){
                replyHelper.reply(status, word);
            }
        } catch (TwitterException e) {
            // とりあえずログ出すだけ.
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if( !(obj instanceof String) ){
            return false;
        }
        return word.equals(obj);
    }

}
