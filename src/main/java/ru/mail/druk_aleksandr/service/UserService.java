package ru.mail.druk_aleksandr.service;

import java.util.List;

import ru.mail.druk_aleksandr.service.model.UserDTO;
import ru.mail.druk_aleksandr.service.model.UserGroupDTO;

public interface UserService {
    UserGroupDTO add(UserGroupDTO userGroupDTO);

    UserDTO add(UserDTO userDTO);

    List<UserDTO> findAll();

    void groupNameWithNumberOfUsers();

    void deleteUsers();

    List<UserDTO> updateActive(UserDTO userDTO);
}
