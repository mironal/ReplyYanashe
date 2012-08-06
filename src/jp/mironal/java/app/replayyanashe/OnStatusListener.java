package jp.mironal.java.app.replayyanashe;

import twitter4j.Status;

public interface OnStatusListener {
    void onStatus(Status status);
}
