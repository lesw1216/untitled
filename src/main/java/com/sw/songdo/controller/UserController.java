package com.sw.songdo.controller;

import com.sw.songdo.dto.UserResDTO;
import com.sw.songdo.entity.UserEntity;
import com.sw.songdo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/*
    로그인
    회원 가입
    아이디 찾기
    비밀번호 찾기
*/
@Slf4j
@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/sign-up")
    public String signUpForm(Model model) {
        model.addAttribute("user", new UserResDTO());
        return "/users/SignUpForm";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "/users/LoginForm";
    }

    @PostMapping("/users")
    public String signUp(@ModelAttribute("user") UserResDTO userResDTO) {
        log.info("회원 가입 컨트롤러");
        log.info("name = {}", userResDTO.getName());
        UserEntity saveUser = userService.save(userResDTO);
        log.info(saveUser.toString());
        return "/users/LoginForm";
    }
}
