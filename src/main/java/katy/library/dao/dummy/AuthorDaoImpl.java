package katy.library.dao.dummy;

import katy.library.dao.AuthorDao;
import katy.library.model.Author;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;


public class AuthorDaoImpl implements AuthorDao {

    @Override
    public Optional<Author> getById(long id) {
        if (id ==1) {
            return Optional.of(Author.builder()
                .id(1)
                .firstName("Terry")
                .lastName("Pratchett")
                .dateOfBirth(LocalDate.of(1948, Month.APRIL, 28))
                .build());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Author create(Author entry) {
        return null;
    }

    @Override
    public Author update(Author entry) {
        return null;
    }

    @Override
    public Optional<Author> delete(long id) {
        return Optional.empty();
    }

    @Override
    public List<Author> findByName(String lastName) {
        return null;
    }
}
