package it.lt.controller.dev;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Atuhor LT
 * @Date Create in  2019/11/26
 */
@Controller
public class Login {
    @GetMapping("/")
    public String login(){
        return "index";
    }
}
