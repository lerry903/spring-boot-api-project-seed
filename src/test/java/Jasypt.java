import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class Jasypt {

    // 密钥
    private static final String KEY = "lerry";

    public static void main(String[] args) {
        String ciphertext1 = encrypt("P@ssw0rd");
        System.out.println(ciphertext1);

        String text1 = decrypt(ciphertext1);
        System.out.println(text1);
    }

    /**
     * 加密
     * @param text 明文
     * @return     密文
     */
    public static String encrypt(String text) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(KEY);
        return encryptor.encrypt(text);
    }

    /**
     * 解密
     * @param ciphertext 密文
     * @return           明文
     */
    public static String decrypt(String ciphertext) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(KEY);
        return encryptor.decrypt(ciphertext);
    }
}
