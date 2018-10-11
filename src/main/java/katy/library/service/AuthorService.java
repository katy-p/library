package katy.library.service;

import katy.library.dao.map.AuthorMapDao;
import katy.library.model.Author;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AuthorService {

    private AuthorMapDao dao = new AuthorMapDao();


    public Optional<Author> getByIdAuthor(long id){

        Objects.requireNonNull(id, "Id required.");

        return dao.getById(id);
    }

    public  Author createAuthor(String firstName, String lastName, LocalDate dateOfBirth){

        Objects.requireNonNull(firstName, "Firstname required.");
        Objects.requireNonNull(lastName, "Lastname required.");
        Objects.requireNonNull(lastName, "Lastname required.");


        Author author = Author.builder()
                .id(1)
                .firstName(firstName)
                .lastName(lastName)
                .dateOfBirth(dateOfBirth)
                .build();

        return dao.create(author);
    }

    public  Author updateAuthor(long id, String firstName, String lastName, LocalDate dateOfBirth){

        Objects.requireNonNull(id, "Id required.");
        Objects.requireNonNull(firstName, "Firstname required.");
        Objects.requireNonNull(lastName, "Lastname required.");
        Objects.requireNonNull(lastName, "Lastname required.");


        Author author = Author.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .dateOfBirth(dateOfBirth)
                .build();

        return dao.update(author);
    }

    public Optional<Author> deleteAuthor(long id){

        Objects.requireNonNull(id, "Id required.");

        return dao.delete(id);
    }

    public List<Author> findByNameAuthor(String lastName){

        Objects.requireNonNull(lastName, "Lastname required.");

        return dao.findByName(lastName);
    }
}
