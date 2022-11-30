package com.analysis.positiverBackend.api;

import com.analysis.positiverBackend.model.User;
import com.analysis.positiverBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class UserAPI {
    private final UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserAPI(UserService userService,
                   PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    //related to User Service
    @GetMapping("/info")
    public String getUser(Principal principal){
        return principal.getName();
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request) throws ServletException {
        request.logout();
    }

    @PostMapping("/login")
    public void login(@RequestBody Map<String, String> body) throws AuthenticationException, NoSuchElementException {
        String email = body.get("email");
        String pass = body.get("password");
        Optional<User> tmp = userService.getUser(email);
        User usr = tmp.get();
        if(tmp.isEmpty()){
            throw new BadCredentialsException("Incorrect Email/Password!");
        }
        if(passwordEncoder.matches(pass,usr.getPassword())){
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(usr.getUsername(),
                    usr.getPassword(), usr.getAuthorities()));
        }else {
            throw new BadCredentialsException("Incorrect Email/Password!");
        }
    }

    @PostMapping("/register")
    public void addUser(@RequestBody Map<String, String> body) throws IllegalArgumentException{
        // String category = body.get("role").toUpperCase();
        User user = new User(body.get("email"),
                passwordEncoder.encode(body.get("password")));
        userService.addUser(user);
    }

    @DeleteMapping("/deleteUser")
    public void deleteUser(Principal principal){
        userService.removeUser(principal.getName());
    }

}

