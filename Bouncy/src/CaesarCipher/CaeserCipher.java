package CaesarCipher;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class CaesarCipher {
    private int key;

    public CaesarCipher(int key) {
        this.key = key;
    }

    public void encrypt (InputStream in, OutputStream out) throws IOException{
        long time = System.nanoTime();
        boolean done = false;
        while (!done){
            int next = in.read();
            if (next == -1){
                done = true;
            }else {
                byte b = (byte) next;
                byte c = (byte) (b + key);

                out.write(c);
                time = System.nanoTime() - time;
                System.out.printf("Elapsed %,9.3f ms\n", time/1_000_000.0);
            }
        }
    }
}
