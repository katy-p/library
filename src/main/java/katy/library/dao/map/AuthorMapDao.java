package katy.library.dao.map;

import katy.library.dao.AuthorDao;
import katy.library.model.Author;

import java.util.*;

public class AuthorMapDao implements AuthorDao {

    private Map<Long, Author> authorMap = new HashMap<>();
    private long lastId = 0;

    @Override
    public Optional<Author> getById(long id) {

        if (!authorMap.containsKey(id)) {
            return Optional.empty();

        } else {
            Author author = authorMap.get(id);
            return Optional.of(author);
        }
    }

    @Override
    public Author create(Author entry) {
        lastId += 1;

        Author author = entry.withId(lastId);

        authorMap.put(lastId, author);

        return author;
    }

    @Override
    public Author update(Author entry) {

        Optional<Author> author = getById(entry.getId());

        if (!author.isPresent()) {
            return create(entry);

        } else {
            authorMap.put(entry.getId(), entry);
            return authorMap.get(entry.getId());
        }
    }

    @Override
    public Optional<Author> delete(long id) {

        Optional<Author> author = getById(id);

        if (author.isPresent()) {
            authorMap.remove(id);
        }
        return author;
    }

    @Override
    public List<Author> findByName(String lastName) {

        Objects.requireNonNull(lastName);

        List <Author> res = new ArrayList<Author>();

        for (Author author : authorMap.values()) {

            if (lastName.equals(author.getLastName())){
                res.add(author);
            }

        }
        return res;
    }
}
