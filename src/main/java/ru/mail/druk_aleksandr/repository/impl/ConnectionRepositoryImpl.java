package ru.mail.druk_aleksandr.repository.impl;

import java.sql.Connection;
import java.sql.SQLException;

import ru.mail.druk_aleksandr.repository.ConnectionRepository;
import ru.mail.druk_aleksandr.util.PropertyUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import static ru.mail.druk_aleksandr.repository.constant.ConnectionConstant.DATABASE_PASSWORD;
import static ru.mail.druk_aleksandr.repository.constant.ConnectionConstant.DATABASE_URL;
import static ru.mail.druk_aleksandr.repository.constant.ConnectionConstant.DATABASE_USERNAME;
import static ru.mail.druk_aleksandr.repository.constant.PoolConstant.DATASOURSE_PREPSTMTS;
import static ru.mail.druk_aleksandr.repository.constant.PoolConstant.DATASOURSE_LIMIT;
import static ru.mail.druk_aleksandr.repository.constant.PoolConstant.DATASOURSE_SIZE;

public class ConnectionRepositoryImpl implements ConnectionRepository {

    private static ConnectionRepository instance;

    private ConnectionRepositoryImpl() {
    }

    public static ConnectionRepository getInstance() {
        if (instance == null) {
            instance = new ConnectionRepositoryImpl();
        }
        return instance;
    }

    private static HikariDataSource ds;

    @Override
    public Connection getConnection() throws SQLException {
        if (ds == null) {
            PropertyUtil propertyUtil = new PropertyUtil();
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(propertyUtil.getProperty(DATABASE_URL));
            config.setUsername(propertyUtil.getProperty(DATABASE_USERNAME));
            config.setPassword(propertyUtil.getProperty(DATABASE_PASSWORD));
            config.setDataSourceJNDI(propertyUtil.getProperty(DATASOURSE_LIMIT));
            config.setDataSourceJNDI(propertyUtil.getProperty(DATASOURSE_PREPSTMTS));
            config.setDataSourceJNDI(propertyUtil.getProperty(DATASOURSE_SIZE));
            ds = new HikariDataSource(config);
        }
        return ds.getConnection();
    }
}


