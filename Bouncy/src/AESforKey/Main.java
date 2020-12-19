package AESforKey;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class Main {
        private static String secretKey = "bqui3hg[qre[ghpr[euighp[rqghp[rueghp[rqughp[qughprego";
        private static String salt = "ssqugowfugoqwrbvgfoqugfoubrguquyg";
        public static void encryptDecryptOperations(int cipherMode, File in, java.io.File out)
            throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IOException, NoSuchProviderException, InvalidAlgorithmParameterException
        {

            Security.addProvider(new BouncyCastleProvider());


            FileInputStream tim = new FileInputStream(in);
            FileOutputStream tom = new FileOutputStream(out);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING", "BC");

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 128);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");


            byte[] ivBytes = new byte[16];
            IvParameterSpec iv = new IvParameterSpec(ivBytes);

            long startTime;
            long estimatedTime;
            startTime = System.nanoTime();

            if (cipherMode == Cipher.ENCRYPT_MODE){
                cipher.init(Cipher.ENCRYPT_MODE, secretKey,iv, SecureRandom.getInstance("SHA1PRNG"));
                CipherInputStream cis = new CipherInputStream(tim, cipher);
                write(cis,tom);

                estimatedTime = (System.nanoTime() - startTime)/1000000;
                System.out.println("Encryption lead time is " + estimatedTime + " ");

            }


            else if (cipherMode == Cipher.DECRYPT_MODE){
                long startTie;
                long estimatedTie;
                startTie = System.nanoTime();
                cipher.init(Cipher.DECRYPT_MODE,secretKey, iv, SecureRandom.getInstance("SHA1PRNG"));
                CipherOutputStream cos = new CipherOutputStream(tom, cipher);
                write(tim,cos);
                estimatedTie = (System.nanoTime() - startTie) / 1000000;
                System.out.println("Decryption lead time is " + estimatedTie + " ");
                System.out.println();
            }
//            estimatedTime = (System.nanoTime() - startTime)/1000000;
//            System.out.println("Decryption lead time is " + estimatedTime + " ");

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
            for (int i = 1; i < 11; i++) {
                System.out.println("The value of i is: " + i);

        File encrypted = new File("C:\\Users\\A\\Desktop\\test\\TEST_512kb.docx");
        File decrypted = new File("C:\\Users\\A\\Desktop\\test\\DeletMeTo.docx");

        File decrypte = new File("C:\\Users\\A\\Desktop\\test\\DeletMeTo.docx");
        File encrypte = new File("C:\\Users\\A\\Desktop\\test\\delete.docx");



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
    }}
    }
