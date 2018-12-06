package katy.library.dao;


import katy.library.model.Author;

import java.util.List;

public interface AuthorDao extends CrudDAO<Author> {
    List<Author> findByName(String lastName);

    List<Author> fullList();

}
