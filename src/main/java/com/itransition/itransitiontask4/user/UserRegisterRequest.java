package com.itransition.itransitiontask4.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Abdulqodir Ganiev 6/6/2022 10:24 PM
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRegisterRequest {
    private String name;
    private String email;
    private String password;
    private String confirmPassword;
}
