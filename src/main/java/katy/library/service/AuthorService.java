package katy.library.service;

import katy.library.dao.AuthorDao;
import katy.library.dao.map.AuthorMapDao;
import katy.library.exception.ResourceNotFoundException;
import katy.library.exception.ValidationException;
import katy.library.model.Author;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AuthorService {

    private AuthorDao dao;

    public AuthorService(AuthorDao dao) {
        this.dao = dao;
    }

    private void validateNotNull(Object value, String fieldName) {
        if (value == null) {
            throw new ValidationException("Field should not be null", fieldName);
        }
    }

    public Author getByIdAuthor(long id){

        Optional<Author> optionalauthor = dao.getById(id);

        return optionalauthor.orElseThrow(()-> new ResourceNotFoundException("Can't find author with id " + id));
    }

    public  Author createAuthor(Author author){

        validateNotNull(author.getFirstName(), "firstname");
        validateNotNull(author.getLastName(),  "lastname");
        validateNotNull(author.getDateOfBirth(), "dateofbirth");

        return dao.create(author);
    }

    public  Author updateAuthor(Author author){

        validateNotNull(author.getFirstName(), "firstname");
        validateNotNull(author.getLastName(),  "lastname");
        validateNotNull(author.getDateOfBirth(), "dateofbirth");

        return dao.update(author);
    }

    public Author deleteAuthor(long id){

        Optional<Author> optionalauthor = dao.getById(id);

        return optionalauthor.orElseThrow(()-> new ResourceNotFoundException("Can't find author with id " + id));
    }

    public List<Author> findByNameAuthor(String lastName){

        Objects.requireNonNull(lastName, "Lastname required.");

        return dao.findByName(lastName);
    }
}
