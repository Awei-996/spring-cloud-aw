package online.k12code.passport.controller;

import online.k12code.passport.properties.PassportProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class IndexRestController {

    @Autowired
    private PassportProperties properties;

    @RequestMapping("t1")
    public Map<String,Object> index(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("k",properties.getTitle());
        return stringObjectHashMap;
    }
}
