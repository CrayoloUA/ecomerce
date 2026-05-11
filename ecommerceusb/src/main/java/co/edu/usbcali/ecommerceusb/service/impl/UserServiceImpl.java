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
import java.util.Objects;

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
        if (id == null || id <= 0) throw new RuntimeException("Debe ingresar un id válido para buscar");
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Usuario no encontrado con el id: %d", id)));
        return UserMapper.modelToUserResponse(user);
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        if (email == null || email.isBlank()) throw new RuntimeException("Debe ingresar email");
        User userByEmail = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(String.format("Usuario no encontrado con el email: %s", email)));
        return UserMapper.modelToUserResponse(userByEmail);
    }

    @Override
    public UserResponse createUser(CreateUserRequest createUserRequest) throws Exception {
        if (Objects.isNull(createUserRequest)) throw new Exception("El objeto createUserRequest no puede ser nulo.");
        if (Objects.isNull(createUserRequest.getFullName()) || createUserRequest.getFullName().isBlank())
            throw new Exception("El campo FullName no puede ser nulo.");
        if (Objects.isNull(createUserRequest.getPhone()) || createUserRequest.getPhone().isBlank())
            throw new Exception("El campo Phone no puede ser nulo.");
        if (Objects.isNull(createUserRequest.getEmail()) || createUserRequest.getEmail().isBlank())
            throw new Exception("El campo Email no puede ser nulo.");
        if (createUserRequest.getDocumentTypeId() == null || createUserRequest.getDocumentTypeId() <= 0)
            throw new Exception("El campo documentTypeId debe contener un numero mayor a 0.");
        if (Objects.isNull(createUserRequest.getDocumentNumber()) || createUserRequest.getDocumentNumber().isBlank())
            throw new Exception("El campo DocumentNumber no puede ser nulo.");
        if (Objects.isNull(createUserRequest.getBirthDate()) || createUserRequest.getBirthDate().isBlank())
            throw new Exception("El campo BirthDate no puede ser nulo.");
        if (Objects.isNull(createUserRequest.getCountry()) || createUserRequest.getCountry().isBlank())
            throw new Exception("El campo Country no puede ser nulo.");
        if (Objects.isNull(createUserRequest.getAddress()) || createUserRequest.getAddress().isBlank())
            throw new Exception("El campo address no puede ser nulo.");
        DocumentType documentType = documentTypeRepository.findById(createUserRequest.getDocumentTypeId())
                .orElseThrow(() -> new Exception("El tipo de documentType no encontrado"));
        if (userRepository.existsByEmail(createUserRequest.getEmail()))
            throw new Exception("El email ya existe");
        if (userRepository.existsByDocumentNumberAndDocumentTypeId(
                createUserRequest.getDocumentNumber(), createUserRequest.getDocumentTypeId()))
            throw new Exception("El documentType ya existe");
        User user = UserMapper.createUserRequestToUser(createUserRequest, documentType);
        return UserMapper.modelToUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse updateUser(Integer id, UpdateUserRequest request) throws Exception {
        if (id == null || id <= 0) throw new Exception("Debe ingresar un id válido");
        if (Objects.isNull(request)) throw new Exception("El objeto updateUserRequest no puede ser nulo.");
        User user = userRepository.findById(id)
                .orElseThrow(() -> new Exception(String.format("Usuario no encontrado con el id: %d", id)));
        if (!Objects.isNull(request.getFullName()) && !request.getFullName().isBlank())
            user.setFullName(request.getFullName());
        if (!Objects.isNull(request.getPhone()) && !request.getPhone().isBlank())
            user.setPhone(request.getPhone());
        if (!Objects.isNull(request.getEmail()) && !request.getEmail().isBlank())
            user.setEmail(request.getEmail());
        if (!Objects.isNull(request.getDocumentNumber()) && !request.getDocumentNumber().isBlank())
            user.setDocumentNumber(request.getDocumentNumber());
        if (!Objects.isNull(request.getBirthDate()) && !request.getBirthDate().isBlank())
            user.setBirthDate(LocalDate.parse(request.getBirthDate(), DateTimeFormatter.ISO_LOCAL_DATE));
        if (!Objects.isNull(request.getCountry()) && !request.getCountry().isBlank())
            user.setCountry(request.getCountry());
        if (!Objects.isNull(request.getAddress()) && !request.getAddress().isBlank())
            user.setAddress(request.getAddress());
        if (request.getDocumentTypeId() != null && request.getDocumentTypeId() > 0) {
            DocumentType documentType = documentTypeRepository.findById(request.getDocumentTypeId())
                    .orElseThrow(() -> new Exception("DocumentType no encontrado con id: " + request.getDocumentTypeId()));
            user.setDocumentType(documentType);
        }
        user.setUpdatedAt(OffsetDateTime.now());
        return UserMapper.modelToUserResponse(userRepository.save(user));
    }
}
