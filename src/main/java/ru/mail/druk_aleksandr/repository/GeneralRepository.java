package ru.mail.druk_aleksandr.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GeneralRepository<T> {
    T add(Connection connection, T t) throws SQLException;

    void update(Connection connection, T t) throws SQLException;

    int delete(Connection connection, T t) throws SQLException;

    List<T> findAll(Connection connection) throws SQLException;
}


