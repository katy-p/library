package katy.library.controllers.sun;

import katy.library.exception.ResourceNotFoundException;
import katy.library.model.Author;
import katy.library.model.Book;
import katy.library.service.BookService;

import java.io.InputStream;
import java.net.URI;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BooksHttpHandler extends AbstractHttpHandler {

    private final BookService bookService;

    public BooksHttpHandler(BookService bookService) {

        this.bookService = bookService;
    }

    @Override
    public String path() {

        return "/books/";
    }

    private Book parseBook(URI requestURI){

        Map<String, String> queryParams = getParameters(requestURI);

        return Book.builder()
                .id(Long.parseLong(queryParams.get("id")))
                .title(queryParams.get("title"))
                .author(Author
                        .builder()
                        .id(Long.parseLong(queryParams.get("authorId")))
                        .build())
                .build();
    }

    @Override
    protected String onPost(URI requestURI, InputStream requestBody) {

        final StringJoiner returnString = new StringJoiner("\n");
        returnString.add("created book:");

        returnString.add(bookService.createBook(parseBook(requestURI)).toString());

        return returnString.toString();
    }

    @Override
    protected String onPut(URI requestURI, InputStream requestBody) {

        final StringJoiner returnString = new StringJoiner("\n");
        returnString.add("updated book");

        returnString.add(bookService.updateBook(parseBook(requestURI)).toString());

        return returnString.toString();
    }

    @Override
    protected String onDelete(URI requestURI) {

        final StringJoiner returnString = new StringJoiner("\n");
        returnString.add("deleted book:");

        final long id = getId(requestURI).orElseThrow(() -> new ResourceNotFoundException("Can't delete author without id."));
        returnString.add(bookService.deleteBook(id).toString());

        return returnString.toString();
    }

    @Override
    protected String onGet(URI requestURI) {

        final StringJoiner returnString = new StringJoiner("\n");
        returnString.add("book list:");

        final OptionalLong id = getId(requestURI);

        if (id.isPresent()) {
            returnString.add(bookService.getByIdBook(id.getAsLong()).toString());

        } else {
            final List<Book> bookList = bookService.fullListOfBooks();
            bookList.forEach(book -> returnString.add(book.toString()));
        }

        return returnString.toString();
    }
}
