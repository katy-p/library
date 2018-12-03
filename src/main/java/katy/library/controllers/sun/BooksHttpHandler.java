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

    private Book getBook(String query){

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

    private long getBookId(String path){

        String[] params = path.split("/");

        return (params.length == 3) ? Long.parseLong(params[params.length - 1]) : 0;
    }


    @Override
    protected String onPost(URI requestURI, InputStream requestBody) {
        throw new RuntimeException("Unsupported method: POST");
    }

    @Override
    protected String onPut(URI requestURI, InputStream requestBody) {
        throw new RuntimeException("Unsupported method: PUT");
    }

    @Override
    protected String onDelete(URI requestURI) {

        final StringJoiner returnString = new StringJoiner("\n");
        returnString.add("deleted book:");

        returnString.add(bookService.deleteBook(getBookId(requestURI.getPath())).toString());

        return returnString.toString();
    }

    @Override
    protected String onGet(URI requestURI) {

        final StringJoiner returnString = new StringJoiner("\n");
        returnString.add("book list:");

        returnString.add(bookService.getByIdBook(getBookId(requestURI.getPath())).toString());

        return returnString.toString();
    }
}
