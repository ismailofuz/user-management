package com.itransition.itransitiontask4.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Abdulqodir Ganiev 6/2/2022 8:23 AM
 */

@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    private LocalDateTime lastLoginTime;

        private LocalDateTime registrationTime;

    private Boolean isBlocked;

    public User(String name, String email,
                String password, LocalDateTime lastLoginTime,
                LocalDateTime registrationTime, Boolean isBlocked) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.lastLoginTime = lastLoginTime;
        this.registrationTime = registrationTime;
        this.isBlocked = isBlocked;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isBlocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
