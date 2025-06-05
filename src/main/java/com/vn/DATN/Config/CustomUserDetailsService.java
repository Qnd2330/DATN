package com.vn.DATN.Config;

import com.vn.DATN.Service.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepository;

    @Override
    public UserDetails loadUserByUsername(String userIdStr) throws UsernameNotFoundException {
        try {
            Integer userId = Integer.parseInt(userIdStr);
            return userRepository.findById(userId)
                    .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy Người dùng: " + userId));
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("Lỗi tìm trong quá trình tìm kiếm người dùng");
        }
    }
}