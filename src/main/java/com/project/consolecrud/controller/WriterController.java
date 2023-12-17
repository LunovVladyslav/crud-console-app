package com.project.consolecrud.controller;

import com.project.consolecrud.model.Writer;
import com.project.consolecrud.service.WriterService;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Scanner;

@Controller
public class WriterController {
    private WriterService service;

    public WriterController(WriterService service) {
        this.service = service;
    }

    public void addWriter() {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Enter the writer's first and last name:");
            Writer writer = new Writer();
            writer.setFirstName(sc.next());
            writer.setLastName(sc.next());
            service.addWriter(writer);
        } catch (Exception e) {
            System.out.println("Writer can't be added");
            System.out.println(e.getMessage());
        }
    }

    public void showAllWriters() {
        try {
            List<Writer> writerList = service.findAll();
            if (writerList.isEmpty()) {
                throw new NullPointerException();
            }
            for (Writer writer : writerList) {
                System.out.printf("ID:%d. Name: %s %s.%n",
                        writer.getId(),
                        writer.getFirstName(), writer.getLastName());
            }
        } catch (NullPointerException e) {
            System.out.println("Any writer not found!");
        }
    }





}
