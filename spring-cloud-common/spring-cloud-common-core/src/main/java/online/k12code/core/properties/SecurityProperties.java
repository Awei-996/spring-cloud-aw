package online.k12code.core.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;

/**
 * @author msi
 */
@Data
@ConfigurationProperties("aw.cloud.security")
public class SecurityProperties {

    /**
     * 私钥
     */
    private String privateKey;

    /**
     * 公钥
     */
    private String publicKey;

    public PublicKey publicKey() {
        return RSAUtils.publicKey(this.publicKey);
    }

    public RSAPublicKey rsaPublicKey() {
        return (RSAPublicKey) publicKey();
    }

    public PrivateKey privateKey() {
        return RSAUtils.privateKey(this.privateKey);
    }
}
