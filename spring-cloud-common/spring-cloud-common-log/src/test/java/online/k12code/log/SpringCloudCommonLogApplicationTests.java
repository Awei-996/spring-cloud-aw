package online.k12code.log;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletRequest;

@SpringBootTest
class SpringCloudCommonLogApplicationTests {

    @Test
    void contextLoads(HttpServletRequest request) {
        System.out.println(request.getRemoteHost());
    }

}
