package co.edu.usbcali.ecommerceusb.service;

import co.edu.usbcali.ecommerceusb.dto.CreateUserRequest;
import co.edu.usbcali.ecommerceusb.dto.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getUsers();
    UserResponse getUserById(Integer id);
    UserResponse getUserByEmail(String email);
    UserResponse createUser(CreateUserRequest createUserRequest) throws Exception;
}