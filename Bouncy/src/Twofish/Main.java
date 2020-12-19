package Twofish;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.Security;

public class Main {
            public static void main(String[] args) {
                Security.addProvider(new BouncyCastleProvider());
                try {

                FileInputStream fis = new FileInputStream("C:\\Users\\A\\Desktop\\test\\TEST_2048kb.docx");
                FileOutputStream fos = new FileOutputStream("C:\\Users\\A\\Desktop\\test\\Delete.docx");
                encrypt(fis, fos);

                FileInputStream fis2 = new FileInputStream("C:\\Users\\A\\Desktop\\test\\Delete.docx");
                FileOutputStream fos2 = new FileOutputStream("C:\\Users\\A\\Desktop\\test\\DeleteMeTo.docx");
                decrypt(fis2, fos2);

            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

            public static void encrypt(InputStream is, OutputStream os)
            throws Throwable {
            encryptOrDecrypt(Cipher.ENCRYPT_MODE, is, os);
        }


            public static void decrypt(InputStream is, OutputStream os)
            throws Throwable {
            encryptOrDecrypt(Cipher.DECRYPT_MODE, is, os);
        }

            public static void encryptOrDecrypt(int mode, InputStream is,
                OutputStream os) throws Throwable {


          SecretKeySpec keySpec = new SecretKeySpec("qwertyuiop12345678910111qwertyui".getBytes(), "Twofish"); //key size 256 bits

            Cipher cipher = Cipher.getInstance("Twofish/OFB/PKCS5Padding");


            byte[] ivSpec = new byte[8*2];
            IvParameterSpec iv = new IvParameterSpec(ivSpec);

            long startTime;
            long estimatedTime;
            startTime = System.nanoTime();

            if (mode == Cipher.ENCRYPT_MODE) {

                cipher.init(Cipher.ENCRYPT_MODE, keySpec,iv);
                CipherInputStream cis = new CipherInputStream(is, cipher);
                doCopy(cis, os);
                estimatedTime = (System.nanoTime() - startTime)/1000000;
                System.out.println("Encryption lead time is " + estimatedTime + " ");

            }

            else if (mode == Cipher.DECRYPT_MODE) {
                cipher.init(Cipher.DECRYPT_MODE, keySpec,iv);
                CipherOutputStream cos = new CipherOutputStream(os, cipher);
                doCopy(is, cos);

                estimatedTime = (System.nanoTime() - startTime)/1000000;
                System.out.println("Decryption lead time is " + estimatedTime + " ");

            }
        }

            public static void doCopy(java.io.InputStream is, OutputStream os)
            throws IOException {
            byte[] bytes = new byte[64];
            int numBytes;
            while ((numBytes = is.read(bytes)) != -1) {
                os.write(bytes, 0, numBytes);
            }
            os.flush();
            os.close();
            is.close();
        }
        }


