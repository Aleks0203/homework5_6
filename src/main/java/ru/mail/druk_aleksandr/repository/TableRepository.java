package ru.mail.druk_aleksandr.repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface TableRepository {
    void deleteTable(Connection connection) throws SQLException;

    void createTable(Connection connection) throws SQLException;
}
