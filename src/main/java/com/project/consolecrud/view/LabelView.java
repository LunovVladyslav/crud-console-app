package com.project.consolecrud.view;

import com.project.consolecrud.controller.LabelController;
import com.project.consolecrud.controller.PostController;
import com.project.consolecrud.model.Label;
import com.project.consolecrud.model.Post;
import com.project.consolecrud.utils.ConsoleService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LabelView {
    private ConsoleService service;
    private LabelController labelController;
    private PostController postController;


    public LabelView(
            ConsoleService service,
            LabelController labelController,
            PostController postController) {
        this.labelController = labelController;
        this.postController = postController;
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

    private void reductLabel() {
        Label label = findLabel();

        while (true) {
            System.out.println("""
                -------------------
                Your label:
                ID: %d.
                Name: %s.
                -------------------
                Choose one option:
                1. Update label name.
                2. Add label to post.
                3. Delete label.
                0. Back.
                """.formatted(label.getId(), label.getName()));

            int input = service.readInt();
            switch (input) {
                case 1 -> {
                    System.out.println("Enter new label name:");
                    String name = service.readLine();
                    if (name.isEmpty() || name.isBlank()) {
                        System.out.println("Not correct name!");
                        continue;
                    }
                    label.setName(name);
                    labelController.updateLabel(label);
                    System.out.println("Name successfully updated!");
                    label = labelController.findLabelByName(name);
                }
                case 2 -> {
                    System.out.println("Enter post id: ");
                    Long id = Long.valueOf(service.readInt());
                    Post post = postController.findPostById(id);
                    labelController.addLabelToPost(label, post);
                    System.out.println(
                            "Label successfully added to post with id: %d".formatted(id));
                }
                case 3 -> {
                    try {
                        labelController.deleteLabelById(label.getId());
                        System.out.println("Label deleted!");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 0 -> {
                    return;
                }
                default -> {
                    System.out.println("Not correct option!");
                }
            }
        }

    }

    private Label findLabel() {
        Label label = null;

        System.out.println("""
                Find label by:
                1. ID.
                2. Name.
                0. Black.
                Choose one option:
                """);

        try {
            switch (service.readInt()) {
                case 1 -> {
                    System.out.println("Enter label id:");
                    label = labelController.findLabelById(Long.valueOf(service.readLine()));
                }
                case 2 -> {
                    System.out.println("Enter label name:");
                    label = labelController.findLabelByName(service.readLine().toLowerCase());
                }
                case 0 -> {
                    return label;
                }
                default -> System.out.println("You choose not correct option! Try again!");
            }
        } catch (Exception e) {
            System.out.println("Label not founded!");
            System.out.println(e.getMessage());
        }
        return label;
    }

    private void addNewLabel() {
        Label label = new Label();
        String name;
        try {
            while (true) {
                System.out.println("Enter label name:");
                name = service.readLine();
                if (name.isEmpty() || name.isBlank()) {
                    System.out.println("Name can't be empty!");
                    continue;
                }
                label.setName(name.toLowerCase());
                break;
            }
            labelController.createNewLabel(label);
            label = labelController.findLabelByName(name);
            System.out.println("Label: id: %d; name: %s. Created successfully!"
                    .formatted(label.getId(), label.getName()));
        } catch (Exception e) {
            System.out.println("Something wrong! Label don't created!");
            System.out.println(e.getMessage());
        }
    }

    public void showAllLabels() {
        List<Label> labelList = new ArrayList<>();
        labelList = labelController.findAllLabels();

        if (labelList.isEmpty()) {
            System.out.println("Any labels not found!");
            return;
        }

        printLabels(labelList);
    }

    private void printLabels(List<Label> labelList) {
        List<Post> posts = new ArrayList<>();
        for (Label label : labelList) {
            posts = postController.findPostsByLabelId(label);
            StringBuilder postBuilder = new StringBuilder();
            if (!posts.isEmpty()) {
                for (Post post : posts) {
                    postBuilder.append(post.getId()).append(" ");
                }
            }

            if (postBuilder.isEmpty()) {
                postBuilder.append("not using yet!");
            }
            System.out.println("""
                    ---------------------------
                    id: %d.
                    name: %s.
                    used in post with id: %s.
                    """.formatted(label.getId(), label.getName(), postBuilder));
        }
        System.out.println("---------------------------");
    }
}
