package com.bus_ticket_pro.service.auth;

import com.bus_ticket_pro.dto.auth.RegisterRequest;
import com.bus_ticket_pro.enums.Role;
import com.bus_ticket_pro.entity.User;
import com.bus_ticket_pro.entity.UserProfile;
import com.bus_ticket_pro.repository.UserProfileRepository;
import com.bus_ticket_pro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final UserProfileRepository profileRepository;

    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequest request){

        if(!request.getPassword()
                .equals(request.getConfirmPassword())){

            throw new RuntimeException(
                    "Mật khẩu không khớp"
                );
        }

        if(userRepository.existsByUsername(
                request.getUsername()
        )){
            throw new RuntimeException(
                    "Username đã tồn tại"
            );
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()
                        )
                )
                .role(Role.PASSENGER)
                .build();

        userRepository.save(user);
    }
}