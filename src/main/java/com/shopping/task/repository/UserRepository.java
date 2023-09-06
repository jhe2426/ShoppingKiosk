package com.shopping.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping.task.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    public boolean existsById(String id);
    public UserEntity findById(String id);
}
