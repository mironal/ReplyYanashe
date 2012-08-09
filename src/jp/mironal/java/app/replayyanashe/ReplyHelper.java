package jp.mironal.java.app.replayyanashe;

import java.util.logging.Logger;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class ReplyHelper {

    private static final Logger LOGGER = jp.mironal.java.app.replayyanashe.LoggerFactory
            .getLogger(ReplyHelper.class);

    private final Twitter twitter = new TwitterFactory().getInstance();

    public void reply(Status status, String text) throws TwitterException {
        if (status == null) {
            throw new NullPointerException("status is null.");
        }
        if (text == null) {
            throw new NullPointerException("text is null.");
        }
        if (text.length() == 0) {
            throw new IllegalArgumentException("text is empty.");
        }

        StringBuilder builder = new StringBuilder();
        builder.append(text).append(" @")
                .append(status.getUser().getScreenName());

        StatusUpdate update = new StatusUpdate(builder.toString());
        update.inReplyToStatusId(status.getId());
        Status reslt = twitter.updateStatus(update);
        LOGGER.info("Reply:statusId=" + reslt.getId() + "inReplyToStatusId="
                + status.getId() + "text=" + text);
    }
}
