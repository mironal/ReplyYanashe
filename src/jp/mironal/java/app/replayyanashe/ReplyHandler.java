package jp.mironal.java.app.replayyanashe;

import java.util.ArrayList;
import java.util.List;

import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;

import twitter4j.Status;
import twitter4j.TwitterException;

public class ReplyHandler {

    private ReplyHelper replyHelper = new ReplyHelper();

    private StringMacher unkoListener = new StringMacher("ウンコ") {

        @Override
        public void onMatch(Status status) {
            // Reply
            String word = makeReplyWord();
            System.out
                    .println("ReplyHandler.unkoListener.new StringMacher() {...}.onMatch()");
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
        public String makeReplyWord() {
            return "huga";
        }
    };

    private StringMacher oppaiListener = new StringMacher("オッパイ") {
        @Override
        public void onMatch(Status status) {
            // Reply
            String word = makeReplyWord();
            System.out
                    .println("ReplyHandler.oppaiListener.new StringMacher() {...}.onMatch()");
            try {
                if(false){
                    replyHelper.reply(status, word);
                }
            } catch (TwitterException e) {
                // とりあえずログ出すだけ.
                e.printStackTrace();
            }
        }

        @Override
        public String makeReplyWord() {
            return "hoge";
        }
    };

    private ArrayList<OnMatchListener> matchListeners = new ArrayList<>();

    /**
     * 
     * @param listener
     * @return {@link ArrayList#add(Object)}の戻り値
     */
    public boolean addMatchListener(OnMatchListener listener) {
        if (listener == null) {
            throw new NullPointerException("listener is null.");
        }
        return matchListeners.add(listener);
    }

    /**
     * 
     * @param listener
     * @return {@link ArrayList#remove(Object)}の戻り値
     */
    public boolean removeMatchListener(OnMatchListener listener) {
        if (listener == null) {
            throw new NullPointerException("listener is null.");
        }
        return matchListeners.remove(listener);
    }

    private OnStatusListener statusListener = new OnStatusListener() {

        private Tokenizer tokenizer = Tokenizer.builder().build();

        @Override
        public void onStatus(Status status) {
            String text = status.getText();
            List<Token> tokens = tokenizer.tokenize(text);
            System.out.println(text);

            for (Token token : tokens) {
                if (token.isKnown()) {
                    execRepry(status, token);
                }
            }
        }

        private void execRepry(Status status, Token token) {
            for (OnMatchListener l : matchListeners) {
                if (l.equals(token.getReading())) {
                    l.onMatch(status);
                }
            }
        }
    };

    private PollUserStream userStream = new PollUserStream();

    public ReplyHandler() {
        addMatchListener(oppaiListener);
        addMatchListener(unkoListener);
        userStream.addOnStatusListener(statusListener);
        userStream.start();
    }

    public static void main(String[] args) {

        new ReplyHandler();

        final Thread main = Thread.currentThread();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("!! Starting shutdown...");
                System.out.flush();
                main.interrupt();
                try {
                    main.join();
                } catch (InterruptedException e) {
                    // ignore
                }
                System.out.println("Done");
                System.out.flush();
            }
        });

        while (!Thread.interrupted()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
