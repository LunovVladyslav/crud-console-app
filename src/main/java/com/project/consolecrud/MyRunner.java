package com.project.consolecrud;

import com.project.consolecrud.view.MainAppView;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class MyRunner implements CommandLineRunner {
    private MainAppView appView;

    public MyRunner(MainAppView appView) {
        this.appView = appView;
    }

    @Override
    public void run(String... args) throws Exception {
        appView.start();
    }
}
