package katy.library.controllers.sun;

import katy.library.model.Book;
import katy.library.service.BookService;

import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.StringJoiner;

public class BooksHttpHandler extends AbstractHttpHandler {

    private  final BookService bookService;

    public BooksHttpHandler(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public String path() {
        return "/books/";
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

        String[] params = requestURI.getPath().split("/");
        long id = Long.parseLong(params[params.length-1]);

        final StringJoiner returnString = new StringJoiner("\n");
        returnString.add("deleted book:");

        returnString.add(bookService.deleteBook(id).toString());

        return returnString.toString();
    }

    @Override
    protected String onGet(URI requestURI) {

        String[] params = requestURI.getPath().split("/");

        long id = Long.parseLong(params[params.length-1]);

        //List<Book> bookList = bookService.findByNameBook(title);

        final StringJoiner returnString = new StringJoiner("\n");
        returnString.add("book list:");

        //for (Book book : bookList) {
        //    returnString.add(book.toString());
        //}
        returnString.add(bookService.getByIdBook(id).toString());

        return returnString.toString();
    }
}
