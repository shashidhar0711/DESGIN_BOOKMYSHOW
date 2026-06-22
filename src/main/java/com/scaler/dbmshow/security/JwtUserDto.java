package com.scaler.dbmshow.security;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class JwtUserDto {
    private Long userId;
    private String email;
    private Set<String> role;
}
