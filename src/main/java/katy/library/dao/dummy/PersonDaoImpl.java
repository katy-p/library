package katy.library.dao.dummy;

import katy.library.dao.PersonDao;
import katy.library.model.Person;

import java.util.List;
import java.util.Optional;

public class PersonDaoImpl implements PersonDao {

    @Override
    public List<Person> findByName(String lastName) {
        return null;
    }

    @Override
    public Optional<Person> getById(long id) {
        return Optional.empty();
    }

    @Override
    public Person create(Person entry) {
        return null;
    }

    @Override
    public Person update(Person entry) {
        return null;
    }

    @Override
    public Optional<Person> delete(long id) {
        return Optional.empty();
    }
}
