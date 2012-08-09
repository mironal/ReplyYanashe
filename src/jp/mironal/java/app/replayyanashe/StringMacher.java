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
        // ファイルが存在しない場合は毎回探しに行く.
        // 　ファイルが作られた時にすぐに気がつけるようにするため.
        if (!reader.isFileExist()) {
            reader.updateLinage();
        } else if ((cnt % 10) == 0) {
            // 10回に一度ぐらい行数を再読み込みする.
            // ファイルに新しい行が追加されている場合があるため.
            reader.updateLinage();
        }
        return reader.randomRead();
    }

    @Override
    public void onMatch(Status status) {

        LOGGER.info("onMatch:" + word);

        String replyWord = makeReplyWord();

        try {
            if ((replyWord != null) && (replyWord.length() > 0)) {
                replyHelper.reply(status, replyWord);
            } else {
                if (replyWord == null) {
                    LOGGER.warning("replyWord is null.");
                } else if (replyWord.length() == 0) {
                    LOGGER.warning("replyWord is empty.");
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
