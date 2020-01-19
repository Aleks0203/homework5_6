package ru.mail.druk_aleksandr.controller.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.mail.druk_aleksandr.controller.HomeWorkController;
import ru.mail.druk_aleksandr.service.TableService;
import ru.mail.druk_aleksandr.service.UserService;
import ru.mail.druk_aleksandr.service.impl.TableServiceImpl;
import ru.mail.druk_aleksandr.service.impl.UserServiceImpl;
import ru.mail.druk_aleksandr.service.model.UserDTO;
import ru.mail.druk_aleksandr.service.model.UserGroupDTO;

import java.lang.invoke.MethodHandles;

public class HomeWorkControllerImpl implements HomeWorkController {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private UserService userService = UserServiceImpl.getInstance();
    private TableService tableService = TableServiceImpl.getInstance();
    UserGroupDTO userGroupDTO = new UserGroupDTO();
    UserDTO userDTO = new UserDTO();
    private static HomeWorkController instance;

    private HomeWorkControllerImpl() {
    }

    public static HomeWorkController getInstance() {
        if (instance == null) {
            instance = new HomeWorkControllerImpl();
        }
        return instance;
    }

    @Override
    public void runTask() {
        tableService.deleteTable();
        tableService.createTable();
        userService.add(userGroupDTO);
        userService.add(userDTO);
        logger.info("Result: " + userService.findAll());
        userService.groupNameWithNumberOfUsers();
        userService.deleteUsers();
        userService.updateActive(userDTO);
    }
}
