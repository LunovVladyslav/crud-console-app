package com.project.consolecrud.view;

import com.project.consolecrud.model.Label;
import com.project.consolecrud.model.Post;
import com.project.consolecrud.repository.LabelRepository;
import com.project.consolecrud.repository.PostRepositry;
import com.project.consolecrud.utils.ConsoleService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LabelView {
    private ConsoleService service;
    private LabelRepository labelRepository;
    private PostRepositry postRepositry;


    public LabelView(
            ConsoleService service,
            LabelRepository labelRepository,
            PostRepositry postRepositry) {
        this.labelRepository = labelRepository;
        this.postRepositry = postRepositry;
        this.service = service;
    }

    public void start() {
        String menuText = """
                Label menu:
                1. Show all labels.
                2. Add new label.
                3. Select label.
                0. Back.
                Choose one option:
                """;
        while (true) {
            System.out.println(menuText);
            String input = service.readLine();
            switch (input) {
                case "1" -> showAllLabels();
                case "2" -> addNewLabel();
                case "3" -> reductLabel();
                case "0" -> {
                    return;
                }
                default -> System.out.println("You choose not correct option!");
            }
        }
    }

    public void showAllLabels() {
        List<Label> labelList = new ArrayList<>();
        labelList = labelRepository.findAll();

        if (labelList.isEmpty()) {
            System.out.println("Any labels not found!");
            return;
        }

        printLabels(labelList);
    }

    private void printLabels(List<Label> labelList) {
        List<Post> posts = new ArrayList<>();
        for (Label label : labelList) {
            posts = postRepositry.findPostByLabelId(label);
        }
    }
}
