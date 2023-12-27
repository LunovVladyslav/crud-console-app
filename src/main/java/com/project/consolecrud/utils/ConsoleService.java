package com.project.consolecrud.utils;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleService {
    private Scanner scanner = new Scanner(System.in);

    public String readLine() {
        return scanner.next();
    }

    public String readText() {
        scanner.nextLine();
        return scanner.nextLine();
    }

    public Integer readInt() {
        return scanner.nextInt();
    }

    public void closeScanner() {
        scanner.close();
    }
}
