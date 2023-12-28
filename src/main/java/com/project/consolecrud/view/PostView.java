package com.project.consolecrud.view;

import com.project.consolecrud.controller.LabelController;
import com.project.consolecrud.controller.PostController;
import com.project.consolecrud.controller.WriterController;
import com.project.consolecrud.model.Label;
import com.project.consolecrud.model.Post;
import com.project.consolecrud.model.PostStatus;
import com.project.consolecrud.model.Writer;
import com.project.consolecrud.utils.ConsoleService;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Component
public class PostView {
    private WriterController writerController;
    private PostController postController;
    private LabelController labelController;
    private ConsoleService service;

    public PostView(
            PostController postController,
            LabelController labelController,
            WriterController writerController,
            ConsoleService service
    ) {
        this.writerController = writerController;
        this.postController = postController;
        this.labelController = labelController;
        this.service = service;
    }

    public void start() {
        String mainMenu = """
                Post menu.
                1. Show all posts;
                2. Add new post;
                3. Select post;
                0. Back.
                
                Choose option:
                """;


        try {
            while (true) {
                System.out.println(mainMenu);
                int input = service.readInt();
                switch (input) {
                    case 1 -> showAllPosts();
                    case 2 -> addNewPost();
                    case 3 -> reductPost();
                    case 0 -> {
                        return;
                    }
                    default -> System.out.println("Not correct option!");
                }
            }
        } catch (Exception e) {
            System.out.println("Error in post view");
        }

    }

    private void showAllPosts() {
        List<Post> posts = this.postController.findAllPosts();

        if (posts.isEmpty()) {
            System.out.println("Any posts not found!");
            return;
        }
        printPosts(posts);
    }

    public void printPosts(List<Post> posts) {
        for (Post post : posts) {
            if (!post.getStatus().getValue().equals("DELETED")) {
                List<Label> labelList = this.postController.findLabelForPost(post);
                StringBuilder labels = new StringBuilder();

                if (!labelList.isEmpty()) {
                    for (Label label : labelList) {
                        labels.append(label.getName())
                                .append(" ");
                    }
                }

                Writer writer = this.writerController.findWriterByPostId(post);

                Date date = Objects.isNull(post.getUpdated()) ? post.getCreated() : post.getUpdated();

                System.out.printf("""
                                ----------------------------
                                Writer: %s %s.
                                ID: %d.
                                Content: %s.
                                Status: %s.
                                Date: %s
                                Label: %s.
                                """,
                        writer.getFirstName(), writer.getLastName(),
                        post.getId(),
                        post.getContent(), post.getStatus(),
                        date, labels.toString()
                );
            }
        }
        System.out.println("----------------------------");
    }

    private void addNewPost() {
        Post post = new Post();
        Writer writer = null;
        try {
            System.out.println("Enter writer first name:");
            String firstName = service.readLine();
            System.out.println("Enter writer last name:");
            String lastName = service.readLine();
            writer = this.writerController.findWriterByName(firstName, lastName);

            if (Objects.isNull(writer)) {
                writer = new Writer();
                writer.setFirstName(firstName);
                writer.setLastName(lastName);
                this.writerController.addWriter(writer);
                writer = this.writerController.findWriterByName(firstName, lastName);
            }

            System.out.println("Enter text:");
            String content = service.readText();

            post.setContent(content);
            post.setCreated(Date.valueOf(LocalDate.now()));
            post.setStatus(PostStatus.ACTIVE);
            this.postController.createNewPost(post);

            post = this.postController.findByPostContent(content);
            this.postController.addPostWriterDep(post, writer);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void reductPost() {
        Post post = findPost();
        if (Objects.isNull(post)) {
            return;
        }

        String menuText = """
                Choose one of the options:
                1. Update post.
                2. Deactivate post
                3. Delete post.
                4. Add labels for the post.
                0. Back.
                User:
                """;

        while (true) {
            System.out.println(menuText);
            String input = service.readLine();
            switch (input) {
                case "1" -> updatePost(post);
                case "2" -> {
                    postController.updatePostStatus(PostStatus.DELETED, post);
                    System.out.println("Post with ID: %d successfully deactivated".formatted(post.getId()));
                }
                case "3" -> {
                    postController.deletePostFromDb(post.getId());
                    System.out.println("Post with ID: %d successfully deleted".formatted(post.getId()));
                }
                case "4" -> addLabelsForPost(post);
                case "0" -> {
                    return;
                }
                default -> System.out.println("You choose not correct option!");
            }
        }
    }

    private void updatePost(Post post) {
        while (true) {
            System.out.println("Enter new post text content:");
            String content = service.readText();
            if (content.isEmpty() || content.isBlank()) {
                System.out.println("Content can't be empty or blank!");
                continue;
            }
            post.setContent(content);
            postController.updatePostContent(post);
            System.out.println("Content for post with id: %d successfully updated");
            return;
        }

    }

    private void addLabelsForPost(Post post) {
        Label label = null;
        while (true) {
            System.out.println("Enter new labels name for the post or 'end' to exit:");
            String input = service.readLine();
            if (input.isEmpty() || input.isBlank() || Objects.isNull(input)) {
                System.out.println("Label can't be empty or blank!");
                continue;
            } else if (input.equals("end")) {
                return;
            }
            label = new Label();
            label.setName(input.toLowerCase());
            labelController.createNewLabel(label);
            label = labelController.findLabelByName(input);
            labelController.addLabelToPost(label, post);
            System.out.println("Label successfully added!");
        }
    }

    private Post findPost() {
        String menuText = """
                Find your post(s):
                1. By ID.
                2. By Content.
                3. By Writer.
                0. Back.
                
                Choose one option:
                """;
        Post post = null;
        while (true) {
            try {
                System.out.println(menuText);
                String input = service.readLine();
                switch (input) {
                    case "1" -> {
                        System.out.println("Enter post ID:");
                        String id = service.readLine();
                        post = postController.findPostById(Long.valueOf(id));
                    }
                    case "2" -> {
                        System.out.println("Enter post text:");
                        String content = service.readText();
                        post = postController.findByPostContent(content);
                    }
                    case "3" -> {
                        System.out.println("Enter writer first name:");
                        String firstName = service.readLine();
                        System.out.println("Enter writer last name:");
                        String lastName = service.readLine();
                        Writer writer = writerController.findWriterByName(firstName, lastName);
                        List<Post> posts = postController.findPostByWriter(writer);
                        printPosts(posts);
                        System.out.println("Choose your post by ID or Text content!");
                    }
                    case "0" -> {
                        return null;
                    }
                    default -> {
                        System.out.println("You choose not correct option!");
                    }
                }
                if (!Objects.isNull(post)) {
                    break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Error in finding post time!");
            }
        }
        return post;
    }
}
