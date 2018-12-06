package katy.library.controllers.sun;

import katy.library.model.Author;
import katy.library.model.Book;
import katy.library.service.BookService;

import java.io.InputStream;
import java.net.URI;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.OptionalLong;
import java.util.StringJoiner;

public class BooksHttpHandler extends AbstractHttpHandler {

    private final BookService bookService;

    public BooksHttpHandler(BookService bookService) {

        this.bookService = bookService;
    }

    @Override
    public String path() {

        return "/books/";
    }

    private Book parseBook(String query){

        Map<String, String> queryParams = new HashMap<>();

        for (String q : query.split("&")) {
            String[] qa = q.split("=");

            queryParams.put(URLDecoder.decode(qa[0]), (qa.length == 2) ? URLDecoder.decode(qa[1]) : "");
        }

        final LocalDate dateOfBirth = LocalDate.parse(queryParams.get("dateOfBirth"), DateTimeFormatter.ISO_LOCAL_DATE);

        return Book.builder()
                .id(Long.parseLong(queryParams.get("id")))
                .title(queryParams.get("title"))
                .author(Author.builder().id(Long.parseLong(queryParams.get("authorId"))).build())
                .build();
    }

    private OptionalLong getBookId(String path){

        String[] params = path.split("/");

        return (params.length == 3) ? OptionalLong.of(Long.parseLong(params[params.length - 1])) : OptionalLong.empty();
    }


    @Override
    protected String onPost(URI requestURI, InputStream requestBody) {

        final StringJoiner returnString = new StringJoiner("\n");
        returnString.add("created book:");

        returnString.add(bookService.createBook(parseBook(requestURI.getQuery())).toString());

        return returnString.toString();
    }

    @Override
    protected String onPut(URI requestURI, InputStream requestBody) {

        final StringJoiner returnString = new StringJoiner("\n");
        returnString.add("updated book");

        returnString.add(bookService.updateBook(parseBook(requestURI.getQuery())).toString());

        return returnString.toString();
    }

    @Override
    protected String onDelete(URI requestURI) {

        final StringJoiner returnString = new StringJoiner("\n");
        returnString.add("deleted book:");

        final long bookId = getBookId(requestURI.getPath()).orElseThrow(() -> new RuntimeException("Invalid path"));
        returnString.add(bookService.deleteBook(bookId).toString());

        return returnString.toString();
    }

    @Override
    protected String onGet(URI requestURI) {

        final StringJoiner returnString = new StringJoiner("\n");
        returnString.add("book list:");

        final long bookId = getBookId(requestURI.getPath()).orElseThrow(() -> new RuntimeException("Invalid path"));
        returnString.add(bookService.getByIdBook(bookId).toString());

        return returnString.toString();
    }
}
