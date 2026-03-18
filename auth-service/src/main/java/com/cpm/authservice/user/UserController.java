package com.cpm.authservice.user;

import com.cpm.authservice.user.dto.UserResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserController {
    UserService userService;

    @PostMapping("/current")
    public Long internalGetCurrentUserId() {
        return userService.getCurrentUserId();
    }

    @GetMapping("/current")
    public UserResponse getCurrentUser() {
        return userService.getCurrentUser();
    }

}
