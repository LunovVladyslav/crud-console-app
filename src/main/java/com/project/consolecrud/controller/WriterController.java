package com.project.consolecrud.controller;

import com.project.consolecrud.model.Post;
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

    public void addWriter(Writer writer) {
        this.service.addWriter(writer);
    }

    public List<Writer> findAllWriters() {
       return this.service.findAll();
    }

    public Writer findWriterById(Long id) {
        return this.service.findById(id);
    }

    public Writer findWriterByPostId(Post post) {
        return this.service.findWriterByPost(post);
    }

    public Writer findWriterByName(String fname, String lname) {
        return this.service.findByName(fname, lname);
    }

    public void updateWriter(Writer writer) {
        this.service.updateWriter(writer);
    }

    public void deleteWriter(Writer writer) {
        Long id = writer.getId();
        this.service.deleteWriterById(id);
    }





}
