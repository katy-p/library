package katy.library.dao.map;

import katy.library.dao.LibraryCardDao;
import katy.library.model.Book;
import katy.library.model.LibraryCard;
import katy.library.model.Person;

import java.util.*;

public class LibraryCardMapDao implements LibraryCardDao {

    private Map<Long, LibraryCard> libraryCardMap = new HashMap<>();
    private long lastId = 0;

    @Override
    public Optional<LibraryCard> getById(long id) {

        if (libraryCardMap.containsKey(id)) {
            return Optional.of(libraryCardMap.get(id));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public LibraryCard create(LibraryCard entry) {

        lastId += 1;
        LibraryCard libraryCard = entry.withId(lastId);

        libraryCardMap.put(lastId, libraryCard);

        return libraryCard;
    }

    @Override
    public LibraryCard update(LibraryCard entry) {

        Optional<LibraryCard> libraryCard = getById(entry.getId());

        if (libraryCard.isPresent()) {
            libraryCardMap.put(entry.getId(), entry);
            return libraryCardMap.get(entry.getId());
        }else{
            return  create(entry);
        }
    }

    @Override
    public Optional<LibraryCard> delete(long id) {

        Optional<LibraryCard> libraryCard = getById(id);

        if (libraryCard.isPresent()) {
            libraryCardMap.remove(id);
        }
        return libraryCard;
    }

    @Override
    public List<LibraryCard> findByPerson(Person person) {

        Objects.requireNonNull(person);

        List <LibraryCard> res = new ArrayList<>();

        for (LibraryCard libraryCard : libraryCardMap.values()) {
            if (person.equals(libraryCard.getPerson())){
                res.add(libraryCard);
            }
        }
        return res;
    }

    @Override
    public List<LibraryCard> findByBook(Book book) {

        Objects.requireNonNull(book);

        List <LibraryCard> res = new ArrayList<>();

        for (LibraryCard libraryCard : libraryCardMap.values()) {
            if (book.equals(libraryCard.getBook())){
                res.add(libraryCard);
            }
        }
        return res;

    }
}
