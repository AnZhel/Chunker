package chunker;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Andrey on 23.01.2016.
 */
public class ChunkDecoder extends OutputStream {
    protected OutputStream out;
    protected Chunker chunker;

    public ChunkDecoder(){
        super();
    }

    public ChunkDecoder(OutputStream os, Chunker ch){
        super();
        this.out = os;
        this.chunker = ch;
    }

    @Override
    public void write(int b) throws IOException{
        out.write(b);
    }

    public void write(byte b[]) throws IOException{
        int offset = 0;
        for (int i = 0; i < chunker.getChunkes(b); i++) {
            out.write(chunker.getHead());
            out.write(Chunker.EOL.getBytes());
            out.write(b,offset,chunker.getChunkSize());
            out.write(Chunker.EOL.getBytes());
            offset += chunker.getChunkSize();
        }
        if (chunker.getTail(b)>0){
            out.write(chunker.getTailBytes(b));
            out.write(Chunker.EOL.getBytes());
            out.write(b,offset,chunker.getTail(b));
            out.write(Chunker.EOL.getBytes());
        }
        out.write(Chunker.EOC.getBytes());
    }

    public void finish() throws IOException{
        out.flush();
        out.close();
    }
}
