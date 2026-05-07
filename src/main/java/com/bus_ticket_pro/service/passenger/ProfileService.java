package com.bus_ticket_pro.service.passenger;

import com.bus_ticket_pro.dto.profile.ProfileDTO;
import com.bus_ticket_pro.entity.User;
import com.bus_ticket_pro.entity.UserProfile;
import com.bus_ticket_pro.repository.UserProfileRepository;
import com.bus_ticket_pro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final UserProfileRepository profileRepository;

    public UserProfile getProfile(String username){

        User user = userRepository
                .findByUsername(username)
                .orElseThrow();

        return profileRepository
                .findByUser(user)
                .orElseGet(() -> {

                    UserProfile profile =
                            new UserProfile();

                    profile.setUser(user);

                    return profileRepository.save(profile);
                });
    }

    public void updateProfile(
            String username,
            ProfileDTO dto
    ){

        User user = userRepository
                .findByUsername(username)
                .orElseThrow();

        UserProfile profile = profileRepository
                .findByUser(user)
                .orElse(new UserProfile());

        profile.setUser(user);

        profile.setFullName(dto.getFullName());

        profile.setPhone(dto.getPhone());

        profile.setEmail(dto.getEmail());

        profile.setAddress(dto.getAddress());

        profileRepository.save(profile);
    }
}