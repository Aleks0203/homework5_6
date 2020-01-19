package ru.mail.druk_aleksandr.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ru.mail.druk_aleksandr.repository.UserRepository;
import ru.mail.druk_aleksandr.repository.model.User;
import ru.mail.druk_aleksandr.util.RandomUtil;

public class UserRepositoryImpl extends GeneralRepositoryImpl<User> implements UserRepository {
    private static UserRepository instance;

    private UserRepositoryImpl() {
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepositoryImpl();
        }
        return instance;
    }

    @Override
    public User add(Connection connection, User user) throws SQLException {
        List<User> users = generateUserList(30);
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO user(name, password, is_active, user_group_id, age) VALUES (?,?,?,?,?)")) {
            for (User us : users) {
                preparedStatement.setString(1, us.getName());
                preparedStatement.setString(2, us.getPassword());
                preparedStatement.setBoolean(3, us.getActive());
                preparedStatement.setInt(4, us.getUserGroupId());
                preparedStatement.setInt(5, us.getAge());
                int rows = preparedStatement.executeUpdate();
            }
            return user;
        }
    }

    @Override
    public List<User> findAll(Connection connection) throws SQLException {
        try (
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM user")
        ) {
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                User user = getUser(rs);
                users.add(user);
            }
            return users;
        }
    }

    @Override
    public HashMap<String, Integer> groupNameWithNumberOfUsers(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT user_group.name, COUNT(*) AS count FROM (user_group INNER JOIN user ON user_group.id = user.user_group_id) GROUP BY name HAVING COUNT")) {
            HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
            while (resultSet.next()) {
                hashMap.put(resultSet.getString(1), resultSet.getInt(2));
            }
            return hashMap;
        }
    }

    @Override
    public int delete(Connection connection, User user) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM user WHERE id=?")) {
            statement.setInt(1, user.getId());
            int rows = statement.executeUpdate();
            return rows;
        }
    }

    @Override
    public void update(Connection connection, User user) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE user SET name=?, password=?, is_active=?, user_group_id=?, age=? WHERE id=?")) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            statement.setBoolean(3, user.getActive());
            statement.setInt(4, user.getUserGroupId());
            statement.setInt(5, user.getAge());
            statement.setInt(6, user.getId());
            statement.executeUpdate();
        }
    }

    public static List<User> generateUserList(int countOfUsers) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < countOfUsers; i++) {
            User user = User.newBuilder()
                    .name("User " + i)
                    .password("222" + i)
                    .isActive(true)
                    .userGroupId(generateUserGroupId())
                    .age(generateAge())
                    .build();
            users.add(user);
        }
        return users;
    }

    private User getUser(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String password = rs.getString("password");
        Boolean isActive = rs.getBoolean("is_active");
        int userGroupId = rs.getInt("user_group_id");
        int age = rs.getInt("age");
        return User.newBuilder()
                .id(id)
                .name(name)
                .password(password)
                .isActive(isActive)
                .userGroupId(userGroupId)
                .age(age)
                .build();
    }

    public static int generateUserGroupId() {
        int minValue = 1;
        int maxValue = 3;
        return RandomUtil.getElement(minValue, maxValue);
    }

    public static int generateAge() {
        int minValue = 25;
        int maxValue = 35;
        return RandomUtil.getElement(minValue, maxValue);
    }
}