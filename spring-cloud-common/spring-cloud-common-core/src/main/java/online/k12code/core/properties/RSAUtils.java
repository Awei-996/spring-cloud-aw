package online.k12code.core.properties;

import lombok.SneakyThrows;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
public class RSAUtils {

    private static final String RSA = "RSA";

    public static KeyPair generate() {
        return generate(2048);
    }

    @SneakyThrows
    public static KeyPair generate(int keysize) {
        if (keysize < 2048) {
//            CloudRuntimeException cloudRuntimeException = new CloudRuntimeException("秘钥大小不得小于 2048 位");
//            cloudRuntimeException.setReferences(Arrays.asList("https://en.wikipedia.org/wiki/Key_size",
//                    "https://en.wikipedia.org/wiki/Strong_cryptography",
//                    "https://cheatsheetseries.owasp.org/cheatsheets/Cryptographic_Storage_Cheat_Sheet.html#algorithms",
//                    "https://owasp.org/www-project-web-security-testing-guide/stable/4-Web_Application_Security_Testing/09-Testing_for_Weak_Cryptography/04-Testing_for_Weak_Encryption",
//                    "https://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-131Ar2.pdf",
//                    "https://cwe.mitre.org/data/definitions/326.html"));
//            throw cloudRuntimeException;
        }
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
        keyPairGenerator.initialize(keysize);
        return keyPairGenerator.generateKeyPair();
    }

    public static KeyPair create(String publicKey, String privateKey) {
        return new KeyPair(publicKey(publicKey), privateKey(privateKey));
    }

    public static String publicKey(PublicKey publicKey) {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    @SneakyThrows
    public static PublicKey publicKey(String publicKey) {
        String cleanedPublicKey = publicKey
                .replaceAll("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
        byte[] publicKeyBytes = Base64.getDecoder().decode(cleanedPublicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePublic(keySpec);
    }

    public static String privateKey(PrivateKey privateKey) {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    @SneakyThrows
    public static PrivateKey privateKey(String privateKey) {
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePrivate(keySpec);
    }

}
