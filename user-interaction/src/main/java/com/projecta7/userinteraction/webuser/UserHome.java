package com.projecta7.userinteraction.webuser;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class UserHome {

    @GetMapping("/")
    public String home() {
        return "home";
    }

}
