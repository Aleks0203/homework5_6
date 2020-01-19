package ru.mail.druk_aleksandr.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import ru.mail.druk_aleksandr.repository.model.User;

public interface UserRepository extends GeneralRepository<User> {
    HashMap<String, Integer> groupNameWithNumberOfUsers(Connection connection) throws SQLException;
}
