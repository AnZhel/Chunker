package chunker;

import java.io.*;

/**
 * Created by Andrey on 23.01.2016.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("c:\\java\\serialize.txt");
        BufferedReader bf = new BufferedReader(new FileReader(file));
        byte[] buf = new byte[(int)file.length()+1];
        int i = 0;
        while ((buf[i++] = (byte)bf.read())!=-1){}
        FileOutputStream os = new FileOutputStream("c:\\java\\serialize_chunked.txt");
        ChunkDecoder cd = new ChunkDecoder(os,new Chunker(30));
        cd.write(buf);
        cd.finish();
        FileInputStream is = new FileInputStream("c:\\java\\serialize_chunked.txt");
        ChunkEncoder ce = new ChunkEncoder(is, new Chunker());
        BufferedWriter bw = new BufferedWriter(new FileWriter("c:\\java\\serialize_unchunked.txt"));
        ce.read(buf);
        bw.write(new String (buf,"CP1251"));
        bw.flush();
        bw.close();
    }
}
