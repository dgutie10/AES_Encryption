import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class AES {
    private byte[] key;
    private SecretKeySpec secretKeySpec;
    private Cipher cipher;
    private final int IV_LENGTH = 16;
    private final int TAG_LENGTH = 128;

    public SecretKeySpec getKey(){ return this.secretKeySpec; }

    private void setKey(String secretKey){
        MessageDigest sha;
        try{
            sha = MessageDigest.getInstance("SHA-1");
            key = secretKey.getBytes(StandardCharsets.UTF_8);
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKeySpec = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public Cipher initializeCipher(int cipherMode,  String operationMode, String secretKey) throws Exception{
        Cipher cipher = Cipher.getInstance("AES/" + operationMode + "/PKCS5Padding");
        byte[] iv = new byte[IV_LENGTH];

        switch (operationMode.toUpperCase()) {
            case "GCM":
                cipher.init(cipherMode, secretKeySpec, new GCMParameterSpec(TAG_LENGTH, iv));
                break;
            case "ECB":
                cipher.init(cipherMode, secretKeySpec);
                break;
            default:
                IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
                cipher.init(cipherMode, secretKeySpec, ivParameterSpec);
                break;

        }
        return cipher;
    }

    public String encrypt (String operationMode, String userString, String secretKey){
        setKey(secretKey);
        try {
            Cipher cipher = initializeCipher(Cipher.ENCRYPT_MODE, operationMode, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(userString.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e){
            System.out.println("Error found while encrypting string: " + e.toString());
        }
        return null;
    }

    public String decrypt(String operationMode, String userString, String secretKey){
        setKey(secretKey);
        try{
            Cipher cipher = initializeCipher(Cipher.DECRYPT_MODE, operationMode, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(userString)));
        } catch (Exception e){
            System.out.println("Error found while decrypting string: " + e.toString());
        }
        return  null;
    }

    public String hex(byte[] bytes ){
        StringBuilder stringBuilder = new StringBuilder();
        for(byte b : bytes){
            stringBuilder.append(String.format("%02x", b));
        }
        return stringBuilder.toString();
    }

    public String hexWithBlockSize(byte[] bytes, int size){
        String hex = hex(bytes);
        size = size * 2;

        List<String> stringList = new ArrayList<>();
        for (int index =  0; index < hex.length(); index = index + size){
            stringList.add(hex.substring(index, Math.min(index + size, hex.length())));
        }

        return stringList.toString();
    }
}
