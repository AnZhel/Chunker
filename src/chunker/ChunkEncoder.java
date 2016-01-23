package chunker;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Andrey on 23.01.2016.
 */
public class ChunkEncoder extends InputStream {

    protected volatile InputStream in;
    protected Chunker chunker;
    private int skiped = 0;

    public ChunkEncoder(){
        super();
    }

    public ChunkEncoder(InputStream is, Chunker ch){
        this.in = is;
        this.chunker = ch;
    }

    @Override
    public int read() throws IOException {
        return in.read();
    }

    public String readBytes() throws IOException {
        StringBuilder res = new StringBuilder();
        while (setChunkerSize()){
            byte[] b = new byte[chunker.getChunkSize()];
            for (int i = 0; i < b.length ; i++) {
                b[i] = (byte)in.read();
            }
            res.append(new String(b,"cp1251"));
            in.skip(2);
        }
        return res.toString();
    }

    private boolean setChunkerSize() throws IOException{
        int size = 0;
        String head = "";
        String eol = "";
        while (!eol.equals(Chunker.EOL)){
            head = head+(char)in.read();
            if (head.length()>=2){
                eol = head.substring(head.length()-2);
            }
        }
        if(!eol.equals(Chunker.EOL)){
            throw new IOException("Bad format!");
        }
        head = head.replace(Chunker.EOL,"");
        size = Integer.parseInt(head,16);
        if (size==0) return false;
        chunker.setChunkerSize(size);
        return true;
    }

}
