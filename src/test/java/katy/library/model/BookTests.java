package katy.library.model;

import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class BookTests {

    @Test
    public void newbook() {

        final Author author = Author.builder()
                .id(1)
                .firstName("Terry")
                .lastName("Pratchett")
                .dateOfBirth(LocalDate.of(1948, Month.APRIL,28))
                .build();

        final Book book = Book.builder()
                .id(1)
                .title("DiscWorld")
                .author(author)
                .build();


        assertEquals(1, book.getId());
        assertEquals("DiscWorld", book.getTitle());
        assertEquals(author, book.getAuthor());

        assertEquals("Book(id=1, title=DiscWorld, author=Terry Pratchett)", book.toString());


        final Book book2 = new Book(1, new String("DiscWorld"), author);

        assertEquals(true, book.equals(book2));
        assertEquals(book, book2);

        assertEquals(book.hashCode(), book2.hashCode());

        final Book book3 = new Book(1, "DiscWorld1", author);

        assertNotEquals(book, book3);

        final Book book4 = new Book(1, null, author);

        assertNotEquals(book, book4);
        assertNotEquals(book4, book);

        assertEquals(new Book(1, "myName", author), book.withTitle("myName"));
    }
}
