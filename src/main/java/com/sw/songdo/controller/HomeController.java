package com.sw.songdo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        Object principal = SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();
        String password = userDetails.getPassword();
        log.info("Authentication user[{}][{}]", username, password);
        return "/index";
    }
}
