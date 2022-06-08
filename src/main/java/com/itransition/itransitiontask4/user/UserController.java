package com.itransition.itransitiontask4.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

import static com.itransition.itransitiontask4.util.Constants.SUCCESS_MESSAGE;

/**
 * Abdulqodir Ganiev 6/2/2022 8:35 AM
 */

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("user", new UserRegisterRequest());
        return "signup";
    }

    @PostMapping("/register")
    public String registerUser(UserRegisterRequest request, Model model) {
        String msg = userService.registerUser(request);
        model.addAttribute("message", msg);
        model.addAttribute("user", new UserRegisterRequest());
        return msg.equals(SUCCESS_MESSAGE) ? "login" : "signup";
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        List<User> userList = userService.getAllUsers();
        List<User> collect = userList.stream().sorted((o1, o2) -> o1.getId().compareTo(o2.getId())).collect(Collectors.toList());
        model.addAttribute("userList", collect);
        return "dashboard";
    }
}
