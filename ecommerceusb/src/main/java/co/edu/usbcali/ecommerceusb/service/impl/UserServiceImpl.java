    package co.edu.usbcali.ecommerceusb.service.impl;

    import co.edu.usbcali.ecommerceusb.dto.CreateUserRequest;
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

        @Override
        public UserResponse createUser(CreateUserRequest createUserRequest) throws Exception {
            if (Objects.isNull(createUserRequest)) {
                throw new Exception("El objeto createUserRequest no puede ser nulo.");
            }
            if (Objects.isNull(createUserRequest.getFullName()) ||
                    createUserRequest.getFullName().isBlank()) {
                throw new Exception("El campo FullName no puede ser nulo.");
            }
            if (Objects.isNull(createUserRequest.getPhone()) ||
                    createUserRequest.getPhone().isBlank()) {
                throw new Exception("El campo Phone no puede ser nulo.");
            }
            if (Objects.isNull(createUserRequest.getEmail()) ||
                    createUserRequest.getEmail().isBlank()) {
                throw new Exception("El campo Email no puede ser nulo.");
            }
            if (createUserRequest.getDocumentTypeId() == null ||
                    createUserRequest.getDocumentTypeId() <= 0) {
                throw new Exception("El campo documentTypeId debe contener un numero mayor a 0.");
            }
            if (Objects.isNull(createUserRequest.getDocumentNumber()) ||
                    createUserRequest.getDocumentNumber().isBlank()) {
                throw new Exception("El campo DocumentNumber no puede ser nulo.");
            }
            if (Objects.isNull(createUserRequest.getBirthDate()) ||
                    createUserRequest.getBirthDate().isBlank()) {
                throw new Exception("El campo BirthDate no puede ser nulo.");
            }
            if (Objects.isNull(createUserRequest.getCountry()) ||
                    createUserRequest.getCountry().isBlank()) {
                throw new Exception("El campo Country no puede ser nulo.");
            }
            if (Objects.isNull(createUserRequest.getAddress()) ||
                    createUserRequest.getAddress().isBlank()) {
                throw new Exception("El campo address no puede ser nulo.");
            }

            DocumentType documentType = documentTypeRepository.findById(createUserRequest.getDocumentTypeId())
                    .orElseThrow(() -> new Exception("El tipo de documentType no encontrado"));

            if (userRepository.existsByEmail(createUserRequest.getEmail())) {
                throw new Exception("El email ya existe");
            }
            if (userRepository.existsByDocumentNumberAndDocumentTypeId(
                    createUserRequest.getDocumentNumber(), createUserRequest.getDocumentTypeId())){
                throw new Exception("El documentType ya existe");
            }
          User user = UserMapper.createUserRequestToUser(createUserRequest, documentType);

            User savedUser = userRepository.save(user);
            UserResponse userResponse = UserMapper.modelToUserResponse(savedUser);
            return userResponse;
        }
    }