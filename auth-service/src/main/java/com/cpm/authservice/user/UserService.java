package com.cpm.authservice.user;

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
    UserProvider provider;

    public Long getCurrentUserId() {
        return provider.getCurrentUser().id();
    }
}
