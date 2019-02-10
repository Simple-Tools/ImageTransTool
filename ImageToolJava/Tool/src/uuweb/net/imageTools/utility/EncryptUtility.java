package uuweb.net.imageTools.utility;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author ZDT
 * Created at 2019/2/10 12:21
 */
public class EncryptUtility {
    /**
     * @param input
     * @param secretKey
     * @return
     */
    public static byte[] encode(byte[] input, String secretKey) throws Exception {
        return encryptOrDecrypt(secretKey, Cipher.ENCRYPT_MODE, input);
    }

    /**
     * @param input
     * @param secretKey
     * @return
     */
    public static byte[]  decode(byte[] input, String secretKey) throws Exception {
        return encryptOrDecrypt(secretKey, Cipher.DECRYPT_MODE, input);
    }


    /**
     * EncryptFile
     * @param file
     * @param dest
     * @param key
     * @throws Exception
     */
    public static void encryptFile(String file, String dest, String key){
        encryptOrDecryptFile(file,dest,key, Cipher.ENCRYPT_MODE);
    }

    /**
     * DecryptFile
     * @param file
     * @param dest
     * @param key
     * @throws Exception
     */
    public static void decryptFile(String file, String dest, String key){
        encryptOrDecryptFile(file,dest,key, Cipher.DECRYPT_MODE);
    }


    /**
     *
     * @param srcFile
     * @param destFile
     * @param key
     * @param mode
     * @throws Exception
     */
    private static void encryptOrDecryptFile(String srcFile, String destFile, String key, int mode){
        try{

           

            DESKeySpec keySpec = new DESKeySpec(key.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(keySpec);
            //https://stackoverflow.com/questions/12894722/c-sharp-and-java-des-encryption-value-are-not-identical
            //Cipher cipher = Cipher.getInstance("DES");
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
            cipher.init(mode, secretKey, iv);
            InputStream is = new FileInputStream(srcFile);
            OutputStream out = new FileOutputStream(destFile);
            CipherInputStream cis = new CipherInputStream(is, cipher);
            byte[] buffer = new byte[1024];
            int r;
            while ((r = cis.read(buffer)) > 0) {
                out.write(buffer, 0, r);
            }
            cis.close();
            is.close();
            out.close();
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    /**
     * @param key
     * @param mode
     * @param input
     * @return
     * @throws Exception
     */
    private static byte[] encryptOrDecrypt(String key, int mode, byte[] input) throws Exception {
        DESKeySpec keySpec = new DESKeySpec(key.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey desKey = keyFactory.generateSecret(keySpec);
        Cipher cipher = Cipher.getInstance("DES");
        if (mode == Cipher.ENCRYPT_MODE) {
            cipher.init(Cipher.ENCRYPT_MODE, desKey);
            byte[] encodeBytes = cipher.doFinal(input);
            return encodeBytes;

        } else if (mode == Cipher.DECRYPT_MODE) {
            cipher.init(Cipher.DECRYPT_MODE, desKey);
            byte[] decodeBytes = cipher.doFinal(input);
            return decodeBytes;
        }
        return null;
    }

    /**
     * Use password to generate SHA-1 Hash Code
     * @param password
     * @return 20bytes digest
     */
    private static byte[] toSha1(String password) throws Exception {
        //https://stackoverflow.com/questions/4895523/java-string-to-sha1
        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        crypt.reset();
        crypt.update(password.getBytes("UTF-8"));
        byte[] hashBytes = crypt.digest();
        // Convert negative to positive
        // -64 to 192
        for(int i=0; i<hashBytes.length; i++){
            hashBytes[i] = (byte)(0xff & hashBytes[i]);
        }
        return hashBytes;
    }
}
