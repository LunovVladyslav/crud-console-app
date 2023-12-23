package com.project.consolecrud.service;

import com.project.consolecrud.model.Label;
import com.project.consolecrud.repository.LabelRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class LabelService {
    private LabelRepository repository;

    public LabelService(LabelRepository repository) {
        this.repository = repository;
    }

    public void addLabel(Label label) {
        try {
            repository.save(label);
            System.out.println("New label successfully added");
        } catch (Exception e) {
            System.out.println("Something wrong, operation aborted");
        }
    }

    public List<Label> findAll() {
        List<Label> labels = repository.findAll();
        if (labels.isEmpty()) {
            System.out.println("Any labels don't added yet.");
            return Collections.emptyList();
        }
        return labels;
    }

    public Label findById(Long id) {
        Label label = repository.findById(id);
        if (Objects.isNull(label)) {
            System.out.println("Label don't found");
            return null;
        }
        return label;
    }

    public void updateLabel(Label label) {
        try {
            repository.update(label);
            System.out.printf("Label with id: %d, successfully updated%n", label.getId());
        } catch (Exception e) {
            System.out.println("Something went wrong. Operation aborted!");
        }
    }

    public void deleteById(Long id) {
        try {
            repository.deleteById(id);
            System.out.printf("Label with id: %d, deleted from DB%n", id);
        } catch (Exception e) {
            System.out.println("Something went wrong. Operation aborted!");
        }
    }


 }
