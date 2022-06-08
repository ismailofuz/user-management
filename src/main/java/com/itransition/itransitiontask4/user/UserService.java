package com.itransition.itransitiontask4.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static com.itransition.itransitiontask4.util.Constants.*;

/**
 * Abdulqodir Ganiev 6/3/2022 7:41 PM
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService, ApplicationListener<AuthenticationSuccessEvent> {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            log.error("User not found");
            throw new UsernameNotFoundException("User not found");
        } else {
            log.info("User found {} user ", email);
        }
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void blockSelectedUsers(List<Long> usersId, User currentUser, HttpServletRequest request) {
        for (Long userId : usersId) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UsernameNotFoundException(USERNAME_NOT_FOUND));

            if (!user.getIsBlocked()) {
                if (user.getEmail().equals(currentUser.getEmail())) {
                    HttpSession session = request.getSession(false);
                    SecurityContextHolder.clearContext();
                    if (session != null) session.invalidate();
                }

                user.setIsBlocked(true);
                userRepository.save(user);
            }

        }
    }

    @Transactional
    public void deleteSelectedUsers(List<Long> usersId, User currentUser, HttpServletRequest request) {
        for (Long userId : usersId) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UsernameNotFoundException(USERNAME_NOT_FOUND));

            if (user.getEmail().equals(currentUser.getEmail())) {
                HttpSession session = request.getSession(false);
                SecurityContextHolder.clearContext();
                if (session != null) session.invalidate();
            }
            userRepository.delete(user);
        }
    }

    public void unblockSelectedUsers(List<Long> usersId) {
        for (Long userId : usersId) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            if (user.getIsBlocked()) {
                user.setIsBlocked(false);
                userRepository.save(user);
            }
        }
    }

    public String registerUser(UserRegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword()))
            return PASSWORD_NOT_MATCH;

        ;

        if (userRepository.findByEmail(request.getEmail()) != null)
            return EMAIL_EXISTS;

        User newUser = new User(
                request.getName(),
                request.getEmail(),
                getEncoder().encode(request.getPassword()),
                null,
                LocalDateTime.now(),
                false
        );

        userRepository.save(newUser);

        return SUCCESS_MESSAGE;
    }

    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        String userName = ((UserDetails) event.getAuthentication().
                getPrincipal()).getUsername();
        User currentUser = userRepository.findByEmail(userName);
        currentUser.setLastLoginTime(LocalDateTime.now());
        userRepository.save(currentUser);
    }
}
