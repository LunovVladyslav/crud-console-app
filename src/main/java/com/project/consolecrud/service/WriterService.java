package com.project.consolecrud.service;

import com.project.consolecrud.model.Writer;
import com.project.consolecrud.repository.WriterRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class WriterService {
    private WriterRepository repository;

    public WriterService(WriterRepository repository) {
        this.repository = repository;
    }

    public void addWriter(Writer writer) {
        try {
            repository.save(writer);
            System.out.println("Writer successfully added.");
        } catch (Exception e) {
            System.out.println("Something wrong! Writer not added");
        }
    }

    public List<Writer> findAll() {
        List<Writer> writerList = repository.findAll();

        if (writerList.isEmpty()) {
            System.out.println("Writers not found");
            return Collections.emptyList();
        }
        return writerList;
    }

    public Writer findByName(String firstName, String lastName) throws NullPointerException {
        Writer writer = repository.findByName(firstName, lastName);
        if (Objects.isNull(writer)) {
            System.out.println("Writer not found");
            return null;
        }
        return writer;
    }

    public Writer findById(Long id) {
        Writer writer = repository.findById(id);
        if (Objects.isNull(writer)) {
            System.out.println("Writer not found");
            return null;
        }
        return writer;
    }

    public void update(Writer writer) {
        try {
            repository.update(writer);
            System.out.printf("Writer %s %s successfully updated%n",
                    writer.getFirstName(), writer.getLastName());
        } catch (Exception e) {
            System.out.println("Something wrong, operation aborted!");
            System.out.println(e.getMessage());
        }
    }

    public void deleteById(Long id) {
        try {
            repository.deleteById(id);
            System.out.println("Writer successfully deleted");
        }  catch (Exception e) {
            System.out.println("Something wrong, operation aborted!");
            System.out.println(e.getMessage());
        }
    }
}
