package com.eprogramming.learning.dto.response;

import com.eprogramming.learning.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String type;
    private Long userId;
    private String name;
    private String email;
    private Role role;
}
