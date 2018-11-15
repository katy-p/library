package katy.library.dao.dummy;

import katy.library.dao.LibraryCardDao;
import katy.library.model.Author;
import katy.library.model.Book;
import katy.library.model.LibraryCard;
import katy.library.model.Person;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LibraryCardDaoImpl implements LibraryCardDao {

    private Author author1 = Author.builder()
            .id(1)
            .firstName("Terry")
            .lastName("Pratchett")
            .dateOfBirth(LocalDate.of(1948, Month.APRIL, 28))
            .build();

    private Book book1 = Book.builder()
            .id(1)
            .title("DiscWorld")
            .author(author1)
            .build();

    private Person person1 = Person.builder()
            .id(-1)
            .firstName("Anna")
            .lastName("Smith")
            .dateOfBirth(LocalDate.of(1980, Month.APRIL, 28))
            .build();

    private LibraryCard libraryCard = LibraryCard.builder()
            .id(-1)
            .person(person1)
            .book(book1)
            .build();



    @Override
    public Optional<LibraryCard> getById(long id) {

        if (id == 1) {
            return Optional.of(libraryCard);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public LibraryCard create(LibraryCard entry) {
        return null;
    }

    @Override
    public LibraryCard update(LibraryCard entry) {
        return null;
    }

    @Override
    public Optional<LibraryCard> delete(long id) {
        return Optional.empty();
    }

    @Override
    public List<LibraryCard> findByPerson(Person person) {

        List<LibraryCard> lbList = new ArrayList<>();

        if (person1.equals(person)) {
            lbList.add(libraryCard);
        }

        return lbList;
    }

    @Override
    public List<LibraryCard> findByBook(Book book) {

        List<LibraryCard> lbList = new ArrayList<>();

        if (book1.equals(book)) {
            lbList.add(libraryCard);
        }

        return lbList;
    }
}
