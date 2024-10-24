package com.uptask.mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.uptask.dto.UserRequest;
import com.uptask.dto.UserResponse;
import com.uptask.model.User;
import com.uptask.model.UserProfile;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configureModelMapper();
    }

    private void configureModelMapper() {

        // Configuración adicional para mapear propiedades a UserProfile
        modelMapper.typeMap(UserRequest.class, UserProfile.class).addMappings(mapper -> {
            mapper.map(UserRequest::getFirstName, UserProfile::setFirstName);
            mapper.map(UserRequest::getLastName, UserProfile::setLastName);
            mapper.map(UserRequest::getDni, UserProfile::setDni);
        });

        // Configuración de ModelMapper para User a UserResponse
        modelMapper.typeMap(User.class, UserResponse.class).addMappings(mapper -> {
            mapper.map(User::getId, UserResponse::setId);
            mapper.map(User::getEmail, UserResponse::setEmail);
            mapper.map(src -> src.getUserProfile().getFirstName(), UserResponse::setFirstName);
            mapper.map(src -> src.getUserProfile().getLastName(), UserResponse::setLastName);
            mapper.map(src -> src.getUserProfile().getDni(), UserResponse::setDni);
        });
    }

    public User userRequestToUser(UserRequest request) {
        User user = modelMapper.map(request, User.class);
        UserProfile userProfile = modelMapper.map(request, UserProfile.class);
        userProfile.setUser(user);
        user.setUserProfile(userProfile);
        return user;
    }

    public UserResponse userToUserResponse(User user) {
        return modelMapper.map(user, UserResponse.class);
    }

    public List<User> userRequestListToUserList(List<UserRequest> requests) {
        return requests.stream()
                .map(this::userRequestToUser)
                .collect(Collectors.toList());
    }

    public List<UserResponse> userListToUserResponseList(List<User> users) {
        return users.stream()
                .map(this::userToUserResponse)
                .collect(Collectors.toList());
    }
}
