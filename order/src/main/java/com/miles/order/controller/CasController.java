package com.miles.order.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @Description TODO
 * @Date 2020/4/18 17:40
 */
@Controller
public class CasController {

    @Value("${casClientLogoutUrl}")
    private String clientLogoutUrl;
    @Value("${casClientLoginUrl}")
    private String clientLoginUrl;

    @RequestMapping("index")
    public String index(ModelMap map, HttpServletRequest request) {
        Principal userPrincipal = request.getUserPrincipal();
        map.addAttribute("name", "clien B");
        return "index";
    }

    @RequestMapping("hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping("logout")
    public String logout() {
        return "redirect:" + clientLogoutUrl;
    }
    @RequestMapping("login")
    public String login() {
        return "redirect:" + clientLoginUrl;
    }

}