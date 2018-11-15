package katy.library.service;

import katy.library.dao.BookDao;
import katy.library.dao.LibraryCardDao;
import katy.library.dao.map.BookMapDao;
import katy.library.dao.map.LibraryCardMapDao;
import katy.library.exception.ResourceNotFoundException;
import katy.library.exception.ValidationException;
import katy.library.model.Author;
import katy.library.model.Book;
import katy.library.model.LibraryCard;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class BookService {

    private final BookDao dao;

    private final LibraryCardDao libraryCardDAO;

    public BookService(BookDao dao, LibraryCardDao libraryCardDAO) {
        this.dao = dao;
        this.libraryCardDAO = libraryCardDAO;
    }

    private void validateNotNull(Object value, String fieldName) {
        if (value == null) {
            throw new ValidationException("Field should not be null", fieldName);
        }
    }

    public Book getByIdBook(long id) {
        Optional<Book> optionalBook = dao.getById(id);

        return optionalBook.orElseThrow(() -> new ResourceNotFoundException("Can't find book with id " + id));
    }

    public Book createBook(Book book) {
        validateNotNull(book.getTitle(), "title");
        validateNotNull(book.getAuthor(), "author");

        return dao.create(book);
    }

    public Book updateBook(Book book) {

        validateNotNull(book.getTitle(), "title");
        validateNotNull(book.getAuthor(), "author");

        return dao.update(book);

    }

    public Book deleteBook(long id) {

        Optional<Book> optionalBook = dao.delete(id);

        return optionalBook.orElseThrow(() -> new ResourceNotFoundException("Can't find book with id " + id));
    }

    public List<Book> findByNameBook(String title) {

        Objects.requireNonNull(title, "Title required.");

        return dao.findByTitle(title);
    }

    public List<Book> findByAuthorBook(Author author) {

        validateNotNull(author, "author");

        return dao.findByAuthor(author);
    }

    public LibraryCard borrowBook(LibraryCard libraryCard) {

        validateNotNull(libraryCard.getBook(), "book");
        validateNotNull(libraryCard.getPerson(), "person");

        return libraryCardDAO.create(libraryCard);
    }

    public LibraryCard returnBook(long id) {

        Optional<LibraryCard> optionalLC = libraryCardDAO.delete(id);

        return optionalLC.orElseThrow(() -> new ResourceNotFoundException("Can't find book with id " + id));
    }
}
