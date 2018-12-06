package katy.library.controllers.sun;

import katy.library.exception.ResourceNotFoundException;
import katy.library.model.Author;
import katy.library.service.AuthorService;

import java.io.InputStream;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.OptionalLong;
import java.util.StringJoiner;

public class AuthorsHttpHandler extends AbstractHttpHandler {

    private final AuthorService authorService;

    public AuthorsHttpHandler(AuthorService authorService) {

        this.authorService = authorService;
    }

    @Override
    public String path() {
        return "/authors/";
    }

    private Author parseAuthor(URI requestURI) {

        Map<String, String> queryParams = getParameters(requestURI);

        final LocalDate dateOfBirth = LocalDate.parse(queryParams.get("dateOfBirth"), DateTimeFormatter.ISO_LOCAL_DATE);

        return Author.builder()
                .id(Long.parseLong(queryParams.get("id")))
                .firstName(queryParams.get("firstName"))
                .lastName(queryParams.get("lastName"))
                .dateOfBirth(dateOfBirth)
                .build();
    }

    @Override
    protected String onPost(URI requestURI, InputStream requestBody) {

        final StringJoiner returnString = new StringJoiner("\n");
        returnString.add("created author:");

        returnString.add(authorService.createAuthor(parseAuthor(requestURI)).toString());

        return returnString.toString();
    }

    @Override
    protected String onPut(URI requestURI, InputStream requestBody) {

        final StringJoiner returnString = new StringJoiner("\n");
        returnString.add("updated author:");

        returnString.add(authorService.updateAuthor(parseAuthor(requestURI)).toString());

        return returnString.toString();
    }

    @Override
    protected String onDelete(URI requestURI) {

        final StringJoiner returnString = new StringJoiner("\n");
        returnString.add("deleted author:");

        final long id = getId(requestURI).orElseThrow(() -> new ResourceNotFoundException("Can't delete author without id."));
        returnString.add(authorService.deleteAuthor(id).toString());

        return returnString.toString();
    }

    @Override
    protected String onGet(URI requestURI) {

        final StringJoiner returnString = new StringJoiner("\n");
        returnString.add("author list:");

        final OptionalLong id = getId(requestURI);

        if (id.isPresent()) {
            returnString.add(authorService.getByIdAuthor(id.getAsLong()).toString());

        } else {
            final List<Author> authorList = authorService.fullListOfAuthors();

            for (Author author : authorList) {
                returnString.add(author.toString());
            }
        }

        return returnString.toString();
    }
}
