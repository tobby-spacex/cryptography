package CaesarCipher;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            FileInputStream inputStream = new FileInputStream("C:\\Users\\A\\Desktop\\test\\DeleteM.docx");
            FileOutputStream outputStream = new FileOutputStream("C:\\Users\\A\\Desktop\\test\\Delet.docx");

            CaesarCipher cipher = new CaesarCipher(-5);
            cipher.encrypt(inputStream, outputStream);
            inputStream.close();
            outputStream.close();
        }catch (IOException e){
            System.out.println("Error " + e);
            System.out.println("Done");

        }


    }
}

