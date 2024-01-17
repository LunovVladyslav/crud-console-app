package com.project.consolecrud.view;

import com.project.consolecrud.utils.ConsoleService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class MainAppView {

    private WriterView writerView;
    private PostView postView;
    private LabelView labelView;
    private ConsoleService consoleService;


    public MainAppView(
            WriterView writerView,
            PostView postView,
            LabelView labelView,
            ConsoleService service
    ) {
        this.consoleService = service;
        this.writerView = writerView;
        this.postView = postView;
        this.labelView = labelView;
    }

    public void start() {
        String mainMenu = """
                Choice your option:
                1. Writer menu.
                2. Post menu.
                3. Label menu.
                0. Exit.
                 """;

        try {
            while (true) {
                System.out.println(mainMenu);
                switch (consoleService.readLine()) {
                    case "1" -> writerView.start();
                    case "2" -> postView.start();
                    case "3" -> labelView.start();
                    case "0" -> {
                        System.out.println("Good bye!");
                        consoleService.closeScanner();
                        return;
                    }
                    default -> System.out.println("Not correct option...");
                }
            }
        } catch (Exception e) {
            System.out.println("error is here");
            e.printStackTrace();
        }
    }
}
