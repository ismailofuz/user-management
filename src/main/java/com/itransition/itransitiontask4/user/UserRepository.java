package com.itransition.itransitiontask4.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Abdulqodir Ganiev 6/2/2022 8:35 AM
 */


public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    void deleteAllByIdIn(Collection<Long> id);
}
