package com.example.had_backend.repository;


import com.example.had_backend.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findByName(String username);

    @Query("SELECT u FROM UserInfo u WHERE u.name LIKE %?1% AND u.roles != ?2")
    List<UserInfo> findByUsernameContainingAndRoleNot(String query, String role);

    @Query("SELECT u FROM UserInfo u WHERE u.roles != ?1")
    List<UserInfo> findByRoleNot(String role);

    @Query("SELECT u FROM UserInfo u WHERE u.roles = ?1")
    List<UserInfo> findByRole(String role);
}