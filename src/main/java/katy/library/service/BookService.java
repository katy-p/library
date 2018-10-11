package katy.library.service;

import katy.library.dao.map.BookMapDao;
import katy.library.dao.map.LibraryCardMapDao;
import katy.library.exception.ResourceNotFoundException;
import katy.library.exception.ValidationException;
import katy.library.model.Author;
import katy.library.model.Book;
import katy.library.model.LibraryCard;
import katy.library.model.Person;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class BookService {

    private BookMapDao dao = new BookMapDao();

    private LibraryCardMapDao libraryCardDAO = new LibraryCardMapDao();


    private void validateNotNull(Object value, String fieldName) {
        if (value == null) {
            throw new ValidationException("Field should not be null", fieldName);
        }
    }

    public Book getByIdBook(long id){
        Optional<Book> optionalBook = dao.getById(id);

        return optionalBook.orElseThrow(() -> new ResourceNotFoundException("Can't find book with id " + id));
    }

    public  Book createBook(Book book){
        validateNotNull(book.getTitle(), "title");
        validateNotNull(book.getAuthor(), "author");

        return dao.create(book);
    }

   public  Book updateBook(Book book){

        validateNotNull(book.getTitle(), "title");
        validateNotNull(book.getAuthor(), "author");

        return dao.update(book);
    }

    public Book deleteBook(long id){

        Optional<Book> optionalBook = dao.delete(id);

        return optionalBook.orElseThrow(()-> new ResourceNotFoundException("Can't find book with id " + id));
    }

    public List<Book> findByNameBook(String title){

        Objects.requireNonNull(title, "Title required.");

        return dao.findByTitle(title);
    }

    public List<Book> findByAuthorBook(Author author){

        Objects.requireNonNull(author, "Author required.");

        return dao.findByAuthor(author);
    }

    public LibraryCard borrowBook(Book book, Person person){

        Objects.requireNonNull(book, "Book required.");
        Objects.requireNonNull(person, "Person required.");

        LibraryCard libraryCard = LibraryCard.builder()
                .id(1)
                .book(book)
                .person(person)
                .build();

        return libraryCardDAO.create(libraryCard);
    }

    public Optional<LibraryCard> returnBook(long id){

        Objects.requireNonNull(id, "Id required.");

        return libraryCardDAO.delete(id);
    }
}
