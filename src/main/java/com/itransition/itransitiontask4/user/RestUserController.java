package com.itransition.itransitiontask4.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Abdulqodir Ganiev 6/2/2022 8:35 AM
 */

@Slf4j
@RestController
@RequiredArgsConstructor
public class RestUserController {

    private final UserService userService;

    @PostMapping("/block-selected")
    public void blockUser(@RequestBody List<Long> users,
                          @AuthenticationPrincipal User currentUser,
                          HttpServletRequest request) {
        userService.blockSelectedUsers(users, currentUser, request);
    }

    @PostMapping("/unblock-selected")
    public void unblockUser(@RequestBody List<Long> users) {
        userService.unblockSelectedUsers(users);
    }

    @PostMapping("/delete-selected")
    public void deleteUser(@RequestBody List<Long> users,
                           @AuthenticationPrincipal User currentUser,
                           HttpServletRequest request) {
        userService.deleteSelectedUsers(users, currentUser, request);
    }
}
