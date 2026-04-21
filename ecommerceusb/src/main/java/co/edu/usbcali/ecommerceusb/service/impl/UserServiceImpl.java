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
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            return List.of();
        }

        return UserMapper.modelToUserResponse(users);
    }

    @Override
    public UserResponse getUserById(Integer id) {
        if (id == null || id <= 0) {
            throw new RuntimeException("Debe ingresar un id válido para buscar");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        String.format("Usuario no encontrado con el id: %d", id)));

        return UserMapper.modelToUserResponse(user);
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new RuntimeException("Debe ingresar email");
        }

        User userByEmail = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(
                        String.format("Usuario no encontrado con el email: %s", email)));

        return UserMapper.modelToUserResponse(userByEmail);
    }
}