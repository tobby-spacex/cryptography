package AES_NEW;

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

    public static void encryptDecryptOperations(int cipherMode, File in, java.io.File out)
            throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IOException, NoSuchProviderException, InvalidAlgorithmParameterException {

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        FileInputStream tim = new FileInputStream(in);
        FileOutputStream tom = new FileOutputStream(out);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");

        SecretKeySpec keySpec = new SecretKeySpec("qwertyuiop123456".getBytes(), "AES");

        byte[] ivBytes = new byte[16];
        IvParameterSpec iv = new IvParameterSpec(ivBytes);


        long startTime;
        long estimatedTime;
        startTime = System.nanoTime();

        if (cipherMode == Cipher.ENCRYPT_MODE){
            cipher.init(Cipher.ENCRYPT_MODE, keySpec,iv, SecureRandom.getInstance("SHA1PRNG"));


            CipherInputStream cis = new CipherInputStream(tim, cipher);
            write(cis,tom);

            estimatedTime = (System.nanoTime() - startTime)/1000000;
            System.out.println("Encryption lead time is " + estimatedTime + " ");

        }


        else if (cipherMode == Cipher.DECRYPT_MODE){
            cipher.init(Cipher.DECRYPT_MODE,keySpec, iv, SecureRandom.getInstance("SHA1PRNG"));
            CipherOutputStream cos = new CipherOutputStream(tom, cipher);
            write(tim,cos);
        }
        estimatedTime = (System.nanoTime() - startTime)/1000000;
        System.out.println("Decryption lead time is " + estimatedTime + " ");

    }


    private static  void  write(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[64];
        int numOfBytesRead;
        while ((numOfBytesRead = in.read(buffer)) != -1){
            out.write(buffer, 0 , numOfBytesRead);
        }
        out.close();
        in.close();

    }

    public static void main(String[] args) {

        File encrypted = new File("C:\\Users\\A\\Desktop\\test\\Next.docx");
        File decrypted = new File("C:\\Users\\A\\Desktop\\test\\EcryptedFile.docx");

        File decrypte = new File("C:\\Users\\A\\Desktop\\test\\EcryptedFile.docx");
        File encrypte = new File("C:\\Users\\A\\Desktop\\test\\DecryptedFile.docx");



        try {
            encryptDecryptOperations(Cipher.ENCRYPT_MODE, encrypted,decrypted);
            encryptDecryptOperations(Cipher.DECRYPT_MODE, decrypte,encrypte);
            System.out.println("The file  is ready");
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException | NoSuchProviderException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
