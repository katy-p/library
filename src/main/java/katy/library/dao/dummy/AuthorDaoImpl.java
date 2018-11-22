package katy.library.dao.dummy;

import katy.library.dao.AuthorDao;
import katy.library.model.Author;
import katy.library.model.Book;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class AuthorDaoImpl implements AuthorDao {

    private Author author1 = Author.builder()
            .id(1)
            .firstName("Terry")
            .lastName("Pratchett")
            .dateOfBirth(LocalDate.of(1948, Month.APRIL, 28))
            .build();


    @Override
    public Optional<Author> getById(long id) {
        if (id ==1) {
            return Optional.of(author1);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Author create(Author entry) {

        if (author1.equals(entry)) {
            return author1;
        } else {
            return entry;
        }
    }

    @Override
    public Author update(Author entry) {

        if (author1.equals(entry)) {
            return author1;
        } else {
            return entry;
        }
    }

    @Override
    public Optional<Author> delete(long id) {

        if (id ==1) {
            return Optional.of(author1);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<Author> findByName(String lastName) {

        List<Author> authorList = new ArrayList<>();

        if ("Smith".equals(lastName)) {
            authorList.add(author1);
        }

        return authorList;
    }
}
