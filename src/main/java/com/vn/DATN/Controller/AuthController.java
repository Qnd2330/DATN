package com.vn.DATN.Controller;

import com.vn.DATN.Config.JwtTokenProvider;
import com.vn.DATN.DTO.request.LoginRequest;
import com.vn.DATN.DTO.response.UserResponse;
import com.vn.DATN.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUserId(), loginRequest.getPassword()
                    )
            );
            Users user = (Users) authentication.getPrincipal();

            String token = jwtTokenProvider.generateToken(user);
            return ResponseEntity.ok(UserResponse.fromUserWithToken(user, token));
    }
}
