package com.project.consolecrud.view;

import com.project.consolecrud.controller.LabelController;
import com.project.consolecrud.controller.PostController;
import org.springframework.stereotype.Component;

@Component
public class PostView {
    private static PostController postController;
    private static LabelController labelController;

    public PostView(PostController postController, LabelController labelController) {
        PostView.postController = postController;
        PostView.labelController = labelController;
    }

    public static void start() {

    }
}
