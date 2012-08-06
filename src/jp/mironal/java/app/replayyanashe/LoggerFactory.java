package jp.mironal.java.app.replayyanashe;

import java.util.logging.Logger;

public class LoggerFactory {

    public static final Logger getLogger(Class<?> clazz) {
        return Logger.getLogger(clazz.getSimpleName());
    }
}
