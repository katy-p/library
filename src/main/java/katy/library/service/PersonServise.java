package katy.library.service;

import katy.library.dao.PersonDao;
import katy.library.dao.map.PersonMapDao;
import katy.library.exception.ResourceNotFoundException;
import katy.library.exception.ValidationException;
import katy.library.model.Person;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PersonServise {

    private PersonDao dao;

    public PersonServise(PersonDao dao) {
        this.dao = dao;
    }


    private void validateNotNull(Object value, String fieldName) {
        if (value == null) {
            throw new ValidationException("Field should not be null", fieldName);
        }
    }


    public Person getByIdPerson(long id){

        Optional<Person> optionalPerson = dao.getById(id);

        return optionalPerson.orElseThrow(() -> new ResourceNotFoundException("Can't find person with id " + id));
    }

    public  Person createPerson(Person person){

        validateNotNull(person.getLastName(), "lastname");
        validateNotNull(person.getFirstName(), "firstname");
        validateNotNull(person.getDateOfBirth(), "dateofbirth");

       return dao.create(person);
    }

    public  Person updatePerson(Person person){

        validateNotNull(person.getLastName(), "lastname");
        validateNotNull(person.getFirstName(), "firstname");
        validateNotNull(person.getDateOfBirth(), "dateofbirth");

        return dao.update(person);
    }

    public Person deletePerson(long id){

        Optional<Person> optionalPerson = dao.delete(id);

        return optionalPerson.orElseThrow(()-> new ResourceNotFoundException("Can't find person with id " + id));
    }

    public List<Person> findByNamePerson(String lastName){

        Objects.requireNonNull(lastName, "Lastname required.");

        return dao.findByName(lastName);
    }

    public List<Person> fullListOfPersons(){

        return dao.fullList();
    }
}
