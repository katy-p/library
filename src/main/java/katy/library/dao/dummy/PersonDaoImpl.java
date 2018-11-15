package katy.library.dao.dummy;

import katy.library.dao.PersonDao;
import katy.library.model.Person;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonDaoImpl implements PersonDao {

    private Person person1 = Person.builder()
            .id(-1)
            .firstName("Anna")
            .lastName("Smith")
            .dateOfBirth(LocalDate.of(1980, Month.APRIL, 28))
            .build();

    @Override
    public Optional<Person> getById(long id) {

        if (id == 1) {
            return Optional.of(person1);
        } else {
            return Optional.empty();
        }
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

    @Override
    public List<Person> findByName(String lastName) {

        List<Person> personList = new ArrayList<>();

        if ("Smith".equals(lastName)) {
            personList.add(person1);
        }

        return personList;
    }
}
