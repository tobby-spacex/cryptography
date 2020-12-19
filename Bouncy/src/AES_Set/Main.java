package AES_Set;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

public class Main {

        public static void encryptDecryptOperations(int cipherMode, File in, java.io.File out)
                throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IOException, InvalidAlgorithmParameterException, NoSuchProviderException {

            Security.addProvider(new BouncyCastleProvider());
            FileInputStream tim = new FileInputStream(in);
            FileOutputStream tom = new FileOutputStream(out);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");

            KeyGenerator key = KeyGenerator.getInstance("AES");
            key.init(256);
            SecretKey teakey = key.generateKey();





            byte[] ivBytes = new byte[16];
            IvParameterSpec iv = new IvParameterSpec(ivBytes);

            long startTime;
            long estimatedTime;
            startTime = System.nanoTime();

            if (cipherMode == Cipher.ENCRYPT_MODE){
                cipher.init(Cipher.ENCRYPT_MODE, teakey,iv, SecureRandom.getInstance("SHA1PRNG"));
                CipherInputStream cis = new CipherInputStream(tim, cipher);
                write(cis,tom);

                estimatedTime = (System.nanoTime() - startTime)/1000000;
                System.out.println("Encryption lead time is " + estimatedTime + " ");


            }


            else if (cipherMode == Cipher.DECRYPT_MODE){
                cipher.init(Cipher.DECRYPT_MODE,teakey,iv,SecureRandom.getInstance("SHA1PRNG"));
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
               File encrypted = new File("C:\\Users\\A\\Desktop\\test\\TEST_1024kb.docx");
               File decrypted = new File("C:\\Users\\A\\Desktop\\test\\deleteMe.docx");

            try {
                  encryptDecryptOperations(Cipher.ENCRYPT_MODE, encrypted,decrypted);
//                encryptDecryptOperations(Cipher.DECRYPT_MODE, decrypted,encrypted);
                System.out.println("The file  is ready");
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (IOException | InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            }
        }
    }

