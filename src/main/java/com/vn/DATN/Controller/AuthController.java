package com.vn.DATN.Controller;

import com.vn.DATN.Config.JwtTokenProvider;
import com.vn.DATN.DTO.request.LoginRequest;
import com.vn.DATN.DTO.request.RegisterRequest;
import com.vn.DATN.DTO.response.UserRespones;
import com.vn.DATN.Service.UsersService;
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
    private final UsersService usersService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(), loginRequest.getPassword()
                    )
            );
            Users user = (Users) authentication.getPrincipal();

            String token = jwtTokenProvider.generateToken(user);
            return ResponseEntity.ok(UserRespones.fromUser(user, token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        // Kiểm tra xem user đã tồn tại chưa
//        if (usersService.findByUserName(registerRequest.getUsername()) != null) {
//            return ResponseEntity.badRequest().body("Username already exists");
//        }
        usersService.save(registerRequest);
        return ResponseEntity.ok("Register successfully");
    }
}
