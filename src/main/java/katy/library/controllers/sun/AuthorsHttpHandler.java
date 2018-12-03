package katy.library.controllers.sun;

import katy.library.model.Author;
import katy.library.model.Person;
import katy.library.service.AuthorService;

import java.io.InputStream;
import java.net.URI;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
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

    private Author getAuthor(String query){

        Map<String, String> queryParams = new HashMap<>();

        for (String q : query.split("&")) {
            String[] qa = q.split("=");

            queryParams.put(URLDecoder.decode(qa[0]), (qa.length == 2) ? URLDecoder.decode(qa[1]) : "");
        }

        final LocalDate dateOfBirth = LocalDate.parse(queryParams.get("dateOfBirth"), DateTimeFormatter.ISO_LOCAL_DATE);

        return Author.builder()
                .id(Long.parseLong(queryParams.get("id")))
                .firstName(queryParams.get("firstName"))
                .lastName(queryParams.get("lastName"))
                .dateOfBirth(dateOfBirth)
                .build();
    }

    private long getAuthorId(String path){

        String[] params = path.split("/");

        return (params.length == 3) ? Long.parseLong(params[params.length - 1]) : 0;
    }

    @Override
    protected String onPost(URI requestURI, InputStream requestBody) {

        final StringJoiner returnString = new StringJoiner("\n");
        returnString.add("created author:");

        returnString.add(authorService.createAuthor(getAuthor(requestURI.getQuery())).toString());

        return returnString.toString();
    }

    @Override
    protected String onPut(URI requestURI, InputStream requestBody) {

        final StringJoiner returnString = new StringJoiner("\n");
        returnString.add("updated author:");

        returnString.add(authorService.updateAuthor(getAuthor(requestURI.getQuery())).toString());

        return returnString.toString();
    }

    @Override
    protected String onDelete(URI requestURI) {

        final StringJoiner returnString = new StringJoiner("\n");
        returnString.add("deleted author:");

        returnString.add(authorService.deleteAuthor(getAuthorId(requestURI.getPath())).toString());

        return returnString.toString();
    }

    @Override
    protected String onGet(URI requestURI) {

        final StringJoiner returnString = new StringJoiner("\n");
        returnString.add("author list:");

        returnString.add(authorService.getByIdAuthor(getAuthorId(requestURI.getPath())).toString());

        return returnString.toString();
    }
}
