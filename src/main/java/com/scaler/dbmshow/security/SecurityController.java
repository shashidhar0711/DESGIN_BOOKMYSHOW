package com.scaler.dbmshow.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class SecurityController {
    @Autowired
    private JwtService jwtService;

    @GetMapping
    public JwtUserDto validateToken(
            @RequestHeader("AUTH_TOKEN") String token) {

        return jwtService.validate(token);
    }
}
