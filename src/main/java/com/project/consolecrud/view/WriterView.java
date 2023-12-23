package com.project.consolecrud.view;

import com.project.consolecrud.controller.WriterController;
import com.project.consolecrud.model.Writer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class WriterView {
    private static WriterController controller;

    public WriterView(WriterController controller) {
        WriterView.controller = controller;
    }

    public static void start() {
        String menuText = """
                Writer menu.
                Choice your option:
                1. Show writers list;
                2. Add new writer;
                3. Select writer;
                4. Back.
                """;

        System.out.println(menuText);
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                int input = scanner.nextInt();
                switch (input) {
                    case 1 -> showAllWritersList();
                    case 2 -> addNewWriter();
                    case 3 -> reductWriter();
                    case 4 -> {
                        return;
                    }
                    default -> System.out.println("Not correct option.");
                }
            }
        } catch (Exception e) {
            System.out.println("Something wrong!");
            System.out.println(e.getMessage());
        }
    }

    public static void showAllWritersList() {
        List<Writer> writerList = controller.findAllWriters();
        if (writerList.isEmpty()) {
            System.out.println("Any writer not found!");
            return;
        }
        for (Writer writer : writerList) {
            System.out.printf("""
                    ---------------------------
                    id: %d
                    first name: %s
                    last name: %s%n
                    """, writer.getId(), writer.getFirstName(), writer.getLastName());
        }
        System.out.println(" ---------------------------");
    }

    public static void addNewWriter() {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Enter the writer's first and last name:");
            Writer writer = new Writer();
            writer.setFirstName(sc.next());
            writer.setLastName(sc.next());
            controller.addWriter(writer);
        } catch (Exception e) {
            System.out.println("Writer can't be added");
            System.out.println(e.getMessage());
        }
    }

    public static void reductWriter() {
        try (Scanner sc = new Scanner(System.in)) {
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

               int input = sc.nextInt();
               switch (input) {
                   case 1 -> {
                       System.out.println("Enter new writer first name: ");
                       writer.setFirstName(sc.next());
                       System.out.println("Enter new writer last name: ");
                       writer.setLastName(sc.next());
                       controller.updateWriter(writer);
                       return;
                   }
                   case 2 -> {
                       controller.deleteWriter(writer);
                       return;
                   }
                   case 3 ->{
                       return;
                   }
                   default -> System.out.println("Not correct option!");
               }
           }
        }
    }

    public static Writer findWriter() {
        Writer writer = new Writer();
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.println("""
                    Find writer by:
                    1. ID;
                    2. Name;
                    3. Back.
                    
                    Choose option
                    """);
                int input = sc.nextInt();
                switch (input) {
                    case 1 -> {
                        System.out.println("Enter writer id: ");
                        Long id = sc.nextLong();
                        writer = controller.findWriterById(id);
                    }
                    case 2 -> {
                        System.out.println("Enter writer first name: ");
                        String firstName = sc.next();
                        System.out.println("Enter writer last name: ");
                        String lastName = sc.next();
                        writer = controller.findWriterByName(firstName, lastName);
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
