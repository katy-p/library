package katy.library.dao.dummy;

import katy.library.dao.BookDao;
import katy.library.model.Author;
import katy.library.model.Book;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

public class BookDaoImpl implements BookDao {

    @Override
    public Optional<Book> getById(long id) {
        if (id == 1) {
            return Optional.of(
                    Book.builder()
                            .id(1)
                            .title("DiscWorld")
                            .author(Author.builder()
                                    .id(1)
                                    .firstName("Terry")
                                    .lastName("Pratchett")
                                    .dateOfBirth(LocalDate.of(1948, Month.APRIL, 28))
                                    .build())
                            .build()
            );
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Book create(Book entry) {
        return null;
    }

    @Override
    public Book update(Book entry) {
        return null;
    }

    @Override
    public Optional<Book> delete(long id) {
        return Optional.empty();
    }


    @Override
    public List<Book> findByAuthor(Author author) {
        return null;
    }

    @Override
    public List<Book> findByTitle(String title) {
        return null;
    }
}
