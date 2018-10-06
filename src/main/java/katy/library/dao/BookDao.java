package katy.library.dao;

import katy.library.model.Author;
import katy.library.model.Book;

import java.util.List;

public interface BookDao extends CrudDAO<Book> {
    List<Book> findByAuthor(Author author);

    List<Book> findByTitle(String title);

}


