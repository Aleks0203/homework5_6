package ru.mail.druk_aleksandr.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.mail.druk_aleksandr.repository.UserGroupRepository;
import ru.mail.druk_aleksandr.repository.model.UserGroup;
import ru.mail.druk_aleksandr.util.RandomUtil;

public class UserGroupRepositoryImpl extends GeneralRepositoryImpl<UserGroup> implements UserGroupRepository {
    private static UserGroupRepository instance;

    private UserGroupRepositoryImpl() {
    }

    public static UserGroupRepository getInstance() {
        if (instance == null) {
            instance = new UserGroupRepositoryImpl();
        }
        return instance;
    }

    @Override
    public UserGroup add(Connection connection, UserGroup userGroup) throws SQLException {
        List<UserGroup> userGroups = generateUserGroupList(3);
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO user_group (name) VALUES (?)")) {
            for (UserGroup user : userGroups) {
                preparedStatement.setString(1, user.getName());
                int rows = preparedStatement.executeUpdate();
            }
            return userGroup;
        }
    }

    @Override
    public void update(Connection connection, UserGroup userGroup) throws SQLException {
    }

    @Override
    public int delete(Connection connection, UserGroup userGroup) throws SQLException {
        return 0;
    }

    @Override
    public List<UserGroup> findAll(Connection connection) throws SQLException {
        return null;
    }

    public static List<UserGroup> generateUserGroupList(int countOfuserGroup) {
        List<UserGroup> userGroups = new ArrayList<>();
        for (int i = 0; i < countOfuserGroup; i++) {
            UserGroup userGroup = UserGroup.newBuilder()
                    .name("Group " + i)
                    .build();
            userGroups.add(userGroup);
        }
        return userGroups;
    }
}