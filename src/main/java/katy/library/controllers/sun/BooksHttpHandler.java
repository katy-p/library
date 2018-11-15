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

        throw new RuntimeException("Unsupported method: DELETE");
    }

    @Override
    protected String onGet(URI requestURI) {

       /* final Map<String, String> params = Arrays.stream(
                Optional.ofNullable(exchange.getRequestURI().getQuery()).orElse("")
                        .split("&"))
                .filter(Objects::nonNull)
                .map(p->p.split("="))
                .filter(a->a.length==2)
                .collect(Collectors.toMap(a->a[0], b->b[1]));*/

        String[] params = requestURI.getPath().split("/");

        for (int i = 2; i < params.length; i++) {
            String[] split = params[i].split("=");

        }


        String title = "";
        long id = 0;
        for (String key : params.keySet()) {
            if ("title".equals(key))  {
                title = params.get(key);
            }
            if ("id".equals(key))  {
                id = Long.parseLong(params.get(key));
            }
        }

        List<Book> bookList = bookService.findByNameBook(title);

        final StringJoiner returnString = new StringJoiner("\n");
        returnString.add("book list:");

        for (Book book : bookList) {
            returnString.add(book.toString());
        }
        //bookList.add(Optional.ofNullable(bookService.getByIdBook(id)).orElse());

        return returnString.toString();
    }
}
