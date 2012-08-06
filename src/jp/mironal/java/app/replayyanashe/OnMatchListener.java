package jp.mironal.java.app.replayyanashe;

import twitter4j.Status;

public interface OnMatchListener {
    void onMatch(Status status);
    
    String makeReplyWord();
}
