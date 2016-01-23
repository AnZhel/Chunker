package chunker;

import java.util.InputMismatchException;

/**
 * Created by Andrey on 23.01.2016.
 */
public class Chunker {
    private int chunkSize;
    public static final String EOC = "0\r\n\r\n";
    public static final String EOL = "\r\n";
    public Chunker(){
        this.chunkSize = 5;
    }

    public Chunker(int headSize){
        this.chunkSize = headSize;
    }

    public void setChunkerSize(int headSize) {
        this.chunkSize = headSize;
    }

    public int getChunkSize() {
        return chunkSize;
    }

    public byte[] getHead(){
        return Integer.toHexString(chunkSize).getBytes();
    }

    public int getChunkes(byte[] b){
        return b.length / chunkSize;
    }

    public int getTail(byte[] b){
        return b.length % chunkSize;
    }

    public byte[] getTailBytes(byte[] b){
        return Integer.toHexString(getTail(b)).getBytes();
    }
}
