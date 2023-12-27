package com.project.consolecrud.view;

import com.project.consolecrud.controller.WriterController;
import com.project.consolecrud.model.Writer;
import com.project.consolecrud.utils.ConsoleService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Component
public class WriterView {
    private WriterController controller;
    private ConsoleService service;


    public WriterView(WriterController controller, ConsoleService service) {
        this.service = service;
        this.controller = controller;
    }

    public void start() {
        String writerMenuText = """
                Writer menu.
                Choice your option:
                1. Show writers list;
                2. Add new writer;
                3. Select writer;
                0. Back.
                """;
        try {
            while (true) {
                System.out.println(writerMenuText);
                switch (service.readLine()) {
                    case "1" -> showAllWritersList();
                    case "2" -> addNewWriter();
                    case "3" -> redactWriter();
                    case "0" -> {
                        return;
                    }
                    default -> {
                        System.out.println("Not correct option.");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void showAllWritersList() {
        List<Writer> writerList = this.controller.findAllWriters();
        if (writerList.isEmpty()) {
            System.out.println("Any writer not found!");
            return;
        }
        for (Writer writer : writerList) {
            System.out.printf("""
                    ---------------------------
                    id: %d
                    first name: %s
                    last name: %s
                    """, writer.getId(), writer.getFirstName(), writer.getLastName());
        }
        System.out.println(" ---------------------------");
    }

    public void addNewWriter() {
        try {
            System.out.println("Enter the writer's first name:");
            String firstName = service.readLine();
            System.out.println("Enter the writer's last name:");
            String lastName = service.readLine();
            Writer writer = new Writer();
            writer.setFirstName(firstName);
            writer.setLastName(lastName);
            this.controller.addWriter(writer);
        } catch (Exception e) {
            System.out.println("Writer can't be added");
            System.out.println(e.getMessage());
        }
    }

    public void redactWriter() {
        try {
            Writer writer = findWriter();

            if (writer.getFirstName().isEmpty() || writer.getLastName().isEmpty()) {
                return;
            }

           while (true) {
               System.out.printf("""
                Your writer:
                id: %d,
                first name: %s,
                last name: %s.
                
                Choose option:
                1. Update writer name;
                2. Delete writer.
                3. Back%n
                """, writer.getId(), writer.getFirstName(), writer.getLastName());

               int input = service.readInt();
               switch (input) {
                   case 1 -> {
                       System.out.println("Enter new writer first name: ");
                       writer.setFirstName(service.readLine());
                       System.out.println("Enter new writer last name: ");
                       writer.setLastName(service.readLine());
                       this.controller.updateWriter(writer);
                       return;
                   }
                   case 2 -> {
                       this.controller.deleteWriter(writer);
                       return;
                   }
                   case 3 ->{
                       return;
                   }
                   default -> System.out.println("Not correct option!");
               }
           }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Writer findWriter() {
        Writer writer = new Writer();
        try {
            while (true) {
                System.out.println("""
                    Find writer by:
                    1. ID;
                    2. Name;
                    3. Back.
                    
                    Choose option:
                    """);
                int input = service.readInt();
                switch (input) {
                    case 1 -> {
                        System.out.println("Enter writer id: ");
                        Long id = Long.parseLong(service.readLine());
                        writer = this.controller.findWriterById(id);
                    }
                    case 2 -> {
                        System.out.println("Enter writer first name: ");
                        String firstName = service.readLine();
                        System.out.println("Enter writer last name: ");
                        String lastName = service.readLine();
                        writer = this.controller.findWriterByName(firstName, lastName);
                    }
                    case 3 -> {
                        return writer;
                    }
                    default -> System.out.println("Not correct option");
                }
                return writer;
            }
        } catch (Exception e) {
            System.out.println("Something wrong!");
            return null;
        }

    }
 }
