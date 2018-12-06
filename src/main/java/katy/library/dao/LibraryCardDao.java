package katy.library.dao;

import katy.library.model.Book;
import katy.library.model.LibraryCard;
import katy.library.model.Person;

import java.util.List;

public interface LibraryCardDao extends CrudDAO<LibraryCard>{

    List<LibraryCard> findByPerson(Person person);

    List<LibraryCard> findByBook(Book book);

    List<LibraryCard> fullList();
}
