package TimeTesting;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

public class Main {

    public static void encryptDecryptOperations(int cipherMode, File in, File out)
            throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IOException, InvalidAlgorithmParameterException, NoSuchProviderException {

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        FileInputStream tim = new FileInputStream(in);
        FileOutputStream tom = new FileOutputStream(out);

        Cipher cipher = Cipher.getInstance("Twofish/OFB/PKCS5Padding", "BC");

//        SecretKeySpec keySpec = new SecretKeySpec("qwertyuiop123456".getBytes(), "Twofish");  //key size 128 bits
//        SecretKeySpec keySpec = new SecretKeySpec("qwertyuiop12345678910111".getBytes(), "Twofish"); //key size 192 bits
        SecretKeySpec keySpec = new SecretKeySpec("qwertyuiop12345678910111qwertyui".getBytes(), "Twofish"); //key size 256 bits

//        SecretKeySpec keySpec = new SecretKeySpec("qwertyuiop12345678910111qwertyup".getBytes(), "Threefish-256"); //key size 256 bits
//        SecretKeySpec keySpec = new SecretKeySpec("qwertyuiopasdfghjklzxcvbnmasdfjgklvnd1234567vcxdfghjkltyui784562".getBytes(), "Threefish-512"); //key size 512 bits
//        SecretKeySpec keySpec = new SecretKeySpec(("qwertyuiopasdfghjklzxcvbnmasdfjgklvnd1234567vcxdfghjkltyui784562" +
//                "qwertyuiopasdfghjklzxcvbnmasdfjgklvnd1234567vcxdfghjkltyui784562").getBytes(), "Threefish-1024"); //key size 1024 bits


        byte[] ivBytes = new byte[16];
        IvParameterSpec iv = new IvParameterSpec(ivBytes);

        long startTime;
        long estimatedTime;
        startTime = System.nanoTime();

        if (cipherMode == Cipher.ENCRYPT_MODE) {
//            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv, SecureRandom.getInstance("SHA1PRNG")); // default
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv, SecureRandom.getInstance("Windows-PRNG")); // For Windows operating system
            CipherInputStream cis = new CipherInputStream(tim, cipher);
            write(cis, tom);

            estimatedTime = (System.nanoTime() - startTime) / 1000000;
            System.out.println("Encryption lead time is " + estimatedTime + " ");
        } else if (cipherMode == Cipher.DECRYPT_MODE) {
            long startTie;
            long estimatedTie;
            startTie = System.nanoTime();
//            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv, SecureRandom.getInstance("SHA1PRNG"));
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv, SecureRandom.getInstance("Windows-PRNG"));
            CipherOutputStream cos = new CipherOutputStream(tom, cipher);
            write(tim, cos);

            estimatedTie = (System.nanoTime() - startTie) / 1000000;
            System.out.println("Decryption lead time is " + estimatedTie + " ");
            System.out.println();
        }
    }

    private static void write(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[64];
        int numOfBytesRead;
        while ((numOfBytesRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, numOfBytesRead);
        }
        out.close();
        in.close();

    }

    public static void main(String[] args) {

        for (int i = 1; i < 11; i++) {
            System.out.println("The value of i is: " + i);

            File plainText = new File("C:\\Users\\A\\Desktop\\test\\TEST_2048kb.mp4"); // Encryption
            File encrypted = new File("C:\\Users\\A\\Desktop\\test\\Delete.mp4");

            File encrypte = new File("C:\\Users\\A\\Desktop\\test\\Delete.mp4"); // Decryption
            File plainTex = new File("C:\\Users\\A\\Desktop\\test\\Delete512.mp4");

            try {
                encryptDecryptOperations(Cipher.ENCRYPT_MODE, plainText, encrypted); // Encryption
                encryptDecryptOperations(Cipher.DECRYPT_MODE, encrypte, plainTex); // Decryption
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException | NoSuchProviderException e) {
                e.printStackTrace();
            } catch (IOException | InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            }
        }
    }
}