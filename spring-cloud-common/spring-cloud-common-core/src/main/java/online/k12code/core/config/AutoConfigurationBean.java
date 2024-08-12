package online.k12code.core.config;

import online.k12code.core.handlers.GlobalExceptionHandler;
import online.k12code.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 处理统一需要注入的bean
 *
 * @author msi
 */
@AutoConfiguration
public class AutoConfigurationBean {
    /**
     * 应用名
     */
    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler(applicationName);
    }

    @Bean
    public SecurityProperties securityProperties () {
        return new SecurityProperties();
    }
}
