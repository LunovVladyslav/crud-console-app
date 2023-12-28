package com.project.consolecrud;

import com.project.consolecrud.utils.DBConnector;
import com.project.consolecrud.view.MainAppView;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class MyRunner implements CommandLineRunner {
    private MainAppView appView;
    private DBConnector db;

    public MyRunner(MainAppView appView, DBConnector db) {
        this.db = db;
        this.appView = appView;
    }

    @Override
    public void run(String... args) throws Exception {
//        Database connection settings:
//        db.setDB_NAME("SET YOUR DB NAME HERE!");
        appView.start();
    }
}
