package katy.library.dao;

import java.util.Optional;

public interface CrudDAO<T> {
    Optional<T> getById(long id);

    T create(T entry);

    T update(T entry);

    Optional<T> delete(long id);
}
