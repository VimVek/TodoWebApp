package org.example.controller;

import jakarta.servlet.http.HttpSession;
import org.example.entity.UserDtls;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepo;
    @GetMapping("/")
    public String home(){
        return "home";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }
    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute UserDtls user, Model m, HttpSession session){

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ADMIN");

        UserDtls u =  userRepo.save(user);
        if (u!=null) {
            session.setAttribute("msg", "Registered Successfully");
        }
        else{
            session.setAttribute("msg", "Error, Please fill correctly or retry after some time");
        }
        return  "redirect:/signup";
    }
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginPage("/login.html")
                .failureUrl("/login-error.html")
                .and()
                .logout()
                .logoutSuccessUrl("/index.html");
    }
    // Login form with error
    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login.html";
    }
}
