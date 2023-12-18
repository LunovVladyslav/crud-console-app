package com.project.consolecrud.view;

import com.project.consolecrud.model.Writer;

import java.util.Scanner;

public class MainAppView {
    public static void start() {
        String mainMenu = """
               Choice your option:
               1. Writer menu;
               2. Post menu;
               0. Exit.
                """;

        while (true) {
            try (Scanner sc = new Scanner(System.in)) {
                System.out.println(mainMenu);
                int input = sc.nextInt();
                switch (input) {
                    case 1 -> WriterView.start();
                    case 2 -> PostView.start();
                    case 0 -> {
                        return;
                    }
                    default -> System.out.println("Not correct option...");
                }
            } catch (Exception e) {
                System.out.println("Something wrong...\n" + e.getMessage());
            }
        }
    }
}
