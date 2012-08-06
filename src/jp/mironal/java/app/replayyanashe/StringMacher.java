package jp.mironal.java.app.replayyanashe;


public abstract class StringMacher implements OnMatchListener {

    private String word;
    
    StringMacher(String word) {
        this.word = word;
    }

    @Override
    public boolean equals(Object obj) {
        if( !(obj instanceof String) ){
            return false;
        }
        return word.equals(obj);
    }

}
