package com.project.consolecrud.view;

import com.project.consolecrud.controller.WriterController;
import com.project.consolecrud.model.Writer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WriterView {
    private List<Writer> writerList;
    private Writer currentWriter;
    private WriterController controller;

    public WriterView(WriterController controller) {
        this.controller = controller;
    }

    public static void start() {
        String menuText = """
                Writer menu.
                Choice your option:
                1. Show writers list;
                2. Add new writer;
                3. Find writer by id;
                4. Find writer by name;
                
                """;

    }
 }
