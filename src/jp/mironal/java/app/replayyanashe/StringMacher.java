package jp.mironal.java.app.replayyanashe;

import java.util.logging.Logger;

import twitter4j.Status;
import twitter4j.TwitterException;

public class StringMacher implements OnMatchListener {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(StringMacher.class);

    private String word;
    private int cnt = 0;

    private final RandomFileReader reader;

    private ReplyHelper replyHelper = new ReplyHelper();

    public StringMacher(String word, String filename) {
        if (word == null) {
            throw new NullPointerException("word is null.");
        }
        if (filename == null) {
            throw new NullPointerException("filename is null.");
        }
        this.word = word;
        this.reader = new RandomFileReader(filename);
    }

    @Override
    public String makeReplyWord() {
        cnt++;
        if ((cnt % 10) == 0) {
            reader.updateLinage();
        }
        return reader.randomRead();
    }

    @Override
    public void onMatch(Status status) {

        LOGGER.info("onMatch:" + word);
        String word = makeReplyWord();

        try {
            if ((word != null) && (word.length() > 0)) {
                replyHelper.reply(status, word);
            }else{
                if( word == null){
                    LOGGER.warning("word is null.");
                }else if(word.length() == 0) {
                    LOGGER.warning("word is empty.");
                }
            }
        } catch (TwitterException e) {
            // とりあえずログ出すだけ.
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof String)) {
            return false;
        }
        return word.equals(obj);
    }

}
