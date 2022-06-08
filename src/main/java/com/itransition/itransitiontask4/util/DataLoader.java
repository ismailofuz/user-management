package com.itransition.itransitiontask4.util;


import com.itransition.itransitiontask4.user.User;
import com.itransition.itransitiontask4.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.sql.init.mode}")
    String initMode;


    @Override
    public void run(String... args) throws Exception {
        if (initMode.equals("always")) {
            User Sirojiddin = new User(
                    "Sirojiddin",
                    "sirojiddin@gmail.com",
                    passwordEncoder.encode("111"),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    false
            );
            User Ismoil = new User(
                    "Ismoilov",
                    "ismoilov@gmail.com",
                    passwordEncoder.encode("111"),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    false
            );

            userRepository.save(Sirojiddin);
            userRepository.save(Ismoil);
        }
    }
}
