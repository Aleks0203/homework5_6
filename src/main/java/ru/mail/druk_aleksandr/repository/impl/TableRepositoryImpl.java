package ru.mail.druk_aleksandr.repository.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import ru.mail.druk_aleksandr.repository.TableRepository;

public class TableRepositoryImpl implements TableRepository {
    private static TableRepository instance;

    private TableRepositoryImpl() {
    }

    public static TableRepository getInstance() {
        if (instance == null) {
            instance = new TableRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void deleteTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS user_information");
            statement.executeUpdate("DROP TABLE IF EXISTS user");
            statement.executeUpdate("DROP TABLE IF EXISTS user_group");
        }
    }

    @Override
    public void createTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE user_group (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100) NOT NULL)");
            statement.executeUpdate("CREATE TABLE user (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(40) NOT NULL, password VARCHAR(40) NOT NULL, is_active BOOLEAN NOT NULL DEFAULT TRUE, user_group_id int, age int NOT NULL, FOREIGN KEY (user_group_id) REFERENCES user_group (id))");
            statement.executeUpdate("CREATE TABLE user_information (user_id INT PRIMARY KEY NOT NULL, adress VARCHAR(100), telephone VARCHAR(40), FOREIGN KEY(user_id) REFERENCES user(id))");
        }
    }
}