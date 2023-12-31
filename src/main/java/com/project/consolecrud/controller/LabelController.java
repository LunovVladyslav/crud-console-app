package com.project.consolecrud.controller;

import com.project.consolecrud.model.Label;
import com.project.consolecrud.model.Post;
import com.project.consolecrud.service.LabelService;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class LabelController {
    private LabelService service;

    public LabelController(LabelService service) {
        this.service = service;
    }

    public void createNewLabel(Label label) {
        this.service.addLabel(label);
    }

    public void addLabelToPost(Label label, Post post) {
        this.service.addLabelForPost(label, post);
    }

    public List<Label> findAllLabels() {
        return this.service.findAll();
    }

    public Label findLabelById(Long id) {
        return this.service.findById(id);
    }

    public Label findLabelByName(String name) {
        return this.service.findByName(name);
    }

    public void updateLabel(Label label) {
        this.service.updateLabel(label);
    }

    public void deleteLabelById(Long id) {
        this.service.deleteById(id);
    }
}
