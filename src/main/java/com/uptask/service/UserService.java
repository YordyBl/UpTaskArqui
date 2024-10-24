package com.uptask.service;

import com.uptask.dto.UserRequest;
import com.uptask.dto.UserResponse;
import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest request);
    UserResponse updateUser(Long id, UserRequest request);
    void deleteUser(Long id);
    UserResponse getUserById(Long id);
    List<UserResponse> getAllUsers();
}
