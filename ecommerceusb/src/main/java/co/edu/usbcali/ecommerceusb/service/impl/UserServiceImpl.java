package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.CreateUserRequest;
import co.edu.usbcali.ecommerceusb.dto.UpdateUserRequest;
import co.edu.usbcali.ecommerceusb.dto.UserResponse;
import co.edu.usbcali.ecommerceusb.mapper.UserMapper;
import co.edu.usbcali.ecommerceusb.model.DocumentType;
import co.edu.usbcali.ecommerceusb.model.User;
import co.edu.usbcali.ecommerceusb.repository.DocumentTypeRepository;
import co.edu.usbcali.ecommerceusb.repository.UserRepository;
import co.edu.usbcali.ecommerceusb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @Override
    public List<UserResponse> getUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) return List.of();
        return UserMapper.modelToUserResponse(users);
    }

    @Override
    public UserResponse getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con el id: " + id));
        return UserMapper.modelToUserResponse(user);
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con el email: " + email));
        return UserMapper.modelToUserResponse(user);
    }

    @Override
    public UserResponse createUser(CreateUserRequest request) throws Exception {
        DocumentType documentType = documentTypeRepository.findById(request.getDocumentTypeId())
                .orElseThrow(() -> new Exception("DocumentType no encontrado"));
        User user = UserMapper.createUserRequestToUser(request, documentType);
        return UserMapper.modelToUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse updateUser(Integer id, UpdateUserRequest request) throws Exception {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new Exception("Usuario no encontrado con el id: " + id));
        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setDocumentNumber(request.getDocumentNumber());
        user.setCountry(request.getCountry());
        user.setAddress(request.getAddress());
        if (request.getBirthDate() != null)
            user.setBirthDate(LocalDate.parse(request.getBirthDate(), DateTimeFormatter.ISO_LOCAL_DATE));
        if (request.getDocumentTypeId() != null) {
            DocumentType documentType = documentTypeRepository.findById(request.getDocumentTypeId())
                    .orElseThrow(() -> new Exception("DocumentType no encontrado"));
            user.setDocumentType(documentType);
        }
        user.setUpdatedAt(OffsetDateTime.now());
        return UserMapper.modelToUserResponse(userRepository.save(user));
    }
}
