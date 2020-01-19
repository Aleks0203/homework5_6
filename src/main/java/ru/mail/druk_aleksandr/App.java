package ru.mail.druk_aleksandr;

import ru.mail.druk_aleksandr.controller.HomeWorkController;
import ru.mail.druk_aleksandr.controller.impl.HomeWorkControllerImpl;

public class App {

    public static void main(String[] args) {
        HomeWorkController homeWorkController = HomeWorkControllerImpl.getInstance();
        homeWorkController.runTask();
    }
}
