package katy.library.controllers.sun;

import katy.library.model.Author;
import katy.library.service.AuthorService;

import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.StringJoiner;

public class AuthorsHttpHandler extends AbstractHttpHandler {

    private  final AuthorService authorService;

    public AuthorsHttpHandler(AuthorService authorService) {

        this.authorService = authorService;
    }

    @Override
    public String path() {
        return "/authors/";
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

        List<Author> authorList = authorService.findByNameAuthor("Pratchett");

        final StringJoiner returnString = new StringJoiner("\n");
        returnString.add("author list:");
        for (Author author : authorList) {
            returnString.add(author.toString());
        }

        return returnString.toString();
    }
}
