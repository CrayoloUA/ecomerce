package co.edu.usbcali.ecommerceusb.service.impl;
import co.edu.usbcali.ecommerceusb.dto.UserResponse;
import co.edu.usbcali.ecommerceusb.mapper.UserMapper;
import co.edu.usbcali.ecommerceusb.model.User;
import co.edu.usbcali.ecommerceusb.repository.UserRepository;
import co.edu.usbcali.ecommerceusb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<UserResponse> getUsers() {
        List<User> users = userRepository.findAll(); // ← agregado

        if (users.isEmpty()) {
            return List.of();
        }

        List<UserResponse> userResponses = UserMapper.modelToUserResponse(users);
        return userResponses;
    }
    @Override
    public UserResponse getUserById(Integer id) {
        return null;
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        return null;
    }
}