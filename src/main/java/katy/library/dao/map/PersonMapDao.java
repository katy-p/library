package katy.library.dao.map;


import katy.library.dao.PersonDao;
import katy.library.model.Person;

import java.util.*;

public class PersonMapDao implements PersonDao {

    private Map<Long, Person> personMap = new HashMap<>();
    private long lastId = 0;


    @Override
    public Optional<Person> getById(long id) {

        if (!personMap.containsKey(id)) {
            return Optional.empty();

        } else {
            Person person = personMap.get(id);
            return Optional.of(person);
        }
    }

    @Override
    public Person create(Person entry) {
        lastId += 1;

        Person person = entry.withId(lastId);

        personMap.put(lastId, person);

        return person;
    }

    @Override
    public Person update(Person entry) {

        Optional<Person> person = getById(entry.getId());

        if (!person.isPresent()) {
            return create(entry);

        } else {
            personMap.put(entry.getId(), entry);
            return personMap.get(entry.getId());
        }
    }

    @Override
    public Optional<Person> delete(long id) {

        Optional<Person> person = getById(id);

        if (person.isPresent()) {
            personMap.remove(id);
        }
        return person;
    }

    @Override
    public List<Person> findByName(String lastName) {
        Objects.requireNonNull(lastName);

        List<Person> res = new ArrayList<>();
        for (Person person : personMap.values()) {
            if (lastName.equals(person.getLastName())){
                res.add(person);
            }
        }
        return res;
    }
}
