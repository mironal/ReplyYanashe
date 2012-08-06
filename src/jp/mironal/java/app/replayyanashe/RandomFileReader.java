package jp.mironal.java.app.replayyanashe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Random;

/**
 * 例外を上に投げたくないので、基本的にもみ消している.
 * 
 * 基本的にファイルが有るかどうかをisFileExist()で調べてから使ったほうが良い.
 * @author yama
 *
 */
public class RandomFileReader {

    private boolean isFileExist = false;
    private final String filename;
    private int linage = 0;
    
    public RandomFileReader(String filename) {
        this.filename = filename;
        isFileExist = isFileExist();
        if( isFileExist ){
            updateLinage();
        }
    }

    public boolean isFileExist() {
        isFileExist = new File(filename).exists();
        return isFileExist;
    }

    /**
     * ファイルから一行Randomに読み出す.
     * IOエラーの時は空文字が返る.
     * ファイルがない場合も、空文字が返る.
     * @return
     */
    public String randomRead() {
        if (!isFileExist) {
            return "";
        }
        
        int lineNum = new Random().nextInt(linage);
        String line = "";
        LineNumberReader reader = null;
        try {
            reader = new LineNumberReader(new FileReader(filename));
            reader.setLineNumber(lineNum);
            line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
        
        return line;
    }

    /**
     * ファイルの行数を取得する.
     * ファイルがない場合には-1になる.
     * IOエラーが発生した場合は0になる.
     * @return
     */
    public int updateLinage(){
        if( !isFileExist() ){
            linage = -1;
            return linage;
        }
        
        BufferedReader br = null;
        linage = 0;
        try{
            br = new BufferedReader(new FileReader(filename));
            int cnt = 0;
            while (br.readLine() != null) {
                cnt++;
            }
            linage = cnt;
        }catch (IOException e) {
            e.printStackTrace();
        }finally{
            if( br != null ){
                try {
                    br.close();
                } catch (IOException e) {
                    // TODO ignore
                }
            }
        }
        
        return linage;
    }

}
