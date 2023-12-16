package com.project.consolecrud.repository;

import com.project.consolecrud.model.Writer;

public interface WriterRepository extends GenericRepository<Writer, Long > {
    Writer findByName(String firstName, String lastName);
}
