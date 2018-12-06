package katy.library.dao;


import katy.library.model.Person;

import java.util.List;

public interface PersonDao extends CrudDAO<Person> {
    List<Person> findByName(String lastName);

    List<Person> fullList();
}
