package co.edu.usbcali.ecommerceusb.mapper;

import co.edu.usbcali.ecommerceusb.dto.UserResponse;
import co.edu.usbcali.ecommerceusb.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    // User (Entity/Model) -> UserResponse (DTO)
    public static UserResponse modelToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .documentTypeId(user.getDocumentType() != null ? user.getDocumentType().getId() : null)
                .documentTypeName(user.getDocumentType() != null ? user.getDocumentType().getName() : null)
                .documentNumber(user.getDocumentNumber())
                .build();
    }

    public static List<UserResponse> modelToUserResponse(List<User> users) {
        return users.stream()
                .map(UserMapper::modelToUserResponse)
                .collect(Collectors.toList());
    }
}