package online.k12code.passport.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

/**
 * 凭证授权 测试用例
 */

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClientCredentialsTests {

    @LocalServerPort
    private int serverPort;

    @Test
   void start(){
       String clientId = "messaging-client";
       String clientSecret = "secret";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.setBasicAuth(clientId,clientSecret);
        LinkedMultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.put("grant_type", Collections.singletonList("client_credentials"));
        requestBody.put("scope", Collections.singletonList("openid profile message.read message.write"));
        HttpEntity<MultiValueMap<String,String>> httpEntity  = new HttpEntity<>(requestBody,httpHeaders);
        Map map = restTemplate.postForObject(String.format("http://127.0.0.1:%d/oauth2/token", serverPort), httpEntity,
                Map.class);
        log.info(String.valueOf(map));

    }
}
