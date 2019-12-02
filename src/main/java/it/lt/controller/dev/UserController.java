package it.lt.controller.dev;

import it.lt.entity.DevUser;
import it.lt.service.dev.IUserService;
import it.lt.service.dev.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Atuhor LT
 * @Date Create in  2019/11/26
 */
@Controller
@RequestMapping("/user")
public class UserController {


    @Autowired
    private IUserService userService;
    @GetMapping("/locallogin")
    public String localLogin(){
        return "main/devlogin";
    }
    @GetMapping("/backlogin")
    public String backLogin(){
        return "back/backLogin";
    }
    @PostMapping("/login")
    public String login(String devCode, String devPassword, HttpServletRequest request){
        DevUser byName = userService.findByName(devCode, devPassword);
        if (byName==null) {
            request.setAttribute("error","用户名或密码不正确");
            return "main/devlogin";
        }
        request.getSession().setAttribute("userSession",byName);
        return "main/main";
    }
}
