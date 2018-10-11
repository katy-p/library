package katy.library.service;

import katy.library.dao.map.PersonMapDao;
import katy.library.model.Person;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PersonServise {

    private PersonMapDao dao = new PersonMapDao();


    public Optional<Person> getByIdPerson(long id){

        Objects.requireNonNull(id, "Id required.");

        return dao.getById(id);
    }

    public  Person createPerson(String firstName, String lastName, LocalDate dateOfBirth){

        Objects.requireNonNull(firstName, "Firstname required.");
        Objects.requireNonNull(lastName, "Lastname required.");
        Objects.requireNonNull(lastName, "Lastname required.");


        Person person = Person.builder()
                .id(1)
                .firstName(firstName)
                .lastName(lastName)
                .dateOfBirth(dateOfBirth)
                .build();

        return dao.create(person);
    }

    public  Person updatePerson(long id, String firstName, String lastName, LocalDate dateOfBirth){

        Objects.requireNonNull(id, "Id required.");
        Objects.requireNonNull(firstName, "Firstname required.");
        Objects.requireNonNull(lastName, "Lastname required.");
        Objects.requireNonNull(lastName, "Lastname required.");


        Person person = Person.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .dateOfBirth(dateOfBirth)
                .build();

        return dao.update(person);
    }

    public Optional<Person> deletePerson(long id){

        Objects.requireNonNull(id, "Id required.");

        return dao.delete(id);
    }

    public List<Person> findByNamePerson(String lastName){

        Objects.requireNonNull(lastName, "Lastname required.");

        return dao.findByName(lastName);
    }
}
