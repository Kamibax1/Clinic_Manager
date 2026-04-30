package com.example.repository;

import com.example.model.entity.UserEntity;
import com.example.model.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);

    UserEntity findByEmail(String email);

    List<UserEntity> findAllByRoleName(RoleEnum roleName);

    List<UserEntity> findAllByOrderByUsernameAsc();
    List<UserEntity> findAllByOrderByUsernameDesc();

    List<UserEntity> findAllByOrderByEmailAsc();
    List<UserEntity> findAllByOrderByEmailDesc();

    List<UserEntity> findAllByUsernameContaining(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}
