package katy.library.dao.dummy;

import katy.library.dao.BookDao;
import katy.library.model.Author;
import katy.library.model.Book;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookDaoImpl implements BookDao {

    private Author author1 = Author.builder()
            .id(1)
            .firstName("Terry")
            .lastName("Pratchett")
            .dateOfBirth(LocalDate.of(1948, Month.APRIL, 28))
            .build();

    private Book book1 = Book.builder()
            .id(1)
            .title("DiscWorld")
            .author(author1)
            .build();

    @Override
    public Optional<Book> getById(long id) {
        if (id == 1) {
            return Optional.of(book1);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Book create(Book entry) {

        if (book1.equals(entry)) {
            return book1;
        } else {
            return entry;
        }
    }

    @Override
    public Book update(Book entry) {

        if (book1.equals(entry)) {
            return book1;
        } else {
            return entry;
        }
    }

    @Override
    public Optional<Book> delete(long id) {

        if (id == 1) {
            return Optional.of(book1);
        } else {
            return Optional.empty();
        }
    }


    @Override
    public List<Book> findByAuthor(Author author) {

        List<Book> bookList = new ArrayList<>();

        if (author1.equals(author)) {
            bookList.add(book1);
        }

        return bookList;
    }

    @Override
    public List<Book> findByTitle(String title) {

        List<Book> bookList = new ArrayList<>();

        if ("DiscWorld".equals(title)) {
            bookList.add(book1);
        }

        return bookList;
    }
}
