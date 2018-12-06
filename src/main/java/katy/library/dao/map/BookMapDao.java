package katy.library.dao.map;

import katy.library.dao.BookDao;
import katy.library.model.Author;
import katy.library.model.Book;

import java.util.*;


public class BookMapDao implements BookDao {


    private Map<Long, Book> bookMap = new HashMap<>();
    private long lastId = 0;

    @Override
    public Optional<Book> getById(long id) {

        if (!bookMap.containsKey(id)) {
            return Optional.empty();

        } else {
            Book book = bookMap.get(id);
            return Optional.of(book);
        }
    }

    @Override
    public Book create(Book entry) {
        lastId += 1;

        Book book = entry.withId(lastId);

        bookMap.put(lastId, book);

        return book;
    }

    @Override
    public Book update(Book entry) {

        Optional<Book> book = getById(entry.getId());

        if (!book.isPresent()) {
            return create(entry);

        } else {
            bookMap.put(entry.getId(), entry);
            return bookMap.get(entry.getId());
        }
    }

    @Override
    public Optional<Book> delete(long id) {

        Optional<Book> book = getById(id);

        if (book.isPresent()) {
            bookMap.remove(id);
        }
        return book;

    }

    @Override
    public List<Book> findByAuthor(Author author) {

        Objects.requireNonNull(author);

        List <Book> res = new ArrayList<>();

        for (Book book : bookMap.values()) {

            if (author.equals(book.getAuthor())){
                res.add(book);
            }

        }
        return res;
    }

    @Override
    public List<Book> findByTitle(String title) {

        Objects.requireNonNull(title);

        List <Book> res = new ArrayList<>();

        for (Book book : bookMap.values()) {

            if (title.equals(book.getTitle())){
                res.add(book);
            }

        }
        return res;
    }

    @Override
    public List<Book> fullList() {

        List <Book> res = new ArrayList<>();
        for (Book book : bookMap.values()) {
            res.add(book);
        }
        return res;
    }


}
