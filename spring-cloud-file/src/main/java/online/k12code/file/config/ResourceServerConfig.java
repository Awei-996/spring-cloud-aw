package online.k12code.file.config;

import online.k12code.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.security.web.SecurityFilterChain;

import java.security.interfaces.RSAPublicKey;

/**
 * @author msi
 */
@Configuration
public class ResourceServerConfig {

    private SecurityProperties securityProperties;

    @Autowired
    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Bean
    public BearerTokenResolver bearerTokenResolver() {
        DefaultBearerTokenResolver defaultBearerTokenResolver = new DefaultBearerTokenResolver();
        // 允许使用参数的形式传递token，默认一般是在head中传递
        // http://localhost:9002/local/5512.jpg?access_token=eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImF1ZCI6Im1lc3NhZ2luZy1jbGllbnQiLCJuYmYiOjE3MTk1Njg3NzUsInNjb3BlIjpbIm9wZW5pZCIsIm1lc3NhZ2UucmVhZCIsIm1lc3NhZ2Uud3JpdGUiXSwiaXNzIjoiaHR0cDovLzEyNy4wLjAuMTo5MDAwIiwiZXhwIjoxNzE5NTY5MDc1LCJpYXQiOjE3MTk1Njg3NzV9.lYb0QEuPROh2LedP-RwMerhwc4dQJ3xUnyqXa-VMlWTZp_1gADEdImjomRig_ZLvqd4aj0rs97OusUinkjHbUZFuahQOvtTqfIk40E727hlDwZvbI_IgqFVWWi67x5DsdGmEdyVnwIVNxWNoRKUTuY_9ZvBGNURno-29Xbnz24h2wVFIIXA_7zbA50anSesB1AtXZFy30xzomHgq9gmA6cVCyypgBy1hLHOQ6TXCitluY3ibvM-DkihsNP6E2ozvYHV-O39_E-ssViR54dSkZR2-YtHOO-FNI8tPVnY3r4y5i75diGGUiFVqE1KdzDhqN51aLtUbev6XDo8QaXeaFQ
        defaultBearerTokenResolver.setAllowUriQueryParameter(true);
        return defaultBearerTokenResolver;
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // 添加认证那些接口
        httpSecurity.authorizeRequests(authorizeRequests -> {
            authorizeRequests.regexMatchers("^/actuator(/.*)?$").permitAll()
                    .anyRequest()
                    .authenticated();
        });
        // 认证资源服务
        httpSecurity.oauth2ResourceServer().jwt(oauth2ResourcesServe -> {
            RSAPublicKey rsaPublicKey = securityProperties.rsaPublicKey();
            NimbusJwtDecoder build = NimbusJwtDecoder.withPublicKey(rsaPublicKey).build();
            oauth2ResourcesServe.decoder(build);
        });
        return httpSecurity.build();
    }
}
