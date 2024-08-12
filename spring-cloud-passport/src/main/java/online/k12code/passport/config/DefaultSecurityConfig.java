/*
 * Copyright 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package online.k12code.passport.config;

import online.k12code.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.security.interfaces.RSAPublicKey;

import static org.springframework.security.config.Customizer.withDefaults;


/**
 * @author msi
 */
@EnableWebSecurity
public class DefaultSecurityConfig {

	private SecurityProperties securityProperties;

	@Autowired
	public void setSecurityProperties(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}

	// @formatter:off
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeRequests(authorizeRequests ->
				authorizeRequests
						.regexMatchers("^/actuator(/.*)?$").permitAll() // 使用正则添加白名单,actuator使其admin能获取服务的状态
						.antMatchers("/t1").permitAll() // 添加过滤的路径
						.anyRequest().authenticated()
			)
			// 开启默认登录界面
			.formLogin(withDefaults());

		// 因为使用自己的配置rsa来生成token所以，这里解码不能直接使用默认方式 OAuth2ResourceServerConfigurer::jwt
		// 默认方式他们本质是读取的OAuth2ResourceServerConfigurer里面 注入的
		// 	@Bean
		// 	public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
		// 	    return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
		// 	}
		// 配置JWT解码器,使用自定义的RSA公钥进行验证
		http.oauth2ResourceServer().jwt(oauth2ResourcesServe -> {
			RSAPublicKey rsaPublicKey = securityProperties.rsaPublicKey();
			NimbusJwtDecoder build = NimbusJwtDecoder.withPublicKey(rsaPublicKey).build();
			oauth2ResourcesServe.decoder(build);
		});
		return http.build();
	}
	// @formatter:on

	// @formatter:off
	@Bean
	UserDetailsService users() {
		// 默认的登录信息
		UserDetails user = User.withDefaultPasswordEncoder()
				.username("user1")
				.password("password")
				.roles("USER")
				.build();
		return new InMemoryUserDetailsManager(user);
	}
	// @formatter:on

}