package DES;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

// BLOCK CIPHERING
public class DES {

    public static void encryptDecryptOperations(String key, int cipherMode, File in, File out)
            throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IOException, InvalidAlgorithmParameterException, NoSuchProviderException {

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        FileInputStream tim = new FileInputStream(in);
        FileOutputStream tom = new FileOutputStream(out);

        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());

        SecretKeyFactory sef = SecretKeyFactory.getInstance("DES", "BC");
        SecretKey secretKey = sef.generateSecret(desKeySpec);

        Cipher cipher = Cipher.getInstance("DES/OFB/PKCS5Padding");
//        long time = System.nanoTime();

        byte[] ivBytes = new byte[8];
        IvParameterSpec iv = new IvParameterSpec(ivBytes);

        long startTime;
        long estimatedTime;
        startTime = System.nanoTime();

        if (cipherMode == Cipher.ENCRYPT_MODE) {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv, SecureRandom.getInstance("SHA1PRNG"));
//                cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv, SecureRandom.getInstance("Windows-PRNG"));
            CipherInputStream cis = new CipherInputStream(tim, cipher);
            write(cis, tom);

            estimatedTime = (System.nanoTime() - startTime) / 1000000;
            System.out.println("Encryption lead time is " + estimatedTime + " ");
        }


        else if (cipherMode == Cipher.DECRYPT_MODE) {
            long startTie;
            long estimatedTie;
            startTie = System.nanoTime();
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv, SecureRandom.getInstance("SHA1PRNG"));
//                cipher.init(Cipher.DECRYPT_MODE, secretKey, iv, SecureRandom.getInstance("Windows-PRNG"));
            CipherOutputStream cos = new CipherOutputStream(tom, cipher);
            write(tim, cos);

            estimatedTie = (System.nanoTime() - startTie) / 1000000;
            System.out.println("Decryption lead time is " + estimatedTie + " ");
            System.out.println();
        }


    }


    private static  void  write(InputStream in, OutputStream out) throws IOException {
        //long m = System.currentTimeMillis();
        byte[] buffer = new byte[64];
        int numOfBytesRead;
        while ((numOfBytesRead = in.read(buffer)) != -1){
            out.write(buffer, 0 , numOfBytesRead);
        }
        out.close();
        in.close();

        //System.out.println((double) (System.currentTimeMillis() - m));

    }

    public static void main(String[] args) {



        for (int i = 1; i < 11; i++) {
            System.out.println("The value of i is: " + i);

            File plainText = new File("C:\\Users\\A\\Desktop\\test\\TEST_512kb.mp4"); // Шифрование
            File encrypted = new File("C:\\Users\\A\\Desktop\\test\\Delete.mp4");

            File encrypte = new File("C:\\Users\\A\\Desktop\\test\\Delete.mp4"); // Дешифрование
            File plainTex = new File("C:\\Users\\A\\Desktop\\test\\Delete512.mp4");


            try {
                encryptDecryptOperations("123441415678", Cipher.ENCRYPT_MODE, plainText, encrypted); // Шифрование
                encryptDecryptOperations("123441415678", Cipher.DECRYPT_MODE, encrypte, plainTex); // Дешифрование
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

