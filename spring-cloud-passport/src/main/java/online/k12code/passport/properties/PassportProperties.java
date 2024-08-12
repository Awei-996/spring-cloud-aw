package online.k12code.passport.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Data
@Component
@RefreshScope
@ConfigurationProperties("aw.properties.passport")
public class PassportProperties {

    private String title;
}
