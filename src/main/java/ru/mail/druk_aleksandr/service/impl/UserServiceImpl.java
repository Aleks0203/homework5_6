package ru.mail.druk_aleksandr.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import ru.mail.druk_aleksandr.repository.ConnectionRepository;
import ru.mail.druk_aleksandr.repository.UserGroupRepository;
import ru.mail.druk_aleksandr.repository.UserRepository;
import ru.mail.druk_aleksandr.repository.impl.ConnectionRepositoryImpl;
import ru.mail.druk_aleksandr.repository.impl.UserGroupRepositoryImpl;
import ru.mail.druk_aleksandr.repository.impl.UserRepositoryImpl;
import ru.mail.druk_aleksandr.repository.model.User;
import ru.mail.druk_aleksandr.repository.model.UserGroup;
import ru.mail.druk_aleksandr.service.UserService;
import ru.mail.druk_aleksandr.service.model.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.mail.druk_aleksandr.service.model.UserGroupDTO;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private ConnectionRepository connectionRepository = ConnectionRepositoryImpl.getInstance();
    private UserGroupRepository userGroupRepository = UserGroupRepositoryImpl.getInstance();
    private UserRepository userRepository = UserRepositoryImpl.getInstance();
    private static final int AGE_VALUE = 27;
    private static final int Min_AGE_VALUE = 30;
    private static final int MAX_AGE_VALUE = 33;
    private static UserService instance;

    private UserServiceImpl() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public UserGroupDTO add(UserGroupDTO userGroupDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                UserGroup userGroup = convertDTOToUserGroup(userGroupDTO);
                userGroup = userGroupRepository.add(connection, userGroup);
                UserGroupDTO userToDTO = convertUserGroupToDTO(userGroup);
                connection.commit();
                return userToDTO;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public UserDTO add(UserDTO userDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User user = convertDTOToUser(userDTO);
                user = userRepository.add(connection, user);
                UserDTO userToDTO = convertUserToDTO(user);
                connection.commit();
                return userToDTO;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<UserDTO> findAll() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<User> users = userRepository.findAll(connection);
                List<UserDTO> userToDTO = convertListUserToDTO(users);
                connection.commit();
                return userToDTO;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void groupNameWithNumberOfUsers() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                HashMap<String, Integer> hashMap = userRepository.groupNameWithNumberOfUsers(connection);
                connection.commit();
                logger.info("Result: " + hashMap);
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void deleteUsers() {
        try (Connection connection = connectionRepository.getConnection()) {
            try {
                List<User> users = userRepository.findAll(connection);
                int count = 0;
                for (User user : users) {
                    if (user.getAge() < AGE_VALUE) {
                        count++;
                        userRepository.delete(connection, user);
                    }
                }
                logger.info("Result: " + count);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<UserDTO> updateActive(UserDTO userDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            try {
                List<User> users = userRepository.findAll(connection);
                for (User user : users) {
                    if (user.getActive()) {
                        if (user.getAge() < MAX_AGE_VALUE && user.getAge() > Min_AGE_VALUE) {
                            user.setActive(false);
                        }
                    }
                    userRepository.update(connection, user);
                }
                List<UserDTO> userToDTO = convertListUserToDTO(users);
                return userToDTO;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<UserDTO> convertListUserToDTO(List<User> users) {
        List<UserDTO> result = users.stream().map(temp -> {
            UserDTO obj = new UserDTO();
            obj.setName(temp.getName());
            obj.setPassword(temp.getPassword());
            obj.setActive(temp.getActive());
            obj.setUserGroupId(temp.getUserGroupId());
            obj.setAge(temp.getAge());
            return obj;
        }).collect(Collectors.toList());
        return result;
    }

    private UserDTO convertUserToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setPassword(user.getPassword());
        userDTO.setActive(user.getActive());
        userDTO.setUserGroupId(user.getUserGroupId());
        userDTO.setAge(user.getAge());
        return userDTO;
    }

    private User convertDTOToUser(UserDTO userDTO) {
        return User.newBuilder()
                .name(userDTO.getName())
                .password(userDTO.getPassword())
                .isActive(userDTO.getActive())
                .userGroupId(userDTO.getUserGroupId())
                .age(userDTO.getAge())
                .build();
    }

    private UserGroupDTO convertUserGroupToDTO(UserGroup userGroup) {
        UserGroupDTO userGroupDTO = new UserGroupDTO();
        userGroupDTO.setName(userGroup.getName());
        return userGroupDTO;
    }

    private UserGroup convertDTOToUserGroup(UserGroupDTO userGroupDTO) {
        return UserGroup.newBuilder()
                .name(userGroupDTO.getName())
                .build();
    }
}
