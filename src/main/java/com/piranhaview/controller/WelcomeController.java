package com.piranhaview.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@EnableAutoConfiguration
public class WelcomeController {
    @RequestMapping("/")
    public String home() {
    	return "redirect:/swagger-ui.html";
    }
}
