package com.cpm.authservice.user;

import com.cpm.authservice.system.exceptions.UserNotFoundException;
import com.cpm.authservice.user.dto.UserResponse;
import com.cpm.authservice.user.provider.UserProvider;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    UserProvider provider;

    public Long getCurrentUserId() {
        return provider.getCurrentUser().id();
    }

    public UserResponse getCurrentUser() {
        var currentUser = userRepository.findById(provider.getCurrentUser().id())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return userMapper.toUserResponse(currentUser);
    }
}
